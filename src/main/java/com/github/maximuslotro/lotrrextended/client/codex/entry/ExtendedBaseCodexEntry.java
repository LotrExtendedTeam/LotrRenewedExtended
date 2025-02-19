package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class ExtendedBaseCodexEntry {

	/*
	 * File/ID location of this codex entry
	 * */
	private final ResourceLocation locationID;
	
	public ExtendedBaseCodexEntry(ResourceLocation locationID) {
		this.locationID = locationID;
	}

	/*
	 * Gets the title for this codex entry, could be {@link StringTextComponent} or {@Link TranslationTextComponent}
	 * @return A {@Link ITextComponent} of the title
	 * */
	public abstract ITextComponent getTitle();

	/*
	 * Gets the {@Link ExtendedCodexEntryType} for this codex entry
	 * @return A {@Link ExtendedCodexEntryType}
	 * */
	public abstract ExtendedCodexEntryType getID();

	/*
	 * Get the File/ID location of this codex entry
	 * @return A {@Link ResourceLocation} address of the entry
	 * */
	public ResourceLocation getLocationID() {
		return locationID;
	}

	/**
	 * Checks if this codex entry is an invalid or unknown type
	 * @return True if unknown
	 * */
	public boolean isUnknown() {
		return false;
	}

	@Override
	public String toString() {
		return "[locID:"+locationID.toString()+"]";
	}
}