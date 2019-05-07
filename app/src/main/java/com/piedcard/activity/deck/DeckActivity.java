package com.piedcard.activity.deck;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.activity.card.InsertCardActivity;
import com.piedcard.model.Deck;
import com.piedcard.model.dao.interfaces.DAO;
import com.piedcard.model.singleton.DaoSingletonFactory;

public class DeckActivity extends AppCompatActivity {

    private TextView title;
    private TextView countCards;
    private Deck deckActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title_deck);
        countCards = findViewById(R.id.count_cards);

        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");
        System.out.println(deckActual.getId());
    }

    public void loadDeck() {
        if ( deckActual != null ){
            DAO deckDAO = (DAO) DaoSingletonFactory.getDeckInstance(getApplicationContext());
            DAO cardDAO = (DAO) DaoSingletonFactory.getCardInstance(getApplicationContext());
            deckActual = (Deck) deckDAO.get(deckActual.getId());
            title.setText( deckActual.getName() );
            countCards.setText(String.valueOf(cardDAO.count(deckActual.getId())) + " " + getString(R.string.cards));
        }
    }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
    protected void onStart() {
        loadDeck();
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(DeckActivity.this, InsertDeckActivity.class);
            intent.putExtra("deckSelected", deckActual);
            startActivity( intent );
        }

        if (id == R.id.action_delete) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(DeckActivity.this);

            //Configura título e mensagem
            dialog.setTitle(R.string.confirm_delete);
            dialog.setMessage(getString(R.string.ask_confirm_delete) + deckActual.getName() + " ?" );

            dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DAO tarefaDAO =  (DAO) DaoSingletonFactory.getDeckInstance(getApplicationContext());
                    if ( tarefaDAO.delete(deckActual) ){

                        finish();
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.sucess_delete_list),
                                Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.error_delete_list),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialog.setNegativeButton(R.string.no, null );

            //Exibir dialog
            dialog.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void insertCard(View view){
        Intent intent = new Intent(DeckActivity.this, InsertCardActivity.class);
        intent.putExtra("deckSelected", deckActual);
        startActivity( intent );
    }

    public void studyList(View view){
        Intent intent = new Intent(DeckActivity.this, StudyActivity.class);
        intent.putExtra("deckSelected", deckActual);
        startActivity( intent );
    }

}
