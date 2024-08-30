package com.github.maximuslotro.lotrrextended.common.block;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.enums.RopeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedRopeBlock extends HorizontalBlock implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<RopeType> SECTION_TYPE = EnumProperty.create("section_type", RopeType.class);
	protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 4.0D, 12.0D, 15.0D, 10.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 4.0D, 0.0D, 15.0D, 10.0D, 4.0D);
	protected static final VoxelShape WEST_AABB = Block.box(12.0D, 4.0D, 1.0D, 16.0D, 10.0D, 15.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 4.0D, 1.0D, 4.0D, 10.0D, 15.0D);

	public ExtendedRopeBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SECTION_TYPE, RopeType.MIDDLE).setValue(WATERLOGGED, false));
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
		return canAttachBehind(pLevel, pPos, pState.getValue(FACING)) || canAttachAbove(pLevel, pPos, Direction.UP);
	}

	private static boolean canAttachBehind(IWorldReader pReader, BlockPos pPos, Direction pDirection) {
		BlockPos blockpos = pPos.relative(pDirection);
		return pReader.getBlockState(blockpos).isFaceSturdy(pReader, blockpos, pDirection.getOpposite());
	}

	private static boolean canAttachAbove(IWorldReader pReader, BlockPos pPos, Direction pDirection) {
		return pReader.getBlockState(pPos.relative(Direction.UP)).getBlock() instanceof ExtendedRopeBlock;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		RopeType type = RopeType.MIDDLE;
		BlockPos blockpos = pContext.getClickedPos();
	    BlockState aboveState = pContext.getLevel().getBlockState(blockpos.above());
	    BlockState belowState = pContext.getLevel().getBlockState(blockpos.below());
	    FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
		if(aboveState.getBlock() instanceof ExtendedRopeBlock && belowState.getBlock() instanceof ExtendedRopeBlock) {
			// Do Nothing as default state is Middle
		}else if (aboveState.getBlock() instanceof ExtendedRopeBlock){
			type = RopeType.BOTTOM; // Since earlier checked if below was rope and didn't find any and above is, set as bottom
		}else {
			type = RopeType.TOP; // Since we are not in the middle or below rope, we must be at the top
		}
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(SECTION_TYPE, type).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}

//	/**
//	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
//	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
//	 * returns its solidified counterpart.
//	 * Note that this method should ideally consider only the specific face passed in.
//	 */
//	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
//		return getConnectedDirection(pState).getOpposite() == pFacing && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
//	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}
}