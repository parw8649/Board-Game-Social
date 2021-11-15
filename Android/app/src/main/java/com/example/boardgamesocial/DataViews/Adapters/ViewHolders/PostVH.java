package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.R;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostVH extends DataClsVH<Post> {
    private final TextView textViewUsername;
    private final TextView textViewPostType;
    private final TextView textViewPostBody;
    private final TextView itemNumberLikes;
    private final ImageView likeImg;

    public PostVH(View postView) {
        super(postView);
        textViewUsername = postView.findViewById(R.id.item_poster);
        textViewPostType = postView.findViewById(R.id.item_type);
        textViewPostBody = postView.findViewById(R.id.item_body);
        itemNumberLikes = postView.findViewById(R.id.item_number_likes);
        likeImg = postView.findViewById(R.id.like_img);
        likeImg.setOnClickListener(view -> {

        });
    }

    @Override
    public void toggleVisibility(int visibility){
        textViewUsername.setVisibility(visibility);
        textViewPostType.setVisibility(visibility);
        textViewPostBody.setVisibility(visibility);
        itemNumberLikes.setVisibility(visibility);
    }

    @Override
    public void onBind(Post post) {
        toggleVisibility(View.INVISIBLE);
        RetrofitClient retrofitClient = RetrofitClient.getClient();
        retrofitClient.getCall(User.class, new HashMap<String, String>(){{
            put("id", String.valueOf(post.getUserId()));
        }}).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<User> users = getObjectList(response.body(), User.class);
                    if (users.size() > 1) {
                        new Exception("Got many Users on primary key: " + response.body()).printStackTrace();
                    } else {
                        toggleVisibility(View.VISIBLE);
                        User user = users.get(0);
                        textViewUsername.setText(user.getUsername());
                        textViewPostType.setText(post.getPostType());
                        textViewPostBody.setText(post.getPostBody());
                        itemNumberLikes.setText(post.getLikes());
                    }
                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
