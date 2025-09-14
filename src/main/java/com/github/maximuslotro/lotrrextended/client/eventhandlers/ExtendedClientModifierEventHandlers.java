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
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

public class ExtendedClientModifierEventHandlers {

	@SubscribeEvent
	public static void addTooltipsForLOTREnchants(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if(ExtendedModifierUtils.hasModifiers(stack)) {
			List<ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierList(stack);
			if (!stack.hasCustomHoverName() && !modifiers.isEmpty()) {
				ITextComponent baseName = stack.getItem().getName(stack);
				for(ExtendedModifier enchantment : modifiers) {
					ITextComponent newName = new TranslationTextComponent("enchant.lotrextended." + enchantment.getLocalKey())
						.append(" ").withStyle(style -> style.withItalic(false)).append(baseName.copy().withStyle(style -> style.withItalic(false)));
					baseName = newName;
				}
				event.getToolTip().set(0, baseName);
			}
			List<ITextComponent> entries = new ArrayList<>();
			for (ExtendedModifier modifier : modifiers) {
				if(modifier.getModifierType().equals(ExtendedModifierTypes.BANE)) {
					entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
						.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
						.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal(), new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()+ ".race")))
						.withStyle(TextFormatting.GRAY));
				}else {
					entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
						.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
						.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
						.withStyle(TextFormatting.GRAY));
				}
			}
			event.getToolTip().addAll(1, entries);
		}else if(ExtendedModifierScrollItem.hasModifier(stack)) {
			ExtendedModifier modifier = ExtendedModifierScrollItem.getModifier(stack);
			List<ITextComponent> entries = new ArrayList<>();
			if(modifier.getModifierType().equals(ExtendedModifierTypes.BANE)) {
				entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
					.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
					.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal(), new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()+ ".race")))
					.withStyle(TextFormatting.GRAY));
			}else {
				entries.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
					.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
					.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
					.withStyle(TextFormatting.GRAY));
			}
			event.getToolTip().addAll(1, entries);
		}
	}
}