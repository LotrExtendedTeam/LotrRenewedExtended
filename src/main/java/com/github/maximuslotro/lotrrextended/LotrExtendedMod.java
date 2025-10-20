package com.github.maximuslotro.lotrrextended;

import com.github.maximuslotro.lotrrextended.client.ExtendedClientProxy;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.chairs.HackyChairDatabase;
import com.github.maximuslotro.lotrrextended.common.commands.ExtendedCommands;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.datafixers.ExtendedDataIdFixer;
import com.github.maximuslotro.lotrrextended.common.datagen.DataGeneratorExtended;
import com.github.maximuslotro.lotrrextended.common.eventhandlers.*;
import com.github.maximuslotro.lotrrextended.common.hiredunits.ExtendedServerHiredUnitProfileManager;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedAttributes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlockStateProviderTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedEffects;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedFoods;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.loot.conditions.ExtendedLootConditions;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedLootFunctions;
import com.github.maximuslotro.lotrrextended.common.loot.modifiers.ExtendedGlobalLootModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeSerializers;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeTypes;
import com.github.maximuslotro.lotrrextended.common.signprocessors.ExtendedSignTextProcessorManager;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedStructureEntitySpawnManager;
import com.github.maximuslotro.lotrrextended.common.stats.ExtendedStats;
import com.github.maximuslotro.lotrrextended.common.traders.ExtendedTraderProfileManager;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.BiomeFeatureAdder;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeature;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.BiomeStructureAdder;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedConfiguredStructuresV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;

import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod{
	public static final String MODID = "lotrextended";
	private IEventBus modBus;
	public static LotrExtendedMod INSTANCE;
	public static ExtendedTickHandlerServer serverTickEventHandler;
	public static final ResourceLocation EMPTY = new ResourceLocation(LotrExtendedMod.MODID, "empty");
	public static HackyChairDatabase hackyChairManager;

	public LotrExtendedMod() {
		ExtendedLog.make();
		INSTANCE = this;
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ExtendedClientProxy::staticClientSetup);
		modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addGenericListener(Block.class, ExtendedDataIdFixer::fixBlockMappings);
		forgeBus.addGenericListener(Item.class, ExtendedDataIdFixer::fixItemMappings);
		forgeBus.addGenericListener(EntityType.class, ExtendedDataIdFixer::fixEntityMappings);
		modBus.addListener(this::extendedCommonSetup);
		modBus.addListener(DataGeneratorExtended::gatherData);
		forgeBus.addListener(EventPriority.LOW, this::extendedRegisterCommands);
		forgeBus.addListener(this::extendedRegisterDataPackListeners);
		forgeBus.addListener(EventPriority.HIGH, this::extendedAddFeatureToBiomes);
		forgeBus.addListener(this::extendesAddDimensionalSpacing);
		forgeBus.addListener(this::extendedServerStopping);
		ExtendedServerConfig.register();
		ExtendedStructuresV5.STRUCTURE_REGISTRY.register(modBus);
		forgeBus.register(BannerEventHandlers.class);
		DevEventHandlers.registerDevListender(modBus, forgeBus);
		forgeBus.register(ExtendedServerModifierEventHandlers.class);
		forgeBus.register(ForgeInventoryEvents.class);
		forgeBus.register(HiredUnitEventHandlers.class);
		forgeBus.register(WorldEventHandlers.class);
		ExtendedBlockStateProviderTypes.register(modBus);
		ExtendedAttributes.register(modBus);
		ExtendedCriteriaTriggers.generate();
		ExtendedRecipeTypes.register();
		ExtendedRecipeSerializers.register(modBus);
		ExtendedParticles.register(modBus);
		ExtendedContainerTypes.register(modBus);
		ExtendedPacketHandler.register();
		ExtendedFeature.register(modBus);
		modBus.addGenericListener(Feature.class, this::extendedRegisterFeatures);
		ExtendedStats.init();
		ExtendedFoods.init();
		ExtendedSounds.register(modBus);
		ExtendedModifiers.buildModifierLists();
		ExtendedGlobalLootModifiers.register(modBus);
		ExtendedLootFunctions.setup();
		ExtendedLootConditions.setup();
		ExtendedTags.init();
		ExtendedEffects.register(modBus);
		serverTickEventHandler = new ExtendedTickHandlerServer();
		hackyChairManager = new HackyChairDatabase();
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
		event.addListener(ExtendedServerHiredUnitProfileManager.INSTANCE);
		event.addListener(ExtendedSignTextProcessorManager.INSTANCE);
		event.addListener(ExtendedTraderProfileManager.INSTANCE);
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

	public void extendedServerStopping(FMLServerStoppingEvent event) {
		hackyChairManager.handleServerStopping();
	}
}
