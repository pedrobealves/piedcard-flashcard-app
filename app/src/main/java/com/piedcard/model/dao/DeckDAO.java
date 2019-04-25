package com.piedcard.model.dao;

import android.content.Context;

import com.piedcard.model.Deck;
import com.piedcard.model.dao.interfaces.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeckDAO implements DAO<Deck> {

    private List<Deck> decks = new ArrayList<>();

    public DeckDAO(Context context) {
    }

    @Override
    public Deck get(long id) {
        return decks.get((int)id);
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
        deck.setId((long) decks.size());
        return decks.add(deck);
    }

    @Override
    public boolean update(Deck deck) {
        return true;
    }

    @Override
    public boolean delete(Deck deck) {
        return decks.remove(deck);
    }
}
