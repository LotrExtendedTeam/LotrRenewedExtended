package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

/**
 * Based on {@link BoneMealCropsTask}
 * */
public class ExtendedFarmingBonemealTask extends ExtendedFarmingAbstractTask {
	private ItemStack previousHeldItem;

	public ExtendedFarmingBonemealTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		if(!goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().isEmpty()) {
			return goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().getItem().equals(Items.BONE_MEAL);
		}
		return false;
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		BlockState plantState = pServerLevel.getBlockState(pAboveBlockPos);
		if (plantState.getBlock() instanceof IGrowable) {
			Item plantItem = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem();
			if (plantItem instanceof BlockItem && ((BlockItem)plantItem).getBlock()==plantState.getBlock()) {
				IGrowable igrowable = (IGrowable)plantState.getBlock();
				if (igrowable.isValidBonemealTarget(pServerLevel, pAboveBlockPos, plantState, pServerLevel.isClientSide)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		ItemStack boneMeal = goalHolder.farmhandData.getFarmhandInfo().getBonemealItem();
		if (!boneMeal.isEmpty() && BoneMealItem.growCrop(boneMeal, pServerLevel, pAboveBlockPos)) {
			goalHolder.getEntity().swing(Hand.MAIN_HAND);
			pServerLevel.levelEvent(2005, pAboveBlockPos, 0);
			boneMeal.shrink(1);
		}
		return true;
	}


	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.BONE_MEAL));
	}

	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
}
