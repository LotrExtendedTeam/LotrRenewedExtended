package lotr.common.world.spawning;

public enum InvasionChance {
    /*
     * For now creating a new one from the BanditChance in case I want the same words to
     * mean different percents
     */

    NONE(0),
    RARE(10),
    UNCOMMON(30),
    COMMON(70);

    private int chance;

    InvasionChance(int chance) {
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }
}
