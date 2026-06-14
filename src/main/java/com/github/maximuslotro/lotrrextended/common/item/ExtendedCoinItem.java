package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCoinType;

import net.minecraft.item.Item;

public class ExtendedCoinItem extends Item {
	public final ExtendedCoinType type;
	public ExtendedCoinItem(Properties p_i48487_1_, ExtendedCoinType type) {
		super(p_i48487_1_);
		this.type = type;
	}
}
