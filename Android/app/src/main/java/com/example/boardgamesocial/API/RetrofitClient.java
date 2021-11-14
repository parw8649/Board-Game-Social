package com.example.boardgamesocial.API;

import static com.example.boardgamesocial.API.API.BASE_URL_LOCAL;
import static com.example.boardgamesocial.API.API.BASE_URL_TESTS;
import static com.example.boardgamesocial.API.API.urlMap;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public class RetrofitClient {
    private final API api;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_LOCAL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        api = retrofit.create(API.class);
    }

    public static RetrofitClient getClient() {
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public static <T> List<T> getObjectList(JsonArray jsonArray, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            for (JsonElement jsonElement : jsonArray) {
                System.out.println(jsonElement);
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> T getObject(JsonObject jsonObject, Class<T> cls) {
        return new Gson().fromJson(jsonObject, cls);
    }

    public void setAuthToken(Token token) {
        HeaderInterceptor.token.setToken(token.getToken());
    }

    public Call<User> signUpCall(User user){
        return api.signUpCall(user);
    }

    public Call<Token> loginCall(User user){
        return api.loginCall(user);
    }

    public Call<JsonObject> logoutCall(Map<String, String> userIdMap){
        return api.logoutCall(userIdMap);
    }

    public Call<JsonArray> getCall(Class<?> cls, Map<String, String> filters){
        return api.getCall(urlMap.get(cls), filters);
    }

    public Call<JsonObject> postCall(Class<?> cls, Object object){
        return api.postCall(urlMap.get(cls), object);
    }

    public Call<JsonObject> putCall(Class<?> cls, Map<String, String> filters){
        return api.putCall(urlMap.get(cls), filters);
    }

    public Call<JsonArray> deleteCall(Class<?> cls, Map<String, String> filters){
        return api.deleteCall(urlMap.get(cls), filters);
    }
}
