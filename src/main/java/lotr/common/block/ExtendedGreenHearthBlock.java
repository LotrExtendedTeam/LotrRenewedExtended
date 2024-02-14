package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class ExtendedGreenHearthBlock extends ExtendedHearthBlock{
	@Override
	protected Block getFireBlock() {
		return Blocks.SOUL_FIRE;
	}
}
