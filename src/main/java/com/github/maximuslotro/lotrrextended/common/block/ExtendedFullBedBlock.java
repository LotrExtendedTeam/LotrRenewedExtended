package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedNoRenderTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ExtendedFullBedBlock extends BedBlock {

	public ExtendedFullBedBlock(AbstractBlock.Properties p_i48442_2_) {
		super(DyeColor.WHITE, p_i48442_2_);
	}

	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.MODEL;
	}

	   public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		      return new ExtendedNoRenderTileEntity();
		   }
}
