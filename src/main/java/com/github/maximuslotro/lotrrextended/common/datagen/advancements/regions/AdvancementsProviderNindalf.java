package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderNindalf extends ExtendedBaseAdvancementType{

	@Override
	public void generateAdvancements() {
		Advancement wetwang = build(true, dataMain(Items.STONE, "wetwang", FrameType.TASK)
				.setTitle("Wetwang").setDescription("Enter the swamps of Nindalf."),
				addBiomeTriggers(make(), LOTRBiomes.NINDALF));

		Advancement stench_of_evil = build(true, data(Items.STONE, "stench_of_evil", FrameType.TASK)
			.setTitle("The Stench of Evil").setDescription("Enter the Dead Marshes."),
			addBiomeTriggers(make(), LOTRBiomes.DEAD_MARSHES).parent(wetwang));

		Advancement risky_excavations = build(true, data(LOTRItems.GLOWSTONE_ORE.get(), "risky_excavations", FrameType.GOAL)
			.setTitle("Worth the Risk?").setDescription("Dig up a block of remains."),
			addBreakBlockTriggers(make(), ExtendedBlocks.REMAINS_DWARF.get(), ExtendedBlocks.REMAINS_ELF.get(), 
				ExtendedBlocks.REMAINS_MAN.get(), ExtendedBlocks.REMAINS_ORC.get()).parent(stench_of_evil));

	}

	@Override
	public String getFolder() {
		return "angmar";
	}

}
