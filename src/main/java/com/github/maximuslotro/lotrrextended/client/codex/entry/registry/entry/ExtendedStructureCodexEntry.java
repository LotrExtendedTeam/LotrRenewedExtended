package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedStructureCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Structure<?>>{
	public static final String ID = "structure";

	public ExtendedStructureCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("biome."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<Structure<?>> getAsociatedForgeRegistry() {
		return ForgeRegistries.STRUCTURE_FEATURES;
	}

	@Override
	public String getID() {
		return ID;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedStructureCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedStructureCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedStructureCodexEntry(getLocation(json));
		}

		@Override
		public String getID() {
			return ExtendedStructureCodexEntry.ID;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedStructureCodexEntry entry) {
			return json;
		}
    }
}
