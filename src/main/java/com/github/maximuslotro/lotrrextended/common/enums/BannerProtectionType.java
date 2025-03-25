package com.github.maximuslotro.lotrrextended.common.enums;

public enum BannerProtectionType {
	FACTION, PLAYER, NONE;
	private static final BannerProtectionType[] values = values();
	private final int id;

	BannerProtectionType() {
		this.id = ordinal();
	}

	public int getId() {
		return id;
	}

	public static BannerProtectionType getByID(int ordinal) {
		return values[ordinal];
	}
}
