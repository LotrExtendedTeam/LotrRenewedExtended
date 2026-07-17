package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFarmingAquireTask extends ExtendedFarmingAbstractChestTask {
	/**
	 * Cache Bonemeal since slot can go to empty, whereas seeds can only go down to 1 item
	 * */
	private final static ItemStack BONEMEAL = new ItemStack(Items.BONE_MEAL); 

	public ExtendedFarmingAquireTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if(!seedsStack.isEmpty() && seedsStack.getCount()<=16) {
			return true;
		}
		if(goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().getCount()<=16) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(pAboveBlockPos);
		if(blockEntity instanceof ChestTileEntity) {
			ChestTileEntity chest = (ChestTileEntity) blockEntity;
			BlockState chestState = blockEntity.getBlockState();
			ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();

			// Only look for seeds if the slot isn't empty
			boolean needsSeeds = !seedsStack.isEmpty() && seedsStack.getCount() < seedsStack.getMaxStackSize();
			boolean needsBonemeal = goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().getCount() < BONEMEAL.getMaxStackSize();
			IInventory inventory = ChestBlock.getContainer((ChestBlock)chestState.getBlock(), chestState, pServerLevel, pAboveBlockPos, true);
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack chestStack = inventory.getItem(i);
				if (chestStack.isEmpty()) {
					continue;
				}
				if (needsSeeds && ExtendedItemStackUtils.isSameItemStackItemAndTag(chestStack, seedsStack)) {
					return true;
				}
				if (needsBonemeal && ExtendedItemStackUtils.isSameItemStackItemAndTag(chestStack, BONEMEAL)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(pAboveBlockPos);
		if (!(blockEntity instanceof ChestTileEntity)) {
			return true;
		}
		ChestTileEntity chest = (ChestTileEntity) blockEntity;

		if(!handleChestOpenDone(pServerLevel, chest)) {
			return false;//Keep task ticking so chest can open
		}

		BlockState chestState = blockEntity.getBlockState();
		IInventory inventory = ChestBlock.getContainer((ChestBlock)chestState.getBlock(), chestState, pServerLevel, pAboveBlockPos, true);

		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!seedsStack.isEmpty() && seedsStack.getCount() < seedsStack.getMaxStackSize()) {
			int amountNeeded = seedsStack.getMaxStackSize() - seedsStack.getCount();
			int acquired = withdrawItemFromChest(inventory, seedsStack, amountNeeded);
			if (acquired > 0) {
				seedsStack.grow(acquired);
			}
		}

		ItemStack bonemealStack = goalHolder.farmhandData.getFarmhandInfo().getBonemealItem();
		if (bonemealStack.getCount() < BONEMEAL.getMaxStackSize()) {
			int amountNeeded = BONEMEAL.getMaxStackSize() - bonemealStack.getCount();
			int acquired = withdrawItemFromChest(inventory, BONEMEAL, amountNeeded);
			if (acquired > 0) {
				if (bonemealStack.isEmpty()) {
					goalHolder.farmhandData.getFarmhandInfo().setBonemealItem(new ItemStack(Items.BONE_MEAL, acquired));
				} else {
					bonemealStack.grow(acquired);
				}
			}
		}
		chest.setChanged();
		return true;
	}

	private int withdrawItemFromChest(IInventory inventory, ItemStack targetMatchingItem, int amountNeeded) {
		int totalAcquired = 0;
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack chestStack = inventory.getItem(i);
			if (!chestStack.isEmpty() && ExtendedItemStackUtils.isSameItemStackItemAndTag(chestStack, targetMatchingItem)) {
				int toTake = Math.min(amountNeeded - totalAcquired, chestStack.getCount());
				chestStack.shrink(toTake);
				totalAcquired += toTake;
				if (totalAcquired >= amountNeeded) {
					break;
				}
			}
		}
		return totalAcquired;
	}
}
