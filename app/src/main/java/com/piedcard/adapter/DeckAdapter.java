package com.piedcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piedcard.R;
import com.piedcard.model.Deck;

import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.MyViewHolder> {

    private List<Deck> listaDecks;

    public DeckAdapter(List<Deck> lista ) {
        this.listaDecks = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_tarefa_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Deck deck = listaDecks.get(position);
        holder.tarefa.setText( deck.getName() );
        Log.i("tarefaAdapter", deck.getName() );

    }

    @Override
    public int getItemCount() {
        return this.listaDecks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tarefa;

        public MyViewHolder(View itemView) {
            super(itemView);

            tarefa = itemView.findViewById(R.id.textTarefa);

        }
    }

}
