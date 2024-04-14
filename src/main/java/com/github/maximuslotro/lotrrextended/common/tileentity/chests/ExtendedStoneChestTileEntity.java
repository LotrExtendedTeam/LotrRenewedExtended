package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedStoneChestTileEntity extends ExtendedChestTileEntity<ExtendedStoneChestTileEntity> {
	public ExtendedStoneChestTileEntity() {
		super(ExtendedTileEntities.STONE_CHEST.get());
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.lotrextended.stone_chest");
	}
}
