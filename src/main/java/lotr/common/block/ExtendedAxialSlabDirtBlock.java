package lotr.common.block;

import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ExtendedAxialSlabDirtBlock extends ExtendedAxialSlabPlantableBlock {
	private final Supplier<Block> slabPath;

    public ExtendedAxialSlabDirtBlock(Block block, Supplier<Block> pSlabPath) {
        super(block);
        this.slabPath = pSlabPath;
    }

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if (toolType == ToolType.SHOVEL) {
        	BlockState newState = ExtendedBlockStateUtils.copyBlockStateToBlock(slabPath.get(), state);
			// Allow shift clicking to set the grass type lotr/vanilla looking
        	if(newState.hasProperty(IExtendedSwappableTexture.USELOTRTEXTURE)) {
        		if(player.isCrouching()) {
        			newState = newState.setValue(IExtendedSwappableTexture.USELOTRTEXTURE, true); // Place lotr slabs when shift placing
    			}else {
    				newState = newState.setValue(IExtendedSwappableTexture.USELOTRTEXTURE, false); // Place vanilla slabs when regular placing
    			}
        	}
			return newState;
        }
        return null;
    }
}