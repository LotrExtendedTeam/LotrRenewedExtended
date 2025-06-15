package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRItems;
import lotr.common.init.PreRegisteredLOTRBiome;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;


public class AdvancementsProviderRhovanion extends ExtendedBaseAdvancementType{
	private static final List<PreRegisteredLOTRBiome> Rhovanion_BIOMES = ImmutableList.of(LOTRBiomes.ANDUIN_HILLS, LOTRBiomes.VALES_OF_ANDUIN);

	@Override
	public void generateAdvancements() {
		Advancement rhovanion = build(true, dataMain(Items.OAK_SAPLING, "the_great_river", FrameType.TASK)
				.setTitle("The Great River").setDescription("Enter the region of Anduin."),
				addBiomeTriggers(make(), Rhovanion_BIOMES));

		Advancement brown_lands = build(true, data(Items.DIRT, "barren_wasteland", FrameType.TASK)
		.setTitle("Barren Wasteland").setDescription("Enter the Brown Lands."),
		addBiomeTrigger(make(), LOTRBiomes.BROWN_LANDS).parent(rhovanion));

		Advancement a_nasty_drop = build(true, data(Items.STONE, "a_nasty_drop", FrameType.TASK)
		.setTitle("A Nasty Drop").setDescription("Enter the Emyn Muil."),
		addBiomeTrigger(make(), LOTRBiomes.EMYN_MUIL).parent(rhovanion));

//		Advancement east_blight = build(data(Items.OAK_LOG, "axes_of_old", FrameType.TASK)
//		.setTitle("Axes of Old").setDescription("Enter the East Bight."),
//		addBiomeTrigger(make(), LOTRBiomes.EAST_BLIGHT).parent(rhovanion));

		Advancement dagorlad = build(true, data(LOTRItems.MORDOR_GRAVEL.get(), "battle_plain", FrameType.TASK)
		.setTitle("Battle Plain").setDescription("Enter the plain of Dagorlad."),
		addBiomeTrigger(make(), LOTRBiomes.DAGORLAD).parent(rhovanion));

		Advancement eastward_bound = build(true, data(Items.GRASS_BLOCK, "eastward_bound", FrameType.TASK)
		.setTitle("Eastward Bound").setDescription("Enter the Wilderland."),
		addBiomeTriggers(make(), LOTRBiomes.WILDERLAND, LOTRBiomes.NORTHERN_WILDERLAND).parent(rhovanion));

//		Advancement field_of_victory = build(data(Items.AZURE_BLUET, "field_of_victory", FrameType.TASK)
//		.setTitle("Field of Victory").setDescription("Enter the Field of Celebrant."),
//		addBiomeTrigger(make(), LOTRBiomes.field_of_celebrant).parent(rhovanion));

		Advancement here_be_dragons = build(true, data(Items.STONE, "here_be_dragons", FrameType.TASK)
		.setTitle("Here be Dragons?").setDescription("Enter the Grey Mountains."),
		addBiomeTriggers(make(), LOTRBiomes.GREY_MOUNTAINS, LOTRBiomes.GREY_MOUNTAINS_FOOTHILLS).parent(rhovanion));

		Advancement past_the_rushes_past_the_reeds = build(true, data(LOTRItems.REEDS.get(), "past_the_rushes_past_the_reeds", FrameType.TASK)
		.setTitle("Past the Rushes, Past the Reeds").setDescription("Enter the Long Marshes."),
		addBiomeTrigger(make(), LOTRBiomes.LONG_MARSHES).parent(rhovanion));

		Advancement watery_grave = build(true, data(LOTRItems.YELLOW_IRIS.get(), "watery_grave", FrameType.TASK)
		.setTitle("Watery Grave").setDescription("Enter the Gladden Fields."),
		addBiomeTrigger(make(), LOTRBiomes.GLADDEN_FIELDS).parent(rhovanion));
	}

	@Override
	public String getFolder() {
		return "rhovanion";
	}

}
