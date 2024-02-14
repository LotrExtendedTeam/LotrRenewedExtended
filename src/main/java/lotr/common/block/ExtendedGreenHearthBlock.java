package lotr.common.block;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;

public class ExtendedGreenHearthBlock extends ExtendedHearthBlock{
	@Override
	protected Block getFireBlock() {
		return ExtendedBlocks.GREEN_FIRE_BLOCK.get();
	}
}
