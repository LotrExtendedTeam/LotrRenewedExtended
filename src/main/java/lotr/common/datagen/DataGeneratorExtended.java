package lotr.common.datagen;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LotrExtendedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorExtended {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeClient()) {
			generator.addProvider(new ExtendedBlockstateModelGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
			generator.addProvider(new ExtendedItemModelGenerator(generator, existingFileHelper));
		}
		if (event.includeServer()) {
			BlockTagsProvider blocktags = new ExtendedBlockTagGenerator(generator, existingFileHelper);
			generator.addProvider(blocktags);
			generator.addProvider(new ExtendedItemTagGenerator(generator, blocktags, existingFileHelper));
			//generator.addProvider(new ExtendedFluidTagGenerator(generator, existingFileHelper));
			//generator.addProvider(new ExtendedLootTableGenerator(generator));
			//generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
		}
	}
}
