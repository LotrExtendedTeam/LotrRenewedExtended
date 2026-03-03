package lotr.common.world.spawning;

/**
 * Represents the relative invasion frequency for a biome.
 *
 * <p>The stored percentage value represents the <b>chance that at least one
 * invasion occurs within one hour</b>. The helper method
 * {@link #getChancePerSecond()} converts this into a per-second probability
 * suitable for repeated random checks.</p>
 *
 * <p>This enum is intentionally separate from BanditChance so that similarly
 * named tiers can use different percentage values if balancing changes.</p>
 */
public enum InvasionChance {
	/** Invasions never occur. */
    NONE(0),
    /** Low hourly spawn probability (10%). */
    RARE(10),
    /** Low hourly spawn probability (10%). */
    UNCOMMON(30),
    /** High hourly spawn probability (70%). */
    COMMON(70);

	/** Hourly percentage chance (0–100). */
    private final int chance;

    /**
     * Creates a new invasion chance tier.
     *
     * @param chance Hourly percentage chance (0–100) that at least one invasion will occur within one hour
     */
    private InvasionChance(int chance) {
        this.chance = chance;
    }

    /**
     * Returns the configured hourly percentage chance.
     *
     * <p>This value represents the probability that at least one invasion
     * will occur within one hour.</p>
     *
     * @return hourly chance as an integer percentage (0–100)
     */
    public int getChance() {
        return chance;
    }

    /**
     * Converts the hourly chance into a per-second probability.
     *
     * <p>This is designed for systems that perform a random roll every second.
     * The returned value ensures that the cumulative probability over
     * 3600 seconds matches {@link #getChance()}.</p>
     *
     * <p>Mathematically:</p>
     * <pre>
     * perSecond = 1 - (1 - hourlyChance)^(1/3600)
     * </pre>
     *
     * @return probability (0.0–1.0) to use for a single second roll
     */
    public double getChancePerSecond() {
        if (chance == 0) return 0.0D;
        return 1.0D - Math.pow(1.0D - (chance / 100.0D), 1.0D / 3600.0D);
    }
}
