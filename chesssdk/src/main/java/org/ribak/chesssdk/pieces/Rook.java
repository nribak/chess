package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.HorizontalMoveBehaviour;
import org.ribak.chesssdk.move_behaviour.VerticalMoveBehaviour;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

/**
 * Created by nribak on 23/02/2017.
 */

public class Rook extends Piece {

    public static final String NAME = "Rook";

    public Rook() {
        super(NAME, 'R');
        moveBehaviours.add(new VerticalMoveBehaviour(false));
        moveBehaviours.add(new HorizontalMoveBehaviour(false));
    }

    @Override
    public int getScore() throws CheckmateGameException {
        return 5;
    }

    @Override
    public Type getType() {
        return Type.rook;
    }
}
