package com.github.maximuslotro.lotrrextended.common.block.containers;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.tileentity.chests.ExtendedOrcBarrelTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExtendedOrcBarrelBlock extends ExtendedBarrelBlock {
	public ExtendedOrcBarrelBlock(AbstractBlock.Properties p_i49996_1_) {
		super(p_i49996_1_);
	}

	@Override
	public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
		if (p_225533_2_.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileentity = p_225533_2_.getBlockEntity(p_225533_3_);
			if (tileentity instanceof ExtendedOrcBarrelTileEntity) {
				p_225533_4_.openMenu((ExtendedOrcBarrelTileEntity) tileentity);
				p_225533_4_.awardStat(Stats.OPEN_BARREL);
				PiglinTasks.angerNearbyPiglins(p_225533_4_, true);
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
		TileEntity tileentity = p_225534_2_.getBlockEntity(p_225534_3_);
		if (tileentity instanceof ExtendedOrcBarrelTileEntity) {
			((ExtendedOrcBarrelTileEntity) tileentity).recheckOpen();
		}

	}

	@Nullable
	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedOrcBarrelTileEntity();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
		if (p_180633_5_.hasCustomHoverName()) {
			TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
			if (tileentity instanceof ExtendedOrcBarrelTileEntity) {
				((ExtendedOrcBarrelTileEntity) tileentity).setCustomName(p_180633_5_.getHoverName());
			}
		}
	}
}