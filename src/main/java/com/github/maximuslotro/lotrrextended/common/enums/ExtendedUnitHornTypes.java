package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedUnitHornTypes {
	NOTSET(0, ""),
	HALT(1, ".halt"),
	READY(2, ".reddy"),
	SUMMON(3, ".summon");

	private final int unchangingIndex;
	private final String disc;
	
	private ExtendedUnitHornTypes(int pIndex, String pDisc) {
		this.unchangingIndex = pIndex;
		this.disc = pDisc;
	}

	public int getUnchangingIndex() {
		return unchangingIndex;
	}

	public boolean isSummon() {
		return this==SUMMON;
	}

	public boolean isHalt() {
		return this==HALT;
	}

	public boolean isReady() {
		return this==READY;
	}

	public boolean isUnset() {
		return this==NOTSET;
	}

	public String getDisc() {
		return disc;
	}

	public static ExtendedUnitHornTypes getByUnchangingIndex(int pIndex) {
		for(ExtendedUnitHornTypes order : values()) {
			if(order.getUnchangingIndex()==pIndex) {
				return order;
			}
		}
		return NOTSET;
	}
}
