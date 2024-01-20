package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraftforge.common.ToolType;

public class ExtendedLOTROreBlock extends LOTROreBlock {
	private ToolType tool;
	public ExtendedLOTROreBlock(Properties properties, ToolType tool) {
		super(properties);
		this.tool = tool;
	}

	@Override
	public ToolType getHarvestTool(BlockState state) {
		return tool;
	}
}
