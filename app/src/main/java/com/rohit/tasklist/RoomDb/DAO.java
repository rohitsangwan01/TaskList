package com.rohit.tasklist.RoomDb;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    public void addNote(Notes notes);

    @Query("select * from Notes")
    public List<Notes> getNotes();

    @Delete
    public void DeleteNote(Notes notes);

    @Update
    public void UpdateNote(Notes notes);

}
