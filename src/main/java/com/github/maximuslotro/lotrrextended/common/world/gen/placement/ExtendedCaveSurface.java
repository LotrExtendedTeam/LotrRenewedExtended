package com.github.maximuslotro.lotrrextended.common.world.gen.placement;

import com.mojang.serialization.Codec;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;


public enum ExtendedCaveSurface implements IStringSerializable {
	   CEILING(Direction.UP, 1, "ceiling"),
	   FLOOR(Direction.DOWN, -1, "floor");

	   public static final Codec<ExtendedCaveSurface> CODEC = IStringSerializable.fromEnum(ExtendedCaveSurface::values, ExtendedCaveSurface::byName);
	   private final Direction direction;
	   private final int y;
	   private final String id;
	   private static final ExtendedCaveSurface[] VALUES = values();

	   private ExtendedCaveSurface(Direction p_162104_, int p_162105_, String p_162106_) {
	      this.direction = p_162104_;
	      this.y = p_162105_;
	      this.id = p_162106_;
	   }

	   public Direction getDirection() {
	      return this.direction;
	   }

	   public int getY() {
	      return this.y;
	   }

	   public static ExtendedCaveSurface byName(String p_162109_) {
	      for(ExtendedCaveSurface cavesurface : VALUES) {
	         if (cavesurface.getSerializedName().equals(p_162109_)) {
	            return cavesurface;
	         }
	      }

	      throw new IllegalArgumentException("Unknown Surface type: " + p_162109_);
	   }

	   public String getSerializedName() {
	      return this.id;
	   }
	}
