package lotr.common.datagen;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LotrExtendedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent evt) {
		evt.getGenerator().addProvider(new ExtendedBlockstateGenerator(evt.getGenerator(), LotrExtendedMod.MODID, evt.getExistingFileHelper()));
		evt.getGenerator().addProvider(new ExtendedLootTableGenerator(evt.getGenerator()));
		evt.getGenerator().addProvider(new ExtendedCraftingRecipiesGenerator(evt.getGenerator()));
	}
}
