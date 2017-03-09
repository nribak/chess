package org.ribak.chesssdk.boards.initilizers;

import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.King;
import org.ribak.chesssdk.pieces.Pawn;
import org.ribak.chesssdk.positions.Positions;
import org.ribak.chesssdk.utils.PieceInPositionArray;

/**
 * Created by nribak on 24/02/2017.
 */

public class SimpleBoardInitializer implements IBoardInitializer {

    @Override
    public PieceInPositionArray initializePieces() {
        return new PieceInPositionArray(
                new PieceInPosition(new King(), Positions.P_1E, true),
                new PieceInPosition(new Pawn(), Positions.P_4H, true),
                new PieceInPosition(new Pawn(), Positions.P_7G, false),
                new PieceInPosition(new King(), Positions.P_8E, false)
        );
    }
}
