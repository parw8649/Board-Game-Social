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

    public static final String TAG = "ProfileFragment";

    private RetrofitClient retrofitClient;
    private TextView textViewFirstName;
    private TextView textViewUsername;
    private TextView textViewBio;
    private Button buttonEditProfile;
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

        Button buttonEditProfile = view.findViewById(R.id.profile_btn_edit);
        Button btnUserGames = view.findViewById(R.id.profile_btn_view_user_gamelist);

        Log.i(TAG, String.format("userId: %s", Utils.getUserId()));
        retrofitClient = RetrofitClient.getClient();
        textViewBio = view.findViewById(R.id.profile_bio);
        buttonEditProfile = view.findViewById(R.id.profile_btn_edit);
        buttonUserGameList = view.findViewById(R.id.profile_btn_view_user_gamelist);
        buttonFriendList = view.findViewById(R.id.profile_btn_view_friends);

        btnUserGames.setOnClickListener(view1 -> NavHostFragment.findNavController(UserProfileFragment.this)
                .navigate(R.id.action_profileFragment_to_userGamesFragment));

        if (Objects.isNull(getArguments())) {
            buttonEditProfile.setVisibility(view.VISIBLE);
            buttonEditProfile.setOnClickListener(view1 -> NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_profileFragment_to_editProfileFragment));
        } else {
            buttonEditProfile.setVisibility(view.GONE);
        }

        textViewBio.setText("Empty bios for all!");

        if (Objects.nonNull(getArguments())) {
            setNames(view, getArguments().getInt("diffUserId"));
        } else {
            setNames(view, Utils.getUserId());
        }

        buttonFriendList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();

            if (Objects.isNull(getArguments().getSerializable(UserToUserVH.USER_TO_USER_KEY))) {
                bundle.putInt("diffUserId", getArguments().getInt("diffUserId"));
            } else {
                bundle = null;
            }
            NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_userProfileFragment_to_userFriendsFragment, bundle);
        });

        buttonUserGameList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();

            if (Objects.nonNull(getArguments())) {
                bundle.putInt("diffUserId", getArguments().getInt("diffUserId"));
            } else {
                bundle = null;
            }
            NavHostFragment.findNavController(UserProfileFragment.this)
                    .navigate(R.id.action_userProfileFragment_to_userGamesFragment, bundle);
        });
    }

    private void setNames(View view, Integer userId) {
        Activity activity = getActivity();
        textViewFirstName = view.findViewById(R.id.userProfile_first_name);
        textViewUsername = view.findViewById(R.id.userProfile_username);


    }
}