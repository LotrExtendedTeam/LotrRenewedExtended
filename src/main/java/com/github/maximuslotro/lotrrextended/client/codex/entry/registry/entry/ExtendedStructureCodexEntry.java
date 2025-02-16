package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedStructureCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Structure<?>>{

	public ExtendedStructureCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public IForgeRegistry<Structure<?>> getAsociatedForgeRegistry() {
		return ForgeRegistries.STRUCTURE_FEATURES;
	}
}
