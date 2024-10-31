package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.common.enums.BannerTypes;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedMEHangingBannerTileEntity;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExtendedMEBannerWallBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D), Direction.SOUTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D), Direction.WEST, Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Direction.EAST, Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D)));
	private final BannerTypes type;

	public ExtendedMEBannerWallBlock(BannerTypes banner, AbstractBlock.Properties p_i48453_2_) {
		super(p_i48453_2_);
		this.type = banner;
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	public ExtendedMEBannerWallBlock(BannerTypes banner) {
		this(banner, AbstractBlock.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).noOcclusion());
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	public String getDescriptionId() {
		return this.asItem().getDescriptionId();
	}

	@Override
	public boolean isPossibleToRespawnInThis() {
		return true;
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		TileEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof ExtendedMEHangingBannerTileEntity && stack.getItem() instanceof ExtendedMCBannerItem) {
			if (stack.getTag()!=null && stack.getTag().contains("ProtectionData", 10)) {
				((ExtendedMEHangingBannerTileEntity) tileentity).loadProtectionData(stack.getTag());
			}
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = world.getBlockEntity(pos);
		return tileentity instanceof ExtendedMEHangingBannerTileEntity ? ((ExtendedMEHangingBannerTileEntity) tileentity).getItem(type) : new ItemStack(this);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ExtendedMEHangingBannerTileEntity();
	}

	@Override
	public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
		return p_196260_2_.getBlockState(p_196260_3_.relative(p_196260_1_.getValue(FACING).getOpposite())).getMaterial().isSolid();
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
		return p_196271_2_ == p_196271_1_.getValue(FACING).getOpposite() && !p_196271_1_.canSurvive(p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPES.get(state.getValue(FACING));
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

	public BannerTypes getBanner() {
		return this.type;
	}
}