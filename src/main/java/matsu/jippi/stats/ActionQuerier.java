package matsu.jippi.stats;

import matsu.jippi.enumeration.stats.State;

public class ActionQuerier {
    public static boolean isRolling(State animation) {
        return animation.getHex() == State.ROLL_BACKWARD.getHex() || animation.getHex() == State.ROLL_FORWARD.getHex();
    }

    public static boolean didStartRoll(State currentAnimation, State previousAnimation) {
        boolean isCurrentlyRolling = ActionQuerier.isRolling(currentAnimation);
        boolean wasPreviouslyRolling = ActionQuerier.isRolling(previousAnimation);

        return isCurrentlyRolling && !wasPreviouslyRolling;
    }

    public static boolean isSpotDodging(State animation) {
        return animation != null && animation.getHex() == State.SPOT_DODGE.getHex();
    }

    public static boolean didStartSpotDodge(State currentAnimation, State previousAnimation) {
        boolean isCurrentlyDodging = ActionQuerier.isSpotDodging(currentAnimation);
        boolean wasPreviouslyDodging = ActionQuerier.isSpotDodging(previousAnimation);

        return isCurrentlyDodging && !wasPreviouslyDodging;
    }

    public static boolean isAirDodging(State animation) {
        return animation != null && animation.getHex() == State.AIR_DODGE.getHex();
    }

    public static boolean didStartAirDodge(State currentAnimation, State previousAnimation) {
        if (previousAnimation == State.UNKNOWN) {
            // If we come from an unknown state, we don't count it as a air dodge
            // TODO: Find out what the fuck those are.
            return false;
        }
        boolean isCurrentlyDodging = ActionQuerier.isAirDodging(currentAnimation);
        boolean wasPreviouslyDodging = ActionQuerier.isAirDodging(previousAnimation);

        return isCurrentlyDodging && !wasPreviouslyDodging;
    }

    public static boolean isGrabbingLedge(State animation) {
        return animation != null && animation.getHex() == State.CLIFF_CATCH.getHex();
    }

    public static boolean didStartLedgeGrab(State currentAnimation, State previousAnimation) {
        boolean isCurrentlyGrabbing = ActionQuerier.isGrabbingLedge(currentAnimation);
        boolean wasPreviouslyGrabbing = ActionQuerier.isGrabbingLedge(previousAnimation);

        return isCurrentlyGrabbing && !wasPreviouslyGrabbing;
    }

    public static boolean isWavedashInitiationAnimation(State animation) {
        if (animation.getHex() == State.AIR_DODGE.getHex())
            return true;

        boolean isAboveMin = animation.getHex() >= State.CONTROLLED_JUMP_START.getHex();
        boolean isBelowMax = animation.getHex() <= State.CONTROLLED_JUMP_END.getHex();
        return isAboveMin && isBelowMax;
    }

}
