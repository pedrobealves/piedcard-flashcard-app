package com.piedcard.model.dao.interfaces;

import com.piedcard.model.Card;

import java.util.List;

public interface ICardDAO {
    boolean insert(Card card);
    boolean update(Card card);
    boolean delete(Card card);
    Card read(long id);
    List<Card> list(long id);
}
