package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.item.IEmptyVesselItem;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselOperations;
import lotr.common.item.VesselType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(VesselOperations.class)
public class MixinVesselOperations {

	/**
	 * @author Maximuslotro
	 * @reason Need for glass bottles to be counted as empty 
	 * @param itemstack Stack to be checked
	 * @return True if item is a empty vessel
	 * 
	 */
	@Overwrite(remap = false)
	public static boolean isItemEmptyVessel(ItemStack itemstack) {
		return itemstack.getItem() instanceof IEmptyVesselItem /*Extended Start*/|| itemstack.getItem().equals(Items.GLASS_BOTTLE)/*Extended End*/;
	}
	
	/**
	 * @author Maximuslotro
	 * @reason Need for vessel for glass bottles
	 * @param itemstack Stack to be checked
	 * @return True if item is a empty vessel
	 * 
	 */
	@Overwrite(remap=false)
	public static VesselType getVessel(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof IEmptyVesselItem) {
			return ((IEmptyVesselItem) item).getVesselType();
		}
		//Extended Start
		if(itemstack.getItem().equals(Items.GLASS_BOTTLE)) {
			return VesselType.valueOf("GLASS_BOTTLE");
		}
		//Extended End
		return item instanceof VesselDrinkItem ? VesselDrinkItem.getVessel(itemstack) : VesselType.WOODEN_MUG;
	}
}
