package com.github.maximuslotro.lotrrextended.common.tileentity;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class ExtendedWeaponRackTileEntity extends TileEntity {
	private static final String tagName = "extended_ditem";
	private ItemStack containedItem;
	public ExtendedWeaponRackTileEntity() {
		super(ExtendedTileEntities.WEAPON_RACK.get());
		this.containedItem = ItemStack.EMPTY;
	}

	@Override
	public void setChanged() {
		super.setChanged();
	}

	@Override
	public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
		if (p_230337_2_.contains(tagName, NBT.TAG_COMPOUND)) {
			this.containedItem = ItemStack.of(p_230337_2_.getCompound(tagName));
			if (containedItem == null) {
				ExtendedLog.warn("Weapon Rack Loaded with a null Item, this should not happen. All Contained Items Have been lost!");
				containedItem = ItemStack.EMPTY;
				this.markAndUpdate();
			}
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT p_189515_1_) {
		super.save(p_189515_1_);
		if (containedItem == null) {
			ExtendedLog.warn("Weapon Rack failed to save as contained item is NULL! This should not happen. All Contained Items Have been lost!");
			containedItem = ItemStack.EMPTY;
			this.markAndUpdate();
		}
		p_189515_1_.put(tagName, this.containedItem.save(new CompoundNBT()));
		return p_189515_1_;
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 12, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		this.load(level.getBlockState(pkt.getPos()), pkt.getTag());
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		super.handleUpdateTag(state, tag);
	}

	@Override
	public boolean triggerEvent(int id, int type) {
		return super.triggerEvent(id, type);
	}

	public void markAndUpdate() {
		this.setChanged();
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public boolean hasItem() {
		if (containedItem != null && !containedItem.isEmpty()) {
			return true;
		}
		return false;
	}
	public ItemStack getContainedItem() {
		if (containedItem == null) {
			ExtendedLog.warn("Weapon Rack contains a null Item, this should not happen. All Contained Items Have been lost!");
			containedItem = ItemStack.EMPTY;
			this.markAndUpdate();
		}
		return containedItem;
	}

	public void setContainedItem(ItemStack containedItem) {
		this.containedItem = containedItem;
		if (containedItem == null) {
			ExtendedLog.warn("Tried Setting a NULL item to a Weapon Rack, correcting to ItemStack.EMPTY!");
			containedItem = ItemStack.EMPTY;
		}
		this.markAndUpdate();
	}
}
