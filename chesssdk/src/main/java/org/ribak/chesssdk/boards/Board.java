package org.ribak.chesssdk.boards;

import org.ribak.chesssdk.boards.initilizers.IBoardInitializer;
import org.ribak.chesssdk.exceptions.CheckmateGameException;
import org.ribak.chesssdk.exceptions.IllegalUndoException;
import org.ribak.chesssdk.moves.Move;
import org.ribak.chesssdk.moves.MoveType;
import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.Piece;
import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.positions.Positions;
import org.ribak.chesssdk.utils.PieceInPositionArray;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class Board {

    public interface IBoardListener {
        void requestPromotion(PieceInPosition pieceInPosition);
    }

    private boolean whiteTurn;
    private BoardState boardState;
    private PieceInPositionArray pieces;
    private IBoardListener boardListener;
    Deque<Move> moves;

    public Board(IBoardInitializer initializer, IBoardListener boardListener) {
        boardState = BoardState.normal;
        whiteTurn = true;
        pieces = initializer.initializePieces();
        this.boardListener = boardListener;
        moves = new ArrayDeque<>();
    }

    public BoardState getBoardState() {
        return boardState;
    }

    /**
     * get all moves for a piece on board
     * @param pieceInPosition the piece that wants to move or attack
     * @return a map between the final possible position and the type of move
     */
    public List<Move> getMovePositions(PieceInPosition pieceInPosition) {
        return this.getMovePositions(pieceInPosition, true, false);
    }

    List<Move> getMovePositions(PieceInPosition pieceInPosition, boolean checkChecks, boolean ignoreTurnColor) {
        if(pieceInPosition.isDead())
            return new ArrayList<>();
        if(!ignoreTurnColor && pieceInPosition.isWhite() != whiteTurn)
            return new ArrayList<>();
        List<Path> movePaths = pieceInPosition.getPiece().getAllPossibleMovePositions(pieceInPosition.getPosition(), pieceInPosition.isWhite());
        List<Path> attackPaths = pieceInPosition.getPiece().getAllPossibleAttackPositions(pieceInPosition.getPosition(), pieceInPosition.isWhite());
        List<Move> allowedPositions = new ArrayList<>();
        BoardAnalyzer analyzer = new BoardAnalyzer(this);
        for (Path path : movePaths) {
            if (!isPathBlocked(path, pieceInPosition.isWhite(), false) && (!checkChecks || !analyzer.hasCheck(pieceInPosition.isWhite(), pieceInPosition.getPosition(), path.getFinalPosition(), MoveType.move)))
                allowedPositions.add(new Move(pieceInPosition, path.getFinalPosition(), MoveType.move));

        }
        for (Path path : attackPaths)
            if(!isPathBlocked(path, pieceInPosition.isWhite(), true) && (!checkChecks || !analyzer.hasCheck(pieceInPosition.isWhite(), pieceInPosition.getPosition(), path.getFinalPosition(), MoveType.attack)))
                allowedPositions.add(new Move(pieceInPosition, path.getFinalPosition(), MoveType.attack));

        if(pieceInPosition.getPiece().getType() == Piece.Type.king) {
            List<MoveType> castleTypes = analyzer.canCastle(whiteTurn);
            BoardAnalyzer.CastleData castleData =  BoardAnalyzer.getCastlingData(whiteTurn);
            for (MoveType castleType : castleTypes)
                allowedPositions.add(new Move(pieceInPosition, castleData.getKingFinalPosition(castleType == MoveType.castleKingSide), castleType));
        }
        if(pieceInPosition.getPiece().getType() == Piece.Type.pawn && !moves.isEmpty()) {
            BoardAnalyzer.EnPassatData enPassatData = BoardAnalyzer.getEnPassatData(whiteTurn);
            Move lastMove = moves.peek();
            int col_dif = pieceInPosition.getPosition().getColumn() - lastMove.getFromPosition().getColumn();
            if(pieceInPosition.getPosition().getRow() == enPassatData.getPawnStartRow() &&
                    lastMove.getPiece().getPiece().getType() == Piece.Type.pawn &&
                    lastMove.getFromPosition().getRow() == enPassatData.getAttackedPawnStartRow() &&
                    lastMove.getToPosition().getRow() == enPassatData.getAttackPawnFinalRow() &&
                    Math.abs(col_dif) == 1) {
                Position enPassatPosition = enPassatData.getPawnFinalPosition(lastMove);
                allowedPositions.add(new Move(pieceInPosition, enPassatPosition, MoveType.enPassat));
            }
        }
        return allowedPositions;
    }


    private void move(Move move) {
        BoardAnalyzer.CastleData castleData = BoardAnalyzer.getCastlingData(whiteTurn);
        switch (move.getType()) {
            case move:
                move.getPiece().setPosition(move.getToPosition());
                if(move.getPiece().getPiece().getType() == Piece.Type.pawn && move.getPiece().isLastRow() && boardListener != null)
                    boardListener.requestPromotion(move.getPiece());
                break;
            case attack:
                PieceInPosition attackedPiece = pieces.getPieceInPosition(move.getToPosition());
                if(attackedPiece != null)
                    attackedPiece.setDead();
                if(!move.isNormal() && move.getExtraPiece() != null)
                    move.getExtraPiece().setDead(false);
                move.getPiece().setPosition(move.getToPosition());
                if(move.getPiece().getPiece().getType() == Piece.Type.pawn && move.getPiece().isLastRow() && boardListener != null)
                    boardListener.requestPromotion(move.getPiece());
                break;
            case castleKingSide:
                move.getPiece().setPosition(move.getToPosition());
                PieceInPosition rookKing = pieces.getPieceInPosition(castleData.getRookPosition(true));
                if(rookKing != null)
                    rookKing.setPosition(castleData.getRookFinalPosition(true));
                break;
            case castleQueenSide:
                move.getPiece().setPosition(move.getToPosition());
                PieceInPosition rookQueen = pieces.getPieceInPosition(castleData.getRookPosition(false));
                if(rookQueen != null)
                    rookQueen.setPosition(castleData.getRookFinalPosition(false));
                break;
            case enPassat:
                PieceInPosition attackedPawn = pieces.getPieceInPosition(Positions.findPosition(move.getFromPosition().getRow(), move.getToPosition().getColumn()));
                if(attackedPawn != null)
                    attackedPawn.setDead();
                if(!move.isNormal() && move.getExtraPiece() != null)
                    move.getExtraPiece().setDead(false);
                move.getPiece().setPosition(move.getToPosition());
                break;
        }
        whiteTurn = !whiteTurn;
        setStateIfNeeded();
    }

    public Move move(PieceInPosition pieceInPosition, Position toPosition) {
        Move move = new Move(pieceInPosition, toPosition, MoveType.move);
        moves.push(move);
        move(move);
        return move;
    }

    public Move attack(PieceInPosition pieceInPosition, Position toPosition) {
        PieceInPosition attacked = pieces.getPieceInPosition(toPosition);
        Move move = new Move(pieceInPosition, attacked, toPosition, MoveType.attack);
        moves.push(move);
        move(move);
        return move;
    }

    public Move castle(boolean kingSide) {
        BoardAnalyzer.CastleData castleData = BoardAnalyzer.getCastlingData(whiteTurn);
        Move move = new Move(pieces.getKing(whiteTurn), castleData.getKingFinalPosition(kingSide), (kingSide) ? MoveType.castleKingSide : MoveType.castleQueenSide);
        moves.push(move);
        move(move);
        return move;
    }

    public Move enPassat(PieceInPosition pieceInPosition, Position toPosition) {
        PieceInPosition attacked = pieces.getPieceInPosition(Positions.findPosition(pieceInPosition.getPosition().getRow(), toPosition.getColumn()));
        Move move = new Move(pieceInPosition, attacked, toPosition, MoveType.enPassat);
        moves.push(move);
        move(move);
        return move;
    }
    public Move undo() throws IllegalUndoException {
        if(moves.isEmpty())
            throw new IllegalUndoException();
        Move lastReversed = moves.poll().createReverseMove();
        move(lastReversed);
        return lastReversed;
    }

    public List<Move> getMoves() {
        return new ArrayList<>(moves);
    }

    public boolean isPieceTurn(PieceInPosition pieceInPosition) {
        return pieceInPosition != null && pieceInPosition.isWhite() == whiteTurn;
    }
    public int getScore(boolean white) throws CheckmateGameException {
        int score = 0;
        for (PieceInPosition pieceInPosition : pieces.getPiecesInColor(!white))
            if(pieceInPosition.isDead())
                score += pieceInPosition.getPiece().getScore();
        return score;
    }

    public PieceInPositionArray getPieces() {
        return pieces;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    private boolean isPathBlocked(Path path, boolean white, boolean attack) {
        for (Position position : path.getThroughPositions())
            for (PieceInPosition pieceInPosition : pieces)
                if(pieceInPosition.getPosition() != null && pieceInPosition.getPosition().equals(position))
                    return true;

        PieceInPosition pieceInTarget = pieces.getPieceInPosition(path.getFinalPosition());
        if(attack)
            return pieceInTarget == null || pieceInTarget.isWhite() == white;
        return pieceInTarget != null;
    }

    private void setStateIfNeeded() {
        boardState = BoardState.normal;
        BoardAnalyzer analyzer = new BoardAnalyzer(this);
        boolean hasCheck = analyzer.hasCheck(whiteTurn); // check if other side is checked
        if(hasCheck) {
            int movesCount = 0;
            for (PieceInPosition piece : pieces.getPiecesInColor(whiteTurn))
                movesCount += getMovePositions(piece, true, true).size();
            if(movesCount > 0)
                boardState = (whiteTurn) ? BoardState.blackCheck : BoardState.whiteCheck;
            else
                boardState = (whiteTurn) ? BoardState.blackCheckMate : BoardState.whiteCheckMate;
        } else if(analyzer.hasStalemate(whiteTurn))
            boardState = BoardState.staleMate;
        else if(analyzer.hasDraw())
            boardState = BoardState.draw;
    }

}
