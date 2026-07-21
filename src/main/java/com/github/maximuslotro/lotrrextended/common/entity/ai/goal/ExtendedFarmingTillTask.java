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
	public boolean isValidPosForTask(BlockPos groundLevelPos, ServerWorld pServerLevel) {
		if(isRePlaceablePos(groundLevelPos.above(), pServerLevel)) {
			BlockState groundState = pServerLevel.getBlockState(groundLevelPos);
			if(groundState.getBlock() instanceof FarmlandBlock) {
				return false;
			}
			BlockState modified = groundState.getToolModifiedState(pServerLevel, groundLevelPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.getEntity().getMainHandItem(), ToolType.HOE);
			if(modified!=null && modified.getBlock() instanceof FarmlandBlock) {
				return !pServerLevel.getBlockState(groundLevelPos.below()).is(BlockTags.SAND) && isNearWater(pServerLevel, groundLevelPos);
			}
		}
		return false;
	}

	@Override
	public boolean executeTask(BlockPos groundLevelPos, ServerWorld pServerLevel) {
		goalHolder.getEntity().swing(Hand.MAIN_HAND);
		boolean playTillSound = tryTillBlock(groundLevelPos, pServerLevel);
		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos sidePos = groundLevelPos.relative(dir);
			if(this.isValidPosForTask(sidePos, pServerLevel)) {
				playTillSound |= tryTillBlock(sidePos, pServerLevel);
			}
		}
		if(playTillSound) {
			pServerLevel.playSound(null, groundLevelPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		return true;
	}

	private boolean tryTillBlock(BlockPos groundLevelPos, ServerWorld pServerLevel) {
		if (isRePlaceablePos(groundLevelPos.above(), pServerLevel)) {
			pServerLevel.destroyBlock(groundLevelPos.above(), false);
		};
		BlockState modified = pServerLevel.getBlockState(groundLevelPos).getToolModifiedState(pServerLevel, groundLevelPos, FakePlayerFactory.getMinecraft(pServerLevel), goalHolder.getEntity().getMainHandItem(), ToolType.HOE);
		if (modified != null) {
			pServerLevel.setBlock(groundLevelPos, modified, 11);
			return true;
		}
		return false;
	}
}
