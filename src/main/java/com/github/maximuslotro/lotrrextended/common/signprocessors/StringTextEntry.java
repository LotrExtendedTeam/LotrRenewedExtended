package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.util.Random;

public class StringTextEntry extends BasicTextEntry {

	private String entry;

	public StringTextEntry(String entry) {
		this.entry = entry;
	}

	@Override
	public String getText(Random rand) {
		return entry;
	}
}
