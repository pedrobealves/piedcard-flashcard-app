package com.piedcard.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.activity.deck.DeckActivity;
import com.piedcard.activity.deck.FavoriteActivity;
import com.piedcard.activity.deck.InsertDeckActivity;
import com.piedcard.activity.deck.ShuffleCardActivity;
import com.piedcard.activity.pages.AboutActivity;
import com.piedcard.adapter.DeckAdapter;
import com.piedcard.database.DeckDatabase;
import com.piedcard.model.Deck;
import com.piedcard.dao.DeckDAO;
import com.piedcard.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setThemeMode();

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
                                dialog.setTitle(R.string.confirm_delete);
                                dialog.setMessage(getString(R.string.ask_confirm_delete) + deckSelected.getName() + " ?" );

                                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        DeckDAO tarefaDAO = DeckDatabase.getDatabase(getApplicationContext()).DeckDAO();
                                        if ( tarefaDAO.delete(deckSelected) > 0){

                                            loadDeck();
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
        DeckDAO tarefaDAO = DeckDatabase.getDatabase(getApplicationContext()).DeckDAO();
        deckList = tarefaDAO.getAll();

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

    private void setThemeMode() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.switch_mode); // This is the menu item that contains your switch
        Switch drawer_switch = (Switch) menuItem.getActionView().findViewById(R.id.switch_mode_item);

        SharedPreferences mPrefs =  PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        boolean isNightModeEnabled = mPrefs.getBoolean("NIGHT_MODE", false);

        drawer_switch.setChecked(isNightModeEnabled);

        if(isNightModeEnabled)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        drawer_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor myEditor = myPreferences.edit();
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getDelegate().setLocalNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                    myEditor.putBoolean("NIGHT_MODE", isChecked);
                    myEditor.apply();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity( intent );
        }

        if(id == R.id.fav){
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity( intent );
        }

        if(id == R.id.match){
            Intent intent = new Intent(MainActivity.this, ShuffleCardActivity.class);
            startActivity( intent );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
