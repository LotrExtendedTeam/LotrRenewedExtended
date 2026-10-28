package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

public class ExtendedFangornRiverWeedBlock extends ExtendedFangornFlowerBlock {
	protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);

	public ExtendedFangornRiverWeedBlock(Effect pSuspiciousStewEffect, int pEffectDuration, Properties pProperties) {
		super(pSuspiciousStewEffect, pEffectDuration, pProperties);
	}

	@Override
	public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		super.entityInside(pState, pLevel, pPos, pEntity);
		if (pLevel instanceof ServerWorld && pEntity instanceof BoatEntity) {
			pLevel.destroyBlock(new BlockPos(pPos), true, pEntity);
		}
	}

	@Override
	protected boolean mayPlaceOn(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		FluidState fluidstate = pLevel.getFluidState(pPos);
		FluidState fluidstate1 = pLevel.getFluidState(pPos.above());
		return (fluidstate.getType() == Fluids.WATER || pState.getMaterial() == Material.ICE) && fluidstate1.getType() == Fluids.EMPTY;
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
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.WATER;
	}
}
