package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;

public class AdvancementsProviderMordor extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement enter_mordor = build(true, dataMain(LOTRItems.MORDOR_BRICK.get(), "enter_mordor", FrameType.TASK)
				.setTitle("One Does Not Simply...").setDescription("Enter the land of Mordor."),
				addBiomeTriggers(make(), LOTRBiomes.MORDOR, LOTRBiomes.MORDOR_MOUNTAINS, LOTRBiomes.MORGUL_VALE, LOTRBiomes.NURN, LOTRBiomes.NURNEN, 
					LOTRBiomes.NURN_MARSHES));

		Advancement slavers = build(true, data(ExtendedItems.ROPE.get(), "slavers", FrameType.TASK)
				.setTitle("Slaves and Slavers").setDescription("Enter the region of Núrn."),
				addBiomeTriggers(make(), LOTRBiomes.NURN, LOTRBiomes.NURNEN,  LOTRBiomes.NURN_MARSHES).parent(enter_mordor));

		Advancement sickly_moon = build(true, data(LOTRItems.MORGUL_FLOWER.get(), "sickly_moon", FrameType.TASK)
			.setTitle("The Sickly Moon").setDescription("Enter the Morgul Vale."),
			addBiomeTriggers(make(), LOTRBiomes.MORGUL_VALE).parent(enter_mordor));

		Advancement guldural_ore = build(true, data(LOTRItems.GULDURIL.get(), "guldural_ore", FrameType.TASK)
			.setTitle("A Sickly Light").setDescription("Mine some Gulduril ore."),
			addBreakBlockTriggers(make(), LOTRBlocks.GULDURIL_ORE_MORDOR.get(), LOTRBlocks.GULDURIL_ORE_STONE.get(), 
				LOTRBlocks.GULDURIL_CRYSTAL.get()).parent(enter_mordor));

		Advancement durnaur_ore = build(true, data(LOTRItems.DURNOR.get(), "durnaur_ore", FrameType.TASK)
			.setTitle("Fires of the Deep").setDescription("Mine a gem of Durnaur."),
			addBreakBlockTriggers(make(), LOTRBlocks.DURNOR_ORE.get()).parent(enter_mordor));

		Advancement mordor_10 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_10", FrameType.TASK)
				.setTitle("Mordor Guest").setDescription("Reach +10.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 10).parent(enter_mordor));
		Advancement mordor_50 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_50", FrameType.TASK)
				.setTitle("Mordor Friend").setDescription("Reach +50.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 50).parent(mordor_10));
		Advancement mordor_100 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_100", FrameType.TASK)
				.setTitle("Mordorian-at-Arms").setDescription("Reach +100.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 100).parent(mordor_50));
		Advancement mordor_200 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_200", FrameType.TASK)
				.setTitle("Mordor Soldier").setDescription("Reach +200.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 200).parent(mordor_100));
		Advancement mordor_500 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_500", FrameType.TASK)
				.setTitle("Mordor Knight").setDescription("Reach +500.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 500).parent(mordor_200));
		Advancement mordor_1000 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_1000", FrameType.TASK)
				.setTitle("Mordor Champion").setDescription("Reach +1000.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 1000).parent(mordor_500));
		Advancement mordor_1500 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_2000", FrameType.GOAL)
				.setTitle("Mordor Captain").setDescription("Reach +2000.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 1500).parent(mordor_1000));
		Advancement mordor_3000 = build(true, data(LOTRItems.GOLD_RING.get(), "mordor_3000", FrameType.CHALLENGE)
				.setTitle("Mordorian Lord/Lady").setDescription("Reach +3000.0 alignment with Mordor."),
				addAlignmentTrigger(make(), FactionPointers.MORDOR, 3000).parent(mordor_1500));

	}

	@Override
	public String getFolder() {
		return "rohan";
	}

}
