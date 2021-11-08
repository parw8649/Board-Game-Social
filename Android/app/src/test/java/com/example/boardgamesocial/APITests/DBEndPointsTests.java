package com.example.boardgamesocial.APITests;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;
import static com.example.boardgamesocial.APITests.DBTestStage.ConsoleColor.colorText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.util.Log;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.boardgamesocial.API.HeaderInterceptor;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.APITests.DBTestStage.ConsoleColor;
import com.example.boardgamesocial.APITests.DBTestStage.StageAction;
import com.example.boardgamesocial.APITests.DBTestStage.StageSettings;
import com.example.boardgamesocial.APITests.DBTestStage.TestStage;
import com.example.boardgamesocial.DataClasses.Comment;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.HostedGame;
import com.example.boardgamesocial.DataClasses.Message;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.EventToUser;
import com.example.boardgamesocial.DataClasses.Relationships.GameToUser;
import com.example.boardgamesocial.DataClasses.Relationships.HostedGameToUser;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataClasses.Review;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBEndPointsTests {

    private static final RetrofitClient retrofit = RetrofitClient.getClient();

    public static final User testUser = new User("testUserRetrofit1U", "testUserRetrofit1P");
    public static final User testUserModified = new User("testUserRetrofit1U-MOD", "testUserRetrofit1P-MOD");

    public static final Game testGame = new Game("testGameRetrofit1GT", "testGame1GG", 1 ,2);
    public static final Game testGameModified = new Game("testGameRetrofit1GT-MOD", "testGame1GG-MOD", 2 ,3);

    private static final Map<Object, Map<String, String>> contextObjects = new HashMap<>();

    public static final String CONTEXT_OBJECTS_TAG = colorText(ConsoleColor.ANSI_CYAN, "Context objects");
    public static final String GET_TEST_TAG = colorText(ConsoleColor.ANSI_CYAN,"Get test");
    public static final String POST_TEST_TAG = colorText(ConsoleColor.ANSI_CYAN,"Post test");
    public static final String PUT_TEST_TAG = colorText(ConsoleColor.ANSI_CYAN,"Put test");
    public static final String DELETE_TEST_TAG = colorText(ConsoleColor.ANSI_CYAN,"Delete test");

    private <K> Map<TestStage, StageAction> getGenericActionMap(Class<K> cls, List<Field> reqFields) {
        return new HashMap<TestStage, StageAction>(){{
            put(TestStage.GET_TEST_FILTER, apiResponse -> {
                Log.d(GET_TEST_TAG, "Testing Filter GET");
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.POST_TEST, apiResponse -> {
                Log.d(POST_TEST_TAG, "Testing POST");
                assertNotNull(apiResponse);
                K apiResponseObject = getObject((JsonObject) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseObject));
                }
            });
            put(TestStage.GET_TEST_POST, apiResponse -> {
                Log.d(GET_TEST_TAG, "Testing POST with GET");
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.PUT_TEST, apiResponse -> {
                Log.d(PUT_TEST_TAG, "Testing PUT");
                assertNotNull(apiResponse);
                K apiResponseObject = getObject((JsonObject) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseObject));
                }
            });
            put(TestStage.GET_TEST_PUT, apiResponse -> {
                Log.d(GET_TEST_TAG, "Testing PUT with GET");
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.DELETE_TEST, apiResponse -> {
                Log.d(DELETE_TEST_TAG, "Testing DELETE");
                assertNotNull(apiResponse);
                List<K> apiResponseList = getObjectList((JsonArray) apiResponse, cls);
                for (Field field : reqFields) {
                    field.setAccessible(true);
                    assertNotNull(field.get(apiResponseList.get(0)));
                }
            });
            put(TestStage.GET_TEST_DELETE, apiResponse -> {
                Log.d(GET_TEST_TAG, "Testing DELETE with GET");
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
        Log.d(CONTEXT_OBJECTS_TAG, String.format("Creating Object %s", object));
        contextObjects.put(object, filter);
        retrofit.postCall(object.getClass(), object).execute();
    }

    private <T> void deleteContextObject(Class<T> cls, Map<String, String> filter) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<T> foundObjects = getObjectList(retrofit.getCall(cls, filter).execute().body(), cls);

        Field idField = cls.getDeclaredField("id");
        idField.setAccessible(true);

        Log.d(CONTEXT_OBJECTS_TAG, String.format("Deleting Object id:%s | %s", idField.get(foundObjects.get(0)), filter));
        retrofit.deleteCall(cls, new HashMap<String, String>(){{
            put("id", String.valueOf(idField.get(foundObjects.get(0))));
        }}).execute();
    }

    @BeforeClass
    public static void beforeClass() {
        HeaderInterceptor.token = new Token("e4a36ccc86bc71b7e78c5d42bbd3109ab4764af1");
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
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            Post testPost = new Post(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    "testPostRetrofitB",
                    "testPostRetrofitT"
            );
            Post testPostModified = new Post(
                    testPost.getUserId(),
                    "testPostRetrofitB-MOD",
                    "testPostT-MOD"
            );
            StageSettings<Post> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            Post.class,
                            Arrays.asList(
                                    Post.class.getDeclaredField("userId"),
                                    Post.class.getDeclaredField("postBody"),
                                    Post.class.getDeclaredField("postType")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("postBody", "Admin Post Body");
                    }},
                    new HashMap<String, String>(){{
                        put("postBody", testPost.getPostBody());
                    }},
                    new HashMap<String, String>(){{
                        put("postBody", testPostModified.getPostBody());
                    }},
                    new HashMap<String, String>(){{
                        put("postBody", testPostModified.getPostBody());
                    }},
                    testPost,
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testPostModified.getUserId()));
                        put("postBody", testPostModified.getPostBody());
                        put("postType", testPostModified.getPostType());
                    }},
                    Post.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void gameTests(){
        try {
            StageSettings<Game> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            Game.class,
                            Arrays.asList(
                                    Game.class.getDeclaredField("gameTitle"),
                                    Game.class.getDeclaredField("genre"),
                                    Game.class.getDeclaredField("minPlayer"),
                                    Game.class.getDeclaredField("maxPlayer")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("gameTitle", "gameTitleTest1");
                    }},
                    new HashMap<String, String>(){{
                        put("gameTitle", testGame.getGameTitle());
                    }},
                    new HashMap<String, String>(){{
                        put("gameTitle", testGameModified.getGameTitle());
                    }},
                    new HashMap<String, String>(){{
                        put("gameTitle", testGameModified.getGameTitle());
                    }},
                    testGame,
                    new HashMap<String, String>(){{
                        put("gameTitle", testGameModified.getGameTitle());
                        put("genre", testGameModified.getGenre());
                        put("minPlayer", String.valueOf(testGameModified.getMinPlayer()));
                        put("maxPlayer", String.valueOf(testGameModified.getMaxPlayer()));
                    }},
                    Game.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void userToUserTests(){
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            createContextObject(testUserModified, new HashMap<String, String>(){{
                put("username", testUserModified.getUsername());
            }});
            UserToUser testUserToUser = new UserToUser(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUserModified.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId()
            );
            Map<TestStage, StageAction> actionMap = getGenericActionMap(
                    UserToUser.class,
                    Arrays.asList(
                            UserToUser.class.getDeclaredField("userOneId"),
                            UserToUser.class.getDeclaredField("userTwoId")
                    )
            );
            actionMap.remove(TestStage.PUT_TEST);
            actionMap.remove(TestStage.GET_TEST_PUT);
            StageSettings<UserToUser> stageSettings = new StageSettings<>(
                    actionMap,
                    new HashMap<String, String>(){{
                        put("userOneId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("userOneId", String.valueOf(testUserToUser.getUserOneId()));
                    }},
                    null,
                    new HashMap<String, String>(){{
                        put("userOneId", String.valueOf(testUserToUser.getUserOneId()));
                    }},
                    testUserToUser,
                    null,
                    UserToUser.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void messageTests(){
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            createContextObject(testUserModified, new HashMap<String, String>(){{
                put("username", testUserModified.getUsername());
            }});
            Message testMessage = new Message(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUserModified.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    "testMessageRetrofitC"
            );
            Message testMessageModified = new Message(
                    testMessage.getSenderId(),
                    testMessage.getReceiverId(),
                    "testMessageRetrofitC-MOD"
            );
            StageSettings<Message> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            Message.class,
                            Arrays.asList(
                                    Message.class.getDeclaredField("senderId"),
                                    Message.class.getDeclaredField("receiverId"),
                                    Message.class.getDeclaredField("content")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("senderId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("senderId", String.valueOf(testMessage.getSenderId()));
                    }},
                    new HashMap<String, String>(){{
                        put("senderId", String.valueOf(testMessageModified.getSenderId()));
                    }},
                    new HashMap<String, String>(){{
                        put("senderId", String.valueOf(testMessageModified.getSenderId()));
                    }},
                    testMessage,
                    new HashMap<String, String>(){{
                        put("senderId", String.valueOf(testMessageModified.getSenderId()));
                        put("receiverId", String.valueOf(testMessageModified.getReceiverId()));
                        put("content", testMessageModified.getContent());
                    }},
                    Message.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void eventToUserTests(){
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            EventToUser testEventToUser = new EventToUser(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    Event.class,
                                    new HashMap<String, String>(){{
                                        put("name", "Admin Event");
                                    }}
                            ).execute().body(), Event.class
                    ).get(0).getId()
            );
            Map<TestStage, StageAction> actionMap = getGenericActionMap(
                    EventToUser.class,
                    Arrays.asList(
                            EventToUser.class.getDeclaredField("userId"),
                            EventToUser.class.getDeclaredField("eventId")
                    )
            );
            actionMap.remove(TestStage.PUT_TEST);
            actionMap.remove(TestStage.GET_TEST_PUT);
            StageSettings<EventToUser> stageSettings = new StageSettings<>(
                    actionMap,
                    new HashMap<String, String>(){{
                        put("userId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testEventToUser.getUserId()));
                    }},
                    null,
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testEventToUser.getUserId()));
                    }},
                    testEventToUser,
                    null,
                    EventToUser.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void commentTests(){
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            Comment testComment = new Comment(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    Post.class,
                                    new HashMap<String, String>(){{
                                        put("postBody", "Admin Post Body");
                                    }}
                            ).execute().body(), Post.class
                    ).get(0).getId(),
                    "testCommentRetrofitC"
            );
            Comment testCommentModified = new Comment(
                    testComment.getUserId(),
                    testComment.getPostId(),
                    "testCommentRetrofitC-MOD"
            );
            StageSettings<Comment> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            Comment.class,
                            Arrays.asList(
                                    Comment.class.getDeclaredField("userId"),
                                    Comment.class.getDeclaredField("postId"),
                                    Comment.class.getDeclaredField("content")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("userId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testComment.getUserId()));
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testCommentModified.getUserId()));
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testCommentModified.getUserId()));
                    }},
                    testComment,
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testCommentModified.getUserId()));
                        put("postId", String.valueOf(testCommentModified.getPostId()));
                        put("content", testCommentModified.getContent());
                    }},
                    Comment.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void gameToUserTests(){
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            createContextObject(testGame, new HashMap<String, String>(){{
                put("gameTitle", testGame.getGameTitle());
            }});
            GameToUser testGameToUser = new GameToUser(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    Game.class,
                                    new HashMap<String, String>(){{
                                        put("gameTitle", testGame.getGameTitle());
                                    }}
                            ).execute().body(), Game.class
                    ).get(0).getId(),
                    true
            );
            GameToUser testGameToUserModified = new GameToUser(
                    testGameToUser.getUserId(),
                    testGameToUser.getGameId(),
                    false
            );
            StageSettings<GameToUser> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            GameToUser.class,
                            Arrays.asList(
                                    GameToUser.class.getDeclaredField("userId"),
                                    GameToUser.class.getDeclaredField("gameId"),
                                    GameToUser.class.getDeclaredField("private_")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("userId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testGameToUser.getUserId()));
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testGameToUserModified.getUserId()));
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testGameToUserModified.getUserId()));
                    }},
                    testGameToUser,
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testGameToUserModified.getUserId()));
                        put("gameId", String.valueOf(testGameToUserModified.getGameId()));
                        put("private", String.valueOf(testGameToUserModified.getPrivate_()));
                    }},
                    GameToUser.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void hostedGameTests(){
        try {
            createContextObject(testGame, new HashMap<String, String>(){{
                put("gameTitle", testGame.getGameTitle());
            }});
            HostedGame testHostedGame = new HostedGame(
                    getObjectList(
                            retrofit.getCall(
                                    Event.class,
                                    new HashMap<String, String>(){{
                                        put("name", "Admin Event");
                                    }}
                            ).execute().body(), Event.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    Game.class,
                                    new HashMap<String, String>(){{
                                        put("gameTitle", testGame.getGameTitle());
                                    }}
                            ).execute().body(), Game.class
                    ).get(0).getId(),
                    1
            );
            HostedGame testHostedGameModified = new HostedGame(
                    testHostedGame.getEventId(),
                    testHostedGame.getGameId(),
                    2
            );
            StageSettings<HostedGame> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            HostedGame.class,
                            Arrays.asList(
                                    HostedGame.class.getDeclaredField("eventId"),
                                    HostedGame.class.getDeclaredField("gameId"),
                                    HostedGame.class.getDeclaredField("seatsAvailable")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("eventId", "66");
                    }},
                    new HashMap<String, String>(){{
                        put("eventId", String.valueOf(testHostedGame.getEventId()));
                    }},
                    new HashMap<String, String>(){{
                        put("eventId", String.valueOf(testHostedGame.getEventId()));
                    }},
                    new HashMap<String, String>(){{
                        put("eventId", String.valueOf(testHostedGameModified.getEventId()));
                    }},
                    testHostedGame,
                    new HashMap<String, String>(){{
                        put("eventId", String.valueOf(testHostedGameModified.getEventId()));
                        put("gameId", String.valueOf(testHostedGameModified.getGameId()));
                        put("seatsAvailable", String.valueOf(testHostedGameModified.getSeatsAvailable()));
                    }},
                    HostedGame.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void reviewTests(){
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            createContextObject(testGame, new HashMap<String, String>(){{
                put("gameTitle", testGame.getGameTitle());
            }});
            Review testReview = new Review(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    Game.class,
                                    new HashMap<String, String>(){{
                                        put("gameTitle", testGame.getGameTitle());
                                    }}
                            ).execute().body(), Game.class
                    ).get(0).getId(),
                    "testReviewRetrofitC"
            );
            Review testReviewModified = new Review(
                    testReview.getUserId(),
                    testReview.getGameId(),
                    "testReviewRetrofitC-MOD"
            );
            StageSettings<Review> stageSettings = new StageSettings<>(
                    getGenericActionMap(
                            Review.class,
                            Arrays.asList(
                                    Review.class.getDeclaredField("userId"),
                                    Review.class.getDeclaredField("gameId"),
                                    Review.class.getDeclaredField("content")
                            )
                    ),
                    new HashMap<String, String>(){{
                        put("userId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testReview.getUserId()));
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testReview.getUserId()));
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testReview.getUserId()));
                    }},
                    testReview,
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testReviewModified.getUserId()));
                        put("gameId", String.valueOf(testReviewModified.getGameId()));
                        put("content", String.valueOf(testReviewModified.getContent()));
                    }},
                    Review.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }

    @Test
    public void hostedGameToUserTests() {
        try {
            createContextObject(testUser, new HashMap<String, String>(){{
                put("username", testUser.getUsername());
            }});
            createContextObject(testGame, new HashMap<String, String>(){{
                put("gameTitle", testGame.getGameTitle());
            }});
            HostedGameToUser testHostedGameToUser = new HostedGameToUser(
                    getObjectList(
                            retrofit.getCall(
                                    User.class,
                                    new HashMap<String, String>(){{
                                        put("username", testUser.getUsername());
                                    }}
                            ).execute().body(), User.class
                    ).get(0).getId(),
                    getObjectList(
                            retrofit.getCall(
                                    Game.class,
                                    new HashMap<String, String>(){{
                                        put("gameTitle", testGame.getGameTitle());
                                    }}
                            ).execute().body(), Game.class
                    ).get(0).getId()
            );
            Map<TestStage, StageAction> actionMap = getGenericActionMap(
                    HostedGameToUser.class,
                    Arrays.asList(
                            HostedGameToUser.class.getDeclaredField("userId"),
                            HostedGameToUser.class.getDeclaredField("gameId")
                    )
            );
            actionMap.remove(TestStage.PUT_TEST);
            actionMap.remove(TestStage.GET_TEST_PUT);
            StageSettings<HostedGameToUser> stageSettings = new StageSettings<>(
                    actionMap,
                    new HashMap<String, String>(){{
                        put("userId", "245");
                    }},
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testHostedGameToUser.getUserId()));
                    }},
                    null,
                    new HashMap<String, String>(){{
                        put("userId", String.valueOf(testHostedGameToUser.getUserId()));
                    }},
                    testHostedGameToUser,
                    null,
                    HostedGameToUser.class,
                    retrofit
            );
            stageSettings.runStageTests();
        } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
            fail("Exception accrued");
        }
    }
}
