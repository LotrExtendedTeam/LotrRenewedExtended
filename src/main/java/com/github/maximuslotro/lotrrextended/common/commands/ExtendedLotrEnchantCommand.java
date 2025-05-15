package com.github.maximuslotro.lotrrextended.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import lotr.common.util.LOTREnchantUtils;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;

import java.util.*;

public class ExtendedLotrEnchantCommand {

    private static final List<String> OPTIONS = Arrays.asList(
            "add", "remove"
    );

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("lotrenchant").requires(context -> context.hasPermission(4))
                .then(Commands.argument("add_or_remove", StringArgumentType.word())
                .suggests((context, builder) -> {
                    for (String s : OPTIONS) {
                        builder.suggest(s);
                    }
                    return builder.buildFuture();
                 })
                .then(Commands.argument("enchant", StringArgumentType.word())
                .suggests((context, builder) -> {
                    for (String s : LOTREnchantment.ENCHANT_MAP.keySet()) {
                        builder.suggest(s);
                    }
                    return builder.buildFuture();
                })
                .executes(context -> enchantItem(
                        context.getSource(),
                        StringArgumentType.getString(context, "add_or_remove"),
                        StringArgumentType.getString(context, "enchant"))
                )))
        );
    }

    public static int enchantItem(CommandSource source, String operation, String enchantName) {
        if (!OPTIONS.contains(operation)) {
            source.sendFailure(new StringTextComponent("Invalid operation: " + operation));
            return 0;
        }

        if (!LOTREnchantment.ENCHANT_MAP.containsKey(enchantName)) {
            source.sendFailure(new StringTextComponent("Invalid enchant: " + enchantName));
            return 0;
        }

        PlayerEntity player;
        if (!(source.getEntity() != null && source.getEntity() instanceof PlayerEntity)) {
            source.sendFailure(new StringTextComponent("This command can only be run by a player"));
            return 0;
        } else {
            player = (PlayerEntity) source.getEntity();
        }

        ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        LOTREnchantment enchantToAdd = LOTREnchantment.ENCHANT_MAP.get(enchantName);
        if (enchantToAdd.getItemType().equals("melee")) {
            if (!(stack.getItem() instanceof SwordItem)) {
                source.sendFailure(new StringTextComponent("This modifier can only be applied to melee weapons."));
                return 0;
            }
        }

        // only allow one bane at a time
        if (enchantToAdd.getEnchantType().equals("bane")) {
            List<LOTREnchantment> enchants = LOTREnchantUtils.getEnchantList(stack);
            boolean containsBane = enchants.stream()
                    .anyMatch(enchantment -> "bane".equals(enchantment.getEnchantType()));
            if (containsBane) {
                source.sendFailure(new StringTextComponent("Only one bane can be applied to a weapon"));
                return 0;
            }
        }

        source.sendSuccess(new StringTextComponent(operation + " " + enchantName + " on " + stack), true);
        if (operation.equals("add")) {
            LOTREnchantUtils.enchant(stack, enchantToAdd, 1);
            source.sendSuccess(new StringTextComponent(LOTREnchantUtils.getEnchantList(stack).toString()), true);
        } else {
            // TODO: Add remove functionality
        }

        return Command.SINGLE_SUCCESS;
    }
}
