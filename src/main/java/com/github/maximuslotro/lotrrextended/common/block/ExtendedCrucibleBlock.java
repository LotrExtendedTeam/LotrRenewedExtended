package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedCrucibleTileEntity;

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
import net.minecraft.pathfinding.PathType;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedCrucibleBlock extends ContainerBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public ExtendedCrucibleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(FACING).add(LIT);
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
			if (tileentity instanceof ExtendedCrucibleTileEntity) {
				pPlayer.openMenu((ExtendedCrucibleTileEntity)tileentity);
				pPlayer.awardStat(ExtendedStats.INTERACT_WITH_CRUCIBLE);
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		if (pStack.hasCustomHoverName()) {
			TileEntity tileentity = pLevel.getBlockEntity(pPos);
			if (tileentity instanceof ExtendedCrucibleTileEntity) {
				((ExtendedCrucibleTileEntity)tileentity).setCustomName(pStack.getHoverName());
			}
		}

	}

	@Override
	public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (!pState.is(pNewState.getBlock())) {
			TileEntity tileentity = pLevel.getBlockEntity(pPos);
			if (tileentity instanceof ExtendedCrucibleTileEntity) {
				InventoryHelper.dropContents(pLevel, pPos, (ExtendedCrucibleTileEntity)tileentity);
				((ExtendedCrucibleTileEntity)tileentity).getRecipesToAwardAndPopExperience(pLevel, Vector3d.atCenterOf(pPos));
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
	public BlockState getStateForPlacement(BlockItemUseContext placeContext) {
		return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
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
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return false;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedCrucibleTileEntity();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState pState, World pLevel, BlockPos pPos, Random pRand) {
		if (!pState.getValue(LIT)) {
			return;
		}
		double x = pPos.getX();
		double y = pPos.getY();
		double z = pPos.getZ();
		double particleY = y + 0.8125D;

		if (pRand.nextInt(5) == 0) {
			double flameX = x + 0.375D + (pRand.nextDouble() * 0.25D);
			double flameZ = z + 0.375D + (pRand.nextDouble() * 0.25D);
			pLevel.addParticle(ParticleTypes.FLAME, flameX, particleY+0.03, flameZ, 0.0D, 0.0D, 0.0D);
		}

		if (pRand.nextInt(32) == 0) {
			double lavaX = x + 0.125D + (pRand.nextDouble() * 0.75D);
			double lavaZ = z + 0.125D + (pRand.nextDouble() * 0.75D);
			double relativeX = lavaX - x;
			double relativeZ = lavaZ - z;
			if (relativeX > 0.3125D && relativeX < 0.6875D && relativeZ > 0.3125D && relativeZ < 0.6875D) {
				// Push to the outer edge based on side
				if (pRand.nextBoolean()) {
					lavaX = (relativeX < 0.5D) ? x + 0.2D : x + 0.8D; 
				} else {
					lavaZ = (relativeZ < 0.5D) ? z + 0.2D : z + 0.8D;
				}
			}
			pLevel.addParticle(ParticleTypes.LAVA, lavaX, particleY, lavaZ, pRand.nextFloat() / 2.0F, 0.005D, pRand.nextFloat() / 2.0F);
		}

		if (pRand.nextInt(3) == 0) {
			double smokeX = x + 0.125D + (pRand.nextDouble() * 0.75D);
			double smokeZ = z + 0.125D + (pRand.nextDouble() * 0.75D);
			pLevel.addParticle(ParticleTypes.SMOKE, smokeX, particleY+0.03, smokeZ, 0.0D, 0.0D, 0.0D);
		}
	}
}
