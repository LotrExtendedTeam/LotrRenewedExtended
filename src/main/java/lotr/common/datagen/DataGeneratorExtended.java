package lotr.common.datagen;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import lotr.common.LOTRMod;
import lotr.common.datagen.crafting.ExtendedCraftingRecipiesGenerator;
import lotr.common.datagen.language.ExtendedLanguageGenerator;
import lotr.common.datagen.loot_tables.ExtendedLootTableGenerator;
import lotr.common.datagen.models.ExtendedBlockstateModelGenerator;
import lotr.common.datagen.models.ExtendedItemModelGenerator;
import lotr.common.datagen.tags.ExtendedBlockTagGenerator;
import lotr.common.datagen.tags.ExtendedFluidTagGenerator;
import lotr.common.datagen.tags.ExtendedItemTagGenerator;
import lotr.common.loot.ExtendedLootTables;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGeneratorExtended {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeClient()) {
			generator.addProvider(new ExtendedBlockstateModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedItemModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedSoundDefinitionsGenerator(generator, LOTRMod.MOD_ID, existingFileHelper));
			generator.addProvider(new ExtendedLanguageGenerator(generator, LotrExtendedMod.MODID,"en_us"));
		}
		if (event.includeServer()) {
			ExtendedLootTables.init();
			BlockTagsProvider blocktags = new ExtendedBlockTagGenerator(generator, LotrExtendedMod.MODID, existingFileHelper);
			generator.addProvider(blocktags);
			generator.addProvider(new ExtendedItemTagGenerator(generator, blocktags, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedFluidTagGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedLootTableGenerator(generator));
			generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
			generator.addProvider(new ExtendedGlobalLootModifierGenerator(generator, LotrExtendedMod.MODID));
		}
	}
}
