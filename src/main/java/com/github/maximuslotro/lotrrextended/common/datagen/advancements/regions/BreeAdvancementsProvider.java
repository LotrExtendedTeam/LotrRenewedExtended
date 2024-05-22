package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class BreeAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> BREE_BIOMES = ImmutableList.of(LOTRBiomes.BREELAND, LOTRBiomes.CHETWOOD);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement bree = build(data(LOTRItems.DRYSTONE.get(), "of_hobbits_and_men", true, FrameType.TASK, true, true, false)
				.setTitle("Of Hobbits and Men").setDescription("Enter the Region of Bree-land.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), BREE_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement chetwood = build(data(Items.OAK_SAPLING, "ruffians_haunt", false, FrameType.TASK, true, true, false)
		.setTitle("Ruffians' Haunt").setDescription("Enter the forest of Chetwood.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.CHETWOOD).parent(bree));

		Advancement bree_man_slayer = build(data(Items.BONE, "bree_man_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-Man Slayer").setDescription("Kill a Bree-Man.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(addKillEntityTrigger(make(), LOTREntities.BREE_GUARD.get()), LOTREntities.BREE_MAN.get())
				.requirements(IRequirementsStrategy.OR).parent(bree));

		Advancement bree_hobbit_slayer = build(data(LOTRItems.HOBBIT_BONE.get(), "bree_hobbit_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Bree-Hobbit Slayer").setDescription("Kill a Bree-Hobbit.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(make(), LOTREntities.BREE_HOBBIT.get()).requirements(IRequirementsStrategy.OR).parent(bree));
	}

	@Override
	public String getFolder() {
		return "bree";
	}

}
