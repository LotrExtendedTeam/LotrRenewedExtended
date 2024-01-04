package lotr.common.datagen;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

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
		generator.addProvider(new ExtendedBlockstateGenerator(generator, LotrExtendedMod.MODID, existingFileHelper));
		//generator.addProvider(new ExtendedLootTableGenerator(generator));
		generator.addProvider(new ExtendedCraftingRecipiesGenerator(generator));
	}
}
