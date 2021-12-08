package com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserToUserVH extends DataClsVH<UserToUser> {
    public static final String USER_TO_USER_KEY = "user_to_user";
    public static final String USER_KEY = "user";

    private static final Map<UserToUser, Bundle> localCache = new HashMap<>();

    private final TextView textViewUsername;
    private final ImageView imageViewUserIcon;

    public UserToUserVH(@NonNull View userToUserView, DataClsAdapter.OnItemListener onItemListener) {
        super(userToUserView, onItemListener);
        textViewUsername = userToUserView.findViewById(R.id.item_username);
        imageViewUserIcon = userToUserView.findViewById(R.id.item_user_icon);
    }

    @Override
    public void toggleVisibility(int visibility) {
        textViewUsername.setVisibility(visibility);
        imageViewUserIcon.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, UserToUser userToUser) {
        toggleVisibility(View.INVISIBLE);
        if (localCache.containsKey(userToUser)){
            performBind(activity, (User) Objects.requireNonNull(localCache.get(userToUser)).getSerializable(USER_KEY));
        } else {
            retrofitClient.getCall(User.class, new HashMap<String, String>(){{
                put("id", String.valueOf(userToUser.getUserTwoId()));
            }}).subscribe(jsonArray -> {
                List<User> users = getObjectList(jsonArray, User.class);
                if (users.size() > 1) {
                    new Exception("Got many Users on primary key: " + users).printStackTrace();
                } else {
                    performBind(activity, users.get(0));
                    contextBundle.putSerializable(USER_KEY, users.get(0));
                    localCache.put(userToUser, contextBundle);
                }
            });
        }
    }

    private void performBind(Activity activity, User user){
        activity.runOnUiThread(()->{
            toggleVisibility(View.VISIBLE);
            textViewUsername.setText(user.getUsername());
        });
    }
}
