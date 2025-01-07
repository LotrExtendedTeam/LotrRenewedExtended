package com.github.maximuslotro.lotrrextended;

import com.github.maximuslotro.lotrrextended.client.LotrExtendedModClient;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.commands.ExtendedCommands;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.datafixers.ExtendedDataIdFixer;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGeneratorExtended;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.*;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedAttributes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlockStateProviderTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedEffects;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedFoods;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedLootFunctions;
import com.github.maximuslotro.lotrrextended.common.loot.modifiers.ExtendedGlobalLootModifiers;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipes;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.BiomeFeatureAdder;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeature;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.BiomeStructureAdder;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedConfiguredStructuresV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;

import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod{
	public static final String MODID = "lotrextended";
	private IEventBus modBus;
	public static LotrExtendedMod INSTANCE;
	public static ExtendedTickHandlerServer serverTickEventHandler;

	public LotrExtendedMod() {
		ExtendedLog.make();
		INSTANCE = this;
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LotrExtendedModClient::staticClientSetup);
		DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> LotrExtendedModServer::staticServerSetup);
		modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addGenericListener(Block.class, ExtendedDataIdFixer::fixBlockMappings);
		forgeBus.addGenericListener(Item.class, ExtendedDataIdFixer::fixItemMappings);
		modBus.addListener(this::extendedCommonSetup);
		modBus.addListener(DataGeneratorExtended::gatherData);
		forgeBus.addListener(this::extendedRegisterCommands);
		forgeBus.addListener(this::extendedRegisterDataPackListeners);
		forgeBus.addListener(EventPriority.HIGH, this::extendedAddFeatureToBiomes);
		forgeBus.addListener(this::extendesAddDimensionalSpacing);
		DevEventHandlers.registerDevListender(modBus, forgeBus);
		ExtendedServerConfig.register();
		ExtendedStructuresV5.STRUCTURE_REGISTRY.register(modBus);
		forgeBus.register(ForgeInventoryEvents.class);
		forgeBus.register(WorldEventHandlers.class);
		forgeBus.register(BannerEventHandlers.class);
		forgeBus.register(HiredUnitEventHandlers.class);
		ExtendedBlockStateProviderTypes.register(modBus);
		ExtendedAttributes.register(modBus);
		ExtendedCriteriaTriggers.generate();
		ExtendedRecipes.register(modBus);
		ExtendedParticles.register(modBus);
		ExtendedPacketHandler.register();
		ExtendedFeature.register(modBus);
		modBus.addGenericListener(Feature.class, this::extendedRegisterFeatures);
		ExtendedStats.init();
		ExtendedFoods.init();
		ExtendedSounds.register(modBus);
		ExtendedGlobalLootModifiers.register(modBus);
		ExtendedLootFunctions.setup();
		ExtendedTags.init();
		ExtendedEffects.register();
		serverTickEventHandler = new ExtendedTickHandlerServer();
	}

	public void extendedCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        	ExtendedStructureProcessors.init();
            ExtendedStructuresV5.setupStructures();
            ExtendedConfiguredStructuresV5.registerConfiguredStructures();
        });
    }

	public IEventBus getModBus() {
		return modBus;
	}

	public void extendedRegisterDataPackListeners(AddReloadListenerEvent event) {
		event.addListener(ExtendedStructureEntitySpawnManager.INSTANCE);
	}

	public void extendedRegisterCommands(RegisterCommandsEvent event) {
		ExtendedCommands.registerCommands(event.getDispatcher(), event.getEnvironment());
	}

	public void extendedRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
		ExtendedFeature.register(event);
	}

	public ModFile getModfile() {
		return ModList.get().getModFileById(MODID).getFile();
	}

	public void extendedAddFeatureToBiomes(BiomeLoadingEvent event) {
		BiomeStructureAdder.addStructuresToBiomes(event);
		BiomeFeatureAdder.addFeatureToBiomes(event);
	}

    public void extendesAddDimensionalSpacing(WorldEvent.Load event) {
		if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            ServerChunkProvider provider = serverWorld.getChunkSource();
            if(!(provider.generator instanceof MiddleEarthChunkGenerator)){
            	return;
            }
            BiomeStructureAdder.addDimensionalSpacing(provider);
        }
    }
}
