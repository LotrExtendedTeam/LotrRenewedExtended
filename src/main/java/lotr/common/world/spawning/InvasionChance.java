package lotr.common.world.spawning;

/**
 * Represents the relative invasion frequency for a biome.
 *
 * <p>The stored value represents the <b>probability that at least one invasion
 * occurs within one hour</b>. Values are expressed in the range {@code [0,1]}.</p>
 *
 * <p>The game performs an invasion roll once per second, so the hourly
 * probability must be converted into an equivalent per-second probability
 * that produces the same cumulative chance over {@code 3600} checks.</p>
 *
 * <h2>Probability model</h2>
 *
 * <p>The conversion is based on the standard probability identity for
 * repeated independent trials:</p>
 *
 * <pre>
 * P(at least one success) = 1 − (1 − p)^n
 * </pre>
 *
 * <p>Where {@code p} is the probability of a single trial and {@code n} is the
 * number of trials. This follows from the complement rule:
 *
 * <pre>
 * P(at least one) = 1 − P(none) = 1 − (1 − p)^n
 * </pre>
 *
 * <h2>Deriving the per-second probability</h2>
 *
 * <p>The hourly probability is:</p>
 *
 * <pre>
 * P_hour = 1 − (1 − P_second)^3600
 * </pre>
 *
 * <p>Solving for {@code P_second}:</p>
 *
 * <pre>
 * 1 − P_hour = (1 − P_second)^3600
 * (1 − P_hour)^(1/3600) = 1 − P_second
 * P_second = 1 − (1 − P_hour)^(1/3600)
 * </pre>
 *
 * <p>This value ensures that performing a random roll every second results
 * in the same cumulative hourly probability.</p>
 *
 * <h2>References</h2>
 *
 * <ul>
 *   <li>Math StackExchange – explanation of {@code 1 − (1 − p)^n}:
 *   https://math.stackexchange.com/questions/4624889/what-is-the-name-of-this-formula-1-1-pn-x</li>
 *
 *   <li>Math StackExchange – OTP guessing probability discussion:
 *   https://math.stackexchange.com/questions/4625219/what-is-the-formula-to-estimate-how-long-it-can-take-to-guess-an-otp</li>
 *
 *   <li>US Naval Academy paper on repeated probability trials:
 *   https://www.usna.edu/Users/physics/mungan/_files/documents/Scholarship/OddsToBeChosen.pdf</li>
 * </ul>
 */
public enum InvasionChance {

    /** Invasions never occur. */
    NONE(0F),

    /** Low hourly spawn probability (10%). */
    RARE(0.1F),

    /** Medium hourly spawn probability (30%). */
    UNCOMMON(0.3F),

    /** High hourly spawn probability (70%). */
    COMMON(0.7F);

    /** Hourly probability (0–1). Represents the chance that at least one invasion occurs within one hour. */
    private final float chance;

    /** Cached probability for a single one-second check. */
    private final double chancePerSecond;

    /**
     * Creates a new invasion chance tier.
     *
     * @param chance hourly probability (0–1) that at least one invasion occurs
     */
    private InvasionChance(float chance) {
        this.chance = chance;

        /*
         * Convert the hourly probability into a per-second probability.
         *
         * Derived from:
         *
         * P_hour = 1 − (1 − P_second)^3600
         *
         * Solving for P_second gives:
         *
         * P_second = 1 − (1 − P_hour)^(1/3600)
         */
        this.chancePerSecond = 1.0D - Math.pow(1.0D - chance, 1.0D / 3600.0D);
    }

    /**
     * Returns the configured hourly probability as a percentage.
     *
     * <p>This value represents the probability that at least one invasion
     * will occur within a one-hour period.</p>
     *
     * @return hourly probability as an integer percentage (0–100)
     */
    public int getChanceInt() {
        return (int) (chance * 100);
    }

    /**
     * Returns the probability used for a single one-second invasion check.
     *
     * <p>If this value is used in a random roll once per second, the total
     * probability accumulated across {@code 3600} checks will equal the
     * configured hourly chance.</p>
     *
     * @return probability in the range {@code [0,1]} for a single second roll
     */
    public double getChancePerSecond() {
        return chancePerSecond;
    }
}