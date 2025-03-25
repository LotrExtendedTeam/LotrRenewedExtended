package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedNetworkToggles {
	FRIENDLY_FIRE, UNITS_FRIENDLY_FIRE, MAP_LOCATION, MAP_MARKERS, UNIT_DEATH_MESSAGES, HIGH_CONTRAST_MESSAGES;
	
	private static final ExtendedNetworkToggles[] BY_ID = values();

	public static ExtendedNetworkToggles byId(int index) {
		if (index < 0 || index >= BY_ID.length) {
			index = 0;
		}
		return BY_ID[index];
	}
}
