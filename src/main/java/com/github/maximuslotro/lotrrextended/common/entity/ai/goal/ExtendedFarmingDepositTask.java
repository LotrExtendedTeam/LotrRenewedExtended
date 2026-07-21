package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFarmingDepositTask extends ExtendedFarmingAbstractChestTask {

	public ExtendedFarmingDepositTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		Inventory outputInv = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		int totalSlots = outputInv.getContainerSize();
		if (totalSlots <= 0) {
			return false;
		}
		int occupiedSlots = 0;
		int eligibleSlotsCount = 0; // Total slots ignoring non-edible seeds
		boolean hasNearingFullStack = false;
		double totalFillRatio = 0.0;

		for (int i = 0; i < totalSlots; i++) {
			ItemStack stack = outputInv.getItem(i);
			// Skip non-edible seeds; they stay in the inventory for the composting task
			if (isNonEdibleSeed(stack)) {
				continue;
			}
			eligibleSlotsCount++;
			if (!stack.isEmpty()) {
				occupiedSlots++;
				double slotFillPercent = (double) stack.getCount() / stack.getMaxStackSize();
				totalFillRatio += slotFillPercent;
				// Check if any single stack is at least 50% full
				if (slotFillPercent >= 0.50) {
					hasNearingFullStack = true;
				}
			}
		}
		// If the entire inventory is filtered out or empty of real crops, don't deposit
		if (eligibleSlotsCount == 0) {
			return false;
		}
		// For small starter inventories (2->3 slots):
		// Only deposit if all slots are occupied AND at least one slot has a decent stack size.
		// This prevents returning to the chest with just 1 wheat and 1 seed.
		if (totalSlots <= 3) {
			return (occupiedSlots >= eligibleSlotsCount) && hasNearingFullStack;
		}
		// For larger inventories:
		// Trigger a deposit if 75% of slots are occupied OR the overall volume is 70% full.
		double slotOccupancyRate = (double) occupiedSlots / eligibleSlotsCount;
		double averageFillLevel = totalFillRatio / eligibleSlotsCount;
		return slotOccupancyRate >= 0.75 || averageFillLevel >= 0.70;
	}

	@Override
	public boolean isValidPosForTask(BlockPos chestBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(chestBlockPos);
		if(blockEntity instanceof ChestTileEntity) {
			BlockState chestState = blockEntity.getBlockState();
			Inventory npcInv = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
			IInventory inventory = ChestBlock.getContainer((ChestBlock)chestState.getBlock(), chestState, pServerLevel, chestBlockPos, true);
			for(int i = 0; i < npcInv.getContainerSize(); i++) {
				ItemStack stack = npcInv.getItem(i);
				// Skip non-edible seeds
				if (stack.isEmpty() || isNonEdibleSeed(stack)) {
					continue;
				}
				for(int j = 0; j < inventory.getContainerSize(); j++) {
					ItemStack itemstack = inventory.getItem(j);
					if (itemstack.isEmpty() || (ExtendedItemStackUtils.isSameItemStackItemAndTag(itemstack, stack) && itemstack.getCount() < itemstack.getMaxStackSize())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean executeTask(BlockPos chestBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(chestBlockPos);
		if (!(blockEntity instanceof ChestTileEntity)) {
			return true;
		}
		ChestTileEntity chest = (ChestTileEntity) blockEntity;

		if(!handleChestOpenDone(pServerLevel, chest)) {
			return false;//Keep task ticking so chest can open
		}

		BlockState chestState = blockEntity.getBlockState();
		Inventory farmhandOutputInv = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		IInventory inventory = ChestBlock.getContainer((ChestBlock)chestState.getBlock(), chestState, pServerLevel, chestBlockPos, true);

		for(int i = 0; i<farmhandOutputInv.getContainerSize(); i++) {
			ItemStack outputStack = farmhandOutputInv.getItem(i);
			// Skip non-edible seeds entirely during deposit cycles
			if (outputStack.isEmpty() || isNonEdibleSeed(outputStack)) {
				continue;
			}
			// Try placing in same item slot fist
			for (int j = 0; j < inventory.getContainerSize(); j++) {
				ItemStack chestStack = inventory.getItem(j);
				if (!chestStack.isEmpty() && ExtendedItemStackUtils.isSameItemStackItemAndTag(chestStack, outputStack)) {
					int spaceLeft = chestStack.getMaxStackSize() - chestStack.getCount();
					if (spaceLeft > 0) {
						int toTransfer = Math.min(outputStack.getCount(), spaceLeft);
						chestStack.grow(toTransfer);
						outputStack.shrink(toTransfer);
						if (outputStack.isEmpty()) {
							break;
						}
					}
				}
			}
			// Then place in empty slot
			if (!outputStack.isEmpty()) {
				for (int j = 0; j < inventory.getContainerSize(); j++) {
					ItemStack chestStack = inventory.getItem(j);
					if (chestStack.isEmpty()) {
						inventory.setItem(j, outputStack.copy());
						outputStack.setCount(0);
						break;
					}
				}
			}
			// Update farmhand inventory
			farmhandOutputInv.setItem(i, outputStack);
		}
		chest.setChanged();
		return true;
	}
}
