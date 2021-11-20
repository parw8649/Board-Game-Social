package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private RetrofitClient retrofitClient;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * returns a new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        retrofitClient = RetrofitClient.getClient();
        TextView textViewFirstName = view.findViewById(R.id.profile_first_name);
        TextView textViewUsername = view.findViewById(R.id.profile_username);
        TextView textViewBio = view.findViewById(R.id.profile_bio);
        Button buttonUserGameList = view.findViewById(R.id.profile_btn_view_user_gamelist);
        Button buttonEditProfile = view.findViewById(R.id.profile_btn_edit);
        Button buttonFriendList = view.findViewById(R.id.profile_btn_view_friends);
        ArrayList<String> names = getNames(Utils.getUserId());

        textViewFirstName.setText(names.get(0));
        textViewUsername.setText(names.get(1));
        textViewBio.setText("hmmm...users don't seem to have a bio field in the database?");
        // https://developer.android.com/guide/topics/resources/string-resource#formatting-strings
        buttonFriendList.setText(getString(R.string.profile_btn_view_friends, names.get(1)));
        buttonUserGameList.setText(getString(R.string.profile_btn_view_user_gamelist, names.get(1)));

        buttonEditProfile.setOnClickListener(view1 -> NavHostFragment.findNavController(ProfileFragment.this)
                .navigate(R.id.action_profileFragment_to_editProfileFragment));
        buttonFriendList.setOnClickListener(v -> NavHostFragment.findNavController(ProfileFragment.this)
                .navigate(R.id.action_profileFragment_to_userFriendsFragment));
        buttonUserGameList.setOnClickListener(v -> NavHostFragment.findNavController(ProfileFragment.this)
                .navigate(R.id.action_profileFragment_to_gameCollectionFragment));
    }

    private ArrayList<String> getNames(Integer userId) {
        ArrayList<String> names = new ArrayList<>();
        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("id", userId.toString());
        }}).subscribe(jsonArray -> {
            List<User> userList = getObjectList(jsonArray, User.class);
            names.add(userList.get(0).getFirstName());
            names.add(userList.get(0).getUsername());
            return names;
        });
    }
}