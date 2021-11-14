package com.example.boardgamesocial;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.DataClasses.DataClass;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.OnGetView;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.databinding.FragmentHomePostBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePostFragment extends Fragment {

    private FragmentHomePostBinding binding;

    private ListView recyclerView;
    private DataClsAdapter<Post> dataClsAdapter;
    private DataClsVM dataClsVM;
    private List<Post> posts;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.postFeed_recyclerView);
        posts = new ArrayList<>();
        dataClsVM = new DataClsVM(Post.class, new HashMap<>());
        dataClsVM.getLiveData().observe(getViewLifecycleOwner(), res -> {
            posts.addAll(getObjectList((JsonArray) res, Post.class));
            dataClsAdapter.notifyDataSetChanged();
        });

        if (dataClsAdapter == null) {
            dataClsAdapter = new DataClsAdapter<>(R.layout.item, getContext(), posts, (postView, object) -> {
                TextView postUser = postView.findViewById(R.id.item_poster);
                TextView postType = postView.findViewById(R.id.item_type);
                TextView postBody = postView.findViewById(R.id.item_body);
                if (postUser != null) {
                    postUser.setText(object.getUserId());
                }
                if (postType != null) {
                    postType.setText(object.getPostType());
                }
                if (postBody != null) {
                    postBody.setText(object.getPostBody());
                }
            });
            recyclerView.setAdapter(dataClsAdapter);
        } else {
            dataClsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}