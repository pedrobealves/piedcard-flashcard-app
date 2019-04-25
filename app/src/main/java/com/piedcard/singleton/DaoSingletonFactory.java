package com.piedcard.singleton;

import android.content.Context;

import com.piedcard.factory.DAOFactory;
import com.piedcard.model.dao.interfaces.DAO;

public class DaoSingletonFactory {

    private static DAO cardDAO;
    private static DAO deckDAO;

    private DaoSingletonFactory() {
    }

    public static DAO getCardInstance(Context context)
    {
        if (cardDAO == null) {
            cardDAO = DAOFactory.getDAOFactory(DAOFactory.CARD, context);
        }

        return cardDAO;
    }

    public static DAO getDeckInstance(Context context)
    {
        if (deckDAO == null) {
            deckDAO = DAOFactory.getDAOFactory(DAOFactory.DECK, context);
        }

        return deckDAO;
    }
}
