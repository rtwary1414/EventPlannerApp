package com.example.eventplannerapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.eventplannerapp.dao.EventDao;
import com.example.eventplannerapp.model.Event;

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EventDao eventDao();
}

/* This is the Room database class. It connects the Event entity and the DAO
together. In simple words, this class creates the local database and gives
access to event operations.”
 */