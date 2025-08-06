package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedBannerProtectionOwners {
	FACTION, PLAYER, NONE;
	private static final ExtendedBannerProtectionOwners[] values = values();
	private final int id;

	ExtendedBannerProtectionOwners() {
		this.id = ordinal();
	}

	public int getId() {
		return id;
	}

	public static ExtendedBannerProtectionOwners getByID(int ordinal) {
		return values[ordinal];
	}
}
