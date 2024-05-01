package com.github.maximuslotro.lotrrextended.common.block.beds;

import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedSilverFurBedTileEntity;

import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ExtendedSilverFurBedBlock extends ExtendedBedBlock{

	public ExtendedSilverFurBedBlock(Properties properties) {
		super(DyeColor.WHITE, properties);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedSilverFurBedTileEntity();
	}
}
