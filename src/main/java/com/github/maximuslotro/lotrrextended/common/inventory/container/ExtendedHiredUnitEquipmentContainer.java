package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.hiredunits.ExtendedClientHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedEquipmentSlotType;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfile;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentSlot.ArmorSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentSlot.OffhandSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentSlot.RangedSlot;

import lotr.common.util.ExtendedHiredUnitHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitEquipmentContainer extends ExtendedHiredUnitContainer implements IContainerListener {

	private ArmorSlot[] armorSlots = new ArmorSlot[4];
	private ExtendedHiredUnitEquipmentSlot meleeSlot;
	private OffhandSlot offhandSlot;
	private RangedSlot rangedSlot;
    public final ExtendedClientHiredUnitProfile profile;

    public static final ResourceLocation EMPTY_SLOT_BOW = new ResourceLocation(LotrExtendedMod.MODID, "item/empty_slot_bow");
    public static final ResourceLocation EMPTY_SLOT_SWORD = new ResourceLocation(LotrExtendedMod.MODID, "item/empty_slot_sword");
    public static final ResourceLocation EMPTY_SLOT_HOE = new ResourceLocation(LotrExtendedMod.MODID, "item/empty_slot_hoe");

    // TODO: Update this
    public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotrextended.hired_unit_equipment");

    private Inventory playerEquipment;
    private Inventory baseEquipment;

    public ExtendedHiredUnitEquipmentContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
        super(ExtendedContainerTypes.HIRED_UNIT_EQUIPMENT.get(), pContainerId, pPlayerInventory, extraData);

        // Read in packet data
        CompoundNBT nbt = extraData.readAnySizeNbt();
        this.profile = ExtendedClientHiredUnitProfile.fromNetwork(nbt);

        // Read equipment from NBT
        List<ItemStack> items = new ArrayList<>();
        ListNBT list = nbt.getList("equipment", 10);
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT node = list.getCompound(i);
            if (!node.isEmpty()) {
                ItemStack item = ItemStack.of(node.getCompound("item"));
                items.add(item);
            }
        }
        // Sort items into their respective inventories
        playerEquipment = new Inventory(10);
        baseEquipment = new Inventory(10);
        for(int i = 0; i<20; i++) {
        	if(i<10) {
        		playerEquipment.setItem(i, items.get(i));
        	}else{
        		baseEquipment.setItem(i-10, items.get(i));
        	}
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

        // setup 4 custom armor slots
        armorSlots[0]=(ArmorSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.ArmorSlot(playerEquipment, 0, 8, 29, ExtendedEquipmentSlotType.HEAD, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET));
        armorSlots[1]=(ArmorSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.ArmorSlot(playerEquipment, 1, 8, 47, ExtendedEquipmentSlotType.CHEST, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE));
        armorSlots[2]=(ArmorSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.ArmorSlot(playerEquipment, 2, 8, 65, ExtendedEquipmentSlotType.LEGS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS));
        armorSlots[3]=(ArmorSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.ArmorSlot(playerEquipment, 3, 8, 83, ExtendedEquipmentSlotType.FEET, PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS));

        // Setup hand slots
        if(profile.getAttackType().isFarming()) {
        	meleeSlot = (ExtendedHiredUnitEquipmentSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.FarmhandMeleeSlot(playerEquipment, 4, 62, 65, EMPTY_SLOT_HOE));
        }else {
        	meleeSlot = (ExtendedHiredUnitEquipmentSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.MeleeSlot(playerEquipment, 4, 62, 65, EMPTY_SLOT_SWORD));
        }
        if(profile.getAttackType().isRanged()) {
            rangedSlot = (RangedSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.RangedSlot(playerEquipment, 5, 62, 47, EMPTY_SLOT_BOW));
        }
        offhandSlot = (OffhandSlot)this.addSlot(new ExtendedHiredUnitEquipmentSlot.OffhandSlot(playerEquipment, 9, 62, 83, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD));
        this.addSlotListener(this);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int p_39254_) {
    	Slot slot = this.slots.get(p_39254_);

    	if(slot!=null && slot.hasItem()) {
    		// handle shift-clicking equipment and sending it back to the player's inventory
    		if(slot instanceof ExtendedHiredUnitEquipmentSlot) {
    			if (player.inventory.getFreeSlot() > -1 || player.inventory.getSlotWithRemainingSpace(slot.getItem()) > -1) {
    				player.addItem(slot.getItem());
    				slot.set(ItemStack.EMPTY);
    				slot.setChanged();
    				ExtendedHiredUnitHelper.updateEquipmentSlot(getCompanion(), ((ExtendedHiredUnitEquipmentSlot)slot).getEquipmentSlotType(), ItemStack.EMPTY);
    			} else {
    				return ItemStack.EMPTY;
    			}
    		} else {
    			// Handle shift-clicking equipment and sending it to the unit's inventory
    			ItemStack shiftingItem = slot.getItem();

    			List<ExtendedHiredUnitEquipmentSlot> equipmentslots = new ArrayList<>();
    			equipmentslots.add(armorSlots[0]);
    			equipmentslots.add(armorSlots[1]);
    			equipmentslots.add(armorSlots[2]);
    			equipmentslots.add(armorSlots[3]);
    			equipmentslots.add(meleeSlot);
    			if(rangedSlot!=null) {
        			equipmentslots.add(rangedSlot);
    			}
    			equipmentslots.add(offhandSlot);
    			for(ExtendedHiredUnitEquipmentSlot equipmentslot : equipmentslots) {
    				if(!equipmentslot.hasItem() && equipmentslot.mayPlaceShifting(shiftingItem)) {
    					equipmentslot.set(shiftingItem);
    					equipmentslot.setChanged();
    					ExtendedHiredUnitHelper.updateEquipmentSlot(getCompanion(), equipmentslot.getEquipmentSlotType(), shiftingItem);
    					slot.set(ItemStack.EMPTY);
        				slot.setChanged();
        				//player.inventory.setItem(slot.index, ItemStack.EMPTY);
        				return ItemStack.EMPTY;
    				}
    			}

    			if (shiftingItem.isEmpty()) {
    				slot.set(ItemStack.EMPTY);
    			} else {
    				slot.setChanged();
    			}
    		}
    	}

    	return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    public static void writeContainerInitData(PacketBuffer extraData, int entityId, ExtendedServerHiredUnitProfile serverUnitProfile, List<ItemStack> equipment) {
    	ExtendedHiredUnitContainer.writeContainerInitData(extraData, entityId);
    	CompoundNBT nbt = new CompoundNBT();
        ExtendedClientHiredUnitProfile.toNetwork(serverUnitProfile, nbt); // Write server unit profile to client

        // write their equipment including empty slots
        ListNBT listnbt = new ListNBT();
        for (int i = 0; i < 20; i++) {
            ItemStack stack = equipment.get(i);

            CompoundNBT compoundnbt = new CompoundNBT();
            CompoundNBT itemTag = new CompoundNBT();
            itemTag = stack.save(itemTag);
            compoundnbt.put("item", itemTag);
            listnbt.add(compoundnbt);
        }
        nbt.put("equipment", listnbt);

        extraData.writeNbt(nbt);
    }

    @Override
    public void refreshContainer(Container pContainerToSend, NonNullList<ItemStack> pItemsList) {

    }

    @Override
    public void slotChanged(Container pContainerToSend, int pSlotInd, ItemStack pStack) {
    	for(Slot slot : slots) {
    		if(slot instanceof ExtendedHiredUnitEquipmentSlot && slot.index==pSlotInd) {
    			ExtendedHiredUnitHelper.updateEquipmentSlot(getCompanion(), ((ExtendedHiredUnitEquipmentSlot)slot).getEquipmentSlotType(), pStack);
    		}
    	}
    }

    @Override
    public void setContainerData(Container pContainer, int pVarToUpdate, int pNewValue) {

    }

	public Slot[] getArmorSlots() {
		return armorSlots;
	}

	@Nullable
	public Slot getRangedSlot() {
		return rangedSlot;
	}

	public Slot getMeleeSlot() {
		return meleeSlot;
	}

	public Slot getOffhandSlot() {
		return offhandSlot;
	}

	public Inventory getBaseInventory() {
		return baseEquipment;
	}
}
