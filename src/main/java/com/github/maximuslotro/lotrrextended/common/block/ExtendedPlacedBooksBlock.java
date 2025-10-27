package com.github.maximuslotro.lotrrextended.common.block;


import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedPlaceableBookTypes;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedPlacedBooksBlock extends Block {
	public static final IntegerProperty HEIGHT = IntegerProperty.create("stack_height", 1, 3);
	public static final EnumProperty<ExtendedPlaceableBookTypes> BOOK_TYPE = EnumProperty.create("book_type", ExtendedPlaceableBookTypes.class);
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final Map<Integer, VoxelShape> SHAPES = Maps.newHashMap(ImmutableMap.of(
			1, Block.box(2.0D, 0.0D, 1.0D, 14.0D, 1.5D, 14.0D),
			2, Block.box(2.0D, 0.0D, 1.0D, 14.0D, 2.5D, 14.0D),
			3, Block.box(2.0D, 0.0D, 1.0D, 14.0D, 4.0D, 14.0D)));

	public ExtendedPlacedBooksBlock(Properties pProps) {
		super(pProps);
		this.registerDefaultState(this.stateDefinition.any().setValue(HEIGHT, 1).setValue(FACING, Direction.NORTH).setValue(BOOK_TYPE, ExtendedPlaceableBookTypes.BOOK));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(HEIGHT).add(BOOK_TYPE).add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPES.get(pState.getValue(HEIGHT));
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation p_185499_2_) {
		return pState.setValue(FACING, p_185499_2_.rotate(pState.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState pState, Mirror p_185471_2_) {
		return pState.rotate(p_185471_2_.getRotation(pState.getValue(FACING)));
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand pHand, BlockRayTraceResult p_225533_6_) {
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
}
