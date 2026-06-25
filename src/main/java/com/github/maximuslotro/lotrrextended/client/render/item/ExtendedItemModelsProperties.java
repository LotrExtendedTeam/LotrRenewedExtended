package com.github.maximuslotro.lotrrextended.client.render.item;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainerNew;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBowItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedBrandingIronItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedCommandHornItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedDaggerItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedModifierScrollItem;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.DaggerItem;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedItemModelsProperties {

	public static ResourceLocation hornType = new ResourceLocation("lotrextended", "horn_type");
	public static ResourceLocation heated = new ResourceLocation("lotrextended", "heated");
	public static ResourceLocation poisoned = new ResourceLocation("lotrextended", "poisoned");
	public static ResourceLocation pull = new ResourceLocation("lotrextended", "pull");
	public static ResourceLocation pulling = new ResourceLocation("lotrextended", "pulling");
	public static ResourceLocation scroll_type = new ResourceLocation("lotrextended", "scroll_type");

	public static void addGuiItemSprites(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(PlayerContainer.BLOCK_ATLAS)) {
			event.addSprite(ExtendedHiredUnitEquipmentContainer.EMPTY_ARMOR_SLOT_BOW);
			event.addSprite(ExtendedHiredUnitEquipmentContainer.EMPTY_ARMOR_SLOT_SWORD);
			event.addSprite(ExtendedRepairContainerNew.EMPTY_SLOT_INGOT);
		}
	}

	public static void registerAlternativeHandheldTextures() {
		ItemModelsProperties.register(ExtendedItems.COMMANDHORN.get(), hornType, (itemStack, clientWorld, LivingEntity) -> ExtendedCommandHornItem.getType(itemStack).isHalt() ? 1 : ExtendedCommandHornItem.getType(itemStack).isReady() ? 2 : ExtendedCommandHornItem.getType(itemStack).isSummon() ? 3 : 0);
		ItemModelsProperties.register(ExtendedItems.BRANDING_IRON.get(), heated, (itemStack, clientWorld, LivingEntity) -> ExtendedBrandingIronItem.isTempered(itemStack) ? 1 : 0);
		ItemModelsProperties.register(ExtendedItems.MODIFIER_SCROLL.get(), scroll_type, (itemStack, clientWorld, LivingEntity) -> ExtendedModifierScrollItem.getModifier(itemStack).getScroll().getId());
		// BOWS
		registerBowItemModelProperties(ExtendedItems.DUNEDAIN_BOW.get());
		registerBowItemModelProperties(ExtendedItems.LONGBOW.get());
		registerBowItemModelProperties(ExtendedItems.ORC_BOW.get());
		registerBowItemModelProperties(ExtendedItems.GUNDABAD_URUK_BOW.get());
		registerBowItemModelProperties(ExtendedItems.RANGER_BOW.get());
		registerBowItemModelProperties(ExtendedItems.RIVENDELL_BOW.get());
		registerBowItemModelProperties(ExtendedItems.LINDON_BOW.get());
		// CROSSBOWS
		registerCrossbowItemModelProperties(ExtendedItems.BRONZE_CROSSBOW.get());
		registerCrossbowItemModelProperties(ExtendedItems.IRON_CROSSBOW.get());
		registerCrossbowItemModelProperties(ExtendedItems.URUK_CROSSBOW.get());
		registerCrossbowItemModelProperties(ExtendedItems.MITHRIL_CROSSBOW.get());
		// DAGGERS
		for(RegistryObject<Item> item : LOTRItems.ITEMS.getEntries()) {
			if(item.get() instanceof DaggerItem || item.get() instanceof ExtendedDaggerItem) {
				registerDaggerItemModelProperties(item.get());
			}
		}
	}

	public static void registerBowItemModelProperties(Item item) {
		ItemModelsProperties.register(item, pull, (thisItem, clientWorld, thisLivingEntity) -> {
			if (thisLivingEntity == null || thisLivingEntity.getUseItem() != thisItem) {
				return 0.0F;
			}
			float num;
			if(thisItem.getItem() instanceof ExtendedBowItem) {
				num = thisLivingEntity.getTicksUsingItem() / (float)((ExtendedBowItem)thisItem.getItem()).getMaterial().getDrawSpeedTicks();
			}else {
				num = thisLivingEntity.getTicksUsingItem() / 20.0F;
			}
			//			if(ExtendedClientConfig.enableDevMode.get() && thisLivingEntity instanceof PlayerEntity) {
			//				PlayerEntity player = ((PlayerEntity)thisLivingEntity);
			//				Minecraft mc = Minecraft.getInstance();
			//				if(mc.player !=null && mc.player.equals(player)) {
			//					mc.gui.getChat().addMessage(new StringTextComponent("TICK: ").withStyle(TextFormatting.GREEN)
			//	                		.append(new StringTextComponent(Integer.toString(thisLivingEntity.getTicksUsingItem())).withStyle(TextFormatting.WHITE)));
			//					mc.gui.getChat().addMessage(new StringTextComponent("PULL: ").withStyle(TextFormatting.GREEN)
			//	                		.append(new StringTextComponent(Float.toString(num)).withStyle(TextFormatting.WHITE)));
			//				}
			//			}
			return num;
		});

		ItemModelsProperties.register(item, pulling, (thisItem, clientWorld, thisLivingEntity) -> thisLivingEntity != null && thisLivingEntity.isUsingItem() && thisLivingEntity.getUseItem() == thisItem ? 1.0F : 0.0F);
	}

	public static void registerCrossbowItemModelProperties(Item item) {
		ItemModelsProperties.register(item, new ResourceLocation("pull"), (thisItem, clientWorld, thisLivingEntity) -> {
			if (thisLivingEntity == null) {
				return 0.0F;
			} else {
				return CrossbowItem.isCharged(thisItem) ? 0.0F : (float)(thisItem.getUseDuration() - thisLivingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(thisItem);
			}
		});
		ItemModelsProperties.register(item, new ResourceLocation("pulling"), (thisItem, clientWorld, thisLivingEntity) -> {
			return thisLivingEntity != null && thisLivingEntity.isUsingItem() && thisLivingEntity.getUseItem() == thisItem && !CrossbowItem.isCharged(thisItem) ? 1.0F : 0.0F;
		});
		ItemModelsProperties.register(item, new ResourceLocation("charged"), (thisItem, clientWorld, thisLivingEntity) -> {
			return thisLivingEntity != null && CrossbowItem.isCharged(thisItem) ? 1.0F : 0.0F;
		});
		ItemModelsProperties.register(item, new ResourceLocation("firework"), (thisItem, clientWorld, thisLivingEntity) -> {
			return thisLivingEntity != null && CrossbowItem.isCharged(thisItem) && CrossbowItem.containsChargedProjectile(thisItem, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
		});
	}

	public static void registerDaggerItemModelProperties(Item item) {
		ItemModelsProperties.register(item, poisoned, (thisItem, clientWorld, thisLivingEntity) -> {
			return ExtendedDaggerItem.isPoisoned(thisItem) ? 1.0f : 0.0f;
		});
	}
}
