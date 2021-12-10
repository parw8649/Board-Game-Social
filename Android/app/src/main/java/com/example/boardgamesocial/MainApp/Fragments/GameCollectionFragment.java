package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Relationships.GameToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter.OnItemListener;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameCollectionFragment extends Fragment implements OnItemListener {

    public static final String TAG = "GameCollectionFragment";

    private final Gson gson = new GsonBuilder().create();

    private List<GameToUser> gameToUserList;

    private FloatingActionButton fab;
    private NavController navController;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameCollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameCollectionFragment newInstance(String param1, String param2) {
        GameCollectionFragment fragment = new GameCollectionFragment();
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
        Log.i(TAG, String.format("GamesCollection - User Id: %s", Utils.getUserId()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_collection, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.gameFeed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DataClsAdapter<Game, GameVH> dataClsAdapter = new DataClsAdapter<>(
                this,
                Game.class,
                getActivity(),
                R.layout.game_item);
        recyclerView.setAdapter(dataClsAdapter);

        RetrofitClient retrofitClient = RetrofitClient.getClient();

        DataClsVM dataClsVM = DataClsVM.getInstance();
        /*dataClsVM.getMediatorLiveData(
                retrofitClient.getCall(GameToUser.class, new HashMap<String, String>() {{
                    put("private", "True");
                }}).mergeWith(retrofitClient.getCall(Game.class, new HashMap<>()))
                        .scan((resultingArray, array) -> {

                            List<GameToUser> gameToUserList = getObjectList(array, GameToUser.class);

                            Log.i(TAG, "STARTING GAME_TO_USER LIST: ");
                            gameToUserList.forEach(System.out::println);
                            Log.i(TAG, "ENDING GAME_TO_USER LIST!");

                            List<Integer> gameIdList = gameToUserList
                                    .stream()
                                    .filter(gameToUser -> Objects.nonNull(gameToUser.getUserId()))
                                    .map(GameToUser::getGameId)
                                    .collect(Collectors.toList());

                            Log.i(TAG, "STARTING GAME ID LIST: ");
                            gameIdList.forEach(x -> System.out.print(x + " "));
                            Log.i(TAG, "ENDING GAME ID LIST!");

                            List<Game> gameList = getObjectList(array, Game.class);

                            return gson.toJsonTree(gameList
                                    .stream()
                                    .filter(game -> Objects.nonNull(game.getGameTitle()))
                                    .filter(game -> !gameIdList.contains(game.getId()))
                                    .collect(Collectors.toList())).getAsJsonArray();
                        })
                , Game.class, true)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);*/


        dataClsVM.getMediatorLiveData(
                retrofitClient.getCall(GameToUser.class, new HashMap<String, String>() {{
                    put("private", "True");
                }}).flatMap(gameToUserArray -> {
                    List<GameToUser> gameToUserList = getObjectList(gameToUserArray, GameToUser.class);
                    List<Integer> gameIdList = gameToUserList
                            .stream()
                            .filter(gameToUser -> Objects.nonNull(gameToUser.getUserId()))
                            .map(GameToUser::getGameId)
                            .collect(Collectors.toList());

                    return retrofitClient.getCall(Game.class, new HashMap<>()).
                            map(gameArray -> {
                                List<Game> gameList = getObjectList(gameArray, Game.class);
                                return gson.toJsonTree(gameList
                                        .stream()
                                        .filter(game -> Objects.nonNull(game.getGameTitle()))
                                        .filter(game -> !gameIdList.contains(game.getId()))
                                        .collect(Collectors.toList())).getAsJsonArray();
                            });
                }).scan((resultingArray, dataArray) -> {
                    resultingArray = new JsonArray();
                    resultingArray.addAll(dataArray);
                    return resultingArray;
                })
                , Game.class, true)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main_app);

        fab = requireActivity().findViewById(R.id.bottom_app_bar_fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(v -> {
            navController.navigate(R.id.addGameFragment);
            fab.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(Bundle contextBundle) {
        //Toast.makeText(getContext(),"Reviews functionality to be implemented!", Toast.LENGTH_LONG).show();

        NavHostFragment.findNavController(GameCollectionFragment.this)
                .navigate(R.id.action_gameCollectionFragment_to_gameReviews, contextBundle);
    }

    //Method to remove private games from the games collection
    /*private Observable<JsonArray> fetchNonPrivateGames() {

        Log.i(TAG, "Inside fetchNonPrivateGames");

        gameToUserList = new ArrayList<>();

        RetrofitClient retrofitClient = RetrofitClient.getClient();

        //JsonArray jsonArray = new JsonArray();

        retrofitClient.getCall(GameToUser.class, new HashMap<String, String>() {{
            put("private", "True");
        }}).mergeWith(retrofitClient.getCall(Game.class, new HashMap<>()))
                .scan((resultingArray, array) -> {
                    List<GameToUser> gameToUserList = getObjectList(array, GameToUser.class);
                    List<Integer> gameIdList = gameToUserList.stream().map(GameToUser::getGameId).collect(Collectors.toList());

                    List<Game> gameList = getObjectList(array, Game.class);
                    resultingArray.addAll(gson.toJsonTree(gameList
                            .stream()
                            .filter(game -> !gameIdList.contains(game.getId()))
                            .collect(Collectors.toList()))
                            .getAsJsonArray()
                    );

                    return resultingArray;
                });

            gameToUserList = getObjectList(gameToUserJson, GameToUser.class);
            List<Integer> gameIdList = gameToUserList.stream().map(GameToUser::getGameId).collect(Collectors.toList());


        //Log.i(TAG, "JSON_ARRAY: " + jsonArray.toString());

        //return Observable.fromArray(jsonArray);
    }*/
}