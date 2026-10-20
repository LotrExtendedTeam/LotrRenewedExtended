package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedStats;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class ExtendedBrandingIronItem extends Item {
	public static final String HEAT = "heat";
	public static final String UserUUID = "TempererName";

	public ExtendedBrandingIronItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack repairItem) {
		return Tags.Items.INGOTS_IRON.contains(repairItem.getItem());
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack pStack, PlayerEntity player, LivingEntity entity, Hand pHand) {
		if(isHeated(pStack)&&getTemperedName(pStack)!=null) {
			if(entity instanceof AnimalEntity) {
				AnimalEntity animal = ((AnimalEntity) entity);
				if(!animal.hasCustomName() || !animal.getCustomName().getString().equals(getTemperedName(pStack))) {
					animal.setCustomName(new StringTextComponent(getTemperedName(pStack)));
					animal.setPersistenceRequired();
					animal.playAmbientSound();
					animal.getJumpControl().jump();
					animal.setLastHurtByMob(player);
					entity.level.playSound(null, entity.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1, 1);
					setTempererName(entity, player.getUUID());
					decreaseHeat(pStack);
					if(!entity.level.isClientSide()) {
						pStack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(pHand));
					}
					player.awardStat(ExtendedStats.ANIMALS_BRANDED);
					return ActionResultType.sidedSuccess(entity.level.isClientSide);
				}
			}
		}
		return super.interactLivingEntity(pStack, player, entity, pHand);
	}

	@Override
	public ActionResultType useOn(ItemUseContext pItemContext) {
		BlockState blockstate = pItemContext.getLevel().getBlockState(pItemContext.getClickedPos());
		if (blockstate.is(BlockTags.FIRE)||blockstate.is(BlockTags.CAMPFIRES)) {
			World world = pItemContext.getLevel();
			if (!world.isClientSide) {
				setHeated(pItemContext.getItemInHand());
			}
			return ActionResultType.sidedSuccess(world.isClientSide);
		} else {
			return super.useOn(pItemContext);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		int heatLevel = getHeated(pStack);
		String heatString = heatLevel >= 5 ? "hot" : heatLevel > 0 ? "warm" : "cold";
		TextFormatting format = heatLevel >= 5 ? TextFormatting.RED : heatLevel > 0 ? TextFormatting.DARK_RED : TextFormatting.BLUE;
		IFormattableTextComponent addition = new TranslationTextComponent("gui.lotrextended.branding_iron."+heatString).withStyle(format);
		pTooltip.add(1, new TranslationTextComponent("gui.lotrextended.branding_iron.heat_level", addition).withStyle(TextFormatting.GRAY));
		String temperedName = getTemperedName(pStack);
		IFormattableTextComponent temperAddition;
		if(temperedName==null || temperedName.isEmpty()) {
			temperAddition = new TranslationTextComponent("gui.lotrextended.base.unset").withStyle(TextFormatting.DARK_GRAY);
		}else {
			temperAddition = new StringTextComponent(temperedName).withStyle(TextFormatting.WHITE);
		}
		pTooltip.add(2, new TranslationTextComponent("gui.lotrextended.branding_iron.named", temperAddition).withStyle(TextFormatting.GRAY));
	}

	public static boolean isHeated(ItemStack itemStack) {
		return getHeated(itemStack)>0;
	}

	public static int getHeated(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(HEAT)) {
			return compoundnbt.getInt(HEAT);
		}
		return 0;
	}

	public static void setHeated(ItemStack itemStack) {
		itemStack.addTagElement(HEAT, IntNBT.valueOf(5));
	}

	public static void decreaseHeat(ItemStack itemStack) {
		if(isHeated(itemStack)) {
			int heat = getHeated(itemStack);
			--heat;
			itemStack.addTagElement(HEAT, IntNBT.valueOf(heat));
		}
	}

	@Nullable
	public static String getTemperedName(ItemStack itemStack) {
		if(itemStack.hasCustomHoverName()) {
			return itemStack.getHoverName().getString();
		}
		return null;
	}

	public static void setTempererName(Entity entity, UUID master) {
		entity.getPersistentData().putString(UserUUID, master.toString());
	}
}
