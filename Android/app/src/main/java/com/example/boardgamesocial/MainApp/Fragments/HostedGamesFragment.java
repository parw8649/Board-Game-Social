package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.HostedGame;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter.OnItemListener;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.EventVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostedGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostedGamesFragment extends Fragment implements OnItemListener {

    public static final String TAG = "HostedGamesFragment";

    private RecyclerView recyclerView;

    private Event event;

    List<HostedGame> hostedGameList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HostedGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HostedGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HostedGamesFragment newInstance(String param1, String param2) {
        HostedGamesFragment fragment = new HostedGamesFragment();
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
        return inflater.inflate(R.layout.fragment_hosted_games, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Retrieve the value
        assert getArguments() != null;
        event = (Event) getArguments().getSerializable(EventVH.EVENT_KEY);

        //List<Integer> gameIdList = new ArrayList<>();

        RetrofitClient.getClient().getCall(HostedGame.class, new HashMap<String, String>() {{
            put("eventId", event.getId().toString());
        }}).blockingSubscribe(hosted -> {
            hostedGameList = getObjectList(hosted, HostedGame.class);
            /*if (Objects.nonNull(hostedGameList) && !hostedGameList.isEmpty()) {
                hostedGameList.forEach(hostedGame -> gameIdList.add(hostedGame.getGameId()));
            }*/
        });

        /*Map<String, String> gameFilter = new HashMap<>();

        Log.i(TAG, "HostedGameList: " + hostedGameList.stream().map(HostedGame::getGameId).map(Object::toString).collect(Collectors.joining(", ")));

        hostedGameList.forEach(hostedGame -> gameFilter.put("hostedgame", hostedGame.getGameId().toString()));
        gameFilter.put("hostedgame", hostedGameList.stream().map(HostedGame::getId).map(Object::toString).collect(Collectors.joining(", ")));

        Observable<JsonArray> arrayObservable =
                hostedGameList.stream().map(hostedGame -> RetrofitClient.getClient().getCall(Game.class, new HashMap<String, String>() {{
            put("hostedgame", hostedGame.getId().toString());
        }}).subscribe(JsonElement::getAsJsonObject)).collect();*/

        JsonArray jsonArray = new JsonArray();

        hostedGameList.forEach(hostedGame -> {
            RetrofitClient.getClient().getCall(Game.class, new HashMap<String, String>() {{
                put("id", hostedGame.getGameId().toString());
            }}).blockingSubscribe(j -> {
                if(!j.isEmpty())
                    jsonArray.add(j.get(0));
            });
        });

        Observable<JsonArray> arrayObservable = Observable.fromArray(jsonArray);

        recyclerView = view.findViewById(R.id.eventHostedGame_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DataClsAdapter<Game, GameVH> dataClsAdapter = new DataClsAdapter<>(
                this,
                Game.class,
                getActivity(),
                R.layout.hosted_game_item);
        recyclerView.setAdapter(dataClsAdapter);

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(arrayObservable, Game.class)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(Bundle contextBundle) {

        Game game = (Game) contextBundle.getSerializable(GameVH.GAME_KEY);

        if(Objects.nonNull(game)) {
            contextBundle.putInt("SEATS_AVAILABLE", getSeatsAvailableByGameId(game.getId()));

            Optional<HostedGame> optionalHostedGame = hostedGameList.stream()
                    .filter(hostedGame -> hostedGame.getGameId().equals(game.getId()))
                    .findFirst();

            optionalHostedGame.ifPresent(hostedGame -> contextBundle.putSerializable("HOSTED_GAME", hostedGame));
        }


        NavHostFragment.findNavController(HostedGamesFragment.this)
                .navigate(R.id.action_hostedGamesFragment_to_singleHostedGameFragment, contextBundle);
    }

    private Integer getSeatsAvailableByGameId(Integer gameId) {

        OptionalInt noOfSeatsAvailable = hostedGameList.stream()
                .filter(hostedGame -> hostedGame.getGameId().equals(gameId))
                .mapToInt(HostedGame::getSeatsAvailable)
                .findFirst();

        if(noOfSeatsAvailable.isPresent())
            return noOfSeatsAvailable.getAsInt();
        else
            return 0;
    }

    //Method added for updating hostedGames in an event
    private Observable<JsonArray> fetchUpdatedEventHostedGames() {

        Log.i(TAG, "Inside fetchUpdatedEventHostedGames");

        //Retrieve the value
        assert getArguments() != null;
        event = (Event) getArguments().getSerializable(EventVH.EVENT_KEY);

        //Fetching all games in the specified event
        RetrofitClient.getClient().getCall(HostedGame.class, new HashMap<String, String>() {{
            put("eventId", event.getId().toString());
        }}).blockingSubscribe(hosted -> hostedGameList = getObjectList(hosted, HostedGame.class));

        JsonArray jsonArray = new JsonArray();

        //Fetching game details for game ids
        hostedGameList.forEach(hostedGame -> RetrofitClient.getClient().getCall(Game.class, new HashMap<String, String>() {{
            put("id", hostedGame.getGameId().toString());
        }}).blockingSubscribe(j -> {
            if(!j.isEmpty())
                jsonArray.add(j.get(0));
        }));

        return Observable.fromArray(jsonArray);
    }
}