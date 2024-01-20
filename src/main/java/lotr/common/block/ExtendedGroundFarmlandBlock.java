package lotr.common.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedGroundFarmlandBlock extends FarmlandBlock {
	private final BlockState base;
	public ExtendedGroundFarmlandBlock(Properties p_i48400_1_, BlockState baseBlockState) {
		super(p_i48400_1_);
		this.base = baseBlockState;
	}

	public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
		if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
			turnToBlock(p_225534_1_, p_225534_2_, p_225534_3_);
		}
	}

	public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos p_225542_3_, Random p_225542_4_) {
		int i = p_225542_1_.getValue(MOISTURE);
		if (!isNearWater(p_225542_2_, p_225542_3_) && !p_225542_2_.isRainingAt(p_225542_3_.above())) {
			if (i > 0) {
				p_225542_2_.setBlock(p_225542_3_, p_225542_1_.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
			} else if (!isUnderCrops(p_225542_2_, p_225542_3_)) {
				turnToBlock(p_225542_1_, p_225542_2_, p_225542_3_);
			}
		} else if (i < 7) {
			p_225542_2_.setBlock(p_225542_3_, p_225542_1_.setValue(MOISTURE, Integer.valueOf(7)), 2);
		}

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return !this.defaultBlockState().canSurvive(p_196258_1_.getLevel(), p_196258_1_.getClickedPos()) ? base : super.getStateForPlacement(p_196258_1_);
	}

	@Override
	public void fallOn(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
		if (!p_180658_1_.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(p_180658_1_, p_180658_2_, base, p_180658_4_, p_180658_3_)) { // Forge: Move logic to Entity#canTrample
			turnToBlock(p_180658_1_.getBlockState(p_180658_2_), p_180658_1_, p_180658_2_);
		}
		super.fallOn(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_);
	}

	public void turnToBlock(BlockState p_199610_0_, World p_199610_1_, BlockPos p_199610_2_) {
		p_199610_1_.setBlockAndUpdate(p_199610_2_, pushEntitiesUp(p_199610_0_, base, p_199610_1_, p_199610_2_));
	}

	private boolean isUnderCrops(IBlockReader p_176529_0_, BlockPos p_176529_1_) {
		BlockState plant = p_176529_0_.getBlockState(p_176529_1_.above());
		BlockState state = p_176529_0_.getBlockState(p_176529_1_);
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(p_176529_0_, p_176529_1_, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
	}

	private static boolean isNearWater(IWorldReader p_176530_0_, BlockPos p_176530_1_) {
		for (BlockPos blockpos : BlockPos.betweenClosed(p_176530_1_.offset(-4, 0, -4), p_176530_1_.offset(4, 1, 4))) {
			if (p_176530_0_.getFluidState(blockpos).is(FluidTags.WATER)) {
				return true;
			}
		}
		return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_176530_0_, p_176530_1_);
	}
}
