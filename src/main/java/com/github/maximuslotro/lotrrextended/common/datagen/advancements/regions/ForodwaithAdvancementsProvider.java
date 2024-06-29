package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;


public class ForodwaithAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> FORODWAITH_BIOMES = ImmutableList.of(LOTRBiomes.FORODWAITH, 
			LOTRBiomes.FORODWAITH_MOUNTAINS, LOTRBiomes.FOROCHEL);

	@Override
	public void generateAdvancements() {
		Advancement everlasting_chill = build(true, data(Items.SNOW_BLOCK, "everlasting_chill", true, FrameType.TASK, true, true, false)
				.setTitle("Everlasting Chill").setDescription("Enter the region of Forodwaith."), 
				addBiomeTriggers(make(), FORODWAITH_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement forochel = build(false, data(Items.ICE, "forochel", true, FrameType.TASK, false, true, false)
				.setTitle("A small Respite").setDescription("Enter the icebay of Forochel."), 
				addBiomeTrigger(make(), LOTRBiomes.FOROCHEL).parent(everlasting_chill));

		Advancement lossoth_10 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Lossoth Guest").setDescription("Reach +10.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.LOSSOTH, 10).parent(everlasting_chill));

		Advancement lossoth_50 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_kinsman", false, FrameType.TASK, true, true, false)
				.setTitle("Lossoth Kinsman").setDescription("Reach +50.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 50).parent(lossoth_10));

		Advancement lossoth_100 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_warrior", false, FrameType.TASK, true, true, false)
				.setTitle("Lossoth Warrior").setDescription("Reach +100.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 100).parent(lossoth_50));

		Advancement lossoth_200 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_ice_breaker", false, FrameType.TASK, true, true, false)
				.setTitle("Lossoth Ice-Breaker").setDescription("Reach +200.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 200).parent(lossoth_100));

		Advancement lossoth_500 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_fire_starter", false, FrameType.TASK, true, true, false)
				.setTitle("Lossoth Fire-Starter").setDescription("Reach +500.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 500).parent(lossoth_200));

		Advancement lossoth_1000 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_chieftain", false, FrameType.GOAL, true, true, false)
				.setTitle("Lossoth Chieftain").setDescription("Reach +1000.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 1000).parent(lossoth_500));

		Advancement lossoth_2000 = build(false, data(LOTRItems.GOLD_RING.get(), "lossoth_eldar", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Lossoth Eldar").setDescription("Reach +2000.0 alignment with the Men of Lossoth."), 
				addAlignmentTrigger(make(), FactionPointers.DUNLAND, 2000).parent(lossoth_1000));
	}

	@Override
	public String getFolder() {
		return "forodwaith";
	}

}
