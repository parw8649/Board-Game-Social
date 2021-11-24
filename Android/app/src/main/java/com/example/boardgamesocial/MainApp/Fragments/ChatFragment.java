package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.boardgamesocial.DataClasses.Message;
import com.example.boardgamesocial.DataClasses.Post;
import com.example.boardgamesocial.DataClasses.Relationships.UserToUser;
import com.example.boardgamesocial.DataClasses.User;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.PostVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements DataClsAdapter.OnItemListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.friendsChats_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DataClsAdapter<User, ChatVH> dataClsAdapter = new DataClsAdapter<>(
                this,
                User.class,
                getActivity(),
                R.layout.chat_item);
        recyclerView.setAdapter(dataClsAdapter);

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(User.class, new HashMap<String, String>(){{
                    put("userOneId", String.valueOf(Utils.getUserId()));
                }})
                .flatMap(jsonArray -> {
                    List<UserToUser> userFriendsRelations = getObjectList(jsonArray, UserToUser.class);
                    List<Observable<JsonArray>> friendsDataObservables = new ArrayList<>();
                    for (UserToUser relation : userFriendsRelations) {
                        friendsDataObservables.add(RetrofitClient.getClient().getCall(User.class, new HashMap<String, String>(){{
                            put("id", String.valueOf(relation.getUserTwoId()));
                        }}));
                    }
                    return Observable.merge(friendsDataObservables);
                }), User.class)
                .observe(getViewLifecycleOwner(), newFriends -> {
                    dataClsAdapter.getObjectList().addAll(newFriends);
                    dataClsAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onItemClick(int position) {
        NavHostFragment.findNavController(ChatFragment.this)
                .navigate(R.id.action_chatFragment_to_friendChatFragment);
        Toast.makeText(getContext(),"Item clicked", Toast.LENGTH_LONG).show();
    }
}