package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedGroundPathBlock extends GrassPathBlock{
	private final RegistryObject<Block> base;
	public ExtendedGroundPathBlock(MaterialColor color, SoundType sound, RegistryObject<Block> baseBlockState) {
		super(Properties.of(Material.DIRT, color).strength(0.65F).sound(sound).harvestTool(ToolType.SHOVEL));
		this.base = baseBlockState;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return !this.defaultBlockState().canSurvive(p_196258_1_.getLevel(), p_196258_1_.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), base.get().defaultBlockState(), p_196258_1_.getLevel(), p_196258_1_.getClickedPos()) : super.getStateForPlacement(p_196258_1_);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		world.setBlockAndUpdate(pos, Block.pushEntitiesUp(state, base.get().defaultBlockState(), world, pos));
	}

	public RegistryObject<Block> getBase() {
		return base;
	}
}
