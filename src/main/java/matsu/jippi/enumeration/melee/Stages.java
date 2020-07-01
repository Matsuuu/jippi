package matsu.jippi.enumeration.melee;

import matsu.jippi.pojo.melee.Stage;

public enum Stages {

    FOUNTAIN_OF_DREAMS(new Stage(2, "Fountain of Dreams")), POKEMON_STADIUM(new Stage(3, "Pokémon Stadium")),
    PRINCESS_PEACHS_CASTLE(new Stage(4, "Princess Peach's Castle")), KONGO_JUNGLE(new Stage(5, "Kongo Jungle")),
    BRINSTAR(new Stage(6, "Brinstar")), CORNERIA(new Stage(7, "Corneria")), YOSHIS_STORY(new Stage(8, "Yoshi's Story")),
    ONETT(new Stage(9, "Onett")), MUTE_CITY(new Stage(10, "Mute City")),
    RAINBOW_CRUISE(new Stage(11, "Rainbow Cruise")), JUNGLE_JAPES(new Stage(12, "Jungle Japes")),
    GREAT_BAY(new Stage(13, "Great Bay")), HYRULE_TEMPLE(new Stage(14, "Hyrule Temple")),
    BRINSTAR_DEPTHS(new Stage(15, "Brinstar Depths")), YOSHIS_ISLAND(new Stage(16, "Yoshi's Island")),
    GREEN_GREENS(new Stage(17, "Green Greens")), FOURSIDE(new Stage(18, "Fourside")),
    MUSHROOM_KINGDOM_I(new Stage(19, "Mushroom Kingdom I")), MUSHROOM_KINGDOM_II(new Stage(20, "Mushroom Kingdom II")),
    VENOM(new Stage(22, "Venom")), POKE_FLOATS(new Stage(23, "Poké Floats")), BIG_BLUE(new Stage(24, "Big Blue")),
    ICICLE_MOUNTAIN(new Stage(25, "Icicle Mountain")), ICETOP(new Stage(26, "Icetop")),
    FLAT_ZONE(new Stage(27, "Flat Zone")), DREAM_LAND_N64(new Stage(28, "Dream Land N64")),
    YOSHIS_ISLAND_N64(new Stage(29, "Yoshi's Island N64")), KONGO_JUNGLE_N64(new Stage(30, "Kongo Jungle N64")),
    BATTLEFIELD(new Stage(31, "Battlefield")), FINAL_DESTINATION(new Stage(32, "Final Destination"));

    private Stage stage;

    private Stages(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public static Stages byId(int id) {
        for (Stages s : Stages.values()) {
            if (s.getStage().getId() == id) {
                return s;
            }
        }
        return null;
    }

}
