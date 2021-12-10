package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Comment;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.CommentVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.PostVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SinglePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SinglePostFragment extends Fragment implements DataClsAdapter.OnItemListener {

    public static final String TAG = "SinglePostFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RetrofitClient retrofitClient;
    private Post post;
    private DataClsAdapter<Comment, CommentVH> dataClsAdapter;
    private TextView textViewUsername;
    private TextView textViewPostType;
    private TextView textViewBody;
    private TextView textViewLikes;
    private ImageView imageViewUserIcon;
    private EditText editTextComment;
    private ImageButton imageButtonSend;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SinglePostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SinglePostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SinglePostFragment newInstance(String param1, String param2) {
        SinglePostFragment fragment = new SinglePostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_post, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert this.getArguments() != null;
        Log.i(TAG, "onViewCreated: Post: " + this.getArguments().getSerializable(PostVH.POST_KEY).toString());
        Toast.makeText(getContext(), this.getArguments().getSerializable(PostVH.POST_KEY).toString(), Toast.LENGTH_SHORT).show();
        setAppBarFab(View.INVISIBLE);
        retrofitClient = RetrofitClient.getClient();

        post = (Post) getArguments().getSerializable(PostVH.POST_KEY);
        textViewUsername = view.findViewById(R.id.single_post_poster);
        textViewPostType = view.findViewById(R.id.single_post_type);
        textViewBody = view.findViewById(R.id.single_post_body);
        textViewLikes = view.findViewById(R.id.single_post_number_likes);
        imageViewUserIcon = view.findViewById(R.id.single_post_user_icon);
        editTextComment = view.findViewById(R.id.single_post_editText_comment);
        imageButtonSend = view.findViewById(R.id.single_post_btn_add_comment);
        RecyclerView recyclerView = view.findViewById(R.id.comments_recyclerView);

        imageButtonSend.setOnClickListener(v -> sendComment(v));

        setUsername(post.getUserId());
        textViewPostType.setText(post.getPostType());
        textViewBody.setText(post.getPostBody());
        textViewLikes.setText(String.valueOf(post.getLikes()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dataClsAdapter = new DataClsAdapter<>(
                this,
                Comment.class,
                getActivity(),
                R.layout.comment_item);
        recyclerView.setAdapter(dataClsAdapter);

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(retrofitClient.getCall(Comment.class, new HashMap<String, String>(){{
//                                            put("postId", post.getId().toString());
//                                            put("userId", post.getUserId().toString());
                                        }}), Comment.class, true)
                                        .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);
    }

    private void setUsername(int userId) {
        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("id", String.valueOf(userId));
        }}).subscribe(jsonArray -> {
            getActivity().runOnUiThread(() -> {
                List<User> users = getObjectList(jsonArray, User.class);
                textViewUsername.setText(users.get(0).getUsername().toString());
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(Bundle bundle) {
        Toast.makeText(getContext(), "comment tapped",Toast.LENGTH_SHORT).show();
    }

    private void sendComment(View view) {
        if (editTextComment.getText().toString().isEmpty()) {
            return;
        }

        retrofitClient.postCall(Comment.class, new Comment(Utils.getUserId(), post.getId(), editTextComment.getText().toString()))
                .subscribe(jsonObject -> {
                    requireActivity().runOnUiThread(() -> {
                        Log.i(TAG, String.format("sendComment: comment: {s}", jsonObject));
                        editTextComment.clearComposingText();
                    });
                }, throwable -> {
                    requireActivity().runOnUiThread(() -> {return;});
                });
    }

    private void setAppBarFab(int visibility) {
        Log.i(TAG, "setAppBarFab: visibility: " + visibility);
        Bundle result = new Bundle();
        result.putInt("visibility", visibility);
        // The child fragment needs to still set the result on its parent fragment manager
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}