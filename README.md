# Personal Event Planner App (Android)

## Overview
This is a Personal Event Planner Android application that allows users to manage their daily events such as meetings, trips, and appointments. 
The app supports full CRUD operations and stores data locally using Room Database.

## Features

- Add new events (Title, Category, Location, Date & Time)
- View all events in a list (sorted by date)
- Update existing events
- Delete events (long press)
- Data persistence using Room Database
- Navigation using Fragments and Bottom Navigation
- Input validation (empty fields & past date restriction)

## App Screens

### Event List Screen
- Displays all saved events
- Uses RecyclerView
- Sorted by date (ascending)

### Add / Update Event Screen
- Input fields for event details
- Supports both adding and editing events

##  Application Flow

1. User enters event details in Add Event screen  
2. Input is validated (no empty title, no past date)  
3. Data is saved in Room Database  
4. Event List screen fetches data from database  
5. RecyclerView displays all events  
6. User can tap event to update or long press to delete  

## Project Structure

- `Event.java` - Model class (Entity for Room)
- `EventDao.java` - Database operations (CRUD)
- `AppDatabase.java` - Room database configuration
- `MainActivity.java` - Fragment container and navigation
- `event_list_fragment.java` - Displays event list
- `add_event_fragment.java` - Handles add/update logic
- `EventAdapter.java` - Binds data to RecyclerView

## Validation Implemented

- Title cannot be empty
- Date/Time cannot be empty
- Past dates are not allowed
- Toast messages for user feedback
