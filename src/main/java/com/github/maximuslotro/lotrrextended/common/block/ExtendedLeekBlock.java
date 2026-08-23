package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedLeekBlock extends CropsBlock {
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)};

	public ExtendedLeekBlock(AbstractBlock.Properties pProperties) {
		super(pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.65f);
	}

	@Override
	protected IItemProvider getBaseSeedId() {
		return ExtendedItems.LEEK.get();
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pWorld, BlockPos pPos, ISelectionContext pContext) {
		return SHAPE_BY_AGE[pState.getValue(this.getAgeProperty())];
	}
}