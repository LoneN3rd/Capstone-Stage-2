package com.example.android.katsapp.database;


import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {

    private static DatabaseClient mInstance;
    private static final String DATABASE_NAME = "MyCatsDb.db";

    private BreedsDatabase breedsDatabase;

    private DatabaseClient(Context context) {
        breedsDatabase = Room.databaseBuilder(context, BreedsDatabase.class, DATABASE_NAME).build();
    }

    public static synchronized DatabaseClient getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mContext);
        }
        return mInstance;
    }

    public BreedsDatabase getAppDatabase() {
        return breedsDatabase;
    }
}
