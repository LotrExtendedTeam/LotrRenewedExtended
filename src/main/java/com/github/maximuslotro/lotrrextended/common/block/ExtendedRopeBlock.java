package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.RopeType;
import com.github.maximuslotro.lotrrextended.common.state.properties.ExtendedBlockStateProperties;

import lotr.common.block.LOTRBlockStates;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedRopeBlock extends Block implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<RopeType> SECTION_TYPE = ExtendedBlockStateProperties.ROPE_TYPE;
	protected static final VoxelShape NORTH_AABB = Block.box(4.0D, 0.0D, 13.0D, 12.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 3.0D);
	protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 4.0D, 3.0D, 16.0D, 12.0D);

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

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand pHand, BlockRayTraceResult p_225533_6_) {
		ItemStack itemstack = player.getItemInHand(pHand);
		if(itemstack.getItem()!=ExtendedItems.ROPE.get()) {
			return ActionResultType.PASS;
		}
		Direction pickedDirection = Direction.NORTH;
		Direction[] adirection = Direction.orderedByNearest(player);
		for(Direction direction : adirection) {
			if (direction.getAxis().isVertical()) {
				pickedDirection = direction;
				break;
			}
		}
		if(!pickedDirection.getAxis().isVertical()) {
			return ActionResultType.PASS;
		}
		int yValue = -1;
		boolean highorlow = true;
		BlockState chosenBlockState = null;
		if(pickedDirection==Direction.UP) {
			BlockState lastBlockState = blockState;
			for(int y = pos.getY(); y<=255; y++) {
				BlockPos localPos = new BlockPos(pos.getX(), y, pos.getZ());
				BlockState emptyBlock = world.getBlockState(localPos);
				if(emptyBlock.getBlock() instanceof ExtendedRopeBlock) {
					lastBlockState = emptyBlock;
					continue;
				}else if(emptyBlock.isAir()) {
					yValue = y;
					chosenBlockState = lastBlockState;
					highorlow=true;
					break;
				}else {
					return ActionResultType.PASS;
				}
			}
		}else if(pickedDirection==Direction.DOWN) {
			BlockState lastBlockState = blockState;
			for(int y = pos.getY(); y>=0; y--) {
				BlockPos localPos = new BlockPos(pos.getX(), y, pos.getZ());
				BlockState emptyBlock = world.getBlockState(localPos);
				if(emptyBlock.getBlock() instanceof ExtendedRopeBlock) {
					lastBlockState = emptyBlock;
					continue;
				}else if(emptyBlock.isAir()) {
					yValue = y;
					chosenBlockState = lastBlockState;
					highorlow=false;
					break;
				}else {
					return ActionResultType.PASS;
				}
			}
		}else {
			return ActionResultType.PASS;
		}
		if(yValue!=-1 && chosenBlockState!=null) {
			Direction dir = chosenBlockState.getValue(FACING);
			RopeType type = chosenBlockState.getValue(SECTION_TYPE);
			if (type == RopeType.TOP_SMALL && highorlow) {
				type = RopeType.TOP_LARGE;
			}else if (type == RopeType.SINGLE && highorlow) {
				type = RopeType.TOP_SMALL;
			}else if (type == RopeType.SINGLE && !highorlow) {
				type = RopeType.BOTTOM_SMALL;
			}else if (type == RopeType.BOTTOM_SMALL && !highorlow) {
				type = RopeType.BOTTOM_LARGE;
			}
			BlockState newRopeBlock = ExtendedBlocks.ROPE.get().defaultBlockState().setValue(FACING, dir).setValue(SECTION_TYPE, type);
			BlockPos newBlockPos = new BlockPos(pos.getX(), yValue, pos.getZ());
			if(newRopeBlock.canSurvive(world, newBlockPos)) {
				world.setBlockAndUpdate(newBlockPos, newRopeBlock);
				updateShape(chosenBlockState, pickedDirection, newRopeBlock, world, pos, newBlockPos, true);
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
				}
				return ActionResultType.sidedSuccess(world.isClientSide);
			}
			return ActionResultType.PASS;
		}
		return ActionResultType.PASS;
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
		Direction ropeFacingDirection = pState.getValue(FACING);
		return canAttachTo(pLevel, pPos.relative(ropeFacingDirection.getOpposite()), ropeFacingDirection) || canAttachAbove(pLevel, pPos, ropeFacingDirection);
	}

	private static boolean canAttachAbove(IWorldReader pReader, BlockPos pPos, Direction ropeFacingDirection) {
		BlockState above = pReader.getBlockState(pPos.relative(Direction.UP));
		return above.getBlock() instanceof ExtendedRopeBlock && above.getValue(FACING) == ropeFacingDirection;
	}

	private static boolean canAttachTo(IBlockReader pBlockReader, BlockPos pPos, Direction pDirection) {
		BlockState blockstate = pBlockReader.getBlockState(pPos);
		if(blockstate.isFaceSturdy(pBlockReader, pPos, pDirection)){
			return true;
		}
		if(blockstate.hasProperty(SlabBlock.TYPE) && blockstate.getValue(SlabBlock.TYPE)!=SlabType.BOTTOM) {
			if(blockstate.hasProperty(LOTRBlockStates.SLAB_AXIS) && blockstate.getValue(LOTRBlockStates.SLAB_AXIS)==Axis.Y) {
				if(blockstate.getValue(LOTRBlockStates.SLAB_AXIS)==Axis.Y) {
					return true;
				}
			}else {
				return true;
			}
		}
		if(blockstate.getBlock() instanceof FenceBlock || blockstate.getBlock() instanceof WallBlock) {
			return true;
		}
		return false;
	}

	/**
	 * When editing this method, ensure to check for loopable code
	 * */
	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return this.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos, false);
	}

	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos, boolean isPlacingRiskyUpdates) {
		if (pFacing.getOpposite() == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			// Actively update rope based upon chance in higher rope and this rope's current state
			if (pFacing == Direction.UP) {
				RopeType thisRopeType = pState.getValue(SECTION_TYPE);
				if (pFacingState.getBlock() instanceof ExtendedRopeBlock) {
					RopeType aboveRopeType = pFacingState.getValue(SECTION_TYPE);
					if (thisRopeType == RopeType.SINGLE && aboveRopeType == RopeType.TOP_SMALL) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_SMALL);
					}
					if (thisRopeType == RopeType.SINGLE && aboveRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_LARGE);
					}
					if ((thisRopeType == RopeType.TOP_LARGE && aboveRopeType == RopeType.TOP_LARGE) || (thisRopeType == RopeType.TOP_LARGE && aboveRopeType == RopeType.MIDDLE_LARGE)) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}
					if (thisRopeType == RopeType.TOP_SMALL && aboveRopeType == RopeType.TOP_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}
					if (thisRopeType == RopeType.TOP_SMALL && aboveRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}
					if (thisRopeType == RopeType.BOTTOM_SMALL && aboveRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_LARGE);
					}
					if (thisRopeType == RopeType.BOTTOM_LARGE && aboveRopeType == RopeType.TOP_SMALL) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_SMALL);
					}
				} else {
					// Ensure rope dies if above rope is gone and this rope can't be supported
					if (!pState.canSurvive(pLevel, pCurrentPos)) {
						return Blocks.AIR.defaultBlockState();
					}
					if (thisRopeType == RopeType.BOTTOM_SMALL || thisRopeType == RopeType.BOTTOM_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.SINGLE);
					}
					if (thisRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.TOP_LARGE);
					}
				}
			}
			if (pFacing == Direction.DOWN) {
				RopeType thisRopeType = pState.getValue(SECTION_TYPE);
				if (pFacingState.getBlock() instanceof ExtendedRopeBlock) {
					RopeType belowRopeType = pFacingState.getValue(SECTION_TYPE);
					// Only Update when rope is of type SINGLE or is at the Bottom
					if (thisRopeType == RopeType.SINGLE && belowRopeType == RopeType.BOTTOM_SMALL) {
						return pState.setValue(SECTION_TYPE, RopeType.TOP_SMALL);
					}
					if (thisRopeType == RopeType.SINGLE && belowRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.TOP_LARGE);
					}
					if ((thisRopeType == RopeType.BOTTOM_SMALL && belowRopeType == RopeType.MIDDLE_LARGE) || (thisRopeType == RopeType.BOTTOM_SMALL && belowRopeType == RopeType.BOTTOM_LARGE)) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}
					if (thisRopeType == RopeType.BOTTOM_LARGE && belowRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}
					if (thisRopeType == RopeType.BOTTOM_LARGE && belowRopeType == RopeType.BOTTOM_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.MIDDLE_LARGE);
					}
				}else {
					// Make sure not to send update upstream actively, only act upon the current state knowing no lower rope
					if (thisRopeType == RopeType.MIDDLE_LARGE) {
						return pState.setValue(SECTION_TYPE, RopeType.BOTTOM_LARGE);
					}
					if (thisRopeType == RopeType.TOP_LARGE || thisRopeType == RopeType.TOP_SMALL) {
						return pState.setValue(SECTION_TYPE, RopeType.SINGLE);
					}
				}
			}
			if (pState.getValue(WATERLOGGED)) {
				pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
			}
			return pState;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		RopeType type;
		Direction pickedDirection = Direction.NORTH;
		IWorldReader iworldreader = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();

		Direction[] adirection = pContext.getNearestLookingDirections();
		BlockState blockstate = this.defaultBlockState();
		for(Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.setValue(FACING, direction1);
				if (blockstate.canSurvive(iworldreader, blockpos)) {
					pickedDirection = direction1;
					break;
				}
			}
		}
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
			}else if(aboveType==RopeType.TOP_SMALL) {
				type = RopeType.BOTTOM_SMALL;
			}else if(aboveType==RopeType.TOP_LARGE) {
				type = RopeType.BOTTOM_SMALL;
			}else if(aboveType==RopeType.MIDDLE_LARGE) {
				type = RopeType.BOTTOM_LARGE;
			}else {
				type = RopeType.MIDDLE_LARGE;
				ExtendedLog.info("Rope Model Type Encountered an unknown combo upstream. Please report this! "+aboveType.getSerializedName());
			}
		}else if (belowState.getBlock() instanceof ExtendedRopeBlock) {
			RopeType belowType = belowState.getValue(SECTION_TYPE);
			if(belowType==RopeType.TOP_LARGE) {
				type = RopeType.TOP_LARGE;
			}else if(belowType==RopeType.TOP_SMALL) {
				type = RopeType.TOP_LARGE;
			}else if(belowType==RopeType.SINGLE) {
				type = RopeType.TOP_SMALL;
			}else if(belowType==RopeType.MIDDLE_LARGE) {
				type = RopeType.TOP_LARGE;
			}else if(belowType==RopeType.BOTTOM_SMALL) {
				type = RopeType.TOP_SMALL;
			}else if(belowType==RopeType.BOTTOM_LARGE) {
				type = RopeType.TOP_SMALL;
			}else {
				type = RopeType.SINGLE;
				ExtendedLog.info("Rope Model Type Encountered an unknown combo downstream. Please report this! "+belowType.getSerializedName());
			}
		}else {
			type = RopeType.SINGLE; // Since we are not in the middle or below rope, we must be a single rat
		}
		return this.defaultBlockState().setValue(FACING, pickedDirection).setValue(SECTION_TYPE, type).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}

	@Override
	public boolean makesOpenTrapdoorAboveClimbable(BlockState state, IWorldReader world, BlockPos pos, BlockState trapdoorState) {
		return state.getBlock() instanceof ExtendedRopeBlock && state.getValue(ExtendedRopeBlock.FACING) == trapdoorState.getValue(HorizontalBlock.FACING);
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}
}