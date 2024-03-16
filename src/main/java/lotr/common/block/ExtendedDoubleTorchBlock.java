package lotr.common.block;

import java.util.Random;
import java.util.function.ToIntFunction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedDoubleTorchBlock extends DoubleTorchBlock{
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public ExtendedDoubleTorchBlock(int light) {
		super(Properties.of(Material.DECORATION).noCollission().strength(0.0F).lightLevel(getDoubleTorchLightLevel(light)).sound(SoundType.WOOD));
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(true)).setValue(HALF, DoubleBlockHalf.LOWER));
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

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult hitBlock) {
		if(blockState.getValue(HALF)== DoubleBlockHalf.UPPER) {
			if (world.isClientSide) {
				BlockState blockstate1 = blockState.cycle(LIT);
				if (blockstate1.getValue(LIT)) {
					makeLightParticles(world, blockPos);
				}
				return ActionResultType.SUCCESS;
			} else {
				BlockState blockstate = this.light(blockState, world, blockPos);
				if(blockstate.getValue(LIT)) {
					world.playSound((PlayerEntity)null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 0.3F, 0.6F);
				} else {
					world.playSound((PlayerEntity)null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.3F, 0.5F);
				}
				return ActionResultType.CONSUME;
			}
		}
		else {
			return super.use(blockState, world, blockPos, player, hand, hitBlock);
		}
	}

	private BlockState light(BlockState blockState, World world, BlockPos blockPos) {
		blockState = blockState.cycle(LIT);
		world.setBlock(blockPos, blockState, 3);
		return blockState;
	}

	private void makeLightParticles(World world, BlockPos pos) {
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.7D;
		double d2 = (double)pos.getZ() + 0.5D;
		world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	private static ToIntFunction<BlockState> getDoubleTorchLightLevel(int level) {
		return state -> {
			if(state.getValue(LIT)&&state.getValue(HALF)== DoubleBlockHalf.UPPER) {
				return level;
			}
			return 0;
		};
	}
}
