package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.function.Supplier;

import lotr.common.init.ExtendedItems;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.Tags.Items;

public enum ExtendedCrossbowMaterial {
	// For reference to vanilla, not actually used
	WOODEN(326, 25, 1.0F, 1, () -> Ingredient.of(Items.STRING)),
	BRONZE(288, 25, 1.05F, 3, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	IRON(313, 30, 1.1F, 3, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	URUK(688, 50, 1.15F, 2, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	MITHRIL(3001, 75, 1.3F, 2, () -> Ingredient.of(ExtendedItems.BOWSTRING.get()));

	private final int durability;
	// Number of ticks needed to draw bow to full power, default is 20 ticks (1 second)
	private final int drawSpeed;
	private final float launchSpeed;
	private final int enchantability;
	private final LazyValue<Ingredient> repairIngredient;

	private ExtendedCrossbowMaterial(int drability, int drawSpeed, float launchSpeed, int ench, Supplier<Ingredient> repairIngredient) {
		this.durability = drability;
		this.drawSpeed = drawSpeed;
		this.launchSpeed = launchSpeed;
		this.enchantability = ench;
		this.repairIngredient = new LazyValue<>(repairIngredient);
	}

	public int getDurability() {
		return durability;
	}

	public int getDrawSpeedTicks() {
		return drawSpeed;
	}

	public float getLaunchSpeedFactor() {
		return launchSpeed;
	}

	public int getEnchantabilityValue() {
		return enchantability;
	}

	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}

}