package com.github.maximuslotro.lotrrextended.common.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
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
			if (itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof IPlantable) {
				try {
					return ((IPlantable)((BlockItem)itemStack.getItem()).getBlock()).getPlantType(null, null) == PlantType.DESERT;
				}catch (NullPointerException e) {}
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
