package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMillstoneTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedMillstoneBlock extends ContainerBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public ExtendedMillstoneBlock(AbstractBlock.Properties p_i50000_1_) {
		super(p_i50000_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)));
	}

	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		if (pLevel.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			this.openContainer(pLevel, pPos, pPlayer);
			return ActionResultType.CONSUME;
		}
	}

	private void openContainer(World pLevel, BlockPos pPos, PlayerEntity pPlayer) {
		TileEntity tileentity = pLevel.getBlockEntity(pPos);
	      if (tileentity instanceof ExtendedMillstoneTileEntity) {
	         //pPlayer.openMenu((INamedContainerProvider)tileentity);
	         pPlayer.awardStat(ExtendedStats.INTERACT_WITH_MILLSTONE);
	      }
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(POWERED, Boolean.valueOf(pContext.getLevel().hasNeighborSignal(pContext.getClickedPos())));
	}

	@Override
	public void neighborChanged(BlockState pState, World pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
		if (!pLevel.isClientSide) {
			boolean flag = pState.getValue(POWERED);
			if (flag != pLevel.hasNeighborSignal(pPos)) {
				if (flag) {
					pLevel.getBlockTicks().scheduleTick(pPos, this, 4);
				} else {
					pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
				}
			}

		}
	}

	@Override
	public void tick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRand) {
		if (pState.getValue(POWERED) && !pLevel.hasNeighborSignal(pPos)) {
			pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
		}
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	 public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		 if (pStack.hasCustomHoverName()) {
			 TileEntity tileentity = pLevel.getBlockEntity(pPos);
			 if (tileentity instanceof ExtendedMillstoneTileEntity) {
				 ((ExtendedMillstoneTileEntity)tileentity).setCustomName(pStack.getHoverName());
			 }
		 }

	 }

	 @SuppressWarnings("deprecation")
	 public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		 if (!pState.is(pNewState.getBlock())) {
			 TileEntity tileentity = pLevel.getBlockEntity(pPos);
			 if (tileentity instanceof ExtendedMillstoneTileEntity) {
				 InventoryHelper.dropContents(pLevel, pPos, (ExtendedMillstoneTileEntity)tileentity);
				 ((ExtendedMillstoneTileEntity)tileentity).getRecipesToAwardAndPopExperience(pLevel, Vector3d.atCenterOf(pPos));
				 pLevel.updateNeighbourForOutputSignal(pPos, this);
			 }

			 super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		 }
	 }

	 public boolean hasAnalogOutputSignal(BlockState pState) {
		 return true;
	 }

	 public int getAnalogOutputSignal(BlockState pBlockState, World pLevel, BlockPos pPos) {
		 return Container.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
	 }

	 public BlockRenderType getRenderShape(BlockState pState) {
		 return BlockRenderType.MODEL;
	 }

	 public BlockState rotate(BlockState pState, Rotation pRotation) {
		 return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
	 }

	 @SuppressWarnings("deprecation")
	 public BlockState mirror(BlockState pState, Mirror pMirror) {
		 return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	 }

	 protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		 pBuilder.add(FACING, POWERED);
	 }

	 @Override
	 public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		 return new ExtendedMillstoneTileEntity();
	 }
}