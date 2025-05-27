package lotr.common.block;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.state.properties.BlockStateProperties;


public class ExtendedDirtSlab extends ExtendedPlantableAxialSlabBlock {

    public ExtendedDirtSlab(Block block) {
        super(block);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos,
                                PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() instanceof ShovelItem) {

            if (!world.isClientSide) {
                BlockState newState = ExtendedBlocks.GRASS_PATH_SLAB.get().defaultBlockState();

                if (state.hasProperty(BlockStateProperties.SLAB_TYPE)) {
                    newState = newState.setValue(BlockStateProperties.SLAB_TYPE, state.getValue(BlockStateProperties.SLAB_TYPE));
                }
                if (state.hasProperty(BlockStateProperties.AXIS)) {
                    newState = newState.setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS));
                }
                if (state.hasProperty(BlockStateProperties.WATERLOGGED) &&
                        newState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    newState = newState.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));
                }

                world.setBlock(pos, newState, 3); // update neighbors + notify clients
                heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand)); // Damage the shovel
            }

            return ActionResultType.SUCCESS;
        }

        return super.use(state, world, pos, player, hand, hit);
    }
}
