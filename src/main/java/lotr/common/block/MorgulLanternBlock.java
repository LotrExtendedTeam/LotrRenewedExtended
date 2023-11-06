package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class MorgulLanternBlock extends NonWaterloggableLanternBlock {
	private static final VoxelShape morgulGroundShape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
	private static final VoxelShape morgulHangingShape = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape morgulGroundCollisionShape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D);
	private static final VoxelShape morgulHangingCollisionShape = Block.box(5.0D, 1.0D, 5.0D, 11.0D, 3.0D, 11.0D);

	public MorgulLanternBlock() {
		super(Properties.of(Material.DECORATION).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(3.5F).sound(SoundType.LANTERN).lightLevel(LOTRBlocks.constantLight(14)).noOcclusion());
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? morgulHangingCollisionShape : morgulGroundCollisionShape;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? morgulHangingShape : morgulGroundShape;
	}
}
