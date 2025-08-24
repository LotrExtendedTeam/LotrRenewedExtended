package com.github.maximuslotro.lotrrextended.common.enums;

/**
 * Defines different types of modifier scrolls, each representing
 * a tier in the upgrade chain (Wood → Bronze → Silver → Gold).
 * <p>
 * These scrolls determine the texture and also represent the
 * maximum level progression a modifier can have:
 * <ul>
 *   <li>4 levels: starts at WOODEN</li>
 *   <li>3 levels: starts at BRONZE</li>
 *   <li>2 levels: starts at SILVER</li>
 *   <li>1 level: only GOLD</li>
 * </ul>
 * </p>
 */
public enum ExtendedModifierScroll {
	/** A basic wooden scroll, the first tier in a 4-level chain. */
	WOODEN("wooden"),
	/** A bronze scroll, usable as the first tier in a 3-level chain. */
	BRONZE("bronze"),
	/** A silver scroll, usable as the first tier in a 2-level chain. */
	SILVER("silver"),
	/** A gold scroll, the final tier or a standalone 1-level chain. */
	GOLD("gold"),
	/** Does not use a scroll. */
	NOSCROLL("NOSCROLL");

    /** The localization/texture key. */
    private final String key;

    ExtendedModifierScroll(String key) {
        this.key = key;
    }

    /**
     * @return the texture/localization key for this scroll.
     */
    public String getKey() {
        return key;
    }

}
