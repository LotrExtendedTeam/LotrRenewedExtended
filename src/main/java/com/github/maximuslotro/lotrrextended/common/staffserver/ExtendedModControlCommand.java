package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.util.Map;
import java.util.Set;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ExtendedModControlCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("mod_control")
				.requires(cs -> cs.hasPermission(3))
				.then(Commands.literal("allowlist")
						.then((Commands.literal("add")
								.then((Commands.argument("modid", StringArgumentType.word())
										.executes(ctx -> addMod(ctx.getSource(), StringArgumentType.getString(ctx, "modid"), ExtendedSpecialConfig.allowList))))))
						.then((Commands.literal("remove")
								.then((Commands.argument("modid", StringArgumentType.word())
										.suggests(SUGGEST_ALLOWLIST)
										.executes(ctx -> removeMod(ctx.getSource(), StringArgumentType.getString(ctx, "modid"), ExtendedSpecialConfig.allowList))))))
						.then((Commands.literal("list")
								.executes(ctx -> listMods(ctx.getSource(), ExtendedSpecialConfig.allowList))))
						)
				.then(Commands.literal("banlist")
						.then((Commands.literal("add")
								.then((Commands.argument("modid", StringArgumentType.word())
										.then((Commands.argument("message", StringArgumentType.word())
												.executes(ctx -> addMod(ctx.getSource(), StringArgumentType.getString(ctx, "modid"), StringArgumentType.getString(ctx, "message"), ExtendedSpecialConfig.autoBanMap))))))))
						.then((Commands.literal("remove")
								.then((Commands.argument("modid", StringArgumentType.word())
										.suggests(SUGGEST_BANLIST)
										.executes(ctx -> removeMod(ctx.getSource(), StringArgumentType.getString(ctx, "modid"), ExtendedSpecialConfig.autoBanMap))))))
						.then((Commands.literal("list")
								.executes(ctx -> listMods(ctx.getSource(), ExtendedSpecialConfig.autoBanMap))))
						)
				);
	}

	public static final SuggestionProvider<CommandSource> SUGGEST_ALLOWLIST = (context, builder) -> {
		return ISuggestionProvider.suggest(ExtendedSpecialConfig.allowList, builder);
	};

	public static final SuggestionProvider<CommandSource> SUGGEST_BANLIST = (context, builder) -> {
		return ISuggestionProvider.suggest(ExtendedSpecialConfig.autoBanMap.keySet(), builder);
	};

	private static int addMod(CommandSource source, String modid, Set<String> modList) {
		if (modList.add(modid)) {
			source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Added mod: " + TextFormatting.YELLOW + modid), true);
		} else {
			source.sendFailure(new StringTextComponent(TextFormatting.RED + "Mod already exists: " + modid));
		}
		ExtendedSpecialConfig.saveAll();
		return Command.SINGLE_SUCCESS;
	}

	private static int removeMod(CommandSource source, String modid, Set<String> modList) {
		if (modList.remove(modid)) {
			source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Removed mod: " + TextFormatting.YELLOW + modid), true);
		} else {
			source.sendFailure(new StringTextComponent(TextFormatting.RED + "Mod not found: " + modid));
		}
		ExtendedSpecialConfig.saveAll();
		return Command.SINGLE_SUCCESS;
	}

	private static int listMods(CommandSource source, Set<String> modList) {
		if (modList.isEmpty()) {
			source.sendSuccess(new StringTextComponent(TextFormatting.RED + "No mods added."), false);
		} else {
			source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Current mods: " + TextFormatting.YELLOW + String.join(", ", modList)), false);
		}
		return Command.SINGLE_SUCCESS;
	}

	private static int addMod(CommandSource source, String modid, String message, Map<String, String> modList) {
		if (!modList.containsKey(modid) || modList.get(modid)!=message) {
			source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Added mod: " + TextFormatting.YELLOW + modid), true);
		} else {
			source.sendFailure(new StringTextComponent(TextFormatting.RED + "Mod already exists: " + modid));
		}
		ExtendedSpecialConfig.saveAll();
		return Command.SINGLE_SUCCESS;
	}

	private static int removeMod(CommandSource source, String modid, Map<String, String> modList) {
		if (modList.containsKey(modid)) {
			modList.remove(modid);
			source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Removed mod: " + TextFormatting.YELLOW + modid), true);
		} else {
			source.sendFailure(new StringTextComponent(TextFormatting.RED + "Mod not found: " + modid));
		}
		ExtendedSpecialConfig.saveAll();
		return Command.SINGLE_SUCCESS;
	}

	private static int listMods(CommandSource source, Map<String, String> modList) {
		if (modList.isEmpty()) {
			source.sendSuccess(new StringTextComponent(TextFormatting.RED + "No mods added."), false);
		} else {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : modList.entrySet()) {
				sb.append(entry.getKey())
				.append(": ")
				.append(entry.getValue())
				.append(", ");
			}
			// Remove trailing comma and space
			if (sb.length() >= 2) sb.setLength(sb.length() - 2);

			source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + "Current mods: " + TextFormatting.YELLOW + sb.toString()), false);
		}
		return Command.SINGLE_SUCCESS;
	}
}