package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedGroundGrassBlock extends SpreadableSnowyDirtBlock implements IGrowable {
	private final RegistryObject<Block> base;
	private final RegistryObject<Block> path;
	private final RegistryObject<Block> till;
	public ExtendedGroundGrassBlock(MaterialColor color, SoundType sound, RegistryObject<Block> spreadBlockState, RegistryObject<Block> pathBlockState, RegistryObject<Block> tillBlockState) {
		super(Properties.of(Material.GRASS, color).randomTicks().strength(0.6F).sound(sound).harvestTool(ToolType.SHOVEL));
		this.base = spreadBlockState;
		this.path = pathBlockState;
		this.till = tillBlockState;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_) {
		return p_176473_1_.getBlockState(p_176473_2_.above()).isAir();
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
		return (toolType == ToolType.SHOVEL && path!=null) ? path.get().defaultBlockState() : 
			(toolType == ToolType.HOE && till!=null) ? till.get().defaultBlockState() : super.getToolModifiedState(state, world, pos, player, stack, toolType);
	}

	@Override
	public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
		return true;
	}

	private static boolean canBeGrass(BlockState p_220257_0_, IWorldReader p_220257_1_, BlockPos p_220257_2_) {
		BlockPos blockpos = p_220257_2_.above();
		BlockState blockstate = p_220257_1_.getBlockState(blockpos);
		if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LightEngine.getLightBlockInto(p_220257_1_, p_220257_0_, p_220257_2_, blockstate, blockpos,
					Direction.UP, blockstate.getLightBlock(p_220257_1_, blockpos));
			return i < p_220257_1_.getMaxLightLevel();
		}
	}

	private static boolean canPropagate(BlockState p_220256_0_, IWorldReader p_220256_1_, BlockPos p_220256_2_) {
		BlockPos blockpos = p_220256_2_.above();
		return canBeGrass(p_220256_0_, p_220256_1_, p_220256_2_)
				&& !p_220256_1_.getFluidState(blockpos).is(FluidTags.WATER);
	}

	@Override
	public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
		if (!canBeGrass(p_225542_1_, p_225542_2_, p_225542_3_)) {
			if (!p_225542_2_.isAreaLoaded(p_225542_3_, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			p_225542_2_.setBlockAndUpdate(p_225542_3_, base.get().defaultBlockState());
		} else {
			if (p_225542_2_.getMaxLocalRawBrightness(p_225542_3_.above()) >= 9) {
				BlockState blockstate = this.defaultBlockState();
				for (int i = 0; i < 4; ++i) {
					BlockPos blockpos = p_225542_3_.offset(p_225542_4_.nextInt(3) - 1, p_225542_4_.nextInt(5) - 3, p_225542_4_.nextInt(3) - 1);
					if (p_225542_2_.getBlockState(blockpos).is(base.get()) && canPropagate(blockstate, p_225542_2_, blockpos)) {
						p_225542_2_.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, Boolean.valueOf(p_225542_2_.getBlockState(blockpos.above()).is(Blocks.SNOW))));
					}
				}
			}

		}
	}

	@Override
	public void performBonemeal(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_) {
	}

	public RegistryObject<Block> getBase() {
		return base;
	}
}
