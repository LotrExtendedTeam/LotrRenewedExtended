package com.github.maximuslotro.lotrrextended.common.init;

import java.util.function.Supplier;

import lotr.common.init.ExtendedItems;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ExtendedBowMaterial {
	GENERIC("generic", 0, 400, 20, 1.1F, 10, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	RANGER_NORTH("ranger_north", 1, 525, 20, 1.2F, 12, () -> Ingredient.of(ExtendedItems.BOWSTRING.get()));
	private final String bowMaterialName;
	private final int bowLevel;
	private final int bowDurability;
	// Number of ticks needed to draw bow to full power, default is 20 ticks (1 second)
	private final int drawSpeed;
	private final float bowDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairIngredient;

	private ExtendedBowMaterial(String materialName, int bowLevel, int bowDurability, int drawSpeed, float bowDamage, int ench, Supplier<Ingredient> repairIngredient) {
		this.bowMaterialName = materialName;
		this.bowLevel = bowLevel;
		this.bowDurability = bowDurability;
		this.drawSpeed = drawSpeed;
		this.bowDamage = bowDamage;
		this.enchantability = ench;
		this.repairIngredient = new LazyValue<>(repairIngredient);
	}

	public float getAttackDamageBonus() {
		return bowDamage;
	}

	public int getEnchantmentValue() {
		return enchantability;
	}

	public int getLevel() {
		return bowLevel;
	}

	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}

	public int getDrawSpeed() {
		return drawSpeed;
	}

	public int getDurability() {
		return bowDurability;
	}
}