package com.github.maximuslotro.lotrrextended.client.codex.entry;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedUnknownCodexEntry extends ExtendedBaseCodexEntry{
	public static final ExtendedUnknownCodexEntry UNKNOWN = new ExtendedUnknownCodexEntry(LotrExtendedMod.EMPTY);

	public ExtendedUnknownCodexEntry(ResourceLocation locationID) {
		super(locationID);
	}

	public ITextComponent getTitle() {
		return new StringTextComponent("UNKNOWN");
	}

	@Override
	public ExtendedCodexEntryType getID() {
		return ExtendedCodexEntryType.UNKNOWN;
	}

	public boolean isUnknown() {
		return true;
	}
}
