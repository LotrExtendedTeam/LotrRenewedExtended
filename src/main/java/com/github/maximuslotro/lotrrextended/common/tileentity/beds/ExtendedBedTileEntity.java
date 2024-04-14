package com.github.maximuslotro.lotrrextended.common.tileentity.beds;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ExtendedBedTileEntity<T extends TileEntity> extends TileEntity {
	public ExtendedBedTileEntity(TileEntityType<T> tileEntityType) {
		super(tileEntityType);
	}
}