package com.piedcard.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.piedcard.model.Card;

import java.util.List;

@Dao
public interface CardDAO extends DAO<Card> {

    @Query("SELECT * FROM card WHERE id = :id")
    Card get(long id);

    @Query("SELECT * FROM card")
    List<Card> getAll();

    @Query("SELECT * FROM card WHERE id_deck = :id")
    List<Card> getAllByDeck(long id);

    @Query("SELECT count(*) FROM card WHERE id_deck = :id")
    int countByDeck(long id);

}
