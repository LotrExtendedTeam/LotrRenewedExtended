package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedColoredText;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum ExtendedUnitHornTypes implements ExtendedColoredText {
	NOTSET(0, "", TextFormatting.WHITE),
	HALT(1, "halt", TextFormatting.RED),
	READY(2, "reddy", TextFormatting.GREEN),
	SUMMON(3, "summon", TextFormatting.BLUE);

	private final int unchangingIndex;
	private final String disc;
	private final TextFormatting formatting;
	private ExtendedUnitHornTypes(int pIndex, String pDisc, TextFormatting pColor) {
		this.unchangingIndex = pIndex;
		this.disc = pDisc;
		this.formatting = pColor;
	}

	public int getUnchangingIndex() {
		return unchangingIndex;
	}

	public boolean isSummon() {
		return this==SUMMON;
	}

	public boolean isHalt() {
		return this==HALT;
	}

	public boolean isReady() {
		return this==READY;
	}

	public boolean isUnset() {
		return this==NOTSET;
	}

	@Override
	public IFormattableTextComponent getText() {
		return new TranslationTextComponent("gui.lotrextended.horn_of_command.type."+disc);
	}

	@Override
	public Integer getColor() {
		return formatting.getColor();
	}

	public TextFormatting getFormatting() {
		return formatting;
	}

	public static ExtendedUnitHornTypes getByUnchangingIndex(int pIndex) {
		for(ExtendedUnitHornTypes order : values()) {
			if(order.getUnchangingIndex()==pIndex) {
				return order;
			}
		}
		return NOTSET;
	}

	public static List<ExtendedUnitHornTypes> getSelectableHornTypes() {
		return Arrays.stream(values())
			.filter(type -> type.getUnchangingIndex() > 0)
			.collect(Collectors.toList());
	}
}
