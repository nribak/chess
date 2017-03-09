package org.ribak.chesssdk.moves;

/**
 * Created by nribak on 24/02/2017.
 */

public enum MoveType {
    move,
    attack,
    castleKingSide,
    castleQueenSide,
    enPassat;

    public boolean isAttacking() {
        return this == attack || this == enPassat;
    }

}
