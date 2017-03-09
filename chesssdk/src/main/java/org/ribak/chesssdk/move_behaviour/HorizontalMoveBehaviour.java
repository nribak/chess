package org.ribak.chesssdk.move_behaviour;

import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.utils.ObjectUtils;

import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class HorizontalMoveBehaviour implements IMoveBehaviour {
    private boolean singleSquareMove;

    public HorizontalMoveBehaviour() { }

    public HorizontalMoveBehaviour(boolean singleSquareMove) {
        this.singleSquareMove = singleSquareMove;
    }

    @Override
    public List<Path> getMovePaths(Position currentPosition, boolean white) {
        List<Path> paths = ObjectUtils.newArrayList();
        int n = (singleSquareMove) ? 1 : Position.N - 1;
        for (int i = -n; i <= n; i++) {
            Position p = currentPosition.getClosePosition(0, i);
            if(p != null)
                paths.add(new Path(p, getPath(currentPosition, p)));
        }
        return paths;
    }

    @Override
    public List<Path> getAttackPaths(Position currentPosition, boolean white) {
        return this.getMovePaths(currentPosition, white);
    }

    @Override
    public List<Position> getPath(Position positionA, Position positionB) {
        List<Position> positions = ObjectUtils.newArrayList();
        if(positionA.getColumn() > positionB.getColumn())
            for (int i = 1; i < positionA.getColumn() - positionB.getColumn(); i++)
                positions.add(positionB.getClosePosition(0, i));
        else
            for (int i = 1; i < positionB.getColumn() - positionA.getColumn(); i++)
                positions.add(positionA.getClosePosition(0, i));
        return positions;
    }
}
