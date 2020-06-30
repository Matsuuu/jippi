package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import matsu.jippi.enumeration.stats.State;
import matsu.jippi.pojo.common.ActionCountsType;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.PlayerActionState;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PostFrameUpdateType;

public class ActionsComputer implements StatComputer<List<ActionCountsType>> {
    private final List<State> dashDanceAnimations = new ArrayList<>(Arrays.asList(State.DASH, State.TURN, State.DASH));
    private List<PlayerIndexedType> playerPermutations = new ArrayList<>();
    private Map<PlayerIndexedType, PlayerActionState> state = new HashMap<>();

    public void setPlayerPermutations(List<PlayerIndexedType> playerPermutations) {
        this.playerPermutations = playerPermutations;
        playerPermutations.forEach((indices) -> {
            ActionCountsType playerCounts = new ActionCountsType(
                    new PlayerIndexedType(indices.getPlayerIndex(), indices.getOpponentIndex()), 0, 0, 0, 0, 0, 0, 0);
            PlayerActionState playerState = new PlayerActionState(playerCounts, new ArrayList<>());
            this.state.put(indices, playerState);
        });
    }

    public void processFrame(FrameEntryType frame, FramesType allFrames) {
        playerPermutations.forEach((indices) -> {
            PlayerActionState currentState = state.get(indices);
            handleActionCompute(currentState, indices, frame);
        });
    }

    public List<ActionCountsType> fetch() {
        List<ActionCountsType> actionCounts = new ArrayList<>();
        for (PlayerActionState actionState : state.values()) {
            actionCounts.add(actionState.getPlayerCounts());
        }
        return actionCounts;
    }

    public void handleActionCompute(PlayerActionState actionState, PlayerIndexedType indices, FrameEntryType frame) {
        PostFrameUpdateType playerFrame = frame.getPlayers().get(indices.getPlayerIndex()).getPost();

        actionState.getAnimations().add(playerFrame.getActionStateId());

        // Grab last 3 frames
        List<Integer> last3Frames = actionState.getAnimations().subList(actionState.getAnimations().size() - 4,
                actionState.getAnimations().size() - 1);
        int currentAnimation = playerFrame.getActionStateId();
        int previousAnimation = last3Frames.get(last3Frames.size() - 2);
        State currentAnimationState = State.from(currentAnimation);
        State previousAnimationState = State.from(previousAnimation);

        boolean didDashDance = last3Frames.equals(dashDanceAnimations);
        boolean didRoll = ActionQuerier.didStartSpotDodge(currentAnimationState, previousAnimationState);
        boolean didSpotDodge = ActionQuerier.didStartSpotDodge(currentAnimationState, previousAnimationState);
        boolean didAirDodge = ActionQuerier.didStartAirDodge(currentAnimationState, previousAnimationState);
        boolean didGrabLedge = ActionQuerier.didStartLedgeGrab(currentAnimationState, previousAnimationState);

        incrementCount(actionState, "dashDanceCount", didDashDance);
        incrementCount(actionState, "rollCount", didRoll);
        incrementCount(actionState, "spotDodgeCount", didSpotDodge);
        incrementCount(actionState, "airDodgeCount", didAirDodge);
        incrementCount(actionState, "ledgegrabCount", didGrabLedge);

        List<State> animations = actionState.getAnimations().stream().map((animHex) -> State.from(animHex))
                .collect(Collectors.toList());

        handleActionWaveDash(actionState.getPlayerCounts(), animations);
    }

    public void handleActionWaveDash(ActionCountsType counts, List<State> animations) {
        State currentAnimation = animations.get(animations.size() - 1);
        State previousAnimation = animations.get(animations.size() - 2);

        boolean isSpecialLanding = currentAnimation == State.LANDING_FALL_SPECIAL;
        boolean isAcceptablePrevious = ActionQuerier.isWavedashInitiationAnimation(previousAnimation);
        boolean isPossibleWavedash = isSpecialLanding && isAcceptablePrevious;

        if (!isPossibleWavedash) {
            return;
        }

        List<State> recentFrames = animations.subList(animations.size() - 9, animations.size() - 1);

        if (recentFrames.size() == 2 && recentFrames.contains(State.AIR_DODGE)) {
            return;
        }

        if (recentFrames.contains(State.AIR_DODGE)) {
            counts.setAirDodgeCount(counts.getAirDodgeCount() + 1);
        }
        if (recentFrames.contains(State.ACTION_KNEE_BEND)) {
            counts.setWavedashCount(counts.getWavedashCount() + 1);
        } else {
            counts.setWavelandCount(counts.getWavelandCount() + 1);
        }
    }

    private void incrementCount(PlayerActionState actionState, String field, boolean condition) {
        if (!condition)
            return;

        switch (field) {
            case "waveDashCount":
                actionState.getPlayerCounts().setWavedashCount(actionState.getPlayerCounts().getWavedashCount() + 1);
                break;
            case "waveLandCount":
                actionState.getPlayerCounts().setWavelandCount(actionState.getPlayerCounts().getWavelandCount() + 1);
                break;
            case "airDodgeCount":
                actionState.getPlayerCounts().setAirDodgeCount(actionState.getPlayerCounts().getAirDodgeCount() + 1);
                break;
            case "dashDanceCount":
                actionState.getPlayerCounts().setDashDanceCount(actionState.getPlayerCounts().getDashDanceCount() + 1);
                break;
            case "spotDodgeCount":
                actionState.getPlayerCounts().setSpotDodgeCount(actionState.getPlayerCounts().getSpotDodgeCount() + 1);
                break;
            case "ledgegrabCount":
                actionState.getPlayerCounts().setLedgegrabCount(actionState.getPlayerCounts().getLedgegrabCount() + 1);
                break;
            case "rollCount":
                actionState.getPlayerCounts().setRollCount(actionState.getPlayerCounts().getRollCount() + 1);
                break;
        }
    }
}
