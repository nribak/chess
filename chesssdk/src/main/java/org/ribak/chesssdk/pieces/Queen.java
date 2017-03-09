package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.BackwardDiagonalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.ForwardDiagonalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.HorizontalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.VerticalMoveBehaviour;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

/**
 * Created by nribak on 23/02/2017.
 */

public class Queen extends Piece {

    public static final String NAME = "Queen";

    public Queen() {
        super(NAME, "Q");
        moveBehaviours.add(new BackwardDiagonalMoveBehaviour(false));
        moveBehaviours.add(new ForwardDiagonalMoveBehaviour(false));
        moveBehaviours.add(new HorizontalMoveBehaviour(false));
        moveBehaviours.add(new VerticalMoveBehaviour(false));
    }

    @Override
    public int getScore() throws CheckmateGameException {
        return 9;
    }

    @Override
    public Type getType() {
        return Type.queen;
    }
}
