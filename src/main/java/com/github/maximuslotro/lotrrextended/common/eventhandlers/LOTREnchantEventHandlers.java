package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.util.LOTREnchantUtils;
import lotr.common.util.representation.LOTREnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LOTREnchantEventHandlers {

    @SubscribeEvent
    public static void addTooltipsForLOTREnchants(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<ITextComponent> entries = new ArrayList<>();
        for (LOTREnchantment enchant : LOTREnchantUtils.getEnchantList(stack)) {
            entries.add(new TranslationTextComponent("enchant.lotrextended." + enchant.getKey()).withStyle(TextFormatting.GRAY));
        }

        if (!entries.isEmpty()) {
            if (event.getToolTip().size() >= 1) {
                event.getToolTip().addAll(1, entries);
            } else {
                event.getToolTip().addAll(0, entries);
            }
        }
    }

    // TODO: Add code for adding extra damage with banes
}
