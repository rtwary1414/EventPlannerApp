package com.example.eventplannerapp.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplannerapp.R;
import com.example.eventplannerapp.database.AppDatabase;
import com.example.eventplannerapp.model.Event;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    private List<Event> eventList;
    private AppDatabase db;
    private OnEventClickListener listener;

    public EventAdapter(List<Event> eventList, AppDatabase db, OnEventClickListener listener) {
        this.eventList = eventList;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.tvTitle.setText(event.title);
        holder.tvCategory.setText("Category: " + event.category);
        holder.tvLocation.setText("Location: " + event.location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        holder.tvDateTime.setText("Date: " + sdf.format(event.datetime));

        holder.itemView.setOnClickListener(v -> listener.onEventClick(event));

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Event")
                    .setMessage("Do you want to delete this event?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.eventDao().delete(event);
                        eventList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvCategory, tvLocation, tvDateTime;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}

/* The adapter takes the list of events and shows each one inside the
RecyclerView row layout. It binds the event title, category, location, and
formatted date/time to the TextViews. It also handles click for update and
long click for delete.
 */