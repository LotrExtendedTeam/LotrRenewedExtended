package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;

public class ExtendedStemableAxialSlabBlock extends AxialSlabBlock {

	public ExtendedStemableAxialSlabBlock(Supplier<Block> block) {
		super(block);
	}

	public ExtendedStemableAxialSlabBlock(Block block) {
		super(block);
	}

	@Override
	public net.minecraftforge.common.ToolType getHarvestTool(BlockState state) {
		return ToolType.SHOVEL;
	}
}
