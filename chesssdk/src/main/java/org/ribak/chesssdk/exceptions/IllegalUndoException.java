package org.ribak.chesssdk.exceptions;

/**
 * Created by nribak on 03/03/2017.
 */

public class IllegalUndoException extends IllegalMoveException {

    public IllegalUndoException() {
        super("Undo is not Allowed");
    }
}
