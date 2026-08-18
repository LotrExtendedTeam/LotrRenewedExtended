package com.github.maximuslotro.lotrrextended.common.tileentity;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTileEntities;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ExtendedGrassVoidTileEntity extends TileEntity implements ITickableTileEntity{
	private int tickCount = 0;

	public ExtendedGrassVoidTileEntity() {
		super(ExtendedTileEntities.GRASS_VOID.get());
	}

	@Override
	public void tick() {
		if (!level.isClientSide &&!ExtendedServerConfig.enableDevMode.get()) {
			if (tickCount >=100) {
				level.removeBlock(worldPosition, false);
				//level.destroyBlock(worldPosition, false);
			}else {
				tickCount+=1;
			}
		}
	}
}
