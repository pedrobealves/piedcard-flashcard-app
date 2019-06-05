package com.piedcard.activity.deck;

import com.piedcard.dao.CardDAO;
import com.piedcard.model.Card;

import java.util.List;

public class StudyActivity extends TemplateCardList {

    @Override
    public List<Card> getCards(CardDAO cardDAO) {
        return cardDAO.getAllByDeck(getDeckActual().getId());
    }
}
