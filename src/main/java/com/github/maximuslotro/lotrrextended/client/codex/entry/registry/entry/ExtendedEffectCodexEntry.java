package com.github.maximuslotro.lotrrextended.client.codex.entry.registry.entry;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedForgeRegistryBasedCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedRegistrySerializer;
import com.google.gson.JsonObject;

import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ExtendedEffectCodexEntry extends ExtendedForgeRegistryBasedCodexEntry<Effect>{

	public ExtendedEffectCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("effect."+location.getNamespace()+"."+location.getPath());
	}

	@Override
	public IForgeRegistry<Effect> getAsociatedForgeRegistry() {
		return ForgeRegistries.POTIONS;
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.EFFECT;
	}

    public static class Serializer implements ExtendedRegistrySerializer<ExtendedEffectCodexEntry>{
        public static final Serializer INSTANCE = new Serializer();

		@Override
		public ExtendedEffectCodexEntry deserializeCodexEntry(JsonObject json) {
			return new ExtendedEffectCodexEntry(getLocation(json));
		}

		@Override
		public ExtendedCodexEntryType getID() {
			return ExtendedCodexEntryType.EFFECT;
		}

		@Override
		public JsonObject serializeRegistryCodexEntry(JsonObject json, ExtendedEffectCodexEntry entry) {
			return json;
		}
    }
}
