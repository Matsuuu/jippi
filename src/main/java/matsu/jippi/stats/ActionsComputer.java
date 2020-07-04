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
        for (PlayerIndexedType indices : playerPermutations) {
            actionCounts.add(this.state.get(indices).getPlayerCounts());
        }
        return actionCounts;
    }

    private List<Integer> getLast3Frames(PlayerActionState actionState) {
        int listSize = actionState.getAnimations().size();
        return listSize < 4 ? actionState.getAnimations()
                : actionState.getAnimations().subList(listSize - 4, listSize - 1);
    }

    private int getCurrentAnimation(List<Integer> frames) {
        if (frames.size() < 2) {
            return frames.get(0);
        }
        return frames.get(frames.size() - 1);
    }

    private Integer getPreviousAnimation(List<Integer> frames) {
        if (frames.size() < 2) {
            return null;
        }
        return frames.get(frames.size() - 2);
    }

    private boolean isDashDanceAnimation(List<Integer> animations) {
        for (int i = 0; i < animations.size(); i++) {
            if (animations.get(i) != dashDanceAnimations.get(i).getHex()) {
                return false;
            }
        }
        return true;
    }

    public void handleActionCompute(PlayerActionState actionState, PlayerIndexedType indices, FrameEntryType frame) {
        PostFrameUpdateType playerFrame = frame.getPlayers().get(indices.getPlayerIndex()).getPost();

        actionState.getAnimations().add(playerFrame.getActionStateId());

        // Grab last 3 frames
        List<Integer> last3Frames = getLast3Frames(actionState);
        int currentAnimation = playerFrame.getActionStateId();
        Integer previousAnimation = getPreviousAnimation(last3Frames);
        State currentAnimationState = State.from(currentAnimation);
        State previousAnimationState = State.from(previousAnimation);

        boolean didDashDance = isDashDanceAnimation(last3Frames);
        boolean didRoll = ActionQuerier.didStartSpotDodge(currentAnimationState, previousAnimationState);
        boolean didSpotDodge = ActionQuerier.didStartSpotDodge(currentAnimationState, previousAnimationState);
        boolean didAirDodge = ActionQuerier.didStartAirDodge(currentAnimationState, previousAnimationState);
        boolean didGrabLedge = ActionQuerier.didStartLedgeGrab(currentAnimationState, previousAnimationState);

        incrementCount(actionState, "dashDanceCount", didDashDance);
        incrementCount(actionState, "rollCount", didRoll);
        incrementCount(actionState, "spotDodgeCount", didSpotDodge);
        incrementCount(actionState, "airDodgeCount", didAirDodge);
        incrementCount(actionState, "ledgegrabCount", didGrabLedge);

        List<State> animations = actionState.getAnimations().stream().map(State::from).collect(Collectors.toList());

        handleActionWaveDash(actionState, animations);
    }

    public void handleActionWaveDash(PlayerActionState actionState, List<State> animations) {
        State currentAnimation = animations.size() > 1 ? animations.get(animations.size() - 1) : animations.get(0);
        State previousAnimation = animations.size() > 1 ? animations.get(animations.size() - 2) : null;

        boolean isSpecialLanding = currentAnimation != null
                && currentAnimation.getHex() == State.LANDING_FALL_SPECIAL.getHex();
        boolean isAcceptablePrevious = previousAnimation != null
                && ActionQuerier.isWavedashInitiationAnimation(previousAnimation);
        boolean isPossibleWavedash = isSpecialLanding && isAcceptablePrevious;

        if (!isPossibleWavedash) {
            return;
        }

        int startIndex = animations.size() - 8;
        int endIndex = animations.size();
        List<State> recentFrames = animations.subList(startIndex, endIndex);
        Map<Integer, State> recentAnimations = new HashMap<>();
        for (State recentFrame : recentFrames) {
            if (recentFrame == null)
                continue;
            recentAnimations.put(recentFrame.getHex(), recentFrame);
        }

        if (recentAnimations.size() == 2 && recentAnimations.containsKey(State.AIR_DODGE.getHex())) {
            return;
        }

        if (recentAnimations.containsKey(State.AIR_DODGE.getHex())) {
            actionState.getPlayerCounts().decrementAirDodgeCount();
        }

        if (recentAnimations.containsKey(State.ACTION_KNEE_BEND.getHex())) {
            actionState.getPlayerCounts().incrementWaveDashCount();
        } else {
            actionState.getPlayerCounts().incrementWavelandCount();
        }
    }

    private void incrementCount(PlayerActionState actionState, String field, boolean condition) {
        if (!condition)
            return;

        switch (field) {
            case "airDodgeCount":
                actionState.getPlayerCounts().incrementAirDodgeCount();
                break;
            case "dashDanceCount":
                actionState.getPlayerCounts().incrementDashDanceCount();
                break;
            case "spotDodgeCount":
                actionState.getPlayerCounts().incrementSpotDodgeCount();
                break;
            case "ledgegrabCount":
                actionState.getPlayerCounts().incrementLedgegrabCount();
                break;
            case "rollCount":
                actionState.getPlayerCounts().incrementRollCount();
                break;
        }
    }
}
