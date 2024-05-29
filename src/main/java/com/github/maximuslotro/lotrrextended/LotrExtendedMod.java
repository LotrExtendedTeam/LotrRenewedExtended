package com.github.maximuslotro.lotrrextended;

import com.github.maximuslotro.lotrrextended.client.LotrExtendedModClient;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.commands.ExtendedCommands;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.datafixers.ExtendedDataIdFixer;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGeneratorExtended;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.DevEventHandlers;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.ForgeInventoryEvents;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.WorldEventHandlers;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedFoods;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.BiomeStructureAdder;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedConfiguredStructuresV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;

import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
public class LotrExtendedMod{

	public static final String MODID = "lotrextended";
	IEventBus modBus;
	IEventBus forgeBus;
	public static LotrExtendedMod INSTANCE;
	public LotrExtendedMod() {
		ExtendedLog.make();
		INSTANCE = this;
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LotrExtendedModClient::staticClientSetup);
		modBus = FMLJavaModLoadingContext.get().getModEventBus();
		forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addGenericListener(Block.class, ExtendedDataIdFixer::fixBlockMappings);
		forgeBus.addGenericListener(Item.class, ExtendedDataIdFixer::fixItemMappings);
		modBus.addListener(this::setup);
		modBus.addListener(DataGeneratorExtended::gatherData);
		forgeBus.addListener(this::registerCommands);
		forgeBus.addListener(EventPriority.HIGH, this::addStructuresToBiomes);
		forgeBus.addListener(this::addDimensionalSpacing);
		DevEventHandlers.registerDevListender(modBus, forgeBus);
		ExtendedServerConfig.register();
		ExtendedStructuresV5.STRUCTURE_REGISTRY.register(modBus);
		forgeBus.register(ForgeInventoryEvents.class);
		forgeBus.register(WorldEventHandlers.class);
		ExtendedCriteriaTriggers.generate();
		ExtendedParticles.register(modBus);
		ExtendedPacketHandler.register();
		ExtendedStats.init();
		ExtendedFoods.init();
		ExtendedSounds.register(modBus);
		ExtendedLootModifiers.register(modBus);
		ExtendedTags.init();
	}

	public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        	ExtendedStructureProcessors.init();
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

	public void addStructuresToBiomes(BiomeLoadingEvent event) {
		BiomeStructureAdder.addStructuresToBiomes(event);
	}

    public void addDimensionalSpacing(WorldEvent.Load event) {
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
