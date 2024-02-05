package com.github.maximuslotro.lotroExtended;

import com.github.maximuslotro.lotroExtended.client.LotrExtendedModClient;
import com.github.maximuslotro.lotroExtended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotroExtended.eventhandlers.ForgeInventoryEvents;
import com.github.maximuslotro.lotroExtended.stats.ExtendedStats;

import lotr.common.structure.StructureV4.ExtendedConfiguredStructures;
import lotr.common.structure.StructureV4.ExtendedStructureLists;
import lotr.common.structure.StructureV4.ExtendedStructurePieces;
import lotr.common.structure.StructureV4.ExtendedStructures;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod {

	public static final String MODID = "lotrextended";

	public static LotrExtendedMod INSTANCE;
	public LotrExtendedMod() {
		INSTANCE = this;
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LotrExtendedModClient::clientSetup);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::addStructuresToBiomes);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, ExtendedStructures::addStructureSeparationSettingsToDimension);
		MinecraftForge.EVENT_BUS.register(ForgeInventoryEvents.class);
		
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ExtendedStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);
 
		ExtendedLog.make();
		ExtendedStats.init();
		ExtendedLootModifiers.register();
	}

	public void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
        	ExtendedStructures.init();
           	ExtendedConfiguredStructures.genConfiguredStructures();
    		ExtendedStructurePieces.init();
    		ExtendedStructureLists.startup();
        });
    }

	public void registerCommands(RegisterCommandsEvent event) {
		InfoCommand.register(event.getDispatcher());
	}

	public ModFile getModfile() {
		return ModList.get().getModFileById(MODID).getFile();
	}

	public void addStructuresToBiomes(final BiomeLoadingEvent event) {
		if(event.getName()!=null) {
			if(event.getName().toString().equals("lotr:shire")) {
				ExtendedStructureLists.addStructureType(event.getGeneration(), ExtendedStructureLists.SHIRE_STRUCTURES);
			}
		}
	}
}
