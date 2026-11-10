package com.github.maximuslotro.lotrrextended.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class ExtendedDirectionalFaceBlock extends ExtendedDirectionalBlock {
	public ExtendedDirectionalFaceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		Direction direction = getConnectedDirection(pState).getOpposite();
		BlockPos blockpos = pPos.relative(direction);
		return pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, direction.getOpposite());
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		for(Direction direction : pContext.getNearestLookingDirections()) {
			BlockState blockstate = this.defaultBlockState().setValue(FACING, direction.getOpposite());
			if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
				return blockstate;
			}
		}
		return null;
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	protected static Direction getConnectedDirection(BlockState pState) {
		return pState.getValue(FACING);
	}
}