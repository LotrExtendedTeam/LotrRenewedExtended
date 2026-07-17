package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFarmingCollectTask extends ExtendedFarmingAbstractTask {
	private static ItemStack dummyPickupItem = new ItemStack(Items.BARRIER);
	private ItemStack previousHeldItem;

	public ExtendedFarmingCollectTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		ItemStack seeds = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!seeds.isEmpty() && seeds.getCount() < seeds.getMaxStackSize()) {
			return true;
		}
		if(goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(dummyPickupItem)) {
			return true;
		}
		// Also valid if inventory is full, BUT there are dropped items nearby that are owned
		List<ItemEntity> nearbyOwnedItems = pServerLevel.getEntitiesOfClass(ItemEntity.class, goalHolder.getEntity().getBoundingBox().inflate(8.0D, 3.0D, 8.0D), this::isItemEntityApplicable);
		return !nearbyOwnedItems.isEmpty();
	}

	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		List<ItemEntity> items = pServerLevel.getEntitiesOfClass(ItemEntity.class, goalHolder.getEntity().getBoundingBox().inflate(8.0D, 3.0D, 8.0D));
		for (ItemEntity itemEntity : items) {
			if (isItemEntityApplicable(itemEntity)) {
				taskPosition.set(itemEntity.blockPosition());
				return;
			}
		}
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		for(ItemEntity itementity : pServerLevel.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(pAboveBlockPos))) {
			if (isItemEntityApplicable(itementity)) {
				return true;
			}
		}
		return false;
	}

	private boolean isItemEntityApplicable(ItemEntity item) {
		if (item.isAlive() && !item.getItem().isEmpty() && !item.hasPickUpDelay() && goalHolder.getEntity().getUUID().equals(item.getOwner())) {
			ItemStack seeds = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
			boolean matchesSeeds = !seeds.isEmpty() && seeds.sameItem(item.getItem()) && ItemStack.tagMatches(seeds, item.getItem());
			boolean hasSeedSpace = matchesSeeds && seeds.getCount() < seeds.getMaxStackSize();
			if (hasSeedSpace || goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(item.getItem())) {
				return true;
			}
			// If inventory is totally full, it's STILL applicable so we can go over and delete it
			return true; 
		}
		return false;
	}
	@Override
	public boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		//Nothing to execute, NPC automatically picks up the items when it walks close enough
		return true;
	}

	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
	}

	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}

	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		return targetPos.closerThan(farmer.position(), 1.25);
	}
}
