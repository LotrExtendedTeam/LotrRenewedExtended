package lotr.common.block;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

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
		try {
			Field privateField = LOTRWallTorchBlock.class.getDeclaredField("torchParticles");
			privateField.setAccessible(true);
			privateField.set(this, ((LOTRTorchBlock) lootBlock.get()).copyTorchParticles());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			ExtendedLog.error("Failed to set torchParticles for ExtendedLOTRWallTorchBlock, of type "+lootBlock.get().getRegistryName().toString());
		}
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
			BlockState blockstate1 = blockState.cycle(LIT);
			if (blockstate1.getValue(LIT)) {
				makeLightParticles(blockState, world, blockPos);
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

	private BlockState light(BlockState blockState, World world, BlockPos blockPos) {
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
