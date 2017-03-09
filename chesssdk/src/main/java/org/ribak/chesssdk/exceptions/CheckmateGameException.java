package org.ribak.chesssdk.exceptions;

/**
 * Created by nribak on 03/03/2017.
 */

public class CheckmateGameException extends Exception {

    public CheckmateGameException() {
        super("Game is checkmated. Cannot play");
    }
}
