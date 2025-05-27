package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedSwappablePathSlabBlock extends ExtendedStemableAxialSlabBlock {

    public ExtendedSwappablePathSlabBlock(Block block) {
        super(block);
        // init LOTRTEXTURE blockstate
        this.registerDefaultState(this.stateDefinition.any().setValue(LOTRTEXTURE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        // Add LOTRTEXTURE to blockstates
        builder.add(LOTRTEXTURE);
    }

    public static final BooleanProperty LOTRTEXTURE = BooleanProperty.create("lotrtexture");

    private static final VoxelShape BOTTOM_LOTR = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    private static final VoxelShape TOP_LOTR = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    private static final VoxelShape DOUBLE_LOTR = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {

        SlabType slabType = state.getValue(TYPE);

        Direction.Axis axis = state.getValue(this.getSlabAxisProperty());

        if (state.getValue(LOTRTEXTURE) && axis == Direction.Axis.Y) {

            return slabType == SlabType.BOTTOM ? BOTTOM_LOTR :
                    (slabType == SlabType.TOP ? TOP_LOTR : DOUBLE_LOTR);

        } else {
            return super.getShape(state, world, pos, context); // initial collision box
        }
    }

    // Swap between LOTR and vanilla texture
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos,
                                PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() instanceof ShovelItem) {

            if (!world.isClientSide) {
                BlockState previousState = world.getBlockState(pos);

                if (state.hasProperty(LOTRTEXTURE)) {
                    previousState = previousState.setValue(LOTRTEXTURE, !state.getValue(LOTRTEXTURE));
                }

                world.setBlock(pos, previousState, 3); // update neighbors + notify clients
                heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand)); // Damage the shovel
            }

            return ActionResultType.SUCCESS;
        }

        return super.use(state, world, pos, player, hand, hit);
    }

}
