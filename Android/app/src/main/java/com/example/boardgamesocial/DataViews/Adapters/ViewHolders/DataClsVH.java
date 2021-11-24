package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.MainApp.Fragments.ProfileFragment;
import com.example.boardgamesocial.R;

public abstract class DataClsVH <DC extends DataClass> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final DataClsAdapter.OnItemListener onItemListener;
    protected final Bundle contextBundle;

    public DataClsVH(View itemView, DataClsAdapter.OnItemListener onItemListener) {
        super(itemView);
        this.onItemListener = onItemListener;
        this.contextBundle = new Bundle();
        itemView.setOnClickListener(this);
    }

    public abstract void toggleVisibility(int visibility);
    public abstract void onBind(Activity activity, DC dataClass);

    public Bundle getContextBundle() {
        return contextBundle;
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(contextBundle);
    }
}
