package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class ForodwaithAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> FORODWAITH_BIOMES = ImmutableList.of(LOTRBiomes.FORODWAITH, 
			LOTRBiomes.FORODWAITH_MOUNTAINS, LOTRBiomes.FOROCHEL);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement everlasting_chill = build(data(Items.ICE, "everlasting_chill", true, FrameType.TASK, true, true, false)
				.setTitle("Everlasting Chill").setDescription("Enter the region of Forodwaith.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), FORODWAITH_BIOMES).requirements(IRequirementsStrategy.OR));
	}

	@Override
	public String getFolder() {
		return "forodwaith";
	}

}
