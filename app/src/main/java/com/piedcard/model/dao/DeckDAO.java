package com.piedcard.model.dao;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.piedcard.model.Deck;
import com.piedcard.model.dao.interfaces.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeckDAO implements DAO<Deck> {

    private List<Deck> decks = new ArrayList<>();
    private static int count = 1;

    public DeckDAO(Context context) {

    }

    @Override
    public Deck get(long id) {
        for (Deck d : decks){
            if(d.getId().equals(id)){
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Deck> getAll() {
        return decks;
    }

    @Override
    public List<Deck> getAllById(long id) {
        return null;
    }

    @Override
    public int count(long id) {
        return decks.size();
    }

    @Override
    public boolean save(Deck deck) {
        deck.setId((long) count++);
        return decks.add(deck);
    }

    @Override
    public boolean update(Deck deck) {
        for (Deck d : decks){
            if(d.getId().equals(deck.getId())){
                d.setName(deck.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Deck deck) {
        return decks.remove(deck);
    }
}
