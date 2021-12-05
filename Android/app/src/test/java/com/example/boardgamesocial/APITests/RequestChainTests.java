package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;


import android.nfc.Tag;
import android.util.Log;

import com.example.boardgamesocial.API.APIMode;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;


public class RequestChainTests {
    public static final String TAG = "RequestChainTests";
    public static final Token token = new Token("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1");
    public static RetrofitClient retrofitClient;

    @BeforeClass
    public static void beforeClass() {
        RetrofitClient.apiMode = APIMode.TEST;
        retrofitClient = RetrofitClient.getClient();
        retrofitClient.setAuthToken(token.getToken());
    }

    @Test
    public void getCallChainTest(){
        retrofitClient.getCall(User.class, new HashMap<>())
                .flatMap(jsonArray -> {
                    User user = getObjectList(jsonArray, User.class).get(0);
                    assertNotNull(user);
                    return retrofitClient.getCall(Post.class, new HashMap<String, String>(){{
                        put("userId", String.valueOf(user.getId()));
                    }});
                })
                .test()
                .assertNoErrors();
    }

    @Test
    public void multiGetCallChainTest() {
        retrofitClient.getCall(User.class, new HashMap<>())
                .doOnNext(jsonArray -> {
                    List<User> userList = getObjectList(jsonArray, User.class);
                    JsonArray postList = new JsonArray();
                    for (User user: userList) {
                        retrofitClient.getCall(Post.class, new HashMap<String, String>(){{
                                    put("userId", String.valueOf(user.getId()));
                                }})
                                .doOnNext(jsonArray2 -> {
                                    postList.addAll(jsonArray2);
                                    Log.i(TAG, String.format("multiGetCallChainTest: %s", postList));
                                    assertFalse(postList.isEmpty());
                                })
                                .test()
                                .assertNoErrors();
                    }
                    assertFalse(jsonArray.isEmpty());
                })
                .test()
                .assertNoErrors();
    }

    @Test
    public void mergeGetCallChainTest() {
        retrofitClient.getCall(User.class, new HashMap<>())
                .mergeWith(retrofitClient.getCall(Post.class, new HashMap<>()))
                .mergeWith(retrofitClient.getCall(Game.class, new HashMap<>()))
                .mergeWith(retrofitClient.getCall(Event.class, new HashMap<>()))
                .scan((cumulativeJsonArray, newJsonArray) -> {
                    cumulativeJsonArray.addAll(newJsonArray);
                    return cumulativeJsonArray;
                })
                .doOnNext(jsonArrayCombined -> Log.i(TAG, String.format("mergeGetCallChainTest: %s", jsonArrayCombined)))
                .test()
                .assertNoErrors();
    }

    @Test
    public void populatePostsChainTest() {
        Integer userId = 245;
        retrofitClient.getCall(UserToUser.class, new HashMap<String, String>(){{
                    put("userOneId", String.valueOf(userId));
                }})
                .flatMap(jsonArray -> {
                    Observable<JsonArray> postsObservable = retrofitClient.getCall(Post.class, new HashMap<String, String>(){{
                        put("private", "False");
                    }});
                    List<UserToUser> usersWithPrivateAccess = getObjectList(jsonArray, UserToUser.class);
                    for (UserToUser userWithPrivateAccess : usersWithPrivateAccess) {
                        postsObservable.mergeWith(retrofitClient.getCall(Post.class, new HashMap<String, String>(){{
                            put("userId", String.valueOf(userWithPrivateAccess.getUserTwoId()));
                            put("private", "True");
                        }})).test().assertNoErrors();
                    }
                    return postsObservable;
                })
                .scan((cumulativeJsonArray, newJsonArray) -> {
                    cumulativeJsonArray.addAll(newJsonArray);
                    return cumulativeJsonArray;
                })
                .doOnNext(jsonArrayCombined -> Log.i(TAG, String.format("populatePostsChainTest: FINAL %s", jsonArrayCombined)))
                .test()
                .assertNoErrors();
    }
}
