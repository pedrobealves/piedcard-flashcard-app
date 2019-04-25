package com.piedcard.activity.deck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.piedcard.R;
import com.piedcard.adapter.CardStudyAdapter;
import com.piedcard.model.Card;
import com.piedcard.model.Deck;
import com.piedcard.model.dao.CardDAO;
import com.piedcard.singleton.DaoSingletonFactory;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    private List<Card> cardList = new ArrayList<>();
    private Deck deckActual;
    private CardStudyAdapter cardAdapter;
    private RecyclerView recyclerView;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.cardRecyclerView);
        title = findViewById(R.id.titleDeckStudy);

        deckActual = (Deck) getIntent().getSerializableExtra("deckSelected");

        title.setText(deckActual.getName());
    }


    public void loadDeck(){

        //Listar tarefas
        CardDAO tarefaDAO = (CardDAO) DaoSingletonFactory.getCardInstance(getApplicationContext());
        cardList = tarefaDAO.getAllById(deckActual.getId());

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

    @Override
    protected void onStart() {
        loadDeck();
        super.onStart();
    }
}
