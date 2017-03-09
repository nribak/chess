package org.ribak.chesssdk.moves;

import org.ribak.chesssdk.pieces.Piece;
import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class PieceInPosition {
    private Piece piece;
    private Position position, finalPosition, startPosition;
    private boolean white;
    private boolean dead;

    public PieceInPosition() { }

    public PieceInPosition(Piece piece, Position position, boolean white) {
        this.piece = piece;
        this.position = position;
        this.startPosition = position;
        this.white = white;
        this.dead = false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void promote(Piece newPiece) {
        if(piece.getType() != Piece.Type.pawn || dead)
            throw new IllegalArgumentException("Cannot Promote " + piece.getFullName());
        if(newPiece == null)
            throw new NullPointerException("Piece cannot be null");
        this.piece = newPiece;
    }
    public Position getPosition() {
        return position;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isLastRow() {
        return white ? position.getRow() == Position.LAST_ROW : position.getRow() == Position.FIRST_ROW;
    }
    public void setDead() {
        this.setDead(true);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
        if(dead) {
            this.finalPosition = position;
            this.position = null;
        } else {
            this.position = finalPosition;
            this.finalPosition = null;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public List<Path> getAllMovePositions() {
        if(isDead())
            return new ArrayList<>();
        return piece.getAllPossibleMovePositions(position, white);
    }
    public List<Path> getAllAttackPositions() {
        if(isDead())
            return new ArrayList<>();
        return piece.getAllPossibleAttackPositions(position, white);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(this.getClass() != obj.getClass())
            return false;
        PieceInPosition rhs = (PieceInPosition) obj;
        return this.piece.equals(rhs.piece) && this.position.equals(rhs.position);
    }

    @Override
    public String toString() {
        return (white ? "white " : "black ") + piece.getFullName() + " at " + (position == null ? "unknown" : position.toString());
    }
}
