package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;


public class DaleAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> DALE_BIOMES = ImmutableList.of(LOTRBiomes.DALE, LOTRBiomes.NORTHERN_DALE);

	@Override
	public void generateAdvancements() {
		Advancement dale = build(true, data(LOTRItems.SHIRE_HEATHER.get(), "kingdom_of_the_northmen", true, FrameType.TASK, true, true, false)
				.setTitle("Kingdom of the Northmen").setDescription("Enter the land of Dale."), 
				addBiomeTriggers(make(), DALE_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement hobbit_slayer = build(true, data(LOTRItems.HOBBIT_BONE.get(), "dale_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Dalishman Slayer").setDescription("Kill a Man of Dale."), 
				addKillEntityTrigger(make(), LOTREntities.DALE_MAN.get()).requirements(IRequirementsStrategy.OR).parent(dale));

		Advancement dale_10 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Barding Guest").setDescription("Reach +10.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 10).parent(dale));
		Advancement dale_50 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Barding Friend").setDescription("Reach +50.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 50).parent(dale_10));
		Advancement dale_100 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_soldier", false, FrameType.TASK, true, true, false)
				.setTitle("Dalish Soldier").setDescription("Reach +100.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 100).parent(dale_50));
		Advancement dale_200 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_herald", false, FrameType.TASK, true, true, false)
				.setTitle("Dalish Herald").setDescription("Reach +200.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 200).parent(dale_100));
		Advancement dale_500 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_captain", false, FrameType.TASK, true, true, false)
				.setTitle("Dalish Captain").setDescription("Reach +500.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 500).parent(dale_200));
		Advancement dale_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_marchal", false, FrameType.GOAL, true, true, false)
				.setTitle("Dalish Marshal").setDescription("Reach +1000.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 1000).parent(dale_500));
		Advancement dale_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "dale_noble", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Dalish Lord/Lady").setDescription("Reach +2000.0 alignment with Dale."), 
				addAlignmentTrigger(make(), FactionPointers.DALE, 2000).parent(dale_1000));
	}

	@Override
	public String getFolder() {
		return "dale";
	}

}
