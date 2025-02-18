package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedVanillaRegistryKeyBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedDimensionCodexEntry extends ExtendedVanillaRegistryKeyBasedCodexEntry<World, RegistryKey<World>>{

	public ExtendedDimensionCodexEntry(ResourceLocation locationID, ResourceLocation location) {
		super(locationID, location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent(location.toString());
	}

	@Override
	public RegistryKey<Registry<World>> getAsociatedVanillaRegistry() {
		return Registry.DIMENSION_REGISTRY;
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.DIMENSION;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedDimensionCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedDimensionCodexEntry deserializeRegistryCodexEntry(ResourceLocation locationID, ResourceLocation location, JsonObject json) {
			return new ExtendedDimensionCodexEntry(locationID, location);
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.DIMENSION;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedDimensionCodexEntry entry) {
			return json;
		}
    }
}
