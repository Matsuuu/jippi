package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matsu.jippi.enumeration.stats.Frames;
import matsu.jippi.enumeration.stats.Timers;
import matsu.jippi.pojo.common.ComboState;
import matsu.jippi.pojo.common.ComboType;
import matsu.jippi.pojo.common.DamageType;
import matsu.jippi.pojo.common.DurationType;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.MoveLandedType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PostFrameUpdateType;

public class ComboComputer implements StatComputer<List<ComboType>> {

    private List<PlayerIndexedType> playerPermutations = new ArrayList<>();
    private Map<PlayerIndexedType, ComboState> state = new HashMap<>();
    private List<ComboType> combos = new ArrayList<>();

    public void setPlayerPermutations(List<PlayerIndexedType> playerPermutations) {
        this.playerPermutations = playerPermutations;
        playerPermutations.forEach((indices) -> {
            ComboState playerState = new ComboState(null, null, 0, null);
            state.put(indices, playerState);
        });
    }

    public void processFrame(FrameEntryType frame, FramesType allFrames) {
        playerPermutations.forEach((indices) -> {
            ComboState comboState = state.get(indices);
            handleComboCompute(allFrames, comboState, indices, frame);
        });
    }

    public List<ComboType> fetch() {
        return combos;
    }

    public void handleComboCompute(FramesType frames, ComboState comboState, PlayerIndexedType indices,
            FrameEntryType frame) {

        PostFrameUpdateType playerFrame = frame.getPlayers().get(indices.getPlayerIndex()).getPost();
        PostFrameUpdateType prevPlayerFrame = frame.getFrame() == Frames.FIRST.getFrame() ? null : frames.getFrames().get(playerFrame.getFrame() - 1).getPlayers()
                .get(indices.getPlayerIndex()).getPost();

        PostFrameUpdateType opponentFrame = frame.getPlayers().get(indices.getOpponentIndex()).getPost();
        PostFrameUpdateType prevOpponentFrame = frame.getFrame() == Frames.FIRST.getFrame() ? null : frames.getFrames().get(playerFrame.getFrame() - 1).getPlayers()
                .get(indices.getOpponentIndex()).getPost();

        boolean opntIsDamaged = StatsQuerier.isDamaged(opponentFrame.getActionStateId());
        boolean opntIsGrabbed = StatsQuerier.isGrabbed(opponentFrame.getActionStateId());
        Float opntDamageTaken = StatsQuerier.calcDamageTaken(opponentFrame, prevOpponentFrame);

        boolean actionChangedSinceHit = playerFrame.getActionStateId() != comboState.getLastHitAnimation();
        Float actionCounter = playerFrame.getActionStateCounter();
        Float prevActionCounter = prevPlayerFrame != null ? prevPlayerFrame.getActionStateCounter() : null;
        boolean actionFrameCounterReset = prevActionCounter != null && actionCounter < prevActionCounter;
        if (actionChangedSinceHit || actionFrameCounterReset) {
            comboState.setLastHitAnimation(null);
        }

        if (opntIsDamaged || opntIsGrabbed) {
            if (comboState.getCombo() == null) {
                comboState.setCombo(new ComboType(
                        new PlayerIndexedType(indices.getPlayerIndex(), indices.getOpponentIndex()),
                        new DurationType(playerFrame.getFrame(), null),
                        new DamageType(prevOpponentFrame.getPercentOrZero(), opponentFrame.getPercentOrZero(), null),
                        new ArrayList<>(), false));

                combos.add(comboState.getCombo());
            }

            if (opntDamageTaken != null && opntDamageTaken > 0) {
                if (comboState.getLastHitAnimation() == null) {
                    comboState.setMove(
                            new MoveLandedType(playerFrame.getFrame(), playerFrame.getLastAttackLanded(), 0, 0));

                    comboState.getCombo().getMoves().add(comboState.getMove());
                }

                if (comboState.getMove() != null) {
                    comboState.getMove().setHitCount(comboState.getMove().getHitCount() + 1);
                    comboState.getMove().setDamage(comboState.getMove().getDamage() + opntDamageTaken);
                }

                comboState.setLastHitAnimation(prevPlayerFrame.getActionStateId());
            }
        }

        if (comboState.getCombo() == null) {
            return;
        }

        boolean opntIsTeching = StatsQuerier.isTeching(opponentFrame.getActionStateId());
        boolean opntIsDowned = StatsQuerier.isDown(opponentFrame.getActionStateId());
        boolean opntDidLoseStock = StatsQuerier.didLoseStock(opponentFrame, prevOpponentFrame);
        boolean opntIsDying = StatsQuerier.isDead(opponentFrame.getActionStateId());

        if (!opntDidLoseStock) {
            comboState.getCombo().getDamageType().setCurrentPercent(opponentFrame.getPercentOrZero());
        }

        if (opntIsDamaged || opntIsGrabbed || opntIsTeching || opntIsDowned || opntIsDying) {
            comboState.setResetCounter(0);
        } else {
            comboState.setResetCounter(comboState.getResetCounter() + 1);
        }

        boolean shouldTerminate = false;

        if (opntDidLoseStock) {
            comboState.getCombo().setDidKill(true);
            shouldTerminate = true;
        }

        if (comboState.getResetCounter() > Timers.COMBO_STRING_RESET_FRAMES.getFrames()) {
            shouldTerminate = true;
        }

        if (shouldTerminate) {
            comboState.getCombo().getDurationType().setEndFrame(playerFrame.getFrame());
            comboState.getCombo().getDamageType().setEndPercent(prevOpponentFrame.getPercentOrZero());
        }

        comboState.setCombo(null);
        comboState.setMove(null);
    }
}
