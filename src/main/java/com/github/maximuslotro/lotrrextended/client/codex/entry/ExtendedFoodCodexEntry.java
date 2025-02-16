package com.github.maximuslotro.lotrrextended.client.codex.entry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedFoodCodexEntry extends ExtendedBaseCodexEntry{

	public ExtendedFoodCodexEntry(ResourceLocation location) {
		super(location);
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent(location.toString());
	}

}
