package com.piedcard.model.dao;

import android.content.Context;

import com.piedcard.model.Card;
import com.piedcard.model.dao.interfaces.DAO;

import java.util.ArrayList;
import java.util.List;

public class CardDAO implements DAO<Card> {

    private List<Card> cards = new ArrayList<>();
    private static int count = 1;

    public CardDAO(Context context) {
    }

    @Override
    public Card get(long id) {
        for (Card d : cards){
            if(d.getId().equals(id)){
                return d;
            }
        }
        return null;    }

    @Override
    public List<Card> getAll() {
        return cards;
    }

    @Override
    public List<Card> getAllById(long id) {
        List<Card> c = new ArrayList<>();
        for (Card d : cards){
            if(d.getId_deck().equals(id)){
                c.add(d);
            }
        }
        return c;
    }

    @Override
    public int count(long id) {
        List<Card> c = new ArrayList<>();
        for (Card d : cards){
            if(d.getId_deck().equals(id)){
                c.add(d);
            }
        }
        return c.size();
    }

    @Override
    public boolean save(Card card) {
        card.setId((long) count++);
        return cards.add(card);
    }

    @Override
    public boolean update(Card card) {
        for (Card d : cards){
            if(d.getId().equals(card.getId())){
                d.setFront(card.getFront());
                d.setBack(card.getBack());
                return true;
            }
        }
        return false;      }

    @Override
    public boolean delete(Card card) {
        return cards.remove(card);
    }
}
