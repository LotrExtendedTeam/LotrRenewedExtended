package com.github.maximuslotro.lotrrextended.client.codex.entry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedUnknownCodexEntry extends ExtendedBaseCodexEntry{
	public static final ExtendedUnknownCodexEntry UNKNOWN = new ExtendedUnknownCodexEntry(new ResourceLocation("empty"));
	public static final String ID = "unknown";

	public ExtendedUnknownCodexEntry(ResourceLocation location) {
		super(location);
	}

	public ITextComponent getTitle() {
		return new StringTextComponent("UNKNOWN");
	}

	@Override
	public String getID() {
		return ID;
	}
}
