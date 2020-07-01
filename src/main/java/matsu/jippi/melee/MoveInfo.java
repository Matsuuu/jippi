package matsu.jippi.melee;

import matsu.jippi.enumeration.melee.Moves;
import matsu.jippi.pojo.melee.Move;

public class MoveInfo {

    public static Move getMoveInfo(int moveId) {
        Move m = Moves.byId(moveId).getMove();
        if (m == null) {
            return Move.unknown();
        }
        return m;
    }

    public static String getMoveShortName(int moveId) {
        return getMoveInfo(moveId).getShortName();
    }

    public static String getMoveName(int moveId) {
        return getMoveInfo(moveId).getName();
    }
}
