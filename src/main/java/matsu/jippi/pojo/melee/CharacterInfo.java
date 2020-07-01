package matsu.jippi.pojo.melee;

import java.util.List;

public class CharacterInfo {

    private int id;
    private String name;
    private String shortName;
    private List<String> colors;

    public CharacterInfo(int id, String name, String shortName, List<String> colors) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.colors = colors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
