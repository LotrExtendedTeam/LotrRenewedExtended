package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import lotr.common.init.LOTRParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedElvenLanternBlock extends ExtendedLanternBlock {
	private static final VoxelShape GROUND_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 9.0D, 10.0D);
	private static final VoxelShape HANGING_SHAPE = Block.box(6.0D, 2.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public ExtendedElvenLanternBlock(int lightLevel) {
		super(lightLevel);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : GROUND_SHAPE;
	}

	@Override
	public void animateTick(BlockState pState, World world, BlockPos pos, Random rand) {
		if(pState.getValue(HANGING)) {
		    world.addParticle(LOTRParticles.BLUE_ELVEN_GLOW.get(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
		}else {
		    world.addParticle(LOTRParticles.BLUE_ELVEN_GLOW.get(), pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
		}
	}
}
