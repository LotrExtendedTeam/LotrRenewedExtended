package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.item.clientside.ExtendedCommandHornClientSide;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ExtendedCommandHornItem extends Item {
	public static final String NBTvalue = "usage_type";

	public ExtendedCommandHornItem(Properties properties) {
		super(properties);
	}

	public ExtendedCommandHornItem() {
		super(new Properties().stacksTo(1).tab(LOTRItemGroups.COMBAT));
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack p_77626_1_) {
		return 40;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entityplayer) {
		if (isHalt(itemstack)) {
			setType(itemstack, 2.0F);
		} else if (isReddy(itemstack)) {
			setType(itemstack, 1.0F);
		}
		world.playSound((PlayerEntity)null, entityplayer.blockPosition(), ExtendedSounds.HORN_BLOW.get(), SoundCategory.PLAYERS, 4.0F, 1.0F);
		return itemstack;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		if (!isNotEmpty(heldItem)) {
			if (world.isClientSide()) {
				new ExtendedCommandHornClientSide().openMenu();
			}
			return ActionResult.sidedSuccess(heldItem, world.isClientSide());
		}
		if (isNotEmpty(heldItem)) {
			player.startUsingItem(hand);
			return ActionResult.sidedSuccess(heldItem, world.isClientSide());
		}
		return ActionResult.fail(heldItem);
	}

	@Override
	public String getDescriptionId(ItemStack itemstack) {
		String s = "";
		if (isHalt(itemstack)) {
			s = ".halt";
		} else if (isReddy(itemstack)) {
			s = ".reddy";
		} else if (isSummon(itemstack)) {
			s = ".summon";
		}
		return super.getDescriptionId()+s;
	}

	public static boolean isNotEmpty(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return compoundnbt != null && compoundnbt.contains(NBTvalue);
	}

	public static boolean isHalt(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return isNotEmpty(itemStack) && compoundnbt.getInt(NBTvalue) == 1.0F;
	}

	public static boolean isReddy(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return isNotEmpty(itemStack) && compoundnbt.getInt(NBTvalue) == 2.0F;
	}

	public static boolean isSummon(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		return isNotEmpty(itemStack) && compoundnbt.getInt(NBTvalue) == 3.0F;
	}

	public static void setType(ItemStack itemStack, float type) {
		if(!itemStack.hasTag()) {
			itemStack.setTag(new CompoundNBT());
		}
		itemStack.getTag().putFloat(NBTvalue, type);
	}
}
