package com.rohit.tasklist.RoomDb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Notes.class,version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract DAO dao();

}
