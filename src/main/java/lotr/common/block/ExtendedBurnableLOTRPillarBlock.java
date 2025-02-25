package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.extensions.IForgeBlockState;

public class ExtendedBurnableLOTRPillarBlock extends LOTRPillarBlock implements IForgeBlockState {

	public ExtendedBurnableLOTRPillarBlock(Supplier<Block> blockSup) {
		super(Properties.copy(blockSup.get()));
	}

	@Override
	public int getFireSpreadSpeed(IBlockReader world, BlockPos pos, Direction face) {
		return 40;
	}

	@Override
	public int getFlammability(IBlockReader world, BlockPos pos, Direction face) {
		return 40;
	}
}
