package com.example.android.katsapp.ViewModels;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.katsapp.database.BreedsDatabase;

public class SingleFavViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    //Add member variables for the database and the entryId
    private final String mBreedId;
    private final BreedsDatabase mDb;

    public SingleFavViewModelFactory(BreedsDatabase database, String breedId) {
        mBreedId = breedId;
        mDb = database;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new SingleFavViewModel(mDb, mBreedId);
    }
}
