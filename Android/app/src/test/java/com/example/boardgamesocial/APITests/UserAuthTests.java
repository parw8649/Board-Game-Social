package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.example.boardgamesocial.API.APIMode;
import com.example.boardgamesocial.API.HeaderInterceptor;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.JsonArray;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

@FixMethodOrder(MethodSorters.JVM)
public class UserAuthTests {

    private static RetrofitClient retrofitClient;
    private static final Token token = new Token("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1");
    private static final User user = new User(
            "testUserRetrofitU",
            "testUserRetrofitP"
    );

    @BeforeClass
    public static void beforeClass() {
        RetrofitClient.apiMode = APIMode.TEST;
        retrofitClient = RetrofitClient.getClient();
        retrofitClient.setAuthToken(token.getToken());
    }

    @AfterClass
    public static void afterClass() {
        HeaderInterceptor.token.setToken(token.getToken());
        retrofitClient.deleteCall(User.class, new HashMap<String, String>(){{
                    put("username", user.getUsername());
                }})
                .test()
                .assertNoErrors();
    }

    @Test
    public void signUpTest() {
        retrofitClient.signUpCall(user)
                .test()
                .assertNoErrors();
    }

    @Test
    public void loginTest(){
        retrofitClient.loginCall(user)
                .switchMap(token -> {
                    assertNotNull(token);
                    HeaderInterceptor.token.setToken(token.getToken());
                    assertFalse(HeaderInterceptor.token.getToken().isEmpty());
                    return retrofitClient.getCall(User.class, new HashMap<String, String>(){{
                        put("username", user.getUsername());
                    }});
                })
                .test()
                .assertNoErrors();
        retrofitClient.getCall(User.class, new HashMap<String, String>(){{
            put("username", user.getUsername());
        }}).subscribe(jsonArray -> user.setId(getObjectList(jsonArray, User.class).get(0).getId()));
    }

    @Test
    public void logoutTest(){
        retrofitClient.logoutCall(new HashMap<String, String>(){{
                    put("user_id", String.valueOf(user.getId()));
                }})
                .switchMap(jsonObject -> {
                    assertNotNull(jsonObject);
                    return retrofitClient.getCall(User.class, new HashMap<>());
                })
                .onExceptionResumeNext(Observer::onComplete)
                .test()
                .assertNoErrors();
    }
}
