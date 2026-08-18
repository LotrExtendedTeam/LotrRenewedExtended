package lotr.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import net.minecraft.block.Block;

public class ExtendedGreenHearthBlock extends ExtendedHearthBlock{
	@Override
	protected Block getFireBlock() {
		return ExtendedBlocks.GREEN_FIRE_BLOCK.get();
	}
}
