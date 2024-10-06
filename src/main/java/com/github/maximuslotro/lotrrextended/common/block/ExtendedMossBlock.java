package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeatures;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedMossBlock extends Block implements IGrowable {
	public ExtendedMossBlock(Properties p_153790_) {
		super(p_153790_);
		CompostingHelper.prepareCompostable(this, 0.65f);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValidBonemealTarget(IBlockReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
		return pLevel.getBlockState(pPos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(World pLevel, Random pRand, BlockPos pPos, BlockState pState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld pLevel, Random pRand, BlockPos pPos, BlockState pState) {
		ExtendedFeatures.MOSS_PATCH_BONEMEAL.place(pLevel, pLevel.getChunkSource().getGenerator(), pRand, pPos.above());
	}
}