package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedBlockCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Block>{

	public ExtendedBlockCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("block."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<Block> getAsociatedForgeRegistry() {
		return ForgeRegistries.BLOCKS;
	}
}
