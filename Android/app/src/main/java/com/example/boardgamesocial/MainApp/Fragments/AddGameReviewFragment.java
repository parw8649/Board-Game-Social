package com.example.boardgamesocial.MainApp.Fragments;

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
import androidx.navigation.fragment.NavHostFragment;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Review;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGameReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGameReviewFragment extends Fragment {

    public static final String TAG = "AddGameReviewFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddGameReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGameReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGameReviewFragment newInstance(String param1, String param2) {
        AddGameReviewFragment fragment = new AddGameReviewFragment();
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
        return inflater.inflate(R.layout.fragment_add_game_review, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etGameReviewContent = view.findViewById(R.id.et_game_review_content);
        Button btnSaveGame = view.findViewById(R.id.btn_save_review);

        btnSaveGame.setOnClickListener(v -> {

            String review = Objects.nonNull(etGameReviewContent) ? etGameReviewContent.getText().toString() : null;

            //Retrieve the value
            assert getArguments() != null;
            Game game = (Game) getArguments().getSerializable(GameVH.GAME_KEY);

            addGameReview(new Review(Utils.getUserId(), game.getId(), review));
            NavHostFragment
                    .findNavController(AddGameReviewFragment.this)
                    .navigate(R.id.action_addGameReviewFragment_to_gameReviewsFragment, getArguments());
        });
    }

    private void addGameReview(Review review) {

        Log.i(TAG, review.toString());

        RetrofitClient retrofitClient = RetrofitClient.getClient();

        retrofitClient.postCall(Review.class, review).subscribe(jsonObject -> {
            Review addedGameReview = getObject(jsonObject, Review.class);
            if (Objects.nonNull(addedGameReview)) {
                Log.i(TAG, "Game Review added successfully");
            } else {
                Log.i(TAG, "Unable to add game review at the moment!");
            }
        });
    }
}