package com.example.boardgamesocial.API;

import static com.example.boardgamesocial.API.API.BASE_URL;
import static com.example.boardgamesocial.API.API.urlMap;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .baseUrl(BASE_URL)
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

    public Call<List<Object>> getCall(Class<?> cls, Map<String, String> filters){
        return api.getCall(urlMap.get(cls), filters);
    }

    public Call<Object> postCall(Class<?> cls, Object filters){
        return api.postCall(urlMap.get(cls), filters);
    }

    public Call<Object> putCall(Class<?> cls, Object filters){
        return api.putCall(urlMap.get(cls), filters);
    }

    public Call<Object> deleteCall(Class<?> cls, Object filters){
        return api.deleteCall(urlMap.get(cls), filters);
    }
}
