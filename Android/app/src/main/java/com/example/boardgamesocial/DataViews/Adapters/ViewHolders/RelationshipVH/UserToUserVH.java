package com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;

public class UserToUserVH extends DataClsVH<UserToUser> {

    public static final String USER_TO_USER_KEY = "user_to_user";
    // TODO: Add TextViews once xml is created
    public UserToUserVH(@NonNull View userToUserView, DataClsAdapter.OnItemListener onItemListener) {
        super(userToUserView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, UserToUser userToUser) {

    }
}
