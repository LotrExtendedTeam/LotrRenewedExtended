package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ExtendedSwappablePathSlabBlock extends ExtendedStemableAxialSlabBlock implements IExtendedSwappableTexture {

	public ExtendedSwappablePathSlabBlock(Block block) {
		super(block);
		this.registerDefaultState(this.stateDefinition.any().setValue(USELOTRTEXTURE, true)); // Set default value to lotr textures not vanilla to preserve already placed slabs
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(USELOTRTEXTURE);
	}

	private static final VoxelShape BOTTOM_LOTR = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
	private static final VoxelShape TOP_LOTR = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	private static final VoxelShape DOUBLE_LOTR = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		SlabType slabType = state.getValue(TYPE);
		Direction.Axis axis = state.getValue(this.getSlabAxisProperty());
		if (!state.getValue(USELOTRTEXTURE) && axis == Direction.Axis.Y) {
			return slabType == SlabType.BOTTOM ? BOTTOM_LOTR : (slabType == SlabType.TOP ? TOP_LOTR : DOUBLE_LOTR);
		} else {
			return super.getShape(state, world, pos, context); // initial collision box
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState axialState = super.getStateForPlacement(context);
		if(context.isSecondaryUseActive()/*sneaking*/) {
			return axialState.setValue(USELOTRTEXTURE, true); // Place lotr slabs when shift placing
		}else {
			return axialState.setValue(USELOTRTEXTURE, false); // Place vanilla slabs when regular placing
		}
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if (toolType == ToolType.SHOVEL) {
        	// Turn vanilla into lotr path slab and vice versa by shift clicking
        	if (state.hasProperty(USELOTRTEXTURE)) {
				state = state.cycle(USELOTRTEXTURE);
			}
			return state;
        }
        return null;
    }
}
