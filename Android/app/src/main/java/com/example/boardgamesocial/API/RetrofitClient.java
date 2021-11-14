package com.example.boardgamesocial.API;

import static com.example.boardgamesocial.API.API.URL_MAP;

import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static UrlToggle urlToggle = UrlToggle.DEV;
    private final API api;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(API.BASE_URL.get(urlToggle)))
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
        return api.getCall(URL_MAP.get(cls), filters);
    }

    public Call<JsonObject> postCall(Class<?> cls, Object object){
        return api.postCall(URL_MAP.get(cls), object);
    }

    public Call<JsonObject> putCall(Class<?> cls, Map<String, String> filters){
        return api.putCall(URL_MAP.get(cls), filters);
    }

    public Call<JsonArray> deleteCall(Class<?> cls, Map<String, String> filters){
        return api.deleteCall(URL_MAP.get(cls), filters);
    }
}
