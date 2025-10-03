package com.github.maximuslotro.lotrrextended.common.init;

import net.minecraft.item.Food;
import net.minecraft.item.Food.Builder;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ExtendedFoods {//saturation gained = nutrition × saturationMod × 2.0
	public static final Food BLUEBERRY = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food BLACKBERRY = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food RASPBERRY = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food CRANBERRY = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food ELDERBERRY = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food WILDBERRY = new Builder().nutrition(2).saturationMod(0.2F).effect(() -> new EffectInstance(Effects.POISON, 40, 1), 0.3F).build();

	public static final Food CHEESE_CURDS = new Builder().nutrition(2).saturationMod(0.4F).build();
	public static final Food CHEESE_SLICE = new Builder().nutrition(4).saturationMod(0.6F).build();
	public static final Food AGED_CHEESE_SLICE = new Builder().nutrition(5).saturationMod(0.7F).build();

	public static final Food ROASTED_CHESTNUT = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food BANANA = new Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food BANANA_BREAD = new Builder().nutrition(5).saturationMod(0.6F).build();

	public static final Food PLUM = new Builder().nutrition(4).saturationMod(0.3F).build();

	public static final Food DUCK = new Food.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build();
	public static final Food COOKED_DUCK = new Food.Builder().nutrition(6).saturationMod(0.6F).meat().build();

	public static final Food HOBBIT_PANCAKE = new Food.Builder().nutrition(4).saturationMod(0.6F).build();
	public static final Food HOBBIT_PANCAKE_SYRUP = new Food.Builder().nutrition(5).saturationMod(0.6F).build();
	public static final Food HOBBIT_PANCAKE_SYRUP_BUTTER = new Food.Builder().nutrition(6).saturationMod(0.6F).build();

	public static final Food BUTTER = new Food.Builder().nutrition(4).saturationMod(0.85F).build();

	public static void init() {}
}
