package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

public class UserVH extends DataClsVH<User> {

    public static final String USER_KEY = "user";
    private final TextView textViewUsername;
    private final ImageView imageViewUserIcon;

    public UserVH(@NonNull View userView, DataClsAdapter.OnItemListener onItemListener) {
        super(userView, onItemListener);
        textViewUsername = userView.findViewById(R.id.item_username);
        imageViewUserIcon = userView.findViewById(R.id.item_user_icon);
    }

    @Override
    public void toggleVisibility(int visibility) {
        textViewUsername.setVisibility(visibility);
        imageViewUserIcon.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, User user) {
        contextBundle.putSerializable(USER_KEY, user);
        activity.runOnUiThread(()->{
            toggleVisibility(View.VISIBLE);
            
            textViewUsername.setText(user.getUsername());
        });
    }
}
