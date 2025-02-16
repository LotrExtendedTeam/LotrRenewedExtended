package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedBiomeCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Biome>{

	public ExtendedBiomeCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public IForgeRegistry<Biome> getAsociatedForgeRegistry() {
		return ForgeRegistries.BIOMES;
	}
}
