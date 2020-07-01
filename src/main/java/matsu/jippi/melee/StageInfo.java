package matsu.jippi.melee;

import matsu.jippi.enumeration.melee.Stages;
import matsu.jippi.pojo.melee.Stage;

public class StageInfo {
    public final static int STAGE_FOD = Stages.FOUNTAIN_OF_DREAMS.getStage().getId();
    public final static int STAGE_POKEMON = Stages.POKEMON_STADIUM.getStage().getId();
    public final static int STAGE_YOSHIS = Stages.YOSHIS_STORY.getStage().getId();
    public final static int STAGE_DREAM_LAND = Stages.DREAM_LAND_N64.getStage().getId();
    public final static int STAGE_BATTLEFIELD = Stages.BATTLEFIELD.getStage().getId();
    public final static int STAGE_FD = Stages.FINAL_DESTINATION.getStage().getId();

    public static Stage getStageInfo(int stageId) {
        Stage s = Stages.byId(stageId).getStage();
        if (s == null) {
            throw new Error("Invalid stage with id " + stageId);
        }
        return s;
    }

    public static String getStageName(int stageId) {
        return getStageInfo(stageId).getName();
    }
}
