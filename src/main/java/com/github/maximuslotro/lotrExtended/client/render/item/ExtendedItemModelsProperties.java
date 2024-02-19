package com.github.maximuslotro.lotrExtended.client.render.item;

import lotr.common.init.ExtendedItems;
import lotr.common.item.ExtendedCommandHornItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class ExtendedItemModelsProperties {

	public static ResourceLocation hornType = new ResourceLocation("lotrextended", "horn_type");
	public static void createAlternateModels() {
		ItemModelsProperties.register(ExtendedItems.COMMANDHORN.get(), hornType, (itemStack, clientWorld, LivingEntity) -> {
	         return ExtendedCommandHornItem.isHalt(itemStack) ? 1.0F : ExtendedCommandHornItem.isReddy(itemStack) ? 2.0F : ExtendedCommandHornItem.isSummon(itemStack) ? 3.0F : 0.0F;
	      });
	}
}
