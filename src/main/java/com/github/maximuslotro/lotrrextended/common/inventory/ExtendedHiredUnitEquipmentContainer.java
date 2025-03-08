package com.github.maximuslotro.lotrrextended.common.inventory;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedEquipmentSlotType;
import com.github.maximuslotro.lotrrextended.common.inventory.CompanionEquipmentSlot.ArmorSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.CompanionEquipmentSlot.RangedSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.CompanionEquipmentSlot.MeleeSlot;
import com.github.maximuslotro.lotrrextended.common.inventory.CompanionEquipmentSlot.OffhandSlot;

import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.ExtendedContainers;
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

public class ExtendedHiredUnitEquipmentContainer extends Container implements IContainerListener {

	private ArmorSlot[] armorSlots = new ArmorSlot[4];
	private MeleeSlot meleeSlot;
	private OffhandSlot offhandSlot;
	private RangedSlot rangedSlot;
    public final int entityId;
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOW = new ResourceLocation("lotrextended", "item/empty_armor_slot_bow");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SWORD = new ResourceLocation("lotrextended", "item/empty_armor_slot_sword");

    private NPCEntity companion;

    // TODO: Update this
    public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotrextended.hired_unit_equipment");

    private Inventory playerEquipment;
    private Inventory baseEquipment;

    public ExtendedHiredUnitEquipmentContainer(int p_39230_, PlayerInventory playerInventory, PacketBuffer extraData) {
        super(ExtendedContainers.HIRED_UNIT_EQUIPMENT.get(), p_39230_);

        // Read in packet data
        CompoundNBT nbt = extraData.readAnySizeNbt();
        this.entityId = nbt.getInt("entityId");

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

        // Get companion from this container
        this.companion = (NPCEntity) playerInventory.player.level.getEntity(entityId);


        // add the 3 rows of player inventory
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 142 + l * 18));
            }
        }

        // add the player's hotbar
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 200));
        }

        // setup 4 custom armor slots
        armorSlots[0]=(ArmorSlot)this.addSlot(new CompanionEquipmentSlot.ArmorSlot(playerEquipment, 0, 8, 31, ExtendedEquipmentSlotType.HEAD, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET));
        armorSlots[1]=(ArmorSlot)this.addSlot(new CompanionEquipmentSlot.ArmorSlot(playerEquipment, 1, 8, 49, ExtendedEquipmentSlotType.CHEST, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE));
        armorSlots[2]=(ArmorSlot)this.addSlot(new CompanionEquipmentSlot.ArmorSlot(playerEquipment, 2, 8, 67, ExtendedEquipmentSlotType.LEGS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS));
        armorSlots[3]=(ArmorSlot)this.addSlot(new CompanionEquipmentSlot.ArmorSlot(playerEquipment, 3, 8, 85, ExtendedEquipmentSlotType.FEET, PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS));

        // Setup hand slots
        meleeSlot = (MeleeSlot)this.addSlot(new CompanionEquipmentSlot.MeleeSlot(playerEquipment, 4, 62, 67, EMPTY_ARMOR_SLOT_SWORD));
        rangedSlot = (RangedSlot)this.addSlot(new CompanionEquipmentSlot.RangedSlot(playerEquipment, 5, 62, 49,EMPTY_ARMOR_SLOT_BOW));
        offhandSlot = (OffhandSlot)this.addSlot(new CompanionEquipmentSlot.OffhandSlot(playerEquipment, 9, 62, 85, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD));

        this.addSlotListener(this);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int p_39254_) {
    	Slot slot = this.slots.get(p_39254_);

    	if(slot!=null && slot.hasItem()) {
    		// handle shift-clicking equipment and sending it back to the player's inventory
    		if(slot instanceof CompanionEquipmentSlot) {
    			if (player.inventory.getFreeSlot() > -1 || player.inventory.getSlotWithRemainingSpace(slot.getItem()) > -1) {
    				player.addItem(slot.getItem());
    				slot.set(ItemStack.EMPTY);
    				slot.setChanged();
    				ExtendedHiredUnitHelper.updateEquipmentSlot(companion, ((CompanionEquipmentSlot)slot).getEquipmentSlotType(), ItemStack.EMPTY);
    			} else {
    				return ItemStack.EMPTY;
    			}
    		} else {
    			// Handle shift-clicking equipment and sending it to the unit's inventory
    			ItemStack shiftingItem = slot.getItem();

    			CompanionEquipmentSlot[] equipmentslots = new CompanionEquipmentSlot[7];
    			equipmentslots[0] = armorSlots[0];
    			equipmentslots[1] = armorSlots[1];
    			equipmentslots[2] = armorSlots[2];
    			equipmentslots[3] = armorSlots[3];
    			equipmentslots[4] = meleeSlot;
    			equipmentslots[5] = rangedSlot;
    			equipmentslots[6] = offhandSlot;
    			for(CompanionEquipmentSlot equipmentslot : equipmentslots) {
    				if(!equipmentslot.hasItem() && equipmentslot.mayPlaceShifting(shiftingItem)) {
    					equipmentslot.set(shiftingItem);
    					equipmentslot.setChanged();
    					ExtendedHiredUnitHelper.updateEquipmentSlot(companion, equipmentslot.getEquipmentSlotType(), shiftingItem);
    					slot.set(ItemStack.EMPTY);
        				slot.setChanged();
        				player.inventory.setItem(slot.index, ItemStack.EMPTY);
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

    public static void writeContainerInitData(PacketBuffer extraData, int entityId, List<ItemStack> equipment) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("entityId", entityId); // write the entity Id

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
    public void refreshContainer(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {

    }

    @Override
    public void slotChanged(Container pContainer, int slotIndex, ItemStack itemStack) {
    	for(Slot slot : slots) {
    		if(slot instanceof CompanionEquipmentSlot && slot.index==slotIndex) {
    			ExtendedHiredUnitHelper.updateEquipmentSlot(companion, ((CompanionEquipmentSlot)slot).getEquipmentSlotType(), itemStack);
    		}
    	}
    }

    @Override
    public void setContainerData(Container p_71112_1_, int p_71112_2_, int p_71112_3_) {

    }

	public Slot[] getArmorSlots() {
		return armorSlots;
	}

	public RangedSlot getRangedSlot() {
		return rangedSlot;
	}

	public MeleeSlot getMeleeSlot() {
		return meleeSlot;
	}

	public OffhandSlot getOffhandSlot() {
		return offhandSlot;
	}

	public Inventory getBaseInventory() {
		return baseEquipment;
	}

	public NPCEntity getCompanion() {
		return companion;
	}

	public int getEntityId() {
		return entityId;
	}
}
