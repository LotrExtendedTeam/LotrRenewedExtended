package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

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
		return goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(dummyPickupItem);
	}

	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		for(ItemEntity itementity : pServerLevel.getEntitiesOfClass(ItemEntity.class, goalHolder.getEntity().getBoundingBox().inflate(5.0D, 1.0D, 5.0D))) {
			if (isItemEntityApplicable(itementity)) {
				taskPosition.set(itementity.blockPosition());
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
		ItemStack seeds = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!item.removed && !item.getItem().isEmpty() && !item.hasPickUpDelay() && goalHolder.getEntity().getUUID().equals(item.getOwner())
			&& ((!seeds.isEmpty() && seeds.sameItem(item.getItem())  && ItemStack.tagMatches(seeds, item.getItem()) && seeds.getCount() < seeds.getMaxStackSize()) 
				||goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(item.getItem()))) {
			return true;
		}
		return false;
	}
	@Override
	public void executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		//Nothing to execute, NPC automatically picks up the items when it walks close enough
	}

	@Override
	public void startTask() {
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
	}

	@Override
	public void stopTask() {
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
}
