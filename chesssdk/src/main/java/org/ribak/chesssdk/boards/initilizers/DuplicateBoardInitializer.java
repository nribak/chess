package org.ribak.chesssdk.boards.initilizers;

import org.ribak.chesssdk.utils.ObjectUtils;
import org.ribak.chesssdk.utils.PieceInPositionArray;

/**
 * Created by nribak on 26/02/2017.
 */

public class DuplicateBoardInitializer implements IBoardInitializer {
    private PieceInPositionArray pieces;

    public DuplicateBoardInitializer(PieceInPositionArray pieces) {
        this.pieces = ObjectUtils.copy(pieces);
    }

    @Override
    public PieceInPositionArray initializePieces() {
        return pieces;
    }
}
