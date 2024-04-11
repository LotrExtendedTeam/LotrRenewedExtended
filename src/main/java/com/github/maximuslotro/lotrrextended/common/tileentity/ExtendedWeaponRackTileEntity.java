package com.github.maximuslotro.lotrrextended.common.tileentity;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class ExtendedWeaponRackTileEntity extends TileEntity{
	private ItemStack containedItem;
	public ExtendedWeaponRackTileEntity() {
		super(ExtendedTileEntities.WEAPON_RACK.get());
		this.containedItem = ItemStack.EMPTY;
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
		if (p_230337_2_.contains("extended_ditem", NBT.TAG_COMPOUND)) {
            this.containedItem = ItemStack.of(p_230337_2_.getCompound("displayed_item"));
            if(containedItem==null) {
            	containedItem = ItemStack.EMPTY;
            }
        }
	}

	@Override
	public CompoundNBT save(CompoundNBT p_189515_1_) {
		super.save(p_189515_1_);
		if(containedItem==null) {
        	containedItem = ItemStack.EMPTY;
        }
		p_189515_1_.put("extended_ditem", this.containedItem.save(new CompoundNBT()));
        return p_189515_1_;
	}

	public ItemStack getContainedItem() {
		if(containedItem==null) {
        	containedItem = ItemStack.EMPTY;
        }
		return containedItem;
	}

	public void setContainedItem(ItemStack containedItem) {
		this.containedItem = containedItem;
	}
}
