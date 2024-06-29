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
import net.minecraft.item.Items;


public class BreeAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> BREE_BIOMES = ImmutableList.of(LOTRBiomes.BREELAND, LOTRBiomes.CHETWOOD);

	@Override
	public void generateAdvancements() {
		Advancement bree = build(true, data(LOTRItems.DRYSTONE.get(), "of_hobbits_and_men", true, FrameType.TASK, true, true, false)
				.setTitle("Of Hobbits and Men").setDescription("Enter the Region of Bree-land."), 
				addBiomeTriggers(make(), BREE_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement chetwood = build(true, data(Items.OAK_SAPLING, "ruffians_haunt", false, FrameType.TASK, true, true, false)
		.setTitle("Ruffians' Haunt").setDescription("Enter the forest of Chetwood."), 
		addBiomeTrigger(make(), LOTRBiomes.CHETWOOD).parent(bree));

		Advancement bree_man_slayer = build(true, data(Items.BONE, "bree_man_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-Man Slayer").setDescription("Kill a Bree-Man."), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.BREE_GUARD.get()), LOTREntities.BREE_MAN.get())
				.requirements(IRequirementsStrategy.OR).parent(bree));

		Advancement bree_hobbit_slayer = build(true, data(LOTRItems.HOBBIT_BONE.get(), "bree_hobbit_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-Hobbit Slayer").setDescription("Kill a Bree-Hobbit."), 
				addKillEntityTrigger(make(), LOTREntities.BREE_HOBBIT.get()).requirements(IRequirementsStrategy.OR).parent(bree));

		Advancement bree_10 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_guest", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Guest").setDescription("Reach +10.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 10).parent(bree));

		Advancement bree_50 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_friend", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Friend").setDescription("Reach +50.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 50).parent(bree_10));

		Advancement bree_100 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_townsman", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Townsman").setDescription("Reach +100.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 100).parent(bree_50));

		Advancement bree_200 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_trustee", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-land Trustee").setDescription("Reach +200.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 200).parent(bree_100));

		Advancement bree_500 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_champion", false, FrameType.TASK, true, true, false)
				.setTitle("Champion of Bree").setDescription("Reach +500.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 500).parent(bree_200));

		Advancement bree_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_captain", false, FrameType.GOAL, true, true, false)
				.setTitle("Captain of Bree").setDescription("Reach +1000.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 1000).parent(bree_500));

		Advancement bree_2000 = build(true, data(LOTRItems.GOLD_RING.get(), "bree_master", false, FrameType.CHALLENGE, true, true, false)
				.setTitle("Master of Bree").setDescription("Reach +2000.0 alignment with Bree-land."), 
				addAlignmentTrigger(make(), FactionPointers.BREE, 2000).parent(bree_1000));
	}

	@Override
	public String getFolder() {
		return "bree";
	}

}
