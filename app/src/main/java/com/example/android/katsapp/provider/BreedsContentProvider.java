package com.example.android.katsapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.katsapp.provider.BreedsContract.BreedsEntry.TABLE_NAME;

public class BreedsContentProvider extends ContentProvider {

    public static final int BREED = 101;

    private static final UriMatcher sUrimatcher = buidUriMatcher();
    public static final String ACTION_DATA_UPDATED = "com.example.android.katsapp.ACTION_DATA_UPDATED";

    public static UriMatcher buidUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(BreedsContract.AUTHORITY, BreedsContract.PATH_BREEDS, BREED);

        return uriMatcher;
    }

    private BreedsDbHelper mBreedsDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mBreedsDbHelper = new BreedsDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase sqLiteDatabase = mBreedsDbHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        Cursor retCursor;

        switch (match){
            case BREED:
                retCursor = sqLiteDatabase.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Query: Unknown uri: " + uri);

        }

        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mBreedsDbHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        Uri returnUri;

        switch (match){
            case BREED:
                long id= db.insert(TABLE_NAME,  null, contentValues);

                if (id > 0){
                    returnUri = ContentUris.withAppendedId(BreedsContract.BreedsEntry.CONTENT_URI, id);

                }else throw new SQLException("Failed to insert row into: " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Insert: Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
