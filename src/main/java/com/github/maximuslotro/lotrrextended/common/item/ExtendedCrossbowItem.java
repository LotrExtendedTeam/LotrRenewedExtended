package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCrossbowMaterial;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ExtendedCrossbowItem extends CrossbowItem {

	private final ExtendedCrossbowMaterial bowMaterial;

	public ExtendedCrossbowItem(ExtendedCrossbowMaterial material) {
		super((new Item.Properties()).tab(LOTRItemGroups.COMBAT).durability(material.getDurability()));
		this.bowMaterial = material;
	}
	@Override
	public int getEnchantmentValue() {
		return this.bowMaterial.getEnchantabilityValue();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
		return this.bowMaterial.getRepairIngredient().test(pRepair) || super.isValidRepairItem(pToRepair, pRepair);
	}

	public ExtendedCrossbowMaterial getMaterial() {
		return this.bowMaterial;
	}

	public static float getAdditionalLaunchSpeed(ItemStack stack) {
		if(stack.getItem() instanceof ExtendedCrossbowItem) {
			return ((ExtendedCrossbowItem)stack.getItem()).getMaterial().getLaunchSpeedFactor();
		}
		return 0;
	}

	public static int getDrawSpeedTicks(ItemStack stack) {
		if(stack.getItem() instanceof ExtendedCrossbowItem) {
			return ((ExtendedCrossbowItem)stack.getItem()).getMaterial().getDrawSpeedTicks();
		}
		return 25;
	}
}
