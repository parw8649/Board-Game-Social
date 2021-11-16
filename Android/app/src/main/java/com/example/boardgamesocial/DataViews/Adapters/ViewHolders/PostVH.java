package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
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

public class PostVH extends DataClsVH<Post> implements View.OnClickListener {
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
    }

    @Override
    public void onBind(Activity activity, Post post) {
        toggleVisibility(View.INVISIBLE);
        RetrofitClient retrofitClient = RetrofitClient.getClient();
        retrofitClient.getCall(User.class, new HashMap<String, String>(){{
            put("id", String.valueOf(post.getUserId()));
        }}).subscribe(jsonArray -> {
            List<User> users = getObjectList(jsonArray, User.class);
            if (users.size() > 1) {
                new Exception("Got many Users on primary key: " + users).printStackTrace();
            } else {
                User user = users.get(0);
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
        });
    }
}
