package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedPlaceableBookTypes;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedPlacedBooksBlock extends Block {
	public static final EnumProperty<ExtendedPlaceableBookTypes> BOOK_TYPE1 = EnumProperty.create("book_type", ExtendedPlaceableBookTypes.class);
	public static final EnumProperty<ExtendedPlaceableBookTypes> BOOK_TYPE2 = EnumProperty.create("book_type2", ExtendedPlaceableBookTypes.class);
	public static final EnumProperty<ExtendedPlaceableBookTypes> BOOK_TYPE3 = EnumProperty.create("book_type3", ExtendedPlaceableBookTypes.class);

	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final Map<Integer, VoxelShape> SHAPES = Maps.newHashMap(ImmutableMap.of(
			1, Block.box(2.0D, 0.0D, 1.0D, 14.0D, 1.5D, 14.0D),
			2, Block.box(2.0D, 0.0D, 1.0D, 14.0D, 2.5D, 14.0D),
			3, Block.box(2.0D, 0.0D, 1.0D, 14.0D, 4.0D, 14.0D)));

	public ExtendedPlacedBooksBlock(Properties pProps) {
		super(pProps);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BOOK_TYPE1, ExtendedPlaceableBookTypes.BOOK)
			.setValue(BOOK_TYPE2, ExtendedPlaceableBookTypes.NONE).setValue(BOOK_TYPE3, ExtendedPlaceableBookTypes.NONE));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext placeContext) {
		ExtendedPlaceableBookTypes type = ExtendedPlaceableBookTypes.getByItem(placeContext.getItemInHand().getItem());
		if(type.isEmpty()) {
			return null;
		}
		return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection()).setValue(BOOK_TYPE1, type);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pStateBuilder) {
		pStateBuilder.add(BOOK_TYPE1).add(BOOK_TYPE2).add(BOOK_TYPE3).add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		if(pState.getValue(BOOK_TYPE3).notEmpty()) {
			return SHAPES.get(3);
		}else if(pState.getValue(BOOK_TYPE2).notEmpty()) {
			return SHAPES.get(2);
		}
		return SHAPES.get(1);
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation p_185499_2_) {
		return pState.setValue(FACING, p_185499_2_.rotate(pState.getValue(FACING)));
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		BlockPos blockpos = pPos.below();
		BlockState below = pLevel.getBlockState(blockpos);
		return below.isFaceSturdy(pLevel, blockpos, Direction.UP) && Block.canSupportCenter(pLevel, pPos.below(), Direction.UP);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState pState, Mirror p_185471_2_) {
		return pState.rotate(p_185471_2_.getRotation(pState.getValue(FACING)));
	}

	@Override
	public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState pNewState, boolean pIsMoving) {
		if (!blockState.is(pNewState.getBlock())) {
			if(blockState.getValue(BOOK_TYPE1).notEmpty()) {
				InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(blockState.getValue(BOOK_TYPE1).getItemType()));
			}
			if(blockState.getValue(BOOK_TYPE2).notEmpty()) {
				InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(blockState.getValue(BOOK_TYPE2).getItemType()));
			}
			if(blockState.getValue(BOOK_TYPE3).notEmpty()) {
				InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(blockState.getValue(BOOK_TYPE3).getItemType()));
			}
			world.updateNeighbourForOutputSignal(blockPos, this);
			super.onRemove(blockState, world, blockPos, pNewState, pIsMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState pState, World world, BlockPos blockPos) {
		if(pState.getValue(BOOK_TYPE3).notEmpty()) {
			return 3;
		}else if(pState.getValue(BOOK_TYPE2).notEmpty()) {
			return 2;
		}
		return 1;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand pHand, BlockRayTraceResult p_225533_6_) {
		ItemStack itemstack = player.getItemInHand(pHand);
		ExtendedPlaceableBookTypes type = ExtendedPlaceableBookTypes.getByItem(itemstack.getItem());
		if(type.notEmpty() && state.getValue(BOOK_TYPE3).isEmpty()) {
			BlockState stateNew;
			if(state.getValue(BOOK_TYPE2).isEmpty()) {
				stateNew = state.setValue(BOOK_TYPE2, type);
			}else {
				stateNew = state.setValue(BOOK_TYPE3, type);
			}
			world.setBlock(blockPos, stateNew, 10);
			if (!player.abilities.instabuild) {
				itemstack.shrink(1);
			}
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
		return super.use(state, world, blockPos, player, pHand, p_225533_6_);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		if(state.getValue(BOOK_TYPE3).notEmpty()) {
			return new ItemStack(state.getValue(BOOK_TYPE3).getItemType());
		}else if(state.getValue(BOOK_TYPE2).notEmpty()) {
			return new ItemStack(state.getValue(BOOK_TYPE2).getItemType());
		}
		return new ItemStack(state.getValue(BOOK_TYPE1).getItemType());
	}
}
