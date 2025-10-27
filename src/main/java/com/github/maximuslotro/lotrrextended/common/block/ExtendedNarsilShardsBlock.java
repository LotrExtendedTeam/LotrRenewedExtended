package com.github.maximuslotro.lotrrextended.common.block;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedNarsilShardsBlock extends HorizontalBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty LARGE_MODEL = BooleanProperty.create("large");

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	public ExtendedNarsilShardsBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LARGE_MODEL, false).setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext placeContext) {
		return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getCounterClockWise())
			.setValue(LARGE_MODEL, placeContext.getPlayer() != null && placeContext.getPlayer().isCrouching());
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pStateBuilder) {
		pStateBuilder.add(FACING).add(LARGE_MODEL);
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return SHAPE;
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
		return below.isFaceSturdy(pLevel, blockpos, Direction.UP) && !below.is(ExtendedBlocks.SHARDS_OF_NARSIL.get());
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if(player.isCrouching()) {
			state = state.cycle(LARGE_MODEL);
			world.setBlock(blockPos, state, 10);
			world.playSound(null, blockPos, SoundEvents.METAL_HIT, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
		return ActionResultType.PASS;
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
}
