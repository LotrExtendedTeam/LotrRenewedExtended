package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneGrinderTileEntity;

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
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedStoneGrinderBlock extends ContainerBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);

	public ExtendedStoneGrinderBlock(AbstractBlock.Properties p_i50000_1_) {
		super(p_i50000_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
		if (pLevel.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileentity = pLevel.getBlockEntity(pPos);
			if (tileentity instanceof ExtendedStoneGrinderTileEntity) {
				pPlayer.openMenu((ExtendedStoneGrinderTileEntity)tileentity);
				pPlayer.awardStat(ExtendedStats.INTERACT_WITH_MILLSTONE);
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
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
	@Override
	public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		if (pStack.hasCustomHoverName()) {
			TileEntity tileentity = pLevel.getBlockEntity(pPos);
			if (tileentity instanceof ExtendedStoneGrinderTileEntity) {
				((ExtendedStoneGrinderTileEntity)tileentity).setCustomName(pStack.getHoverName());
			}
		}

	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (!pState.is(pNewState.getBlock())) {
			TileEntity tileentity = pLevel.getBlockEntity(pPos);
			if (tileentity instanceof ExtendedStoneGrinderTileEntity) {
				InventoryHelper.dropContents(pLevel, pPos, (ExtendedStoneGrinderTileEntity)tileentity);
				((ExtendedStoneGrinderTileEntity)tileentity).getRecipesToAwardAndPopExperience(pLevel, Vector3d.atCenterOf(pPos));
				pLevel.updateNeighbourForOutputSignal(pPos, this);
			}

			super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState pBlockState, World pLevel, BlockPos pPos) {
		return Container.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.MODEL;
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

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, POWERED);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedStoneGrinderTileEntity();
	}

	/**
	 * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	 * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	 * of whether the block can receive random update ticks
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState pState, World pLevel, BlockPos pPos, Random pRand) {
		if (pState.getValue(POWERED)) {
			double dX = pPos.getX() + 0.5D;
			double dY = pPos.getY();
			double dZ = pPos.getZ() + 0.5D;
			if (pRand.nextDouble() < 0.2D) {
				pLevel.playLocalSound(dX, dY, dZ, ExtendedSounds.MILLSTONE_GRINDING.get(), SoundCategory.BLOCKS, 0.5F, 0.8F, false);
			}
			pLevel.addParticle(ParticleTypes.SMOKE, dX , dY + 0.6D, dZ , 0.0D, 0.0D, 0.0D);
		}
	}
}