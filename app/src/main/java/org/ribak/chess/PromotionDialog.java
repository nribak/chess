package org.ribak.chess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ribak.chesssdk.pieces.Piece;

/**
 * Created by nribak on 01/03/2017.
 */

public class PromotionDialog extends DialogFragment {

    private static final String COLOR_EXTRA = "color";

    public interface OnPromotionSelectedListener {
        void onPromotionTypeSelected(Piece.Type type);
    }

    public static PromotionDialog newDialog(boolean white) {
        PromotionDialog dialog = new PromotionDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(COLOR_EXTRA, white);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final OnPromotionSelectedListener onPromotionSelectedListener = (OnPromotionSelectedListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Adapter adapter = new Adapter(R.layout.item_dialog_promotion);
        builder.setTitle(R.string.promote).setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPromotionSelectedListener.onPromotionTypeSelected(adapter.getItem(which));
            }
        });
        return builder.create();
    }

    private class Adapter extends ArrayAdapter<Piece.Type> {
        private boolean white;
        private int resource;
        Adapter(int resource) {
            super(getActivity(), resource, Piece.Type.getPossiblePromotionTypes());
            this.resource = resource;
            this.white = getArguments().getBoolean(COLOR_EXTRA, true);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if(convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(resource, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.textViewItemDialogPromotion);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewItemDialogPromotion);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            final Piece.Type type = getItem(position);
            if(type != null) {
                holder.textView.setText(type.getTypeName());
                PieceIcons.Icons icons = PieceIcons.getPieceIcons(type);
                holder.imageView.setImageResource(white ? icons.getWhiteIcon() : icons.getBlackIcon());
            }
            return convertView;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }

}
