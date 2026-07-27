package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

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
/**
 * Farming task responsible for searching nearby container entities and restocking the NPC's supplies.
 * <p>
 * This task activates when the NPC's required farming resources (such as seeds or bonemeal) fall below 
 * restocking thresholds. It locates containers holding matching items, handles container interactions, 
 * extracts the needed quantities to top off internal inventory stacks, and marks the container as dirty.
 * </p>
 * 
 * @see ExtendedFarmingAbstractChestTask
 * @see ExtendedFarmingGoalHolder
 * @see ExtendedItemStackUtils
 */
public class ExtendedFarmingAquireTask extends ExtendedFarmingAbstractChestTask {
	/**
	 * Cached stack reference used for matching and measuring bone meal items 
	 * during inventory checks and withdrawal logic.
	 */
	private final static ItemStack BONEMEAL = new ItemStack(Items.BONE_MEAL); 
	/**
	 * Constructs a new resource acquisition task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingAquireTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Checks whether the NPC is in need of supply restocking.
	 * <p>
	 * Evaluates current internal supply quantities (e.g., active seed type or bonemeal reserve) 
	 * against low-resource thresholds to determine if a chest search should be triggered.
	 * </p>
	 *
	 * @param pServerLevel world where the state evaluation occurs
	 * @return {@code true} if any tracked supply stack is depleted or low enough to warrant restocking
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if(!seedsStack.isEmpty() && seedsStack.getCount()<=16) {
			return true;
		}
		if(ExtendedFarmingBonemealTask.doesCropSupportBonemeal(seedsStack) && goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().getCount()<=16) {
			return true;
		}
		return false;
	}
	/**
	 * Validates whether a given chest position contains needed supplies.
	 * <p>
	 * Wraps double-chest access checks and iterates over container contents, matching item IDs and NBT tags 
	 * against currently required resources (seeds and/or bonemeal).
	 * </p>
	 *
	 * @param chestBlockPos target block coordinates of the candidate container
	 * @param pServerLevel  world where the container check takes place
	 * @return {@code true} if the target container holds at least one matching item required for restocking
	 */
	@Override
	public boolean isValidPosForTask(BlockPos chestBlockPos, ServerWorld pServerLevel) {
		TileEntity blockEntity = pServerLevel.getBlockEntity(chestBlockPos);
		if(blockEntity instanceof ChestTileEntity) {
			ChestTileEntity chest = (ChestTileEntity) blockEntity;
			BlockState chestState = blockEntity.getBlockState();
			ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();

			// Only look for seeds if the slot isn't empty
			boolean needsSeeds = !seedsStack.isEmpty() && seedsStack.getCount() < seedsStack.getMaxStackSize();
			boolean needsBonemeal = ExtendedFarmingBonemealTask.doesCropSupportBonemeal(seedsStack) && goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().getCount() < BONEMEAL.getMaxStackSize();
			if (!needsSeeds && !needsBonemeal) {
	            return false; // Early exit if neither supply is needed
	        }
			IInventory inventory = ChestBlock.getContainer((ChestBlock)chestState.getBlock(), chestState, pServerLevel, chestBlockPos, true);
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
	/**
	 * Executes the resource withdrawal process at the target container.
	 * <p>
	 * Defers execution while the chest opening animation completes. Once open, calculates required deficit 
	 * amounts, transfers matching items into the entity's inventory data structures, and marks the block entity as changed.
	 * </p>
	 *
	 * @param chestBlockPos coordinates of the container being accessed
	 * @param pServerLevel  world where the task is executing
	 * @return {@code true} when the extraction operation completes or fails early (releasing task execution); 
	 *         {@code false} while awaiting chest opening animation ticks
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
		IInventory inventory = ChestBlock.getContainer((ChestBlock)chestState.getBlock(), chestState, pServerLevel, chestBlockPos, true);

		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if (!seedsStack.isEmpty() && seedsStack.getCount() < seedsStack.getMaxStackSize()) {
			int amountNeeded = seedsStack.getMaxStackSize() - seedsStack.getCount();
			int acquired = withdrawItemFromChest(inventory, seedsStack, amountNeeded);
			if (acquired > 0) {
				seedsStack.grow(acquired);
			}
		}

		ItemStack bonemealStack = goalHolder.farmhandData.getFarmhandInfo().getBonemealItem();
		if (ExtendedFarmingBonemealTask.doesCropSupportBonemeal(seedsStack) && bonemealStack.getCount() < BONEMEAL.getMaxStackSize()) {
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
	/**
	 * Iterates container slots to extract a target item up to a desired quantity.
	 * <p>
	 * Shrinks source item stacks in place as items are withdrawn and stops once the needed count is satisfied.
	 * </p>
	 *
	 * @param inventory          container inventory accessor to withdraw from
	 * @param targetMatchingItem archetype stack defining item identity and tag matching rules
	 * @param amountNeeded       maximum total count of items to pull from the container
	 * @return actual quantity of items successfully extracted from the inventory
	 */
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
