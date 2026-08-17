package com.github.maximuslotro.lotrrextended.common.commands;

import com.mojang.brigadier.Command;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ExtendedValueSetCommand {

	public static int retrieveBooleanValue(CommandSource source, String name, boolean value) {
		return retrieveValue(source, name, Boolean.toString(value));
	}
	public static int retrieveIntValue(CommandSource source, String name, int value) {
		return retrieveValue(source, name, Integer.toString(value));
	}
	public static int retrieveDoubleValue(CommandSource source, String name, double value) {
		return retrieveValue(source, name, Double.toString(value));
	}

	public static int retrieveValue(CommandSource source, String name, String value) {
		IFormattableTextComponent nameT = new StringTextComponent(name).withStyle(TextFormatting.AQUA);
		IFormattableTextComponent valueT = new StringTextComponent(value).withStyle(TextFormatting.GOLD);
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.config.current_value", nameT, valueT).withStyle(TextFormatting.GREEN), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int cantSetValue(CommandSource source, String name) {
		source.sendSuccess(new StringTextComponent(name+" ").withStyle(TextFormatting.AQUA)
			.append(new TranslationTextComponent("command.lotrextended.config.cant").withStyle(TextFormatting.RED)), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int setBooleanValue(CommandSource source, String name, boolean value) {
		IFormattableTextComponent text;
		if(value) {
			text = new TranslationTextComponent("gui.lotrextended.base.enabled").withStyle(TextFormatting.GREEN);
		}else {
			text = new TranslationTextComponent("gui.lotrextended.base.disabled").withStyle(TextFormatting.RED);
		}
		IFormattableTextComponent valueT = new StringTextComponent(" "+name).withStyle(TextFormatting.AQUA);
		source.sendSuccess(text.append(valueT), false);
		return Command.SINGLE_SUCCESS;
	}

	public static int setIntValue(CommandSource source, String name, int value) {
		TextComponent text = new TranslationTextComponent("gui.lotrextended.base.set");
		text.withStyle(TextFormatting.GREEN);
		text.append(" "+name+":").withStyle(TextFormatting.AQUA);
		text.append(" "+ value).withStyle(TextFormatting.GOLD);
		source.sendSuccess(text, false);
		return Command.SINGLE_SUCCESS;
	}

	public static int setDoubleValue(CommandSource source, String name, double value) {
		TextComponent text = new TranslationTextComponent("gui.lotrextended.base.set");
		text.withStyle(TextFormatting.GREEN);
		text.append(" "+name+":").withStyle(TextFormatting.AQUA);
		text.append(" "+ value).withStyle(TextFormatting.GOLD);
		source.sendSuccess(text, false);
		return Command.SINGLE_SUCCESS;
	}
}
