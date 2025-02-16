package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;

import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedEffectCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Effect>{

	public ExtendedEffectCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public IForgeRegistry<Effect> getAsociatedForgeRegistry() {
		return ForgeRegistries.POTIONS;
	}
}
