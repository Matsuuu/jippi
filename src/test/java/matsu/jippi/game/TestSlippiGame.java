package matsu.jippi.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import matsu.jippi.enumeration.melee.Stages;
import matsu.jippi.pojo.common.GameStartType;

public class TestSlippiGame {

    @Test
    public void testReadSettings() throws IOException {

        SlippiGame game = new SlippiGame("slp/sheik_vs_ics_yoshis.slp");
        GameStartType settings = game.getSettings();

        assertEquals(settings.getStageId(), Integer.valueOf(Stages.YOSHIS_STORY.getStage().getId()));
    }

}
