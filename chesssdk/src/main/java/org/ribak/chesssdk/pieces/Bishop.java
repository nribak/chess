package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.BackwardDiagonalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.ForwardDiagonalMoveBehaviour;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

/**
 * Created by nribak on 23/02/2017.
 */

public class Bishop extends Piece {

    public static final String NAME = "Bishop";

    public Bishop() {
        super(NAME, 'B');
        moveBehaviours.add(new ForwardDiagonalMoveBehaviour(false));
        moveBehaviours.add(new BackwardDiagonalMoveBehaviour(false));
    }

    @Override
    public int getScore() throws CheckmateGameException {
        return 3;
    }

    @Override
    public Type getType() {
        return Type.bishop;
    }
}
