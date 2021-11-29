package com.example.boardgamesocial.API;

import static com.example.boardgamesocial.API.API.URL_MAP;

import android.util.Log;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Token;
import com.example.boardgamesocial.DataClasses.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String TAG = "RetrofitClient";

    public static APIMode apiMode = APIMode.PROD;
    private final API api;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(){
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new ValidatorInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(API.BASE_URL.get(apiMode)))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    public static <DC extends DataClass> List<DC> getObjectList(JsonArray jsonArray, Class<DC> cls) {
        List<DC> list = new ArrayList<>();
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

    public static <DC extends DataClass> DC getObject(JsonObject jsonObject, Class<DC> cls) {
        return new Gson().fromJson(jsonObject, cls);
    }

    public void setAuthToken(String token) {
        HeaderInterceptor.token.setToken(token);
    }

    public Observable<User> signUpCall(User user){
        Observable<User> observable = api.signUpCall(user);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }

    public Observable<Token> loginCall(User user){
        Observable<Token> observable = api.loginCall(user);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }

    public Observable<JsonObject> logoutCall(Map<String, String> userIdMap){
        Observable<JsonObject> observable = api.logoutCall(userIdMap);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }

    public Observable<JsonArray> getCall(Class<? extends DataClass> cls, Map<String, String> filters){
        Observable<JsonArray> observable = api.getCall(URL_MAP.get(cls), filters);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }

    public Observable<JsonObject> postCall(Class<? extends DataClass> cls, Object object){
        Observable<JsonObject> observable = api.postCall(URL_MAP.get(cls), object);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }

    public Observable<JsonObject> putCall(Class<? extends DataClass> cls, Map<String, String> filters){
        Observable<JsonObject> observable = api.putCall(URL_MAP.get(cls), filters);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }

    public Observable<JsonArray> deleteCall(Class<? extends DataClass> cls, Map<String, String> filters){
        Observable<JsonArray> observable = api.deleteCall(URL_MAP.get(cls), filters);
        return (apiMode == APIMode.TEST) ? observable : observable.subscribeOn(Schedulers.io());
    }


}
