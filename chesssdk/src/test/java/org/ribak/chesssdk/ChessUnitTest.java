package org.ribak.chesssdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ribak.chesssdk.boards.Board;
import org.ribak.chesssdk.boards.BoardAnalyzer;
import org.ribak.chesssdk.boards.initilizers.SimpleBoardInitializer;
import org.ribak.chesssdk.moves.Move;
import org.ribak.chesssdk.moves.MoveType;
import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.Piece;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.positions.Positions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessUnitTest {
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(new SimpleBoardInitializer(), null);
    }

    @Test
    public void boardCheck() throws Exception {
        PieceInPosition pieceInPosition = board.getPieces().getPieceInPosition(Positions.P_2E);

        Assert.assertNotNull(pieceInPosition);
        Assert.assertEquals("Pawn", pieceInPosition.getPiece().getFullName());

        List<Move> pawnPositions = board.getMovePositions(pieceInPosition, true, false);

        Assert.assertNotNull(pawnPositions);
        Assert.assertEquals(2, pawnPositions.size());

        final Map<Position, MoveType> targets = new HashMap<>();
        targets.put(Positions.P_3E, MoveType.move);
        targets.put(Positions.P_4E, MoveType.move);
        Assert.assertEquals(targets, pawnPositions);
    }

    @Test
    public void positions() throws Exception {
        Position position = Positions.P_8H;
        Assert.assertEquals(7, position.getRow());
        Assert.assertEquals(7, position.getColumn());
    }

    @Test
    public void analyzeCheck() throws Exception {
        BoardAnalyzer analyzer = new BoardAnalyzer(new Board(new SimpleBoardInitializer(), null));
        Assert.assertFalse(analyzer.hasCheck(true));
        Assert.assertTrue(analyzer.hasCheck(false));
    }

    @Test
    public void count() throws Exception {
        Map<Piece.Type, Integer> allPieces = new HashMap<>();
        allPieces.put(Piece.Type.queen, 1);
        allPieces.put(Piece.Type.king, 1);
        allPieces.put(Piece.Type.rook, 2);
        allPieces.put(Piece.Type.knight, 2);
        allPieces.put(Piece.Type.bishop, 2);
        allPieces.put(Piece.Type.pawn, 8);

        Map<Piece.Type, Integer> countWhite = board.getPieces().count(true);
        Map<Piece.Type, Integer> countBlack = board.getPieces().count(false);

        Assert.assertNotNull(countWhite);
        Assert.assertNotNull(countBlack);

        Assert.assertEquals(allPieces, countWhite);
        Assert.assertEquals(allPieces, countBlack);
    }

    @Test
    public void castle() throws Exception {
        List<MoveType> whiteResult = new ArrayList<>();
        List<MoveType> blackResult = Collections.singletonList(MoveType.castleKingSide);

        PieceInPosition rook = board.getPieces().getPieceInPosition(Positions.P_1H);
        board.move(rook, Positions.P_3H);

        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        List<MoveType> whites = analyzer.canCastle(true);
        List<MoveType> blacks = analyzer.canCastle(false);

        Assert.assertNotNull(whites);
        Assert.assertNotNull(blacks);

        Assert.assertEquals(whiteResult, whites);
        Assert.assertEquals(blackResult, blacks);
    }

    @Test
    public void deque() throws Exception {
        Deque<String> deque = new ArrayDeque<>();
        deque.push("aaa");
        deque.push("bbb");
        deque.push("ccc");
        deque.push("ddd");

        Assert.assertEquals("ddd", deque.peek());
        Assert.assertEquals("ddd", deque.poll());
        Assert.assertEquals("ccc", deque.peek());
        Assert.assertEquals("ccc", deque.poll());

    }
}