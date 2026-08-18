package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.Tags.Items;

public enum ExtendedBowMaterial {
	VANILLA(385, 20, 1F, 1, () -> Ingredient.of(Items.STRING)),
	GENERIC(400, 20, 1.08F, 10, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	ORC(600, 20, 1.125F, 10, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	GUNDABAD_URUK(750, 30, 1.2F, 10, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	RANGER_NORTH(525, 20, 1.05F, 12, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	DUNEDAIN_NORTH(600, 20, 1.15F, 12, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	RIVENDELL(1050, 16, 1.25F, 12, () -> Ingredient.of(ExtendedItems.BOWSTRING.get())),
	LINDON(1050, 16, 1.25F, 12, () -> Ingredient.of(ExtendedItems.BOWSTRING.get()));

	private final int bowDurability;
	// Number of ticks needed to draw bow to full power, default is 20 ticks (1 second)
	private final int drawSpeed;
	private final float bowDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairIngredient;

	private ExtendedBowMaterial(int bowDurability, int drawSpeed, float bowDamage, int ench, Supplier<Ingredient> repairIngredient) {
		this.bowDurability = bowDurability;
		this.drawSpeed = drawSpeed;
		this.bowDamage = bowDamage;
		this.enchantability = ench;
		this.repairIngredient = new LazyValue<>(repairIngredient);
	}

	public float getAttackDamageBonusScale() {
		return bowDamage;
	}

	public int getEnchantabilityValue() {
		return enchantability;
	}

//	public int getMaterialLevel() {
//		return bowLevel;
//	}

	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}

	public int getDrawSpeedTicks() {
		return drawSpeed;
	}

	public int getDurability() {
		return bowDurability;
	}
}