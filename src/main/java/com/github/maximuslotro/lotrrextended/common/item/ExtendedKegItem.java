package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nonnull;

import com.github.maximuslotro.lotrextended.mixins.lotr.common.item.MixinVesselDrinkItemAccessor;
import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.LOTRMod;
import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedKegItem extends BlockItem {

	public ExtendedKegItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
	}

	@Nonnull
	public static ItemStack getOutputKegForVesselItem(@Nonnull ItemStack pStack) {
		if(pStack.getItem() instanceof VesselDrinkItem) {
			ItemStack output = pStack.copy();
			output.setCount(16);
			ItemStack kegStack = new ItemStack(LOTRItems.KEG.get());
	        CompoundNBT itemNBT = new CompoundNBT();
	        output.save(itemNBT);
	        itemNBT.putByte("Slot", (byte) 9);
	        ListNBT itemsList = new ListNBT();
	        itemsList.add(itemNBT);
	        CompoundNBT kegDroppableData = new CompoundNBT();
	        kegDroppableData.put("Items", itemsList);
	        CompoundNBT blockEntityTag = new CompoundNBT();
	        blockEntityTag.put("KegDroppableData", kegDroppableData);
	        kegStack.getOrCreateTag().put("BlockEntityTag", blockEntityTag);
	        return kegStack;
		}
		ExtendedLog.info("Tried setting an invalid drink into a keg: "+pStack.getItem().getRegistryName().toString());
		return ItemStack.EMPTY;
	}

	@Override
	public void appendHoverText(ItemStack pKegStack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		CompoundNBT blockEntityTag = pKegStack.getTagElement("BlockEntityTag");
		if (blockEntityTag != null && blockEntityTag.contains("KegDroppableData")) {
			CompoundNBT kegData = blockEntityTag.getCompound("KegDroppableData");
			ListNBT items = kegData.getList("Items", 10);
			ItemStack output = ItemStack.EMPTY;
			if(items.size()>0) {
				CompoundNBT itemNBT = items.getCompound(items.size()-1);
				if(itemNBT.contains("Slot") && itemNBT.getByte("Slot")==9) {
					output = ItemStack.of(itemNBT);
				}
			}

			if (!output.isEmpty() && output.getItem() instanceof VesselDrinkItem) {
				VesselDrinkItem drink = (VesselDrinkItem) output.getItem();
				IFormattableTextComponent drinkName = output.getHoverName().copy().withStyle(TextFormatting.GRAY);
				ITextComponent count = StringTextComponent.EMPTY;
				ITextComponent potencyName = StringTextComponent.EMPTY;
				if(output.getCount()>1) {
					count = new TranslationTextComponent("x%s", output.getCount()).withStyle(TextFormatting.WHITE);
				}
				if(drink.hasPotencies) {
					potencyName = VesselDrinkItem.getPotency(output).getDisplayName().withStyle(TextFormatting.WHITE);
				}
				String recipe = kegData.getString("CurrentRecipe");
				int brewTime = kegData.getInt("BrewingTime");
				int brewTimeTotal = kegData.getInt("BrewingTimeTotal");
				if (brewTime > 0 && !recipe.isEmpty()) {
					float progress = (float) brewTime / brewTimeTotal * 100f;
					if(potencyName.equals(StringTextComponent.EMPTY)) {
						tooltip.add(new TranslationTextComponent("Brewing %s", drinkName));
					}else {
						tooltip.add(new TranslationTextComponent("Brewing %s (%s)", drinkName, potencyName).withStyle(TextFormatting.GRAY));
					}
					tooltip.add(new TranslationTextComponent("Brew Progress: %s%%", (int) progress).withStyle(TextFormatting.GRAY));

					return;
				}
				if(potencyName.equals(StringTextComponent.EMPTY)) {
					tooltip.add(new TranslationTextComponent("%s %s", drinkName, count).withStyle(TextFormatting.GRAY));
				}else {
					tooltip.add(new TranslationTextComponent("%s (%s) %s", drinkName, potencyName, count).withStyle(TextFormatting.GRAY));
				}
				if (drink.drinkAlcoholicity > 0.0F) {
					float alc = ((MixinVesselDrinkItemAccessor)drink).invokeGetAlcoholicityForStrength(output) * 10.0F;
					TextFormatting color;
					if (alc < 2.0F) color = TextFormatting.GREEN;
					else if (alc < 5.0F) color = TextFormatting.YELLOW;
					else if (alc < 10.0F) color = TextFormatting.GOLD;
					else if (alc < 20.0F) color = TextFormatting.RED;
					else color = TextFormatting.DARK_RED;

					tooltip.add(new TranslationTextComponent("item.lotr.drink.alcoholicity", alc).withStyle(color));
				}

				if (((MixinVesselDrinkItemAccessor)drink).getDrinkDamage() > 0.0F) {
					float dmg = ((MixinVesselDrinkItemAccessor)drink).invokeGetDrinkDamageForStrength(output);
					tooltip.add(new TranslationTextComponent("item.lotr.drink.damage", dmg).withStyle(TextFormatting.RED));
				}

				if(flag.isAdvanced()) {
					((MixinVesselDrinkItemAccessor)drink).invokeAddPreEffectsTooltip(output, world, tooltip, flag);
					LivingEntity relevantEntity = LOTRMod.PROXY.getClientPlayer();
					VesselDrinkItem.addPotionEffectsToTooltip(output, tooltip, flag, ((MixinVesselDrinkItemAccessor)drink).invokeConvertPotionEffectsForStrengthAndEntity(output, relevantEntity));
				}
			}
		}
	}
}
