package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.potion.Effect;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class ExtendedFangornStoneRootBlock extends ExtendedSixDirectionPlantBlock {
	protected static final VoxelShape CEILING_AABB = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	protected static final VoxelShape FLOOR_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 1.0D, 8.0D, 15.0D, 15.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 8.0D);
	protected static final VoxelShape WEST_AABB = Block.box(8.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 1.0D, 1.0D, 8.0D, 15.0D, 15.0D);

	public ExtendedFangornStoneRootBlock(Effect pSuspiciousStewEffect, int pEffectDuration, Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
	}

	@Override
	protected boolean mayPlaceOn(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return pState.is(ExtendedTags.Blocks.BASE_STONE_MIDDLE_EARTH);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		return super.canSurvive(pState, pLevel, pPos) && pLevel.getRawBrightness(pPos, 0) < 13;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, FACE);
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		Direction direction = pState.getValue(FACING);
		switch((AttachFace)pState.getValue(FACE)) {
			case FLOOR:
				return FLOOR_AABB;
			case WALL:
				switch(direction) {
					case EAST:
						return EAST_AABB;
					case WEST:
						return WEST_AABB;
					case SOUTH:
						return SOUTH_AABB;
					case NORTH:
					default:
						return NORTH_AABB;
				}
			case CEILING:
			default:
				return CEILING_AABB;
		}
	}
}
