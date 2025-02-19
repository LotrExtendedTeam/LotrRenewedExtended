package com.github.maximuslotro.lotrrextended.common.datagen;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedAdvancementProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.ExtendedCraftingRecipiesGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.entity.hiredunitprofiles.ExtendedHiredUnitProfileGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.language.ExtendedLanguageGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.loot_tables.ExtendedLootTableGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedBlockstateModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedItemModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.structure.entities.StructureEntitySpawnerGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedBlockTagProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedFluidTagProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedItemTagProvider;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ExtendedDataGenerator {
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeServer()) {
			ExtendedLootTables.init();
			ExtendedBlockTagProvider blocktags = new ExtendedBlockTagProvider(generator, LotrExtendedMod.MODID, existingFileHelper);
			generator.addProvider(blocktags);
			generator.addProvider(new ExtendedItemTagProvider(generator, blocktags, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedFluidTagProvider(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedLootTableGenerator(generator));
			generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
			generator.addProvider(new ExtendedGlobalLootModifierGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedAdvancementProvider(generator, existingFileHelper));
			generator.addProvider(new StructureEntitySpawnerGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedHiredUnitProfileGenerator(generator, LotrExtendedMod.MODID));
		}
		if (event.includeClient()) {
			ExtendedClientConfig.registerDataGen();
			generator.addProvider(new ExtendedBlockstateModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedItemModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedSoundDefinitionsGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedLanguageGenerator(generator, LotrExtendedMod.MODID,"en_us"));
		}
	}
}
