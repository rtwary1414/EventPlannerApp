package com.example.eventplannerapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.eventplannerapp.database.AppDatabase;
import com.example.eventplannerapp.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_event_fragment extends Fragment {

    EditText etTitle, etCategory, etLocation, etDateTime;
    Button btnSaveEvent;
    AppDatabase db;
    Event existingEvent = null;

    public add_event_fragment() {
    }

    public static add_event_fragment newInstance(Event event) {
        add_event_fragment fragment = new add_event_fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", event.id);
        bundle.putString("title", event.title);
        bundle.putString("category", event.category);
        bundle.putString("location", event.location);
        bundle.putLong("datetime", event.datetime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_event_fragment, container, false);

        etTitle = view.findViewById(R.id.etTitle);
        etCategory = view.findViewById(R.id.etCategory);
        etLocation = view.findViewById(R.id.etLocation);
        etDateTime = view.findViewById(R.id.etDateTime);
        btnSaveEvent = view.findViewById(R.id.btnSaveEvent);

        db = Room.databaseBuilder(requireContext(),
                        AppDatabase.class, "event-db")
                .allowMainThreadQueries()
                .build();

        if (getArguments() != null) {
            existingEvent = new Event();
            existingEvent.id = getArguments().getInt("id");
            existingEvent.title = getArguments().getString("title");
            existingEvent.category = getArguments().getString("category");
            existingEvent.location = getArguments().getString("location");
            existingEvent.datetime = getArguments().getLong("datetime");

            etTitle.setText(existingEvent.title);
            etCategory.setText(existingEvent.category);
            etLocation.setText(existingEvent.location);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
            etDateTime.setText(sdf.format(existingEvent.datetime));

            btnSaveEvent.setText("Update Event");
        }

        btnSaveEvent.setOnClickListener(v -> saveEvent());

        return view;
    }

    private void saveEvent() {
        String title = etTitle.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String dateTimeStr = etDateTime.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(dateTimeStr)) {
            Toast.makeText(getContext(), "Title and Date/Time are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        Date parsedDate;

        try {
            parsedDate = sdf.parse(dateTimeStr);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Enter date/time in correct format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (parsedDate == null) {
            Toast.makeText(getContext(), "Invalid date/time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (parsedDate.getTime() < System.currentTimeMillis()) {
            Toast.makeText(getContext(), "Date cannot be in the past", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingEvent == null) {
            Event event = new Event();
            event.title = title;
            event.category = category;
            event.location = location;
            event.datetime = parsedDate.getTime();
            db.eventDao().insert(event);
            Toast.makeText(getContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            existingEvent.title = title;
            existingEvent.category = category;
            existingEvent.location = location;
            existingEvent.datetime = parsedDate.getTime();
            db.eventDao().update(existingEvent);
            Toast.makeText(getContext(), "Event updated successfully", Toast.LENGTH_SHORT).show();
        }

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new event_list_fragment())
                .commit();
    }
}

/* This fragment handles both adding and updating events. It first takes
user input from the EditText fields. Then it validates the title and
date/time. If the input is valid, it either inserts a new event or updates
an existing one
 */