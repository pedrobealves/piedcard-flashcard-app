package com.piedcard.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.activity.deck.DeckActivity;
import com.piedcard.activity.deck.InsertDeckActivity;
import com.piedcard.activity.settings.SettingsActivity;
import com.piedcard.adapter.DeckAdapter;
import com.piedcard.model.Deck;
import com.piedcard.util.RecyclerItemClickListener;
import com.piedcard.model.dao.DeckDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeckAdapter deckAdapter;
    private List<Deck> deckList = new ArrayList<>();
    private Deck deckSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurar recycler
        recyclerView = findViewById(R.id.recyclerView);

        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                Deck deckSelected = deckList.get( position );

                                //Envia DECK para tela adicionar tarefa
                                Intent intent = new Intent(MainActivity.this, DeckActivity.class);
                                intent.putExtra("deckSelected", deckSelected);

                                startActivity( intent );

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recupera DECK para delete
                                deckSelected = deckList.get( position );

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //Configura título e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa: " + deckSelected.getName() + " ?" );

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        DeckDAO tarefaDAO = new DeckDAO(getApplicationContext());
                                        if ( tarefaDAO.delete(deckSelected) ){

                                            loadDeck();
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

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertDeckActivity.class );
                startActivity( intent );
            }
        });
    }

    public void loadDeck(){

        //Listar tarefas
        DeckDAO tarefaDAO = new DeckDAO( getApplicationContext() );
        deckList = tarefaDAO.list();

        /*
            Exibe lista de DECK no Recyclerview
        */

        //Configurar um adapter
        deckAdapter = new DeckAdapter(deckList);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(deckAdapter);

    }

    @Override
    protected void onStart() {
        loadDeck();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity( intent );
        }

        return super.onOptionsItemSelected(item);
    }
}
