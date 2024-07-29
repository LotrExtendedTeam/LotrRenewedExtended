package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ExtendedEntitySpawnerItem extends Item{

	public ExtendedEntitySpawnerItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}
	 
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack heldItem = player.getItemInHand(hand);
		if(!world.isClientSide) {
			ExtendedStructureSpawnerEntity conker = new ExtendedStructureSpawnerEntity(world);
			world.addFreshEntity(conker);
		}
		world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.abilities.instabuild) {
			heldItem.shrink(1);
		}
		return ActionResult.sidedSuccess(heldItem, world.isClientSide());
	}
}
