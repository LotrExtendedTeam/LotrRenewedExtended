package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming.ExtendedFarmingGoalHolder;
import com.github.maximuslotro.lotrrextended.common.entity.ai.goal.farming.ExtendedFarmingPlantingTask;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedItemStackUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedHiredUnitFarmhandSlot extends Slot {
	private ExtendedHiredUnitFarmhandSlot(IInventory pContainer, int pIndex, int pX, int pY) {
		super(pContainer, pIndex, pX, pY);
	}

	public static class SeedsSlot extends ExtendedHiredUnitFarmhandSlot {
		ExtendedGoalHolderAccessor accessor;

		public SeedsSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation empty_SLOT_LOCATION, ExtendedGoalHolderAccessor pAccessor) {
			super(pContainer, pIndex, pX, pY);
			setBackground(PlayerContainer.BLOCK_ATLAS, empty_SLOT_LOCATION);
			this.accessor = pAccessor;
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return ExtendedFarmingPlantingTask.isSupportedPlantType(ExtendedItemStackUtils.getPlantFromItem(itemStack), null, null, accessor.getHolder());
		}
		
		public interface ExtendedGoalHolderAccessor {
			ExtendedFarmingGoalHolder getHolder();
		}
	}

	public static class BoneMealSlot extends ExtendedHiredUnitFarmhandSlot {
		private final ExtendedSeedsValidator seeds;
		public BoneMealSlot(IInventory pContainer, int pIndex, int pX, int pY, ResourceLocation EMPTY_SLOT_LOCATION, ExtendedSeedsValidator pSeeds) {
			super(pContainer, pIndex, pX, pY);
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_SLOT_LOCATION);
			seeds = pSeeds;
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return seeds.isSeedsValid() && itemStack.getItem() == Items.BONE_MEAL;
		}

		public interface ExtendedSeedsValidator {
			boolean isSeedsValid();
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
