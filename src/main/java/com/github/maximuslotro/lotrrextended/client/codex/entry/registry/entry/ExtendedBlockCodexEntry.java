package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedBlockCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Block>{
	public static final String ID = "block";

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

	@Override
	public String getID() {
		return ID;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedBlockCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedBlockCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedBlockCodexEntry(getLocation(json));
		}

		@Override
		public String getID() {
			return ExtendedBlockCodexEntry.ID;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedBlockCodexEntry entry) {
			return json;
		}
    }
}
