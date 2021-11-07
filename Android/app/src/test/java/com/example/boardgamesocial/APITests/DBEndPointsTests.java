package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.example.boardgamesocial.API.API;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.APITests.DBTestStage.StageAction;
import com.example.boardgamesocial.APITests.DBTestStage.StageSettings;
import com.example.boardgamesocial.APITests.DBTestStage.TestStage;
import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DBEndPointsTests {

    private static final RetrofitClient retrofit = RetrofitClient.getClient();

    private <K> Map<TestStage, StageAction> getGenericActionMap(Class<K> cls, List<Field> reqFields){
        return new HashMap<TestStage, StageAction>(){{
            put(TestStage.GET_TEST_FILTER, apiResponse -> {
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.POST_TEST, apiResponse -> {
                assertNotNull(apiResponse);
                K apiResponseObject = getObject((JsonObject) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseObject));
                }
            });
            put(TestStage.GET_TEST_POST, apiResponse -> {
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.PUT_TEST, apiResponse -> {
                assertNotNull(apiResponse);
                K apiResponseObject = getObject((JsonObject) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseObject));
                }
            });
            put(TestStage.GET_TEST_PUT, apiResponse -> {
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.DELETE_TEST, apiResponse -> {
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.GET_TEST_DELETE, apiResponse -> {
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertTrue(apiResponseList.isEmpty());
                }
            });
        }};
    }


    @Test
    public void userTests(){
        try {
            StageSettings<User> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            User.class,
                            Arrays.asList(
                                User.class.getDeclaredField("username"),
                                User.class.getDeclaredField("password")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("username", "admin");
                    }},
                    new HashMap<String, String>(){{
                        put("username", "testUserRetrofit1U");
                    }},
                    new HashMap<String, String>(){{
                        put("username", "testUserRetrofit1U-MOD");
                    }},
                    new HashMap<String, String>(){{
                        put("username", "testUserRetrofit1U-MOD");
                    }},
                    new User("testUserRetrofit1U", "testUserRetrofit1P"),
                    new HashMap<String, String>(){{
                        put("username", "testUserRetrofit1U-MOD");
                        put("password", "testUserRetrofit1P-MOD");
                    }},
                    User.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void eventTests(){

    }

    @Test
    public void postTests(){

    }

    @Test
    public void gameTests(){

    }

    @Test
    public void userToUserTests(){

    }

    @Test
    public void messageTests(){

    }

    @Test
    public void eventToUserTests(){

    }

    @Test
    public void commentTests(){

    }

    @Test
    public void gameToUserTests(){

    }

    @Test
    public void hostedGameTests(){

    }

    @Test
    public void reviewTests(){

    }

    @Test
    public void hostedGameToUserTests() {

    }

}
