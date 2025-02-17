package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

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

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.ITEM;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedItemCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedItemCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedItemCodexEntry(getLocation(json));
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.ITEM;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedItemCodexEntry entry) {
			return json;
		}
    }
}
