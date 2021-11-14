package com.example.boardgamesocial.RecycleAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent,false);

        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post currentPost = posts.get(position);
        // TODO: get username via API
        String username = "username placeholder";

        holder.textViewUsername.setText(username);
        holder.textViewPostType.setText(currentPost.getPostType());
        holder.textViewPostBody.setText(currentPost.getPostBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        private TextView textViewUsername;
        private TextView textViewPostType;
        private TextView textViewPostBody;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.item_poster);
            textViewPostType = itemView.findViewById(R.id.item_type);
            textViewPostBody = itemView.findViewById(R.id.item_body);
        }
    }
}
