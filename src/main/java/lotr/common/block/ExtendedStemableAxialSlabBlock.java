package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;

public class ExtendedStemableAxialSlabBlock extends AxialSlabBlock {

	public ExtendedStemableAxialSlabBlock(Supplier<Block> block) {
		super(block);
	}

	public ExtendedStemableAxialSlabBlock(Block block) {
		super(block);
	}
}
