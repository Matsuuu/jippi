package matsu.jippi.pojo.common;

import java.io.IOException;

import matsu.jippi.game.SlippiGame;

public class JippiOutputType {

    private StatsType finalStats;
    private MetadataType metadata;

    public JippiOutputType(SlippiGame game) {
        try {
            this.finalStats = game.getStats();
            this.metadata = game.getMetadata();
        } catch (IOException e) {
            System.out.println("Error creating output file");
            e.printStackTrace();
        }
    }

    public StatsType getFinalStats() {
        return finalStats;
    }

    public void setFinalStats(StatsType finalStats) {
        this.finalStats = finalStats;
    }

    public MetadataType getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataType metadata) {
        this.metadata = metadata;
    }

}
