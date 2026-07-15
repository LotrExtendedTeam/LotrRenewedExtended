package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayerFactory;

public class ExtendedFarmingTillTask extends ExtendedFarmingAbstractTask{

	public ExtendedFarmingTillTask(ExtendedFarmingGoalHolder goalHolder) {
		super(goalHolder);
	}

	@Override
	public boolean isTaskValidForNPC(ServerWorld pServerLevel) {
		return true;
	}

	@Override
	public boolean isValidPosForTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		if(isRePlaceablePos(pAboveBlockPos, pServerLevel) && pServerLevel.getFluidState(pAboveBlockPos).isEmpty()) {
			BlockPos groundPos = pAboveBlockPos.below();
			BlockState groundState = pServerLevel.getBlockState(groundPos);
			if(groundState.getBlock() instanceof FarmlandBlock) {
				return false;
			}
			BlockState modified = groundState.getToolModifiedState(pServerLevel, groundPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.getEntity().getMainHandItem(), ToolType.HOE);
			if(modified!=null && modified.getBlock() instanceof FarmlandBlock) {
				return !pServerLevel.getBlockState(groundPos.below()).is(BlockTags.SAND) && isNearWater(pServerLevel, groundPos);
			}
		}
		return false;
	}

	@Override
	public void executeTask(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		goalHolder.getEntity().swing(Hand.MAIN_HAND);
		boolean playTillSound = tryTillBlock(pAboveBlockPos, pServerLevel);
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos sidePos = pAboveBlockPos.relative(dir);
			if(this.isValidPosForTask(sidePos, pServerLevel)) {
				playTillSound |= tryTillBlock(sidePos, pServerLevel);
			}
		}
		if(playTillSound) {
			pServerLevel.playSound(null, pAboveBlockPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

	private boolean tryTillBlock(BlockPos pAboveBlockPos, ServerWorld pServerLevel) {
		if (isRePlaceablePos(pAboveBlockPos, pServerLevel)) {
			pServerLevel.destroyBlock(pAboveBlockPos, false);
		}
		BlockPos groundPos = pAboveBlockPos.below();
		BlockState modified = pServerLevel.getBlockState(groundPos).getToolModifiedState(pServerLevel, groundPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.getEntity().getMainHandItem(), ToolType.HOE);
		if (modified != null) {
			pServerLevel.setBlock(groundPos, modified, 11);
			return true;
		}
		return false;
	}
}
