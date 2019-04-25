package com.piedcard.model.dao;

import android.content.Context;

import com.piedcard.model.Card;
import com.piedcard.model.dao.interfaces.DAO;

import java.util.ArrayList;
import java.util.List;

public class CardDAO implements DAO<Card> {

    private List<Card> cards = new ArrayList<>();

    public CardDAO(Context context) {
    }

    @Override
    public Card get(long id) {
        return cards.get( (int) id);
    }

    @Override
    public List<Card> getAll() {
        return cards;
    }

    @Override
    public List<Card> getAllById(long id) {
        List<Card> c = new ArrayList<>();
        for (Card d : cards){
            if(d.getId_deck() == id){
                c.add(d);
            }
        }
        return c;
    }

    @Override
    public int count(long id) {
        List<Card> c = new ArrayList<>();
        for (Card d : cards){
            if(d.getId_deck() == id){
                c.add(d);
            }
        }
        return c.size();
    }

    @Override
    public boolean save(Card card) {
        card.setId((long) cards.size());
        return cards.add(card);
    }

    @Override
    public boolean update(Card card) {
        return true;
    }

    @Override
    public boolean delete(Card card) {
        return cards.remove(card);
    }
}
