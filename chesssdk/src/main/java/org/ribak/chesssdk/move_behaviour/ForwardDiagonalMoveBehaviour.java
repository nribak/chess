package org.ribak.chesssdk.move_behaviour;

import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.utils.ObjectUtils;

import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public class ForwardDiagonalMoveBehaviour implements IMoveBehaviour {
    private boolean singleSquare;

    public ForwardDiagonalMoveBehaviour() { }

    public ForwardDiagonalMoveBehaviour(boolean singleSquare) {
        this.singleSquare = singleSquare;
    }

    @Override
    public List<Path> getMovePaths(Position currentPosition, boolean white) {
        List<Path> paths = ObjectUtils.newArrayList();
        int n = (singleSquare) ? 1 : Position.N - 1;
        for (int i = -n; i <= n; i++) {
            Position p = currentPosition.getClosePosition(i, i);
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
                positions.add(positionB.getClosePosition(i, i));
        else
            for (int i = 1; i < positionB.getRow() - positionA.getRow(); i++)
                positions.add(positionA.getClosePosition(i, i));
        return positions;
    }
}
