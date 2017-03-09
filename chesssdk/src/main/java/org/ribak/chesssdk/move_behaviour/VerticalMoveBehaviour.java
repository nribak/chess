package org.ribak.chesssdk.move_behaviour;

import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.utils.ObjectUtils;

import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class VerticalMoveBehaviour implements IMoveBehaviour {
    private boolean singleSquareMove;

    public VerticalMoveBehaviour() { }

    public VerticalMoveBehaviour(boolean singleSquareMove) {
        this.singleSquareMove = singleSquareMove;
    }

    @Override
    public List<Path> getMovePaths(Position currentPosition, boolean white) {
        List<Path> paths = ObjectUtils.newArrayList();
        int n = (singleSquareMove) ? 1 : Position.N - 1;
        for (int i = -n; i <= n; i++) {
            Position p = currentPosition.getClosePosition(i, 0);
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
        if(positionA.getRow() > positionB.getRow())
            for (int i = 1; i < positionA.getRow() - positionB.getRow(); i++)
                positions.add(positionB.getClosePosition(i, 0));
        else
            for (int i = 1; i < positionB.getRow() - positionA.getRow(); i++)
                positions.add(positionA.getClosePosition(i, 0));
        return positions;
    }
}
