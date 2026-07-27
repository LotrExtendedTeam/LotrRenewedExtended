package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

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
/**
 * Task responsible for unloading harvested crops and materials from the NPC's internal output 
 * inventory into a nearby designated storage chest.
 * <p>
 * This task evaluates inventory fullness against adaptive capacity thresholds—preventing premature 
 * trips for tiny quantities while ensuring small starter inventories deposit reliably. During transfer, 
 * non-edible seeds are automatically filtered out and retained in the NPC's inventory for composting, 
 * while eligible items are merged into existing container stacks before occupying open slots.
 * </p>
 * 
 * @see ExtendedFarmingAbstractChestTask
 * @see ExtendedFarmingGoalHolder
 * @see ChestTileEntity
 */
public class ExtendedFarmingDepositTask extends ExtendedFarmingAbstractChestTask {
	/**
	 * Constructs a new deposit task bound to the specified goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingDepositTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Determines whether the NPC's output inventory meets the threshold criteria to perform a deposit trip.
	 * <p>
	 * Ignores non-edible seeds destined for composting. Applies an adaptive threshold model:
	 * <ul>
	 *   <li><b>Small Inventories (&le; 3 slots):</b> Requires all eligible slots to be occupied with at least 
	 *       one stack reaching &ge; 50% capacity.</li>
	 *   <li><b>Large Inventories (&gt; 3 slots):</b> Requires either &ge; 75% slot occupancy or an overall 
	 *       volume capacity fill of &ge; 70%.</li>
	 * </ul>
	 * </p>
	 *
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} if eligible items meet or exceed the inventory fullness criteria
	 */
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
			// Skip non-depositable seeds; they stay in the inventory for the composting task
			if(!shouldDepositItem(stack)) {
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
	/**
	 * Validates whether the target chest container has available capacity to accept at least 
	 * one transferable item stack from the NPC.
	 * <p>
	 * Verifies that the block entity is a valid chest container and scans for existing matching 
	 * stacks with space or unreserved empty slots. Non-edible seeds are ignored during the check.
	 * </p>
	 *
	 * @param chestBlockPos target coordinates of the storage chest
	 * @param pServerLevel  world where the validation check takes place
	 * @return {@code true} if the chest exists and can accept at least one deposit-eligible item
	 */
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
				if (stack.isEmpty() || !shouldDepositItem(stack)) {
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
	/**
	 * Executes the deposit transfer into the target chest container.
	 * <p>
	 * Manages lid animation sequencing via {@link #handleChestOpenDone}. Once open, items are transferred 
	 * using a two-pass strategy: first filling existing matching item stacks, then utilizing empty slots. 
	 * Updates the container state via {@link ChestTileEntity#setChanged} upon completion.
	 * </p>
	 *
	 * @param chestBlockPos target block position of the chest container
	 * @param pServerLevel  world where the transfer execution occurs
	 * @return {@code false} while awaiting chest opening animation ticks; {@code true} when transfer completes
	 */
	@Override
	public boolean executeTask(BlockPos chestBlockPos, ServerWorld pServerLevel) {
		super.executeTaskSuper(chestBlockPos, pServerLevel);
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
			if (outputStack.isEmpty() || !shouldDepositItem(outputStack)) {
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
