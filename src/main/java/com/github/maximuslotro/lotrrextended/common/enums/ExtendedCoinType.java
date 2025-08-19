package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedCoinType {
	ZERO(0),
	ONE(1), 
	TEN(10), 
	HUNDRED(100);

	private final int value;
	
	ExtendedCoinType(int i) {
		value = i;
	}

	public int getValue() {
		return value;
	}
}
