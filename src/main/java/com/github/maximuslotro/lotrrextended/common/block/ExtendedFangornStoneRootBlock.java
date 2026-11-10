package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class ExtendedFangornStoneRootBlock extends ExtendedSixDirectionPlantBlock implements ExtendedIEffectItem {
	protected static final VoxelShape CEILING_AABB = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	protected static final VoxelShape FLOOR_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 1.0D, 8.0D, 15.0D, 15.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 8.0D);
	protected static final VoxelShape WEST_AABB = Block.box(8.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 1.0D, 1.0D, 8.0D, 15.0D, 15.0D);
	private final Effect suspiciousStewEffect;
	private final int effectDuration;

	public ExtendedFangornStoneRootBlock(Effect pSuspiciousStewEffect, int pEffectDuration, Properties pProperties) {
		super(pProperties);
		this.suspiciousStewEffect = pSuspiciousStewEffect;
		this.effectDuration = pEffectDuration * 20;
	}

	@Override
	protected boolean mayPlaceOn(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return pState.is(ExtendedTags.Blocks.STONE_ROOT_GROWABLE);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
	    if (pLevel instanceof net.minecraft.world.gen.WorldGenRegion) {
	        return super.canSurvive(pState, pLevel, pPos);
	    }
	    return super.canSurvive(pState, pLevel, pPos) && pLevel.getRawBrightness(pPos, 0) < 13;
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		Direction direction = pState.getValue(FACING);
		switch(pState.getValue(FACING)) {
		case EAST:
			return EAST_AABB;
		case WEST:
			return WEST_AABB;
		case SOUTH:
			return SOUTH_AABB;
		case NORTH:
			return NORTH_AABB;
		case UP:
			return FLOOR_AABB;
		case DOWN:
		default:
			return CEILING_AABB;
		}
	}

	@Override
	public Effect getSuspiciousStewEffect() {
		return suspiciousStewEffect;
	}

	@Override
	public int getEffectDuration() {
		return effectDuration;
	}
}
