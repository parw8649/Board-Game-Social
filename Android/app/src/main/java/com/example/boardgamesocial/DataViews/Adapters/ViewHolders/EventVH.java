package com.example.boardgamesocial.DataViews.Adapters.ViewHolders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.Event;
import com.example.boardgamesocial.DataViews.Adapters.DataClsAdapter;
import com.example.boardgamesocial.R;

import java.util.HashMap;

public class EventVH extends DataClsVH<Event> {
    public static final String EVENT_KEY = "event";

    private final TextView tvEventName;
    private final TextView tvEventDateTime;
    private final TextView tvEventDescription;
    private final ImageButton btnDeleteEvent;

    public EventVH(View eventView, DataClsAdapter.OnItemListener onItemListener) {
        super(eventView, onItemListener);
        tvEventName = eventView.findViewById(R.id.tv_event_name);
        tvEventDateTime = eventView.findViewById(R.id.tv_event_date_and_time);
        tvEventDescription = eventView.findViewById(R.id.cd_view_event_description);
        btnDeleteEvent = eventView.findViewById(R.id.btn_delete_event);
    }

    @Override
    public void toggleVisibility(int visibility){
        tvEventName.setVisibility(visibility);
        tvEventDateTime.setVisibility(visibility);
        tvEventDescription.setVisibility(visibility);
        btnDeleteEvent.setVisibility(visibility);
    }

    @Override
    public void onBind(Activity activity, Event event) {
        toggleVisibility(View.INVISIBLE);
        contextBundle.putSerializable(EVENT_KEY, event);
        /*retrofitClient.getCall(User.class, new HashMap<String, String>(){{
            put("id", String.valueOf(event.getHostUserId()));
        }}).subscribe(jsonArray -> {
            List<User> users = getObjectList(jsonArray, User.class);
            if (users.size() > 1) {
                new Exception("Got many Users on primary key: " + users).printStackTrace();
            } else {
                User user = users.get(0);*/
                activity.runOnUiThread(()->{
                    toggleVisibility(View.VISIBLE);
                    tvEventName.setText(event.getName());
                    tvEventDateTime.setText(event.getDateTime().toString());
                    tvEventDescription.setText(event.getDescription());
                });
                btnDeleteEvent.setOnClickListener(view -> retrofitClient.deleteCall(Event.class, new HashMap<String, String>() {{
                    put("id", String.valueOf(event.getId()));
                }}).subscribe());
            }
       // });
    //}
}
