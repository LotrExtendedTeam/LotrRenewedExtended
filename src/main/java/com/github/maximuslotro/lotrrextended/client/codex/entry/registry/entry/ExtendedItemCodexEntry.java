package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedItemCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Item>{

	public ExtendedItemCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("item."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<Item> getAsociatedForgeRegistry() {
		return ForgeRegistries.ITEMS;
	}
}
