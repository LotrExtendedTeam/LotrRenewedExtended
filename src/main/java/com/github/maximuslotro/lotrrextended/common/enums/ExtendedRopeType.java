package com.github.maximuslotro.lotrrextended.common.enums;

import net.minecraft.util.IStringSerializable;

public enum ExtendedRopeType implements IStringSerializable {
	SINGLE("single"),
	BOTTOM_SMALL("bottom_small"),
	TOP_SMALL("top_small"),
	BOTTOM_LARGE("bottom_large"),
	MIDDLE_LARGE("middle_large"),
	TOP_LARGE("top_large");


	private final String name;

	private ExtendedRopeType(String pName) {
		this.name = pName;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}