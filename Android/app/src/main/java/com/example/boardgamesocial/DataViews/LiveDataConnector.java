package com.example.boardgamesocial.DataViews;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.boardgamesocial.API.RetrofitClient;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.Map;

public class LiveDataConnector {

    private static LiveDataConnector liveDataConnector;
    private final RetrofitClient retrofitClient;

    private LiveDataConnector(){
        retrofitClient = RetrofitClient.getClient();
    }

    public static LiveDataConnector getInstance(){
        if (liveDataConnector == null){
            liveDataConnector = new LiveDataConnector();
        }
        return liveDataConnector;
    }

    public MutableLiveData<List<?>> getMutableLiveData(Class<?> dataClass, Map<String, String> filter) {
        MutableLiveData<List<?>> mutableLiveData = new MutableLiveData<>();
        retrofitClient.getCall(dataClass, filter).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<?> objectList = getObjectList(response.body(), dataClass);
                    mutableLiveData.setValue(objectList);
                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return mutableLiveData;
    }
}
