package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Message;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatVH extends DataClsVH<User> {
    public static final String USER_KEY = "user_to_user";
    public static final String MSG_KEY = "last_msg";

    private static final Map<User, Bundle> localCache = new HashMap<>();

    private final TextView friendsNameField;
    private final TextView lastMessageField;

    public ChatVH(@NonNull View chatView, DataClsAdapter.OnItemListener onItemListener) {
        super(chatView, onItemListener);
        friendsNameField = chatView.findViewById(R.id.item_friends_name);
        lastMessageField = chatView.findViewById(R.id.item_last_message);
    }

    @Override
    public void toggleVisibility(int visibility) {
        friendsNameField.setVisibility(visibility);
        lastMessageField.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, User user) {
        toggleVisibility(View.INVISIBLE);
        if (localCache.containsKey(user)){
            performBind(activity, user, (Message) Objects.requireNonNull(localCache.get(user)).getSerializable(MSG_KEY));
        } else {
            retrofitClient.getCall(Message.class, new HashMap<String, String>(){{
                put("senderId", String.valueOf(user.getId()));
            }}).subscribe(jsonArray -> {
                List<Message> messages = getObjectList(jsonArray, Message.class);
                if (messages.size() > 1) {
                    Log.i(TAG,"onBind: No messages");
                } else {
                    performBind(activity, user, messages.get(0));
                    contextBundle.putSerializable(USER_KEY, user);
                    contextBundle.putSerializable(MSG_KEY, messages.get(0));
                    localCache.put(user, contextBundle);
                }
            });
        }
    }

    private void performBind(Activity activity, User user, Message message){
        activity.runOnUiThread(()->{
            toggleVisibility(View.VISIBLE);
            friendsNameField.setText(user.getUsername());
            lastMessageField.setText(message.getContent());
        });
    }
}
