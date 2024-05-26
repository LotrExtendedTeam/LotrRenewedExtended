package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.BaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;


public class RhovanionAdvancementsProvider extends BaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> Rhovanion_BIOMES = ImmutableList.of(LOTRBiomes.ANDUIN_HILLS, LOTRBiomes.VALES_OF_ANDUIN);

	@SuppressWarnings("unused")
	@Override
	public void generateAdvancements() {
		Advancement rhovanion = build(true, data(Items.OAK_SAPLING, "the_great_river", true, FrameType.TASK, true, true, false)
				.setTitle("The Great River").setDescription("Enter the region of Anduin.").setTitleStype(TextFormatting.GREEN), 
				addBiomeTriggers(make(), Rhovanion_BIOMES).requirements(IRequirementsStrategy.OR));

		Advancement brown_lands = build(true, data(Items.DIRT, "barren_wasteland", false, FrameType.TASK, true, true, false)
		.setTitle("Barren Wasteland").setDescription("Enter the Brown Lands.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.BROWN_LANDS).parent(rhovanion));

		Advancement a_nasty_drop = build(true, data(Items.STONE, "a_nasty_drop", false, FrameType.TASK, true, true, false)
		.setTitle("A Nasty Drop").setDescription("Enter the Emyn Muil.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.EMYN_MUIL).parent(rhovanion));

//		Advancement east_blight = build(data(Items.OAK_LOG, "axes_of_old", false, FrameType.TASK, true, true, false)
//		.setTitle("Axes of Old").setDescription("Enter the East Bight.").setTitleStype(TextFormatting.GREEN), 
//		addBiomeTrigger(make(), LOTRBiomes.EAST_BLIGHT).parent(rhovanion));

		Advancement dagorlad = build(true, data(LOTRItems.MORDOR_GRAVEL.get(), "battle_plain", false, FrameType.TASK, true, true, false)
		.setTitle("Battle Plain").setDescription("Enter the plain of Dagorlad.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.DAGORLAD).parent(rhovanion));

		Advancement eastward_bound = build(true, data(Items.GRASS_BLOCK, "eastward_bound", false, FrameType.TASK, true, true, false)
		.setTitle("Eastward Bound").setDescription("Enter the Wilderland.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(addBiomeTrigger(make(), LOTRBiomes.WILDERLAND), LOTRBiomes.NORTHERN_WILDERLAND).requirements(IRequirementsStrategy.OR).parent(rhovanion));

//		Advancement field_of_victory = build(data(Items.AZURE_BLUET, "field_of_victory", false, FrameType.TASK, true, true, false)
//		.setTitle("Field of Victory").setDescription("Enter the Field of Celebrant.").setTitleStype(TextFormatting.GREEN), 
//		addBiomeTrigger(make(), LOTRBiomes.field_of_celebrant).parent(rhovanion));
		
		Advancement here_be_dragons = build(true, data(Items.STONE, "here_be_dragons", false, FrameType.TASK, true, true, false)
		.setTitle("Here be Dragons?").setDescription("Enter the Grey Mountains.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(addBiomeTrigger(make(), LOTRBiomes.GREY_MOUNTAINS), LOTRBiomes.GREY_MOUNTAINS_FOOTHILLS).requirements(IRequirementsStrategy.OR).parent(rhovanion));

		Advancement past_the_rushes_past_the_reeds = build(true, data(LOTRItems.REEDS.get(), "past_the_rushes_past_the_reeds", false, FrameType.TASK, true, true, false)
		.setTitle("Past the Rushes, Past the Reeds").setDescription("Enter the Long Marshes.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.LONG_MARSHES).parent(rhovanion));

		Advancement watery_grave = build(true, data(LOTRItems.YELLOW_IRIS.get(), "watery_grave", false, FrameType.TASK, true, true, false)
		.setTitle("Watery Grave").setDescription("Enter the Gladden Fields.").setTitleStype(TextFormatting.GREEN), 
		addBiomeTrigger(make(), LOTRBiomes.GLADDEN_FIELDS).parent(rhovanion));
	}

	@Override
	public String getFolder() {
		return "rhovanion";
	}

}
