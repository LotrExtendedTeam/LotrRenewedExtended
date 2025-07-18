package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.init.LOTRBiomes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderBelegaer extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement to_the_sea = build(true, dataMain(Items.OAK_BOAT, "to_the_sea", FrameType.TASK)
				.setTitle("To the Sea!").setDescription("Travel to the the seas surrounding Middle-earth."),
				addBiomeTrigger(make(), LOTRBiomes.SEA));
	}

	@Override
	public String getFolder() {
		return "belegaer";
	}

	@Override
	public int getLegacyCount() {
		return 2;
	}

}
