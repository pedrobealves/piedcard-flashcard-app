package com.piedcard.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.piedcard.R;
import com.piedcard.database.DeckDatabase;
import com.piedcard.model.Card;

import java.util.List;

public class CardStudyAdapter extends RecyclerView.Adapter<CardStudyAdapter.MyViewHolder> {

    private List<Card> cardList;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onLongItemClick(View view, int position);
        void onFavorite(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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

        return new MyViewHolder(itemLista, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Card card = cardList.get(position);
        holder.front.setText( card.isFace() ? card.getBack() : card.getFront());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(card.isFavorite()) {
                    holder.favorite.setAlpha(200);
                }
                else
                    holder.favorite.setAlpha(100);
            }
        Log.i("favorite:","is: " + card.isFavorite());
        Log.i("cardStudyAdapter", "Front: " + card.getFront() + " Back: " + card.getBack() );
    }

    @Override
    public int getItemCount() {
        return this.cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView front;
        ImageButton favorite;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            front = itemView.findViewById(R.id.textFront);
            favorite = itemView.findViewById(R.id.fav_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongItemClick(v, position);
                            return true;
                        }
                    }
                    return false;
                }
            });

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onFavorite(position);
                        }
                    }
                }
            });
        }
    }

}
