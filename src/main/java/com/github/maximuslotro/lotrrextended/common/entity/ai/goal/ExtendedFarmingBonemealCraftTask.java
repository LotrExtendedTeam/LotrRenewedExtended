package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEntityUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Based on {@link FarmerWorkTask}
 * */
public class ExtendedFarmingBonemealCraftTask extends ExtendedFarmingAbstractTask {
	// Cached composter position
	private BlockPos cachedComposterPos = null;
	private static final double MAX_DISTANCE_SQ = Math.pow(10, 2);
	private static final int MIN_KEEP = 0; // Min seed count to keep
	private static final int BONEMEAL_THRESHOLD = 32; // Surplus seeds required to trigger
	private static final int INTERACTION_COOLDOWN_TICKS = 5; // 0.5 seconds (20 ticks per second)
	private int lastCompostTime;
	private ItemStack previousHeldItem;

	public ExtendedFarmingBonemealCraftTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		return isTaskStillValidForAmount(pServerLevel, BONEMEAL_THRESHOLD);
	}

	@Override
	public boolean isTaskStillValidForNPC(ServerWorld pServerLevel) {
		return isTaskStillValidForAmount(pServerLevel, MIN_KEEP);
	}

	public boolean isTaskStillValidForAmount(ServerWorld pServerLevel, int amount) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!isNonEdibleSeed(seedsStack)) {
			return false;
		}
		Inventory inventory = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		int totalSeeds = inventory.countItem(seedsStack.getItem());
		return totalSeeds > amount;
	}

	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		if (cachedComposterPos != null) {
			if (goalHolder.getEntity().distanceToSqr(cachedComposterPos.getX() + 0.5, cachedComposterPos.getY() + 0.5, cachedComposterPos.getZ() + 0.5) <= MAX_DISTANCE_SQ) {
				taskPosition.set(cachedComposterPos);
				return;
			}
		}
		ExtendedEntityUtils.getRandomPosInBounds(taskPosition, goalHolder.getEntity(), 5, -1, 1);
	}

	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1.5);
	}

	@Override
	public void invalidateCache() {
		cachedComposterPos = null;
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		return pServerLevel.getBlockState(pAboveBlockPos).is(Blocks.COMPOSTER);
	}

	@Override
	public boolean executeTask(BlockPos pComposterPos, ServerWorld pServerLevel) {
		if(cachedComposterPos==null) {
			cachedComposterPos = pComposterPos.immutable();
		}
		int currentGoalTick = goalHolder.tickTime;
		if (currentGoalTick - lastCompostTime < INTERACTION_COOLDOWN_TICKS) {
			return false; // keep ticking and staying at the composter
		}
		BlockState workingState = pServerLevel.getBlockState(pComposterPos);
		Inventory inventory = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		int totalSeedsAvailable = inventory.countItem(seedsStack.getItem());

		if (workingState.getValue(ComposterBlock.LEVEL) == 8) {
			workingState = extractProduce(workingState, pServerLevel, pComposterPos, goalHolder.getEntity());
			lastCompostTime = currentGoalTick;
			goalHolder.getEntity().swing(Hand.MAIN_HAND);
			return totalSeedsAvailable <= MIN_KEEP; //Exit the task since there no seeds left
		}
		if (totalSeedsAvailable > MIN_KEEP) {
			for (int slot = inventory.getContainerSize() - 1; slot >= 0; slot--) {
				ItemStack itemstack = inventory.getItem(slot);
				if (!itemstack.isEmpty() && itemstack.sameItem(seedsStack)) {
					BlockState initialComposterState = workingState;
					workingState = ComposterBlock.insertItem(workingState, pServerLevel, itemstack, pComposterPos);
					goalHolder.getEntity().swing(Hand.MAIN_HAND);
					lastCompostTime = currentGoalTick;
					pServerLevel.levelEvent(1500, pComposterPos, workingState != initialComposterState ? 1 : 0);
					// If the composter ends at level 7 after insertItem, minecraft waits a second to level 8, bypass here
					if (workingState.getValue(ComposterBlock.LEVEL) == 7) {
						workingState = workingState.setValue(ComposterBlock.LEVEL, 8);
						pServerLevel.setBlock(pComposterPos, workingState, 3);
						// Usually called on composter tick, but move here since we are bypassing the tick
						pServerLevel.playSound(null, pComposterPos, SoundEvents.COMPOSTER_READY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					}
					totalSeedsAvailable--;
					break; 
				}
			}
		}
		if (totalSeedsAvailable <= MIN_KEEP && workingState.getValue(ComposterBlock.LEVEL) != 8) {
			return true; // Exit task if no seeds left, and there isn't a bonemeal to pop from composter
		}
		return false; // Keep returning to process remaining surplus items on upcoming cooldown ticks
	}

	// Copied from ComposterBlock.extractProduce
	public static BlockState extractProduce(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		if (!pLevel.isClientSide) {
			float f = 0.7F;
			double d0 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.15F;
			double d1 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
			double d2 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.15F;
			ItemEntity itementity = new ItemEntity(pLevel, (double)pPos.getX() + d0, (double)pPos.getY() + d1, (double)pPos.getZ() + d2, new ItemStack(Items.BONE_MEAL));
			itementity.setDefaultPickUpDelay();
			itementity.setOwner(pEntity.getUUID());// Add entity as owner
			pLevel.addFreshEntity(itementity);
		}

		BlockState blockstate = ComposterBlock.empty(pState, pLevel, pPos);
		pLevel.playSound((PlayerEntity)null, pPos, SoundEvents.COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
		return blockstate;
	}

	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem()));
		lastCompostTime=0;
	}

	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
}
