package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.fac.FactionPointers;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderFangorn extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> FANGORN_BIOMES = ImmutableList.of(LOTRBiomes.FANGORN);

	@Override
	public void generateAdvancements() {
		Advancement fangorn = build(true, dataMain(Items.OAK_LEAVES, "lower_your_axe", FrameType.TASK)
				.setTitle("Lower Your Axe").setDescription("Enter Fangorn Forest."),
				addBiomeTriggers(make(), FANGORN_BIOMES));

//		Advancement ent_slayer = build(true, data(Items.STICK, "ent_slayer", FrameType.TASK)
//				.setTitle("Enemy of the Forest").setDescription("Kill an Ent."),
//				addKillEntityTrigger(make(), ExtendedEntities.ENT.get()).parent(fangorn));

		Advancement ent_10 = build(true, data(LOTRItems.GOLD_RING.get(), "ent_10", FrameType.TASK)
				.setTitle("Fangorn Newcomer").setDescription("Reach +10.0 alignment with the Ents."),
				addAlignmentTrigger(make(), FactionPointers.FANGORN, 10).parent(fangorn));
		Advancement ent_50 = build(true, data(LOTRItems.GOLD_RING.get(), "ent_50", FrameType.TASK)
				.setTitle("Fangorn Friend").setDescription("Reach +50.0 alignment with the Ents."),
				addAlignmentTrigger(make(), FactionPointers.FANGORN, 50).parent(ent_10));
		Advancement ent_100 = build(true, data(LOTRItems.GOLD_RING.get(), "ent_100", FrameType.TASK)
				.setTitle("Fangorn Treeherd").setDescription("Reach +100.0 alignment with the Ents."),
				addAlignmentTrigger(make(), FactionPointers.FANGORN, 100).parent(ent_50));
		Advancement ent_250 = build(true, data(LOTRItems.GOLD_RING.get(), "ent_250", FrameType.TASK)
				.setTitle("Fangorn Master").setDescription("Reach +200.0 alignment with the Ents."),
				addAlignmentTrigger(make(), FactionPointers.FANGORN, 200).parent(ent_100));
		Advancement ent_500 = build(true, data(LOTRItems.GOLD_RING.get(), "ent_500", FrameType.GOAL)
				.setTitle("Fangorn Elder").setDescription("Reach +500.0 alignment with the Ents."),
				addAlignmentTrigger(make(), FactionPointers.FANGORN, 500).parent(ent_250));
		Advancement ent_10000 = build(false, data(LOTRItems.GOLD_RING.get(), "ent_1000", FrameType.CHALLENGE).setHidden()
				.setTitle("True Ent").setDescription("Reach +10000.0 alignment with the Ents and become a true Ent!"),
				addAlignmentTrigger(make(), FactionPointers.FANGORN, 1000).parent(ent_500));
	}

	@Override
	public String getFolder() {
		return "fangorn";
	}

	@Override
	public int getLegacyCount() {
		return 12;
	}

}
