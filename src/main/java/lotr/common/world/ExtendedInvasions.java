package lotr.common.world;

public enum ExtendedInvasions {

    RIVENDELL("lotr:high_elves,rivendell"),
    LINDON("lotr:high_elves,lindon"),
    GUNDABAD("lotr:northern_orcs"),
    GUNDABAD_WARGS("lotr:northern_orcs,wargs"),
    DUNEDAIN("lotr:dunedain_north"),
    MORDOR("lotr:mordor"),
    GONDOR("lotr:gondor"),
    LOTHLORIEN("lotr:lothlorien");

    private final String key;

    ExtendedInvasions(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
