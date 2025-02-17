package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedMechanicsCodexEntry extends ExtendedBaseCodexEntry{

	public ExtendedMechanicsCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent(location.toString());
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.MECHANICS;
	}

    public static class Serializer implements ExtendedBaseSerializer<ExtendedMechanicsCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedMechanicsCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedMechanicsCodexEntry(getLocation(json));
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.MECHANICS;
		}

		@Override
		public JsonObject serializeCodexEntry(JsonObject json, ExtendedMechanicsCodexEntry entry) {
			return json;
		}
    }
}
