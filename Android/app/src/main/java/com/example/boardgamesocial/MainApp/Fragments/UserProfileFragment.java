package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
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
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH.UserToUserVH;
import com.example.boardgamesocial.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    public static final String TAG = "UserProfileFragment";

    private User user;
    private Bundle userBundle;
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Snackbar.make(view, getArguments().getSerializable(UserToUserVH.USER_TO_USER_KEY).toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        // grab user info from viewholder that was tapped on friendlist
        // this is assuming that the viewholder holds the all user information
        user = (User) getArguments().getSerializable(UserToUserVH.USER_TO_USER_KEY);
        // make a custom bundle to send to UserFriendsFragment and UserGamesFragment to minimize retrofit calls?
        userBundle = new Bundle();
        userBundle.putInt("userId", user.getId());
        userBundle.putString("username", user.getUsername());
        Log.i(TAG, String.format("userBundle: %s", userBundle));

        textViewBio = view.findViewById(R.id.userProfile_bio);
        buttonUserGameList = view.findViewById(R.id.userProfile_btn_view_user_gamelist);
        buttonFriendList = view.findViewById(R.id.userProfile_btn_view_friends);

        buttonUserGameList.setOnClickListener(view1 -> NavHostFragment.findNavController(UserProfileFragment.this)
                .navigate(R.id.action_userProfileFragment_to_userGamesFragment));

        textViewBio.setText("Empty bios for all!");

        setNames(view);

        buttonFriendList.setOnClickListener(v -> {
            // this is specifically sending only the user's id and username in a custom bundle
            // should the getArguments() bundle be sent instead?
            NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_userProfileFragment_to_userFriendsFragment, userBundle);
        });

        buttonUserGameList.setOnClickListener(v -> {
            // this is specifically sending only the user's id and username in a custom bundle
            // should the getArguments() bundle be sent instead?
            NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_userProfileFragment_to_userGamesFragment, userBundle);
        });
    }

    private void setNames(View view) {
        textViewFirstName = view.findViewById(R.id.userProfile_first_name);
        textViewUsername = view.findViewById(R.id.userProfile_username);

        textViewFirstName.setText(user.getFirstName());
        textViewUsername.setText(user.getUsername());
        // https://developer.android.com/guide/topics/resources/string-resource#formatting-strings
        String viewUserFriends = getString(R.string.profile_btn_view_friends, user.getUsername());
        String viewUserGames = getString(R.string.profile_btn_view_user_gamelist, user.getUsername());
        buttonFriendList.setText(viewUserFriends);
        buttonUserGameList.setText(viewUserGames);
    }
}