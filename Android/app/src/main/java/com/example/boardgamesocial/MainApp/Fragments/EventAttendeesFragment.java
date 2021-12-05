package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataClasses.Relationships.EventToUser;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter.OnItemListener;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.EventVH;
import com.example.boardgamesocial.DataViews.Adapters.ViewHolders.RelationshipVH.EventToUserVH;
import com.example.boardgamesocial.DataViews.DataClsVM;
import com.example.boardgamesocial.R;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventAttendeesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventAttendeesFragment extends Fragment implements OnItemListener {

    public static final String TAG = "EventAttendeesFragment";

    private Event event;

    private List<EventToUser> eventToUserList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventAttendeesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventAttendeesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventAttendeesFragment newInstance(String param1, String param2) {
        EventAttendeesFragment fragment = new EventAttendeesFragment();
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
        return inflater.inflate(R.layout.fragment_event_attendees, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Retrieve the value
        assert getArguments() != null;
        event = (Event) getArguments().getSerializable(EventVH.EVENT_KEY);

        /*//Fetching userIds from EventToUser based on specified event
        RetrofitClient.getClient().getCall(EventToUser.class, new HashMap<String, String>() {{
            put("eventId", event.getId().toString());
        }}).blockingSubscribe(usersInEventJson -> {
            eventToUserList = getObjectList(usersInEventJson, EventToUser.class);
        });

        JsonArray jsonArray = new JsonArray();

        //Fetching user details based on users in the event
        eventToUserList.forEach(eventToUser -> {
            RetrofitClient.getClient().getCall(User.class, new HashMap<String, String>() {{
                put("id", eventToUser.getUserId().toString());
            }}).blockingSubscribe(j -> {
                if(!j.isEmpty())
                    jsonArray.add(j.get(0));
            });
        });

        Log.i(TAG, "Attendees: " + jsonArray);
        TextView tvAttendees = view.findViewById(R.id.tv_attendees);
        tvAttendees.setText(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(jsonArray.toString())));*/

        //Observable<JsonArray> arrayObservable = Observable.fromArray(jsonArray);

        RecyclerView recyclerView = view.findViewById(R.id.attendeesFeed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        DataClsAdapter<EventToUser, EventToUserVH> dataClsAdapter = new DataClsAdapter<>(
                this,
                EventToUser.class,
                getActivity(),
                R.layout.user_item);
        recyclerView.setAdapter(dataClsAdapter);

        DataClsVM dataClsVM = DataClsVM.getInstance();
        dataClsVM.getMediatorLiveData(RetrofitClient.getClient().getCall(EventToUser.class, new HashMap<String, String>(){{
            put("eventId", String.valueOf(event.getId()));
        }}), EventToUser.class, true)
                .observe(getViewLifecycleOwner(), dataClsAdapter::addNewObjects);
    }

    @Override
    public void onItemClick(Bundle contextBundle) {

    }
}