package lotr.common.block;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;

public class ExtendedPlantableSpreadableGrowableAxialSlabBlock extends ExtendedPlantableSpreadableAxialSlabBlock implements IGrowable, IExtendedSwappableTexture  {

	public ExtendedPlantableSpreadableGrowableAxialSlabBlock(Supplier<Block> block, Supplier<Block> slabBase, Supplier<Block> blockBase, Supplier<Block> blockGrowable) {
		super(block, slabBase, blockBase, blockGrowable);
		this.registerDefaultState(this.stateDefinition.any().setValue(LOTRTEXTURE, false));
	}

	public ExtendedPlantableSpreadableGrowableAxialSlabBlock(Block block, Supplier<Block> slabBase, Supplier<Block> blockBase, Supplier<Block> blockGrowable) {
		super(block, slabBase, blockBase, blockGrowable);
		this.registerDefaultState(this.stateDefinition.any().setValue(LOTRTEXTURE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		// Add LOTRTEXTURE to blockstates
		builder.add(LOTRTEXTURE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValidBonemealTarget(IBlockReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
		return pLevel.getBlockState(pPos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(World pLevel, Random pRand, BlockPos pPos, BlockState pState) {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public void performBonemeal(ServerWorld pLevel, Random pRand, BlockPos pPos, BlockState pState) {
		BlockPos blockpos = pPos.above();
	      BlockState blockstate = Blocks.GRASS.defaultBlockState();

	      label48:
	      for(int i = 0; i < 128; ++i) {
	         BlockPos blockpos1 = blockpos;

	         for(int j = 0; j < i / 16; ++j) {
	            blockpos1 = blockpos1.offset(pRand.nextInt(3) - 1, (pRand.nextInt(3) - 1) * pRand.nextInt(3) / 2, pRand.nextInt(3) - 1);
	            if (!pLevel.getBlockState(blockpos1.below()).is(this) || pLevel.getBlockState(blockpos1).isCollisionShapeFullBlock(pLevel, blockpos1)) {
	               continue label48;
	            }
	         }

	         BlockState blockstate2 = pLevel.getBlockState(blockpos1);
	         if (blockstate2.is(blockstate.getBlock()) && pRand.nextInt(10) == 0) {
	            ((IGrowable)blockstate.getBlock()).performBonemeal(pLevel, pRand, blockpos1, blockstate2);
	         }

	         if (blockstate2.isAir()) {
	            BlockState blockstate1;
	            if (pRand.nextInt(8) == 0) {
	               List<ConfiguredFeature<?, ?>> list = pLevel.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
	               if (list.isEmpty()) {
	                  continue;
	               }

	               ConfiguredFeature<?, ?> configuredfeature = list.get(0);
	               FlowersFeature flowersfeature = (FlowersFeature)configuredfeature.feature;
	               blockstate1 = flowersfeature.getRandomFlower(pRand, blockpos1, configuredfeature.config());
	            } else {
	               blockstate1 = blockstate;
	            }

	            if (blockstate1.canSurvive(pLevel, blockpos1)) {
	               pLevel.setBlock(blockpos1, blockstate1, 3);
	            }
	         }
	      }
	}
	
}
