package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedLoreCodexEntry extends ExtendedBaseCodexEntry{
	public static final String ID = "lotr";

	public ExtendedLoreCodexEntry(ResourceLocation location) {
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

    public static class Serializer implements ExtendedBaseSerializer<ExtendedLoreCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedLoreCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedLoreCodexEntry(getLocation(json));
		}

		@Override
		public String getID() {
			return ExtendedLoreCodexEntry.ID;
		}

		@Override
		public JsonObject serializeCodexEntry(JsonObject json, ExtendedLoreCodexEntry entry) {
			return json;
		}
    }
}
