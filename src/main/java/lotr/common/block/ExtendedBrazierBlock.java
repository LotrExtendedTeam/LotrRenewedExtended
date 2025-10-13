package lotr.common.block;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBlockParms;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class ExtendedBrazierBlock extends NonWaterloggableLanternBlock {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final VoxelShape GROUND_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
	private static final VoxelShape HANGING_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape GROUND_SHAPE_COLLISION = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D);
	private static final VoxelShape HANGING_SHAPE_COLLISION = Block.box(5.0D, 1.0D, 5.0D, 11.0D, 3.0D, 11.0D);

	public ExtendedBrazierBlock(int lightLevel) {
		super(Properties.of(Material.DECORATION).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(3.5F).sound(SoundType.LANTERN).lightLevel(ExtendedBlockParms.litBlockEmission(lightLevel)).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.TRUE));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE_COLLISION : GROUND_SHAPE_COLLISION;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : GROUND_SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
		super.createBlockStateDefinition(stateBuilder);
		stateBuilder.add(LIT);
	}
}
