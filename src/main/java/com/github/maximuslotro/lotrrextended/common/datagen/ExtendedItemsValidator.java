package com.github.maximuslotro.lotrrextended.common.datagen;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedRepairContainerNew;

import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedItemsValidator implements IDataProvider {

	public ExtendedItemsValidator() {}

	@Override
	public void run(DirectoryCache cache) {
		for(Item itemToRepair : ForgeRegistries.ITEMS.getValues()) {
			if(itemToRepair.canBeDepleted()){
				if(itemToRepair.equals(Items.TRIDENT)) {
					continue;
				}
				boolean foundRepairMaterial=false;
				for(Item itemMaterial : ForgeRegistries.ITEMS.getValues()) {
					try {
						if(itemToRepair !=itemMaterial && ExtendedRepairContainerNew.isValidRepairMaterial(new ItemStack(itemToRepair), new ItemStack(itemMaterial))) {
							foundRepairMaterial=true;
							break;
						}
					}catch(IllegalStateException e) {
						//System.out.println("Skipped because of tags: " + itemToRepair.getRegistryName());
						foundRepairMaterial = true;
						break;
					}
				}
				if (!foundRepairMaterial) {
					System.out.println("Missing repair material for: " + itemToRepair.getRegistryName());
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Extended Items Validator";
	}
}
