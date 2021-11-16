package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.view.View;

import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;

public interface VHConstructor {
    DataClsVH<?> create(View view, DataClsAdapter.OnItemListener onItemListener);
}
