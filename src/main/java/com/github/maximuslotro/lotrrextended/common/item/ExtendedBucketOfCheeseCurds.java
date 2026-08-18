package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedBucketOfCheeseCurds extends Item {

	public ExtendedBucketOfCheeseCurds(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(!world.isClientSide) {
			 float f = 0.5F;
	         double d0 = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
	         double d1 = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
	         double d2 = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
	         ItemEntity itementity = new ItemEntity(world, player.getX() + d0, player.getY() + d1, player.getZ() + d2, new ItemStack(ExtendedItems.CHEESE_CURDS.get()));
	         itementity.setDefaultPickUpDelay();
	         world.addFreshEntity(itementity);
	         ItemEntity itementity1 = new ItemEntity(world, player.getX() + d0, player.getY() + d1, player.getZ() + d2, new ItemStack(Items.BUCKET));
	         itementity1.setDefaultPickUpDelay();
	         world.addFreshEntity(itementity1);
		}
		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.abilities.instabuild) {
			heldItem.shrink(1);
		}
		return ActionResult.sidedSuccess(heldItem, world.isClientSide());
	}
}
