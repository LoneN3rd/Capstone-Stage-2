package com.example.android.katsapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoriteBreeds.class}, version = 2, exportSchema = false)
public abstract class BreedsDatabase extends RoomDatabase {

    private static final String LOG_TAG = BreedsDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MyCatsDb.db";
    private static BreedsDatabase sInstance;

    public static BreedsDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BreedsDatabase.class, BreedsDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract BreedsDao breedsDao();

}
