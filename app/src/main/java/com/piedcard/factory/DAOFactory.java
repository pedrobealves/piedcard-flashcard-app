package com.piedcard.factory;

import android.content.Context;

import com.piedcard.model.dao.CardDAO;
import com.piedcard.model.dao.DeckDAO;
import com.piedcard.model.dao.database.CardDAOSQLite;
import com.piedcard.model.dao.database.DeckDAOSQLite;
import com.piedcard.model.dao.interfaces.DAO;

public abstract class DAOFactory {
    public static final int DECK = 1;
    public static final int CARD = 2;

    public static DAO getDAOFactory(int aux, Context context){
        switch(aux){
            case 1:
                return new DeckDAOSQLite(context);
            case 2:
                return new CardDAOSQLite(context);
            default:
                return null;
        }
    }
}