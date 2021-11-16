package com.example.boardgamesocial.DataViews;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.DataClass;
import com.google.gson.JsonArray;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.BackpressureStrategy;

public class DataClsVM extends ViewModel {
    private static DataClsVM liveDataConnector;
    private final RetrofitClient retrofitClient;
    private final Map<Class<?>, MediatorLiveData<?>> mutableLiveDataMap;

    private DataClsVM(){
        retrofitClient = RetrofitClient.getClient();
        mutableLiveDataMap = new HashMap<>();
    }

    public static DataClsVM getInstance(){
        if (liveDataConnector == null){
            liveDataConnector = new DataClsVM();
        }
        return liveDataConnector;
    }

    public <DC extends DataClass> MediatorLiveData<List<DC>> getMutableLiveData(Class<DC> dataClass, Map<String, String> filter) {
        if (!mutableLiveDataMap.containsKey(dataClass)){
            MediatorLiveData<List<DC>> mediatorLiveData = new MediatorLiveData<>();
            final LiveData<JsonArray> source = LiveDataReactiveStreams.fromPublisher(
                    retrofitClient.getCall(dataClass, filter).toFlowable(BackpressureStrategy.BUFFER)
            );
            mediatorLiveData.addSource(source, jsonArray -> {
                List<DC> dcList = getObjectList(jsonArray, dataClass);
                Collections.reverse(dcList);
                mediatorLiveData.setValue(dcList);
                mediatorLiveData.removeSource(source);
            });
            mutableLiveDataMap.put(dataClass, mediatorLiveData);
        }
        return (MediatorLiveData<List<DC>>) mutableLiveDataMap.get(dataClass);
    }
}
