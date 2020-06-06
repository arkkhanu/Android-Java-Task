package com.ark.roomdb.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BioDao {
    @Insert
    void insertData(Bio bio);

    @Query("Select * from bio where regNo = :regNo_")
    Bio getsearched(int regNo_);

    @Update()
    void updateBio(Bio bio);

    @Query("Delete from bio where regNo = :regNo_")
    void deleteBio(int regNo_);
}
