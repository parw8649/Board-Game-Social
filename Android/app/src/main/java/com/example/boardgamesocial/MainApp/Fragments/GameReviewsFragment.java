package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataClasses.Review;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.ReviewVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameReviewsFragment extends Fragment implements DataClsAdapter.OnItemListener {

    public static final String TAG = "GameReviewsFragment";

    private Game game;

    private List<Review> reviewList;

    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;
    private NavController navController;

    private float fabOffsetVisible = 0.0f;
    private float fabOffsetInvisible = 35.0f;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Retrieve the value
        assert getArguments() != null;
        game = (Game) getArguments().getSerializable(GameVH.GAME_KEY);

        /*//Fetching reviews from Review based on specified game
        RetrofitClient.getClient().getCall(Review.class, new HashMap<String, String>() {{
            put("gameId", game.getId().toString());
        }}).blockingSubscribe(reviewJson -> {
            reviewList = getObjectList(reviewJson, Review.class);
        });

        JsonArray jsonArray = new JsonArray();

        //Fetching user details based on users in the event
        reviewList.forEach(review -> {
            RetrofitClient.getClient().getCall(User.class, new HashMap<String, String>() {{
                put("id", review.getUserId().toString());
            }}).blockingSubscribe(j -> {
                if(!j.isEmpty()) {
                    jsonArray.add(j.get(0));
                    jsonArray.add(review.getContent());
                }
            });
        });

        Log.i(TAG, "Attendees: " + jsonArray);
        TextView tvAttendees = view.findViewById(R.id.tv_game_reviews);
        tvAttendees.setText(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(jsonArray.toString())));*/

        //Observable<JsonArray> arrayObservable = Observable.fromArray(jsonArray);

        RecyclerView recyclerView = view.findViewById(R.id.gameReviewsFeed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DataClsAdapter<Review, ReviewVH> dataClsAdapter = new DataClsAdapter<>(
                this,
                Review.class,
                getActivity(),
                R.layout.user_game_review_item);
        recyclerView.setAdapter(dataClsAdapter);

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(Review.class, new HashMap<String, String>(){{
            put("gameId", String.valueOf(game.getId()));
        }}), Review.class, false)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main_app);

        bottomAppBar = requireActivity().findViewById(R.id.bottom_app_bar);
        //bottomAppBar.setCradleVerticalOffset(fabOffsetVisible);
        fab = requireActivity().findViewById(R.id.bottom_app_bar_fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(v -> {
            navController.navigate(R.id.addGameReviewFragment, getArguments());
            //bottomAppBar.setCradleVerticalOffset(fabOffsetInvisible);
            fab.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    public void onItemClick(Bundle contextBundle) {

    }
}