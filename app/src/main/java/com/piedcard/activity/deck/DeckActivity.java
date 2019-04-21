package com.piedcard.activity.deck;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.helper.DeckDAO;
import com.piedcard.model.Deck;

public class DeckActivity extends AppCompatActivity {

    private TextView title;
    private Deck deckActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title_deck);

        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");

    }

    public void loadDeck() {
        if ( deckActual != null ){
            DeckDAO deckDAO = new DeckDAO( getApplicationContext() );
            deckActual = deckDAO.read(deckActual);
            title.setText( deckActual.getName() );
        }
    }

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
            dialog.setTitle("Confirmar exclusão");
            dialog.setMessage("Deseja excluir a tarefa: " + deckActual.getName() + " ?" );

            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DeckDAO tarefaDAO = new DeckDAO(getApplicationContext());
                    if ( tarefaDAO.delete(deckActual) ){

                        finish();
                        Toast.makeText(getApplicationContext(),
                                "Sucesso ao excluir lista!",
                                Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Erro ao excluir lista!",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialog.setNegativeButton("Não", null );

            //Exibir dialog
            dialog.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

}
