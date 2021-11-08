package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.boardgamesocial.API.HeaderInterceptor;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.HashMap;

@FixMethodOrder(MethodSorters.JVM)
public class UserAuthTests {

    private static final RetrofitClient retrofit = RetrofitClient.getClient();
    public static final User user = new User(
            "testUserRetrofitU",
            "testUserRetrofitP"
    );

    @AfterClass
    public static void afterClass() throws Exception {
        HeaderInterceptor.token.setToken("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1");
        retrofit.deleteCall(User.class, new HashMap<String, String>(){{
            put("username", user.getUsername());
        }}).execute();
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
            user.setId(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", user.getUsername());
                                    }}
                            ).execute().body(),
                            User.class
                    ).get(0).getId()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logoutTest(){
        try {
            assertEquals(200,
                retrofit.logoutCall(new HashMap<String, String>(){{
                    put("user_id", String.valueOf(user.getId()));
                }}).execute().code()
            );
            assertNull(retrofit.getCall(User.class, new HashMap<>()).execute().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
