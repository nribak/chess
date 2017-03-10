package org.ribak.chesssdk.positions;

import android.support.annotation.Nullable;

/**
 * Created by nribak on 23/02/2017.
 */

public class Position {
    public static final int N = 8;
    public static final int FIRST_ROW = 0;
    public static final int LAST_ROW = 7;
    private int row, column;

    Position(int row, int column) {
        if(isValid(row) && isValid(column)) {
            this.row = row;
            this.column = column;
        } else
            throw new IndexOutOfBoundsException("Position is not Valid");
    }

    Position() { }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Nullable
    public Position getClosePosition(int numOfRows, int numOfColumns) {
        int newRow = row + numOfRows;
        int newColumn = column + numOfColumns;
        if(isValid(newRow) && isValid(newColumn)) {
            Position position = Positions.findPosition(newRow, newColumn);
            if(!this.equals(position))
                return position;
        }
        return null;
    }

    private boolean isValid(int p) {
        return p >= 0 && p < N;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(this.getClass() != obj.getClass())
            return false;
        Position rhs = (Position) obj;
        return this.row == rhs.row && this.column == rhs.column;
    }

    @Override
    public String toString() {
        String columnString = String.valueOf(COLUMNS[column]);
        return columnString + (row + 1);
    }

    private static final char[] COLUMNS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
}
