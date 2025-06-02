package lotr.common.block; // Use your actual package

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lotr.common.block.IExtendedSwappableTexture.LOTRTEXTURE;

@Mod.EventBusSubscriber(modid = LotrExtendedMod.MODID) // Replace with your actual mod ID
public class ExtendedBlockInteractionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        BlockState state = world.getBlockState(pos);
        Hand hand = event.getHand();

        // Check if it's your specific block
        if (state.getBlock() instanceof ExtendedPlantableSpreadableGrowableAxialSlabBlock) {

            if (heldItem.getItem() instanceof ShovelItem) {
                if (!world.isClientSide) {

                    if (player.isCrouching()) {

                        if (state.hasProperty(LOTRTEXTURE)) {
                            state = state.setValue(LOTRTEXTURE, !state.getValue(LOTRTEXTURE));
                        }

                        world.setBlock(pos, state, 3);
                        heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));

                        event.setCanceled(true);
                        event.setCancellationResult(ActionResultType.SUCCESS);

                        return;
                    }

                    // Your transformation logic
                    BlockState newState = ExtendedBlocks.GRASS_PATH_SLAB.get().defaultBlockState();

                    // Copy state properties
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

                    world.setBlock(pos, newState, 3);
                    heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(event.getHand()));

                }

                // Cancel the event so it doesn't continue to the block's use method
                event.setCanceled(true);
                event.setCancellationResult(ActionResultType.SUCCESS);
            }

        } else if (state.getBlock() instanceof ExtendedSwappablePathSlabBlock){


            if (heldItem.getItem() instanceof ShovelItem) {

                if (!world.isClientSide) {
                    BlockState previousState = world.getBlockState(pos);

                    if (state.hasProperty(LOTRTEXTURE)) {
                        previousState = previousState.setValue(LOTRTEXTURE, !state.getValue(LOTRTEXTURE));
                    }

                    world.setBlock(pos, previousState, 3); // update neighbors + notify clients
                    heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand)); // Damage the shovel
                }

                event.setCanceled(true);
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }
    }
}