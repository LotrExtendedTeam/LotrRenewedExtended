package com.github.maximuslotro.lotrrextended.client.render.item;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import lotr.common.init.ExtendedItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class ExtendedItemModelsProperties {

	public static ResourceLocation hornType = new ResourceLocation("lotrextended", "horn_type");
	public static ResourceLocation heated = new ResourceLocation("lotrextended", "heated");
	public static ResourceLocation poisoned = new ResourceLocation("lotrextended", "poisoned");

	public static void registerAlternativeHandheldTextures() {
		ItemModelsProperties.register(ExtendedItems.COMMANDHORN.get(), hornType, (itemStack, clientWorld, LivingEntity) -> {
			return ExtendedCommandHornItem.isHalt(itemStack) ? 1.0F : ExtendedCommandHornItem.isReddy(itemStack) ? 2.0F : ExtendedCommandHornItem.isSummon(itemStack) ? 3.0F : 0.0F;
		});
		ItemModelsProperties.register(ExtendedItems.BRANDING_IRON.get(), heated, (itemStack, clientWorld, LivingEntity) -> {
			return ExtendedBrandingIronItem.isTempered(itemStack) ? 1.0F : 0.0F;
		});
//		ItemModelsProperties.register(ExtendedItems.BRANDING_IRON.get(), poisoned, (itemStack, clientWorld, LivingEntity) -> {
//			return ExtendedDaggerItem.isPoisoned(itemStack) ? 1.0F : 0.0F;
//		});
		registerBowItemModelProperties(ExtendedItems.DUNEDAIN_BOW.get());
		registerBowItemModelProperties(ExtendedItems.LONGBOW.get());
	}

    public static void registerBowItemModelProperties(Item item) {
        ItemModelsProperties.register(item, new ResourceLocation("pull"), (thisItem, clientWorld, thisLivingEntity) -> {
            if (thisLivingEntity == null) {
                return 0.0F;
            } else {
                return thisLivingEntity.getUseItem() != thisItem ? 0.0F : (float)(thisItem.getUseDuration() - thisLivingEntity.getTicksUsingItem()) / 20.0F;
            }
        });

        ItemModelsProperties.register(item, new ResourceLocation("pulling"), (thisItem, clientWorld, thisLivingEntity) -> {
            return thisLivingEntity != null && thisLivingEntity.isUsingItem() && thisLivingEntity.getUseItem() == thisItem ? 1.0F : 0.0F;
        });
    }
}
