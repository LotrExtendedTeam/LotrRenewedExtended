package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedBiomeCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Biome>{

	public ExtendedBiomeCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("biome."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<Biome> getAsociatedForgeRegistry() {
		return ForgeRegistries.BIOMES;
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.BIOME;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedBiomeCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedBiomeCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedBiomeCodexEntry(getLocation(json));
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.BIOME;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedBiomeCodexEntry entry) {
			return json;
		}
    }
}
