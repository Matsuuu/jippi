package matsu.jippi.pojo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameEntryType {
    private int frame;
    private Map<Integer, FrameEntryPlayerOrFollower> players = new HashMap<>();
    private Map<Integer, FrameEntryPlayerOrFollower> followers = new HashMap<>();
    private List<ItemUpdateType> items = new ArrayList<>();

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public Map<Integer, FrameEntryPlayerOrFollower> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, FrameEntryPlayerOrFollower> players) {
        this.players = players;
    }

    public Map<Integer, FrameEntryPlayerOrFollower> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<Integer, FrameEntryPlayerOrFollower> followers) {
        this.followers = followers;
    }

    public List<ItemUpdateType> getItems() {
        return items;
    }

    public void setItems(List<ItemUpdateType> items) {
        this.items = items;
    }

    public FrameEntryType(int frame, Map<Integer, FrameEntryPlayerOrFollower> players,
            Map<Integer, FrameEntryPlayerOrFollower> followers) {
        this.frame = frame;
        this.players = players;
        this.followers = followers;
    }

    public FrameEntryType() {
    }

}
