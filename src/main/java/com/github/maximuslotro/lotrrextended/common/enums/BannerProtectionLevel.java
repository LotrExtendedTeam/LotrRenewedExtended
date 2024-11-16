package com.github.maximuslotro.lotrrextended.common.enums;

public enum BannerProtectionLevel {
	NONE(0), COPPER(4), BRONZE(8), SILVER(16), GOLD(32), CUSTOM(0);

	private final int protectionRadius;

	private BannerProtectionLevel(int range) {
		protectionRadius = range;
	}

	public int getProtectionRadius() {
		return protectionRadius;
	}

}