package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedWargCarpetBlock;

import lotr.common.block.ExtendedPackedFurBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum WargFurType implements IStringSerializable {
	BLACK(0, "black", DyeColor.BLACK),
	BROWN(1, "brown", DyeColor.BROWN),
	GRAY(2, "gray", DyeColor.GRAY),
	SILVER(3, "silver", DyeColor.LIGHT_GRAY),
	WHITE(4, "white", DyeColor.WHITE);

	private static final WargFurType[] BY_ID = Arrays.stream(values())
									.sorted(Comparator.comparingInt(WargFurType::getId))
									.toArray((p_199795_0_) -> {return new WargFurType[p_199795_0_];});
	private final int id;
	private final String name;
	private final DyeColor color;

	private WargFurType(int id, String name, DyeColor color) {
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

	public static WargFurType byId(int p_196056_0_) {
		if (p_196056_0_ < 0 || p_196056_0_ >= BY_ID.length) {
			p_196056_0_ = 0;
		}
		return BY_ID[p_196056_0_];
	}

	public static WargFurType byName(String p_204271_0_, WargFurType p_204271_1_) {
		for (WargFurType dyecolor : values()) {
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
	public static WargFurType getWargFurType(ItemStack stack) {
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