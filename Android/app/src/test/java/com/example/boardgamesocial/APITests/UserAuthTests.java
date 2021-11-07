package com.example.boardgamesocial.APITests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.boardgamesocial.API.HeaderInterceptor;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class UserAuthTests {

    private static RetrofitClient retrofit;
    public static final User user = new User(
            "testUserRetrofitU",
            "testUserRetrofitP"
    );

    @BeforeClass
    public static void beforeClass() throws Exception {
        HeaderInterceptor.token = new Token("");
        retrofit = RetrofitClient.getClient();
    }

    @Test
    public void signUpTest() {
        try {
            assertEquals(200, retrofit.signUpCall(user).execute().code());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest(){
        try {
            Token token = retrofit.loginCall(user).execute().body();
            assertNotNull(token);
            HeaderInterceptor.token.setToken(token.getToken());
            assertFalse(HeaderInterceptor.token.getToken().isEmpty());
            assertNotNull(retrofit.getCall(User.class, new HashMap<>()).execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logoutTest(){
        try {
            assertEquals(200,
                retrofit.logoutCall(new HashMap<String, String>(){{
                    put("userId", String.valueOf(user.getId()));
                }}).execute().code()
            );
            assertNotNull(retrofit.getCall(User.class, new HashMap<>()).execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
