package org.ribak.chess.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.positions.Positions;

/**
 * Created by nribak on 24/02/2017.
 */

public class BoardView extends LinearLayout {

    public interface OnBoardViewChangedListener {
        void onSquareClicked(Position position);
        void onDragged(Position fromPosition, Position toPosition);
    }
    private OnBoardViewChangedListener onBoardViewChangedListener;

    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutParams verticalParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        for (int i = 0; i < Position.N; i++) {
            LinearLayout rowLayout = new LinearLayout(getContext());
            rowLayout.setLayoutParams(verticalParams);
            for (int j = 0; j < Position.N; j++) {
                SquareView squareView = new SquareView(getContext(), Positions.findPosition(Position.N - i - 1, j));
                squareView.setOnTouchListener(onTouchListener);
                squareView.setOnDragListener(onDragListener);
                rowLayout.addView(squareView);
            }
            this.addView(rowLayout);
        }
    }

    private SquareView getSquareView(Position position) {
        LinearLayout layout = (LinearLayout) getChildAt(Position.N - position.getRow() -1);
        return (SquareView) layout.getChildAt(position.getColumn());
    }
    public void setOnBoardViewChangedListener(OnBoardViewChangedListener onBoardViewChangedListener) {
        this.onBoardViewChangedListener = onBoardViewChangedListener;
    }

    public void setSquare(Position position, @DrawableRes int iconResId) {
        SquareView imageView = getSquareView(position);
        imageView.setPieceIcon(iconResId);
    }
    public void addMark(Position position, @DrawableRes int markResId) {
        SquareView imageView = getSquareView(position);
        imageView.changeMark(markResId);
    }
    public void clearMarkings() {
        for (int i = 0; i < Position.N; i++)
            for (int j = 0; j < Position.N; j++)
                addMark(Positions.findPosition(i, j), 0);
    }
    private OnDragListener onDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            if(event.getAction() == DragEvent.ACTION_DROP) {
                SquareView toView = (SquareView) v;
                SquareView fromView = (SquareView) event.getLocalState();
                if(fromView != null) {
                    Position fromPosition = fromView.getPosition();
                    Position toPosition = toView.getPosition();
                    fromView.stopDrag();
                    if(fromPosition == toPosition)
                        onBoardViewChangedListener.onSquareClicked(fromPosition);
                    else
                        onBoardViewChangedListener.onDragged(fromPosition, toPosition);
                }
            }
            return true;
        }
    };

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            SquareView view  = (SquareView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    view.startDrag();
            return true;
        }
    };

}
