package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedVanillaRegistryKeyBasedCodexEntry;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedDimensionCodexEntry extends ExtendedVanillaRegistryKeyBasedCodexEntry<World, RegistryKey<World>>{

	public ExtendedDimensionCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent(location.toString());
	}

	@Override
	public RegistryKey<Registry<World>> getAsociatedVanillaRegistry() {
		return Registry.DIMENSION_REGISTRY;
	}

}
