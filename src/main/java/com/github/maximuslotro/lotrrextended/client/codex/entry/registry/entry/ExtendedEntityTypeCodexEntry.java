package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedEntityTypeCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<EntityType<?>>{
	public static final String ID = "entitytype";

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

	@Override
	public String getID() {
		return ID;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedEntityTypeCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedEntityTypeCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedEntityTypeCodexEntry(getLocation(json));
		}

		@Override
		public String getID() {
			return ExtendedEntityTypeCodexEntry.ID;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedEntityTypeCodexEntry entry) {
			return json;
		}
    }
}
