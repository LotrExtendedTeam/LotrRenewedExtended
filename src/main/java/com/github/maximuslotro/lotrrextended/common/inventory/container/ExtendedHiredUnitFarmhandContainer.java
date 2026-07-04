package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.data.ExtendedNpcFarmhandInfoHolder;
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
	public int farmhandContainterSize;

	public ExtendedHiredUnitFarmhandContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		super(ExtendedContainerTypes.HIRED_UNIT_FARMHAND.get(), pContainerId, pPlayerInventory, extraData);

		this.farmhandSupplies = getFarmhandInfo().getFarmhandInventory();
		farmhandSupplies.startOpen(pPlayerInventory.player);
		// Subtract 2 instead of 4 because we want size of output slots, not just additional output slots (minimum is 2 base)
		farmhandContainterSize = Math.min(farmhandSupplies.getContainerSize()-2, 12);
		// Inventory slots
		seedsSlot = this.addSlot(new ExtendedHiredUnitFarmhandSlot.SeedsSlot(farmhandSupplies, 0, 44, 61, EMPTY_SLOT_SEEDS));
		bonemealSlot = this.addSlot(new ExtendedHiredUnitFarmhandSlot.BoneMealSlot(farmhandSupplies, 1, 116, 61, EMPTY_SLOT_BONEMEAL));
		int[] xCoordinates = {
				71, 89,   // Center of top row (Slots 1, 2)
				53, 107,  // Mid-outer top row (Slots 3, 4)
				35, 125,  // Outer top row     (Slots 5, 6)
				17, 143,  // Far edges top row (Slots 7, 8)
				71, 89,   // Center bottom row (Slots 9, 10)
				53, 107   // Edges bottom row  (Slots 11, 12)
		};
		for (int i = 0; i < farmhandContainterSize; ++i) {
			int xPos = xCoordinates[i];
			int yPos = (i < 8) ? 93 : 111;
			this.addSlot(new ExtendedHiredUnitFarmhandSlot.CropsSlot(farmhandSupplies, 2 + i, xPos, yPos));
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

	public ExtendedNpcFarmhandInfoHolder getFarmhandInfo() {
		return ((ExtendedINPCEntity)getCompanion()).getFarmhandInfo();
	}
	@Override
	public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex < farmhandSupplies.getContainerSize()) {
				if (!this.moveItemStackTo(itemstack1, farmhandSupplies.getContainerSize()+1, farmhandSupplies.getContainerSize()+34, true)) {
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
	public boolean stillValid(PlayerEntity pPlayer) {
		if(!pPlayer.level.isClientSide() && farmhandContainterSize != getFarmhandInfo().getInventoryAdditionalSize()+2) {
			// Close UI upon farmer container size change
			return false;
		}
		return this.farmhandSupplies.stillValid(pPlayer) && this.getCompanion().isAlive();
	}

	public static void writeContainerInitData(PacketBuffer extraData, int entityId) {
		ExtendedHiredUnitContainer.writeContainerInitData(extraData, entityId);
	}

	@Override
	public void removed(PlayerEntity pPlayer) {
		super.removed(pPlayer);
		this.farmhandSupplies.stopOpen(pPlayer);
	}
}
