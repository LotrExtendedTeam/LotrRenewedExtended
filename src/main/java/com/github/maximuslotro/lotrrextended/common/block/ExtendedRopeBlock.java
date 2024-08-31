package com.github.maximuslotro.lotrrextended.common.block;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.enums.RopeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedRopeBlock extends HorizontalBlock implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<RopeType> SECTION_TYPE = EnumProperty.create("section_type", RopeType.class);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

	public ExtendedRopeBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SECTION_TYPE, RopeType.SINGLE).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		switch (blockState.getValue(FACING)) {
			case EAST :
				return EAST_AABB;
			case WEST :
				return WEST_AABB;
			case SOUTH :
				return SOUTH_AABB;
			case NORTH :
			default :
				return NORTH_AABB;
		}
	}

	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand unused, BlockRayTraceResult p_225533_6_) {
		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, SECTION_TYPE, WATERLOGGED);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		Direction direction = pState.getValue(FACING);
		return canAttachTo(pLevel, pPos.relative(direction.getOpposite()), direction) || canAttachAbove(pLevel, pPos);
	}

	private static boolean canAttachAbove(IWorldReader pReader, BlockPos pPos) {
		return pReader.getBlockState(pPos.relative(Direction.UP)).getBlock() instanceof ExtendedRopeBlock;
	}

	private boolean canAttachTo(IBlockReader pBlockReader, BlockPos pPos, Direction pDirection) {
		BlockState blockstate = pBlockReader.getBlockState(pPos);
		return blockstate.isFaceSturdy(pBlockReader, pPos, pDirection);
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		if (pFacing.getOpposite() == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (pFacing == Direction.UP) {
				RopeType thisRopeType = pState.getValue(SECTION_TYPE);
				if (pFacingState.getBlock() instanceof ExtendedRopeBlock) {
					RopeType aboveRopeType = pFacingState.getValue(SECTION_TYPE);
					if (thisRopeType == RopeType.SINGLE && aboveRopeType == RopeType.TOP_SMALL) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_SMALL);
					}

					if (thisRopeType == RopeType.TOP_SMALL && aboveRopeType == RopeType.TOP_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}

					if (thisRopeType == RopeType.BOTTOM_SMALL && aboveRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_LARGE);
					}

					if (thisRopeType == RopeType.BOTTOM_LARGE && aboveRopeType == RopeType.TOP_SMALL) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_SMALL);
					}
				} else {
					if (thisRopeType == RopeType.BOTTOM_SMALL || thisRopeType == RopeType.BOTTOM_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.SINGLE);
					}
					if (thisRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.TOP_SMALL);
					}
				}
			}
			if (pState.getValue(WATERLOGGED)) {
				pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
			}
			return pState;
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		RopeType type;
		BlockPos blockpos = pContext.getClickedPos();
	    BlockState aboveState = pContext.getLevel().getBlockState(blockpos.above());
	    BlockState belowState = pContext.getLevel().getBlockState(blockpos.below());
	    FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
		if(aboveState.getBlock() instanceof ExtendedRopeBlock && belowState.getBlock() instanceof ExtendedRopeBlock) {
			type = RopeType.MIDDLE_LARGE;
		}else if (aboveState.getBlock() instanceof ExtendedRopeBlock) {
			RopeType aboveType = aboveState.getValue(SECTION_TYPE);
			if(aboveType==RopeType.BOTTOM_LARGE) {
				type = RopeType.BOTTOM_LARGE;
			}else if(aboveType==RopeType.BOTTOM_SMALL) {
				type = RopeType.BOTTOM_LARGE;
			}else if(aboveType==RopeType.SINGLE) {
				type = RopeType.BOTTOM_SMALL;
			}
			type = RopeType.SINGLE;
		}else if (belowState.getBlock() instanceof ExtendedRopeBlock) {
			RopeType belowType = belowState.getValue(SECTION_TYPE);
			if(belowType==RopeType.TOP_LARGE) {
				type = RopeType.TOP_LARGE;
			}else if(belowType==RopeType.TOP_SMALL) {
				type = RopeType.TOP_LARGE;
			}else if(belowType==RopeType.SINGLE) {
				type = RopeType.TOP_SMALL;
			}
			type = RopeType.SINGLE;
		}else {
			type = RopeType.SINGLE; // Since we are not in the middle or below rope, we must be a single rat
		}
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(SECTION_TYPE, type).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}

	@Override
    public boolean makesOpenTrapdoorAboveClimbable(BlockState state, IWorldReader world, BlockPos pos, BlockState trapdoorState)
    {
        return state.getBlock() instanceof ExtendedRopeBlock && state.getValue(ExtendedRopeBlock.FACING) == trapdoorState.getValue(TrapDoorBlock.FACING);
    }
}