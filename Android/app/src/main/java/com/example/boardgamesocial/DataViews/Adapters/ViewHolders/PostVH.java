package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostVH extends DataClsVH<Post> {
    public static final String POST_KEY = "post";
    public static final String USER_KEY = "user";

    private static final Map<Post, Bundle> localCache = new HashMap<>();

    private final TextView textViewUsername;
    private final TextView textViewPostType;
    private final TextView textViewPostBody;
    private final TextView itemNumberLikes;
    private final ImageView likeImg;

    public PostVH(View postView, DataClsAdapter.OnItemListener onItemListener) {
        super(postView, onItemListener);
        textViewUsername = postView.findViewById(R.id.item_poster);
        textViewPostType = postView.findViewById(R.id.item_type);
        textViewPostBody = postView.findViewById(R.id.item_body);
        itemNumberLikes = postView.findViewById(R.id.item_number_likes);
        likeImg = postView.findViewById(R.id.like_img);
    }

    @Override
    public void toggleVisibility(int visibility){
        textViewUsername.setVisibility(visibility);
        textViewPostType.setVisibility(visibility);
        textViewPostBody.setVisibility(visibility);
        itemNumberLikes.setVisibility(visibility);
        likeImg.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, Post post) {
        toggleVisibility(View.INVISIBLE);
        if (localCache.containsKey(post)){
            performBind(activity, post, (User) Objects.requireNonNull(localCache.get(post)).getSerializable(USER_KEY));
        } else {
            retrofitClient.getCall(User.class, new HashMap<String, String>(){{
                put("id", String.valueOf(post.getUserId()));
            }}).subscribe(jsonArray -> {
                List<User> users = getObjectList(jsonArray, User.class);
                if (users.size() > 1) {
                    new Exception("Got many Users on primary key: " + users).printStackTrace();
                } else {
                    performBind(activity, post, users.get(0));
                    contextBundle.putSerializable(USER_KEY, users.get(0));
                    contextBundle.putSerializable(POST_KEY, post);
                    localCache.put(post, contextBundle);
                }
            });
        }
    }

    private void performBind(Activity activity, Post post, User user){
        activity.runOnUiThread(()->{
            toggleVisibility(View.VISIBLE);
            textViewUsername.setText(user.getUsername());
            textViewPostType.setText(post.getPostType());
            textViewPostBody.setText(post.getPostBody());
            itemNumberLikes.setText(String.valueOf(post.getLikes()));
        });
        likeImg.setOnClickListener(view -> {
                    itemNumberLikes.setText(String.valueOf(post.getLikes() + 1));
                    retrofitClient.putCall(Post.class, new HashMap<String, String>() {{
                        put("id", String.valueOf(post.getId()));
                        put("userId", String.valueOf(user.getId()));
                        put("postBody", String.valueOf(post.getPostBody()));
                        put("postType", String.valueOf(post.getPostType()));
                        put("likes", String.valueOf(post.getLikes() + 1));
                    }}).subscribe(jsonObject -> {
                        Post putPost = getObject(jsonObject, Post.class);
                        post.setLikes(putPost.getLikes());
                    });
                }
        );
    }
}
