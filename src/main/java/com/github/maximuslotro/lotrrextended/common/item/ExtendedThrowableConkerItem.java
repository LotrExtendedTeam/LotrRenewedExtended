package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedDeferredComposterInjector;
import com.github.maximuslotro.lotrrextended.common.dispenser.ExtendedDispenserConker;
import com.github.maximuslotro.lotrrextended.common.entity.projectile.ExtendedConkerEntity;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedThrowableConkerItem extends Item{

	public ExtendedThrowableConkerItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
		DispenserBlock.registerBehavior(this, new ExtendedDispenserConker());
		ExtendedDeferredComposterInjector.addCompostable(this, 0.3F);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(!world.isClientSide) {
			ExtendedConkerEntity conker = new ExtendedConkerEntity(player, world);
			conker.setItem(heldItem);
			conker.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(conker);
		}
		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.abilities.instabuild) {
			heldItem.shrink(1);
		}
		return ActionResult.sidedSuccess(heldItem, world.isClientSide());
	}
}
