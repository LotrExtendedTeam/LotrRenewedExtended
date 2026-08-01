package com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

/**
 * Task responsible for planting seed items onto fertile soil blocks.
 * <p>
 * This task validates that the NPC has a sufficient reserve of seeds and checks target positions for 
 * soil fertility, plant type compatibility ({@link PlantType#CROP}), and block replaceability. Upon 
 * execution, it places the crop block, plays planting sound and arm-swing effects, and decrements 
 * the NPC's seed supply.
 * </p>
 * 
 * Based on vanilla brain task:
 * <ul>
 *   <li>{@link FarmTask}</li>
 * </ul>
 * 
 * @see ExtendedFarmingAbstractTask
 * @see ExtendedFarmingGoalHolder
 * @see IPlantable
 */
public class ExtendedFarmingPlantingTask extends ExtendedFarmingAbstractTask {
	/**
	 * Remembers the item stack held in the NPC's main hand prior to starting the task,
	 * allowing it to be safely restored when the task terminates or completes.
	 */
	private ItemStack previousHeldItem;
	/**
	 * Constructs a new seed planting task bound to the specified goal holder.
	 *
	 * @param goalHolder the parent goal holder managing this task instance
	 */
	public ExtendedFarmingPlantingTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}
	/**
	 * Checks whether the task can currently be assigned to the NPC based on seed inventory levels.
	 * <p>
	 * Requires the seed stack count to be greater than 1 to ensure at least one seed remains 
	 * as a reference or reserve stack.
	 * </p>
	 *
	 * @param pServerLevel world where the validation check takes place
	 * @return {@code true} if the NPC holds more than a single seed item stack
	 */
	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		if(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().isEmpty()) {
			return false;
		}
		return goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getCount()>1;
	}
	/**
	 * Validates whether a given block position above soil is suitable for planting the active seed item.
	 *
	 * @param cropPosition  target block coordinates above the soil
	 * @param pServerLevel  world where the check takes place
	 * @return {@code true} if the target position is replaceable and the block below can sustain the plant
	 */
	@Override
	public boolean isValidPosForTask(BlockPos cropPosition, ServerWorld pServerLevel) {
		BlockPos groundPos = cropPosition.below();
		BlockState farmland = pServerLevel.getBlockState(groundPos);
		if (farmland.is(Blocks.SUGAR_CANE)) {
			return false;
		}
		IPlantable plant = ExtendedItemStackUtils.getPlantFromItem(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem());
		return isRePlaceablePos(cropPosition, pServerLevel)
			&& isSupportedPlantType(plant, pServerLevel, cropPosition)
			&& (plant.getPlantType(pServerLevel, cropPosition) == PlantType.CROP ? farmland.isFertile(pServerLevel, cropPosition) : true)
			&& farmland.canSustainPlant(pServerLevel, groundPos, Direction.UP, plant)
			&& !pServerLevel.getBlockState(groundPos.below()).is(BlockTags.SAND);
	}
	/**
	 * Places the crop block at the target position.
	 * <p>
	 * Swings the entity's main hand, places the plant state via {@link ServerWorld#setBlock}, plays 
	 * {@link SoundEvents#CROP_PLANTED}, and shrinks the active seed stack count by 1.
	 * </p>
	 *
	 * @param pAboveBlockPos block position where the crop is planted
	 * @param pServerLevel   world where execution takes place
	 * @return {@code true} indicating completion of the planting action
	 */
	@Override
	public boolean executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		ItemStack seedsStack = goalHolder.farmhandData.getFarmhandInfo().getSeedsItem();
		IPlantable plant = ExtendedItemStackUtils.getPlantFromItem(seedsStack);
		if (plant!=null) {
			goalHolder.getEntity().swing(Hand.MAIN_HAND);
			BlockState plantState = plant.getPlant(pServerLevel, pAboveBlockPos);
			pServerLevel.setBlock(pAboveBlockPos, plantState, 3);
			pServerLevel.playSound(null, pAboveBlockPos, SoundEvents.CROP_PLANTED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			seedsStack.shrink(1);
		}
		return true;
	}
	/**
	 * Prepares the NPC for seed planting.
	 * <p>
	 * Stashes the currently held main-hand item stack and equips a visual seed stack matching 
	 * the NPC's assigned plant type in the main hand.
	 * </p>
	 */
	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem()));
	}
	/**
	 * Cleans up planting task state upon termination or completion.
	 * <p>
	 * Restores the NPC's original main-hand item stack.
	 * </p>
	 */
	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
	/**
	 * Evaluates whether a plant instance is supported by this task.
	 *
	 * TODO evaluate if there is a better solution than accepting nullable world and positions
	 * 
	 * @param pPlant the plant instance to evaluate
	 * @param level the level to evaluate at
	 * @param pos  the position to evaluate at
	 * @return {@code true} if the plant is a supported crop type; {@code false} otherwise
	 */
	public static boolean isSupportedPlantType(@Nullable IPlantable pPlant, @Nullable World level, @Nullable BlockPos pos) {
		if(pPlant!=null) {
			if (pPlant instanceof SugarCaneBlock) {
				return true;
			}
			if (pPlant instanceof SweetBerryBushBlock) {
				return true;
			}
			try {
				//TODO is there a better way to handle this other than null passing?
				return pPlant.getPlantType(level, pos) == PlantType.CROP;
			} catch (NullPointerException e) {}
		}
		return false;
	}
}
