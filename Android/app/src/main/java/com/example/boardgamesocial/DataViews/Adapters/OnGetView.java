package com.example.boardgamesocial.DataViews.Adapters;

import android.view.View;

import com.example.boardgamesocial.DataClasses.DataClass;

public interface OnGetView <T extends DataClass> {
     void render (View view, T object);
}
