package com.piedcard.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.piedcard.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class DeckDAO implements IDeckDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public DeckDAO(Context context) {
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean insert(Deck deck) {

        ContentValues cv = new ContentValues();
        cv.put("name", deck.getName() );

        try {
            escreve.insert(DbHelper.TABLE_DECK, null, cv );
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
            escreve.update(DbHelper.TABLE_DECK, cv, "id=?", args );
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
            escreve.delete(DbHelper.TABLE_DECK, "id=?", args );
            Log.i("INFO", "Deck removida com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover deck " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public List<Deck> list() {

        List<Deck> decks = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABLE_DECK + " ;";
        Cursor c = le.rawQuery(sql, null);

        while ( c.moveToNext() ){

            Deck deck = new Deck();

            Long id = c.getLong( c.getColumnIndex("id") );
            String deckName = c.getString( c.getColumnIndex("name") );

            deck.setId( id );
            deck.setName( deckName );

            decks.add(deck);
            Log.i("tarefaDao", deck.getName() );
        }

        return decks;

    }
}
