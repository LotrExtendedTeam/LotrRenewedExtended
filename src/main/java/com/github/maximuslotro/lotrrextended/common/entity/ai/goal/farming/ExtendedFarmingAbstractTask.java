package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEntityUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

/**
 * Abstract base task for farmer NPC behavior, managed by {@link ExtendedFarmingGoalHolder}.
 * <p>
 * Concrete implementations define individual sub-tasks such as tilling, planting, harvesting, 
 * or fertilizing.
 * </p>
 * 
 * Based on vanilla brain tasks:
 * <ul>
 *   <li>{@link FarmTask}</li>
 *   <li>{@link FarmerWorkTask}</li>
 *   <li>{@link BoneMealCropsTask}</li>
 *   <li>{@link HoeItem}</li>
 *   <li>{@link FarmlandBlock}</li>
 * </ul>
 */
public abstract class ExtendedFarmingAbstractTask {
	/**
	 * Cached location of a validated work block to avoid repeated spatial searches while in range.
	 */
	protected BlockPos cachedWorkBlockPos = null;
	/**
	 * Vertical search offset radius around the NPC when selecting candidate target blocks.
	 */
	protected static final int YSPLIT = 1;
	/**
	 * Maximum allowable squared distance from the NPC to its cached block before forcing target re-validation.
	 */
	protected static final double MAX_DISTANCE_SQ = Math.pow(12, 2);
	/**
	 * Parent goal instance holding references to the entity, world, and inventory data.
	 */
	protected ExtendedFarmingGoalHolder goalHolder;
	/**
	 * Constructs a new farming task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingAbstractTask(ExtendedFarmingGoalHolder goalHolder) {
		this.goalHolder = goalHolder;
	}
	/**
	 * Checks whether this task can currently be assigned to the NPC based on entity state or inventory requirements.
	 *
	 * @param pServerLevel the world where the task is being checked
	 * @return {@code true} if the NPC is in a valid state/condition to execute this task
	 */
	public abstract boolean isTaskValidForNPC(ServerWorld pServerLevel);
	/**
	 * Checks whether an actively executing or selected task remains valid to continue running.
	 *
	 * @param pServerLevel the world where the task is being checked
	 * @return {@code true} if the task should continue running, {@code false} to abort
	 */
	public boolean isTaskStillValidForNPC(ServerWorld pServerLevel){
		return isTaskValidForNPC(pServerLevel);
	}
	/**
	 * Mutates {@code taskPosition} to either a random block in bounds, or via overrides to a specific position.
	 *
	 * @param taskPosition {@link net.minecraft.util.math.BlockPos.Mutable} to mutate in place
	 * @param pServerLevel world where task is being executed
	 */
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		ExtendedEntityUtils.getRandomPosInBounds(taskPosition, goalHolder.getEntity(), 5, -YSPLIT, YSPLIT);
	}
	/**
	 * Evaluates whether a mutable target block position is valid for executing this task.
	 * <p>
	 * Unlike {@link #isValidPosForTask(BlockPos, ServerWorld)}, subclasses overriding this method 
	 * are permitted to mutate {@code pAboveBlockPos} in-place to adjust the final target coordinates 
	 * (e.g., shifting execution down to the base block of stacked structures) when validation succeeds.
	 * </p>
	 *
	 * @param pAboveBlockPos mutable target position to validate and potentially adjust in-place
	 * @param pServerLevel   world where the task is being checked
	 * @return {@code true} if the position (or its adjusted coordinates) is valid for task execution; {@code false} otherwise
	 */
	public boolean isValidPosForTaskMutable(BlockPos.Mutable pAboveBlockPos, ServerWorld pServerLevel) {
		return isValidPosForTask(pAboveBlockPos, pServerLevel);
	}
	/**
	 * Checks if a target block position is valid for executing this specific task.
	 * <p>
	 * <b>Note:</b> Because {@code pAboveBlockPos} may be mutable or reused, implementation methods 
	 * must not mutate or store this position directly without creating an immutable copy.
	 * </p>
	 *
	 * @param pAboveBlockPos target position to validate
	 * @param pServerLevel   world where task is being executed
	 * @return {@code true} if the position is valid for this task
	 */
	public abstract boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel);
	/**
	 * Invoked when this task is assigned and execution begins.
	 */
	public void startTask() {}
	/**
	 * Invoked when this task is stopped or canceled.
	 */
	public void stopTask() {}
	/**
	 * Invalidate any position caches or internal state when target search fails.
	 */
	public void invalidateCache() {
		cachedWorkBlockPos=null;
	}
	/**
	 * Determines whether the NPC entity is within range to execute the action on the target block position.
	 *
	 * @param farmer    the entity performing the task
	 * @param targetPos target block coordinates
	 * @return {@code true} if the entity is close enough to interact with {@code targetPos}
	 */
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1);
	}
	public boolean executeTaskBase(BlockPos pChosenBlockPos, ServerWorld pServerLevel) {
		if(cachedWorkBlockPos==null) {
			cachedWorkBlockPos = pChosenBlockPos.immutable();
		}
		return executeTask(pChosenBlockPos, pServerLevel);
	}

	/**
	 * Performs the task action on the target block position.
	 *
	 * @param pChosenBlockPos position to modify or execute action upon
	 * @param pServerLevel   world where task is being executed
	 * @return {@code true} if the task action is completed, or {@code false} to defer exiting and keep ticking
	 */
	public abstract boolean executeTask(BlockPos pChosenBlockPos, ServerWorld pServerLevel);
	/**
	 * Helper method to determine if a block position and the block directly above it are replaceable materials, 
	 * and that no liquid is present, as well as another block above it are replaceable materials.
	 *
	 * @param pPos         ground position to evaluate
	 * @param pServerLevel world where block checks occur
	 * @return {@code true} if both {@code pPos} and {@code pPos.above()} are replaceable non-fluid blocks
	 */
	protected boolean isRePlaceablePos(BlockPos pPos, ServerWorld pServerLevel) {
		BlockState blockstate = pServerLevel.getBlockState(pPos);
		FluidState fluidstate = pServerLevel.getFluidState(pPos);
		BlockState aboveState = pServerLevel.getBlockState(pPos.above());
		return blockstate.getMaterial().isReplaceable() && aboveState.getMaterial().isReplaceable() && fluidstate.isEmpty();
	}
	/**
	 * Checks whether water exists within a 9x9 horizontal region (4 blocks radius) and 1 block vertically above the position.
	 * <p>
	 * Direct replication of internal check in {@link FarmlandBlock}.
	 * </p>
	 *
	 * @param pLevel world/level accessor
	 * @param pPos   ground block position being checked
	 * @return {@code true} if water fluid tag is detected nearby
	 */
	protected static boolean isNearWater(IWorldReader pLevel, BlockPos pPos) {
		for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
			if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Determines if an item stack in the output inventory should be deposited into storage.
	 *
	 * @param stack item stack to check
	 * @return {@code true} if the item is a depositable harvest product (crops, sugar cane, or edible seed crops)
	 */
	protected boolean shouldDepositItem(ItemStack stack) {
	    return shouldDepositItem(stack, goalHolder.farmhandData.getFarmhandInfo().getSeedsItem());
	}

	/**
	 * Determines if an item stack should be deposited into storage relative to a active seed stack.
	 * <p>
	 * Sugar Cane and non-seed harvested crops (e.g., Wheat, Beetroot, Melons) are always depositable.
	 * If the item matches the active seed item, it is only depositable if it is edible (e.g., Carrots, Potatoes).
	 * </p>
	 *
	 * @param pStack       the item stack to evaluate
	 * @param pActiveSeeds the active seed item stack assigned to the farmer
	 * @return {@code true} if the item is a depositable harvest product; {@code false} otherwise
	 */
	public static boolean shouldDepositItem(ItemStack pStack, ItemStack pActiveSeeds) {
	    if (pStack.isEmpty()) {
	        return false;
	    }
	    Item item = pStack.getItem();
	    if (item == Items.SUGAR_CANE) {
	        return true;
	    }
	    if (!pActiveSeeds.isEmpty() && item == pActiveSeeds.getItem()) {
	        return item.isEdible();
	    }
	    return true;
	}
}
