package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEntityUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayerFactory;

/**
 * Based on {@link FarmTask}, {@link FarmerWorkTask}, {@link BoneMealCropsTask}, {@link HoeItem}, {@link FarmlandBlock}
 * */
public class ExtendedFarmingTasks {
	protected ExtendedFarmingGoalHolder goalHolder;

	public ExtendedFarmingTasks(ExtendedFarmingGoalHolder goalHolder) {
		this.goalHolder = goalHolder;
	}

	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		ExtendedEntityUtils.getRandomPosInBounds(taskPosition, goalHolder.farmhand, 8, -4, 4);
	}

	public boolean isTaskValid(ServerWorld pServerLevel) {
		return true;
	}

	public boolean validPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		if(isRePlaceablePos(pAboveBlockPos, pServerLevel)) {
			BlockPos groundPos = pAboveBlockPos.below();
			BlockState groundState = pServerLevel.getBlockState(groundPos);
			if(groundState.getBlock() instanceof FarmlandBlock) {
				return false;
			}
			BlockState modified = groundState.getToolModifiedState(pServerLevel, groundPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.farmhand.getMainHandItem(), ToolType.HOE);
			if(modified!=null && modified.getBlock() instanceof FarmlandBlock) {
				return !pServerLevel.getBlockState(groundPos.below()).is(BlockTags.SAND) && isNearWater(pServerLevel, groundPos);
			}
		}
		return false;
	}

	public void executeAction(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		if(validPosForTask(pAboveBlockPos, pServerLevel)) {
			goalHolder.farmhand.swing(Hand.MAIN_HAND);
			if(isRePlaceablePos(pAboveBlockPos, pServerLevel)) {
				pServerLevel.destroyBlock(pAboveBlockPos, false);
			}
			BlockPos groundPos = pAboveBlockPos.below();
			BlockState modified = pServerLevel.getBlockState(groundPos).getToolModifiedState(pServerLevel, groundPos, null, null, ToolType.HOE);
			if(modified!=null) {
				pServerLevel.setBlock(groundPos, modified, 11);
				pServerLevel.playSound(null, pAboveBlockPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	public boolean closeEnoughToExecuteAction(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1.0);
	}

	private boolean isRePlaceablePos(BlockPos pPos, ServerWorld pServerLevel) {
		BlockState blockstate = pServerLevel.getBlockState(pPos);
		return blockstate.getMaterial().isReplaceable();
	}

	private boolean isPlaceablePos(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		BlockState blockstate = pServerLevel.getBlockState(pAboveBlockPos);
		Block blockBelow = pServerLevel.getBlockState(pAboveBlockPos.below()).getBlock();
		return blockstate.isAir() && blockBelow instanceof FarmlandBlock;
	}

	private boolean isCropsPos(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		BlockState blockstate = pServerLevel.getBlockState(pAboveBlockPos);
		Block block = blockstate.getBlock();
		Block block1 = pServerLevel.getBlockState(pAboveBlockPos.below()).getBlock();
		return block instanceof CropsBlock && ((CropsBlock)block).isMaxAge(blockstate) || blockstate.isAir() && block1 instanceof FarmlandBlock;
	}

	private boolean isUnderCrops(IBlockReader pLevel, BlockPos pPos) {
		BlockState plant = pLevel.getBlockState(pPos.above());
		BlockState state = pLevel.getBlockState(pPos);
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(pLevel, pPos, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
	}

	// Copy private method from FarmlandBlock
	private static boolean isNearWater(IWorldReader pLevel, BlockPos pPos) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
			if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
				return true;
			}
		}
		return false;
	}

	private enum ExtendedFarmingTaskType{
		NAVIGATING_TO_FARMLAND, 
		PREPAIRING_FARMLAND,
		PLANTING_FARMLAND,
		HARVESTING_FARMLAND,
		BONEMEALING_FARMLAND,
		NAVIGATING_TO_COMPOSTER,
		MAKING_BONEMEAL,
		NAVIGATING_TO_CHEST,
		INTERRACTING_WITH_CHEST
	}
}
