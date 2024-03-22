package com.github.maximuslotro.lotrrextended.common.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class ExtendedWeaponRackTileEntity extends TileEntity{
	private ItemStack containedItem;
	// TODO please remove the null
	public ExtendedWeaponRackTileEntity() {
		super(null);
		this.containedItem= ItemStack.EMPTY;
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
		if (p_230337_2_.contains("extended_ditem", NBT.TAG_COMPOUND)) {
            this.containedItem = ItemStack.of(p_230337_2_.getCompound("displayed_item"));
        }
	}

	@Override
	public CompoundNBT save(CompoundNBT p_189515_1_) {
		super.save(p_189515_1_);
		p_189515_1_.put("extended_ditem", this.containedItem.save(new CompoundNBT()));
        return p_189515_1_;
	}
}
