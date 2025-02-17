package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedEntityTypeCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<EntityType<?>>{

	public ExtendedEntityTypeCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("entity."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<EntityType<?>> getAsociatedForgeRegistry() {
		return ForgeRegistries.ENTITIES;
	}
}
