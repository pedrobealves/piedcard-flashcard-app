package com.piedcard.model.dao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.piedcard.helper.DbHelper;
import com.piedcard.model.Deck;
import com.piedcard.model.dao.interfaces.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeckDAOSQLite implements DAO<Deck> {

    private SQLiteDatabase wt;
    private SQLiteDatabase rd;

    public DeckDAOSQLite(Context context) {
        DbHelper db = new DbHelper( context );
        wt = db.getWritableDatabase();
        rd = db.getReadableDatabase();
    }

    @Override
    public boolean save(Deck deck) {

        ContentValues cv = new ContentValues();
        cv.put("name", deck.getName() );

        try {
            wt.insert(DbHelper.TABLE_DECK, null, cv );
            Log.i("INFO", "Deck salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao insert deck " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Deck deck) {

        ContentValues cv = new ContentValues();
        cv.put("name", deck.getName() );

        try {
            String[] args = {deck.getId().toString()};
            wt.update(DbHelper.TABLE_DECK, cv, "id=?", args );
            Log.i("INFO", "Deck atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizada deck " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Deck deck) {

        try {
            String[] args = { deck.getId().toString() };
            wt.delete(DbHelper.TABLE_DECK, "id=?", args );
            Log.i("INFO", "Deck removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover deck " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public Deck get(long id) {

        String sql = "SELECT * FROM " + DbHelper.TABLE_DECK + " WHERE id=? ;";
        Cursor c = rd.rawQuery(sql,  new String[]{Long.toString(id)});

        if ( c.moveToNext() ){

            Long id_deck = c.getLong( c.getColumnIndex("id") );
            String deckName = c.getString( c.getColumnIndex("name") );

            Deck d = new Deck(id_deck, deckName );

            Log.i("tarefaDao", d.getName() );

            return d;
        }

        return null;
    }

    @Override
    public List<Deck> getAll() {

        List<Deck> decks = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABLE_DECK + " ;";
        Cursor c = rd.rawQuery(sql, null);

        while ( c.moveToNext() ){


            Long id = c.getLong( c.getColumnIndex("id") );
            String deckName = c.getString( c.getColumnIndex("name") );

            Deck deck = new Deck(id, deckName);

            decks.add(deck);
            Log.i("tarefaDao", deck.getName() );
        }

        return decks;

    }

    @Override
    public List<Deck> getAllById(long id) {
        return null;
    }

    @Override
    public int count(long id) {
        return 0;
    }
}
