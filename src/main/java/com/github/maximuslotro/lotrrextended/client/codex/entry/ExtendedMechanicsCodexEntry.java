package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedMechanicsCodexEntry extends ExtendedBaseCodexEntry{

	public ExtendedMechanicsCodexEntry(ResourceLocation locationID) {
		super(locationID);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("MECHANICS");
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.MECHANICS;
	}

    public static class Serializer implements ExtendedBaseSerializer<ExtendedMechanicsCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedMechanicsCodexEntry deserializeCodexEntry(ResourceLocation locationID, JsonObject json) {
			return new ExtendedMechanicsCodexEntry(locationID);
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
