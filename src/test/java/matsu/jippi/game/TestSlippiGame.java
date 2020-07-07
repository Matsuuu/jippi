package matsu.jippi.game;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.function.Function;

import matsu.jippi.pojo.common.MetadataType;
import matsu.jippi.util.FileReader;
import org.junit.Test;

import matsu.jippi.enumeration.melee.Stages;
import matsu.jippi.pojo.common.ConversionType;
import matsu.jippi.pojo.common.GameStartType;
import matsu.jippi.pojo.common.StatsType;

import static org.junit.Assert.*;

public class TestSlippiGame {

    private int copyPos = 0;

    @Test
    public void testReadSettings() throws IOException {

        SlippiGame game = new SlippiGame("slp/sheik_vs_ics_yoshis.slp");
        GameStartType settings = game.getSettings();

        assertEquals("Stage is Yoshis", Integer.valueOf(Stages.YOSHIS_STORY.getStage().getId()), settings.getStageId());
    }

    @Test
    public void testStats() throws IOException, InterruptedException {
        SlippiGame game = new SlippiGame("slp/test.slp");
        StatsType stats = game.getStats();

        assertEquals(5, stats.getStocks().size());
        assertEquals(Integer.valueOf(3694), stats.getLastFrame());
        assertEquals(stats.getStocks().get(stats.getStocks().size() - 1).getDurationType().getEndFrame(),
                Integer.valueOf(3694));
        //
        // Test conversions
        assertEquals(10, stats.getConversions().size());
        ConversionType firstConversion = stats.getConversions().get(0);
        assertEquals(4, firstConversion.getMoves().size());
        assertEquals(15, firstConversion.getMoves().get(0).getMoveId());
        assertEquals(17, firstConversion.getMoves().get(firstConversion.getMoves().size() - 1).getMoveId());

        // Test action counts
        assertEquals(16, stats.getActionCounts().get(0).getWavedashCount());
        assertEquals(1, stats.getActionCounts().get(0).getWavelandCount());
        assertEquals(3, stats.getActionCounts().get(0).getAirDodgeCount());

        // Test Overall
        assertEquals(494, stats.getOverall().get(0).getInputCount());
    }

    @Test
    public void testMetaData() throws IOException {
        SlippiGame game = new SlippiGame("slp/test.slp");
        MetadataType metadata = game.getMetadata();

        assertEquals("2017-12-18T21:14:14Z", metadata.getStartAt());
        assertEquals("dolphin", metadata.getPlayedOn());
    }

    @Test
    public void testIncomplete() throws IOException {
        SlippiGame game = new SlippiGame("slp/incomplete.slp");
        GameStartType settings = game.getSettings();

        assertEquals(2, settings.getPlayers().size());
        game.getMetadata();
        game.getStats();
    }

    @Test
    public void testNametags() throws IOException {
        SlippiGame game = new SlippiGame("slp/nametags.slp");
        GameStartType settings = game.getSettings();
        assertEquals("ＡＭＮイ", settings.getPlayers().get(0).getNameTag());
        assertEquals("", settings.getPlayers().get(1).getNameTag());

        SlippiGame game2 = new SlippiGame("slp/nametags2.slp");
        GameStartType settings2 = game2.getSettings();
        assertEquals("Ａ１＝＄", settings2.getPlayers().get(0).getNameTag());
        assertEquals("か、９＠", settings2.getPlayers().get(1).getNameTag());

        SlippiGame game3 = new SlippiGame("slp/nametags3.slp");
        GameStartType settings3 = game3.getSettings();
        assertEquals("Ｂ　　Ｒ", settings3.getPlayers().get(0).getNameTag());
        assertEquals("．　　。", settings3.getPlayers().get(1).getNameTag());
    }

    @Test
    public void testIsPAL() throws IOException {
        SlippiGame palGame = new SlippiGame("slp/pal.slp");
        SlippiGame ntscGame = new SlippiGame("slp/ntsc.slp");

        assertTrue(palGame.getSettings().isPAL());
        assertFalse(ntscGame.getSettings().isPAL());
    }

    @Test
    public void testControllerFixes() throws IOException {
        SlippiGame game = new SlippiGame("slp/controllerFixes.slp");
        GameStartType settings = game.getSettings();

        assertEquals("Dween", settings.getPlayers().get(0).getControllerFix());
        assertEquals("UCF", settings.getPlayers().get(1).getControllerFix());
        assertEquals("None", settings.getPlayers().get(2).getControllerFix());
    }

    @Test
    public void testBufferInput() throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(Files.readAllBytes(Paths.get("slp/sheik_vs_ics_yoshis.slp")));
        SlippiGame game = new SlippiGame(buf);
        GameStartType settings = game.getSettings();

        assertEquals(Integer.valueOf(8), settings.getStageId());
        assertEquals(Integer.valueOf(0x13), settings.getPlayers().get(0).getCharacterId());
        assertEquals(Integer.valueOf(0xE),
                settings.getPlayers().get(settings.getPlayers().size() - 1).getCharacterId());
    }

    @Test
    public void testRealtime() throws Exception {
        // TODO: Implement this
        /*ByteBuffer fullData = ByteBuffer.wrap(Files.readAllBytes(Paths.get("slp/realtimeTest.slp")));
        ByteBuffer buf = ByteBuffer.allocate((int) 100e6);

        SlippiGame game = new SlippiGame(buf);
        RealtimeDataObject data;
        copyPos = 0;

        Callable<RealtimeDataObject> getData = () -> {
            try {
                return new RealtimeDataObject(game.getSettings(), game.getFrames(),
                        game.getMetadata(), game.getGameEnd(), game.getStats(),
                        game.getLatestFrame());
            } catch (IOException e) {
                return new RealtimeDataObject();
            }
        };

        data = getData.call();
        assertNull(data.getSettings());

        copyBuf(fullData, buf, 0x1D);
        data = getData.call();
        copyBuf(fullData, buf, 0x1A3);

        data = getData.call();
        assertEquals(Integer.valueOf(8), data.getSettings().getStageId());

        copyBuf(fullData, buf, 0xE8 * 3);
        data = getData.call();
        assertEquals(3, data.getFrames().getFrames().size());
        assertEquals(-122, data.getLatestFrame().getFrame());
        assertNull(data.getStats().getStocks().get(1).getDurationType().getEndFrame());*/


    }

    private void copyBuf(ByteBuffer fullData, ByteBuffer buf, int length) {
        byte[] bytes = new byte[length];
        fullData.position(copyPos);
        fullData.get(bytes, 0, length);
        buf.position(copyPos);
        buf.put(bytes);
        copyPos += length;
    }
}
