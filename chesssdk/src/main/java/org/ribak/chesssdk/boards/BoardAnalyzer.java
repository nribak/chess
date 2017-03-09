package org.ribak.chesssdk.boards;

import org.ribak.chesssdk.boards.initilizers.DuplicateBoardInitializer;
import org.ribak.chesssdk.moves.Move;
import org.ribak.chesssdk.moves.MoveType;
import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.Piece;
import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.positions.Positions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by nribak on 26/02/2017.
 */

public class BoardAnalyzer {
    private static CastleData CASTLING_DATA_WHITE, CASTLING_DATA_BLACK;
    private static EnPassatData ENPASSAT_DATA_WHITE, ENPASSAT_DATA_BLACK;
    private final Board origBoard;

    static {
        CASTLING_DATA_WHITE = new CastleData(true);
        CASTLING_DATA_BLACK = new CastleData(false);

        ENPASSAT_DATA_WHITE = new EnPassatData(true);
        ENPASSAT_DATA_BLACK = new EnPassatData(false);
    }
    public static CastleData getCastlingData(boolean white) {
        return (white) ? CASTLING_DATA_WHITE : CASTLING_DATA_BLACK;
    }
    public static EnPassatData getEnPassatData(boolean white) {
        return white ? ENPASSAT_DATA_WHITE : ENPASSAT_DATA_BLACK;
    }
    public BoardAnalyzer(Board board) {
        this.origBoard = new Board(new DuplicateBoardInitializer(board.getPieces()), null);
        origBoard.moves = new ArrayDeque<>(board.moves);
    }

    private boolean hasCheck(boolean white, Board board) {
        if(board == null)
            board = origBoard;
        PieceInPosition kingPiece = board.getPieces().getKing(white);
        for (PieceInPosition pieceInPosition : board.getPieces().getPiecesInColor(!white)) {
            if(isTargetingKing(board, pieceInPosition, kingPiece.getPosition()))
                return true;
        }
        return false;
    }

    /**
     * Check if there is a game check in the current state of the board
     * @param white the king color
     * @return true if this king is checked
     */
    public boolean hasCheck(boolean white) {
        return this.hasCheck(white, null);
    }

    /**
     * Check is there is a game check after a move or attack operation
     * @param white the king color
     * @param fromPosition the move stating position
     * @param toPosition the move ending position
     * @param moveType the type of the move
     * @return true is the king will be checked after the move
     */
    public boolean hasCheck(boolean white, Position fromPosition, Position toPosition, MoveType moveType) {
        Board board = new Board(new DuplicateBoardInitializer(origBoard.getPieces()), null);
        PieceInPosition srcPiece = board.getPieces().getPieceInPosition(fromPosition);
        PieceInPosition targetPiece = board.getPieces().getPieceInPosition(toPosition);
        if(moveType == MoveType.attack) {
            if(targetPiece != null)
                targetPiece.setDead();
        }
        if(srcPiece != null)
            srcPiece.setPosition(toPosition);
        return hasCheck(white, board);
    }

    /**
     * check if the specified color has any move available
     * @param white the pieces color to check
     * @return true if no moves are available
     */
    public boolean hasStalemate(boolean white) {
        for (PieceInPosition pieceInPosition : origBoard.getPieces().getPiecesInColor(white))
            if(!origBoard.getMovePositions(pieceInPosition, true, true).isEmpty())
                return false;
        return true;
    }
    public boolean hasDraw() {
        Map<Piece.Type, Integer> countWhite = origBoard.getPieces().count(true);
        Map<Piece.Type, Integer> countBlack = origBoard.getPieces().count(false);

        int sumWhite = 0;
        int sumBlack = 0;
        for (Integer i : countWhite.values())
            if(i != null)
                sumWhite += i;
        for (Integer i : countBlack.values())
            if(i != null)
                sumBlack += i;

        return !countWhite.containsKey(Piece.Type.pawn) &&
                !countWhite.containsKey(Piece.Type.rook) &&
                !countWhite.containsKey(Piece.Type.queen) &&

                !countBlack.containsKey(Piece.Type.pawn) &&
                !countBlack.containsKey(Piece.Type.rook) &&
                !countBlack.containsKey(Piece.Type.queen) &&
                sumWhite < 3 && sumBlack < 3;
    }
    public List<MoveType> canCastle(boolean white) {
        CastleData castleData = getCastlingData(white);
        Collection<Move> moves = origBoard.getMoves();
        List<MoveType> types = new ArrayList<>();
        types.add(MoveType.castleKingSide);
        types.add(MoveType.castleQueenSide);
        for (Move move : moves) {
            if (move.getPiece().isWhite() == white && move.getPiece().getPiece().getType() == Piece.Type.king) // king has moved
                types.clear();
            if (move.getPiece().isWhite() == white && move.getPiece().getPiece().getType() == Piece.Type.rook) { // rook has moved
                if(move.getPiece().getStartPosition().equals(castleData.getRookKingSidePosition()))
                    types.remove(MoveType.castleKingSide);
                else if(move.getPiece().getStartPosition().equals(castleData.getRookQueenSidePosition()))
                    types.remove(MoveType.castleQueenSide);
            }
        }
        if(types.contains(MoveType.castleKingSide)) {
            for (Position position : castleData.getBetweenKingSidePositions()) {
                PieceInPosition target = origBoard.getPieces().getPieceInPosition(position);
                if(target == null || (target.getPiece().getType() == Piece.Type.king && target.isWhite() == white)) {
                    for (PieceInPosition pieceInPosition : origBoard.getPieces().getPiecesInColor(!white))
                        if(isTargetingKing(origBoard, pieceInPosition, position))
                            types.remove(MoveType.castleKingSide);
                } else
                    types.remove(MoveType.castleKingSide);
            }
        }
        if(types.contains(MoveType.castleQueenSide)) {
            for (Position position : castleData.getBetweenQueenPositions()) {
                PieceInPosition target = origBoard.getPieces().getPieceInPosition(position);
                if(target == null || (target.getPiece().getType() == Piece.Type.king && target.isWhite() == white)) {
                    for (PieceInPosition pieceInPosition : origBoard.getPieces().getPiecesInColor(!white))
                        if (isTargetingKing(origBoard, pieceInPosition, position))
                            types.remove(MoveType.castleQueenSide);
                }
                else
                    types.remove(MoveType.castleQueenSide);
            }
        }
        return types;
    }

    private boolean isTargetingKing(Board board, PieceInPosition pieceInPosition, Position kingPosition) {
        List<Path> paths = pieceInPosition.getAllAttackPositions();
        for (Path path : paths) {
            boolean attacking = true;
            if(path.getFinalPosition().equals(kingPosition)) {
                for (Position position : path.getThroughPositions())
                    if (board.getPieces().getPieceInPosition(position) != null)
                        attacking = false;
            }
            else
                attacking = false;
            if(attacking)
                return true;
        }
        return false;
    }

    public static class CastleData {
        private final Position kingPosition, rookKingSidePosition, rookQueenSidePosition,
                kingSideFinalPosition, queenSideFinalPosition, rookKingSideFinalPosition, rookQueenSideFinalPosition;
        private final Position[] betweenKingSidePositions, betweenQueenPositions;
        private CastleData(boolean white) {
            if(white) {
                kingPosition = Positions.P_1E;
                rookQueenSidePosition = Positions.P_1A;
                rookKingSidePosition = Positions.P_1H;
                kingSideFinalPosition = Positions.P_1G;
                queenSideFinalPosition = Positions.P_1C;
                rookKingSideFinalPosition = Positions.P_1F;
                rookQueenSideFinalPosition = Positions.P_1D;
                betweenKingSidePositions = new Position[]{Positions.P_1E, Positions.P_1F, Positions.P_1G};
                betweenQueenPositions = new Position[]{Positions.P_1E, Positions.P_1D, Positions.P_1C};
            } else {
                kingPosition = Positions.P_8E;
                rookQueenSidePosition = Positions.P_8A;
                rookKingSidePosition = Positions.P_8H;
                kingSideFinalPosition = Positions.P_8G;
                queenSideFinalPosition = Positions.P_8C;
                rookKingSideFinalPosition = Positions.P_8F;
                rookQueenSideFinalPosition = Positions.P_8D;
                betweenKingSidePositions = new Position[]{Positions.P_8E, Positions.P_8F, Positions.P_8G};
                betweenQueenPositions = new Position[]{Positions.P_8E, Positions.P_8D, Positions.P_8C};
            }
        }

        public Position getKingPosition() {
            return kingPosition;
        }

        public Position getRookKingSidePosition() {
            return rookKingSidePosition;
        }

        public Position getRookQueenSidePosition() {
            return rookQueenSidePosition;
        }

        public Position[] getBetweenKingSidePositions() {
            return betweenKingSidePositions;
        }

        public Position[] getBetweenQueenPositions() {
            return betweenQueenPositions;
        }


        public Position getKingFinalPosition(boolean kingSide) {
            return kingSide ? kingSideFinalPosition : queenSideFinalPosition;
        }

        public Position getRookFinalPosition(boolean kingSide) {
            return kingSide ? rookKingSideFinalPosition : rookQueenSideFinalPosition;
        }

        public Position getRookPosition(boolean kingSide) {
            return kingSide ? rookKingSidePosition : rookQueenSidePosition;
        }
    }

    public static class EnPassatData {
        private final int pawnStartRow, attackedPawnStartRow, attackPawnFinalRow, pawnFinalRow;
        private EnPassatData(boolean white) {
            if(white) {
                pawnStartRow = 4;
                pawnFinalRow = 5;
                attackedPawnStartRow = 6;
                attackPawnFinalRow = 4;
            } else {
                pawnStartRow = 3;
                pawnFinalRow = 2;
                attackedPawnStartRow = 1;
                attackPawnFinalRow = 3;
            }
        }
        public Position getPawnFinalPosition(Move lastMove) {
            return Positions.findPosition(pawnFinalRow, lastMove.getFromPosition().getColumn());
        }
        public Position getAttackedPawnPosition(Move lastMove) {
            return Positions.findPosition(attackPawnFinalRow, lastMove.getFromPosition().getColumn());
        }
        public int getPawnStartRow() {
            return pawnStartRow;
        }

        public int getAttackedPawnStartRow() {
            return attackedPawnStartRow;
        }

        public int getAttackPawnFinalRow() {
            return attackPawnFinalRow;
        }


    }
}
