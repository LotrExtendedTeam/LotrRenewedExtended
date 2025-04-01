package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedLootModifiers {
	public static LootConditionType REMOVE_UNIT_COINS = register("remove_unit_coins", new ExtendedRemoveUnitCoinDrops.Serializer());

	private static LootConditionType register(String name, ILootSerializer<? extends ILootCondition> pSerializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation("lotrextended", name), new LootConditionType(pSerializer));
	}

	public static void setup() {}
}
