package com.github.maximuslotro.lotrrextended.common.loot.functions;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedLootFunctions {
	public static LootFunctionType WARG_FUR_TYPE = register("warg_fur_type", new ExtendedSetWargFurType.Serializer());

	private static LootFunctionType register(String name, ILootSerializer<? extends ILootFunction> serializer) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation("lotrextended", name), new LootFunctionType(serializer));
	}

	public static void setup() {}
}
