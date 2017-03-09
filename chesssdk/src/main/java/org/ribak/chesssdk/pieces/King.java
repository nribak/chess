package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.BackwardDiagonalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.ForwardDiagonalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.HorizontalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.VerticalMoveBehaviour;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

/**
 * Created by nribak on 23/02/2017.
 */

public class King extends Piece {

    public static final String NAME = "King";

    public King() {
        super(NAME, "K");
        moveBehaviours.add(new ForwardDiagonalMoveBehaviour(true));
        moveBehaviours.add(new BackwardDiagonalMoveBehaviour(true));
        moveBehaviours.add(new VerticalMoveBehaviour(true));
        moveBehaviours.add(new HorizontalMoveBehaviour(true));
    }

    @Override
    public int getScore() throws CheckmateGameException {
        throw new CheckmateGameException();
    }

    @Override
    public Type getType() {
        return Type.king;
    }
}
