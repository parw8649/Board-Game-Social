package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Message;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;

public class MessageVH extends DataClsVH<Message>{

    public static final String MESSAGE_KEY = "message";
    // TODO: Add TextViews once xml is created

    public MessageVH(@NonNull View messageView, DataClsAdapter.OnItemListener onItemListener) {
        super(messageView, onItemListener);
    }

    @Override
    public void toggleVisibility(int visibility) {

    }

    @Override
    public void onBind(Activity activity, Message message) {

    }

}
