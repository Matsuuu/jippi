package matsu.jippi.pojo.common;

import com.devsmart.ubjson.UBObject;
import com.devsmart.ubjson.UBReader;
import com.devsmart.ubjson.UBValue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

public class MetadataType {
    private String startAt;
    private String playedOn;
    private Integer lastFrame;
    private Map<Integer, Map<Character, Integer>> players;

    public static MetadataType parseUBObject(ByteBuffer buffer) {
        try {
            UBReader reader = new UBReader(new ByteArrayInputStream(buffer.array()));
            UBValue value = reader.read();
            UBObject metaDataJson = value != null ? value.asObject() : null;
            reader.close();
            if (metaDataJson == null) {
                return null;
            }

            UBValue startAt = metaDataJson.getOrDefault("startAt", null);
            UBValue playedOn = metaDataJson.getOrDefault("playedOn", null);
            UBValue lastFrame = metaDataJson.getOrDefault("lastFrame", null);
            UBValue players = metaDataJson.getOrDefault("players", null);
            return new MetadataType(startAt != null ? startAt.toString() : null,
                    playedOn != null ? playedOn.toString() : null, lastFrame != null ? lastFrame.asInt() : null,
                    players != null ? (Map<Integer, Map<Character, Integer>>) players : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MetadataType();
    }

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
