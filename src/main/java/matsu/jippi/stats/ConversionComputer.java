package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import matsu.jippi.enumeration.stats.Timers;
import matsu.jippi.pojo.common.ConversionMetadataType;
import matsu.jippi.pojo.common.ConversionType;
import matsu.jippi.pojo.common.DamageType;
import matsu.jippi.pojo.common.DurationType;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.MoveLandedType;
import matsu.jippi.pojo.common.PlayerConversionState;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PostFrameUpdateType;

public class ConversionComputer implements StatComputer<List<ConversionType>> {
    private List<PlayerIndexedType> playerPermutations = new ArrayList<>();
    private List<ConversionType> conversions = new ArrayList<>();
    private Map<PlayerIndexedType, PlayerConversionState> state = new HashMap<>();
    private ConversionMetadataType metadata;

    public ConversionComputer() {
        this.metadata = new ConversionMetadataType();
    }

    public void setPlayerPermutations(List<PlayerIndexedType> playerPermutations) {
        this.playerPermutations = playerPermutations;
        playerPermutations.forEach((indices) -> {
            PlayerConversionState playerState = new PlayerConversionState(null, null, 0, null);
            state.put(indices, playerState);
        });
    }

    public void processFrame(FrameEntryType frame, FramesType allFrames) {
        playerPermutations.forEach((indices) -> {
            PlayerConversionState conversionState = state.get(indices);
            handleConversionCompute(allFrames, conversionState, indices, frame);
        });
    }

    public List<ConversionType> fetch() {
        populateConversionTypes();
        return conversions;
    }

    private void populateConversionTypes() {
        List<ConversionType> conversionsToHandle = conversions.stream()
                .filter(con -> con.getOpeningType().equals("unknown")).collect(Collectors.toList());

        Map<Integer, List<ConversionType>> sortedConversions = new HashMap<>();
        for (ConversionType con : conversionsToHandle) {

            int startFrame = con.getDurationType().getStartFrame();
            if (!sortedConversions.containsKey(startFrame)) {
                sortedConversions.put(startFrame, new ArrayList<>());
            }
            sortedConversions.get(startFrame).add(con);
        }

        sortedConversions.values().forEach(cons -> {
            boolean isTrade = cons.size() >= 2;
            cons.forEach((con) -> {
                metadata.getLastEndFrameByOppIdx().put(con.getPlayerIndexedType().getPlayerIndex(),
                        con.getDurationType().getEndFrame());

                if (isTrade) {
                    con.setOpeningType("trade");
                    return;
                }

                Integer oppEndFrame = metadata.getLastEndFrameByOppIdx()
                        .get(con.getPlayerIndexedType().getOpponentIndex());
                boolean isCounterAttack = oppEndFrame != null && oppEndFrame > con.getDurationType().getStartFrame();
                con.setOpeningType(isCounterAttack ? "counter-attack" : "neutral-win");
            });
        });
    }

    public void handleConversionCompute(FramesType frames, PlayerConversionState conversionState,
            PlayerIndexedType indices, FrameEntryType frame) {
        PostFrameUpdateType playerFrame = frame.getPlayers().get(indices.getPlayerIndex()).getPost();
        PostFrameUpdateType prevPlayerFrame = frames.getFrames().get(frames.getFrames().size() - 2).getPlayers()
                .get(indices.getPlayerIndex()).getPost();

        PostFrameUpdateType opponentFrame = frame.getPlayers().get(indices.getOpponentIndex()).getPost();
        PostFrameUpdateType prevOpponentFrame = frames.getFrames().get(frames.getFrames().size() - 2).getPlayers()
                .get(indices.getOpponentIndex()).getPost();

        boolean opntIsDamaged = StatsQuerier.isDamaged(opponentFrame.getActionStateId());
        boolean opntIsGrabbed = StatsQuerier.isGrabbed(opponentFrame.getActionStateId());
        Float opntDamageTaken = StatsQuerier.calcDamageTaken(opponentFrame, prevOpponentFrame);

        boolean actionChangedSinceHit = playerFrame.getActionStateId() != conversionState.getLastHitAnimation();
        Float actionCounter = playerFrame.getActionStateCounter();
        Float prevActionCounter = prevPlayerFrame.getActionStateCounter();
        boolean actionFrameCounterReset = actionCounter < prevActionCounter;
        if (actionChangedSinceHit || actionFrameCounterReset) {
            conversionState.setLastHitAnimation(null);
        }

        if (opntIsDamaged || opntIsGrabbed) {
            if (conversionState.getConversion() == null) {
                conversionState.setConversion(new ConversionType(
                        new PlayerIndexedType(indices.getPlayerIndex(), indices.getOpponentIndex()),
                        new DurationType(playerFrame.getFrame(), null),
                        new DamageType(prevOpponentFrame.getPercentOrZero(), opponentFrame.getPercentOrZero(), null),
                        new ArrayList<>(), "unknown", false));

                conversions.add(conversionState.getConversion());
            }

            if (opntDamageTaken != null && opntDamageTaken > 0) {
                if (conversionState.getLastHitAnimation() == null) {
                    conversionState.setMove(
                            new MoveLandedType(playerFrame.getFrame(), playerFrame.getLastAttackLanded(), 0, 0));

                    conversionState.getConversion().getMoves().add(conversionState.getMove());
                }

                if (conversionState.getMove() != null) {
                    conversionState.getMove().setHitCount(conversionState.getMove().getHitCount() + 1);
                    conversionState.getMove().setDamage(conversionState.getMove().getDamage() + opntDamageTaken);
                }

                conversionState.setLastHitAnimation(prevPlayerFrame.getActionStateId());
            }
        }

        if (conversionState.getConversion() == null) {
            return;
        }

        boolean opntInControl = StatsQuerier.isInControl(opponentFrame.getActionStateId());
        boolean opntDidLoseStock = StatsQuerier.didLoseStock(opponentFrame, prevOpponentFrame);

        if (!opntDidLoseStock) {
            conversionState.getConversion().getDamageType().setCurrentPercent(opponentFrame.getPercentOrZero());
        }

        if (opntIsDamaged || opntIsGrabbed) {
            conversionState.setResetCounter(0);
        }

        boolean shouldStartResetCounter = conversionState.getResetCounter() == 0 && opntInControl;
        boolean shouldContinueResetCounter = conversionState.getResetCounter() > 0;
        if (shouldStartResetCounter || shouldContinueResetCounter) {
            conversionState.setResetCounter(conversionState.getResetCounter() + 1);
        }

        boolean shouldTerminate = false;

        if (opntDidLoseStock) {
            conversionState.getConversion().setDidKill(true);
            shouldTerminate = true;
        }

        if (conversionState.getResetCounter() > Timers.PUNISH_RESET_FRAMES.getFrames()) {
            shouldTerminate = true;
        }

        if (shouldTerminate) {
            conversionState.getConversion().getDurationType().setEndFrame(playerFrame.getFrame());
            conversionState.getConversion().getDamageType().setEndPercent(prevOpponentFrame.getPercentOrZero());

            conversionState.setConversion(null);
            conversionState.setMove(null);
        }
    }
}
