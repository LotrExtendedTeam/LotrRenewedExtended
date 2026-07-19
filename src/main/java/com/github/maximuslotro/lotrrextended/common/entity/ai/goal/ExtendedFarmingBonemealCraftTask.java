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
	private static final double MAX_DISTANCE_SQ = 25.0; // 5 blocks away
	private static final int MIN_KEEP = 16; // Min seed count to keep
	private static final int BONEMEAL_THRESHOLD = 32; // Surplus seeds required to trigger

	public ExtendedFarmingBonemealCraftTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!isNonEdibleSeed(seedsStack)) {
			return false;
		}
		Inventory inventory = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		int totalSeeds = inventory.countItem(seedsStack.getItem());
		return totalSeeds >= (MIN_KEEP + BONEMEAL_THRESHOLD);
	}

	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		if (cachedComposterPos != null) {
			boolean isStillComposter = pServerLevel.getBlockState(cachedComposterPos).is(Blocks.COMPOSTER);
			boolean isCloseEnough = goalHolder.getEntity().distanceToSqr(cachedComposterPos.getX() + 0.5, cachedComposterPos.getY() + 0.5, cachedComposterPos.getZ() + 0.5) <= MAX_DISTANCE_SQ;
			if (isStillComposter && isCloseEnough) {
				taskPosition.set(cachedComposterPos);
				return;
			}
		}
		ExtendedEntityUtils.getRandomPosInBounds(taskPosition, goalHolder.getEntity(), 5, -1, 1);
		if (pServerLevel.getBlockState(taskPosition).is(Blocks.COMPOSTER)) {
			cachedComposterPos = taskPosition.immutable();
		} else {
			cachedComposterPos = null;
		}
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		return pServerLevel.getBlockState(pAboveBlockPos).is(Blocks.COMPOSTER);
	}

	@Override
	public boolean executeTask(BlockPos pComposterPos, ServerWorld pServerLevel) {
		BlockState initialComposterState = pServerLevel.getBlockState(pComposterPos);
		if (initialComposterState.getValue(ComposterBlock.LEVEL) == 8) {
			initialComposterState = ComposterBlock.extractProduce(initialComposterState, pServerLevel, pComposterPos);
		}
		Inventory inventory = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		BlockState workingState = initialComposterState;

		int totalSeedsAvailable = inventory.countItem(seedsStack.getItem());
		for (int slot = inventory.getContainerSize() - 1; slot >= 0; slot--) {
			ItemStack itemstack = inventory.getItem(slot);
			if (!itemstack.isEmpty() && itemstack.sameItem(seedsStack)) {
				while (!itemstack.isEmpty() && totalSeedsAvailable > MIN_KEEP) {
					if (workingState.getValue(ComposterBlock.LEVEL) == 8) {
						workingState = ComposterBlock.extractProduce(workingState, pServerLevel, pComposterPos);
					}
					workingState = ComposterBlock.insertItem(workingState, pServerLevel, itemstack, pComposterPos);
					totalSeedsAvailable--;
					if (workingState.getValue(ComposterBlock.LEVEL) == 7) {
						pServerLevel.levelEvent(1500, pComposterPos, workingState != initialComposterState ? 1 : 0);
						workingState = workingState.setValue(ComposterBlock.LEVEL, 8);
						workingState = extractProduce(workingState, pServerLevel, pComposterPos, goalHolder.getEntity());
					}
				}
			}
		}
		if (workingState.getValue(ComposterBlock.LEVEL) != 8) {
			pServerLevel.levelEvent(1500, pComposterPos, workingState != initialComposterState ? 1 : 0);
		}
		return true;
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
}
