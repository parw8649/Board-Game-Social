package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.example.boardgamesocial.API.API;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.APITests.DBTestStage.StageAction;
import com.example.boardgamesocial.APITests.DBTestStage.StageSettings;
import com.example.boardgamesocial.APITests.DBTestStage.TestStage;
import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Game;
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

    public static final User testUser = new User("testUserRetrofit1U", "testUserRetrofit1P");
    public static final User testUserModified = new User("testUserRetrofit1U-MOD", "testUserRetrofit1P-MOD");

    public static final Game testGame = new Game("testGameRetrofit1GT", "testGameRetrofit1GG", 1 ,2);
    public static final Game testGameModified = new Game("testGameRetrofit1GT-MOD", "testGameRetrofit1GG-MOD", 2 ,3);

    private static final Map<Object, Map<String, String>> contextObjects = new HashMap<>();

    private <K> Map<TestStage, StageAction> getGenericActionMap(Class<K> cls, List<Field> reqFields) {
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

    private <T> void createContextObject(T object, Map<String, String> filter) throws IOException {
        contextObjects.put(object, filter);
        retrofit.postCall(object.getClass(), object).execute();
    }

    private <T> void deleteContextObject(Class<T> cls, Map<String, String> filter) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<T> foundObjects = getObjectList(retrofit.getCall(cls, filter).execute().body(), cls);

        Field idField = cls.getDeclaredField("id");
        idField.setAccessible(true);

        retrofit.deleteCall(cls, new HashMap<String, String>(){{
            put("id", String.valueOf(idField.get(foundObjects.get(0))));
        }}).execute();
    }

    @After
    public void tearDown() throws Exception {
        for (Map.Entry<Object, Map<String, String>> entry: contextObjects.entrySet()) {
            deleteContextObject(entry.getKey().getClass(), entry.getValue());
        }
        contextObjects.clear();
    }

    @Test
    public void userTests() {
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
                        put("username", testUser.getUsername());
                    }},
                    new HashMap<String, String>(){{
                        put("username", testUserModified.getUsername());
                    }},
                    new HashMap<String, String>(){{
                        put("username", testUserModified.getUsername());
                    }},
                    testUser,
                    new HashMap<String, String>(){{
                        put("username", testUserModified.getUsername());
                        put("password", testUserModified.getPassword());
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
    public void eventTests() {
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            Event testEvent = new Event(
                    "testEventRetrofitN",
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId()
            );
            Event testEventModified = new Event("testEventRetrofitN-MOD", testEvent.getHostUserId());
            StageSettings<Event> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            Event.class,
                            Arrays.asList(
                                    Event.class.getDeclaredField("name"),
                                    Event.class.getDeclaredField("hostUserId")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("name", "Admin Event");
                    }},
                    new HashMap<String, String>(){{
                        put("name", testEvent.getName());
                    }},
                    new HashMap<String, String>(){{
                        put("name", testEventModified.getName());
                    }},
                    new HashMap<String, String>(){{
                        put("name", testEventModified.getName());
                    }},
                    testEvent,
                    new HashMap<String, String>(){{
                        put("name", testEventModified.getName());
                        put("hostUserId", String.valueOf(testEventModified.getHostUserId()));
                    }},
                    Event.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
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
