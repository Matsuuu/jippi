package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.List;

import matsu.jippi.enumeration.stats.State;
import matsu.jippi.pojo.common.GameStartType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PostFrameUpdateType;

public class StatsQuerier {
    public static List<PlayerIndexedType> getSinglesPlayerPermutationsFromSettings(GameStartType settings) {
        if (settings == null || settings.getPlayers().size() != 2) {
            return new ArrayList<>();
        }
        List<PlayerIndexedType> players = new ArrayList<>();
        players.add(new PlayerIndexedType(settings.getPlayers().get(0).getPlayerIndex(),
                settings.getPlayers().get(1).getPlayerIndex()));
        players.add(new PlayerIndexedType(settings.getPlayers().get(1).getPlayerIndex(),
                settings.getPlayers().get(0).getPlayerIndex()));

        return players;
    }

    public static boolean didLoseStock(PostFrameUpdateType frame, PostFrameUpdateType prevFrame) {
        if (frame == null || prevFrame == null) {
            return false;
        }

        return (prevFrame.getStocksRemaining() - frame.getStocksRemaining()) > 0;
    }

    public static boolean isInControl(int state) {
        boolean ground = state >= State.GROUNDED_CONTROL_START.getHex() && state <= State.GROUNDED_CONTROL_END.getHex();
        boolean squat = state > State.SQUAT_START.getHex() && state <= State.SQUAT_END.getHex();
        boolean groundAttack = state > State.GROUND_ATTACK_START.getHex() && state <= State.GROUND_ATTACK_END.getHex();
        boolean isGrab = state == State.GRAB.getHex();

        return ground || squat || groundAttack || isGrab;
    }

    public static boolean isTeching(int state) {
        return state >= State.TECH_START.getHex() && state <= State.TECH_END.getHex();
    }

    public static boolean isDown(int state) {
        return state >= State.DOWN_START.getHex() && state <= State.DOWN_END.getHex();
    }

    public static boolean isDamaged(int state) {

        return state >= State.DAMAGE_START.getHex() && state <= State.DAMAGE_END.getHex();
    }

    public static boolean isGrabbed(int state) {
        return state >= State.CAPTURE_START.getHex() && state <= State.CAPTURE_END.getHex();
    }

    public static boolean isDead(int state) {
        return state >= State.DYING_START.getHex() && state <= State.DYING_END.getHex();
    }

    public static Integer calcDamageTaken(PostFrameUpdateType frame, PostFrameUpdateType prevFrame) {
        int percent = frame.getPercent() != null ? frame.getPercent() : 0;
        int prevPercent = prevFrame.getPercent() != null ? prevFrame.getPercent() : 0;

        return percent - prevPercent;
    }

}
