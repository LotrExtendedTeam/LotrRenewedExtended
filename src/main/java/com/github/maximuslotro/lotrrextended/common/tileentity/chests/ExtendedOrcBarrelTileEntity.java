package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedTileEntities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedOrcBarrelTileEntity extends ExtendedBarrelTileEntity {

	public ExtendedOrcBarrelTileEntity() {
		super(ExtendedTileEntities.ORC_BARREL.get());
	}

	@Override
	public ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.lotrextended.orc_barrel");
	}

	@Override
	public void recheckOpen() {
		this.recheckOpen(ExtendedBlocks.ORC_BARREL.get());
	}
}
