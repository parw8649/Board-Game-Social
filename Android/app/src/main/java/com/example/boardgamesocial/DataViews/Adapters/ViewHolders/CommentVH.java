package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.boardgamesocial.DataClasses.Comment;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommentVH extends DataClsVH<Comment> {
    public static final String COMMENT_KEY = "comment";
    public static final String USER_KEY = "user";
    private static final Map<Comment, Bundle> localCache = new HashMap<>();

    private final TextView textViewUsername;
    private final TextView textViewCommentBody;

    public CommentVH(@NonNull View commentView, DataClsAdapter.OnItemListener onItemListener) {
        super(commentView, onItemListener);

        textViewUsername = commentView.findViewById(R.id.comment_item_poster);
        textViewCommentBody = commentView.findViewById(R.id.comment_item_body);
    }

    @Override
    public void toggleVisibility(int visibility) {
        textViewUsername.setVisibility(visibility);
        textViewCommentBody.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, Comment comment) {
        contextBundle.putSerializable(COMMENT_KEY, comment);
        toggleVisibility(View.INVISIBLE);

        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("id", comment.getUserId().toString());
        }}).subscribe(jsonArray -> {
            List<User> users = getObjectList(jsonArray,User.class);
            if (users.size() > 1) {
                new Exception(String.format("Got many Users on primary key [%d] %s:", comment.getUserId().toString(), users));
            } else {
                performBind(activity, comment, users.get(0));
                contextBundle.putSerializable(USER_KEY, users.get(0));
                contextBundle.putSerializable(COMMENT_KEY, comment);
                localCache.put(comment, contextBundle);
            }
        });
    }

    private void performBind(Activity activity, Comment comment, User user) {
        activity.runOnUiThread(() -> {
            toggleVisibility(View.VISIBLE);
            textViewUsername.setText(user.getUsername());
            textViewCommentBody.setText(comment.getContent());
        });
    }
}
