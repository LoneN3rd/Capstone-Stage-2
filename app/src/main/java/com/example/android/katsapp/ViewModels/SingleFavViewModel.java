package com.example.android.katsapp.ViewModels;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.katsapp.database.BreedsDatabase;
import com.example.android.katsapp.database.FavoriteBreeds;

public class SingleFavViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = FavoritesViewModel.class.getSimpleName();
    private LiveData<FavoriteBreeds> favorite;

    /*
    Create a constructor where you call loadEntryById of the entryDao to initialize
    the favorite variable
     */
    public SingleFavViewModel(BreedsDatabase database, String breedId) {
        favorite = database.breedsDao().getBreedById(breedId);
    }

    // Create a getter for the favorite variable
    public LiveData<FavoriteBreeds> getFavorite() {
        return favorite;
    }
}
