package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Comment;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;

public class CommentVH extends DataClsVH<Comment> {
    public static final String COMMENT_KEY = "comment";
    // TODO: Add TextViews once xml is created
    public CommentVH(@NonNull View commentView, DataClsAdapter.OnItemListener onItemListener) {
        super(commentView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, Comment comment) {

    }
}
