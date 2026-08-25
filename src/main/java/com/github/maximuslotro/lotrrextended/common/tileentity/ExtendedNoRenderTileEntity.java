package com.github.maximuslotro.lotrrextended.common.tileentity;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;

import net.minecraft.tileentity.TileEntity;

public class ExtendedNoRenderTileEntity extends TileEntity{

	public ExtendedNoRenderTileEntity() {
		super(ExtendedTileEntities.NORENDER.get());
	}

}
