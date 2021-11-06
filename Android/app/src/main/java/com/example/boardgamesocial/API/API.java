package com.example.boardgamesocial.API;

import com.example.boardgamesocial.DataClasses.Game;

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
    String BASE_URL = "http://10.0.2.2:8000/";
    HashMap<Class<?>, String> urlMap =new HashMap<Class<?>, String>() {{
        put(Game.class, "/api/game/");
    }};

    @GET
    Call<List<Object>> getCall(
            @Url String url,
            @QueryMap Map<String, String> filters
    );

    @POST
    Call<Object> postCall(
            @Url String url,
            @Body Object filters
    );

    @PUT
    Call<Object> putCall(
            @Url String url,
            @Body Object filters
    );

    @DELETE
    Call<Object> deleteCall(
            @Url String url,
            @Body Object filters
    );
}
