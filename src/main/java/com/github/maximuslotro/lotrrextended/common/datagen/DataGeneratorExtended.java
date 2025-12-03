package com.github.maximuslotro.lotrrextended.common.datagen;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedAdvancementProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.aggregation.ExtendedBiomeInfoGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.ExtendedCraftingRecipiesGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.ExtendedEntityRelatedGenerators;
import com.github.maximuslotro.lotrrextended.common.datagen.language.ExtendedLanguageGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.loot_tables.ExtendedLootTableGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedBlockstateModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedEntityTextureValidator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedItemModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedTagRelatedGenerators;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGeneratorExtended {
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeServer()) {
			ExtendedLootTables.init();
			ExtendedTagRelatedGenerators.gatherData(generator, existingFileHelper, LotrExtendedMod.MODID);
			generator.addProvider(new ExtendedLootTableGenerator(generator));
			generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
			generator.addProvider(new ExtendedGlobalLootModifierGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedAdvancementProvider(generator, existingFileHelper));
			ExtendedEntityRelatedGenerators.gatherData(generator, existingFileHelper, LotrExtendedMod.MODID);
			generator.addProvider(new ExtendedBiomeInfoGenerator(generator, existingFileHelper));
			//generator.addProvider(new ExtendedItemsValidator());
		}
		if (event.includeClient()) {
			ExtendedClientConfig.registerDataGen();
			generator.addProvider(new ExtendedBlockstateModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedItemModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedEntityTextureValidator(LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedSoundDefinitionsGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedLanguageGenerator(generator, LotrExtendedMod.MODID,"en_us"));
		}
	}
}
