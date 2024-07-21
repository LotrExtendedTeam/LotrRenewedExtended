package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class MistyMountainsAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> MISTY_MOUNTAIN_BIOMES = ImmutableList.of(LOTRBiomes.MISTY_MOUNTAINS, 
			LOTRBiomes.MISTY_MOUNTAINS_FOOTHILLS);

	@Override
	public void generateAdvancements() {
		Advancement mm = build(true, data(Items.SNOW_BLOCK, "the_misty_mountains_cold", true, FrameType.TASK, true, true, false)
				.setTitle("The Mountains Cold").setDescription("Enter the Misty Mountains."), 
				addBiomeTriggers(make(), MISTY_MOUNTAIN_BIOMES));
	}

	@Override
	public String getFolder() {
		return "misty_mountains";
	}

}
