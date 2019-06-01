package com.piedcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piedcard.R;
import com.piedcard.database.DeckDatabase;
import com.piedcard.model.Deck;

import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.MyViewHolder> {

    private List<Deck> listaDecks;
    private Context context;

    public DeckAdapter(List<Deck> lista) {
        this.listaDecks = lista;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.deck_list_detail, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Deck deck = listaDecks.get(position);
        int count = DeckDatabase.getDatabase(context).CardDAO().countByDeck(deck.getId());
        holder.tarefa.setText( deck.getName() );
        holder.countCards.setText(String.valueOf(count) + " " + context.getString(R.string.cards));
        Log.i("deckAdapter", deck.getName() );

    }

    @Override
    public int getItemCount() {
        return this.listaDecks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tarefa;
        TextView countCards;

        public MyViewHolder(View itemView) {
            super(itemView);

            tarefa = itemView.findViewById(R.id.textTarefa);
            countCards = itemView.findViewById(R.id.sub_text);
        }
    }

}
