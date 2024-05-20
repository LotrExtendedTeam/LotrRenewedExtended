package com.github.maximuslotro.lotrrextended.common.datagen;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.datagen.advancements.ExtendedAdvancementProvider;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.ExtendedCraftingRecipiesGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.language.ExtendedLanguageGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.loot_tables.ExtendedLootTableGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedBlockstateModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.models.ExtendedItemModelGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedBlockTagGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedFluidTagGenerator;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedItemTagGenerator;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGeneratorExtended {
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeServer()) {
			ExtendedLootTables.init();
			BlockTagsProvider blocktags = new ExtendedBlockTagGenerator(generator, LotrExtendedMod.MODID, existingFileHelper);
			generator.addProvider(blocktags);
			generator.addProvider(new ExtendedItemTagGenerator(generator, blocktags, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedFluidTagGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedLootTableGenerator(generator));
			generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
			generator.addProvider(new ExtendedGlobalLootModifierGenerator(generator, LotrExtendedMod.MODID));
			generator.addProvider(new ExtendedAdvancementProvider(generator, existingFileHelper));
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
