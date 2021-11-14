package com.example.boardgamesocial.DataViews.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.PostVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.VHConstructor;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataClsAdapter <DC extends DataClass, VH extends DataClsVH<DC>> extends RecyclerView.Adapter<VH> {
    private List<DC> objectList;
//    private Constructor<VH> vhConstructor;
    private Class<DC> cls;

    public static final Map<Class<?>, VHConstructor> VH_MAP = new HashMap<Class<?>, VHConstructor>(){{
        put(Post.class, PostVH::new);
    }};

    public DataClsAdapter(List<DC> objectList, Class<DC> cls) {
        this.objectList = objectList;
        this.cls = cls;
    }

    public void setCls(Class<DC> cls) {
        this.cls = cls;
    }

    public void setPosts(List<DC> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent,false);
        Log.w("View", String.valueOf(itemView));
        return (VH) Objects.requireNonNull(VH_MAP.get(cls)).create(itemView);
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
