package com.github.maximuslotro.lotrrextended.client.codex.entry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class ExtendedBaseCodexEntry {

	/*
	 * Location of the codex entry
	 * */
	protected ResourceLocation location;

	public ExtendedBaseCodexEntry(ResourceLocation location) {
		this.location = location;
	}

	/*
	 * Gets the title for this codex entry, could be {@link StringTextComponent} or {@Link TranslationTextComponent}
	 * @return A {@Link ITextComponent} of the title
	 * */
	public abstract ITextComponent getTitle();

	/*
	 * Get the locaion of this codex entry
	 * @return A {@Link ResourceLocation} address of the entry
	 * */
	public ResourceLocation getLocation() {
		return location;
	}
}
