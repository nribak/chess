package org.ribak.chesssdk.positions;

import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class Path {
    private Position finalPosition;
    private List<Position> throughPositions;

    public Path() { }

    public Path(Position finalPosition, List<Position> throughPositions) {
        this.finalPosition = finalPosition;
        this.throughPositions = throughPositions;
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public List<Position> getThroughPositions() {
        return throughPositions;
    }

}
