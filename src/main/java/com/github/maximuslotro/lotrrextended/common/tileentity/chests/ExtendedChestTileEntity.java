package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class ExtendedChestTileEntity<T extends ChestTileEntity> extends ChestTileEntity {

	public ExtendedChestTileEntity(TileEntityType<T> tileEntityType) {
		super(tileEntityType);
	}

}
