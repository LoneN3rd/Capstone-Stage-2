package com.example.android.katsapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {FavoriteBreeds.class}, version = 1, exportSchema = false)
public abstract class BreedsDatabase extends RoomDatabase {

    public abstract BreedsDao breedsDao();

}
