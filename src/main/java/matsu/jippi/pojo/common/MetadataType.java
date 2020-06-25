package matsu.jippi.pojo.common;

import java.util.Map;

public class MetadataType {
    private String startAt;
    private String playedOn;
    private Integer lastFrame;
    private Map<Integer, Map<Character, Integer>> players;

    public MetadataType() {
    }

    public MetadataType(String startAt, String playedOn, Integer lastFrame,
            Map<Integer, Map<Character, Integer>> players) {
        this.startAt = startAt;
        this.playedOn = playedOn;
        this.lastFrame = lastFrame;
        this.players = players;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(String playedOn) {
        this.playedOn = playedOn;
    }

    public Integer getLastFrame() {
        return lastFrame;
    }

    public void setLastFrame(Integer lastFrame) {
        this.lastFrame = lastFrame;
    }

    public Map<Integer, Map<Character, Integer>> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, Map<Character, Integer>> players) {
        this.players = players;
    }
}
