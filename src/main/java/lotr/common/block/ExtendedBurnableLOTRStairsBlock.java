package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class ExtendedBurnableLOTRStairsBlock extends LOTRStairsBlock{

	public ExtendedBurnableLOTRStairsBlock(Supplier<Block> block) {
		super(block);
		((FireBlock) Blocks.FIRE).setFlammable(this, 60, 20);
	}
}
