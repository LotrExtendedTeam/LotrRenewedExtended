package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
/**
 * Farming task responsible for pathfinding to crafting tables and converting harvested crops into seeds.
 * <p>
 * This task activates when the NPC's primary seed supply drops below a designated threshold and harvested 
 * raw crops (such as pumpkins or melon slices) are available in the farmhand's output inventory. It handles 
 * navigating to a valid crafting table block, swapping main-hand item visuals during crafting, iteratively 
 * processing raw crops into seed item stacks, and dropping overflow seeds into the world when internal storage reaches capacity.
 * </p>
 * 
 */
public class ExtendedFarmingSeedCraftTask extends ExtendedFarmingAbstractTask {
	/** Maximum allowable squared distance (10 blocks squared = 100) for reusing the cached crafting table position. */
	private static final double MAX_DISTANCE_SQ = Math.pow(10, 2);
	/** Threshold count below which seed crafting becomes eligible to trigger for the NPC. */
	private static final int CRAFTING_THRESHOLD = 32;
	/** Target maximum count up to which seeds will be crafted and stored in the main seed stack. */
	private static final int CRAFTING_MAX = 64;
	/** Cooldown delay in goal ticks required between consecutive craft operations. */
	private static final int INTERACTION_COOLDOWN_TICKS = 5;
	/** The tick timestamp when the last seed crafting iteration occurred. */
	private int lastCraftTime;
	/** The item stack originally held in the main hand prior to beginning the crafting task. */
	private ItemStack previousHeldItem;
	/**
	 * Constructs a new seed crafting task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingSeedCraftTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Checks whether the NPC needs seed crafting to activate this task.
	 * <p>
	 * Evaluates whether the primary seed supply is below {@link #CRAFTING_THRESHOLD} and whether 
	 * matching raw crop items are present in the output inventory.
	 * </p>
	 *
	 * @param pServerLevel world where the state evaluation occurs
	 * @return {@code true} if the seed count is below the activation threshold and required crops are available
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		return isTaskStillValidForAmount(pServerLevel, CRAFTING_THRESHOLD);
	}
	/**
	 * Checks whether the NPC should continue running the seed crafting task once activated.
	 * <p>
	 * Evaluates whether the seed supply remains below {@link #CRAFTING_MAX} and whether raw crops 
	 * remain available for crafting.
	 * </p>
	 *
	 * @param pServerLevel world where the state evaluation occurs
	 * @return {@code true} if seed crafting should continue toward maximum capacity
	 */
	@Override
	public boolean isTaskStillValidForNPC(ServerWorld pServerLevel) {
		return isTaskStillValidForAmount(pServerLevel, CRAFTING_MAX);
	}
	/**
	 * Helper method evaluating whether the seed count is below a target limit and valid raw crops exist.
	 *
	 * @param pServerLevel world where the state evaluation occurs
	 * @param amount       the seed count limit to evaluate against
	 * @return {@code true} if seed count is less than {@code amount} and more than 1 matching crop is stored
	 */
	public boolean isTaskStillValidForAmount(ServerWorld pServerLevel, int amount) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if(seedsStack.getCount() < amount && getCraftCropFromSeeds(seedsStack.getItem())!=null) {
			return goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().countItem(getCraftCropFromSeeds(seedsStack.getItem()))>1;
		}
		return false;
	}
	@Override
    protected Optional<Supplier<BlockPos>> getCacheGetter() {
        return Optional.of(goalHolder.farmhandData.getFarmhandInfo()::getCachedCraftingTable);
    }

    @Override
    protected Optional<Consumer<BlockPos>> getCacheSetter() {
        return Optional.of(goalHolder.farmhandData.getFarmhandInfo()::setCachedCraftingTable);
    }
	/**
	 * Assigns a target block position for this task, utilizing cached coordinates if still within valid distance.
	 *
	 * @param taskPosition mutable coordinate instance updated with the target block position
	 * @param pServerLevel world where the search occurs
	 */
	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		BlockPos cachedCraftingTablePos = getCacheGetter().get().get();
		if (cachedCraftingTablePos != null) {
			if (goalHolder.getEntity().distanceToSqr(cachedCraftingTablePos.getX() + 0.5, cachedCraftingTablePos.getY() + 0.5, cachedCraftingTablePos.getZ() + 0.5) <= MAX_DISTANCE_SQ) {
				taskPosition.set(cachedCraftingTablePos);
				return;
			}
		}
		super.setRandomBlockByTask(taskPosition, pServerLevel);
	}
	/**
	 * Determines whether the NPC is close enough to the crafting table to execute crafting operations.
	 *
	 * @param farmer    the entity performing the task
	 * @param targetPos target coordinates of the crafting block
	 * @return {@code true} if the entity is within 1.5 blocks of the target block position
	 */
	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1.5);
	}
	/**
	 * Validates whether a given block position contains a crafting table.
	 *
	 * @param pComposterPos target coordinates to evaluate
	 * @param pServerLevel  world where the block check occurs
	 * @return {@code true} if the block state at the position is a crafting table
	 */
	@Override
	public boolean isValidPosForTask(BlockPos pComposterPos, ServerWorld pServerLevel) {
		return pServerLevel.getBlockState(pComposterPos).is(Blocks.CRAFTING_TABLE);
	}
	/**
	 * Executes the seed crafting operation at the target crafting table.
	 * <p>
	 * Processes raw crops from the output inventory into seeds, respects {@link #INTERACTION_COOLDOWN_TICKS} 
	 * between crafting actions, tops off the primary seed stack up to {@link #CRAFTING_MAX}, and drops excess seeds 
	 * into the world if internal storage is capped.
	 * </p>
	 *
	 * @param pComposterPos target block coordinates of the crafting table
	 * @param pServerLevel  world where the execution occurs
	 * @return {@code true} when crafting completes or no valid inputs remain; {@code false} to keep processing on future ticks
	 */
	@Override
	public boolean executeTask(BlockPos pComposterPos, ServerWorld pServerLevel) {
		int currentGoalTick = goalHolder.tickTime;
		if (currentGoalTick - lastCraftTime < INTERACTION_COOLDOWN_TICKS) {
			return false; // keep ticking and staying at the crafting table
		}
		ItemStack targetSeedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		Item cropItem = getCraftCropFromSeeds(targetSeedsStack.getItem());
		if (cropItem == null) {
			return true; // No valid recipe, mark task as complete
		}
		Inventory outputInventory = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		int cropSlot = -1;
		for (int i = 0; i < outputInventory.getContainerSize(); i++) {
			ItemStack stack = outputInventory.getItem(i);
			if (!stack.isEmpty() && stack.getItem() == cropItem) {
				cropSlot = i;
				break;
			}
		}
		if (cropSlot == -1) {
			return true; // No slot found, exit task
		}
		outputInventory.removeItem(cropSlot, 1);
		ItemStack createdSeeds = getCraftFromCrop(cropItem);
		if (createdSeeds != null) {
			int seedsProduced = createdSeeds.getCount();
			int spaceInMainStack = CRAFTING_MAX - targetSeedsStack.getCount();
			if (spaceInMainStack > 0) {
				int toAdd = Math.min(seedsProduced, spaceInMainStack);
				targetSeedsStack.grow(toAdd);
				seedsProduced -= toAdd;
			}
			if (seedsProduced > 0) {
				ItemStack dropStack = new ItemStack(targetSeedsStack.getItem(), seedsProduced);
				dropSeeds(pServerLevel.getBlockState(pComposterPos), pServerLevel, pComposterPos, goalHolder.getEntity(), dropStack);
			}
		}
		goalHolder.getEntity().swing(Hand.MAIN_HAND);
		lastCraftTime = currentGoalTick;
		boolean isSeedsFull = targetSeedsStack.getCount() >= CRAFTING_MAX;
		boolean hasMoreCrops = outputInventory.countItem(cropItem) > 0;
		return isSeedsFull || !hasMoreCrops;
	}
	/**
	 * Spawns an item entity containing crafted seeds at the crafter block location.
	 *
	 * @param pState      block state at the crafter location
	 * @param pLevel      world where the entity will spawn
	 * @param pCrafterPos block coordinates where crafting occurred
	 * @param pEntity     the crafting entity owner
	 * @param dropStack   the item stack of seeds to drop
	 */
	public static void dropSeeds(BlockState pState, World pLevel, BlockPos pCrafterPos, Entity pEntity, ItemStack dropStack) {
		if (!pLevel.isClientSide) {
			float f = 0.7F;
			double d0 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.15F;
			double d1 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
			double d2 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.15F;
			ItemEntity itementity = new ItemEntity(pLevel, pCrafterPos.getX() + d0, pCrafterPos.getY() + d1, pCrafterPos.getZ() + d2, dropStack);
			itementity.setDefaultPickUpDelay();
			itementity.setOwner(pEntity.getUUID());// Add entity as owner
			pLevel.addFreshEntity(itementity);
		}
		// TODO add sounds that play
	}
	/**
	 * Prepares the task for execution.
	 * <p>
	 * Caches the NPC's currently held item stack and temporarily equips a raw crop item in the main hand 
	 * for visual crafting immersion.
	 * </p>
	 */
	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(getCraftCropFromSeeds(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem())));
		lastCraftTime=0;
	}
	/**
	 * Cleans up task state when execution terminates.
	 * <p>
	 * Restores the NPC's originally held main-hand item stack.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
	/**
	 * Determines the required input crop item corresponding to a given seed item.
	 *
	 * @param seeds target seed item
	 * @return matching raw crop item required for crafting, or {@code null} if no recipe mapping exists
	 */
	private @Nullable Item getCraftCropFromSeeds(Item seeds) {
		if (seeds==Items.MELON_SEEDS) {
			return Items.MELON_SLICE;
		}
		if (seeds==Items.PUMPKIN_SEEDS) {
			return Items.PUMPKIN;
		}
		return null;
	}
	/**
	 * Generates the craft result item stack produced from a single unit of the specified crop.
	 *
	 * @param crop input crop item being processed
	 * @return resulting seed {@link ItemStack} with appropriate output yield, or {@code null} if unmapped
	 */
	private @Nullable ItemStack getCraftFromCrop(Item crop) {
		if (crop==Items.MELON_SLICE) {
			return new ItemStack(Items.MELON_SEEDS);
		}
		if (crop==Items.PUMPKIN) {
			return new ItemStack(Items.PUMPKIN_SEEDS, 4);
		}
		return null;
	}
}
