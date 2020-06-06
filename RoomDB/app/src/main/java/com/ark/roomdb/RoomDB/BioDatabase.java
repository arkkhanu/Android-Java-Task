package com.ark.roomdb.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Bio.class}, version = 3, exportSchema = false)
public abstract class BioDatabase extends RoomDatabase {

    public abstract BioDao bioDao();

    private static BioDatabase INSTANCE;

    public static BioDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (BioDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), BioDatabase.class, "BioDatabase")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
