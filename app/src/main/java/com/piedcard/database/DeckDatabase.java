package com.piedcard.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.piedcard.dao.CardDAO;
import com.piedcard.dao.DeckDAO;
import com.piedcard.model.Card;
import com.piedcard.model.Deck;

@Database(entities = {Deck.class, Card.class}, version = 1)
public abstract class DeckDatabase extends RoomDatabase {

    public abstract DeckDAO DeckDAO();
    public abstract CardDAO CardDAO();

    private static DeckDatabase instance;

    public static DeckDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (DeckDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            DeckDatabase.class,
                            "deck.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
