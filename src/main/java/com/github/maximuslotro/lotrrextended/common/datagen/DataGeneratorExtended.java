package com.github.maximuslotro.lotrrextended.common.datagen;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedAdvancementProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.aggregation.ExtendedBiomeDataAggregator;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.ExtendedCraftingRecipiesGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.ExtendedCrucibleRecipiesGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedCaptainProfileGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedEntitySettingsGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedEntitySettingsMissingValidator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedStructureSpawnerGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedHiredUnitProfileGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedTraderProfileGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.language.ExtendedLanguageGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.loot_tables.ExtendedLootTableGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.map.ExtendedMapWaypointGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.map.ExtendedTravelRegions;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedBlockstateModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedEntityTextureValidator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedItemModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.structures.ExtendedStructureProcessorListGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedTagRelatedGenerators;
import com.github.maximuslotro.lotrrextended.common.datagen.world.ExtendedBiomeInformationGenerator;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.world.ExtendedInvasions;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGeneratorExtended {
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeServer()) {
			// Hot load data
			ExtendedLootTables.init();
			ExtendedTravelRegions.init();
			// Make Data
			ExtendedTagRelatedGenerators.gatherData(generator, existingFileHelper, LotrExtendedMod.MODID);
			generator.addProvider(new ExtendedLootTableGenerator(generator));
			generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
			generator.addProvider(new ExtendedCrucibleRecipiesGenerator(generator));
			generator.addProvider(new ExtendedGlobalLootModifierGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedAdvancementProvider(generator, existingFileHelper));
			generator.addProvider(new ExtendedHiredUnitProfileGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedTraderProfileGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedCaptainProfileGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedStructureSpawnerGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedEntitySettingsGenerator(generator, existingFileHelper));
			generator.addProvider(new ExtendedMapWaypointGenerator(generator, existingFileHelper, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedStructureProcessorListGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedBiomeInformationGenerator(generator, LotrExtendedMod.MODID));
			// Make Data from Data
			generator.addProvider(new ExtendedBiomeDataAggregator(generator, existingFileHelper));
			// Validate Data
			generator.addProvider(new ExtendedEntitySettingsMissingValidator(existingFileHelper));
			generator.addProvider(new ExtendedItemsValidator());
			generator.addProvider(new ExtendedStructureValidator(generator, existingFileHelper, LotrExtendedMod.MODID));
		}
		if (event.includeClient()) {
			// Hot load data
			ExtendedClientConfig.registerDataGen();
			ExtendedInvasions.DURINS_FOLK.getTypeKey();// Force populate invasion language keys
			// Make Data
			generator.addProvider(new ExtendedBlockstateModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedItemModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedEntityTextureValidator(LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedSoundDefinitionsGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedLanguageGenerator(generator, LotrExtendedMod.MODID,"en_us"));
		}
	}
}
