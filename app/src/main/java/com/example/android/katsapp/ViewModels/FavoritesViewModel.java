package com.example.android.katsapp.ViewModels;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.katsapp.database.BreedsDatabase;
import com.example.android.katsapp.database.FavoriteBreeds;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String LOG_TAG = FavoritesViewModel.class.getSimpleName();

    private LiveData<List<FavoriteBreeds>> favorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        BreedsDatabase database = BreedsDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Actively retrieving the tasks from the DataBase");
        favorites = database.breedsDao().getAll();

    }

    public LiveData<List<FavoriteBreeds>> getFavorites() {
        return favorites;
    }
}
