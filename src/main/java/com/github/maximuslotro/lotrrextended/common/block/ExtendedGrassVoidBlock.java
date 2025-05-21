package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedGrassVoidTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedGrassVoidBlock extends Block {
	private static final VoxelShape SHAPE = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);

	public ExtendedGrassVoidBlock(AbstractBlock.Properties props) {
		super(props);
	}

	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.INVISIBLE;
	}

	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return SHAPE;
	}

	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return 1.0F;
	}

	public PushReaction getPistonPushReaction(BlockState pState) {
		return PushReaction.DESTROY;
	}


	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExtendedGrassVoidTileEntity();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
}