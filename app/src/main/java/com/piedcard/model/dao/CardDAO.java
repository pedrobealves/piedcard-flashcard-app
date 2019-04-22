package com.piedcard.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.piedcard.helper.DbHelper;
import com.piedcard.model.Card;
import com.piedcard.model.dao.interfaces.ICardDAO;

import java.util.ArrayList;
import java.util.List;

public class CardDAO implements ICardDAO {

    private SQLiteDatabase wt;
    private SQLiteDatabase rd;

    public CardDAO(Context context) {
        DbHelper db = new DbHelper( context );
        wt = db.getWritableDatabase();
        rd = db.getReadableDatabase();
    }

    @Override
    public boolean insert(Card card) {

        ContentValues cv = new ContentValues();
        cv.put("front", card.getFront() );
        cv.put("back", card.getBack() );
        cv.put("id_deck", card.getId_deck() );

        try {
            wt.insert(DbHelper.TABLE_CARD, null, cv );
            Log.i("INFO", "Carta salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar carta " + e.getMessage() );
            return false;
        }

        return true;    }

    @Override
    public boolean update(Card card) {

        ContentValues cv = new ContentValues();
        cv.put("front", card.getFront() );
        cv.put("back", card.getBack() );
        cv.put("id_deck", card.getId_deck() );

        try {
            String[] args = {card.getId().toString()};
            wt.update(DbHelper.TABLE_DECK, cv, "id=?", args );
            Log.i("INFO", "Carta atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizar carta " + e.getMessage() );
            return false;
        }

        return true;    }

    @Override
    public boolean delete(Card card) {

        try {
            String[] args = { card.getId().toString() };
            wt.delete(DbHelper.TABLE_CARD, "id=?", args );
            Log.i("INFO", "Carta removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover carta " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public Card read(long id) {

        Card card = new Card();

        String sql = "SELECT * FROM " + DbHelper.TABLE_CARD + " WHERE id=? ;";
        Cursor c = rd.rawQuery(sql,  new String[]{Long.toString(id)});

        if ( c.moveToNext() ){

            Long id_card = c.getLong( c.getColumnIndex("card.id") );
            String front = c.getString( c.getColumnIndex("front") );
            String back = c.getString( c.getColumnIndex("back") );
            Long id_deck = c.getLong( c.getColumnIndex("deck.id") );

            card.setId( id_card );
            card.setFront( front );
            card.setBack( back );
            card.setId_deck(id_deck);

            Log.i("tarefaDao", card.getFront() );

            return card;
        }

        return null;    }

    @Override
    public List<Card> list(long id_deck) {

        List<Card> cards = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABLE_CARD + " WHERE id_deck=? ;";
        Cursor c = rd.rawQuery(sql, new String[]{Long.toString(id_deck)});

        while ( c.moveToNext() ){

            Long id_card = c.getLong( c.getColumnIndex("card.id") );
            String front = c.getString( c.getColumnIndex("front") );
            String back = c.getString( c.getColumnIndex("back") );

            Card card = new Card();

            card.setId( id_card );
            card.setFront( front );
            card.setBack( back );
            card.setId_deck(id_deck);

            cards.add(card);
            Log.i("tarefaDao", card.getFront() );
        }

        return cards;
    }

    public int count(long id_deck){
        String sql = "SELECT count(id) FROM " + DbHelper.TABLE_CARD + " WHERE id_deck=? ;";
        Cursor c = rd.rawQuery(sql, new String[]{Long.toString(id_deck)});
        c.moveToFirst();
        return c.getInt(0);
    }
}
