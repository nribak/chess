package org.ribak.chesssdk.boards;

/**
 * Created by nribak on 23/02/2017.
 */

public enum BoardState {
    normal(0,0, false),
    whiteCheck(0,0, false), // white is checking black
    blackCheck(0,0, false), // black is checking white
    whiteCheckMate(1,-1, true), // white checkmates black
    blackCheckMate(-1,1, true), // black checkmates white
    staleMate(0.5f,0.5f, true),
    draw(0.5f,0.5f, true);

    private float gameWhiteScore, gameBlackScore;
    private boolean finished;

    BoardState(float gameWhiteScore, float gameBlackScore, boolean finished) {
        this.gameWhiteScore = gameWhiteScore;
        this.gameBlackScore = gameBlackScore;
        this.finished = finished;
    }

    public float getGameWhiteScore() {
        return gameWhiteScore;
    }

    public float getGameBlackScore() {
        return gameBlackScore;
    }

    public boolean isFinished() {
        return finished;
    }
}
