package com.github.maximuslotro.lotroExtended;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.client.LotrExtendedModClient;
import com.github.maximuslotro.lotroExtended.commands.ExtendedCommands;
import com.github.maximuslotro.lotroExtended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotroExtended.config.ExtendedServerConfig;
import com.github.maximuslotro.lotroExtended.datagen.DataGeneratorExtended;
import com.github.maximuslotro.lotroExtended.eventhandlers.ForgeInventoryEvents;
import com.github.maximuslotro.lotroExtended.stats.ExtendedStats;

import lotr.common.structure.scanning.StructureScanner;
import lotr.common.structureV5.ExtendedConfiguredStructuresV5;
import lotr.common.structureV5.ExtendedStructuresV5;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod {

	public static final String MODID = "lotrextended";

	public static LotrExtendedMod INSTANCE;
	public LotrExtendedMod() {
		ExtendedLog.make();
		INSTANCE = this;
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LotrExtendedModClient::staticClientSetup);
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		modBus.addListener(this::setup);
		modBus.addListener(DataGeneratorExtended::gatherData);
		forgeBus.addListener(this::registerCommands);
		forgeBus.addListener(EventPriority.HIGH, this::addStructuresToBiomes);
		forgeBus.addListener(this::addDimensionalSpacing);
		ExtendedServerConfig.register();
		ExtendedStructuresV5.STRUCTURE_REGISTRY.register(modBus);
		forgeBus.register(ForgeInventoryEvents.class);
		ExtendedStats.init();
		ExtendedLootModifiers.register();
		if(!FMLEnvironment.dist.equals(Dist.CLIENT)) {
			MinecraftForge.EVENT_BUS.register(new StructureScanner());
		}
	}

	public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ExtendedStructuresV5.setupStructures();
            ExtendedConfiguredStructuresV5.registerConfiguredStructures();
        });
    }

	public void registerCommands(RegisterCommandsEvent event) {
		ExtendedCommands.registerCommands(event.getDispatcher(), event.getEnvironment());
	}

	public ModFile getModfile() {
		return ModList.get().getModFileById(MODID).getFile();
	}

	public void addStructuresToBiomes(final BiomeLoadingEvent event) {
		if(event.getName()!=null) {
			if(event.getName().toString().equals("lotr:shire")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
			}
		}
	}

    public void addDimensionalSpacing(final WorldEvent.Load event) {
		if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            ServerChunkProvider provider = serverWorld.getChunkSource();
            if(!(provider.generator instanceof MiddleEarthChunkGenerator)){
            	return;
            }
            ExtendedLog.info("Adding structures to valid dimension instanceof MiddleEarthChunkGenerator");
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(provider.generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ExtendedStructuresV5.HOBBIT_HOUSE.get(), DimensionStructuresSettings.DEFAULTS.get(ExtendedStructuresV5.HOBBIT_HOUSE.get()));
            provider.generator.getSettings().structureConfig = tempMap;
        }
    }

	public void gatherData(GatherDataEvent event) {
		DataGeneratorExtended.gatherData(event);
	}
}
