package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.R;

public class PostVH extends DataClsVH<Post> {
    private final TextView textViewUsername;
    private final TextView textViewPostType;
    private final TextView textViewPostBody;

    public PostVH(View itemView) {
        super(itemView);
        textViewUsername = itemView.findViewById(R.id.item_poster);
        textViewPostType = itemView.findViewById(R.id.item_type);
        textViewPostBody = itemView.findViewById(R.id.item_body);
    }

    @Override
    public void onBind(Post post) {
        textViewUsername.setText("TestUser");
        textViewPostType.setText(post.getPostType());
        textViewPostBody.setText(post.getPostBody());
    }
}
