package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import matsu.jippi.pojo.common.DamageType;
import matsu.jippi.pojo.common.DurationType;
import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.PlayerIndexedType;
import matsu.jippi.pojo.common.PostFrameUpdateType;
import matsu.jippi.pojo.common.StockState;
import matsu.jippi.pojo.common.StockType;

public class StockComputer implements StatComputer<List<StockType>> {
    private Map<PlayerIndexedType, StockState> state = new HashMap<>();
    private List<PlayerIndexedType> playerPermutations = new ArrayList<>();
    private List<StockType> stocks = new ArrayList<>();

    @Override
    public void setPlayerPermutations(List<PlayerIndexedType> playerPermutations) {
        this.playerPermutations = playerPermutations;
        playerPermutations.forEach((indices) -> {
            StockState stockState = new StockState(null);
            state.put(indices, stockState);
        });

    }

    @Override
    public void processFrame(FrameEntryType frame, FramesType allFrames) {
        playerPermutations.forEach((indices) -> {
            StockState stockState = state.get(indices);
            handleStockCompute(allFrames, stockState, indices, frame);
        });
    }

    @Override
    public List<StockType> fetch() {
        return stocks;
    }

    public void handleStockCompute(FramesType frames, StockState stockState, PlayerIndexedType indices,
            FrameEntryType frame) {

        PostFrameUpdateType playerFrame = frame.getPlayers().get(indices.getPlayerIndex()).getPost();
        PostFrameUpdateType prevPlayerFrame = frames.getFrames().get(playerFrame.getFrame() - 1).getPlayers()
                .get(indices.getPlayerIndex()).getPost();

        if (stockState.getStock() == null) {
            boolean isPlayerDead = StatsQuerier.isDead(playerFrame.getActionStateId());
            if (isPlayerDead) {
                return;
            }

            stockState = new StockState(
                    new StockType(new PlayerIndexedType(indices.getPlayerIndex(), indices.getOpponentIndex()),
                            new DurationType(playerFrame.getFrame(), null), new DamageType(0, 0, null),
                            playerFrame.getStocksRemaining(), null));

            stocks.add(stockState.getStock());

        } else if (StatsQuerier.didLoseStock(playerFrame, prevPlayerFrame)) {
            stockState.getStock().getDurationType().setEndFrame(playerFrame.getFrame());
            stockState.getStock().getDamageType().setEndPercent(prevPlayerFrame.getPercentOrZero());
            stockState.getStock().setDeathAnimation(playerFrame.getActionStateId());
            stockState.setStock(null);
        } else {
            stockState.getStock().getDamageType().setCurrentPercent(playerFrame.getPercentOrZero());
        }
    }
}
