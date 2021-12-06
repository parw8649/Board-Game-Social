package com.example.boardgamesocial.MainApp.Fragments;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;
import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.HostedGame;
import com.example.boardgamesocial.DataClasses.Relationships.EventToUser;
import com.example.boardgamesocial.DataClasses.Relationships.HostedGameToUser;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleHostedGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleHostedGameFragment extends Fragment {

    public static final String TAG = "SingleHostedGameFragment";

    private RetrofitClient retrofitClient;

    private Integer eventId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingleHostedGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleHostedGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleHostedGameFragment newInstance(String param1, String param2) {
        SingleHostedGameFragment fragment = new SingleHostedGameFragment();
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
        return inflater.inflate(R.layout.fragment_single_hosted_game, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvGameTitle = view.findViewById(R.id.tv_game_title);
        TextView tvGenre = view.findViewById(R.id.tv_game_genre);
        TextView tvMinPlayer = view.findViewById(R.id.tv_min_player_count);
        TextView tvMaxPlayer = view.findViewById(R.id.tv_max_player_count);
        TextView tvDescription = view.findViewById(R.id.tv_game_description);
        ImageView ivImageUrl = view.findViewById(R.id.game_icon);
        TextView tvOverallPlayCount = view.findViewById(R.id.tv_overall_play_count);
        TextView tvSeatsAvailable = view.findViewById(R.id.tv_single_game_seats_available);
        Button btnJoinGame = view.findViewById(R.id.btn_join_game);

        //Retrieve the value
        assert getArguments() != null;
        Game game = (Game) getArguments().getSerializable(GameVH.GAME_KEY);

        tvGameTitle.setText(game.getGameTitle());
        tvGenre.setText(game.getGenre());
        tvMinPlayer.setText(String.format(getString(R.string.game_min_player_text), game.getMinPlayer()));
        tvMaxPlayer.setText(String.format(getString(R.string.game_max_player_text), game.getMaxPlayer()));
        tvDescription.setText(game.getDescription());
        tvDescription.setMovementMethod(new ScrollingMovementMethod());

        Picasso
                .with(view.getContext())
                .load(game.getImageUrl())
                .fit()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(ivImageUrl);

        tvOverallPlayCount.setText(String.format(getString(R.string.game_overall_play_count_text), game.getOverallPlayCount()));

        int seatsAvailable = getArguments().getInt("SEATS_AVAILABLE");
        tvSeatsAvailable.setText(String.format(getString(R.string.game_seats_available_text), seatsAvailable));

        HostedGame hostedGame = (HostedGame) getArguments().getSerializable("HOSTED_GAME");

        eventId = hostedGame.getEventId();

        retrofitClient = RetrofitClient.getClient();

        btnJoinGame.setText(doesHostedGameToUserExists(hostedGame.getGameId()) ? "Un-Join Game" : "Join Game");

        btnJoinGame.setOnClickListener(v -> {

            HostedGame updatedHostedGame = getHostedGameByGameId(eventId, hostedGame.getGameId());
            int availableSeats = updatedHostedGame.getSeatsAvailable();

            if(availableSeats <= 0)
                Toast.makeText(getContext(), "No Seats Available at the moment!", Toast.LENGTH_LONG).show();

            availableSeats = processJoinGame(availableSeats, updatedHostedGame);

            btnJoinGame.setText(doesHostedGameToUserExists(updatedHostedGame.getGameId()) ? "Un-Join Game" : "Join Game");

            tvSeatsAvailable.setText(String.format(getString(R.string.game_seats_available_text), availableSeats));

        });
    }

    private HostedGame getHostedGameByGameId(Integer eventId, Integer gameId) {

        AtomicReference<HostedGame> hostedGame = new AtomicReference<>();

        try {

            retrofitClient.getCall(HostedGame.class, new HashMap<String, String>() {{
                put("eventId", String.valueOf(eventId));
                put("gameId", String.valueOf(gameId));
            }}).blockingSubscribe(hostedGameJson -> {
                List<HostedGame> hostedGameList = getObjectList(hostedGameJson, HostedGame.class);
                if (!hostedGameList.isEmpty()) {
                    hostedGame.set(hostedGameList.get(0));
                }
            });

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return hostedGame.get();
    }

    private int processJoinGame(int seatsAvailable, HostedGame hostedGameToJoin) {

        Log.i(TAG, "Inside processJoinEvent with seatsAvailable: " + seatsAvailable);

        try {

            if(insertHostedGameToUser(hostedGameToJoin.getGameId())) {
                insertEventToUser(hostedGameToJoin.getEventId());
                seatsAvailable--;
            } else {
                seatsAvailable++;
                deleteHostedGameToUser(hostedGameToJoin);
            }

            hostedGameToJoin.setSeatsAvailable(seatsAvailable);
            updateHostedGameAvailableSeats(hostedGameToJoin);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return seatsAvailable;
    }

    private void deleteHostedGameToUser(HostedGame hostedGame) {

        try {

            if(doesHostedGameToUserExists(hostedGame.getGameId())) {
                int userId = Utils.getUserId();
                retrofitClient.deleteCall(HostedGameToUser.class, new HashMap<String, String>() {{
                    put("gameId", String.valueOf(hostedGame.getGameId()));
                    put("userId", String.valueOf(userId));
                }}).blockingSubscribe(deletedHostedGameToUserJson -> {
                    retrofitClient.getCall(HostedGameToUser.class, new HashMap<String, String>() {{
                        put("userId", String.valueOf(userId));
                    }}).blockingSubscribe(hostedGameToUserJson -> {
                        List<HostedGameToUser> hostedGameToUserList = getObjectList(hostedGameToUserJson, HostedGameToUser.class);
                        if (hostedGameToUserList.isEmpty()) {
                            deleteEventToUser(hostedGame.getEventId());
                        }
                    });
                });
            } else
                Log.e(TAG, "No Records found for HostedGameToUser!");

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    private void deleteEventToUser(Integer eventId) {

        try {

            if(doesEventToUserExists(eventId)) {
                int userId = Utils.getUserId();
                retrofitClient.deleteCall(EventToUser.class, new HashMap<String, String>() {{
                    put("eventId", String.valueOf(eventId));
                    put("userId", String.valueOf(userId));
                }}).blockingSubscribe();
            } else
                Log.e(TAG, "No Records found for EventToUser!");

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }


    private void updateHostedGameAvailableSeats(HostedGame hostedGameToJoin) {

        try {

            retrofitClient.putCall(HostedGame.class, new HashMap<String, String>() {{
                put("id", String.valueOf(hostedGameToJoin.getId()));
                put("eventId", String.valueOf(hostedGameToJoin.getEventId()));
                put("gameId", String.valueOf(hostedGameToJoin.getGameId()));
                put("seatsAvailable", String.valueOf(hostedGameToJoin.getSeatsAvailable()));
            }}).blockingSubscribe(updatedHostedGameJson -> {
                HostedGame updatedHostedGame = getObject(updatedHostedGameJson, HostedGame.class);
                if (Objects.nonNull(updatedHostedGame)) {
                    Log.i(TAG, updatedHostedGame.toString());
                    Log.i(TAG, "HostedGame seats updated successfully");
                } else
                    Log.e(TAG, "Unable to update HostedGame seats at the moment");
            });

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean doesHostedGameToUserExists(Integer gameId) {

        Log.i(TAG, "Inside doesHostedGameToUserExists with gameId: " + gameId);

        AtomicBoolean hostedGameToUserExists = new AtomicBoolean(false);

        try {

            //if(doesEventToUserExists(eventId)) {

                int userId = Utils.getUserId();
                retrofitClient.getCall(HostedGameToUser.class, new HashMap<String, String>() {{
                    put("gameId", String.valueOf(gameId));
                    put("userId", String.valueOf(userId));
                }}).blockingSubscribe(hostedGameToUserJson -> {
                    List<HostedGameToUser> hostedGameToUserList = getObjectList(hostedGameToUserJson, HostedGameToUser.class);
                    if (Objects.nonNull(hostedGameToUserList) && !hostedGameToUserList.isEmpty()) {
                        hostedGameToUserExists.set(true);
                    } else
                        Log.i(TAG, "No HostedGameToUser Records found!");
                });
            //}

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return hostedGameToUserExists.get();
    }

    private boolean insertHostedGameToUser(Integer gameId) {

        AtomicBoolean isRecordInserted = new AtomicBoolean(false);

        try {

            if(!doesHostedGameToUserExists(gameId)) {
                int userId = Utils.getUserId();
                HostedGameToUser gameToUser = new HostedGameToUser(userId, gameId);

                retrofitClient.postCall(HostedGameToUser.class, gameToUser).blockingSubscribe(gameToUserJson -> {
                    HostedGameToUser addedHostedGameToUser = getObject(gameToUserJson, HostedGameToUser.class);
                    if (Objects.nonNull(addedHostedGameToUser)) {
                        Log.i(TAG, addedHostedGameToUser.getId() + " : " + addedHostedGameToUser.getGameId());
                        Log.i(TAG, "HostedGameToUser added successfully");

                        isRecordInserted.set(true);
                    } else
                        Log.e(TAG, "Unable to add HostedGameToUser at the moment!");
                });
            } else
                Log.e(TAG, "HostedGameToUser already exists!");

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return isRecordInserted.get();
    }

    private boolean doesEventToUserExists(Integer eventId) {

        AtomicBoolean eventToUserExists = new AtomicBoolean(false);

        try {

            int userId = Utils.getUserId();
            retrofitClient.getCall(EventToUser.class, new HashMap<String, String>() {{
                put("eventId", String.valueOf(eventId));
                put("userId", String.valueOf(userId));
            }}).blockingSubscribe(eventToUserJson -> {
                List<EventToUser> eventToUserList = getObjectList(eventToUserJson, EventToUser.class);
                if (Objects.nonNull(eventToUserList) && !eventToUserList.isEmpty()) {
                    eventToUserExists.set(true);
                } else
                    Log.i(TAG, "No EventToUser Records found!");
            });

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return eventToUserExists.get();
    }

    private void insertEventToUser(Integer eventId) {

        try {

            if(!doesEventToUserExists(eventId)) {

                int userId = Utils.getUserId();
                EventToUser eventToUser = new EventToUser(userId, eventId);

                retrofitClient.postCall(EventToUser.class, eventToUser).blockingSubscribe(eventToUserJson -> {
                    EventToUser addedEventToUser = getObject(eventToUserJson, EventToUser.class);
                    if (Objects.nonNull(addedEventToUser)) {
                        Log.i(TAG, addedEventToUser.getId() + " : " + addedEventToUser.getEventId());
                        Log.i(TAG, "EventToUser added successfully");
                    } else
                        Log.e(TAG, "Unable to add EventToUser at the moment!");
                });
            } else
                Log.e(TAG, "EventToUser already exists!");

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}