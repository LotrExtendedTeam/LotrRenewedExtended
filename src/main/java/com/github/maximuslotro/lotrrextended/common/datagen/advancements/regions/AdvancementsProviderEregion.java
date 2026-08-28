package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;


public class AdvancementsProviderEregion extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement eregion = build(true, dataMain(LOTRItems.HOLLY_SAPLING.get(), "faded_splendor", FrameType.TASK)
				.setTitle("Faded Splendor").setDescription("Enter the land of Eregion."),
				addBiomeTrigger(make(), LOTRBiomes.EREGION));
		Advancement eregion_armor = build(false, data(ExtendedItems.EREGION_HELMET.get(), "eregion_armor", FrameType.TASK)
			.setTitle("Armament of Ost-in-Edhil").setDescription("Acquire a full set of Eregion armor."),
			addArmorTrigger(make(), "eregion", ExtendedItems.EREGION_HELMET.get(), ExtendedItems.EREGION_CHESTPLATE.get(),
				ExtendedItems.EREGION_LEGGINGS.get(), ExtendedItems.EREGION_BOOTS.get()).parent(eregion));
	}

	@Override
	public String getFolder() {
		return "eregion";
	}

	@Override
	public int getLegacyCount() {
		return 1;
	}

}
