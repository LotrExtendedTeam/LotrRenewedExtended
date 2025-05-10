package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExtendedSignTextList {
	public static final ExtendedSignTextList EMPTY = new ExtendedSignTextList(new ArrayList<>());

	private final List<String> entries;

	public ExtendedSignTextList(List<String> entries) {
		this.entries = entries;
	}

	public String getRandom(Random random) {
		if(entries.size()==0) {
			return "";
		}
		String rt = entries.get(random.nextInt(entries.size()));
		System.out.println("Found: ["+rt+"]");
		return rt;
	}
}
