package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.PostVH;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SinglePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SinglePostFragment extends Fragment {

    public static final String TAG = "SinglePostFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RetrofitClient retrofitClient;
    private TextView textViewUsername;
    private TextView textViewPostType;
    private TextView textViewBody;
    private TextView textViewLikes;
    private ImageView imageViewUserIcon;

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
        Toast.makeText(getContext(), this.getArguments().getSerializable(PostVH.POST_KEY).toString(), Toast.LENGTH_SHORT).show();
        retrofitClient = RetrofitClient.getClient();

        Post post = (Post) getArguments().getSerializable(PostVH.POST_KEY);
        textViewUsername = view.findViewById(R.id.single_post_poster);
        textViewPostType = view.findViewById(R.id.single_post_type);
        textViewBody = view.findViewById(R.id.single_post_body);
        textViewLikes = view.findViewById(R.id.single_post_number_likes);
        imageViewUserIcon = view.findViewById(R.id.single_post_user_icon);

        setUsername(post.getUserId());
        textViewPostType.setText(post.getPostType().toString());
        textViewBody.setText(post.getPostBody().toString());
        textViewLikes.setText(post.getLikes().toString());
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
}