package com.github.maximuslotro.lotrrextended.common.block;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBookshelfTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ExtendedBookshelfBlock extends ContainerBlock {

	   public ExtendedBookshelfBlock(AbstractBlock.Properties p_i49996_1_) {
	      super(p_i49996_1_);
	   }

	   @Override
	   public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
	      if (p_225533_2_.isClientSide) {
	         return ActionResultType.SUCCESS;
	      } else {
	         TileEntity tileentity = p_225533_2_.getBlockEntity(p_225533_3_);
	         if (tileentity instanceof ExtendedBookshelfTileEntity) {
	            p_225533_4_.openMenu((ExtendedBookshelfTileEntity)tileentity);
	            p_225533_4_.awardStat(ExtendedStats.OPEN_BOOKSHELF);
	         }
	         return ActionResultType.CONSUME;
	      }
	   }

	@SuppressWarnings("deprecation")
	@Override
	   public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
	      if (!p_196243_1_.is(p_196243_4_.getBlock())) {
	         TileEntity tileentity = p_196243_2_.getBlockEntity(p_196243_3_);
	         if (tileentity instanceof IInventory) {
	            InventoryHelper.dropContents(p_196243_2_, p_196243_3_, (IInventory)tileentity);
	            p_196243_2_.updateNeighbourForOutputSignal(p_196243_3_, this);
	         }

	         super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
	      }
	   }

	   @Nullable
	   @Override
	   public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
	      return new ExtendedBookshelfTileEntity();
	   }

	   @Override
	   public BlockRenderType getRenderShape(BlockState p_149645_1_) {
	      return BlockRenderType.MODEL;
	   }

	   @Override
	   public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
	      if (p_180633_5_.hasCustomHoverName()) {
	         TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
	         if (tileentity instanceof ExtendedBookshelfTileEntity) {
	            ((ExtendedBookshelfTileEntity)tileentity).setCustomName(p_180633_5_.getHoverName());
	         }
	      }

	   }

	   @Override
	   public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
	      return true;
	   }

	   @Override
	   public int getAnalogOutputSignal(BlockState p_180641_1_, World p_180641_2_, BlockPos p_180641_3_) {
	      return Container.getRedstoneSignalFromBlockEntity(p_180641_2_.getBlockEntity(p_180641_3_));
	   }
	}
