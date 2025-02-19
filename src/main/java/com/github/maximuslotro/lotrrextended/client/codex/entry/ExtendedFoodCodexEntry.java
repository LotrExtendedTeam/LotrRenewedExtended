package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedFoodCodexEntry extends ExtendedBaseCodexEntry{

	public ExtendedFoodCodexEntry(ResourceLocation locationID) {
		super(locationID);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("FOOD");
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.FOOD;
	}

    public static class Serializer implements ExtendedBaseSerializer<ExtendedFoodCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedFoodCodexEntry deserializeCodexEntry(ResourceLocation locationID, JsonObject json) {
			return new ExtendedFoodCodexEntry(locationID);
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.FOOD;
		}

		@Override
		public JsonObject serializeCodexEntry(JsonObject json, ExtendedFoodCodexEntry entry) {
			return json;
		}
    }
}
