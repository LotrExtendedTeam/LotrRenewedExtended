package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Map;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSOpenStoneTextSignPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneTextSignTileEntity;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.WallSignBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

/**
 * Based on {@link WallSignBlock}
 * */
public class ExtendedStoneTextSignBlock extends ContainerBlock implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D), Direction.SOUTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D), Direction.EAST, Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D), Direction.WEST, Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)));

	public ExtendedStoneTextSignBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return AABBS.get(pState.getValue(FACING));
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		return pLevel.getBlockState(pPos.relative(pState.getValue(FACING).getOpposite())).getMaterial().isSolid();
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		BlockState blockstate = this.defaultBlockState();
		FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
		IWorldReader iworldreader = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		Direction[] adirection = pContext.getNearestLookingDirections();

		for(Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.setValue(FACING, direction1);
				if (blockstate.canSurvive(iworldreader, blockpos)) {
					return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
				}
			}
		}

		return null;
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		if(pFacing.getOpposite() == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos)) {
			return Blocks.AIR.defaultBlockState();
		}
		if (pState.getValue(WATERLOGGED)) {
			pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}

		return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRotation) {
		return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, WATERLOGGED);
	}

	/**
	 * Return true if an entity can be spawned inside the block (used to get the player's bed spawn location)
	 */
	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedStoneTextSignTileEntity();
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		boolean canBuild = pPlayer.abilities.mayBuild;
		if (pLevel.isClientSide) {
			return canBuild ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
		} else {
			TileEntity tileentity = pLevel.getBlockEntity(pPos);
			if (tileentity instanceof ExtendedStoneTextSignTileEntity) {
				ExtendedStoneTextSignTileEntity signtileentity = (ExtendedStoneTextSignTileEntity)tileentity;
				if (canBuild && itemstack.getItem() instanceof DyeItem) {
					if (signtileentity.setColor(((DyeItem)itemstack.getItem()).getDyeColor())) {
						if(!pPlayer.isCreative()){
							itemstack.shrink(1);
						}
						return ActionResultType.SUCCESS;
					}
				}else if (canBuild && itemstack.getItem() instanceof ExtendedChiselItem) {
					if (signtileentity.getPlayerWhoMayEdit()==null) {
						signtileentity.setAllowedPlayerEditor(pPlayer);
						ExtendedPacketHandler.sendToClient(new ExtendedSOpenStoneTextSignPacket(((ExtendedStoneTextSignTileEntity)pLevel.getBlockEntity(pPos)).getBlockPos()), (ServerPlayerEntity) pPlayer);
						if (!pPlayer.isCreative()) {
							itemstack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pPlayer.getUsedItemHand()));
						}
						return ActionResultType.SUCCESS;
					}
				}
				return signtileentity.executeClickCommands(pPlayer) ? ActionResultType.SUCCESS : ActionResultType.PASS;
			}
			return ActionResultType.PASS;
		}
	}

	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}
}