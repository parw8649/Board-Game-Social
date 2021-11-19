package com.example.boardgamesocial.MainApp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boardgamesocial.R;
import com.example.boardgamesocial.databinding.FragmentEventsBinding;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.eventFeed_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //recyclerView.setAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}