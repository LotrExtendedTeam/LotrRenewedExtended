package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;

public class ExtendedStaffItem extends ExtendedSwordItem {

	public ExtendedStaffItem(IItemTier pItemTeir, int pAttackDamage, float pAttackSpeed, float pAttackReach, Properties pProperties) {
		super(pItemTeir, pAttackDamage, pAttackSpeed, pAttackReach, pProperties);
	}

	public ExtendedStaffItem(IItemTier pItemTeir, Properties pProperties) {
		this(pItemTeir, 4, -2.667F, 1.0F, pProperties);
	}

	public ExtendedStaffItem(IItemTier pItemTeir, ItemGroup pCategory) {
		this(pItemTeir, new Properties().tab(pCategory));
	}

}
