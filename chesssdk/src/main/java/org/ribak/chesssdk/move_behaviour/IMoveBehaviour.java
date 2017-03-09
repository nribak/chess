package org.ribak.chesssdk.move_behaviour;

import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;

import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public interface IMoveBehaviour {
    List<Path> getMovePaths(Position currentPosition, boolean white);
    List<Path> getAttackPaths(Position currentPosition, boolean white);

    List<Position> getPath(Position positionA, Position positionB);

}
