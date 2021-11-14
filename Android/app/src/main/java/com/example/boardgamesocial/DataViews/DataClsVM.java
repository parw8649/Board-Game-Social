package com.example.boardgamesocial.DataViews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boardgamesocial.DataClasses.DataClass;

import java.util.HashMap;
import java.util.Map;

public class DataClsVM extends ViewModel {

    private final LiveDataConnector liveDataConnector;
    private final Class<?> dataClass;
    private MutableLiveData<?> mutableLiveData;

    public DataClsVM(Class<?> dataClass, Map<String, String> filter){
        this.dataClass = dataClass;
        this.liveDataConnector = LiveDataConnector.getInstance();
        this.mutableLiveData = liveDataConnector.getMutableLiveData(dataClass, filter);
    }

    public void updateLiveData(Map<String, String> filter){
        this.mutableLiveData = liveDataConnector.getMutableLiveData(dataClass, filter);
    }

    public LiveData<?> getLiveData() {
        return mutableLiveData;
    }
}
