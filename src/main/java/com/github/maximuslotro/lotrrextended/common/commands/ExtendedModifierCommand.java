package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class ExtendedModifierCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		// Build the sub-command nodes
		LiteralArgumentBuilder<CommandSource> clear = Commands.literal("clear").executes(context -> clearEnchants(context.getSource()));
		LiteralArgumentBuilder<CommandSource> add = Commands.literal("add").then(Commands.argument("modifierType", StringArgumentType.greedyString())
			.suggests(ENCHANTMENTS).executes(context -> modifyEnchant(context.getSource(), StringArgumentType.getString(context, "modifierType"), true)));
		LiteralArgumentBuilder<CommandSource> remove = Commands.literal("remove").then(Commands.argument("modifierType", StringArgumentType.greedyString())
			.suggests(ENCHANTMENTS).executes(context -> modifyEnchant(context.getSource(), StringArgumentType.getString(context, "modifierType"), false)));
		LiteralArgumentBuilder<CommandSource> generate = Commands.literal("generate").executes(context -> generateEnchant(context.getSource(), false, false))
			.then(Commands.argument("reforge", BoolArgumentType.bool())
				.executes(context -> generateEnchant(context.getSource(), BoolArgumentType.getBool(context, "reforge"), false))
				.then(Commands.argument("noNegatives", BoolArgumentType.bool())
					.executes(context -> generateEnchant(context.getSource(), BoolArgumentType.getBool(context, "reforge"), BoolArgumentType.getBool(context, "noNegatives")))));
		// Register multiple alias commands
		dispatcher.register(Commands.literal("lotr_enchant").requires(context -> context.hasPermission(4)).then(clear).then(add).then(remove).then(generate));
		dispatcher.register(Commands.literal("lotr_modifiers").requires(context -> context.hasPermission(4)).then(clear).then(add).then(remove).then(generate));
		dispatcher.register(Commands.literal("lotrExtendedModifiers").requires(context -> context.hasPermission(4)).then(clear).then(add).then(remove).then(generate));
	}

	public static final SuggestionProvider<CommandSource> ENCHANTMENTS = (ctx, builder) -> {
		return ISuggestionProvider.suggest(ExtendedModifiers.MODIFIER_MAP.keySet(), builder);
	};

	public static int modifyEnchant(CommandSource source, String enchantName, boolean add) {
		if (!ExtendedModifiers.MODIFIER_MAP.containsKey(enchantName)) {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.modifier_invalid", enchantName));
			return 0;
		}
		if (!(source.getEntity() != null && source.getEntity() instanceof PlayerEntity)) {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.generic_error.only_player"));
			return 0;
		}
		ItemStack stack = ((PlayerEntity) source.getEntity()).getItemInHand(Hand.MAIN_HAND);

		ExtendedModifier enchant = ExtendedModifiers.MODIFIER_MAP.get(enchantName);
		if (!enchant.canApplyToItem(stack)) {
			ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchant.getLocalKey());
			source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.modifier_cant_item", newName));
			return 0;
		}
		if(add) {
			// don't allow duplicates
			if (ExtendedModifierUtils.getModifierList(stack).contains(enchant)) {
				source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.modifier_duplicate"));
				return 0;
			}
			// only allow one bane at a time
			if (enchant.getModifierType().equals(ExtendedModifierType.BANE)) {
				List<ExtendedModifier> enchants = ExtendedModifierUtils.getModifierList(stack);
				boolean containsBane = enchants.stream().anyMatch(enchantment -> ExtendedModifierType.BANE.equals(enchantment.getModifierType()));
				if (containsBane) {
					source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.only_one_bane"));
					return 0;
				}
			}
			if (!ExtendedModifierType.isItemApplicableWithModifier(stack, enchant.getModifierType())) {
				source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.modifier_cant_modifier"));
				return 0;
			}
			ExtendedModifierUtils.addModifier(stack, enchant);
			ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchant.getLocalKey());
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.enchant.modifier_added", newName, stack.getItem().getName(stack)).withStyle(TextFormatting.GOLD), true);
		}else {
			if (ExtendedModifierUtils.getModifierList(stack).contains(enchant)) {
				ExtendedModifierUtils.removeModifier(stack, enchant);
				ITextComponent removedName = new TranslationTextComponent("enchant.lotrextended." + enchant.getLocalKey());
				source.sendSuccess(new TranslationTextComponent("command.lotrextended.enchant.modifier_removed", removedName, stack.getItem().getName(stack)).withStyle(TextFormatting.GOLD), true);
			} else {
				source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.modifier_missing"));
				return 0;
			}
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int clearEnchants(CommandSource source) {
		if (!(source.getEntity() != null && source.getEntity() instanceof PlayerEntity)) {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.generic_error.only_player"));
			return 0;
		}

		ItemStack stack = ((PlayerEntity) source.getEntity()).getItemInHand(Hand.MAIN_HAND);
		if (ExtendedModifierUtils.clearModifiers(stack)) {
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.enchant.modifier_removed_all", stack.getItem().getName(stack)).withStyle(TextFormatting.GOLD), true);
		} else {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.enchant.no_present_modifiers"));
			return 0;
		}

		return Command.SINGLE_SUCCESS;
	}

	public static int generateEnchant(CommandSource source, boolean reforge, boolean noNegatives) {
		if (!(source.getEntity() != null && source.getEntity() instanceof PlayerEntity)) {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.generic_error.only_player"));
			return 0;
		}

		ItemStack stack = ((PlayerEntity) source.getEntity()).getItemInHand(Hand.MAIN_HAND);
		ExtendedModifierUtils.generateItemModifiersTagged(source.getEntity().random, stack, false, false);
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.enchant.generated"), true);
		return Command.SINGLE_SUCCESS;
	}
}
