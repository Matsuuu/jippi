package matsu.jippi.enumeration.melee;

import java.util.Arrays;

import matsu.jippi.pojo.melee.CharacterInfo;

public enum ExternalCharacters {

    CAPTAIN_FALCON(new CharacterInfo(0, "Captain Falcon", "Falcon",
            Arrays.asList("Default", "Black", "Red", "White", "Green", "Blue"))),
    DONKEY_KONG(new CharacterInfo(1, "Donkey Kong", "DK", Arrays.asList("Default", "Black", "Red", "Blue", "Green"))),
    FOX(new CharacterInfo(2, "Fox", "Fox", Arrays.asList("Default", "Red", "Blue", "Green"))),
    MR_GAME_AND_WATCH(
            new CharacterInfo(3, "Mr. Game & Watch", "G&W", Arrays.asList("Default", "Red", "Blue", "Green"))),
    KIRBY(new CharacterInfo(4, "Kirby", "Kirby", Arrays.asList("Default", "Yellow", "Blue", "Red", "Green", "White"))),
    BOWSER(new CharacterInfo(5, "Bowser", "Bowser", Arrays.asList("Default", "Red", "Blue", "Black"))),
    LINK(new CharacterInfo(6, "Link", "Link", Arrays.asList("Default", "Red", "Blue", "Black", "White"))),
    LUIGI(new CharacterInfo(7, "Luigi", "Luigi", Arrays.asList("Default", "White", "Blue", "Red"))),
    MARIO(new CharacterInfo(8, "Mario", "Mario", Arrays.asList("Default", "Yellow", "Black", "Blue", "Green"))),
    MARTH(new CharacterInfo(9, "Marth", "Marth", Arrays.asList("Default", "Red", "Green", "Black", "White"))),
    MEWTWO(new CharacterInfo(10, "Mewtwo", "Mewtwo", Arrays.asList("Default", "Red", "Blue", "Green"))),
    NESS(new CharacterInfo(11, "Ness", "Ness", Arrays.asList("Default", "Yellow", "Blue", "Green"))),
    PEACH(new CharacterInfo(12, "Peach", "Peach", Arrays.asList("Default", "Daisy", "White", "Blue", "Green"))),
    PIKACHU(new CharacterInfo(13, "Pikachu", "Pikachu", Arrays.asList("Default", "Red", "Party Hat", "Cowboy Hat"))),
    ICE_CLIMBERS(new CharacterInfo(14, "Ice Climbers", "ICs", Arrays.asList("Default", "Green", "Orange", "Red"))),
    JIGGLYPUFF(
            new CharacterInfo(15, "Jigglypuff", "Puff", Arrays.asList("Default", "Red", "Blue", "Headband", "Crown"))),
    SAMUS(new CharacterInfo(16, "Samus", "Samus", Arrays.asList("Default", "Pink", "Black", "Green", "Purple"))),
    YOSHI(new CharacterInfo(17, "Yoshi", "Yoshi", Arrays.asList("Default", "Red", "Blue", "Yellow", "Pink", "Cyan"))),
    ZELDA(new CharacterInfo(18, "Zelda", "Zelda", Arrays.asList("Default", "Red", "Blue", "Green", "White"))),
    SHEIK(new CharacterInfo(19, "Sheik", "Sheik", Arrays.asList("Default", "Red", "Blue", "Green", "White"))),
    FALCO(new CharacterInfo(20, "Falco", "Falco", Arrays.asList("Default", "Red", "Blue", "Green"))),
    YOUNG_LINK(new CharacterInfo(21, "Young Link", "YLink", Arrays.asList("Default", "Red", "Blue", "White", "Black"))),
    DR_MARIO(new CharacterInfo(22, "Dr. Mario", "Doc", Arrays.asList("Default", "Red", "Blue", "Green", "Black"))),
    ROY(new CharacterInfo(23, "Roy", "Roy", Arrays.asList("Default", "Red", "Blue", "Green", "Yellow"))),
    PICHU(new CharacterInfo(24, "Pichu", "Pichu", Arrays.asList("Default", "Red", "Blue", "Green"))),
    GANONDORF(new CharacterInfo(25, "Ganondorf", "Ganon", Arrays.asList("Default", "Red", "Blue", "Green", "Purple")));

    private CharacterInfo info;

    private ExternalCharacters(CharacterInfo info) {
        this.info = info;
    }

    public CharacterInfo getInfo() {
        return info;
    }

    public static ExternalCharacters byId(int id) {
        for (ExternalCharacters e : ExternalCharacters.values()) {
            if (e.getInfo().getId() == id) {
                return e;
            }
        }
        return null;
    }

    public static ExternalCharacters byName(String name) {
        for (ExternalCharacters e : ExternalCharacters.values()) {
            if (e.getInfo().getName().equals(name)) {
                return e;
            }
        }
        return null;

    }
}
