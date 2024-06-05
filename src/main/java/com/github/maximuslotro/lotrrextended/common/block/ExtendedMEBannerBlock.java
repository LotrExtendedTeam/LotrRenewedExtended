package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.enums.BannerTypes;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedMEBannerBlock extends ContainerBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty SMALL = BlockStateProperties.SHORT;
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final Map<BannerTypes, Block> BY_BANNER = Maps.newHashMap();
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D), 
			Direction.SOUTH, Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D), 
			Direction.WEST, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D), 
			Direction.EAST, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D)));
	private final BannerTypes banner;

	public ExtendedMEBannerBlock(BannerTypes bannerType, AbstractBlock.Properties p_i48453_2_) {
		super(p_i48453_2_);
		this.banner = bannerType;
		BY_BANNER.put(bannerType, this);
		this.registerDefaultState(this.stateDefinition.any().setValue(SMALL, false).setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			boolean isPresent = state.getBlock() == this;
			if (!isPresent) {
				return true;
			} else {
				BlockState belowState = world.getBlockState(pos.below());
				return belowState.getBlock() == this && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
			}
		} else {
			return canSupportCenter(world, pos.below(), Direction.UP);
		}
		//return world.getBlockState(pos.below()).getMaterial().isSolid();
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		world.setBlock(pos.above(), defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)
				.setValue(SMALL, state.getValue(SMALL))
				.setValue(FACING, state.getValue(FACING)), 3);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPES.get(state.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.defaultBlockState();
		IWorldReader iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
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
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf half = state.getValue(HALF);
		if (facing.getAxis() != Axis.Y || half == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.getBlock() == this && facingState.getValue(HALF) != half) {
			if (half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, currentPos)) {
				return Blocks.AIR.defaultBlockState();
			}

			if (state.canSurvive(world, currentPos)) {
				return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
			}
		}
		return Blocks.AIR.defaultBlockState();
	}

	@Override
	public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
		return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
		return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING).add(HALF).add(SMALL);
	}

	public static Block byBanner(BannerTypes banner) {
		return BY_BANNER.getOrDefault(banner, Blocks.WHITE_BANNER);
	}

	public BannerTypes getBanner() {
		return this.banner;
	}

	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return null;
	}

//	@Override
//	public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
//		if (p_180633_5_.hasCustomHoverName()) {
//			TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
//			if (tileentity instanceof BannerTileEntity) {
//				((BannerTileEntity) tileentity).setCustomName(p_180633_5_.getHoverName());
//			}
//		}
//	}

//	@Override
//	public ItemStack getCloneItemStack(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
//		TileEntity tileentity = p_185473_1_.getBlockEntity(p_185473_2_);
//		return tileentity instanceof BannerTileEntity ? ((BannerTileEntity) tileentity).getItem(p_185473_3_) : super.getCloneItemStack(p_185473_1_, p_185473_2_, p_185473_3_);
//	}

//	@Override
//	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
//        Boolean isSmall = state.getValue(SMALL);
//        DoubleBlockHalf half = state.getValue(HALF);
//        world.setBlock(blockPos, state.setValue(SMALL, !isSmall), 2);
//        if(half==DoubleBlockHalf.LOWER && world.getBlockState(blockPos.above()).hasProperty(SMALL)) {
//        	world.setBlock(blockPos.above(), state.setValue(SMALL, !isSmall), 2);
//        }else if(half==DoubleBlockHalf.UPPER && world.getBlockState(blockPos.below()).hasProperty(SMALL)){
//        	world.setBlock(blockPos.below(), state.setValue(SMALL, !isSmall), 2);
//        }
//        return ActionResultType.sidedSuccess(world.isClientSide);
//     }
}