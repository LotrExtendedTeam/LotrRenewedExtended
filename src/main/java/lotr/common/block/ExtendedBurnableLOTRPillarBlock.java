package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ExtendedBurnableLOTRPillarBlock extends LOTRPillarBlock {

	public ExtendedBurnableLOTRPillarBlock(Supplier<Block> blockSup) {
		super(Properties.copy(blockSup.get()));
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 40;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 40;
    }
}
