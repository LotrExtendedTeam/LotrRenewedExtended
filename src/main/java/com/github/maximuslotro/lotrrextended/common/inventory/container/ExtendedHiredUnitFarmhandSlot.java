package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public abstract class ExtendedHiredUnitFarmhandSlot extends Slot{
	private ExtendedHiredUnitFarmhandSlot(IInventory pContainer, int pIndex, int pX, int pY) {
		super(pContainer, pIndex, pX, pY);
	}

	public static class SeedsSlot extends ExtendedHiredUnitFarmhandSlot {
		public SeedsSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation empty_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY);
			setBackground(PlayerContainer.BLOCK_ATLAS, empty_SLOT_LOCATION);
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			IPlantable plant = ExtendedItemStackUtils.getPlantFromItem(itemStack);
			if(plant!=null) {
				try {
					//TODO is there a better way to handle this?
					return plant.getPlantType(null, null) == PlantType.CROP;
				} catch (NullPointerException e) {}
			}
			return false;
		}
	}

	public static class BoneMealSlot extends ExtendedHiredUnitFarmhandSlot{
		public BoneMealSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation EMPTY_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY);
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_LOCATION);
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return itemStack.getItem() == Items.BONE_MEAL;
		}
	}

	public static class CropsSlot extends ExtendedHiredUnitFarmhandSlot{
		public CropsSlot(IInventory pContainer, int pIndex, int pX, int pY) {
			super(pContainer, pIndex, pX, pY);
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return false;
		}
	}
}
