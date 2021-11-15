package com.example.boardgamesocial.DataViews;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

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
            final LiveData<JsonArray> source = LiveDataReactiveStreams.fromPublisher(retrofitClient.getCall(dataClass, filter));
            mediatorLiveData.addSource(source, jsonArray -> {
                mediatorLiveData.setValue(getObjectList(jsonArray, dataClass));
                mediatorLiveData.removeSource(source);
            });
            mutableLiveDataMap.put(dataClass, mediatorLiveData);
        }
        return (MediatorLiveData<List<DC>>) mutableLiveDataMap.get(dataClass);
    }
}
