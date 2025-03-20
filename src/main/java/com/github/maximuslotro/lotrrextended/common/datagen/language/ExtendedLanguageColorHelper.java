package com.github.maximuslotro.lotrrextended.common.datagen.language;

import net.minecraft.item.DyeColor;

public class ExtendedLanguageColorHelper {
	public static String getColorFullTextChnager(DyeColor form) {
		switch(form) {
			case WHITE:
				return "White";
			case ORANGE:
				return "Orange";
			case MAGENTA:
				return "Magenta";
			case LIGHT_BLUE:
				return "Light Blue";
			case YELLOW:
				return "Yellow";
			case LIME:
				return "Lime";
			case PINK:
				return "Pink";
			case GRAY:
				return "Gray";
			case LIGHT_GRAY:
				return "Light Gray";
			case CYAN:
				return "Cyan";
			case PURPLE:
				return "Purple";
			case BLUE:
				return "Blue";
			case BROWN:
				return "Brown";
			case GREEN:
				return "Green";
			case RED:
				return "Red";
			case BLACK:
				return "Black";
			default:
				return "INVALID_DYE_COLOR";
		}
	}
}
