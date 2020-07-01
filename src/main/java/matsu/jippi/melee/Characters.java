package matsu.jippi.melee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import matsu.jippi.enumeration.melee.ExternalCharacters;
import matsu.jippi.pojo.melee.CharacterInfo;

public class Characters {

    public static List<CharacterInfo> getAllCharacters() {
        return Arrays.asList(ExternalCharacters.values()).stream().map(exc -> exc.getInfo())
                .collect(Collectors.toList());
    }

    public static CharacterInfo getCharacterInfo(int externalCharacterId) {
        if (externalCharacterId < 0 || externalCharacterId >= ExternalCharacters.values().length) {
            throw new Error("Invalid character id " + externalCharacterId);
        }
        return ExternalCharacters.byId(externalCharacterId).getInfo();
    }

    public static String getCharacterShortName(int externalCharacterId) {
        return getCharacterInfo(externalCharacterId).getShortName();
    }

    public static String getCharactername(int externalCharacterId) {
        return getCharacterInfo(externalCharacterId).getName();
    }

    public static String getCharacterColorName(int externalCharacterId, int characterColor) {
        return getCharacterInfo(externalCharacterId).getColors().get(characterColor);
    }
}
