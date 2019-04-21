package com.piedcard.activity.deck;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.helper.DeckDAO;
import com.piedcard.model.Deck;

public class InsertDeckActivity extends AppCompatActivity {

    private TextInputEditText editDeck;
    private Deck deckActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editDeck = findViewById(R.id.textTarefa);

        //Recuperar DECK, caso seja edição
        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");

        //Configurar DECK na caixa de texto
        if ( deckActual != null ){
            editDeck.setText( deckActual.getName() );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.itemSalvar :
                //Executa açao para o item insert

                DeckDAO deckDAO = new DeckDAO( getApplicationContext() );

                if ( deckActual != null ){//edicao

                    String nomeTarefa = editDeck.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){

                        Deck deck = new Deck();
                        deck.setName( nomeTarefa );
                        deck.setId( deckActual.getId() );

                        //update no banco de dados
                        if ( deckDAO.update(deck) ){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao update deck!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao update deck!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {//insert

                    String nomeTarefa = editDeck.getText().toString();
                    if ( !nomeTarefa.isEmpty() ){
                        Deck deck = new Deck();
                        deck.setName( nomeTarefa );

                        if ( deckDAO.insert(deck) ){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao insert deck!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao insert deck!",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }

                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
