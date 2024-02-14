package com.github.maximuslotro.lotroExtended;

import com.github.maximuslotro.lotroExtended.client.LotrExtendedModClient;
import com.github.maximuslotro.lotroExtended.commands.ExtendedCommands;
import com.github.maximuslotro.lotroExtended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotroExtended.common.init.ExtendedTags;
import com.github.maximuslotro.lotroExtended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotroExtended.config.ExtendedServerConfig;
import com.github.maximuslotro.lotroExtended.datagen.DataGeneratorExtended;
import com.github.maximuslotro.lotroExtended.eventhandlers.ForgeInventoryEvents;
import com.github.maximuslotro.lotroExtended.eventhandlers.WorldEventHandlers;
import com.github.maximuslotro.lotroExtended.stats.ExtendedStats;

import lotr.common.network.ExtendedPacketHandler;
import lotr.common.structureV5.BiomeStructureAdder;
import lotr.common.structureV5.ExtendedConfiguredStructuresV5;
import lotr.common.structureV5.ExtendedStructuresV5;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
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
		forgeBus.register(WorldEventHandlers.class);
		ExtendedParticles.register();
		ExtendedPacketHandler.register();
		ExtendedStats.init();
		ExtendedLootModifiers.register();
		ExtendedTags.init();
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
		BiomeStructureAdder.addStructuresToBiomes(event);
	}

    public void addDimensionalSpacing(final WorldEvent.Load event) {
		if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            ServerChunkProvider provider = serverWorld.getChunkSource();
            if(!(provider.generator instanceof MiddleEarthChunkGenerator)){
            	return;
            }
            BiomeStructureAdder.addDimensionalSpacing(provider);
        }
    }

	public void gatherData(GatherDataEvent event) {
		DataGeneratorExtended.gatherData(event);
	}
}
