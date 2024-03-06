package com.github.maximuslotro.lotrrextended.common.tileentity;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedStoneChestTileEntity extends ChestTileEntity{
	public ExtendedStoneChestTileEntity() {
		super(ExtendedTileEntities.STONE_CHEST.get());
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.lotrextended.stone_chest");
	}
}
