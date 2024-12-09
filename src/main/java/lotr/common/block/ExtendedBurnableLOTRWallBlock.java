package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class ExtendedBurnableLOTRWallBlock extends LOTRWallBlock{

	public ExtendedBurnableLOTRWallBlock(Supplier<Block> block) {
		super(block);
		((FireBlock) Blocks.FIRE).setFlammable(this, 60, 20);
	}
}
