package com.example.boardgamesocial.DataViews.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.EventVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.PostVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH.UserToUserVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.VHConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataClsAdapter <DC extends DataClass, VH extends DataClsVH<DC>> extends RecyclerView.Adapter<VH> {
    private final Activity activity;
    private final OnItemListener onItemListener;
    private final int itemLayout;
    private List<DC> objectList;
    private Class<DC> cls;

    public static final Map<Class<?>, VHConstructor> VH_MAP = new HashMap<Class<?>, VHConstructor>(){{
        put(Post.class, PostVH::new);
        put(Game.class, GameVH::new);
        put(Event.class, EventVH::new);
        put(UserToUser.class, UserToUserVH::new);
    }};

    public DataClsAdapter(OnItemListener onItemListener, Class<DC> cls, Activity activity, int itemLayout) {
        this.activity = activity;
        this.objectList = new ArrayList<>();
        this.cls = cls;
        this.onItemListener = onItemListener;
        this.itemLayout = itemLayout;
    }

    public void setCls(Class<DC> cls) {
        this.cls = cls;
    }

    public void setPosts(List<DC> objectList) {
        this.objectList = objectList;
    }

    public List<DC> getObjectList() {
        return objectList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return (VH) Objects.requireNonNull(VH_MAP.get(cls)).create(itemView, onItemListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        DC currentObject = objectList.get(position);
        holder.onBind(activity, currentObject);
    }

    public void addNewObjects(List<DC> newObjects) {
        for (DC object : newObjects) {
            if (!objectList.contains(object)){
                objectList.add(object);
                notifyItemInserted(objectList.indexOf(object));
            }

            objectList.removeIf(obj -> {
                boolean exists = newObjects.contains(obj);
                if(!exists){
                    notifyItemRemoved(objectList.indexOf(obj));
                }
                return !exists;
            });
        }
    }

//    public void filterSearch(String searchQuery, String filterBy){
//
//
//    }



    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public interface OnItemListener {
        void onItemClick(Bundle contextBundle);
    }
}
