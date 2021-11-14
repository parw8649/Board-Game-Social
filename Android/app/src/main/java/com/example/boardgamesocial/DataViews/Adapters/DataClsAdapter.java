package com.example.boardgamesocial.DataViews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.example.boardgamesocial.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

public class DataClsAdapter <DC extends DataClass, VH extends DataClsVH<DC>> extends RecyclerView.Adapter<VH> {
    private List<DC> objectList;
    private Class<VH> vhConstructor;

    public DataClsAdapter(List<DC> objectList, Class<VH> cls) {
        this.objectList = objectList;
        this.vhConstructor = cls;
    }

    public void setVhConstructor(Class<VH> cls) {
        this.vhConstructor = cls;
    }

    public void setPosts(List<DC> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        try {
            return vhConstructor.getConstructor().newInstance(itemView);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not create instance of ViewHolder in DataClsAdapter");
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        DC currentObject = objectList.get(position);
        holder.onBind(currentObject);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }
}
