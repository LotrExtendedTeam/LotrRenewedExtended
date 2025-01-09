package com.github.maximuslotro.lotrrextended.common.world.gen.tree;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unchecked")
public class ExtendedFoilagePlacers {
	public static FoliagePlacerType<BananaFoliagePlacer> BANANA_FOLIAGE;

	public static void registerLeafBuilders() {
		BANANA_FOLIAGE = registerLeafBuilders("banana_foliage_placer", BananaFoliagePlacer.CODEC);
	}

	@SuppressWarnings("rawtypes")
	private static FoliagePlacerType registerLeafBuilders(String name, Codec codec) {
		FoliagePlacerType type = new FoliagePlacerType(codec);
		type.setRegistryName("lotr", name);
		ForgeRegistries.FOLIAGE_PLACER_TYPES.register(type);
		return type;
	}
}