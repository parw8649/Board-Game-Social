package com.example.boardgamesocial.MainApp.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH.UserToUserVH;
import com.example.boardgamesocial.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    public static final String TAG = "UserProfileFragment";

    private TextView textViewFirstName;
    private TextView textViewUsername;
    private TextView textViewBio;
    private Button buttonUserGameList;
    private Button buttonFriendList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewFirstName = view.findViewById(R.id.userProfile_first_name);
        textViewUsername = view.findViewById(R.id.userProfile_username);
        buttonFriendList = view.findViewById(R.id.userProfile_btn_view_friends);
        buttonUserGameList = view.findViewById(R.id.userProfile_btn_view_user_gamelist);

        User user = (User) requireArguments().getSerializable(UserToUserVH.USER_KEY);
        Log.i(TAG, "onViewCreated: " + user);

        textViewFirstName.setText(user.getFirstName());
        textViewUsername.setText(user.getUsername());
        buttonFriendList.setText(getString(R.string.profile_btn_view_friends, user.getUsername()));
        buttonUserGameList.setText(getString(R.string.profile_btn_view_user_gamelist, user.getUsername()));

        buttonFriendList.setOnClickListener(v -> {
            // this is specifically sending only the user's id and username in a custom bundle
            // should the getArguments() bundle be sent instead?
            NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_userProfileFragment_to_userFriendsFragment, requireArguments());
        });

        buttonUserGameList.setOnClickListener(v -> {
            // this is specifically sending only the user's id and username in a custom bundle
            // should the getArguments() bundle be sent instead?
            NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_userProfileFragment_to_userGamesFragment, requireArguments());
        });
    }
}