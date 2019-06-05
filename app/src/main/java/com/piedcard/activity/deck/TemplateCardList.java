package com.piedcard.activity.deck;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.piedcard.R;
import com.piedcard.activity.card.InsertCardActivity;
import com.piedcard.adapter.CardStudyAdapter;
import com.piedcard.dao.CardDAO;
import com.piedcard.database.DeckDatabase;
import com.piedcard.model.Card;
import com.piedcard.model.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TemplateCardList extends AppCompatActivity {

    private List<Card> cardList = new ArrayList<>();
    private Deck deckActual;
    private CardStudyAdapter cardAdapter;
    private RecyclerView recyclerView;
    private TextView title;
    private TextView front;

    private ActionMode actionMode;
    private int        posicaoSelecionada = -1;
    private View       viewSelecionada;


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflate = actionMode.getMenuInflater();
            inflate.inflate(R.menu.menu_action, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_edit:
                    editCard();
                    actionMode.finish();
                    return true;

                case R.id.action_delete:
                    deleteCard();
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.WHITE);
            }

            viewSelecionada    = null;

            recyclerView.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        front = findViewById(R.id.textFront);

        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");
        Collections.shuffle(cardList);
    }

    private void deleteCard() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(TemplateCardList.this);

        //Configura título e mensagem
        dialog.setTitle(R.string.confirm_delete);
        dialog.setMessage(getString(R.string.ask_confirm_delete) + " ?" );

        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            Card card = cardList.get(posicaoSelecionada);
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CardDAO cardDAO =  DeckDatabase.getDatabase(getApplicationContext()).CardDAO();
                if ( cardDAO.delete(card) > 0 ) {
                    cardList.remove(posicaoSelecionada);
                    cardAdapter.notifyDataSetChanged();
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

    private void editCard() {
        Card card = cardList.get(posicaoSelecionada);
        Intent intent = new Intent(TemplateCardList.this, InsertCardActivity.class);
        intent.putExtra("cardSelected", card);
        startActivity( intent );
    }


    public void loadDeck(){

        cardList = getCards(DeckDatabase.getDatabase(getApplicationContext()).CardDAO());
        Collections.shuffle(cardList);

        /*
            Exibe lista de DECK no Recyclerview
        */

        //Configurar um adapter
        cardAdapter = new CardStudyAdapter(cardList);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(cardAdapter);
    }

    public void actionCard(View view, int position){
        posicaoSelecionada = position;
        view.setBackgroundColor(Color.LTGRAY);
        viewSelecionada = view;
        recyclerView.setEnabled(false);
        actionMode = startSupportActionMode(mActionModeCallback);
    }

    public void changeFaceCard(int position){
        Card card = cardList.get(position);
        card.setFace(!card.isFace());
        cardAdapter.notifyDataSetChanged();
    }

    public void setFavoriteCard(int position){
        Card card = cardList.get(position);
        card.setFavorite(!card.isFavorite());
        CardDAO cardDAO =  DeckDatabase.getDatabase(getApplicationContext()).CardDAO();
        if ( cardDAO.update(card) > 0 ) {
            cardAdapter.notifyDataSetChanged();
        }
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.cardRecyclerView);
        loadDeck();
        cardAdapter.setOnItemClickListener(new CardStudyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeFaceCard(position);
            }
            @Override
            public void onLongItemClick(View view, int position) {
                actionCard(view, position);
            }
            @Override
            public void onFavorite(int position) {
                setFavoriteCard(position);
            }
        });
    }

    public abstract List<Card> getCards(CardDAO cardDAO);

    @Override
    protected void onStart() {
        buildRecyclerView();
        super.onStart();
    }

    public Deck getDeckActual() {
        return deckActual;
    }
}
