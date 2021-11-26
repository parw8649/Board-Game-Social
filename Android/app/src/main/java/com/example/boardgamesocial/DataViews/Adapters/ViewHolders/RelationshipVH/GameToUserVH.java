package com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Relationships.GameToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;

public class GameToUserVH extends DataClsVH<GameToUser> {

    public static final String GAME_TO_USER_KEY = "game_to_user";
    // TODO: Add TextViews once xml is created
    public GameToUserVH(@NonNull View gameToUserView, DataClsAdapter.OnItemListener onItemListener) {
        super(gameToUserView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, GameToUser gameToUser) {

    }
}
