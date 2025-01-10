package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedGroundFarmlandBlock extends FarmlandBlock {
	private final RegistryObject<Block> base;
	public ExtendedGroundFarmlandBlock(MaterialColor color, SoundType sound, RegistryObject<Block> baseBlockState) {
		super(Properties.of(Material.DIRT, color).randomTicks().strength(0.6F).sound(sound).harvestTool(ToolType.SHOVEL));
		this.base = baseBlockState;
	}

	@Override
	public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
		if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
			turnToBlock(p_225534_1_, p_225534_2_, p_225534_3_, base.get().defaultBlockState());
		}
	}

	@Override
	public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
		int i = p_225542_1_.getValue(MOISTURE);
		if (!isNearWater(p_225542_2_, p_225542_3_) && !p_225542_2_.isRainingAt(p_225542_3_.above())) {
			if (i > 0) {
				p_225542_2_.setBlock(p_225542_3_, p_225542_1_.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
			} else if (!isUnderCrops(p_225542_2_, p_225542_3_)) {
				turnToBlock(p_225542_1_, p_225542_2_, p_225542_3_, base.get().defaultBlockState());
			}
		} else if (i < 7) {
			p_225542_2_.setBlock(p_225542_3_, p_225542_1_.setValue(MOISTURE, Integer.valueOf(7)), 2);
		}

	}

	@Override
	public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
	      BlockState blockstate = p_196260_2_.getBlockState(p_196260_3_.above());
	      return blockstate.getBlock() instanceof ExtendedBerryBushBlock ? true : super.canSurvive(p_196260_1_, p_196260_2_, p_196260_3_);
	   }

	@Override
	   public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
	      net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.relative(facing));
	      if ((plantable instanceof BushBlock) || net.minecraftforge.common.PlantType.CROP.equals(type)) {
	         return true;
	      }
	      return false;
	  }

	@Override
	public boolean isFertile(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(MOISTURE) > 0;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return !this.defaultBlockState().canSurvive(p_196258_1_.getLevel(), p_196258_1_.getClickedPos()) ? base.get().defaultBlockState() : super.getStateForPlacement(p_196258_1_);
	}

	@Override
	public void fallOn(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
		if (!p_180658_1_.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(p_180658_1_, p_180658_2_, base.get().defaultBlockState(), p_180658_4_, p_180658_3_)) { // Forge: Move logic to Entity#canTrample
			turnToBlock(p_180658_1_.getBlockState(p_180658_2_), p_180658_1_, p_180658_2_, base.get().defaultBlockState());
		}
		// Pull from Block.fallOn(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_)
		// to hurt entities but bypass FarmlandBlock.fallOn conflicting with block placement
		p_180658_3_.causeFallDamage(p_180658_4_, 1.0F);
		//super.fallOn(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_);
	}

	public static void turnToBlock(BlockState p_199610_0_, World p_199610_1_, BlockPos p_199610_2_, BlockState toChangeTo) {
		p_199610_1_.setBlockAndUpdate(p_199610_2_, pushEntitiesUp(p_199610_0_, toChangeTo, p_199610_1_, p_199610_2_));
	}

	// Copy private method from FarmlandBlock
	private boolean isUnderCrops(IBlockReader p_176529_0_, BlockPos p_176529_1_) {
		BlockState plant = p_176529_0_.getBlockState(p_176529_1_.above());
		BlockState state = p_176529_0_.getBlockState(p_176529_1_);
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(p_176529_0_, p_176529_1_, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
	}

	// Copy private method from FarmlandBlock
	private static boolean isNearWater(IWorldReader p_176530_0_, BlockPos p_176530_1_) {
		for (BlockPos blockpos : BlockPos.betweenClosed(p_176530_1_.offset(-4, 0, -4), p_176530_1_.offset(4, 1, 4))) {
			if (p_176530_0_.getFluidState(blockpos).is(FluidTags.WATER)) {
				return true;
			}
		}
		return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_176530_0_, p_176530_1_);
	}

	public RegistryObject<Block> getBase() {
		return base;
	}
}
