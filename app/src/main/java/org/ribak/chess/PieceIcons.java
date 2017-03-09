package org.ribak.chess;

import android.support.annotation.DrawableRes;

import org.ribak.chesssdk.pieces.Piece;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by nribak on 24/02/2017.
 */

public class PieceIcons {
    private static Map<Piece.Type, Icons> iconsMap;

    static {
        iconsMap = new EnumMap<>(Piece.Type.class);
        iconsMap.put(Piece.Type.pawn, new Icons(R.drawable.pawn_white, R.drawable.pawn_black));
        iconsMap.put(Piece.Type.bishop, new Icons(R.drawable.bishop_white, R.drawable.bishop_black));
        iconsMap.put(Piece.Type.knight, new Icons(R.drawable.knight_white, R.drawable.knight_black));
        iconsMap.put(Piece.Type.rook, new Icons(R.drawable.rook_white, R.drawable.rook_black));
        iconsMap.put(Piece.Type.queen, new Icons(R.drawable.queen_white, R.drawable.queen_black));
        iconsMap.put(Piece.Type.king, new Icons(R.drawable.king_white, R.drawable.king_black));
    }

    public static Icons getPieceIcons(Piece piece) {
        return iconsMap.get(piece.getType());
    }

    public static Icons getPieceIcons(Piece.Type type) {
        return iconsMap.get(type);
    }

    public static class Icons {
        @DrawableRes
        private int whiteIcon, blackIcon;

        private Icons(int whiteIcon, int blackIcon) {
            this.whiteIcon = whiteIcon;
            this.blackIcon = blackIcon;
        }

        @DrawableRes
        public int getWhiteIcon() {
            return whiteIcon;
        }

        @DrawableRes
        public int getBlackIcon() {
            return blackIcon;
        }
    }
}
