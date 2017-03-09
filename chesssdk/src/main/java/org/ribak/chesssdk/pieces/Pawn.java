package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.PawnMoveBehaviour;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

/**
 * Created by nribak on 23/02/2017.
 */

public class Pawn extends Piece {
    public static final String NAME = "Pawn";
    public Pawn() {
        super(NAME, "");
        moveBehaviours.add(new PawnMoveBehaviour());
    }

    @Override
    public int getScore() throws CheckmateGameException {
        return 1;
    }

    @Override
    public Type getType() {
        return Type.pawn;
    }
}
