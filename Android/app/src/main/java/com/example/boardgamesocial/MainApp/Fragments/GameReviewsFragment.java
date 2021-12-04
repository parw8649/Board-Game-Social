package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Review;
import com.example.boardgamesocial.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameReviewsFragment extends Fragment {

    public static final String TAG = "GameReviewsFragment";

    private Game game;

    private List<Review> reviewList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameReviewsFragment newInstance(String param1, String param2) {
        GameReviewsFragment fragment = new GameReviewsFragment();
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
        return inflater.inflate(R.layout.fragment_game_reviews, container, false);
    }
}