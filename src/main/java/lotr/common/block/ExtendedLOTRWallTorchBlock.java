package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedReflectionHelper;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedLOTRWallTorchBlock extends LOTRWallTorchBlock{
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public ExtendedLOTRWallTorchBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(true)));
	}

	public ExtendedLOTRWallTorchBlock(SoundType sound, Supplier<Block> lootBlock) {
		this(Properties.copy((AbstractBlock) lootBlock.get()).lootFrom(lootBlock));
		ExtendedReflectionHelper.setTorchValues(this, (LOTRTorchBlock) lootBlock.get());
	}

	public ExtendedLOTRWallTorchBlock(Supplier<Block> lootBlock) {
		this(SoundType.WOOD, lootBlock);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (state.getValue(LIT)) {
			super.animateTick(state, world, pos, rand);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
		super.createBlockStateDefinition(stateBuilder);
		stateBuilder.add(LIT);
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult hitBlock) {
		if (world.isClientSide) {
			if(blockState.getValue(LIT)) {
				return ActionResultType.PASS;
			}else {
				if(!player.getMainHandItem().isEmpty() && ExtendedTags.Items.TORCH_LIGHTING.contains(player.getMainHandItem().getItem())) {
					this.cycle(blockState, world, blockPos);
					makeLightParticles(blockState, world, blockPos);
					return ActionResultType.SUCCESS;
				}
				return ActionResultType.PASS;
			}
		} else {
			if(blockState.getValue(LIT)) {
				if(!player.getMainHandItem().isEmpty() && ExtendedTags.Items.TORCH_EXTINGUISHING.contains(player.getMainHandItem().getItem())) {
					this.cycle(blockState, world, blockPos);
					world.playSound((PlayerEntity)null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.3F, 0.5F);
					return ActionResultType.CONSUME;
				}
			}else {
				if(!player.getMainHandItem().isEmpty() && ExtendedTags.Items.TORCH_LIGHTING.contains(player.getMainHandItem().getItem())) {
					this.cycle(blockState, world, blockPos);
					world.playSound((PlayerEntity)null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 0.3F, 0.6F);
					return ActionResultType.CONSUME;
				}
			}
			return ActionResultType.PASS;
		}
	}

	private BlockState cycle(BlockState blockState, World world, BlockPos blockPos) {
		blockState = blockState.cycle(LIT);
		world.setBlock(blockPos, blockState, 3);
		return blockState;
	}

	private void makeLightParticles(BlockState state, World world, BlockPos pos) {
		Direction facing = (Direction) state.getValue(FACING);
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.7D;
		double d2 = (double)pos.getZ() + 0.5D;
		double up = 0.22D;
		double across = 0.27D;
		Direction opposite = facing.getOpposite();
		world.addParticle(ParticleTypes.SMOKE, d0 + across * opposite.getStepX(), d1 + up, d2 + across * opposite.getStepZ(), 0.0D, 0.0D, 0.0D);
	}
}
