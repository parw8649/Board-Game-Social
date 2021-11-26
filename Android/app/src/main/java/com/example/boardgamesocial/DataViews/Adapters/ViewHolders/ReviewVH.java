package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Review;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;

public class ReviewVH extends DataClsVH<Review>{
    public static final String REVIEW_KEY = "review";
    // TODO: Add TextViews once xml is created

    public ReviewVH(@NonNull View reviewView, DataClsAdapter.OnItemListener onItemListener) {
        super(reviewView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, Review review) {

    }
}
