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
import java.util.concurrent.TimeUnit;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class DataClsVM extends ViewModel {
    private static DataClsVM liveDataConnector;
    private final Map<Class<?>, MediatorLiveData<?>> mediatorLiveDataMap;
    private static long updateInterval = 10;

    private DataClsVM(){
        mediatorLiveDataMap = new HashMap<>();
    }

    public static DataClsVM getInstance(){
        if (liveDataConnector == null){
            liveDataConnector = new DataClsVM();
        }
        return liveDataConnector;
    }

    public <DC extends DataClass> MediatorLiveData<List<DC>> getMediatorLiveData(Observable<JsonArray> observable, Class<DC> dataClass, boolean reset) {
        if (!mediatorLiveDataMap.containsKey(dataClass) || reset){
            MediatorLiveData<List<DC>> mediatorLiveData = new MediatorLiveData<>();
            final LiveData<JsonArray> source = LiveDataReactiveStreams.fromPublisher(
                    Observable.interval(0, updateInterval, TimeUnit.SECONDS)
                            .flatMap((Function<Long, ObservableSource<JsonArray>>) aLong -> observable)
                            .toFlowable(BackpressureStrategy.BUFFER)
            );
            mediatorLiveData.addSource(source, jsonArray -> {
                List<DC> objectList = getObjectList(jsonArray, dataClass);
                Collections.reverse(objectList);
                mediatorLiveData.setValue(objectList);
            });
            mediatorLiveDataMap.put(dataClass, mediatorLiveData);
        }
        return (MediatorLiveData<List<DC>>) mediatorLiveDataMap.get(dataClass);
    }
}
