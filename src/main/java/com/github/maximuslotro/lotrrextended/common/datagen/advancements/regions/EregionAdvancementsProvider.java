package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;


public class EregionAdvancementsProvider extends BaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement eregion = build(true, data(LOTRItems.HOLLY_SAPLING.get(), "faded_splendor", true, FrameType.TASK, true, true, false)
				.setTitle("Faded Splendor").setDescription("Enter the land of Eregion."),
				addBiomeTrigger(make(), LOTRBiomes.EREGION));
	}

	@Override
	public String getFolder() {
		return "eregion";
	}

}
