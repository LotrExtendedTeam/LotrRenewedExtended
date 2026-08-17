package com.github.maximuslotro.lotrrextended.common.utils;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedEffects;

import lotr.common.item.VesselDrinkItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

public class ExtendedDrinkUtils {
	public static ItemStack createDrink(Random rand, ItemStack drink) {
		drink.setCount(16);
		VesselDrinkItem.setPotency(drink, VesselDrinkItem.Potency.randomForNPC(rand));
		return drink;
	}

	public static ItemStack drinkOfPotency(Item item, VesselDrinkItem.Potency potency) {
		ItemStack drink = new ItemStack(item);
		drink.setCount(1);
		VesselDrinkItem.setPotency(drink, potency);
		return drink;
	}

	public static void addDeadlyPoison(LivingEntity entity) {
		entity.addEffect(new EffectInstance(ExtendedEffects.DEADLY_POISON.get(), 1200));
	}
}
