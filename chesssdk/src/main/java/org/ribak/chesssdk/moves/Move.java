package org.ribak.chesssdk.moves;

import org.ribak.chesssdk.positions.Position;

/**
 * Created by nribak on 03/03/2017.
 */

public class Move {
    private PieceInPosition pieceInPosition, extraPiece;
    private Position fromPosition, toPosition;
    private MoveType type;
    private boolean normal; // true if the move is a regular move, false if the move is a reversed move

    public Move(PieceInPosition pieceInPosition, Position toPosition, MoveType type) {
        this.pieceInPosition = pieceInPosition;
        this.fromPosition = pieceInPosition.getPosition();
        this.toPosition = toPosition;
        this.type = type;
        this.normal = true;
    }

    public Move(PieceInPosition pieceInPosition, PieceInPosition extraPiece, Position toPosition, MoveType type) {
        this.pieceInPosition = pieceInPosition;
        this.fromPosition = pieceInPosition.getPosition();
        this.toPosition = toPosition;
        this.type = type;
        this.extraPiece = extraPiece;
        this.normal = true;
    }

    public Move createReverseMove() {
        Move move = new Move(pieceInPosition, extraPiece, fromPosition, type);
        move.fromPosition = toPosition;
        move.normal = false;
        return move;
    }
    public PieceInPosition getPiece() {
        return pieceInPosition;
    }

    public Position getFromPosition() {
        return fromPosition;
    }

    public Position getToPosition() {
        return toPosition;
    }

    public MoveType getType() {
        return type;
    }

    public PieceInPosition getExtraPiece() {
        return extraPiece;
    }

    public boolean isNormal() {
        return normal;
    }

    @Override
    public String toString() {
        switch (type) {
            case move:
                return pieceInPosition.getPiece().getLetter() + toPosition.toString();
            case attack: case enPassat:
                return pieceInPosition.getPiece().getLetter() + "x" + toPosition.toString();
            case castleKingSide:
                return "0-0";
            case castleQueenSide:
                return "0-0-0";
            default:
                return pieceInPosition.getPiece().getLetter() + toPosition.toString();
        }
    }
}
