package org.ribak.chesssdk.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.Piece;
import org.ribak.chesssdk.positions.Position;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nribak on 26/02/2017.
 */

public class PieceInPositionArray extends ArrayList<PieceInPosition> {

    public PieceInPositionArray() { }

    public PieceInPositionArray(PieceInPosition... items) {
        for (PieceInPosition item : items)
            add(item);
    }

    @Nullable
    public PieceInPosition getPieceInPosition(Position position) {
        for (PieceInPosition pieceInPosition : this)
            if(pieceInPosition.getPosition() != null && pieceInPosition.getPosition().equals(position))
                return pieceInPosition;
        return null;
    }

    public List<PieceInPosition> getPiecesInColor(boolean white) {
        PieceInPositionArray array = new PieceInPositionArray();
        for (PieceInPosition pieceInPosition : this)
            if(pieceInPosition.isWhite() == white)
                array.add(pieceInPosition);
        return array;
    }

    public PieceInPositionArray getSpecificPiece(Piece.Type type, boolean white) {
        PieceInPositionArray array = new PieceInPositionArray();
        for (PieceInPosition pieceInPosition : this)
            if(pieceInPosition.getPiece().getType() == type && pieceInPosition.isWhite() == white)
                array.add(pieceInPosition);
        return array;
    }

    @NonNull
    public PieceInPosition getKing(boolean white) {
        for (PieceInPosition pieceInPosition : this)
            if(pieceInPosition.getPiece().getType() == Piece.Type.king && pieceInPosition.isWhite() == white)
                return pieceInPosition;
        return new PieceInPosition();
    }
    public Map<Piece.Type, Integer> count(boolean white) {
        Map<Piece.Type, Integer> map = new EnumMap<>(Piece.Type.class);
        for (PieceInPosition pieceInPosition : this)
            if(pieceInPosition.isWhite() == white && !pieceInPosition.isDead()) {
                Integer count = map.get(pieceInPosition.getPiece().getType());
                if(count == null)
                    count = 0;
                map.put(pieceInPosition.getPiece().getType(), ++count);
            }
        return map;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PieceInPosition pieceInPosition : this) {
            if(builder.length() > 0)
                builder.append(",");
            builder.append(pieceInPosition.toString());
        }
        return builder.toString();
    }
}
