package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager;
import com.github.maximuslotro.lotrrextended.common.bannerprotection.ExtendedBannerProtectionManager.SingleProtectionInstance;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerPermissions;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBannerTypes;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSBannerOpenScreenPacket;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEBannerTileEntity;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedMEBannerBlock extends Block implements IWaterLoggable{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty SMALL = BlockStateProperties.SHORT;
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private final ExtendedBannerTypes type;
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
		Direction.NORTH, Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D),
		Direction.SOUTH, Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D),
		Direction.WEST, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D),
		Direction.EAST, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D)));

	public ExtendedMEBannerBlock(ExtendedBannerTypes bannerType, AbstractBlock.Properties p_i48453_2_) {
		super(p_i48453_2_);
		type = bannerType;
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(SMALL, false).setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
	}

	public ExtendedMEBannerBlock(ExtendedBannerTypes bannerType) {
		this(bannerType, AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).noOcclusion());
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		if(pState.getValue(HALF)==DoubleBlockHalf.LOWER) {
			return BlockRenderType.INVISIBLE;
		}
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader world, BlockPos pos) {
		if (pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			boolean isPresent = pState.getBlock() == this;
			if (!isPresent) {
				return true;
			} else {
				BlockState belowState = world.getBlockState(pos.below());
				return belowState.getBlock() == this && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
			}
		} else {
			return canSupportCenter(world, pos.below(), Direction.UP);
		}
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		world.setBlock(pos.above(), defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)
			.setValue(SMALL, state.getValue(SMALL))
			.setValue(FACING, state.getValue(FACING)), 3);
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof ExtendedMEBannerTileEntity && stack.getItem() instanceof ExtendedMCBannerItem) {
			if(placer instanceof PlayerEntity) {
				if (stack.getTag()!=null && stack.getTag().contains("ProtectionData", 10)) {
					((ExtendedMEBannerTileEntity) tileentity).loadProtectionData(stack.getTag());
				}else {
					((ExtendedMEBannerTileEntity) tileentity).setPlacedPlayerNameAndID(placer.getUUID(), ((PlayerEntity)placer).getGameProfile().getName());
				}
			}
		}
	}

	public ExtendedBannerTypes getBanner() {
		return this.type;
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPES.get(pState.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.defaultBlockState();
		World iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = iworldreader.getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		blockstate.setValue(WATERLOGGED, flag);
		Direction[] adirection = context.getNearestLookingDirections();
		for (Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.setValue(FACING, direction1);
				if (blockstate.canSurvive(iworldreader, blockpos)) {
					if(blockpos.getY() < context.getLevel().dimensionType().logicalHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
						return blockstate;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState thisState, Direction updateDirection, BlockState p_196271_3_, IWorld pLevel, BlockPos pCurrentPos, BlockPos p_196271_6_) {
		if (thisState.getValue(WATERLOGGED)) {
			pLevel.getLiquidTicks().scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}
		DoubleBlockHalf half = thisState.getValue(HALF);
		if (updateDirection.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (updateDirection == Direction.UP)) {
			if(p_196271_3_.is(this) && p_196271_3_.getValue(HALF) != half) {
				return thisState.setValue(FACING, p_196271_3_.getValue(FACING)).setValue(SMALL, p_196271_3_.getValue(SMALL));
			}else {
				return Blocks.AIR.defaultBlockState();
			}
		} else {
			if(half == DoubleBlockHalf.LOWER && updateDirection == Direction.DOWN && !thisState.canSurvive(pLevel, pCurrentPos)) {
				return Blocks.AIR.defaultBlockState();
			}else {
				return super.updateShape(thisState, updateDirection, p_196271_3_, pLevel, pCurrentPos, p_196271_6_);
			}
		}
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
	 * this block
	 */
	@Override
	public void playerWillDestroy(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
		if (!pLevel.isClientSide && pPlayer.isCreative()) {
			DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
			if (doubleblockhalf == DoubleBlockHalf.UPPER) {
				BlockPos blockpos = pPos.below();
				BlockState blockstate = pLevel.getBlockState(blockpos);
				if (blockstate.getBlock() == pState.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
					pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
					pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
				}
			}
		}

		super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
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
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING).add(HALF).add(SMALL).add(WATERLOGGED);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, PlacementType type, EntityType<?> entityType) {
		return false;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExtendedMEBannerTileEntity();
	}

	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = world.getBlockEntity(pos);
		return tileentity instanceof ExtendedMEBannerTileEntity ? ((ExtendedMEBannerTileEntity) tileentity).getItem(type) : new ItemStack(this);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if(player.isCrouching()) {
			state = state.cycle(SMALL);
			world.setBlock(blockPos, state, 10);
			world.playSound(null, blockPos, SoundEvents.WOOL_STEP, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			return ActionResultType.sidedSuccess(world.isClientSide);
		}
		if(!world.isClientSide) {
			BlockPos blockPosBottom = blockPos;
			DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
			if (doubleblockhalf == DoubleBlockHalf.UPPER) {
				blockPosBottom = blockPos.below();
			}
			TileEntity tileentity = world.getBlockEntity(blockPosBottom);
			if (tileentity instanceof ExtendedMEBannerTileEntity && ((ExtendedMEBannerTileEntity) tileentity).isProtecting()) {
				if(!ExtendedServerConfig.enableBannerProtection.get()) {
					ExtendedSBannerOpenScreenPacket packet = new ExtendedSBannerOpenScreenPacket(false);
					ExtendedPacketHandler.sendToClient(packet, (ServerPlayerEntity) player);
					return ActionResultType.sidedSuccess(world.isClientSide);
				}
				if(ExtendedBannerProtectionManager.getProtectionStatus(world, blockPosBottom, new SingleProtectionInstance.PlayerProtection(player, ExtendedBannerPermissions.BANNER)).isAllowed()) {
					boolean clientOP = ServerLifecycleHooks.getCurrentServer().getPlayerList().isOp(player.getGameProfile());
					ExtendedSBannerOpenScreenPacket packet = new ExtendedSBannerOpenScreenPacket((ExtendedMEBannerTileEntity)tileentity, clientOP);
					ExtendedPacketHandler.sendToClient(packet, (ServerPlayerEntity) player);
					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}
		return ActionResultType.PASS;
	}
}