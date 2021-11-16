package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;


import android.nfc.Tag;
import android.util.Log;

import com.example.boardgamesocial.API.APIMode;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.JsonArray;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class RequestChainTests {
    public static final String TAG = "RequestChainTests";
    public static RetrofitClient retrofitClient;

    @BeforeClass
    public static void beforeClass() {
        RetrofitClient.apiMode = APIMode.TEST;
        retrofitClient = RetrofitClient.getClient();
        retrofitClient.setAuthToken(new Token("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1"));
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
}
