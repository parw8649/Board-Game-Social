package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Game;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.DataClsVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.EventVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.GameVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;
import com.google.gson.JsonArray;


import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, DataClsAdapter.OnItemListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SearchFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView svSearchBar;
    private Spinner spSearch;
    private String searchOption;
    private RecyclerView rvGames;
    private RecyclerView rvEvents;
    private RecyclerView rvPeople;
    private CompositeDisposable disposables = new CompositeDisposable();
    private boolean gamesLoaded;
    private RetrofitClient retrofitClient;
    private DataClsAdapter<Game, GameVH> dataClsAdapterGames;
    private DataClsVM dataClsVM;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

        Utils.checkForUser(requireContext());
        Log.i(TAG, String.format("Search - User Id: %s", Utils.getUserId()));
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        svSearchBar = view.findViewById(R.id.svSearch);
        spSearch = view.findViewById(R.id.sp_search);
        rvGames = view.findViewById(R.id.rvGamesSearch);
        rvEvents = view.findViewById(R.id.rvEventsSearch);
        rvPeople = view.findViewById(R.id.rvPeopleSearch);
        searchOption = "Games";
        gamesLoaded = false;

        retrofitClient = RetrofitClient.getClient();
        dataClsVM = DataClsVM.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.search_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearch.setAdapter(adapter);
        spSearch.setOnItemSelectedListener(this);
//        btnSearch.setOnClickListener(view1 -> {
//            Toast a = Toast.makeText(this.getContext(), "Search" + searchOption, Toast.LENGTH_LONG);
//            a.show();
//            //search(retrofitClient);
//        });


        Observable<String> observableQueryText = Observable
                .create((ObservableOnSubscribe<String>) emitter -> svSearchBar.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(final String newText) {
                        if (!emitter.isDisposed()) {
                            if (!newText.isEmpty()) {
                                emitter.onNext(newText); // Pass the query to the emitter
                            }
                        }
                        return false;
                    }
                }))
                .debounce(500, TimeUnit.MILLISECONDS) // Apply Debounce() operator to limit requests
                .subscribeOn(AndroidSchedulers.mainThread());

        observableQueryText.subscribe(res -> {
            Log.d(TAG, "onNext: search query: " + res);
            // method for sending a request to the server
            //sendRequestToServer(s);
            requireActivity().runOnUiThread(()-> search(res));
//            search(res);
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        searchOption = adapterView.getItemAtPosition(pos).toString();
        //TODO: Figure out how to limit calls after called at least once

        switch (searchOption) {
            case "Games":
                Log.i(TAG, "Games Search");
                changeVisibility("Games");
                if (!gamesLoaded) {
                    rvGames.setLayoutManager(new LinearLayoutManager(this.getContext()));

                    dataClsAdapterGames = new DataClsAdapter<>(
                        this,
                        Game.class,
                        getActivity(),
                        R.layout.game_item);
                    rvGames.setAdapter(dataClsAdapterGames);

                    dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(Game.class, new HashMap<>()), Game.class, true)
                        .observe(getViewLifecycleOwner(), dataClsAdapterGames::addNewObjects);
                    gamesLoaded = true;
                }
                break;
            case "People":
                Log.i(TAG, "People Search");
                break;
            case "Events":
                Log.i(TAG, "Event Search");
                changeVisibility("Events");
                rvEvents.setLayoutManager(new LinearLayoutManager(this.getContext()));

                DataClsAdapter<Event, EventVH> dataClsAdapterEvents = new DataClsAdapter<>(
                        this,
                        Event.class,
                        getActivity(),
                        R.layout.event_item);
                rvEvents.setAdapter(dataClsAdapterEvents);

                dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(Event.class, new HashMap<>()), Event.class, true)
                        .observe(getViewLifecycleOwner(), dataClsAdapterEvents::addNewObjects);
                break;
            default:
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear(); // clear disposables
    }

    public void changeVisibility(String searchType) {
        switch (searchType) {
            case "Games":
                Log.i(TAG, "Games Visible");
                rvGames.setVisibility(View.VISIBLE);
                rvPeople.setVisibility(View.GONE);
                rvEvents.setVisibility(View.GONE);
                break;
            case "People":
                Log.i(TAG, "People Visible");
                rvGames.setVisibility(View.GONE);
                rvPeople.setVisibility(View.VISIBLE);
                rvEvents.setVisibility(View.GONE);
                break;
            case "Events":
                Log.i(TAG, "Event Visible");
                rvGames.setVisibility(View.GONE);
                rvPeople.setVisibility(View.GONE);
                rvEvents.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }


    }


    public void search(String searchQuery) {
//      TODO: validate query
        switch(searchOption) {
            case "Games":
                Log.i(TAG,"Games Search");
                Observable<JsonArray> gameQuery = retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
                    put("gameTitle", searchQuery);
                }}).mergeWith(retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
                    put("genre", searchQuery);
                }})).mergeWith(retrofitClient.getCall(Game.class, new HashMap<String, String>(){{
                    put("description", searchQuery);
                }})).scan((cumulativeJsonArray, newJsonArray) -> {
                    cumulativeJsonArray.addAll(newJsonArray);
                    return cumulativeJsonArray;
                });
                dataClsVM.getMediatorLiveData(gameQuery, Game.class, true)
                        .observe(getViewLifecycleOwner(), dataClsAdapterGames::addNewObjects);
                break;
            case "People":
                Log.i(TAG,"People Search");
                break;
            case "Events":
                Log.i(TAG,"Event Search");
                break;
            default:
                break;

        }
    }

    @Override
    public void onItemClick(Bundle contextBundle) {

    }
}


