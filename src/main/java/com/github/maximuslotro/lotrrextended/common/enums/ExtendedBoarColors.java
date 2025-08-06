package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public enum ExtendedBoarColors {
	REGULAR(0),
	REGULAR_DARK(1),
	DARKER(2),
	DARKER_DARK(3),
	DARK(4);

	private static final ExtendedBoarColors[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ExtendedBoarColors::getId)).toArray((p_234249_0_) -> {
		return new ExtendedBoarColors[p_234249_0_];
	});
	private final int id;

	private ExtendedBoarColors(int pId) {
		this.id = pId;
	}

	public int getId() {
		return this.id;
	}

	public static ExtendedBoarColors byId(int pId) {
		return BY_ID[pId % BY_ID.length];
	}

	public static ExtendedBoarColors getRandom(Random rand) {
		int i = rand.nextInt(50);
		if(i<20) {
			return REGULAR;
		}else if(i<35) {
			return REGULAR_DARK;
		}else if(i<43) {
			return DARKER;
		}else if(i<49) {
			return DARKER_DARK;
		}
		return DARK;
	}
}