package org.ribak.chess;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nribak on 03/03/2017.
 */

public class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.ViewHolder> {
    private Context context;
    private int resource;
    private List<String> moves;

    public MovesAdapter(Context context, int resource, List<String> moves) {
        this.context = context;
        this.resource = resource;
        this.moves = moves;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String move = moves.get(position);
        holder.textView.setText(move);
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public void add(String move) {
        if(move != null)
            moves.add(move);
        notifyDataSetChanged();
    }

    public void removeLast() {
        moves.remove(moves.size() - 1);
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewItemMove);
        }
    }
}
