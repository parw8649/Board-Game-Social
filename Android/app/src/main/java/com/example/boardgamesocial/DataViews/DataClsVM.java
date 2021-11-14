package com.example.boardgamesocial.DataViews;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataClsVM<DC extends DataClass> extends ViewModel {

    private final LiveDataConnector liveDataConnector;
    private final Class<DC> dataClass;
    private MutableLiveData<List<DC>> mutableLiveData;

    public DataClsVM(Class<DC> dataClass, Map<String, String> filter){
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
