package matsu.jippi.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import matsu.jippi.enumeration.melee.Stages;
import matsu.jippi.pojo.common.GameStartType;
import matsu.jippi.pojo.common.StatsType;

public class TestSlippiGame {

    @Test
    public void testReadSettings() throws IOException {

        SlippiGame game = new SlippiGame("slp/sheik_vs_ics_yoshis.slp");
        GameStartType settings = game.getSettings();

        assertEquals(Integer.valueOf(Stages.YOSHIS_STORY.getStage().getId()), settings.getStageId());
    }

    @Test
    public void testStats() throws IOException {
        SlippiGame game = new SlippiGame("slp/test.slp");
        StatsType stats = game.getStats();

        assertEquals(stats.getStocks().size(), 5);
        assertEquals(stats.getLastFrame(), 3694);
    }

}
