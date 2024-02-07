package lotr.common.structureV4.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lotr.common.structureV4.structure.standalone.*;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedStructures {
    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, LotrExtendedMod.MODID);
	public static final List<RegistryObject<Structure<NoFeatureConfig>>> STRUCTURESSUPPLIERHOLDER = new ArrayList<>();
	public static final int SALT = 785555898;
	// Structures
	// #######################SHIRE STANDALONE STRUCTURES
	public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_TAVERN_SMALL = DEFERRED_REGISTRY_STRUCTURE.register("hobbit_tavern_small", () -> new ExtendedHobbitTavernStructure(NoFeatureConfig.CODEC));
//	public static final Structure<NoFeatureConfig> HOBBIT_BARN = new ExtendedHobbitBarnStructure(NoFeatureConfig.CODEC);
//	public static final Structure<NoFeatureConfig> HOBBIT_HOUSE = new ExtendedHobbitHouseStructure(NoFeatureConfig.CODEC);
//	public static final Structure<NoFeatureConfig> HOBBIT_HOLE_SMALL = new ExtendedHobbitHoleSmallStructure(NoFeatureConfig.CODEC);
//	public static final Structure<NoFeatureConfig> HOBBIT_WINDMILL = new ExtendedHobbitWindmillStructure(NoFeatureConfig.CODEC);

	public static void register(RegistryObject<Structure<NoFeatureConfig>> structure, boolean editLand, int spacing, int seperation) {
		if(spacing-seperation<= 0) {
			throw new IllegalArgumentException("Structure SeperationSettings setup detected an invalid configuration where seperation>=spacing: "+seperation+">="+spacing);
		}
		StructureSeparationSettings settings = new StructureSeparationSettings(spacing, seperation, SALT);
		Structure.STRUCTURES_REGISTRY.put(structure.get().getRegistryName().toString(), structure.get());
		if (editLand) {Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure.get()).build();}
		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure.get(), settings).build();
		STRUCTURESSUPPLIERHOLDER.add(structure);
	}

	public static void init() {
		STRUCTURESSUPPLIERHOLDER.clear();
		// For StructureSeparationSettings, the first value is the average distance in chunks between spawn attempts (spacing)
		// the second value it minimum distance in chunks between spawn attempts (separation) (this must always be less than the first!
		// Setting spacing/separation to be 1/0, you can then have the structure spawn only in certain chunks in Structure.isFeatureChunk()
		// Setting spacing/separation to be 2/1, you can then have the structure spawn in every chunk
		register(HOBBIT_TAVERN_SMALL, true, 2, 1);
//		register(event, HOBBIT_BARN, CONFIGURED_HOBBIT_BARN, extendedLoc("hobbit_barn"), true, 5, 2);
//		register(event, HOBBIT_HOUSE, CONFIGURED_HOBBIT_HOUSE, extendedLoc("hobbit_house"), true, 3, 1);
//		register(event, HOBBIT_HOLE_SMALL, CONFIGURED_HOBBIT_HOLE_SMALL, extendedLoc("hobbit_hole_small"), true, 3, 1);
//		register(event, HOBBIT_WINDMILL, CONFIGURED_HOBBIT_WINDMILL, extendedLoc("hobbit_windmill"), true, 7, 3);
	}

	public static ResourceLocation extendedLoc(String name) {
		return new ResourceLocation(LotrExtendedMod.MODID, name);
	}

	public static void addStructureSeparationSettingsToDimension(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            ServerChunkProvider provider = serverWorld.getChunkSource();
            if(!(serverWorld.getChunkSource().getGenerator() instanceof MiddleEarthChunkGenerator)){
            	provider.generator.getSettings().structureConfig = editMap(new HashMap<>(provider.generator.getSettings().structureConfig()), true);
            	return;
            }
            ExtendedLog.info("Adding structures to valid dimension instanceof MiddleEarthChunkGenerator");
            provider.generator.getSettings().structureConfig = editMap(new HashMap<>(provider.generator.getSettings().structureConfig()), false);
        }
	}

	public static Map<Structure<?>, StructureSeparationSettings> editMap(Map<Structure<?>, StructureSeparationSettings> map, boolean remove){
		for(RegistryObject<Structure<NoFeatureConfig>> str : STRUCTURESSUPPLIERHOLDER) {
			if(remove) {
				if(map.containsKey(str.get())) {
					map.remove(str.get());
				}
			}
			else {
				map.putIfAbsent(str.get(), DimensionStructuresSettings.DEFAULTS.get(str.get()));
			}
		}
		return map;
	}
}
