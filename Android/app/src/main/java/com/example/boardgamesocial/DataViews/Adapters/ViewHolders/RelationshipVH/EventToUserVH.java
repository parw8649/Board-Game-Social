package com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Relationships.EventToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;

public class EventToUserVH extends DataClsVH<EventToUser> {

    public static final String EVENT_TO_USER_KEY = "event_to_user";
    // TODO: Add TextViews once xml is created
    public EventToUserVH(@NonNull View eventToUserView, DataClsAdapter.OnItemListener onItemListener) {
        super(eventToUserView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, EventToUser eventToUser) {

    }
}
