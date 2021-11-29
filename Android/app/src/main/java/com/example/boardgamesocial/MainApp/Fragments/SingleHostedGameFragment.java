package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleHostedGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleHostedGameFragment extends Fragment {

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
        TextView tvMinPlayer = view.findViewById(R.id.tv_single_game_min_player_count);
        TextView tvMaxPlayer = view.findViewById(R.id.tv_single_game_max_player_count);
        TextView tvDescription = view.findViewById(R.id.tv_game_description);
        ImageView ivImageUrl = view.findViewById(R.id.game_icon);
        TextView tvOverallPlayCount = view.findViewById(R.id.tv_single_game_overall_play_count);
        TextView tvSeatsAvailable = view.findViewById(R.id.tv_single_game_seats_available);

        //Retrieve the value
        assert getArguments() != null;
        Game game = (Game) getArguments().getSerializable(GameVH.GAME_KEY);

        tvGameTitle.setText(game.getGameTitle());
        tvGenre.setText(game.getGenre());
        tvMinPlayer.setText(game.getMinPlayer());
        tvMaxPlayer.setText(game.getMaxPlayer());
        tvDescription.setText(game.getDescription());

        Picasso
                .with(view.getContext())
                .load(game.getImageUrl())
                .fit()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(ivImageUrl);

        tvOverallPlayCount.setText(game.getOverallPlayCount());

        int seatsAvailable = getArguments().getInt("SEATS_AVAILABLE");
        tvSeatsAvailable.setText(seatsAvailable);
    }
}