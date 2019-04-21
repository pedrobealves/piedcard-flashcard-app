package com.piedcard.model.dao.interfaces;

import com.piedcard.model.Deck;

import java.util.List;

public interface IDeckDAO {

    boolean insert(Deck deck);
    boolean update(Deck deck);
    boolean delete(Deck deck);
    Deck read(long id);
    List<Deck> list();

}
