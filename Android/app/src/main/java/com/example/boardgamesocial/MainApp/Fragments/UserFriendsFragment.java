package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.PostVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH.UserToUserVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFriendsFragment extends Fragment implements DataClsAdapter.OnItemListener {

    public static final String TAG = "UserFriendsFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DataClsAdapter<UserToUser, UserToUserVH> dataClsAdapter;

    public UserFriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFriendsFragment newInstance(String param1, String param2) {
        UserFriendsFragment fragment = new UserFriendsFragment();
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
        return inflater.inflate(R.layout.fragment_user_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.friendFeed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dataClsAdapter = new DataClsAdapter<>(
                this,
                UserToUser.class,
                getActivity(),
                R.layout.user_item);
        recyclerView.setAdapter(dataClsAdapter);

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(UserToUser.class, new HashMap<String, String>(){{
            put("userOneId", String.valueOf(Utils.getUserId()));
        }}), UserToUser.class)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);
    }

    @Override
    public void onItemClick(Bundle contextBundle) {
        NavHostFragment.findNavController(UserFriendsFragment.this)
                .navigate(R.id.action_userFriendsFragment_to_userProfileFragment, contextBundle);
    }
}