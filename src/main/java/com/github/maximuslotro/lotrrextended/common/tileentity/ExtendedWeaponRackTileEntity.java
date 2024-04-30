package com.github.maximuslotro.lotrrextended.common.tileentity;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.init.ExtendedTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class ExtendedWeaponRackTileEntity extends LockableLootTileEntity {
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
	public void load(BlockState p_230337_1_, CompoundNBT nbt) {
		super.load(p_230337_1_, nbt);
		if (!this.tryLoadLootTable(nbt)) {
			if (nbt.contains(tagName, NBT.TAG_COMPOUND)) {
				this.containedItem = ItemStack.of(nbt.getCompound(tagName));
				if (containedItem == null) {
					ExtendedLog.warn("Weapon Rack Loaded with a null Item, this should not happen. All Contained Items Have been lost!");
					containedItem = ItemStack.EMPTY;
					this.markAndUpdate();
				}
			}
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if (!this.trySaveLootTable(nbt)) {
			if (containedItem == null) {
				ExtendedLog.warn("Weapon Rack failed to save as contained item is NULL! This should not happen. All Contained Items Have been lost!");
				containedItem = ItemStack.EMPTY;
				this.markAndUpdate();
			}
			nbt.put(tagName, this.containedItem.save(new CompoundNBT()));
		}
		return nbt;
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

	@Override
	public int getContainerSize() {
		return 1;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return NonNullList.withSize(1, this.containedItem==null ? ItemStack.EMPTY : this.containedItem);
	}

	@Override
	protected void setItems(NonNullList<ItemStack> list) {
		if(list.size()>=1) {
			this.containedItem=list.get(0);
		}
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new StringTextComponent("Weapon Rack Secret Lounge");
	}

	@Override
	protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
		return null; //Return null, since weapon racks don't have an GUI
	}
}
