package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Post;

public abstract class DataClsVH <DC extends DataClass> extends RecyclerView.ViewHolder {
    public DataClsVH(View itemView) {
        super(itemView);
    }

    public abstract void toggleVisibility(int visibility);
    public abstract void onBind(DC dataClass);
}
