package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import lotr.common.entity.npc.ExtendedIFarmhand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedHiredUnitFarmhandContainer extends ExtendedHiredUnitContainer {
	public static final ResourceLocation EMPTY_SLOT_BONEMEAL = new ResourceLocation(LotrExtendedMod.MODID, "item/empty_slot_bonemeal");
	public static final ResourceLocation EMPTY_SLOT_SEEDS = new ResourceLocation(LotrExtendedMod.MODID, "item/empty_slot_seeds");
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lotrextended.hired_unit_farmhand");

	private Slot seedsSlot;
	private Slot bonemealSlot;
	private IInventory farmhandSupplies;

	public ExtendedHiredUnitFarmhandContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		super(ExtendedContainerTypes.HIRED_UNIT_FARMHAND.get(), pContainerId, pPlayerInventory, extraData);

		IInventory companionInv = ((ExtendedIFarmhand)getCompanion()).getFarmhandInventory();
		this.farmhandSupplies = companionInv;
		companionInv.startOpen(pPlayerInventory.player);

		// Inventory slots
		seedsSlot = this.addSlot(new ExtendedHiredUnitFarmhandSlot.SeedsSlot(farmhandSupplies, 0, 62, 85, EMPTY_SLOT_SEEDS));
		bonemealSlot = this.addSlot(new ExtendedHiredUnitFarmhandSlot.BoneMealSlot(farmhandSupplies, 1, 80, 85, EMPTY_SLOT_BONEMEAL));
		for (int i1 = 0; i1 < 3; ++i1) {
			this.addSlot(new ExtendedHiredUnitFarmhandSlot.CropsSlot(farmhandSupplies, 2+i1, 80 + i1 * 18, 100));
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

	@Override
	public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < 4) {
				if (!this.moveItemStackTo(itemstack1, 5, 40, true)) {
					return ItemStack.EMPTY;
				}
			} else if (seedsSlot.mayPlace(itemstack1) && !this.moveItemStackTo(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			} else if (bonemealSlot.mayPlace(itemstack1) && !this.moveItemStackTo(itemstack1, 1, 2, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
	            return ItemStack.EMPTY;
	         }
			slot.onTake(pPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		return true;
	}

	public static void writeContainerInitData(PacketBuffer extraData, int entityId) {
		ExtendedHiredUnitContainer.writeContainerInitData(extraData, entityId);
	}
}
