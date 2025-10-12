package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedPackedFurBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWargCarpetBlock;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum ExtendedWargFurType implements IStringSerializable {
	BLACK(0, "black", DyeColor.BLACK),
	BROWN(1, "brown", DyeColor.BROWN),
	GRAY(2, "gray", DyeColor.GRAY),
	SILVER(3, "silver", DyeColor.LIGHT_GRAY),
	WHITE(4, "white", DyeColor.WHITE);

	private static final ExtendedWargFurType[] BY_ID = Arrays.stream(values())
									.sorted(Comparator.comparingInt(ExtendedWargFurType::getId))
									.toArray(ExtendedWargFurType[]::new);
	private final int id;
	private final String name;
	private final DyeColor color;

	private ExtendedWargFurType(int id, String name, DyeColor color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public DyeColor getDyeColor() {
		return this.color;
	}

	public static ExtendedWargFurType byId(int p_196056_0_) {
		if (p_196056_0_ < 0 || p_196056_0_ >= BY_ID.length) {
			p_196056_0_ = 0;
		}
		return BY_ID[p_196056_0_];
	}

	public static ExtendedWargFurType byName(String p_204271_0_, ExtendedWargFurType p_204271_1_) {
		for (ExtendedWargFurType dyecolor : values()) {
			if (dyecolor.name.equals(p_204271_0_)) {
				return dyecolor;
			}
		}
		return p_204271_1_;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	@Nullable
	public static ExtendedWargFurType getWargFurType(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem) {
			Block block = ((BlockItem) stack.getItem()).getBlock();
			if(block instanceof ExtendedWargCarpetBlock) {
				return ((ExtendedWargCarpetBlock) block).getWargFurType();
			}
			if(block instanceof ExtendedPackedFurBlock) {
				return ((ExtendedPackedFurBlock) block).getWargFurType();
			}
		}
		return null;
	}
}