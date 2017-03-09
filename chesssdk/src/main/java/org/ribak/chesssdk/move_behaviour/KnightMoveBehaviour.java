package org.ribak.chesssdk.move_behaviour;

import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class KnightMoveBehaviour implements IMoveBehaviour {

    @Override
    public List<Path> getMovePaths(Position currentPosition, boolean white) {
        List<Position> positions = ObjectUtils.newArrayList();
        positions.add(currentPosition.getClosePosition(2, 1));
        positions.add(currentPosition.getClosePosition(1, 2));
        positions.add(currentPosition.getClosePosition(-1, 2));
        positions.add(currentPosition.getClosePosition(-2, 1));
        positions.add(currentPosition.getClosePosition(-2, -1));
        positions.add(currentPosition.getClosePosition(-1, -2));
        positions.add(currentPosition.getClosePosition(1, -2));
        positions.add(currentPosition.getClosePosition(2, -1));

        List<Path> paths = ObjectUtils.newArrayList();
        for (Position position : positions)
            paths.add(new Path(position, getPath(currentPosition, position)));
        return paths;
    }

    @Override
    public List<Path> getAttackPaths(Position currentPosition, boolean white) {
        return this.getMovePaths(currentPosition, white);
    }

    @Override
    public List<Position> getPath(Position positionA, Position positionB) {
        return new ArrayList<>();
    }
}
