package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedFoodCodexEntry extends ExtendedBaseCodexEntry{
	public static final String ID = "food";

	public ExtendedFoodCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent(location.toString());
	}

	@Override
	public String getID() {
		return ID;
	}

    public static class Serializer implements ExtendedBaseSerializer<ExtendedFoodCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedFoodCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedFoodCodexEntry(getLocation(json));
		}

		@Override
		public String getID() {
			return ExtendedFoodCodexEntry.ID;
		}

		@Override
		public JsonObject serializeCodexEntry(JsonObject json, ExtendedFoodCodexEntry entry) {
			return json;
		}
    }
}
