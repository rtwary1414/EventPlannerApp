package com.example.eventplannerapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eventplannerapp.model.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("SELECT * FROM events ORDER BY datetime ASC")
    List<Event> getAllEvents();
}

/* This is the DAO, which stands for Data Access Object. It contains the
database operations. Here I defined methods for insert, update, delete, and
fetching all events. So this class is the main bridge between the app and the
database.
 */