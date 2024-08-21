package com.github.maximuslotro.lotrrextended.client.render.item;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;

import lotr.common.init.ExtendedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ExtendedItemModelsProperties {

	public static ResourceLocation hornType = new ResourceLocation("lotrextended", "horn_type");
	public static ResourceLocation heated = new ResourceLocation("lotrextended", "heated");
	public static ResourceLocation poisoned = new ResourceLocation("lotrextended", "poisoned");
	public static ResourceLocation pull = new ResourceLocation("lotrextended", "pull");
	public static ResourceLocation pulling = new ResourceLocation("lotrextended", "pulling");

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
		ItemModelsProperties.register(item, pull, (thisItem, clientWorld, thisLivingEntity) -> {
			if (thisLivingEntity == null || thisLivingEntity.getUseItem() != thisItem) {
				return 0.0F;
			}
			float num;
			if(thisItem.getItem() instanceof ExtendedBowItem) {
				num = thisLivingEntity.getTicksUsingItem() / (float)((ExtendedBowItem)thisItem.getItem()).getMaterial().getDrawSpeed();
			}else {
				num = thisLivingEntity.getTicksUsingItem() / 20.0F;
			}
			if(ExtendedClientConfig.enableDevMode.get() && thisLivingEntity instanceof PlayerEntity) {
				PlayerEntity player = ((PlayerEntity)thisLivingEntity);
				Minecraft mc = Minecraft.getInstance();
				if(mc.player !=null && mc.player.equals(player)) {
					mc.gui.getChat().addMessage(new StringTextComponent("TICK: ").withStyle(TextFormatting.GREEN)
	                		.append(new StringTextComponent(Integer.toString(thisLivingEntity.getTicksUsingItem())).withStyle(TextFormatting.WHITE)));
					mc.gui.getChat().addMessage(new StringTextComponent("PULL: ").withStyle(TextFormatting.GREEN)
	                		.append(new StringTextComponent(Float.toString(num)).withStyle(TextFormatting.WHITE)));
				}
			}
			return num;
		});

        ItemModelsProperties.register(item, pulling, (thisItem, clientWorld, thisLivingEntity) -> {
            return thisLivingEntity != null && thisLivingEntity.isUsingItem() && thisLivingEntity.getUseItem() == thisItem ? 1.0F : 0.0F;
        });
    }
}
