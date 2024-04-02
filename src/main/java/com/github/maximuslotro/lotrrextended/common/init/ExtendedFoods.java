package com.github.maximuslotro.lotrrextended.common.init;

import net.minecraft.item.Food;
import net.minecraft.item.Food.Builder;
import net.minecraft.potion.*;

public class ExtendedFoods {
	public static final Food BLUEBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
	public static final Food BLACKBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
	public static final Food RASPBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
	public static final Food CRANBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
	public static final Food ELDERBERRY = new Builder().nutrition(4).saturationMod(0.4F).build();
	public static final Food WILDBERRY = new Builder().nutrition(4).saturationMod(0.4F).effect(() -> new EffectInstance(Effects.POISON, 40, 1), 0.3F).build();

	public static final Food CHEESE_CURDS = new Builder().nutrition(2).saturationMod(0.4F).build();
	public static final Food CHEESE_SLICE = new Builder().nutrition(4).saturationMod(0.8F).build();

	public static final Food ROASTED_CHESTNUT = new Builder().nutrition(2).saturationMod(0.8F).build();
	public static final Food BANANA = new Builder().nutrition(2).saturationMod(2.0F).build();
	public static final Food BANANA_BREAD = new Builder().nutrition(5).saturationMod(6).build();

	public static void init() {}
}
