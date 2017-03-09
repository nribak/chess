package org.ribak.chess.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.ribak.chesssdk.positions.Position;

/**
 * Created by nribak on 24/02/2017.
 */

public class SquareView extends RelativeLayout {
    private static final int[] colors = {android.R.color.darker_gray, android.R.color.holo_red_light};

    private Position position;
    private ImageView iconImageView, markImageView;
    public SquareView(Context context, Position position) {
        super(context);
        this.position = position;
        init();
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        LinearLayout.LayoutParams horizontalParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
        setLayoutParams(horizontalParams);
        setBackgroundResource(colors[(position.getRow() + position.getColumn()) % colors.length]);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        iconImageView = new ImageView(getContext());
        iconImageView.setLayoutParams(params);

        markImageView = new ImageView(getContext());
        markImageView.setLayoutParams(params);

        addView(iconImageView);
        addView(markImageView);

    }

    public Position getPosition() {
        return position;
    }

    public void setPieceIcon(@DrawableRes int pieceIcon) {
        if(pieceIcon == 0)
            iconImageView.setImageDrawable(null);
        else
            iconImageView.setImageResource(pieceIcon);
    }

    public void changeMark(@DrawableRes int markResId) {
        if(markResId == 0)
            markImageView.setImageDrawable(null);
        else
            markImageView.setImageResource(markResId);
    }

    public View getShadowIcon() {
        return iconImageView;
    }
}
