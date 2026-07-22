package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Task responsible for identifying mature crops or harvestable stem fruits, breaking them, 
 * and spawning ownership-tagged item drops for the NPC to collect.
 * <p>
 * This task validates crop growth states (such as maximum age for {@link CropsBlock} or matching 
 * fruit blocks for {@link StemBlock}) against the NPC's assigned crop type. During execution, custom 
 * block removal logic drops items tagged specifically with the NPC's UUID as the owner—ensuring nearby 
 * players cannot accidentally siphon NPC harvest drops before the collection task runs.
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
	private int harvestTick;
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
	 */	@Override
	 public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		 if(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().isEmpty()) {
			 return false;
		 }
		 ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		 return goalHolder.farmhandData.getFarmhandInfo().getFarmhandOutputInventory().canAddItem(seedsStack);
	 }
	 /**
	  * Determines whether a target block position contains a mature crop or fruit ready for harvest.
	  * <p>
	  * Checks if standard crops ({@link CropsBlock}) have reached maximum growth age or if stem crops 
	  * ({@link StemBlock}) have produced matching adjacent fruit blocks. Returns {@code true} unconditionally 
	  * while an active harvest tick sequence is mid-execution.
	  * </p>
	  *
	  * @param cropPosition block position of the candidate target
	  * @param pServerLevel world where the check takes place
	  * @return {@code true} if the target block is a fully grown, matching crop or fruit block
	  */
	 @Override
	 public boolean isValidPosForTask(BlockPos cropPosition, ServerWorld pServerLevel) {
		 if(harvestTick>0) {
			 return true;
		 }
		 Item plantItem = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem();
		 BlockState plantState = pServerLevel.getBlockState(cropPosition);
		 Block plantBlock = plantState.getBlock();
		 if(plantBlock instanceof CropsBlock && ((CropsBlock)plantBlock).isMaxAge(plantState)) {
			 if (plantItem instanceof BlockItem && ((BlockItem)plantItem).getBlock()==plantBlock) {
				 return true;
			 }
		 }
		 if (plantItem instanceof BlockItem && ((BlockItem)plantItem).getBlock() instanceof StemBlock) {
			 if (((StemBlock)((BlockItem)plantItem).getBlock()).getFruit()==plantBlock) {
				 return true;
			 }
		 }
		 return false;
	 }
	 /**
	  * Executes the block breaking and harvest sequence over the required tick duration.
	  * <p>
	  * On the initial tick, swings the main hand, destroys the crop block via {@link #destroyBlock}, 
	  * spawns owned item drops, and increments farmhand harvest metrics. Advances internal tick progress 
	  * until reaching {@link #TICKTIME}.
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
			 //pServerLevel.destroyBlock(pAboveBlockPos, true, goalHolder.getEntity());
			 destroyBlock(cropPosition, pServerLevel, goalHolder.getEntity());
			 goalHolder.farmhandData.getFarmhandInfo().addCropsHarvested(1, 1);
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
	 public static void dropResourcesOwning(BlockState pState, World pLevel, BlockPos cropPosition, @Nullable TileEntity pBlockEntity, Entity pEntity, ItemStack pTool) {
		 if (pLevel instanceof ServerWorld) {
			 Block.getDrops(pState, (ServerWorld)pLevel, cropPosition, pBlockEntity, pEntity, pTool).forEach((itemStack) -> {
				 //Block.popResource(pLevel, pPos, itemStack, pEntity);
				 popResourceOwning(pLevel, cropPosition, itemStack, pEntity);
			 });
			 pState.spawnAfterBreak((ServerWorld)pLevel, cropPosition, pTool);
		 }
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
