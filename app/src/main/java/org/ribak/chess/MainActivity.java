package org.ribak.chess;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.ribak.chess.views.BoardView;
import org.ribak.chesssdk.boards.Board;
import org.ribak.chesssdk.boards.BoardAnalyzer;
import org.ribak.chesssdk.boards.initilizers.SimpleBoardInitializer;
import org.ribak.chesssdk.exceptions.CheckmateGameException;
import org.ribak.chesssdk.exceptions.IllegalUndoException;
import org.ribak.chesssdk.moves.Move;
import org.ribak.chesssdk.moves.MoveType;
import org.ribak.chesssdk.moves.PieceInPosition;
import org.ribak.chesssdk.pieces.Piece;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.positions.Positions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements BoardView.OnBoardViewChangedListener,
        Board.IBoardListener, PromotionDialog.OnPromotionSelectedListener {
    private BoardView boardView;
    private Board board;

    private Button infoButton;
    private PieceInPosition pieceSelected, promotionPiece;
    private MovesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        boardView = (BoardView) findViewById(R.id.boardView);
        boardView.setOnBoardViewChangedListener(this);
        board = new Board(new SimpleBoardInitializer(), this);

        infoButton = (Button) findViewById(R.id.buttonInfo);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (PieceInPosition pieceInPosition : board.getPieces()) {
                    Log.d("PIECES", pieceInPosition.toString());
                }
            }
        });
        init();
    }

    private void init() {
        for (PieceInPosition pieceInPosition : board.getPieces()) {
            if(pieceInPosition.isWhite())
                boardView.setSquare(pieceInPosition.getPosition(), PieceIcons.getPieceIcons(pieceInPosition.getPiece()).getWhiteIcon());
            else
                boardView.setSquare(pieceInPosition.getPosition(), PieceIcons.getPieceIcons(pieceInPosition.getPiece()).getBlackIcon());
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoves);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MovesAdapter(this, R.layout.item_move, new ArrayList<String>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSquareClicked(Position position) {
        PieceInPosition pieceInPosition = board.getPieces().getPieceInPosition(position);
        if (pieceSelected == null) {
            listPieceMovements(pieceInPosition);
        } else {
            setPieceMovement(position, pieceSelected);
            pieceSelected = null;
        }
    }

    private void setPieceMovement(Position position,@NonNull PieceInPosition pieceToMove) {
        List<Move> moves = board.getMovePositions(pieceToMove, true, false);
        PieceIcons.Icons icons = PieceIcons.getPieceIcons(pieceToMove.getPiece());
        Move selectedMove = null;
        for (Move move : moves)
            if(move.getToPosition().equals(position))
                selectedMove = move;
        if(selectedMove != null)
            switch (selectedMove.getType()) {
                case move:
                    boardView.setSquare(pieceToMove.getPosition(), 0);
                    boardView.setSquare(position, (pieceToMove.isWhite()) ? icons.getWhiteIcon() : icons.getBlackIcon());
                    boardView.clearMarkings();
                    adapter.add(board.move(pieceToMove, position).toString());
                    setScores();
                    break;
                case attack:
                    boardView.setSquare(pieceToMove.getPosition(), 0);
                    boardView.setSquare(position, (pieceToMove.isWhite()) ? icons.getWhiteIcon() : icons.getBlackIcon());
                    boardView.clearMarkings();
                    adapter.add(board.attack(pieceToMove, position).toString());
                    setScores();
                    break;
                case castleKingSide: case castleQueenSide:
                    boardView.setSquare(pieceToMove.getPosition(), 0);
                    boardView.setSquare(position, (pieceToMove.isWhite()) ? icons.getWhiteIcon() : icons.getBlackIcon());
                    BoardAnalyzer.CastleData castleData = BoardAnalyzer.getCastlingData(pieceToMove.isWhite());
                    PieceIcons.Icons rook_icons = PieceIcons.getPieceIcons(Piece.Type.rook);
                    boolean kingSide = (selectedMove.getType() == MoveType.castleKingSide);
                    boardView.setSquare(castleData.getRookPosition(kingSide), 0);
                    boardView.setSquare(castleData.getRookFinalPosition(kingSide), (pieceToMove.isWhite()) ? rook_icons.getWhiteIcon() : rook_icons.getBlackIcon());
                    boardView.clearMarkings();
                    adapter.add(board.castle(kingSide).toString());
                    setScores();
                    break;
                case enPassat:
                    boardView.setSquare(pieceToMove.getPosition(), 0);
                    boardView.setSquare(position, (pieceToMove.isWhite()) ? icons.getWhiteIcon() : icons.getBlackIcon());
                    BoardAnalyzer.EnPassatData enPassatData = BoardAnalyzer.getEnPassatData(pieceToMove.isWhite());
                    boardView.setSquare(Positions.findPosition(enPassatData.getAttackPawnFinalRow(), position.getColumn()), 0);
                    boardView.clearMarkings();
                    adapter.add(board.enPassat(pieceToMove, position).toString());
                    setScores();
                    break;
            }
        else {
            boardView.clearMarkings();
        }
    }

    private void listPieceMovements(PieceInPosition pieceInPosition) {
        if(board.isPieceTurn(pieceInPosition)) {
            List<Move> moves = board.getMovePositions(pieceInPosition, true, false);
            for (Move move : moves) {
                MoveType type = move.getType();
                boardView.addMark(move.getToPosition(), (type.isAttacking()) ? R.drawable.ic_position_attack : R.drawable.ic_position_move);
            }
            pieceSelected = pieceInPosition;
        }
    }

    @Override
    public void onDragged(Position fromPosition, Position toPosition) {
        PieceInPosition pieceInPosition = board.getPieces().getPieceInPosition(fromPosition);
        if(pieceInPosition != null)
            setPieceMovement(toPosition, pieceInPosition);
    }

    private void setScores() {
        try {
            infoButton.setText(String.format(Locale.getDefault(), "%d : %d", board.getScore(true), board.getScore(false)));
        } catch (CheckmateGameException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestPromotion(PieceInPosition pieceInPosition) {
        promotionPiece = pieceInPosition;
        PromotionDialog.newDialog(pieceInPosition.isWhite()).show(getFragmentManager(), null);
    }

    @Override
    public void onPromotionTypeSelected(Piece.Type type) {
        if(promotionPiece != null) {
            promotionPiece.promote(type.newInstance());
            PieceIcons.Icons icons = PieceIcons.getPieceIcons(type);
            boardView.setSquare(promotionPiece.getPosition(), promotionPiece.isWhite() ? icons.getWhiteIcon() : icons.getBlackIcon());
        }
        promotionPiece = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_undo) {
            undo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void undo() {
        try {
            Move last = board.undo();
            adapter.removeLast();
            switch (last.getType()) {
                case move: case attack:
                    setSquare(last.getFromPosition(), last.getExtraPiece());
                    setSquare(last.getToPosition(), last.getPiece());
                    boardView.clearMarkings();
                    setScores();
                    break;
                case enPassat:
                    setSquare(last.getFromPosition(), null);
                    setSquare(last.getExtraPiece().getPosition(), last.getExtraPiece());
                    setSquare(last.getToPosition(), last.getPiece());
                    break;
            }
        } catch (IllegalUndoException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setSquare(Position position, @Nullable PieceInPosition pieceInPosition) {
        if(pieceInPosition == null)
            boardView.setSquare(position, 0);
        else {
            PieceIcons.Icons icons = PieceIcons.getPieceIcons(pieceInPosition.getPiece());
            boardView.setSquare(position, (pieceInPosition.isWhite()) ? icons.getWhiteIcon() : icons.getBlackIcon());
        }
    }
}
