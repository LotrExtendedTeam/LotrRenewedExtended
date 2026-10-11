package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.client.hiredunits.ExtendedClientHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcHiredUnitInfoHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedHiredUnitOverviewContainer extends ExtendedHiredUnitContainer {
	private final IInventory container;

	public final ExtendedClientHiredUnitProfile profile;

	public final ExtendedUnitOrders initialOrders;

	public ExtendedHiredUnitOverviewContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		super(ExtendedContainerTypes.HIRED_UNIT_OVERVIEW.get(), pContainerId, pPlayerInventory, extraData);

		// read in packet data
		CompoundNBT nbt = extraData.readAnySizeNbt();
		this.profile = ExtendedClientHiredUnitProfile.fromNetwork(nbt);
		this.initialOrders = ExtendedUnitOrders.getByUnchangingIndex(nbt.getInt("orders_id"));
		ExtendedNpcHiredUnitInfoHolder unitInfo = ((ExtendedINPCEntity)getCompanion()).getUnitInfo();
		unitInfo.setUnitLevel(nbt.getInt("level"));
		unitInfo.setCurrentXp(nbt.getInt("cxp"));
		unitInfo.setMaxXp(nbt.getInt("mxp"));
		unitInfo.setMobKills(nbt.getInt("kills"));
		unitInfo.setGuardRadius(nbt.getInt("radius"));
		IInventory companionInv = unitInfo.getInternalUnitInventory();
		this.container = companionInv;
		companionInv.startOpen(pPlayerInventory.player);

		// add the 9 companion inventory slots
		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(companionInv, k, 8 + k * 18, 108));
		}

		// add the 3 rows of player inventory
		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlot(new Slot(pPlayerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 140 + l * 18));
			}
		}

		// add the player's hotbar
		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new Slot(pPlayerInventory, i1, 8 + i1 * 18, 198));
		}
	}

	@Override
	public boolean stillValid(PlayerEntity pPlayer) {
		return this.container.stillValid(pPlayer);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			if (pIndex < 9) {
				if (!this.moveItemStackTo(itemstack1, 9, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemstack;
	}

	@Override
	public void removed(PlayerEntity pPlayer) {
		super.removed(pPlayer);
		this.container.stopOpen(pPlayer);
	}

	public static void writeContainerInitData(PacketBuffer extraData, int entityId, ExtendedNpcHiredUnitInfoHolder info) {
		ExtendedHiredUnitContainer.writeContainerInitData(extraData, entityId);
		CompoundNBT nbt = new CompoundNBT();
		ExtendedClientHiredUnitProfile.toNetwork(info.getUnitProfile(), nbt); // Write server unit profile to client
		nbt.putInt("orders_id", info.getOrders().getUnchangingIndex()); // write initial entity orders
		nbt.putInt("level", info.getUnitLevel());
		nbt.putFloat("cxp", info.getCurrentXp());
		nbt.putFloat("mxp", info.getMaxXp());
		nbt.putInt("kills", info.getMobKills());
		nbt.putInt("radius", info.getGuardRadius());
		extraData.writeNbt(nbt);
	}
}
