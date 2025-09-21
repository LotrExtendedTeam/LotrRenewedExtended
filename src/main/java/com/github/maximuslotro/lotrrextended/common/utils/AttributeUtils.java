package com.github.maximuslotro.lotrrextended.common.utils;

import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class AttributeUtils {
	public static double getItemAttributeValue(ItemStack stack, EquipmentSlotType slot, Attribute attribute) {
		double d0 = attribute.getDefaultValue();
		double newValue = 0.0;

		Multimap<Attribute, AttributeModifier> modifiers = stack.getAttributeModifiers(slot);

		if (modifiers.containsKey(attribute)) {
			for (AttributeModifier mod : modifiers.get(attribute)) {
				switch (mod.getOperation()) {
				case ADDITION:
					newValue += mod.getAmount();
					break;
				case MULTIPLY_BASE:
					newValue += d0 * mod.getAmount();
					break;
				case MULTIPLY_TOTAL:
					newValue *= 1.0D + mod.getAmount();
					break;
				}
			}
		}
		return attribute.sanitizeValue(newValue);
	}
}