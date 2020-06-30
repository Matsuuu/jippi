package matsu.jippi.stats;

import java.util.List;

import matsu.jippi.pojo.common.FrameEntryType;
import matsu.jippi.pojo.common.FramesType;
import matsu.jippi.pojo.common.PlayerIndexedType;

public interface StatComputer<T> {
    public void setPlayerPermutations(List<PlayerIndexedType> indices);

    public void processFrame(FrameEntryType newFrame, FramesType allFrames);

    public T fetch();
}

