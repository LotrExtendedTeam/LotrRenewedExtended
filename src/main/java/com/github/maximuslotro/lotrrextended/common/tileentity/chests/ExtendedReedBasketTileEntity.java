package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedTileEntities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedReedBasketTileEntity extends ExtendedBarrelTileEntity {

	public ExtendedReedBasketTileEntity() {
		super(ExtendedTileEntities.REED_BASKET.get());
	}

	@Override
	public ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.lotrextended.reed_basket");
	}

	@Override
	public void recheckOpen() {
		this.recheckOpen(ExtendedBlocks.REED_BASKET.get());
	}
}
