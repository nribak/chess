package org.ribak.chesssdk.pieces;

import org.ribak.chesssdk.move_behaviour.IMoveBehaviour;
import org.ribak.chesssdk.positions.Path;
import org.ribak.chesssdk.positions.Position;
import org.ribak.chesssdk.exceptions.CheckmateGameException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nribak on 23/02/2017.
 */

public abstract class Piece {
    private String fullName;
    private String letter;
    List<IMoveBehaviour> moveBehaviours;

    Piece(String fullName, String letter) {
        this.fullName = fullName;
        this.letter = letter;
        this.moveBehaviours = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getLetter() {
        return letter;
    }

    public abstract int getScore() throws CheckmateGameException;

    public abstract Type getType();

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(this.getClass() != obj.getClass())
            return false;
        Piece rhs = (Piece) obj;
        return this.letter.equals(rhs.letter);
    }

    public List<Path> getAllPossibleMovePositions(Position currentPositions, boolean white) {
        List<Path> paths = new ArrayList<>();
        for (IMoveBehaviour moveBehaviour : moveBehaviours)
            paths.addAll(moveBehaviour.getMovePaths(currentPositions, white));
        return paths;
    }

    public List<Path> getAllPossibleAttackPositions(Position currentPositions, boolean white) {
        List<Path> paths = new ArrayList<>();
        for (IMoveBehaviour moveBehaviour : moveBehaviours)
            paths.addAll(moveBehaviour.getAttackPaths(currentPositions, white));
        return paths;
    }

    public enum Type {
        pawn(Pawn.NAME, Pawn.class),
        bishop(Bishop.NAME, Bishop.class),
        knight(Knight.NAME, Knight.class),
        rook(Rook.NAME, Rook.class),
        queen(Queen.NAME, Queen.class),
        king(King.NAME, King.class);

        private String typeName;
        private Class<? extends Piece> cls;

        Type(String typeName, Class<? extends Piece> cls) {
            this.typeName = typeName;
            this.cls = cls;
        }

        public Piece newInstance() {
            try {
                return cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        public String getTypeName() {
            return typeName;
        }

        public static Type[] getPossiblePromotionTypes() {
            return new Type[] {bishop, knight, rook, queen};
        }
    }
}
