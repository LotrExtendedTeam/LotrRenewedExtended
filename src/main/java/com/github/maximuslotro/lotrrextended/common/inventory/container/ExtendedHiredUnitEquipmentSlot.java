package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedEquipmentSlotType;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;

import lotr.common.item.HammerItem;
import lotr.common.item.SpearItem;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedHiredUnitEquipmentSlot extends Slot{
	private final ExtendedEquipmentSlotType equipmentSlotType;
	private ExtendedHiredUnitEquipmentSlot(IInventory pContainer, int pIndex, int pX, int pY, ExtendedEquipmentSlotType equipmentSlotType) {
		super(pContainer, pIndex, pX, pY);
		this.equipmentSlotType = equipmentSlotType;
	}

	public ExtendedEquipmentSlotType getEquipmentSlotType() {
		return equipmentSlotType;
	}

	public abstract boolean mayPlaceShifting(ItemStack pShiftingItem);

	public static class ArmorSlot extends ExtendedHiredUnitEquipmentSlot{
		public ArmorSlot(IInventory pContainer, int pIndex, int pX, int pY, ExtendedEquipmentSlotType equipmentSlotType, ResourceLocation empty_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY, equipmentSlotType);
			setBackground(PlayerContainer.BLOCK_ATLAS, empty_SLOT_LOCATION);
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			if (itemStack.getItem() instanceof ArmorItem) {
				return ((ArmorItem) itemStack.getItem()).getSlot() == getEquipmentSlotType().getVanillaEquilibrant();
			} else if (itemStack.getItem() instanceof BlockItem && ((BlockItem)itemStack.getItem()).getBlock() instanceof AbstractSkullBlock ||
				itemStack.getItem().equals(Items.PUMPKIN) || itemStack.getItem().equals(Items.CARVED_PUMPKIN) || 
				itemStack.getItem().equals(Items.JACK_O_LANTERN)) {
				// Allow Skull and pumpkin blocks to be worn
				return ExtendedEquipmentSlotType.HEAD.equals(getEquipmentSlotType());
			} else if (itemStack.getItem() instanceof ElytraItem) {
				// Allow Elytra to be equipped on the chest, as a visual thing only
				return ExtendedEquipmentSlotType.CHEST.equals(getEquipmentSlotType());
			} else {
				return false;
			}
		}

		@Override
		public boolean mayPlaceShifting(ItemStack pShiftingItem) {
			return mayPlace(pShiftingItem);
		}
	}

	public static class RangedSlot extends ExtendedHiredUnitEquipmentSlot{
		public RangedSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation EMPTY_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY, ExtendedEquipmentSlotType.RANGED);
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_LOCATION);
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return itemStack.getItem() instanceof BowItem || itemStack.getItem() instanceof ExtendedThrowableAxeItem;
		}

		@Override
		public boolean mayPlaceShifting(ItemStack pShiftingItem) {
			return mayPlace(pShiftingItem);
		}
	}

	public static class MeleeSlot extends ExtendedHiredUnitEquipmentSlot{
		public MeleeSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation EMPTY_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY, ExtendedEquipmentSlotType.MELEE);
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_LOCATION);
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return !(itemStack.getItem() instanceof BowItem);
		}

		@Override
		public boolean mayPlaceShifting(ItemStack pShiftingItem) {
			return pShiftingItem.getItem() instanceof SwordItem || pShiftingItem.getItem() instanceof HammerItem || pShiftingItem.getItem() instanceof SpearItem;
		}
	}

	public static class FarmhandMeleeSlot extends ExtendedHiredUnitEquipmentSlot {
		public FarmhandMeleeSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation EMPTY_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY, ExtendedEquipmentSlotType.MELEE);
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_LOCATION);
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return itemStack.getItem() instanceof HoeItem;
		}

		@Override
		public boolean mayPlaceShifting(ItemStack pShiftingItem) {
			return mayPlace(pShiftingItem);
		}
	}

	public static class OffhandSlot extends ExtendedHiredUnitEquipmentSlot{
		public OffhandSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation EMPTY_SLOT_LOCATION) {
			super(pContainer, pIndex, pX, pY, ExtendedEquipmentSlotType.OFFHAND);
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_LOCATION);
		}

		@Override
		public boolean mayPlaceShifting(ItemStack pShiftingItem) {
			return pShiftingItem.getItem() instanceof ShieldItem;
		}
	}
}
