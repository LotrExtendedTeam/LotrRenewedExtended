package com.github.maximuslotro.lotrrextended.common.block.beds;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedBlackFurBedTileEntity;

import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ExtendedBlackFurBedBlock extends ExtendedBedBlock{

	public ExtendedBlackFurBedBlock(Properties properties) {
		super(DyeColor.WHITE, properties);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedBlackFurBedTileEntity();
	}
}
