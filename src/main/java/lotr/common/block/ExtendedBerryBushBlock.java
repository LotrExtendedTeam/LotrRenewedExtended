package lotr.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lotr.common.init.LOTRBlocks;

public class ExtendedBerryBushBlock extends GlassBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    public static final BooleanProperty FULL = BooleanProperty.create("full");
    private final RegistryObject<Item> berryItem;

    private final RegistryObject<Item> berryBlock;

    public ExtendedBerryBushBlock(RegistryObject<Item> berryItem, RegistryObject<Item> berryBlock) {
    	super(Properties.of(Material.LEAVES, MaterialColor.COLOR_GREEN)
    			.randomTicks()
    			.sound(SoundType.SWEET_BERRY_BUSH)
    			.strength(0.2F)
    			.noOcclusion()
    			.harvestTool(ToolType.HOE)
    			.isValidSpawn(LOTRBlocks::allowSpawnOnLeaves)
    			.isRedstoneConductor(LOTRBlocks::posPredicateFalse)
    			.isSuffocating(LOTRBlocks::posPredicateFalse)
    			.isViewBlocking(LOTRBlocks::posPredicateFalse));
        this.berryItem = berryItem;
        this.berryBlock = berryBlock;
        registerDefaultState(stateDefinition.any().setValue(AGE, 3).setValue(FULL, true));
    }

	@SuppressWarnings("deprecation")
	@Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        int age = state.getValue(AGE);
        if (age < 3) {
            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, age + 1));
        } else if (age == 3 && !state.getValue(FULL)) {
            worldIn.setBlockAndUpdate(pos, state.setValue(FULL, true));
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() == Items.BONE_MEAL) {
            if (state.getValue(AGE) < 3) {
                // Apply bonemeal effect
                if (!world.isClientSide) {
                    world.setBlockAndUpdate(pos, state.setValue(AGE, 3).setValue(FULL, true));
                    spawnBonemealParticles(world, pos);
                    if (!player.isCreative()) {
                        heldItem.shrink(1);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        } else if (state.getValue(AGE) == 3 && !player.getMainHandItem().getItem().equals(Items.BONE_MEAL)) {
            if (!world.isClientSide) {
                world.setBlockAndUpdate(pos, state.setValue(AGE, 1).setValue(FULL, false));
                spawnBerriesAsEntity(world, pos, new ItemStack(berryItem.get(), world.random.nextInt(5) + 1));
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    private void spawnBonemealParticles(World world, BlockPos pos) {
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.25, 0.25, 0.25, 0.1);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        int age = state.getValue(AGE);
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(berryBlock.get()));
        if (state.getValue(FULL) && age == 3) {
            drops.add(new ItemStack(berryItem.get(), builder.getLevel().getRandom().nextInt(5) + 1));
        }
        return drops;
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, FULL);
    }

    private void spawnBerriesAsEntity(World world, BlockPos pos, ItemStack stack) {
        if (!world.isClientSide) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            world.addFreshEntity(itemEntity);
        }
    }
    @Override
	public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
		return true;
	}
}
