package com.example.android.katsapp.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BreedsDao {

    @Query("SELECT * FROM breeds")
    List<FavoriteBreeds> getAll();

    @Query("SELECT * FROM breeds WHERE breed_id = :id")
    FavoriteBreeds getBreedById(String id);

    // insert single record
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBreed(FavoriteBreeds favoriteBreeds);

    @Query("DELETE FROM breeds WHERE breed_id = :id")
    void delete(String id);
}
