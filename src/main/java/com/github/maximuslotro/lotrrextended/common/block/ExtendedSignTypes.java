package com.github.maximuslotro.lotrrextended.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.block.WoodType;

public class ExtendedSignTypes {
	private static final List<WoodType> allExtendedModTypes = new ArrayList<>();
	public static final WoodType CHESTNUT = register("chestnut");
	public static final WoodType BANANA = register("banana");
	public static final WoodType PLUM = register("plum");
	public static final WoodType WILLOW = register("willow");
	public static final WoodType EBONY = register("ebony");

	public static void forEach(Consumer<WoodType> action) {
		allExtendedModTypes.forEach(action);
	}

	private static WoodType register(String name) {
		WoodType type = WoodType.create("lotrextended:" + name);
		//WoodType.register(type);
		allExtendedModTypes.add(type);
		return type;
	}
}
