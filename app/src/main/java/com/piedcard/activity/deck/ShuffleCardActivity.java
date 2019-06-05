package com.piedcard.activity.deck;

import com.piedcard.dao.CardDAO;
import com.piedcard.model.Card;

import java.util.Collections;
import java.util.List;

public class ShuffleCardActivity extends TemplateCardList {
    @Override
    public List<Card> getCards(CardDAO cardDAO) {
        List<Card> cards = cardDAO.getAll();
        Collections.shuffle(cards);
        return cards.subList(0, cards.size() / 2);
    }
}
