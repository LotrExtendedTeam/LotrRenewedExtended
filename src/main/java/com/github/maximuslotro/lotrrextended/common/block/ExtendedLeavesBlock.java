package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class ExtendedLeavesBlock extends LeavesBlock {

	public ExtendedLeavesBlock() {
		this(MaterialColor.PLANT);
	}
	public ExtendedLeavesBlock(MaterialColor materialColor) {
		super(Properties.of(Material.LEAVES, materialColor).strength(0.2F).randomTicks()
			.sound(SoundType.GRASS).noOcclusion().harvestTool(ToolType.HOE)
			.isValidSpawn(ExtendedBlockParms::allowSpawnOnLeaves)
			.isSuffocating(ExtendedBlockParms::never)
			.isViewBlocking(ExtendedBlockParms::never));
		ExtendedDeferredComposterInjector.addCompostable(this, 0.3F);
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}
}