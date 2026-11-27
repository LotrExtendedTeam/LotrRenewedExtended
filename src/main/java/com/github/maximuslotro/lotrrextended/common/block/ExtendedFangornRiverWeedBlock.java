package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedFangornRiverWeedBlock extends Block implements ExtendedIEffectItem{
	protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
	private final Effect suspiciousStewEffect;
	private final int effectDuration;

	public ExtendedFangornRiverWeedBlock(Effect pSuspiciousStewEffect, int pEffectDuration, Properties pProperties) {
		super(pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.65F);
		this.suspiciousStewEffect = pSuspiciousStewEffect;
		this.effectDuration = pEffectDuration * 20;
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		FluidState belowFluid = pLevel.getFluidState(pPos.below());
		FluidState atFluid = pLevel.getFluidState(pPos);
		return (belowFluid.getType() == Fluids.WATER || pState.getMaterial() == Material.ICE) && atFluid.getType() == Fluids.EMPTY;
	}

	@Override
	public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		super.entityInside(pState, pLevel, pPos, pEntity);
		if (pLevel instanceof ServerWorld && pEntity instanceof BoatEntity) {
			pLevel.destroyBlock(new BlockPos(pPos), true, pEntity);
		}
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return AABB;
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return pType == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(pState, pLevel, pPos, pType);
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState pState, IBlockReader pReader, BlockPos pPos) {
		return pState.getFluidState().isEmpty();
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
