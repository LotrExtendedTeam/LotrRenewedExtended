package lotr.common.world.spawning;

public enum BanditChance {

    NONE(0),
    RARE(10),
    UNCOMMON(30),
    COMMON(70);

    private int chance;

    BanditChance(int chance) {
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }

}
