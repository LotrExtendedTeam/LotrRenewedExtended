package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

/**
 * Copied from {@link FlowerBlock} but to remove effect instances
 * */
public class ExtendedFlowerEffectlessBlock extends BushBlock {
	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

	public ExtendedFlowerEffectlessBlock(Properties pProperties) {
		super(pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.65F);
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		Vector3d vector3d = pState.getOffset(pLevel, pPos);
		return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}
}
