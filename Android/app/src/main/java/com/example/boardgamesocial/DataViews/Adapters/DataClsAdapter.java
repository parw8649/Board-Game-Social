package com.example.boardgamesocial.DataViews.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.boardgamesocial.DataClasses.DataClass;

import java.util.List;

public class DataClsAdapter <T extends DataClass> extends BaseAdapter {

    private final int layout;
    private final Context context;
    private final List<T> objectList;
    private final OnGetView<T> renderAction;

    public DataClsAdapter(int layout, Context context, List<T> objectList, OnGetView<T> renderAction) {
        this.layout = layout;
        this.context = context;
        this.objectList = objectList;
        this.renderAction = renderAction;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public T getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(context);
            view = vi.inflate(layout, null);
        }
        T object = getItem(position);
        if (object != null) {
            renderAction.render(view, object);
        }
        return view;
    }
}
