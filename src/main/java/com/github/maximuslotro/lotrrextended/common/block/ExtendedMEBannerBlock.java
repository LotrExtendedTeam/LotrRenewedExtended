package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.enums.BannerTypes;
import com.google.common.collect.Maps;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class ExtendedMEBannerBlock extends ContainerBlock {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final Map<BannerTypes, Block> BY_BANNER = Maps.newHashMap();
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private final BannerTypes banner;

	public ExtendedMEBannerBlock(BannerTypes bannerType, AbstractBlock.Properties p_i48453_2_) {
		super(p_i48453_2_);
		this.banner = bannerType;
		BY_BANNER.put(bannerType, this);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
		return p_196260_2_.getBlockState(p_196260_3_.below()).getMaterial().isSolid();
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		BlockState blockstate = this.defaultBlockState();
		IWorldReader iworldreader = p_196258_1_.getLevel();
		BlockPos blockpos = p_196258_1_.getClickedPos();
		Direction[] adirection = p_196258_1_.getNearestLookingDirections();
		for (Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.setValue(FACING, direction1);
				if (blockstate.canSurvive(iworldreader, blockpos)) {
					return blockstate;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
		return p_196271_2_ == Direction.DOWN && !p_196271_1_.canSurvive(p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
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
		p_206840_1_.add(FACING);
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
}