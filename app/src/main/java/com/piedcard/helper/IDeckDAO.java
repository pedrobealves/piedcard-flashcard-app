package com.piedcard.helper;

import com.piedcard.model.Deck;

import java.util.List;

public interface IDeckDAO {

    public boolean insert(Deck deck);
    public boolean update(Deck deck);
    public boolean delete(Deck deck);
    public List<Deck> list();

}
