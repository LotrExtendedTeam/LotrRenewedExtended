package lotr.common.block;

import com.github.maximuslotro.lotroExtended.common.init.ExtendedTags;

import net.minecraft.block.Block;
import net.minecraft.tags.ITag.INamedTag;

public class ExtendedGreenFireBlock extends ExtendedFireBlock{

	public ExtendedGreenFireBlock(Properties p_i241187_1_) {
		super(p_i241187_1_);
	}

	@Override
	public INamedTag<Block> getTag() {
		return ExtendedTags.Blocks.GREEN_FIRE_BASE_BLOCKS;
	}
}
