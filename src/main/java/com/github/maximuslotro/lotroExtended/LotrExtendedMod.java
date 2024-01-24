package com.github.maximuslotro.lotroExtended;

import java.util.Map;

import com.github.maximuslotro.lotroExtended.client.LotrExtendedModClient;
import com.github.maximuslotro.lotroExtended.common.loot.ExtendedLootModifiers;
import com.github.maximuslotro.lotroExtended.eventhandlers.ForgeInventoryEvents;
import com.github.maximuslotro.lotroExtended.stats.ExtendedStats;

import lotr.common.structure.ExtendedStructures;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;

@Mod(value = LotrExtendedMod.MODID)
public class LotrExtendedMod {

	public static final String MODID = "lotrextended";

	public static LotrExtendedMod INSTANCE;
	public LotrExtendedMod() {
		INSTANCE = this;
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LotrExtendedModClient::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(ForgeInventoryEvents.class);

		ExtendedLog.make();
		ExtendedStats.init();
		ExtendedLootModifiers.register();
	}

	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		InfoCommand.register(event.getDispatcher());
	}

	public ModFile getModfile() {
		return ModList.get().getModFileById(MODID).getFile();
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeModification(final BiomeLoadingEvent event) {
		//TODO add structures to lotr biomes here
		event.getName();
		if(event.getName()!=null) {
			if(event.getName().getNamespace().equals("lotr")) {
				//System.out.println("Loaded lotr biome: "+event.getName().toString());
			}
		}
		
	}

//	public void setup(final FMLCommonSetupEvent event)
//    {
//        event.enqueueWork(() -> {
//            ExtendedStructures.init();
//            ExtendedConfiguredStructures.register();
//        });
//    }
//
//	public void addDimensionalSpacing(final WorldEvent.Load event) {
//        if(event.getWorld() instanceof ServerWorld){
//            ServerWorld serverWorld = (ServerWorld)event.getWorld();
//            ServerChunkProvider provider = serverWorld.getChunkSource();
//            if(!(serverWorld.getChunkSource().getGenerator() instanceof MiddleEarthChunkGenerator)|| serverWorld.dimension().equals(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)){
//            	provider.generator.getSettings().structureConfig = editMap(new HashMap<>(provider.generator.getSettings().structureConfig()), true);
//            	return;
//                }
//            provider.generator.getSettings().structureConfig = editMap(new HashMap<>(provider.generator.getSettings().structureConfig()), false);
//
//        }
//	}

	public Map<Structure<?>, StructureSeparationSettings> editMap(Map<Structure<?>, StructureSeparationSettings> map, boolean remove){
		if(remove) {
			map.remove(ExtendedStructures.HOBBIT_TAVERN_SMALL.get());
		}
		else {
			map.putIfAbsent(ExtendedStructures.HOBBIT_TAVERN_SMALL.get(), DimensionStructuresSettings.DEFAULTS.get(ExtendedStructures.HOBBIT_TAVERN_SMALL.get()));
		}
		return map;
	}
}
