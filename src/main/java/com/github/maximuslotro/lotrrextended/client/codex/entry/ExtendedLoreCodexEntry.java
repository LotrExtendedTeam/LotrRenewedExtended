package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedLoreCodexEntry extends ExtendedBaseCodexEntry{

	public ExtendedLoreCodexEntry(ResourceLocation locationID) {
		super(locationID);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("FOOD");
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.LORE;
	}

    public static class Serializer implements ExtendedBaseSerializer<ExtendedLoreCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedLoreCodexEntry deserializeCodexEntry(ResourceLocation locationID, JsonObject json) {
			return new ExtendedLoreCodexEntry(locationID);
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.LORE;
		}

		@Override
		public JsonObject serializeCodexEntry(JsonObject json, ExtendedLoreCodexEntry entry) {
			return json;
		}
    }
}
