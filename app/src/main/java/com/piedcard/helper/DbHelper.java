package com.piedcard.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DB_NAME = "DB_PIED_CARDS";
    public static String TABLE_DECK = "deck";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_DECK
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL ); ";

        try {
            db.execSQL( sql );
            Log.i("INFO DB", "Sucesso ao criar a tabela" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_DECK + " ;" ;

        try {
            db.execSQL( sql );
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao update App" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao update App" + e.getMessage() );
        }

    }

}
