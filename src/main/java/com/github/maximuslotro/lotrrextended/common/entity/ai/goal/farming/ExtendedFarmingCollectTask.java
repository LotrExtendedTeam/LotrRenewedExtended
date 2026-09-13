package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
/**
 * Task responsible for scanning nearby dropped item entities, navigating to them, and collecting them.
 * <p>
 * This task targets world {@link ItemEntity} instances within a spatial bounding box surrounding the NPC.
 * To prevent NPCs from stealing player drops, filtering ensures the NPC only seeks items explicitly 
 * registered with its own UUID ownership tag (such as drops produced from harvesting or composting).
 * </p>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingGoalHolder
 * @see ItemEntity
 */
public class ExtendedFarmingCollectTask extends ExtendedFarmingAbstractTask {
	/**
	 * Cached stack used as a fallback inventory space check template.
	 */
	private static ItemStack dummyPickupItem = new ItemStack(Items.BARRIER);
	/**
	 * Remembers the item stack held in the NPC's main hand prior to starting the task,
	 * restored when collection terminates or cancels.
	 */
	private ItemStack previousHeldItem;
	/**
	 * Constructs a new item collection task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingCollectTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Checks whether the task can currently be assigned to the NPC based on nearby items.
	 * <p>
	 * Performs a spatial bounding-box search around the NPC to detect any owned item entities 
	 * that are eligible for pickup.
	 * </p>
	 *
	 * @param pServerLevel world where the spatial search takes place
	 * @return {@code true} if at least one applicable, owned item entity is detected nearby
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		/*
		ItemStack seeds = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!seeds.isEmpty() && seeds.getCount() < seeds.getMaxStackSize()) {
			return true;
		}
		if(goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(dummyPickupItem)) {
			return true;
		}*/
		// Also valid if inventory is full, BUT there are dropped items nearby that are owned
		List<ItemEntity> nearbyOwnedItems = pServerLevel.getEntitiesOfClass(ItemEntity.class, goalHolder.getEntity().getBoundingBox().inflate(8.0D, 3.0D, 8.0D), this::isItemEntityApplicable);
		return !nearbyOwnedItems.isEmpty();
	}
	/**
	 * Mutates the target block position buffer to point to the location of a nearby valid item entity.
	 *
	 * @param taskPosition position buffer mutated in-place if an applicable item entity is found
	 * @param pServerLevel world where the entity search takes place
	 */
	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		List<ItemEntity> items = pServerLevel.getEntitiesOfClass(ItemEntity.class, goalHolder.getEntity().getBoundingBox().inflate(8.0D, 3.0D, 8.0D));
		for (ItemEntity itemEntity : items) {
			if (isItemEntityApplicable(itemEntity) && goalHolder.isWithinBounds(itemEntity.blockPosition())) {
				taskPosition.set(itemEntity.blockPosition());
				return;
			}
		}
		taskPosition.set(BlockPos.ZERO);
	}
	/**
	 * Validates whether a specific block position contains at least one applicable item entity.
	 *
	 * @param pAboveBlockPos block coordinates to evaluate for item entity presence
	 * @param pServerLevel   world where the check takes place
	 * @return {@code true} if an applicable item entity intersects the target block bounding box
	 */
	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		for(ItemEntity itementity : pServerLevel.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(pAboveBlockPos))) {
			if (isItemEntityApplicable(itementity)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Evaluates whether a specific item entity is valid for NPC retrieval.
	 * <p>
	 * Validates entity lifecycle state, non-empty stack status, pickup delay timers, and strictly 
	 * requires that the item's owner UUID matches the executing entity's UUID.
	 * </p>
	 *
	 * @param item item entity candidate to check
	 * @return {@code true} if the item entity is active, ready for pickup, and owned by this NPC
	 */
	private boolean isItemEntityApplicable(ItemEntity item) {
		if (item.isAlive() && !item.getItem().isEmpty() && !item.hasPickUpDelay() && goalHolder.getEntity().getUUID().equals(item.getOwner())) {
			/*
			ItemStack seeds = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
			boolean matchesSeeds = !seeds.isEmpty() && seeds.sameItem(item.getItem()) && ItemStack.tagMatches(seeds, item.getItem());
			boolean hasSeedSpace = matchesSeeds && seeds.getCount() < seeds.getMaxStackSize();
			if (hasSeedSpace || goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(item.getItem())) {
				return true;
			}*/
			// If inventory is totally full, it's STILL applicable so we can go over and delete it
			return true; 
		}
		return false;
	}
	/**
	 * Completes the task execution step.
	 * <p>
	 * Because Minecraft's standard entity collision mechanisms handle picking up item entities 
	 * as the NPC approaches, no additional block modification or inventory logic is required here.
	 * </p>
	 *
	 * @param pAboveBlockPos target block coordinates of the item
	 * @param pServerLevel   world where the task is running
	 * @return {@code true} immediately to signal execution completion
	 */
	@Override
	public boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		//Nothing to execute, NPC automatically picks up the items when it walks close enough
		return true;
	}
	/**
	 * Prepares the NPC for item collection.
	 * <p>
	 * Stashes the currently held main-hand item and clears the main-hand slot to visually indicate 
	 * readiness for gathering items.
	 * </p>
	 */
	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
	}
	/**
	 * Cleans up item collection state upon task termination.
	 * <p>
	 * Restores the NPC's original main-hand item stack.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
	/**
	 * Determines whether the NPC is close enough to collect the item entity.
	 *
	 * @param farmer    the entity collecting the item
	 * @param targetPos block coordinates of the target item entity
	 * @return {@code true} if the NPC is directly touching or adjacent to the target position
	 */
	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 0);
	}
}
