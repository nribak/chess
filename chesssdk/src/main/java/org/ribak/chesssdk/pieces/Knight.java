package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.KnightMoveBehaviour;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

/**
 * Created by nribak on 23/02/2017.
 */

public class Knight extends Piece {

    public static final String NAME = "Knight";

    public Knight() {
        super(NAME, 'N');
        moveBehaviours.add(new KnightMoveBehaviour());
    }

    @Override
    public int getScore() throws CheckmateGameException {
        return 3;
    }

    @Override
    public Type getType() {
        return Type.knight;
    }
}
