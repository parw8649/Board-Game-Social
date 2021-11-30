package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.Commons.Utils;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter.OnItemListener;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.EventVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;

import java.util.HashMap;

public class EventsFragment extends Fragment implements OnItemListener {

    public static final String TAG = "EventsFragment";

    private RecyclerView recyclerView;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utils.checkForUser(requireContext());
        Log.i(TAG, String.format("Events - User Id: %s", Utils.getUserId()));

        recyclerView = view.findViewById(R.id.eventFeed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DataClsAdapter<Event, EventVH> dataClsAdapter = new DataClsAdapter<>(
                this,
                Event.class,
                getActivity(),
                R.layout.event_item);
        recyclerView.setAdapter(dataClsAdapter);

        /*RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
        TextView tvEventDescription = childViewHolder.itemView.findViewById(R.id.cd_view_event_description);
        tvEventDescription.setVisibility(View.GONE);*/

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(Event.class, new HashMap<>()), Event.class)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);
    }

    @Override
    public void onDestroyView() { super.onDestroyView(); }

    @Override
    public void onItemClick(Bundle contextBundle) {

        DataClsAdapter<Event, EventVH> dataClsAdapter = (DataClsAdapter<Event, EventVH>) recyclerView.getAdapter();
        assert dataClsAdapter != null;

        NavHostFragment.findNavController(EventsFragment.this)
                .navigate(R.id.action_EventsFragment_to_singleEventFragment, contextBundle);
    }

}