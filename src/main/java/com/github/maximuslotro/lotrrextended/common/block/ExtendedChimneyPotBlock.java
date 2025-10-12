package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedChimneyTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

/**
 * Based upon {@link CampfireBlock}
 * */
public class ExtendedChimneyPotBlock extends Block implements IWaterLoggable {
	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public ExtendedChimneyPotBlock(Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader reader) {
		return new ExtendedChimneyTileEntity();
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return SHAPE;
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void animateTick(BlockState pState, World pLevel, BlockPos pPos, Random pRand) {
		if (!pState.getValue(WATERLOGGED)) {
			if (pRand.nextInt(10) == 0) {
				for(int i = 0; i < pRand.nextInt(1) + 1; ++i) {
					pLevel.addParticle(ParticleTypes.LAVA, pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D, (pRand.nextFloat() / 2.0F), 5.0E-5D, (pRand.nextFloat() / 2.0F));
				}
			}
		}
	}

	@Override
	public boolean placeLiquid(IWorld pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
		if (!pState.getValue(BlockStateProperties.WATERLOGGED) && pFluidState.getType() == Fluids.WATER) {
			if (!pLevel.isClientSide()) {
				pLevel.playSound((PlayerEntity)null, pPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			dowse(pLevel, pPos, pState);
			pLevel.setBlock(pPos, pState.setValue(WATERLOGGED, Boolean.valueOf(true)), 3);
			pLevel.getLiquidTicks().scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
			return true;
		} else {
			return false;
		}
	}

	public static void dowse(IWorld pLevel, BlockPos pPos, BlockState p_235475_2_) {
		if (pLevel.isClientSide() && pLevel instanceof World) {
			for(int i = 0; i < 20; ++i) {
				makeParticles((World)pLevel, pPos, true);
			}
		}
	}

	public static void makeParticles(World pLevel, BlockPos pPos, boolean pSpawnExtraSmoke) {
		Random random = pLevel.getRandom();
		pLevel.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pPos.getX() + 0.5D + random.nextDouble() / 3.0D * (random.nextBoolean() ? 1 : -1), pPos.getY() + random.nextDouble() + random.nextDouble(), pPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
		if (pSpawnExtraSmoke) {
			pLevel.addParticle(ParticleTypes.SMOKE, (double)pPos.getX() + 0.25D + random.nextDouble() / 2.0D * (random.nextBoolean() ? 1 : -1), pPos.getY() + 0.4D, pPos.getZ() + 0.25D + random.nextDouble() / 2.0D * (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
		}
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		if (pState.getValue(WATERLOGGED)) {
			pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}
		return pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return false;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		IWorld iworld = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(flag));
	}

	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(WATERLOGGED);
	}
}
