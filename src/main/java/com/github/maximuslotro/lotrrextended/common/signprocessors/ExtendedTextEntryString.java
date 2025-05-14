package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.util.Random;

public class ExtendedTextEntryString extends ExtendedTextEntryBasic {

	private String entry;

	public ExtendedTextEntryString(String entry) {
		this.entry = entry;
	}

	@Override
	public String getText(Random rand) {
		return entry;
	}
}
