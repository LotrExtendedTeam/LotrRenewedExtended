package com.github.maximuslotro.lotrrextended.common.enums;

/**
 * Represents the possible owners of a protected banner.
 *
 * <p><strong>Important:</strong> When adding new values, always append them to the end of the list.
 * Do NOT reorder or insert values in the middle. The enum order (ordinal) is persisted as an ID
 * in saved data, and changing it will break compatibility.</p>
 */
public enum ExtendedBannerProtectionOwners {
	/** The banner is owned by a faction. */
    FACTION,
    /** The banner is owned by a single player. */
    PLAYER,
    /** The banner has no owner. */
    NONE,
    /** The banner is owned by the server/system. */
    SERVER;
	/**
     * Cached array of enum values for fast lookup by ID.
     */
    private static final ExtendedBannerProtectionOwners[] values = values();
    /**
     * The persistent numeric ID for this enum value.
     * <p>
     * This is based on the enum's ordinal position and is used for storage.
     * </p>
     */
    private final int id;
    /**
     * Constructs an enum value and assigns its persistent ID.
     * The ID is based on the ordinal position.
     */
	ExtendedBannerProtectionOwners() {
		this.id = ordinal();
	}
	/**
     * Returns the persistent ID of this owner type.
     *
     * @return the numeric ID used for storage
     */
	public int getId() {
		return id;
	}
	/**
     * Gets an {@link ExtendedBannerProtectionOwners} value by its stored ID.
     *
     * @param ordinal the stored numeric ID
     * @return the corresponding {@link ExtendedBannerProtectionOwners} enum value
     * @throws ArrayIndexOutOfBoundsException if the ID is invalid
     */
	public static ExtendedBannerProtectionOwners getByID(int ordinal) {
		return values[ordinal];
	}
}
