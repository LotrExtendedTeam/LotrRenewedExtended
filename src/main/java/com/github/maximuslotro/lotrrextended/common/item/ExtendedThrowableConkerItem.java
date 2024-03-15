package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.dispenser.ExtendedDispenserConker;
import lotr.common.entity.projectile.ExtendedConkerEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedThrowableConkerItem extends Item{

	public ExtendedThrowableConkerItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
		DispenserBlock.registerBehavior(this, new ExtendedDispenserConker());
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if(!world.isClientSide) {
			world.addFreshEntity(new ExtendedConkerEntity(world));
			world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		}
		if(!player.isCreative()) {
			player.getItemInHand(hand).shrink(1);
		}
		return ActionResult.success(player.getItemInHand(hand));
	}
}
