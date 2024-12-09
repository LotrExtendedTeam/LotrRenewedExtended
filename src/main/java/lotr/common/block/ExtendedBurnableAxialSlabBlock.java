package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class ExtendedBurnableAxialSlabBlock extends AxialSlabBlock{

	public ExtendedBurnableAxialSlabBlock(Supplier<Block> block) {
		super(block);
		((FireBlock) Blocks.FIRE).setFlammable(this, 60, 20);
	}

}
