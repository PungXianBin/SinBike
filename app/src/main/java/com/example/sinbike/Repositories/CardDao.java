package com.example.sinbike.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sinbike.POJO.Card;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CardDao {

    @Insert(onConflict = REPLACE)
    void insert(Card card);

    @Query("SELECT * FROM card")
    LiveData<List<Card>> get();
}