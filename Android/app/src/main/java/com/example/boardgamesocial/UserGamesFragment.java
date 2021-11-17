package com.example.boardgamesocial;

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

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.RecycleAdapters.GameAdapter;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserGamesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> games;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserGamesFragment newInstance(String param1, String param2) {
        UserGamesFragment fragment = new UserGamesFragment();
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

        Utils.checkForUser(requireContext());
        Log.i("GamesCollection - User Token: ", Utils.getUserToken());

        getUserGamesData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_games, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.userGames_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
        gameAdapter = new GameAdapter();
        recyclerView.setAdapter(gameAdapter);
    }

    private void getUserGamesData() {

        RetrofitClient retrofitClient = RetrofitClient.getClient();

        retrofitClient.setAuthToken(Utils.getUserToken());

        //TODO: Need to fetch user games from db.
        retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
            put("gameTitle", "7 Wonders Duel");
        }}).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    games = getObjectList(response.body(), Game.class);
                    Log.i("Game", games.get(0).getGameTitle());

                    if(games != null && !games.isEmpty())
                        gameAdapter.setGames(games);

                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}