package com.github.maximuslotro.lotrrextended.common.block.beds;

import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedDwarvenBedTileEntity;

import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ExtendedDwarvenBedBlock extends ExtendedBedBlock{

	public ExtendedDwarvenBedBlock(Properties properties) {
		super(DyeColor.WHITE, properties);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedDwarvenBedTileEntity();
	}
}
