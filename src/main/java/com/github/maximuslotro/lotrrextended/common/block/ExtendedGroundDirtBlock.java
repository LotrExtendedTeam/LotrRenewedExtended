package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedGroundDirtBlock extends Block implements IGrowable {
	private final RegistryObject<Block> path;
	private final RegistryObject<Block> till;
	public ExtendedGroundDirtBlock(MaterialColor color, SoundType sound, RegistryObject<Block> pathBlockState, RegistryObject<Block> tillBlockState) {
		super(Properties.of(Material.DIRT, color).strength(0.5F).sound(sound).harvestTool(ToolType.SHOVEL));
		this.path = pathBlockState;
		this.till = tillBlockState;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_) {
		BlockPos abovePos = p_176473_2_.above();
		return p_176473_1_.getBlockState(abovePos).isAir(p_176473_1_, abovePos);
	}

	@Override
	public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
		return true;
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
		return (toolType == ToolType.SHOVEL && path!=null) ? path.get().defaultBlockState() : 
			(toolType == ToolType.HOE && till!=null) ? till.get().defaultBlockState() : super.getToolModifiedState(state, world, pos, player, stack, toolType);
	}

	@Override
	public void performBonemeal(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
	}
}
