package org.ribak.chess;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ribak.chesssdk.boards.BoardState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nribak on 03/03/2017.
 */

public class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.ViewHolder> {
    private Context context;
    private int resource;
    private List<String> movesWhite, movesBlack;
    private boolean whiteLast;
    public MovesAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
        this.movesWhite = new ArrayList<>();
        this.movesBlack = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String whiteMove = (position < movesWhite.size()) ? movesWhite.get(position) : "";
        String blackMove = (position < movesBlack.size()) ? movesBlack.get(position) : "";
        holder.textViewWhite.setText(whiteMove);
        holder.textViewBlack.setText(blackMove);
    }

    @Override
    public int getItemCount() {
        return Math.max(movesWhite.size(), movesBlack.size());
    }

    public void add(String move, boolean white, BoardState boardState) {

        if(move != null) {
            if(white)
                movesWhite.add(move + boardState.getStartSign());
            else
                movesBlack.add(move + boardState.getStartSign());
            whiteLast = white;
            notifyDataSetChanged();
        }
    }

    public void removeLast() {
        if(getItemCount() > 0) {
            if(whiteLast)
                movesWhite.remove(movesWhite.size() - 1);
            else
                movesBlack.remove(movesBlack.size() - 1);
            whiteLast = !whiteLast;
            notifyDataSetChanged();
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWhite, textViewBlack;

        ViewHolder(View itemView) {
            super(itemView);
            textViewWhite = (TextView) itemView.findViewById(R.id.textViewItemMoveWhite);
            textViewBlack = (TextView) itemView.findViewById(R.id.textViewItemMoveBlack);
        }
    }
}
