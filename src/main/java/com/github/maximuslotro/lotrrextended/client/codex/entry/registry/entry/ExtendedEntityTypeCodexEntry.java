package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.JsonObject;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedEntityTypeCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<EntityType<?>>{

	public ExtendedEntityTypeCodexEntry(ResourceLocation locationID, ResourceLocation location) {
		super(locationID, location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("entity."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<EntityType<?>> getAsociatedForgeRegistry() {
		return ForgeRegistries.ENTITIES;
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.ENTITYTYPE;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedEntityTypeCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedEntityTypeCodexEntry deserializeRegistryCodexEntry(ResourceLocation locationID, ResourceLocation location, JsonObject json) {
			return new ExtendedEntityTypeCodexEntry(locationID, location);
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.ENTITYTYPE;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedEntityTypeCodexEntry entry) {
			return json;
		}
    }
}
