package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

/**
 * Task responsible for applying bone meal fertilizer to growable crops assigned to the NPC.
 * <p>
 * This task validates that the NPC possesses bone meal and targets immature plant blocks matching 
 * the NPC's configured crop type. When executed, it applies the fertilizer, triggers growth particle 
 * and sound effects, swings the entity's main hand, and consumes bone meal items from internal supply.
 * </p>
 * 
 * Based on vanilla brain task:
 * <ul>
 *   <li>{@link BoneMealCropsTask}</li>
 * </ul>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingGoalHolder
 * @see IGrowable
 */
public class ExtendedFarmingBonemealTask extends ExtendedFarmingAbstractTask {
	/**
	 * Remembers the item stack held in the NPC's main hand prior to starting the task,
	 * allowing it to be safely restored when the task finishes or stops.
	 */
	private ItemStack previousHeldItem;
	/**
	 * Constructs a new bone meal fertilization task bound to the given goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingBonemealTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Checks whether the task can currently be assigned based on bone meal supply availability.
	 *
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} if the NPC holds a non-empty bone meal item stack
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		if(!goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().isEmpty()) {
			return goalHolder.farmhandData.getFarmhandInfo().getBonemealItem().getItem().equals(Items.BONE_MEAL);
		}
		return false;
	}
	/**
	 * Validates whether a target block position contains a growing crop that can receive bone meal.
	 * <p>
	 * Checks that the block at the target position is {@link IGrowable}, matches the specific crop type 
	 * configured for this NPC, and is currently in a state capable of receiving bone meal growth ticks.
	 * </p>
	 *
	 * @param pAboveBlockPos block position of the target crop
	 * @param pServerLevel   world where the check takes place
	 * @return {@code true} if the block is an immature, bone-mealable crop matching the NPC's active crop type
	 */
	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		BlockState plantState = pServerLevel.getBlockState(pAboveBlockPos);
		if (plantState.getBlock() instanceof IGrowable) {
			Item plantItem = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem();
			if (plantItem instanceof BlockItem && ((BlockItem)plantItem).getBlock()==plantState.getBlock()) {
				IGrowable igrowable = (IGrowable)plantState.getBlock();
				if (igrowable.isValidBonemealTarget(pServerLevel, pAboveBlockPos, plantState, pServerLevel.isClientSide)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Applies bone meal to the target crop block position.
	 * <p>
	 * Advances plant growth stages via {@link BoneMealItem#growCrop}, plays fertilization visual particles 
	 * and sounds, swings the main hand, and decrements the internal bone meal stack count.
	 * </p>
	 *
	 * @param pAboveBlockPos target block coordinates to fertilize
	 * @param pServerLevel   world where the task is executing
	 * @return {@code true} indicating the fertilization action attempted and completed on this tick
	 */
	@Override
	public boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		ItemStack boneMeal = goalHolder.farmhandData.getFarmhandInfo().getBonemealItem();
		if (!boneMeal.isEmpty() && BoneMealItem.growCrop(boneMeal, pServerLevel, pAboveBlockPos)) {
			goalHolder.getEntity().swing(Hand.MAIN_HAND);
			pServerLevel.levelEvent(2005, pAboveBlockPos, 0);
			boneMeal.shrink(1);
		}
		return true;
	}
	/**
	 * Prepares the NPC for bone meal fertilization interactions.
	 * <p>
	 * Stashes the currently held main-hand item stack and equips a visual bone meal item in the main hand.
	 * </p>
	 */
	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.BONE_MEAL));
	}
	/**
	 * Cleans up fertilization state upon task completion or termination.
	 * <p>
	 * Restores the NPC's original main-hand item stack.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
}
