package com.github.maximuslotro.lotrrextended.client.eventhandlers;

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

public class LOTREnchantEventHandlerClient {

    @SubscribeEvent
    public static void addTooltipsForLOTREnchants(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<LOTREnchantment> enchants = LOTREnchantUtils.getEnchantList(stack);
        if (!stack.hasCustomHoverName() && !enchants.isEmpty()) {
        	ITextComponent baseName = stack.getItem().getName(stack);
    		for(LOTREnchantment enchantment : enchants) {
    			ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchantment.getKey().toLowerCase())
    				.append(" ").withStyle(style -> style.withItalic(false)).append(baseName.copy().withStyle(style -> style.withItalic(false)));
    			baseName = newName;
    		}
    		event.getToolTip().set(0, baseName);
        }
        List<ITextComponent> entries = new ArrayList<>();
        for (LOTREnchantment enchant : enchants) {
        	entries.add(new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase()).append(": ")
        		.append(new TranslationTextComponent("enchant.lotrextended." + enchant.getKey().toLowerCase() + ".desc")).withStyle(TextFormatting.GRAY));
        }
        event.getToolTip().addAll(1, entries);
    }
}
