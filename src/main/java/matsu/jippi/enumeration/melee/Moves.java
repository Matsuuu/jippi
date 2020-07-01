package matsu.jippi.enumeration.melee;

import matsu.jippi.pojo.melee.Move;

public enum Moves {

    MISCELLANEOUS(new Move(1, "Miscellaneous", "misc")), JAB(new Move(2, "Jab", "jab")),
    JAB_2(new Move(3, "Jab", "jab")), JAB_3(new Move(4, "Jab", "jab")),
    RAPID_JABS(new Move(5, "Rapid Jabs", "rapid-jabs")), DASH_ATTACK(new Move(6, "Dash Attack", "dash")),
    FORWARD_TILT(new Move(7, "Forward Tilt", "ftilt")), UP_TILT(new Move(8, "Up Tilt", "utilt")),
    DOWN_TILT(new Move(9, "Down Tilt", "dtilt")), FORWARD_SMASH(new Move(10, "Forward Smash", "fsmash")),
    UP_SMASH(new Move(11, "Up Smash", "usmash")), DOWN_SMASH(new Move(12, "Down Smash", "dsmash")),
    NEUTRAL_AIR(new Move(13, "Neutral Air", "nair")), FORWARD_AIR(new Move(14, "Forward Air", "fair")),
    BACK_AIR(new Move(15, "Back Air", "bair")), UP_AIR(new Move(16, "Up Air", "uair")),
    DOWN_AIR(new Move(17, "Down Air", "dair")), NEUTRAL_B(new Move(18, "Neutral B", "neutral-b")),
    SIDE_B(new Move(19, "Side B", "side-b")), UP_B(new Move(20, "Up B", "up-b")),
    DOWN_B(new Move(21, "Down B", "down-b")), GETUP_ATTACK(new Move(50, "Getup Attack", "getup")),
    GETUP_ATTACK_SLOW(new Move(51, "Getup Attack (Slow)", "getup-slow")),
    GRAB_PUMMEL(new Move(52, "Grab Pummel", "pummel")), FORWARD_THROW(new Move(53, "Forward Throw", "fthrow")),
    BACK_THROW(new Move(54, "Back Throw", "bthrow")), UP_THROW(new Move(55, "Up Throw", "uthrow")),
    DOWN_THROW(new Move(56, "Down Throw", "dthrow")), EDGE_ARRACK_SLOW(new Move(61, "Edge Attack (Slow)", "edge-slow")),
    EDGE_ATTACK(new Move(62, "Edge Attack", "edge"));

    private Move move;

    private Moves(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public static Moves byId(int id) {
        for (Moves move : Moves.values()) {
            if (move.getMove().getId() == id) {
                return move;
            }
        }
        return null;
    }

    public static Moves byName(String name) {
        for (Moves move : Moves.values()) {
            if (move.getMove().getName().equals(name)) {
                return move;
            }
        }
        return null;
    }
}
