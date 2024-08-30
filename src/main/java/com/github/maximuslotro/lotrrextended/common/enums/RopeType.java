package com.github.maximuslotro.lotrrextended.common.enums;

import net.minecraft.util.IStringSerializable;

public enum RopeType implements IStringSerializable {
	SINGLE("single"),
	BOTTOM_SMALL("bottom_small"),
	TOP_SMALL("top_small"),
	BOTTOM_LARGE("bottom_large"),
	MIDDLE_LARGE("middle_large"),
	TOP_LARGE("top_large");


	private final String name;

	private RopeType(String pName) {
		this.name = pName;
	}

	public String getSerializedName() {
		return this.name;
	}
}