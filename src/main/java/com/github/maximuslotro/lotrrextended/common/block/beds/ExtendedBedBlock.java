package com.github.maximuslotro.lotrrextended.common.block.beds;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBedType;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedBedTileEntity;

import net.minecraft.block.BedBlock;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ExtendedBedBlock extends BedBlock {
	private ExtendedBedType bedType;

	public ExtendedBedBlock(ExtendedBedType pBedType, Properties pProperties) {
		super(DyeColor.WHITE, pProperties);
		this.bedType = pBedType;
	}

	public ExtendedBedType getBedType() {
		return this.bedType;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader pWorld) {
		return new ExtendedBedTileEntity(bedType);
	}
}
