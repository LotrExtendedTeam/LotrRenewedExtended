package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.client.hiredunits.ExtendedClientHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitOrders;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class ExtendedHiredUnitContainer extends Container {
    private final IInventory container;
    private final int containerRows = 1;

    private int entityId;
    private NPCEntity companion;

    public final ExtendedClientHiredUnitProfile profile;
    
    public final ExtendedUnitOrders initialOrders;
    
    public ExtendedHiredUnitContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
        super(ExtendedContainerTypes.HIRED_UNIT_CONTAINER.get(), pContainerId);

        // read in packet data
        CompoundNBT nbt = extraData.readAnySizeNbt();
        this.entityId = nbt.getInt("entityId");
        this.profile = ExtendedClientHiredUnitProfile.fromNetwork(nbt);
        this.initialOrders = ExtendedUnitOrders.getByUnchangingIndex(nbt.getInt("orders_id"));
       
        this.companion = (NPCEntity) pPlayerInventory.player.level.getEntity(entityId);

        IInventory companionInv = ((ExtendedNPCEntity)companion).getUnitInfo().getInternalUnitInventory();

        checkContainerSize(companionInv, companionInv.getContainerSize());
        this.container = companionInv;
        companionInv.startOpen(pPlayerInventory.player);

        // add the 9 companion inventory slots
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(companionInv, k, 8 + k * 18, 110));
        }

        // add the 3 rows of player inventory
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(pPlayerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 142 + l * 18));
            }
        }

        // add the player's hotbar
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(pPlayerInventory, i1, 8 + i1 * 18, 200));
        }
    }

    public int getEntityId() {
        return entityId;
    }

    @Override
	public boolean stillValid(PlayerEntity p_39242_) {
        return this.container.stillValid(p_39242_);
    }

    @Override
	public ItemStack quickMoveStack(PlayerEntity player, int p_39254_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39254_);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (p_39254_ < this.containerRows * 9) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
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
	public void removed(PlayerEntity p_39251_) {
        super.removed(p_39251_);
        this.container.stopOpen(p_39251_);
    }

    public int getRowCount() {
        return this.containerRows;
    }

    public NPCEntity getCompanion() {
    	return companion;
    }

    public static void writeContainerInitData(PacketBuffer extraData, int entityId, ExtendedServerHiredUnitProfile serverUnitProfile, ExtendedUnitOrders currentOrders) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("entityId", entityId); // write the entity Id
        ExtendedClientHiredUnitProfile.toNetwork(serverUnitProfile, nbt); // Write server unit profile to client
        nbt.putInt("orders_id", currentOrders.getUnchangingIndex()); // write initial entity orders
        extraData.writeNbt(nbt);
    }
}
