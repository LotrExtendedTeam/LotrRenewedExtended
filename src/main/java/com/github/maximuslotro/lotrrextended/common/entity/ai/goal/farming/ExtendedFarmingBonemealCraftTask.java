package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lotr.common.entity.npc.data.ExtendedNpcFarmhandInfoHolder.FarmingWarning;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.item.ItemEntity;
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
 * Task responsible for converting surplus seeds into bone meal using nearby composter blocks.
 * <p>
 * This task triggers when the NPC accumulates a surplus of non-edible seeds beyond a specific threshold. 
 * The NPC navigates to a composter, iteratively deposits seeds on a cooldown timer, directly handles full 
 * composter state transitions, and extracts the resulting bone meal items into the world with entity ownership set.
 * </p>
 * 
 * Based on vanilla brain task:
 * <ul>
 *   <li>{@link FarmerWorkTask}</li>
 * </ul>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingGoalHolder
 * @see ComposterBlock
 */
public class ExtendedFarmingBonemealCraftTask extends ExtendedFarmingAbstractTask {
	/**
	 * Minimum quantity of seeds that must remain in the NPC's inventory during composting operations.
	 */
	private static final int MIN_KEEP = 0;
	/**
	 * Minimum surplus seed count required to validate starting this task.
	 */
	private static final int BONEMEAL_THRESHOLD = 32;
	/**
	 * Cooldown duration in world ticks required between successive composting interactions.
	 */
	private static final int INTERACTION_COOLDOWN_TICKS = 5;
	/**
	 * World tick timestamp tracking the most recent composting interaction.
	 */
	private int lastCompostTime;
	/**
	 * Remembers the item stack held in the NPC's main hand prior to starting the task,
	 * restored upon task termination.
	 */
	private ItemStack previousHeldItem;
	/**
	 * Constructs a new bone meal crafting task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingBonemealCraftTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Checks whether the task can initially be assigned to the NPC based on surplus seed availability.
	 *
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} if non-edible seed inventory exceeds the initial activation threshold
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		return isTaskStillValidForAmount(pServerLevel, BONEMEAL_THRESHOLD);
	}
	/**
	 * Checks whether an active composting task should continue running.
	 *
	 * @param pServerLevel world where the execution check takes place
	 * @return {@code true} if any seed surplus remains above the absolute minimum threshold
	 */
	@Override
	public boolean isTaskStillValidForNPC(ServerWorld pServerLevel) {
		return isTaskStillValidForAmount(pServerLevel, MIN_KEEP);
	}
	/**
	 * Helper method evaluating whether total non-edible seeds in the output inventory exceed a designated threshold.
	 *
	 * @param pServerLevel world where the inventory check takes place
	 * @param amount       threshold amount of seeds required
	 * @return {@code true} if total matching seed count exceeds {@code amount}
	 */
	public boolean isTaskStillValidForAmount(ServerWorld pServerLevel, int amount) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (shouldDepositItem(seedsStack)) {
			return false;
		}
		Inventory inventory = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		int totalSeeds = inventory.countItem(seedsStack.getItem());
		return totalSeeds > amount;
	}
	@Override
    protected Optional<Supplier<BlockPos>> getCacheGetter() {
        return Optional.of(goalHolder.farmhandData.getFarmhandInfo()::getCachedComposter);
    }

    @Override
    protected Optional<Consumer<BlockPos>> getCacheSetter() {
        return Optional.of(goalHolder.farmhandData.getFarmhandInfo()::setCachedComposter);
    }
	/**
	 * Mutates the target block position buffer to point to a valid composter.
	 * <p>
	 * Utilizes a cached composter position if available and within spatial limits; 
	 * falls back to random search behavior if no valid cached position exists.
	 * </p>
	 *
	 * @param taskPosition position buffer mutated in-place
	 * @param pServerLevel world where target selection takes place
	 */
	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		BlockPos cachedComposterPos = getCacheGetter().get().get();
		if (cachedComposterPos != null) {
			if (goalHolder.getEntity().distanceToSqr(cachedComposterPos.getX() + 0.5, cachedComposterPos.getY() + 0.5, cachedComposterPos.getZ() + 0.5) <= MAX_DISTANCE_SQ) {
				taskPosition.set(cachedComposterPos);
				return;
			}
		}
		taskPosition.set(BlockPos.ZERO);
		goalHolder.farmhandData.getFarmhandInfo().setWarning(FarmingWarning.NO_COMPOSTER, true);
	}
	@Override
	public boolean shouldCheckOnce() {
		return true;
	}
	/**
	 * Determines whether the NPC is close enough to interact with the target composter block.
	 *
	 * @param farmer    the entity performing the task
	 * @param targetPos position of the composter block
	 * @return {@code true} if the NPC is within interaction range of {@code targetPos}
	 */
	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1.5);
	}
	/**
	 * Validates whether a given block position contains a composter block.
	 *
	 * @param pComposterPos target coordinates to evaluate
	 * @param pServerLevel  world where the block check occurs
	 * @return {@code true} if the block state at the position is a composter
	 */
	@Override
	public boolean isValidPosForTask(BlockPos pComposterPos, ServerWorld pServerLevel) {
		return pServerLevel.getBlockState(pComposterPos).is(Blocks.COMPOSTER);
	}
	/**
	 * Performs the composting cycle on the target composter block.
	 * <p>
	 * Throttles operations via an interaction cooldown. Deposits surplus seeds from output inventory into 
	 * the composter, triggers visual/sound feedback, directly advances composter growth levels when near completion, 
	 * and empties ready composters into item entities in the world.
	 * </p>
	 *
	 * @param pComposterPos coordinates of the target composter
	 * @param pServerLevel  world where the operation executes
	 * @return {@code true} if the task is finished (e.g., seeds depleted and composter emptied); 
	 *         {@code false} to remain active and continue ticking on future cycles
	 */
	@Override
	public boolean executeTask(BlockPos pComposterPos, ServerWorld pServerLevel) {
		goalHolder.farmhandData.getFarmhandInfo().setWarning(FarmingWarning.NO_COMPOSTER, false);
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

	/**
	 * Extracts bone meal produce from a full composter block, spawning it into the world as an item entity.
	 * <p>
	 * Resets the composter state to empty, plays associated sound effects, and assigns the executing 
	 * entity's UUID as the item entity's owner.
	 * </p>
	 *
	 * @param pState        current block state of the ready composter
	 * @param pLevel        world where the item entity is spawned
	 * @param pComposterPos block coordinates of the composter
	 * @param pEntity       entity collecting the produce, registered as item owner
	 * @return updated block state reflecting the now-emptied composter
	 */
	public static BlockState extractProduce(BlockState pState, World pLevel, BlockPos pComposterPos, Entity pEntity) {
		if (!pLevel.isClientSide) {
			float f = 0.7F;
			double d0 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.15F;
			double d1 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
			double d2 = (double)(pLevel.random.nextFloat() * 0.7F) + (double)0.15F;
			ItemEntity itementity = new ItemEntity(pLevel, pComposterPos.getX() + d0, pComposterPos.getY() + d1, pComposterPos.getZ() + d2, new ItemStack(Items.BONE_MEAL));
			itementity.setDefaultPickUpDelay();
			itementity.setOwner(pEntity.getUUID());// Add entity as owner
			pLevel.addFreshEntity(itementity);
		}

		BlockState blockstate = ComposterBlock.empty(pState, pLevel, pComposterPos);
		pLevel.playSound(null, pComposterPos, SoundEvents.COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
		return blockstate;
	}
	/**
	 * Prepares the NPC for composting interactions.
	 * <p>
	 * Stashes the currently held main-hand item, equips a visual instance of the seed being composted 
	 * into the main hand slot, and resets interaction timer state.
	 * </p>
	 */
	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem()));
		lastCompostTime=0;
	}
	/**
	 * Cleans up composting state upon task completion or cancellation.
	 * <p>
	 * Restores the NPC's original main-hand item stack.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
}
