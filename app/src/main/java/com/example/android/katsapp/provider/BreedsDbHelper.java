package com.example.android.katsapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.katsapp.provider.BreedsContract.BreedsEntry;

public class BreedsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CatsDb.db";
    private static final  int VERSION = 1;

    public BreedsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE = "CREATE TABLE " + BreedsEntry.TABLE_NAME + " ("+
                BreedsEntry._ID + " INTEGER PRIMARY KEY, " +
                BreedsEntry.COLUMN_ID + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_BREED_NAME + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_ORIGIN + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_COUNTRY_CODE + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_HYPO_ALLERGENIC + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_TEMPERAMENT + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_AFFECTION + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_ADAPTABILITY + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_CHILD_FRIENDLY + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_DOG_FRIENDLY + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_ENERGY_LEVEL + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_GROOMING + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_HEALTH_ISSUES + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_INTELLIGENCE + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_SHEDDING_LEVEL + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_SOCIAL_NEEDS + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_STRANGER_FRIENDLY + " TEXT NOT NULL, " +
                BreedsEntry.COLUMN_WIKIPEDIA + " TEXT NULL);";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
