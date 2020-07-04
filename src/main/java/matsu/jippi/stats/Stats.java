package matsu.jippi.stats;

import java.util.ArrayList;
import java.util.List;

import matsu.jippi.enumeration.stats.Frames;
import matsu.jippi.pojo.common.*;

public class Stats {

    private final StatOptions defaultOptions = new StatOptions(false);

    private StatOptions options;
    private Integer lastProcessedFrame = null;
    private FramesType frames = new FramesType();;
    private List<PlayerIndexedType> playerPermutations;
    private List<StatComputer<?>> allComputers = new ArrayList<>();

    public Stats(StatOptions options) {
        this.options = options == null ? defaultOptions : options;
    }

    public void setPlayerPermutations(List<PlayerIndexedType> indices) {
        this.playerPermutations = indices;
        allComputers.forEach(comp -> comp.setPlayerPermutations(indices));
    }

    public void register(StatComputer<?> computer) {
        allComputers.add(computer);
    }

    public void registerAll(List<StatComputer<?>> computers) {
        allComputers.addAll(computers);
    }

    public void process() {
        if (playerPermutations.size() == 0) {
            return;
        }

        int i = lastProcessedFrame != null ? lastProcessedFrame + 1 : Frames.FIRST.getFrame();
        while (frames.getFrames().get(i) != null) {
            FrameEntryType frame = frames.getFrames().get(i);
            if (!isCompletedFrame(playerPermutations, frame)) {
                return;
            }

            allComputers.forEach(comp -> comp.processFrame(frame, frames));
            lastProcessedFrame = i;
            i++;
        }
    }

    public void addFrame(FrameEntryType frame) {
        frames.getFrames().put(frame.getFrame(), frame);

        if (options.isProcessOnTheFly()) {
            process();
        }
    }

    public boolean isCompletedFrame(List<PlayerIndexedType> playerPermutations, FrameEntryType frame) {
        PlayerIndexedType indices = playerPermutations.get(0);
        FrameEntryPlayerOrFollower player = frame.getPlayers().get(indices.getPlayerIndex());
        FrameEntryPlayerOrFollower opponent = frame.getPlayers().get(indices.getOpponentIndex());

        PostFrameUpdateType playerPostFrame = player != null ? player.getPost() : null;
        PostFrameUpdateType oppPostFrame = opponent != null ? opponent.getPost() : null;

        return playerPostFrame != null && oppPostFrame != null;
    }
}
