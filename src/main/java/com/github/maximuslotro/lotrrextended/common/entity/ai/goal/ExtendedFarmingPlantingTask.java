package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

/**
 * Based on {@link FarmTask}
 * */
public class ExtendedFarmingPlantingTask extends ExtendedFarmingAbstractTask {
	private ItemStack previousHeldItem;

	public ExtendedFarmingPlantingTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		if(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().isEmpty()) {
			return false;
		}
		return goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getCount()>1;
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		BlockState farmland = pServerLevel.getBlockState(pAboveBlockPos.below());
		IPlantable plant = ExtendedItemStackUtils.getPlantFromItem(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem());
		return isRePlaceablePos(pAboveBlockPos, pServerLevel)
			&& plant.getPlantType(pServerLevel, pAboveBlockPos) == PlantType.CROP
			&& farmland.isFertile(pServerLevel, pAboveBlockPos)
			&& farmland.canSustainPlant(pServerLevel, pAboveBlockPos.below(), Direction.UP, plant);
	}

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

	@Override
	public void startTask() {
		super.startTask();
		previousHeldItem = goalHolder.getEntity().getMainHandItem();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, new ItemStack(goalHolder.farmhandData.getFarmhandInfo().getSeedsItem().getItem()));
	}

	@Override
	public void stopTask() {
		super.stopTask();
		goalHolder.getEntity().setItemInHand(Hand.MAIN_HAND, previousHeldItem);
	}
}
