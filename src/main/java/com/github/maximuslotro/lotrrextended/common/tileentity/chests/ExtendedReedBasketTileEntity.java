package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedTileEntities;
import net.minecraft.util.SoundEvent;
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

	@Override
	public SoundEvent BarrelOpen() {
		return ExtendedSounds.BASKET_OPEN.get();
	}

	@Override
	public SoundEvent BarrelClose() {
		return ExtendedSounds.BASKET_CLOSE.get();
	}
}
