package com.github.maximuslotro.lotrrextended.common.modifiers;

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
	WOODEN("wooden", 0),
	/** A bronze scroll, usable as the first tier in a 3-level chain. */
	BRONZE("bronze", 1),
	/** A silver scroll, usable as the first tier in a 2-level chain. */
	SILVER("silver", 2),
	/** A gold scroll, the final tier or a standalone 1-level chain. */
	GOLD("golden", 3),
	/** Does not use a scroll. */
	NOSCROLL("NOSCROLL", -1);

	/** The localization/texture key. */
	private final String key;

	/** The localization/texture id. */
	private final int id ;

	ExtendedModifierScroll(String pKey, int pId) {
		this.key = pKey;
		this.id = pId;
	}

	/**
	 * @return the texture/localization key for this scroll.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the texture/localization id for this scroll.
	 */
	public int getId() {
		return id;
	}
}
