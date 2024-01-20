package lotr.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class ExtendedGroundPathBlock extends GrassPathBlock{
	private final BlockState base;
	public ExtendedGroundPathBlock(Properties p_i48400_1_, BlockState baseBlockState) {
		super(p_i48400_1_);
		this.base = baseBlockState;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return !this.defaultBlockState().canSurvive(p_196258_1_.getLevel(), p_196258_1_.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), base, p_196258_1_.getLevel(), p_196258_1_.getClickedPos()) : super.getStateForPlacement(p_196258_1_);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		world.setBlockAndUpdate(pos, Block.pushEntitiesUp(state, base, world, pos));
	}
}
