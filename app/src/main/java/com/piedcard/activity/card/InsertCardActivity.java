package com.piedcard.activity.card;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.model.Card;
import com.piedcard.model.Deck;
import com.piedcard.model.dao.CardDAO;

public class InsertCardActivity extends AppCompatActivity {

    private TextInputEditText term;
    private TextInputEditText def;
    private CardView cardView;
    private Card cardActual;
    private Deck deckActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_card);

        term = findViewById(R.id.termText);
        def = findViewById(R.id.defText);
        cardView = findViewById(R.id.cardView);


        //Recuperar DECK, caso seja edição
        cardActual = (Card) getIntent().getSerializableExtra("cardSelected");
        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");

        //Configurar DECK na caixa de texto
        if ( cardActual != null ){
            term.setText( cardActual.getFront() );
            def.setText( cardActual.getBack() );
        }

/*        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cardView.setElevation(16);
            }
        });

        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setElevation(16);
            }
        });*/
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

                CardDAO cardDAO = new CardDAO( getApplicationContext() );

                if ( cardActual != null ){//edicao

                    String termText = term.getText().toString();
                    String defText = def.getText().toString();
                    if ( !termText.isEmpty() && !defText.isEmpty()){

                        Card card = new Card(cardActual.getId(), termText, defText, cardActual.getId_deck());

                        //update no banco de dados
                        if ( cardDAO.update(card) ){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao update card!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao update card!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {//insert

                    String termText = term.getText().toString();
                    String defText = def.getText().toString();
                    if ( !termText.isEmpty() && !defText.isEmpty() ){
                        Card card = new Card(termText, defText, deckActual.getId());

                        if ( cardDAO.insert(card) ){
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
