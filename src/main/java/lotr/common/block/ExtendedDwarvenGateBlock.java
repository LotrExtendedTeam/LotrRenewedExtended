package lotr.common.block;

import com.github.maximuslotro.lotrrextended.mixins.lotr.common.block.MixinGateBlockAccessor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedDwarvenGateBlock extends GateBlock{

	public ExtendedDwarvenGateBlock(Properties properties, boolean cutout) {
		super(properties, cutout);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.block();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentState, Direction side) {
		Block block = adjacentState.getBlock();
		if (block instanceof GateBlock) {
			GateBlock otherGateBlock = (GateBlock) block;
			if (false == ((MixinGateBlockAccessor)otherGateBlock).isCutoutGate()) {
				Direction thisFacing = state.getValue(FACING);
				Direction otherFacing = adjacentState.getValue(FACING);
				boolean thisOpen = state.getValue(OPEN);
				boolean otherOpen = adjacentState.getValue(OPEN);
				boolean connectToSide = !directionsMatch(thisFacing, side);
				if(thisOpen && connectToSide) {
					return thisOpen == otherOpen && directionsMatch(thisFacing, otherFacing) && otherGateBlock.directionsMatch(thisFacing, otherFacing);
				}
			}
		}

		return false;
	}
}
