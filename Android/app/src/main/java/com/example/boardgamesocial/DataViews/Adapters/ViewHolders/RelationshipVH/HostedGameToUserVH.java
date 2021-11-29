package com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Relationships.HostedGameToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;

public class HostedGameToUserVH extends DataClsVH<HostedGameToUser> {
    public static final String HOSTED_GAME_TO_USER_KEY = "hosted_game_to_user";
    // TODO: Add TextViews once xml is created
    public HostedGameToUserVH(@NonNull View hostedGameToUserView, DataClsAdapter.OnItemListener onItemListener) {
        super(hostedGameToUserView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, HostedGameToUser hostedGameToUser) {

    }
}
