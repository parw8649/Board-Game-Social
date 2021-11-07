package com.example.boardgamesocial.API;

import com.example.boardgamesocial.DataClasses.Comment;
import com.example.boardgamesocial.DataClasses.DataClass;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface API {
    String BASE_URL_LOCAL = "http://10.0.2.2:8000/";
    String BASE_URL_TESTS = "http://0.0.0.0:8000/";

    HashMap<Class<?>, String> urlMap = new HashMap<Class<?>, String>() {{
        put(User.class, "api/user/");
        put(Event.class, "api/event/");
        put(Post.class, "api/post/");
        put(Game.class, "api/game/");
        put(UserToUser.class, "api/user_to_user/");
        put(Message.class, "api/message/");
        put(EventToUser.class, "api/event_to_user/");
        put(Comment.class, "api/comment/");
        put(GameToUser.class, "api/game_to_user/");
        put(HostedGame.class, "api/hosted_game/");
        put(Review.class, "api/review/");
        put(HostedGameToUser.class, "api/hosted_game_to_user/");
    }};

    @POST("api/sign_up/")
    Call<User> signUpCall(
            @Body User user
    );

    @POST("api/login/")
    Call<Token> loginCall(
            @QueryMap User user
    );

    @GET("api/logout/")
    Call<JsonObject> logoutCall(
            @QueryMap Map<String, String> userIdMap
    );

    @GET
    Call<JsonArray> getCall(
            @Url String url,
            @QueryMap Map<String, String> filters
    );

    @POST
    Call<JsonObject> postCall(
            @Url String url,
            @Body Object object
    );

    @PUT
    Call<JsonObject> putCall(
            @Url String url,
            @Body Map<String, String> filters
    );

    @DELETE
    Call<JsonArray> deleteCall(
            @Url String url,
            @QueryMap Map<String, String> filters
    );
}
