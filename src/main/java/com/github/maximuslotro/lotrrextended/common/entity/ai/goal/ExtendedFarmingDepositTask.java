package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFarmingDepositTask extends ExtendedFarmingAbstractTask {

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
		boolean hasNearingFullStack = false;
		double totalFillRatio = 0.0;

		for (int i = 0; i < totalSlots; i++) {
			ItemStack stack = outputInv.getItem(i);
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
		// For small starter inventories (2->3 slots):
		// Only deposit if all slots are occupied AND at least one slot has a decent stack size.
		// This prevents returning to the chest with just 1 wheat and 1 seed.
		if (totalSlots <= 3) {
			return (occupiedSlots >= totalSlots) && hasNearingFullStack;
		}
		// For larger inventories:
		// Trigger a deposit if 75% of slots are occupied OR the overall volume is 70% full.
		double slotOccupancyRate = (double) occupiedSlots / totalSlots;
		double averageFillLevel = totalFillRatio / totalSlots;
		return slotOccupancyRate >= 0.75 || averageFillLevel >= 0.70;
	}

	@Override
	public void setRandomBlockByTask(BlockPos.Mutable taskPosition, ServerWorld pServerLevel) {
		BlockPos farmhandPos = goalHolder.getEntity().blockPosition();
		int farmhandChunkX = farmhandPos.getX() >> 4;
		int farmhandChunkZ = farmhandPos.getZ() >> 4;
		List<BlockPos> validChests = new ArrayList<>();
		for (TileEntity tileEntity : pServerLevel.blockEntityList) {
			if (tileEntity != null && !tileEntity.isRemoved() && tileEntity.hasLevel()) {
				BlockPos pos = tileEntity.getBlockPos();
				int tileChunkX = pos.getX() >> 4;
				int tileChunkZ = pos.getZ() >> 4;
				if (Math.abs(tileChunkX - farmhandChunkX) <= 1 && Math.abs(tileChunkZ - farmhandChunkZ) <= 1) {
					if (pServerLevel.getChunkSource().isTickingChunk(pos) && pServerLevel.getWorldBorder().isWithinBounds(pos)) {
						if (tileEntity instanceof ChestTileEntity) {
							if (isValidPosForTask(pos, pServerLevel)) {
								validChests.add(pos.immutable());
							}
						}
					}
				}
			}
		}
		if (!validChests.isEmpty()) {
			Collections.shuffle(validChests);
			taskPosition.set(validChests.get(0));
		}
	}

	//Copied and modified from {@link Inventory#canAddItem}
	public boolean canAddItem(ChestTileEntity chest, ItemStack pStack) {
		if (pStack.isEmpty()) {
			return false;
		}
		for(int i = 0; i < chest.getContainerSize(); i++) {
			ItemStack itemstack = chest.getItem(i);
			if (itemstack.isEmpty() || (ExtendedItemStackUtils.isSameItemStackItemAndTag(itemstack, pStack) && itemstack.getCount() < itemstack.getMaxStackSize())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(pAboveBlockPos);
		if(blockEntity instanceof ChestTileEntity) {
			ChestTileEntity chest = (ChestTileEntity) blockEntity;
			Inventory npcInv = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
			for(int i = 0; i < npcInv.getContainerSize(); i++) {
				ItemStack stack = npcInv.getItem(i);
				if(canAddItem(chest, stack)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(pAboveBlockPos);
		if (!(blockEntity instanceof ChestTileEntity)) {
			return;
		}
		ChestTileEntity chest = (ChestTileEntity) blockEntity;
		Inventory farmhandOutputInv = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory();
		for(int i = 0; i<farmhandOutputInv.getContainerSize(); i++) {
			ItemStack outputStack = farmhandOutputInv.getItem(i);
			if (outputStack.isEmpty()) {
				continue;
			}
			// Try placing in same item slot fist
			for (int j = 0; j < chest.getContainerSize(); j++) {
				ItemStack chestStack = chest.getItem(j);
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
				for (int j = 0; j < chest.getContainerSize(); j++) {
					ItemStack chestStack = chest.getItem(j);
					if (chestStack.isEmpty()) {
						chest.setItem(j, outputStack.copy());
						outputStack.setCount(0);
						break;
					}
				}
			}
			// Update farmhand inventory
			farmhandOutputInv.setItem(i, outputStack);
		}
		chest.setChanged();
	}
}
