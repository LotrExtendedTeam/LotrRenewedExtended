package com.github.maximuslotro.lotrrextended.common.enums;

public enum BannerProtectionLevel {
	NONE(0), COPPER(8), BRONZE(16), SILVER(32), GOLD(64), CUSTOM(0);

	private final int protectionRadius;

	private BannerProtectionLevel(int range) {
		protectionRadius = range;
	}

	public int getProtectionRadius() {
		return protectionRadius;
	}

}