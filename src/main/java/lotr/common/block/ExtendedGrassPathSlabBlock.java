package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.shapes.ISelectionContext;

public class ExtendedGrassPathSlabBlock extends ExtendedStemableAxialSlabBlock {
	public ExtendedGrassPathSlabBlock(Block block) {
		super(block);
	}

	private static final VoxelShape BOTTOM_Y = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {

		SlabType slabType = state.getValue(TYPE);

		Direction.Axis axis = state.getValue(this.getSlabAxisProperty());

		if (axis == Axis.Y && slabType == SlabType.BOTTOM) { // Change collision box
			return BOTTOM_Y;
		} else {
			return super.getShape(state, world, pos, context); // initial collision box
		}
	}
}