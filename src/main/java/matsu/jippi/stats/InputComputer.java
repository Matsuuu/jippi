package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import matsu.jippi.enumeration.inputs.JoystickRegion;
import matsu.jippi.enumeration.stats.Frames;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PlayerInput;
import matsu.jippi.pojo.common.PreFrameUpdateType;

public class InputComputer implements StatComputer<List<PlayerInput>> {
    private List<PlayerIndexedType> playerPermutations = new ArrayList<>();
    private Map<PlayerIndexedType, PlayerInput> state = new HashMap<>();

    public void setPlayerPermutations(List<PlayerIndexedType> playerPermutations) {
        this.playerPermutations = playerPermutations;
        playerPermutations.forEach((indices) -> {
            PlayerInput playerState = new PlayerInput(indices.getPlayerIndex(), indices.getOpponentIndex(), 0);
            state.put(indices, playerState);
        });
    }

    public void processFrame(FrameEntryType frame, FramesType allFrames) {
        playerPermutations.forEach((indices) -> {
            PlayerInput inputState = state.get(indices);
            handleInputCompute(allFrames, inputState, indices, frame);
        });
    }

    public List<PlayerInput> fetch() {
        return state.values().stream().collect(Collectors.toList());
    }

    public void handleInputCompute(FramesType frames, PlayerInput inputState, PlayerIndexedType indices,
            FrameEntryType frame) {

        PreFrameUpdateType playerFrame = frame.getPlayers().get(indices.getPlayerIndex()).getPre();
        PreFrameUpdateType prevPlayerFrame = frames.getFrames().get(frames.getFrames().size() - 2).getPlayers()
                .get(indices.getPlayerIndex()).getPre();

        if (playerFrame.getFrame() < Frames.FIRST_PLAYABLE.getFrame()) {
            return;
        }

        Integer invertedPreviousButtons = ~prevPlayerFrame.getPhysicalButtons();
        Integer currentButtons = playerFrame.getPhysicalButtons();
        Integer buttonChanges = (invertedPreviousButtons & currentButtons) & 0xFFF;
        inputState.setInputCount(inputState.getInputCount() + countSetBits(buttonChanges));

        JoystickRegion prevAnalogRegion = getJoystickRegion(prevPlayerFrame.getJoystickX(),
                prevPlayerFrame.getJoystickY());
        JoystickRegion currentAnalogRegion = getJoystickRegion(playerFrame.getJoystickX(), playerFrame.getJoystickY());

        if ((prevAnalogRegion != currentAnalogRegion) && (currentAnalogRegion.getRegion() != 0)) {
            inputState.setInputCount(inputState.getInputCount() + 1);
        }

        JoystickRegion prevCStickRegion = getJoystickRegion(prevPlayerFrame.getcStickX(), prevPlayerFrame.getcStickY());
        JoystickRegion currentCStickRegion = getJoystickRegion(playerFrame.getcStickX(), playerFrame.getcStickY());
        if ((prevCStickRegion != currentCStickRegion) && (currentCStickRegion.getRegion() != 0)) {
            inputState.setInputCount(inputState.getInputCount() + 1);
        }
        // FIXME: Implement lTrigger and rTrigger handling as soon as they're added to
        // data model
    }

    public int countSetBits(int x) {
        Integer bits = x;
        Integer count;
        for (count = 0; bits != null && bits > 0; count += 1) {
            bits &= bits - 1;
        }
        return count;
    }

    public JoystickRegion getJoystickRegion(float x, float y) {
        JoystickRegion region = JoystickRegion.DZ;

        if (x >= 0.2875 && y >= 0.2875) {
            region = JoystickRegion.NE;
        } else if (x >= 0.2875 && y <= -0.2875) {
            region = JoystickRegion.SE;
        } else if (x <= -0.2875 && y <= -0.2875) {
            region = JoystickRegion.SW;
        } else if (x <= -0.2875 && y >= 0.2875) {
            region = JoystickRegion.NW;
        } else if (y >= 0.2875) {
            region = JoystickRegion.N;
        } else if (x >= 0.2875) {
            region = JoystickRegion.E;
        } else if (y <= -0.2875) {
            region = JoystickRegion.S;
        } else if (x <= -0.2875) {
            region = JoystickRegion.W;
        }

        return region;
    }
}
