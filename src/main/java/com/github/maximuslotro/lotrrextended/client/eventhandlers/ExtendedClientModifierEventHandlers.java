package com.github.maximuslotro.lotrrextended.client.eventhandlers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierTypes;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

public class ExtendedClientModifierEventHandlers {

    @SubscribeEvent
    public static void addTooltipsForLOTREnchants(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(ExtendedModifierUtils.hasModifiers(stack)) {
        	List<ExtendedModifier> enchants = ExtendedModifierUtils.getModifierList(stack);
            if (!stack.hasCustomHoverName() && !enchants.isEmpty()) {
            	ITextComponent baseName = stack.getItem().getName(stack);
        		for(ExtendedModifier enchantment : enchants) {
        			ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchantment.getLocalKey())
        				.append(" ").withStyle(style -> style.withItalic(false)).append(baseName.copy().withStyle(style -> style.withItalic(false)));
        			baseName = newName;
        		}
        		event.getToolTip().set(0, baseName);
            }
            List<ITextComponent> entries = new ArrayList<>();
            for (ExtendedModifier enchant : enchants) {
            	if(enchant.getModifierType().equals(ExtendedModifierTypes.BANE)) {
            		entries.add(new TranslationTextComponent("enchant.lotrextended."+enchant.getLocalKey()).append(": ")
            			.append(new StringTextComponent((enchant.getModifierEffect()>0 ? enchant.getModifierType().getFrontValue() : "")+String.valueOf(enchant.getModifierEffect())+ " "))
                		.append(new TranslationTextComponent(enchant.getModifierType().getKeyLocal(), new TranslationTextComponent("enchant.lotrextended."+enchant.getLocalKey()+ ".race")))
                		.withStyle(TextFormatting.GRAY));
            	}else {
                	entries.add(new TranslationTextComponent("enchant.lotrextended."+enchant.getLocalKey()).append(": ")
                		.append(new StringTextComponent((enchant.getModifierEffect()>0 ? enchant.getModifierType().getFrontValue() : "")+String.valueOf(enchant.getModifierEffect())+ " "))
                		.append(new TranslationTextComponent(enchant.getModifierType().getKeyLocal()))
                		.withStyle(TextFormatting.GRAY));
            	}
            }
            event.getToolTip().addAll(1, entries);
        }
    }
}