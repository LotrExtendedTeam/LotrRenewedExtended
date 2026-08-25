package com.github.maximuslotro.lotrrextended.common.block;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

/**
 * Items need to be added to the composter block after all registries have fired, so use this class to defer the adding.
 * */
public class ExtendedDeferredComposterInjector {
	private static final Map<IItemProvider, Float> COMPOSTABLES = new HashMap<>();

	public static void addCompostable(IItemProvider pItemProvider, Float pChance) {
		COMPOSTABLES.put(pItemProvider, pChance);
	}

	public static void registerCompostables() {
		COMPOSTABLES.forEach((itemProvider, chance) -> ComposterBlock.add(chance, itemProvider));
	}
}