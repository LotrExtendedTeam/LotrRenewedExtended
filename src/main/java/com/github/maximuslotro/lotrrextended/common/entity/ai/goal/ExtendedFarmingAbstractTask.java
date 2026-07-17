package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEntityUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.item.HoeItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

/**
 * This and child classes are based on {@link FarmTask}, {@link FarmerWorkTask}, {@link BoneMealCropsTask}, {@link HoeItem}, {@link FarmlandBlock}
 * */
public abstract class ExtendedFarmingAbstractTask {
	protected ExtendedFarmingGoalHolder goalHolder;

	public ExtendedFarmingAbstractTask(ExtendedFarmingGoalHolder goalHolder) {
		this.goalHolder = goalHolder;
	}

	public abstract boolean isTaskValidForNPC(ServerWorld pServerLevel);

	// default condition
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		ExtendedEntityUtils.getRandomPosInBounds(taskPosition, goalHolder.getEntity(), 5, -1, 1);
	}

	public abstract boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel);

	public void startTask() {}

	public void stopTask() {}

	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1);
	}

	// Return false to defer exiting the task
	public boolean executeTaskChecked(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		if(isValidPosForTask(pAboveBlockPos, pServerLevel)) {
			return executeTask(pAboveBlockPos, pServerLevel);
		}
		return true;
	}
	// Return false to defer exiting the task
	public abstract boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel);

	protected boolean isRePlaceablePos(BlockPos pPos, ServerWorld pServerLevel) {
		BlockState blockstate = pServerLevel.getBlockState(pPos);
		return blockstate.getMaterial().isReplaceable();
	}

	// Copy private method from FarmlandBlock
	protected static boolean isNearWater(IWorldReader pLevel, BlockPos pPos) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
			if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
				return true;
			}
		}
		return false;
	}

	private enum ExtendedFarmingTaskType{
		MAKING_BONEMEAL,
		ADDING_TO_CHEST,
		TAKING_FROM_CHEST
	}
}
