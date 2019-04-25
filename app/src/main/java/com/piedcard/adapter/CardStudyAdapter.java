package com.piedcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piedcard.R;
import com.piedcard.model.Card;
import com.piedcard.singleton.DaoSingletonFactory;

import java.util.List;

public class CardStudyAdapter extends RecyclerView.Adapter<CardStudyAdapter.MyViewHolder> {

    private List<Card> cardList;
    private Context context;

    public CardStudyAdapter(List<Card> cards) {
        this.cardList = cards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.card_study_detail, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Card card = cardList.get(position);
        int count = DaoSingletonFactory.getCardInstance(context).count(card.getId());
        holder.front.setText( card.getFront() );
        Log.i("cardStudyAdapter", card.getFront() );

    }

    @Override
    public int getItemCount() {
        return this.cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView front;

        public MyViewHolder(View itemView) {
            super(itemView);

            front = itemView.findViewById(R.id.textFront);
        }
    }

}
