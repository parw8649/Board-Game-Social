package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Profile;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH.UserToUserVH;
import com.example.boardgamesocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

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
    private FloatingActionButton fab;
    private TextView textViewFirstName;
    private TextView textViewUsername;
    private TextView textViewBio;
    private ImageView imageViewUserIcon;
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
        Button buttonEditProfile = view.findViewById(R.id.profile_btn_edit);

        fab = view.findViewById(R.id.bottom_app_bar_fab);
        fab.setVisibility(View.INVISIBLE);

        Log.i(TAG, String.format("userId: %s", Utils.getUserId()));
        retrofitClient = RetrofitClient.getClient();
        textViewBio = view.findViewById(R.id.profile_bio);
        imageViewUserIcon = view.findViewById(R.id.profile_user_icon);
        buttonEditProfile = view.findViewById(R.id.profile_btn_edit);
        buttonUserGameList = view.findViewById(R.id.profile_btn_view_user_gamelist);
        buttonFriendList = view.findViewById(R.id.profile_btn_view_friends);

        if (Objects.isNull(getArguments())) {
            buttonEditProfile.setVisibility(view.VISIBLE);
            buttonEditProfile.setOnClickListener(view1 -> NavHostFragment.findNavController(ProfileFragment.this)
                    .navigate(R.id.action_profileFragment_to_editProfileFragment));
        } else {
            buttonEditProfile.setVisibility(view.GONE);
        }

        setNames(view);
        setBioAndIcon();

        buttonFriendList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();

            if (Objects.nonNull(getArguments())) {
                bundle.putInt("diffUserId", getArguments().getInt("diffUserId"));
            } else {
                bundle = null;
            }
            fab.setVisibility(View.INVISIBLE);

            NavHostFragment.findNavController(ProfileFragment.this)
                    .navigate(R.id.action_profileFragment_to_userFriendsFragment);
        });

        buttonUserGameList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();

            if (Objects.nonNull(getArguments())) {
                bundle.putInt("diffUserId", getArguments().getInt("diffUserId"));
            } else {
                bundle = null;
            }
            fab.setVisibility(View.VISIBLE);

            NavHostFragment.findNavController(ProfileFragment.this)
                    .navigate(R.id.action_profileFragment_to_userGamesFragment);
        });
    }

    private void setNames(View view) {
        Activity activity = getActivity();
        textViewFirstName = view.findViewById(R.id.profile_first_name);
        textViewUsername = view.findViewById(R.id.profile_username);

        retrofitClient.getCall(User.class, new HashMap<String, String>() {{
            put("id", String.valueOf(Utils.getUserId()));
        }}).subscribe(jsonArray -> {
            List<User> userList = getObjectList(jsonArray, User.class);
            activity.runOnUiThread(() -> {
                textViewFirstName.setText(userList.get(0).getFirstName());
                textViewUsername.setText(userList.get(0).getUsername());
                // https://developer.android.com/guide/topics/resources/string-resource#formatting-strings
                String viewUserFriends = getString(R.string.profile_btn_view_friends, userList.get(0).getUsername());
                String viewUserGames = getString(R.string.profile_btn_view_user_gamelist, userList.get(0).getUsername());
                buttonFriendList.setText(viewUserFriends);
                buttonUserGameList.setText(viewUserGames);
            });
        });
    }

    private void setBioAndIcon() {
        retrofitClient.getCall(Profile.class, new HashMap<String, String>() {{
            put("userId", String.valueOf(Utils.getUserId()));
        }}).subscribe(jsonArray -> {
            List<Profile> profiles = getObjectList(jsonArray, Profile.class);
            if (profiles.size() == 1) {
                Utils.addProfileIdToPreferences(getContext(), (Objects.isNull(profiles.get(0).getId())? 99 : profiles.get(0).getId()));
                requireActivity().runOnUiThread(() -> {
                    textViewBio.setText(profiles.get(0).getBio());
                    Picasso
                            .with(getContext())
                            .load(profiles.get(0).getIconUrl())
                            .fit()
                            .placeholder(R.drawable.show_circular_image)
                            .into(imageViewUserIcon);
                });
            } else if (profiles.size() == 0) {
                retrofitClient.postCall(Profile.class, new Profile(Utils.getUserId(), getString(R.string.profile_bio_placeholder),null))
                        .subscribe(jsonObject -> {
                            Profile profile = getObject(jsonObject,Profile.class);
                            Utils.addProfileIdToPreferences(getContext(), (Objects.isNull(profile.getId())? 99 : profile.getId()));
                            requireActivity().runOnUiThread(() -> {
                                textViewBio.setText(profile.getBio());
                            });
                        },throwable1 -> {
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "Unable to connect user bio and icon", Toast.LENGTH_SHORT).show();
                            });
                        });
            } else {
                Toast.makeText(getContext(), "Retrieved many Profiles with given username.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}