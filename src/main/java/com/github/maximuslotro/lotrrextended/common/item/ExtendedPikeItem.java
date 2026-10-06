package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;

public class ExtendedPikeItem extends ExtendedSwordItem {

	public ExtendedPikeItem(IItemTier pItemTeir, int pAttackDamage, float pAttackSpeed, float pAttackReach, Properties pProperties) {
		super(pItemTeir, pAttackDamage, pAttackSpeed, pAttackReach, pProperties);
	}

	public ExtendedPikeItem(IItemTier pItemTeir, Properties pProperties) {
		this(pItemTeir, 4, -3F, 1.5F, pProperties);
	}

	public ExtendedPikeItem(IItemTier pItemTeir, ItemGroup pCategory) {
		this(pItemTeir, new Properties().tab(pCategory));
	}

}
