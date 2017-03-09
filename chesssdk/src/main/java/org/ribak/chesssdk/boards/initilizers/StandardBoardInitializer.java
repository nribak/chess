package org.ribak.chesssdk.boards.initilizers;

import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.Bishop;
import org.ribak.chesssdk.pieces.King;
import org.ribak.chesssdk.pieces.Knight;
import org.ribak.chesssdk.pieces.Pawn;
import org.ribak.chesssdk.pieces.Queen;
import org.ribak.chesssdk.pieces.Rook;
import org.ribak.chesssdk.positions.Positions;
import org.ribak.chesssdk.utils.PieceInPositionArray;

/**
 * Created by nribak on 23/02/2017.
 */

public class StandardBoardInitializer implements IBoardInitializer {
    @Override
    public PieceInPositionArray initializePieces() {
        return new PieceInPositionArray(
                new PieceInPosition(new Rook(), Positions.P_1A, true),
                new PieceInPosition(new Knight(), Positions.P_1B, true),
                new PieceInPosition(new Bishop(), Positions.P_1C, true),
                new PieceInPosition(new Queen(), Positions.P_1D, true),
                new PieceInPosition(new King(), Positions.P_1E, true),
                new PieceInPosition(new Bishop(), Positions.P_1F, true),
                new PieceInPosition(new Knight(), Positions.P_1G, true),
                new PieceInPosition(new Rook(), Positions.P_1H, true),
                new PieceInPosition(new Pawn(), Positions.P_2A, true),
                new PieceInPosition(new Pawn(), Positions.P_2B, true),
                new PieceInPosition(new Pawn(), Positions.P_2C, true),
                new PieceInPosition(new Pawn(), Positions.P_2D, true),
                new PieceInPosition(new Pawn(), Positions.P_2E, true),
                new PieceInPosition(new Pawn(), Positions.P_2F, true),
                new PieceInPosition(new Pawn(), Positions.P_2G, true),
                new PieceInPosition(new Pawn(), Positions.P_2H, true),

                new PieceInPosition(new Rook(), Positions.P_8A, false),
                new PieceInPosition(new Knight(), Positions.P_8B, false),
                new PieceInPosition(new Bishop(), Positions.P_8C, false),
                new PieceInPosition(new Queen(), Positions.P_8D, false),
                new PieceInPosition(new King(), Positions.P_8E, false),
                new PieceInPosition(new Bishop(), Positions.P_8F, false),
                new PieceInPosition(new Knight(), Positions.P_8G, false),
                new PieceInPosition(new Rook(), Positions.P_8H, false),
                new PieceInPosition(new Pawn(), Positions.P_7A, false),
                new PieceInPosition(new Pawn(), Positions.P_7B, false),
                new PieceInPosition(new Pawn(), Positions.P_7C, false),
                new PieceInPosition(new Pawn(), Positions.P_7D, false),
                new PieceInPosition(new Pawn(), Positions.P_7E, false),
                new PieceInPosition(new Pawn(), Positions.P_7F, false),
                new PieceInPosition(new Pawn(), Positions.P_7G, false),
                new PieceInPosition(new Pawn(), Positions.P_7H, false)
        );
    }
}
