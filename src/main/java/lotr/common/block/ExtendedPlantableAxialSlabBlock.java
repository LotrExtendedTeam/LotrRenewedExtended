package lotr.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

public class ExtendedPlantableAxialSlabBlock extends ExtendedStemableAxialSlabBlock {

	public ExtendedPlantableAxialSlabBlock(Supplier<Block> block) {
		super(block);
	}

	public ExtendedPlantableAxialSlabBlock(Block block) {
		super(block);
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.relative(facing));
		PlantType type = plantable.getPlantType(world, pos.relative(facing));
		if (PlantType.CROP.equals(type)) {
			if(plantable instanceof StemBlock) {
				if(state.getValue(SlabBlock.TYPE)!=SlabType.BOTTOM) {
					if(state.getValue(BlockStateProperties.AXIS)==Axis.Y) {
						return true;
					}else if(state.getValue(BlockStateProperties.AXIS)==Axis.X && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
						return true;
					}else if(state.getValue(BlockStateProperties.AXIS)==Axis.Z && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
						return true;
					}
				}
			}
		}else if (PlantType.PLAINS.equals(type)) {
			if(state.hasProperty(SlabBlock.TYPE)) {
				// Check valid orientation
				if(state.getValue(SlabBlock.TYPE)!=SlabType.BOTTOM) {
					if(state.getBlock() instanceof AxialSlabBlock && state.hasProperty(BlockStateProperties.AXIS)) {
						if(state.getValue(BlockStateProperties.AXIS)==Axis.Y) {
							return true;
						}else if(state.getValue(BlockStateProperties.AXIS)==Axis.X && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
							return true;
						}else if(state.getValue(BlockStateProperties.AXIS)==Axis.Z && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
							return true;
						}
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
}
