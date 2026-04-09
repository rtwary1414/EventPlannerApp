package com.example.eventplannerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplannerapp.adapter.EventAdapter;
import com.example.eventplannerapp.database.AppDatabase;
import com.example.eventplannerapp.model.Event;

import java.util.List;

public class event_list_fragment extends Fragment {

    RecyclerView recyclerView;
    AppDatabase db;

    public event_list_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_list_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = Room.databaseBuilder(requireContext(),
                        AppDatabase.class, "event-db")
                .allowMainThreadQueries()
                .build();

        List<Event> eventList = db.eventDao().getAllEvents();

        EventAdapter adapter = new EventAdapter(eventList, db, event -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, add_event_fragment.newInstance(event))
                    .commit();
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}

/* This fragment is responsible for reading all events from Room and
displaying them in the RecyclerView. It fetches the list using the DAO,
creates the adapter, and binds the data to the screen.
 */