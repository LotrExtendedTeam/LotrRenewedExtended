package com.github.maximuslotro.lotrrextended.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
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
		return p_176473_1_.getBlockState(p_176473_2_.above()).isAir();
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

	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	public void performBonemeal(ServerWorld pLevel, Random pRand, BlockPos pPos, BlockState pState) {
		BlockPos blockpos = pPos.above();
		BlockState blockstate = Blocks.GRASS.defaultBlockState();

		label48:
			for(int i = 0; i < 128; ++i) {
				BlockPos blockpos1 = blockpos;

				for(int j = 0; j < i / 16; ++j) {
					blockpos1 = blockpos1.offset(pRand.nextInt(3) - 1, (pRand.nextInt(3) - 1) * pRand.nextInt(3) / 2, pRand.nextInt(3) - 1);
					if (!pLevel.getBlockState(blockpos1.below()).is(this) || pLevel.getBlockState(blockpos1).isCollisionShapeFullBlock(pLevel, blockpos1)) {
						continue label48;
					}
				}

				BlockState blockstate2 = pLevel.getBlockState(blockpos1);
				if (blockstate2.is(blockstate.getBlock()) && pRand.nextInt(10) == 0) {
					((IGrowable)blockstate.getBlock()).performBonemeal(pLevel, pRand, blockpos1, blockstate2);
				}

				if (blockstate2.isAir()) {
					BlockState blockstate1;
					if (pRand.nextInt(8) == 0) {
						List<ConfiguredFeature<?, ?>> list = pLevel.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
						if (list.isEmpty()) {
							continue;
						}

						ConfiguredFeature<?, ?> configuredfeature = list.get(0);
						FlowersFeature flowersfeature = (FlowersFeature)configuredfeature.feature;
						blockstate1 = flowersfeature.getRandomFlower(pRand, blockpos1, configuredfeature.config());
					} else {
						blockstate1 = blockstate;
					}

					if (blockstate1.canSurvive(pLevel, blockpos1)) {
						pLevel.setBlock(blockpos1, blockstate1, 3);
					}
				}
			}
	}
}
