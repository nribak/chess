package org.ribak.chesssdk.move_behaviour;

import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.utils.ObjectUtils;

import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class PawnMoveBehaviour implements IMoveBehaviour {

    private boolean isInitPosition(int row, boolean white) {
        if(white)
            return row == 1;
        else
            return row == Position.N - 2;
    }

    @Override
    public List<Path> getMovePaths(Position currentPosition, boolean white) {
        List<Path> paths = ObjectUtils.newArrayList();
        Position p = currentPosition.getClosePosition((white) ? 1 : -1, 0);
        if(p != null)
            paths.add(new Path(p, getPath(currentPosition, p)));
        if(isInitPosition(currentPosition.getRow(), white)) {
            p = currentPosition.getClosePosition((white) ? 2 : -2, 0);
            if(p != null)
                paths.add(new Path(p, getPath(currentPosition, p)));
        }
        return paths;
    }

    @Override
    public List<Path> getAttackPaths(Position currentPosition, boolean white) {
        List<Path> paths = ObjectUtils.newArrayList();
        Position p = currentPosition.getClosePosition((white) ? 1 : -1, 1);
        if(p != null)
            paths.add(new Path(p, getPath(currentPosition, p)));
        p = currentPosition.getClosePosition((white) ? 1 : -1, -1);
        if(p != null)
            paths.add(new Path(p, getPath(currentPosition, p)));
        return paths;
    }

    @Override
    public List<Position> getPath(Position positionA, Position positionB) {
        List<Position> positions = ObjectUtils.newArrayList();
        if(positionA.getRow() > positionB.getRow())
            for (int i = 1; i < positionA.getRow() - positionB.getRow(); i++)
                positions.add(positionB.getClosePosition(i, 0));
        else
            for (int i = 1; i < positionB.getRow() - positionA.getRow(); i++)
                positions.add(positionA.getClosePosition(i, 0));
        return positions;    }
}
