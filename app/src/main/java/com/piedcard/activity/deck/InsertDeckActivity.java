package com.piedcard.activity.deck;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.dao.DeckDAO;
import com.piedcard.database.DeckDatabase;
import com.piedcard.model.Deck;
import com.piedcard.dao.DAO;

import java.util.ArrayList;

public class InsertDeckActivity extends AppCompatActivity {

    private TextInputEditText editDeck;
    private Deck deckActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_deck);

        editDeck = findViewById(R.id.textTarefa);

        //Recuperar DECK, caso seja edição
        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");

        //Configurar DECK na caixa de texto
        if (deckActual != null) {
            editDeck.setText(deckActual.getName());
            editDeck.setSelection(editDeck.getText().length());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insert_deck, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:

                DeckDAO deckDAO = DeckDatabase.getDatabase(getApplicationContext()).DeckDAO();

                if (deckActual != null) {//edicao
                    String nomeTarefa = editDeck.getText().toString();

                    if (validateData()) {

                        Deck deck = new Deck();
                        deck.setId(deckActual.getId());
                        deck.setName(nomeTarefa);

                        //update no banco de dados
                        if (deckDAO.update(deck) > 0) {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.sucess_update_list),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.error_update_list),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {//insert

                    String nomeTarefa = editDeck.getText().toString();
                    if (validateData()) {
                        Deck deck = new Deck();
                        deck.setName(nomeTarefa);

                        if (deckDAO.save(deck) > 0) {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.sucess_insert_list),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.error_insert_list),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }

                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateData() {
        String listName = editDeck.getText().toString();

        if (listName.isEmpty()) {
            this.editDeck.setError(getString(R.string.empty_name_list));
            return false;
        }

        ArrayList<Deck> decks = (ArrayList<Deck>) DeckDatabase.getDatabase(getApplicationContext()).DeckDAO().getAll();
        for (Deck d : decks) {
            if (d.getName().equalsIgnoreCase(listName)) {
                this.editDeck.setError(getString(R.string.exists_name_list));
                return false;
            }


        }

        return true;
    }

}
