package com.piedcard.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.piedcard.model.Deck;

import java.util.List;

@Dao
public interface DeckDAO extends DAO<Deck> {
    @Query("SELECT * FROM deck WHERE id = :id")
    Deck get(long id);

    @Query("SELECT * FROM deck")
    List<Deck> getAll();

}
