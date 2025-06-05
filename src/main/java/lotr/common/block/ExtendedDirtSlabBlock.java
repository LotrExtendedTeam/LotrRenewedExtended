package lotr.common.block;

import com.github.maximuslotro.lotrrextended.common.utils.BlockStateUtils;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ExtendedDirtSlabBlock extends ExtendedPlantableAxialSlabBlock {

    public ExtendedDirtSlabBlock(Block block) {
        super(block);
    }

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if (toolType == ToolType.SHOVEL) {
        	BlockState newState = BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.GRASS_PATH_SLAB, state);
			// Allow shift clicking to set the grass type lotr/vanilla looking
			if(player.isCrouching()) {
				newState.setValue(ExtendedPlantableSpreadableGrowableAxialSlabBlock.USELOTRTEXTURE, true); // Place lotr slabs when shift placing
			}else {
				newState.setValue(ExtendedPlantableSpreadableGrowableAxialSlabBlock.USELOTRTEXTURE, false); // Place vanilla slabs when regular placing
			}
			return newState;
        }
        return null;
    }
}