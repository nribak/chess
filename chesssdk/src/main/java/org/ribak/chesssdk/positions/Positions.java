package org.ribak.chesssdk.positions;

import android.support.annotation.Nullable;

/**
 * Created by nribak on 23/02/2017.
 */

public class Positions {
    public static final Position P_1A = new Position(0, 0);
    public static final Position P_1B = new Position(0, 1);
    public static final Position P_1C = new Position(0, 2);
    public static final Position P_1D = new Position(0, 3);
    public static final Position P_1E = new Position(0, 4);
    public static final Position P_1F = new Position(0, 5);
    public static final Position P_1G = new Position(0, 6);
    public static final Position P_1H = new Position(0, 7);

    public static final Position P_2A = new Position(1, 0);
    public static final Position P_2B = new Position(1, 1);
    public static final Position P_2C = new Position(1, 2);
    public static final Position P_2D = new Position(1, 3);
    public static final Position P_2E = new Position(1, 4);
    public static final Position P_2F = new Position(1, 5);
    public static final Position P_2G = new Position(1, 6);
    public static final Position P_2H = new Position(1, 7);

    public static final Position P_3A = new Position(2, 0);
    public static final Position P_3B = new Position(2, 1);
    public static final Position P_3C = new Position(2, 2);
    public static final Position P_3D = new Position(2, 3);
    public static final Position P_3E = new Position(2, 4);
    public static final Position P_3F = new Position(2, 5);
    public static final Position P_3G = new Position(2, 6);
    public static final Position P_3H = new Position(2, 7);

    public static final Position P_4A = new Position(3, 0);
    public static final Position P_4B = new Position(3, 1);
    public static final Position P_4C = new Position(3, 2);
    public static final Position P_4D = new Position(3, 3);
    public static final Position P_4E = new Position(3, 4);
    public static final Position P_4F = new Position(3, 5);
    public static final Position P_4G = new Position(3, 6);
    public static final Position P_4H = new Position(3, 7);

    public static final Position P_5A = new Position(4, 0);
    public static final Position P_5B = new Position(4, 1);
    public static final Position P_5C = new Position(4, 2);
    public static final Position P_5D = new Position(4, 3);
    public static final Position P_5E = new Position(4, 4);
    public static final Position P_5F = new Position(4, 5);
    public static final Position P_5G = new Position(4, 6);
    public static final Position P_5H = new Position(4, 7);

    public static final Position P_6A = new Position(5, 0);
    public static final Position P_6B = new Position(5, 1);
    public static final Position P_6C = new Position(5, 2);
    public static final Position P_6D = new Position(5, 3);
    public static final Position P_6E = new Position(5, 4);
    public static final Position P_6F = new Position(5, 5);
    public static final Position P_6G = new Position(5, 6);
    public static final Position P_6H = new Position(5, 7);

    public static final Position P_7A = new Position(6, 0);
    public static final Position P_7B = new Position(6, 1);
    public static final Position P_7C = new Position(6, 2);
    public static final Position P_7D = new Position(6, 3);
    public static final Position P_7E = new Position(6, 4);
    public static final Position P_7F = new Position(6, 5);
    public static final Position P_7G = new Position(6, 6);
    public static final Position P_7H = new Position(6, 7);

    public static final Position P_8A = new Position(7, 0);
    public static final Position P_8B = new Position(7, 1);
    public static final Position P_8C = new Position(7, 2);
    public static final Position P_8D = new Position(7, 3);
    public static final Position P_8E = new Position(7, 4);
    public static final Position P_8F = new Position(7, 5);
    public static final Position P_8G = new Position(7, 6);
    public static final Position P_8H = new Position(7, 7);

    public static final Position[] ALL_POSITIONS = {
            P_1A, P_1B, P_1C, P_1D, P_1E, P_1F, P_1G, P_1H,
            P_2A, P_2B, P_2C, P_2D, P_2E, P_2F, P_2G, P_2H,
            P_3A, P_3B, P_3C, P_3D, P_3E, P_3F, P_3G, P_3H,
            P_4A, P_4B, P_4C, P_4D, P_4E, P_4F, P_4G, P_4H,
            P_5A, P_5B, P_5C, P_5D, P_5E, P_5F, P_5G, P_5H,
            P_6A, P_6B, P_6C, P_6D, P_6E, P_6F, P_6G, P_6H,
            P_7A, P_7B, P_7C, P_7D, P_7E, P_7F, P_7G, P_7H,
            P_8A, P_8B, P_8C, P_8D, P_8E, P_8F, P_8G, P_8H
    };

    @Nullable
    public static Position findPosition(int row, int column) {
        for (Position position : ALL_POSITIONS)
            if(position.getRow() == row && position.getColumn() == column)
                return position;
        return null;
    }

    private Positions() { }
}
