package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;

public class ExtendedAxialSlabStemableBlock extends AxialSlabBlock {

	public ExtendedAxialSlabStemableBlock(Supplier<Block> block) {
		super(block);
	}

	public ExtendedAxialSlabStemableBlock(Block block) {
		super(block);
	}

	@Override
	public net.minecraftforge.common.ToolType getHarvestTool(BlockState state) {
		return ToolType.SHOVEL;
	}
}
