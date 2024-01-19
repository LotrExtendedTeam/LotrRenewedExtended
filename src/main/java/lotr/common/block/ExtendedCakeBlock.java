package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.CakeBlock;

public class ExtendedCakeBlock extends CakeBlock {

	public ExtendedCakeBlock(Properties p_i48434_1_, float compostValue) {
		super(p_i48434_1_);
		if(compostValue>0) {
			CompostingHelper.prepareCompostable(this, compostValue);
		}
	}
}
