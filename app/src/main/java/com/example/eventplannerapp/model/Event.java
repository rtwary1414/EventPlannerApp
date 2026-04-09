package com.example.eventplannerapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String category;
    public String location;
    public long datetime;
}

/* This is the Event model class. It represents one event object in the app.
It contains fields like id, title, category, location, and datetime.
Since this class is marked with @Entity, Room uses it to create the events
table in the database.”
 */