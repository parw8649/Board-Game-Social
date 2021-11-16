package com.example.boardgamesocial;

import static com.example.boardgamesocial.API.RetrofitClient.getObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Game;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGameFragment extends Fragment {

     private EditText etGameTitle;
     private EditText etGenre;
     private EditText etMinPlayer;
     private EditText etMaxPlayer;
     private EditText etDescription;
     private EditText etImageUrl;
     private EditText etOverallPlayCount;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGameFragment newInstance(String param1, String param2) {
        AddGameFragment fragment = new AddGameFragment();
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
        return inflater.inflate(R.layout.fragment_add_game, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etGameTitle = view.findViewById(R.id.et_game_title);
        etGenre = view.findViewById(R.id.et_game_genre);
        etMinPlayer = view.findViewById(R.id.et_game_min_players);
        etMaxPlayer = view.findViewById(R.id.et_game_max_players);
        etDescription = view.findViewById(R.id.et_game_description);
        etImageUrl = view.findViewById(R.id.et_game_image_url);
        etOverallPlayCount = view.findViewById(R.id.et_game_overall_play_count);

        Button btnSaveGame = view.findViewById(R.id.btn_save_game);

        btnSaveGame.setOnClickListener(v -> {

            String gameTitle = Objects.nonNull(etGameTitle) ? etGameTitle.getText().toString() : null;
            String genre = Objects.nonNull(etGenre) ? etGenre.getText().toString() : null;
            Integer minPlayer = Objects.nonNull(etMinPlayer) ? Integer.parseInt(etMinPlayer.getText().toString()) : 0;
            Integer maxPlayer = Objects.nonNull(etMaxPlayer) ? Integer.parseInt(etMaxPlayer.getText().toString()) : 0;
            String description = Objects.nonNull(etDescription) ? etDescription.getText().toString() : null;
            String imageUrl = Objects.nonNull(etImageUrl) ? etImageUrl.getText().toString() : null;
            Integer overallPlayCount = Objects.nonNull(etOverallPlayCount) ? Integer.parseInt(etOverallPlayCount.getText().toString()) : 0;

            addGame(new Game(gameTitle, genre, minPlayer, maxPlayer, description, imageUrl, overallPlayCount));
        });
    }

    private void addGame(Game game) {

        RetrofitClient retrofitClient = RetrofitClient.getClient();

        retrofitClient.setAuthToken(Utils.getUserToken());

        retrofitClient.postCall(Game.class, game).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    Game respGame = getObject(response.body(), Game.class);
                    Log.i("Game", respGame.getGameTitle());

                } else {
                    new Exception("Request failed, code: " + response.code()).printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}