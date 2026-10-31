package com.github.maximuslotro.lotrrextended.common.datagen.advancements.regions;


import java.util.List;

import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedBaseAdvancementType;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBiomePointers;
import com.google.common.collect.ImmutableList;

import lotr.common.init.LOTRItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Items;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;


public class AdvancementsProviderRhovanion extends ExtendedBaseAdvancementType{
	private static final List<RegistryKey<Biome>> Rhovanion_BIOMES = ImmutableList.of(ExtendedBiomePointers.ANDUIN_HILLS, ExtendedBiomePointers.VALES_OF_ANDUIN);

	@Override
	public void generateAdvancements() {
		Advancement rhovanion = build(true, dataMain(Items.OAK_SAPLING, "the_great_river", FrameType.TASK)
				.setTitle("The Great River").setDescription("Enter the region of Anduin."),
				addBiomeTriggers(make(), Rhovanion_BIOMES));

		Advancement brown_lands = build(true, data(Items.DIRT, "barren_wasteland", FrameType.TASK)
		.setTitle("Barren Wasteland").setDescription("Enter the Brown Lands."),
		addBiomeTrigger(make(), ExtendedBiomePointers.BROWN_LANDS).parent(rhovanion));

		Advancement a_nasty_drop = build(true, data(Items.STONE, "a_nasty_drop", FrameType.TASK)
		.setTitle("A Nasty Drop").setDescription("Enter the Emyn Muil."),
		addBiomeTrigger(make(), ExtendedBiomePointers.EMYN_MUIL).parent(rhovanion));

//		Advancement east_blight = build(data(Items.OAK_LOG, "axes_of_old", FrameType.TASK)
//		.setTitle("Axes of Old").setDescription("Enter the East Bight."),
//		addBiomeTrigger(make(), ExtendedBiomes.EAST_BLIGHT).parent(rhovanion));

		Advancement dagorlad = build(true, data(LOTRItems.MORDOR_GRAVEL.get(), "battle_plain", FrameType.TASK)
		.setTitle("Battle Plain").setDescription("Enter the plain of Dagorlad."),
		addBiomeTrigger(make(), ExtendedBiomePointers.DAGORLAD).parent(rhovanion));

		Advancement eastward_bound = build(true, data(Items.GRASS_BLOCK, "eastward_bound", FrameType.TASK)
		.setTitle("Eastward Bound").setDescription("Enter the Wilderland."),
		addBiomeTriggers(make(), ExtendedBiomePointers.WILDERLAND, ExtendedBiomePointers.NORTHERN_WILDERLAND).parent(rhovanion));

//		Advancement field_of_victory = build(data(Items.AZURE_BLUET, "field_of_victory", FrameType.TASK)
//		.setTitle("Field of Victory").setDescription("Enter the Field of Celebrant."),
//		addBiomeTrigger(make(), ExtendedBiomes.field_of_celebrant).parent(rhovanion));

		Advancement here_be_dragons = build(true, data(Items.STONE, "here_be_dragons", FrameType.TASK)
		.setTitle("Here be Dragons?").setDescription("Enter the Grey Mountains."),
		addBiomeTriggers(make(), ExtendedBiomePointers.GREY_MOUNTAINS, ExtendedBiomePointers.GREY_MOUNTAINS_FOOTHILLS).parent(rhovanion));

		Advancement past_the_rushes_past_the_reeds = build(true, data(LOTRItems.REEDS.get(), "past_the_rushes_past_the_reeds", FrameType.TASK)
		.setTitle("Past the Rushes, Past the Reeds").setDescription("Enter the Long Marshes."),
		addBiomeTrigger(make(), ExtendedBiomePointers.LONG_MARSHES).parent(rhovanion));

		Advancement watery_grave = build(true, data(LOTRItems.YELLOW_IRIS.get(), "watery_grave", FrameType.TASK)
		.setTitle("Watery Grave").setDescription("Enter the Gladden Fields."),
		addBiomeTrigger(make(), ExtendedBiomePointers.GLADDEN_FIELDS).parent(rhovanion));
	}

	@Override
	public String getFolder() {
		return "rhovanion";
	}

	@Override
	public int getLegacyCount() {
		return 10;
	}

}
