package matsu.jippi.pojo.common;

import java.util.List;

public class PlayerActionState {
    private ActionCountsType playerCounts;
    private List<Integer> animations;

    public ActionCountsType getPlayerCounts() {
        return playerCounts;
    }

    public void setPlayerCounts(ActionCountsType playerCounts) {
        this.playerCounts = playerCounts;
    }

    public List<Integer> getAnimations() {
        return animations;
    }

    public void setAnimations(List<Integer> animations) {
        this.animations = animations;
    }

    public PlayerActionState(ActionCountsType playerCounts) {
        this.playerCounts = playerCounts;
    }

    public PlayerActionState(ActionCountsType playerCounts, List<Integer> animations) {
        this.playerCounts = playerCounts;
        this.animations = animations;
    }

}
