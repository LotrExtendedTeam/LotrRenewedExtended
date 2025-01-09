package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class ExtendedHearthBlock extends HearthBlock {

	@Override
	public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos currentPos, BlockPos facingPos) {
		state = super.updateShape(state, facing, facingState, world, currentPos, facingPos);
		return facing == Direction.UP && !(Boolean) state.getValue(LIT)
				? ((Block) LOTRBlocks.HEARTH_BLOCK.get()).defaultBlockState()
				: state;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			PlayerEntity player) {
		return new ItemStack(LOTRBlocks.HEARTH_BLOCK.get());
	}
}
