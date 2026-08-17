package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;

import lotr.common.entity.npc.data.ExtendedNpcFarmhandInfoHolder.FarmingWarning;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.StemGrownBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Task responsible for identifying mature crops, harvestable stem fruits, or stacked crop structures, 
 * breaking them, and spawning ownership-tagged item drops for the NPC to collect.
 * <p>
 * This task validates crop growth states (such as maximum age for {@link CropsBlock}, matching 
 * fruit blocks for {@link StemBlock}, or structural height requirements for vertically growing blocks) 
 * against the NPC's assigned crop type. During execution, custom block removal logic drops items 
 * tagged specifically with the NPC's UUID as the owner—ensuring nearby players cannot accidentally 
 * siphon NPC harvest drops before the collection task runs.
 * </p>
 * 
 * Based on vanilla brain task:
 * <ul>
 *   <li>{@link FarmTask}</li>
 * </ul>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingCollectTask
 * @see ExtendedFarmingGoalHolder
 * @see CropsBlock
 * @see StemBlock
 */
public class ExtendedFarmingHarvestTask extends ExtendedFarmingAbstractTask {
	/**
	 * Total duration in ticks required to complete a single harvest action.
	 */
	private static final int TICKTIME = 10;
	/**
	 * Internal counter tracking progress ticks during the active harvesting sequence.
	 */
	private int harvestTick=0;
	/**
	 * Number of hits the inventory has continuously been full.
	 */
	private int fullInventoryHits = 0;
	/**
	 * Threshold in hits before raising a notification
	 */
	private static final int FULL_INVENTORY_THRESHOLD = 200;
	/**
	 * Constructs a new crop harvest task bound to the specified goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingHarvestTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Validates whether the NPC has sufficient inventory capacity to perform harvest operations.
	 *
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} if the NPC holds valid seed item configuration and the output inventory 
	 *         can accept additional harvested items
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		if(seedsStack.isEmpty()) {
			return false;
		}
		if(!goalHolder.farmhandData.getUnitInfo().isNpcActiveDuty()) {
			return true;
		}
		boolean canAddItem = goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(seedsStack);
		if(!canAddItem) {
			if(fullInventoryHits >= FULL_INVENTORY_THRESHOLD) {
				goalHolder.farmhandData.getFarmhandInfo().setWarning(FarmingWarning.INVENTORY_FULL, true);
			}else {
				fullInventoryHits++;
			}
			return false;
		}
		if(fullInventoryHits>0) {
			fullInventoryHits = 0;
			goalHolder.farmhandData.getFarmhandInfo().setWarning(FarmingWarning.INVENTORY_FULL, false);
		}
		return canAddItem;
	}
	/**
	 * Validates whether a mutable crop position is harvestable, mutating the position down to 
	 * the base of the crop column if a multi-block sugar cane structure is detected.
	 * <p>
	 * Delegates validation to {@link #isValidPosForTask(BlockPos, ServerWorld)}. If valid and the target 
	 * block is a {@link SugarCaneBlock}, {@code cropPosition} is iteratively shifted downward until it points 
	 * to the bottom-most sugar cane block in the stack before returning {@code true}.
	 * </p>
	 *
	 * @param cropPosition mutable position to evaluate, modified in-place to the root block if valid sugar cane
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} if the crop at the given position (or its shifted root) is ready for harvest; {@code false} otherwise
	 */
	@Override
	public boolean isValidPosForTaskMutable(BlockPos.Mutable cropPosition, ServerWorld pServerLevel) {
		if (this.isValidPosForTask(cropPosition, pServerLevel)) {
			BlockState plantState = pServerLevel.getBlockState(cropPosition);
			if (plantState.getBlock() instanceof SugarCaneBlock) {
				// Traverse down to set cropPosition to the bottom-most sugar cane block
				while (pServerLevel.getBlockState(cropPosition.below()).getBlock() instanceof SugarCaneBlock) {
					cropPosition.move(0, -1, 0);
				}
			}
			return true;
		}
		return false;
	}
	/**
	 * Determines whether a target block position contains a crop or plant structure ready for harvest.
	 *
	 * @param cropPosition block position of the candidate target
	 * @param pServerLevel world where the check takes place
	 * @return {@code true} if the target block meets the growth or structural criteria for the NPC's configured crop type
	 */
	@Override
	public boolean isValidPosForTask(BlockPos cropPosition, ServerWorld pServerLevel) {
		if(harvestTick>0) {
			return true;
		}
		Item plantItem = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem();
		if(!(plantItem instanceof BlockItem)) {
			return false;
		}
		BlockState plantState = pServerLevel.getBlockState(cropPosition);
		Block plantBlock = plantState.getBlock();
		if(plantBlock instanceof CropsBlock && ((BlockItem)plantItem).getBlock()==plantBlock && ((CropsBlock)plantBlock).isMaxAge(plantState)) {
			return true;
		} else if (plantBlock instanceof StemGrownBlock && ((BlockItem)plantItem).getBlock() instanceof StemBlock) {
			if (((StemBlock)((BlockItem)plantItem).getBlock()).getFruit()==plantBlock) {
				return true;
			}
		} else if(plantBlock instanceof SugarCaneBlock && ((BlockItem) plantItem).getBlock()==plantBlock) {
			BlockState aboveState = pServerLevel.getBlockState(cropPosition.above());
			// Middle of stack
			if (aboveState.getBlock() instanceof SugarCaneBlock && pServerLevel.getBlockState(cropPosition.below()).getBlock() instanceof SugarCaneBlock) {
				return true;
			}
			BlockState aboveTwoState = pServerLevel.getBlockState(cropPosition.above(2));
			// Bottom of stack
			if (aboveState.getBlock() instanceof SugarCaneBlock && aboveTwoState.getBlock() instanceof SugarCaneBlock) {
				return true;
			}
		} else if(plantBlock instanceof SweetBerryBushBlock && ((BlockItem)plantItem).getBlock()==plantBlock) {
			if (plantState.getValue(SweetBerryBushBlock.AGE)>=3) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Executes the block breaking and harvest sequence over the required tick duration.
	 * <p>
	 * On the initial tick, swings the main hand and processes the target block. For standard single-block 
	 * crops, breaks the block directly via {@link #destroyBlock}. For stacked multi-block structures, resolves 
	 * the full height above the base root and breaks blocks in descending order (top to bottom) to ensure 
	 * every harvested piece correctly invokes ownership-tagged item drops. Increments farmhand harvest metrics 
	 * and advances internal tick progress until reaching {@link #TICKTIME}.
	 * </p>
	 *
	 * @param cropPosition block position of the target crop
	 * @param pServerLevel world where execution occurs
	 * @return {@code true} when the harvest tick progress reaches completion duration
	 */
	@Override
	public boolean executeTask(BlockPos cropPosition, ServerWorld pServerLevel) {
		if(harvestTick==0) {
			goalHolder.getEntity().swing(Hand.MAIN_HAND);
			BlockState targetState = pServerLevel.getBlockState(cropPosition);
			if (targetState.getBlock() instanceof SugarCaneBlock) {
				// Get all sugar cane pos starting from layer 2 and upwards
				List<BlockPos> canePositionsToBreak = new ArrayList<>();
				BlockPos checkPos = cropPosition.above();
				while (pServerLevel.getBlockState(checkPos).getBlock() instanceof SugarCaneBlock) {
					canePositionsToBreak.add(checkPos);
					checkPos = checkPos.above();
				}
				// Reverse to break top-to-bottom
				Collections.reverse(canePositionsToBreak);
				for (BlockPos canePos : canePositionsToBreak) {
					destroyBlock(canePos, pServerLevel, goalHolder.getEntity());
				}
				goalHolder.farmhandData.getFarmhandInfo().addCropsHarvested(canePositionsToBreak.size(), 1);
			} else if (targetState.getBlock() instanceof SweetBerryBushBlock) {
				ItemStack popStack;
				if(targetState.getBlock() instanceof ExtendedBerryBushBlock) {
					popStack = new ItemStack(((ExtendedBerryBushBlock)targetState.getBlock()).getBerryItem(), 2 + pServerLevel.random.nextInt(2));
				}else {
					popStack = new ItemStack(Items.SWEET_BERRIES, 2 + pServerLevel.random.nextInt(2));
				}
				popResourceOwning(pServerLevel, cropPosition, popStack, goalHolder.getEntity());
				pServerLevel.playSound((PlayerEntity)null, cropPosition, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + pServerLevel.random.nextFloat() * 0.4F);
				pServerLevel.setBlock(cropPosition, targetState.setValue(ExtendedBerryBushBlock.AGE, Integer.valueOf(1)), 2);
				goalHolder.farmhandData.getFarmhandInfo().addCropsHarvested(1, 1);
			} else {
				// Standard single block harvest
				destroyBlock(cropPosition, pServerLevel, goalHolder.getEntity());
				goalHolder.farmhandData.getFarmhandInfo().addCropsHarvested(1, 1);
			}
		}
		harvestTick++;
		return harvestTick==TICKTIME;
	}
	/**
	 * Removes a crop block from the world and triggers owned resource drops.
	 * <p>
	 * Plays block break effects and replaces the block with ambient fluid state or air, 
	 * delegating drop creation to {@link #dropResourcesOwning}.
	 * </p>
	 *
	 * @param cropPosition position of the block to destroy
	 * @param pServerLevel world where the destruction takes place
	 * @param pEntity      the entity breaking the block
	 * @return {@code true} if the block was successfully removed; {@code false} if air
	 */	
	public boolean destroyBlock(BlockPos cropPosition, ServerWorld pServerLevel, Entity pEntity) {
		BlockState blockstate = pServerLevel.getBlockState(cropPosition);
		if (blockstate.isAir()) {
			return false;
		} else {
			FluidState fluidstate = pServerLevel.getFluidState(cropPosition);
			if (!(blockstate.getBlock() instanceof AbstractFireBlock)) {
				pServerLevel.levelEvent(2001, cropPosition, Block.getId(blockstate));
			}
			TileEntity tileentity = blockstate.hasTileEntity() ? pServerLevel.getBlockEntity(cropPosition) : null;
			//Block.dropResources(blockstate, this, pPos, tileentity, pEntity, ItemStack.EMPTY);
			dropResourcesOwning(blockstate, pServerLevel, cropPosition, tileentity, pEntity, ItemStack.EMPTY);
			return pServerLevel.setBlock(cropPosition, fluidstate.createLegacyBlock(), 3, 512);
		}
	}
	/**
	 * Generates block harvest drops and routes them through ownership tagging.
	 *
	 * @param pState       state of the broken block
	 * @param pLevel       world where drops spawn
	 * @param cropPosition block position where drops originate
	 * @param pBlockEntity optional tile entity associated with the broken block
	 * @param pEntity      entity credited with breaking the block
	 * @param pTool        tool item stack used during breaking
	 */
	public static void dropResourcesOwning(BlockState pState, ServerWorld pLevel, BlockPos cropPosition, @Nullable TileEntity pBlockEntity, Entity pEntity, ItemStack pTool) {
		Block.getDrops(pState, (ServerWorld)pLevel, cropPosition, pBlockEntity, pEntity, pTool).forEach((itemStack) -> {
			//Block.popResource(pLevel, pPos, itemStack, pEntity);
			popResourceOwning(pLevel, cropPosition, itemStack, pEntity);
		});
		pState.spawnAfterBreak((ServerWorld)pLevel, cropPosition, pTool);
	}
	/**
	 * Spawns an {@link ItemEntity} in the world configured with the breaking entity's UUID as owner.
	 * <p>
	 * Verifies world side, item stack presence, and {@link GameRules#RULE_DOBLOCKDROPS} prior to 
	 * spawning the physical entity into the world.
	 * </p>
	 *
	 * @param pLevel       world where the item entity is added
	 * @param cropPosition block coordinates used as the base spawn origin
	 * @param pStack       item stack to assign to the item entity
	 * @param pEntity      entity assigned as the drop owner
	 */
	public static void popResourceOwning(World pLevel, BlockPos cropPosition, ItemStack pStack, Entity pEntity) {
		if (!pLevel.isClientSide && !pStack.isEmpty() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.restoringBlockSnapshots) {
			float f = 0.5F;
			double d0 = (double)(pLevel.random.nextFloat() * 0.5F) + 0.25D;
			double d1 = (double)(pLevel.random.nextFloat() * 0.5F) + 0.25D;
			double d2 = (double)(pLevel.random.nextFloat() * 0.5F) + 0.25D;
			ItemEntity itementity = new ItemEntity(pLevel, cropPosition.getX() + d0, cropPosition.getY() + d1, cropPosition.getZ() + d2, pStack);
			itementity.setDefaultPickUpDelay();
			itementity.setOwner(pEntity.getUUID());// Add entity as owner
			pLevel.addFreshEntity(itementity);
		}
	}
	/**
	 * Evaluates interaction distance limits for crop harvesting.
	 *
	 * @param farmer    the entity performing the harvest
	 * @param targetPos position of the target crop block
	 * @return {@code true} if the entity is within 1.5 blocks of the target
	 */
	@Override
	public boolean closeEnoughToExecuteTask(Entity farmer, BlockPos targetPos) {
		Item plantItem = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem();
		if(plantItem==Items.SUGAR_CANE) {
			targetPos.closerThan(farmer.position(), 2);
		}
		return targetPos.closerThan(farmer.position(), 1.5);
	}
	/**
	 * Resets harvest execution timers when task tracking begins.
	 */
	@Override
	public void startTask() {
		super.startTask();
		harvestTick=0;
	}
	/**
	 * Resets harvest execution state upon task cancellation or completion.
	 * <p>
	 * Zeroing {@code harvestTick} prevents positional validation bypass in {@link #isValidPosForTask}.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		harvestTick=0; //Important, fixes isValidPosForTask being bypassed, allowing any block to be broken
	}
}
