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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class ExtendedLotrEnchantCommand {

    private static final List<String> OPTIONS = Arrays.asList(
            "add", "remove"
    );
    private static final List<String> CLEAR = Collections.singletonList(
            "clear"
    );

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("lotrenchant").requires(context -> context.hasPermission(4))
                .then(Commands.argument("clear", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            for (String s : CLEAR) {
                                builder.suggest(s);
                            }
                            return builder.buildFuture();
                        })
                        .executes(context -> clearEnchants(
                                context.getSource(),
                                StringArgumentType.getString(context, "clear")
                        )))
        );
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
        LOTREnchantment enchant = LOTREnchantment.ENCHANT_MAP.get(enchantName);
        if (enchant.getItemType().equals("melee")) {
            if (!(stack.getItem() instanceof SwordItem)) {
                source.sendFailure(new StringTextComponent("This modifier can only be applied to melee weapons."));
                return 0;
            }
        }

        if (operation.equals("add")) {
            // only allow one bane at a time
            if (enchant.getEnchantType().equals("bane")) {
                List<LOTREnchantment> enchants = LOTREnchantUtils.getEnchantList(stack);
                boolean containsBane = enchants.stream()
                        .anyMatch(enchantment -> "bane".equals(enchantment.getEnchantType()));
                if (containsBane) {
                    source.sendFailure(new StringTextComponent("Only one bane can be applied to a weapon"));
                    return 0;
                }
            }
            if (LOTREnchantUtils.getEnchantList(stack).contains(enchant)) {
               source.sendFailure(new StringTextComponent("That item already contains that enchant."));
               return 0;
            }

            LOTREnchantUtils.enchant(stack, enchant, 1);
            ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase());
            source.sendSuccess(newName.copy().append(" added to ").append(stack.getItem().getName(stack)).withStyle(TextFormatting.GOLD), true);
        } else if (operation.equals("remove")) {
            if (LOTREnchantUtils.getEnchantList(stack).contains(enchant)) {
                LOTREnchantUtils.removeEnchant(stack, enchant);
                ITextComponent enchantTranslated = new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase());
                source.sendSuccess(enchantTranslated.copy().append(" removed from ").append(stack.getItem().getName(stack)).withStyle(TextFormatting.GOLD), true);
            } else {
                source.sendFailure(new StringTextComponent("That item does not have that enchant."));
                return 0;
            }
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int clearEnchants(CommandSource source, String operation) {
        if (!CLEAR.contains(operation)) {
            source.sendFailure(new StringTextComponent("Invalid operation: " + operation));
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
        if (operation.equals("clear")) {
            if (LOTREnchantUtils.clearEnchants(stack)) {
                source.sendSuccess(new StringTextComponent("All LOTR enchants removed from ").append(stack.getItem().getName(stack)).withStyle(TextFormatting.GOLD), true);
            } else {
                source.sendFailure(new StringTextComponent("That item does not have any enchants."));
                return 0;
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
