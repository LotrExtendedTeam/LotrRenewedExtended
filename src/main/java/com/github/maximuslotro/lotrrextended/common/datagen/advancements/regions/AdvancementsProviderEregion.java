package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

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
