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


public class ShireAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> SHIRE_BIOMES = ImmutableList.of(LOTRBiomes.SHIRE, LOTRBiomes.SHIRE_MARSHES, 
			LOTRBiomes.SHIRE_MOORS, LOTRBiomes.SHIRE_WOODLANDS, LOTRBiomes.WHITE_DOWNS);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement shire = build(data(Items.GRASS_BLOCK, "a_simple_land", true, FrameType.TASK, true, true, false)
				.setTitle("A Simple Land").setDescription("Enter the Shire.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), SHIRE_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement white_downs = build(data(LOTRItems.CHALK.get(), "chalken_delvings", false, FrameType.TASK, true, true, false)
		.setTitle("Chalken Delvings").setDescription("Enter the White Downs.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.WHITE_DOWNS).parent(shire));

		Advancement old_forest = build(data(Items.OAK_LOG, "unfriendly_trees", false, FrameType.TASK, true, true, false)
		.setTitle("Unfriendly Trees").setDescription("Enter the Old Forest.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.OLD_FOREST).parent(shire));

		Advancement hobbit_slayer = build(data(LOTRItems.HOBBIT_BONE.get(), "hobbit_slayer", false, FrameType.TASK, true, true, false)
				.setTitle("Hobbit Slayer").setDescription("Kill a Hobbit.").setTitleStype(TextFormatting.GREEN), 
				addKillEntityTrigger(make(), LOTREntities.HOBBIT.get()).requirements(IRequirementsStrategy.OR).parent(shire));

		Advancement cover = build(data(LOTRItems.FOUR_LEAF_CLOVER.get(), "feeling_lucky", false, FrameType.GOAL, true, true, false)
				.setTitle("Feeling Lucky?").setDescription("Find a rare Four-Leaf Clover.").setTitleStype(TextFormatting.GOLD), 
				addItemTrigger(make(), LOTRItems.FOUR_LEAF_CLOVER.get()).parent(shire));
	}

	@Override
	public String getFolder() {
		return "shire";
	}

}
