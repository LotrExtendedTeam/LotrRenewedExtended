package com.github.maximuslotro.lotrrextended.common.enums;

import net.minecraft.util.IStringSerializable;

public enum RopeType implements IStringSerializable {
	   TOP("bottom"),
	   MIDDLE("middle"),
	   BOTTOM("bottom");

	   private final String name;

	   private RopeType(String pName) {
	      this.name = pName;
	   }

	   public String getSerializedName() {
	      return this.name;
	   }
	}