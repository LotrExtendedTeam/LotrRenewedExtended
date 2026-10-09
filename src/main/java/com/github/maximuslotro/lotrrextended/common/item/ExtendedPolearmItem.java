package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;

public class ExtendedPolearmItem extends ExtendedSwordItem {

	public ExtendedPolearmItem(IItemTier pItemTeir, int pAttackDamage, float pAttackSpeed, float pAttackReach, Properties pProperties) {
		super(pItemTeir, pAttackDamage, pAttackSpeed, pAttackReach, pProperties);
	}

	public ExtendedPolearmItem(IItemTier pItemTeir, Properties pProperties) {
		this(pItemTeir, 3, -3.333F, 1.5F, pProperties);
	}

	public ExtendedPolearmItem(IItemTier pItemTeir, ItemGroup pCategory) {
		this(pItemTeir, new Properties().tab(pCategory));
	}

}
