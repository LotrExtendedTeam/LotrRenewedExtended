package com.github.maximuslotro.lotrrextended.common.block.containers;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedReedBasketTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedReedBasketBlock extends ExtendedBarrelBlock {
	protected static final VoxelShape WEST_AABB = Block.box(5.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 1.0D, 1.0D, 11.0D, 15.0D, 15.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(1.0D, 1.0D, 5.0D, 15.0D, 15.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 11.0D);
	protected static final VoxelShape UP_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D);
	protected static final VoxelShape DOWN_AABB = Block.box(1.0D, 5.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public ExtendedReedBasketBlock(AbstractBlock.Properties p_i49996_1_) {
		super(p_i49996_1_);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		if (p_220053_1_.getValue(FACING) == Direction.DOWN) {
			return DOWN_AABB;
		}
		if (p_220053_1_.getValue(FACING) == Direction.UP) {
			return UP_AABB;
		}
		if (p_220053_1_.getValue(FACING) == Direction.NORTH) {
			return NORTH_AABB;
		}
		if (p_220053_1_.getValue(FACING) == Direction.SOUTH) {
			return SOUTH_AABB;
		}
		if (p_220053_1_.getValue(FACING) == Direction.EAST) {
			return EAST_AABB;
		}
		if (p_220053_1_.getValue(FACING) == Direction.WEST) {
			return WEST_AABB;
		}
		return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 11.0D, 15.0D);
	}

	@Override
	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (p_225533_2_.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileentity = p_225533_2_.getBlockEntity(p_225533_3_);
			if (tileentity instanceof ExtendedReedBasketTileEntity) {
				p_225533_4_.openMenu((ExtendedReedBasketTileEntity) tileentity);
				p_225533_4_.awardStat(Stats.OPEN_BARREL);
				PiglinTasks.angerNearbyPiglins(p_225533_4_, true);
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
		TileEntity tileentity = p_225534_2_.getBlockEntity(p_225534_3_);
		if (tileentity instanceof ExtendedReedBasketTileEntity) {
			((ExtendedReedBasketTileEntity) tileentity).recheckOpen();
		}

	}

	@Nullable
	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedReedBasketTileEntity();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
		if (p_180633_5_.hasCustomHoverName()) {
			TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
			if (tileentity instanceof ExtendedReedBasketTileEntity) {
				((ExtendedReedBasketTileEntity) tileentity).setCustomName(p_180633_5_.getHoverName());
			}
		}
	}
}