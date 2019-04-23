package com.example.android.katsapp.ViewModels;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.katsapp.database.BreedsDatabase;
import com.example.android.katsapp.database.FavoriteBreeds;

public class SingleFavViewModel extends ViewModel {

    private LiveData<FavoriteBreeds> favorite;

    public SingleFavViewModel(BreedsDatabase database, String breedId) {
        favorite = database.breedsDao().getBreedById(breedId);
    }

    // Create a getter for the entry variable
    public LiveData<FavoriteBreeds> getFavorite() {
        return favorite;
    }
}
