package com.github.maximuslotro.lotrrextended.common.tileentity.chests;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedStoneChestTileEntity extends ExtendedChestTileEntity<ExtendedStoneChestTileEntity> {
	private boolean hasSpawnedWight = false;
	
	public ExtendedStoneChestTileEntity() {
		super(ExtendedTileEntities.STONE_CHEST.get());
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
		hasSpawnedWight = p_230337_2_.getBoolean("hasSpawnedWight");
	}

	@Override
	public CompoundNBT save(CompoundNBT pCompound) {
		super.save(pCompound);
		pCompound.putBoolean("hasSpawnedWight", hasSpawnedWight);
		return pCompound;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.lotrextended.stone_chest");
	}

	@Override
	public SoundEvent openSound() {
		return ExtendedSounds.STONE_CHEST_OPEN.get();
	}

	@Override
	public SoundEvent closeCound() {
		return ExtendedSounds.STONE_CHEST_CLOSE.get();
	}

	public boolean hasSpawnedWight() {
		return hasSpawnedWight;
	}

	public void setHasSpawnedWight(boolean hasSpawnedWight) {
		this.hasSpawnedWight = hasSpawnedWight;
	}
}
