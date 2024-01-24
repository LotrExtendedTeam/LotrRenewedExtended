package lotr.common.structure.StructureV4;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lotr.common.structure.StructureV4.standalone.HobbitTavernStructure;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ExtendedStructures {
	public static final Map<Structure<?>, StructureSeparationSettings> SETTINGS = new HashMap<>();
	public static final int SALT = 785555898;
	// Structures
	// #######################SHIRE STANDALONE STRUCTURES
	public static final Structure<NoFeatureConfig> HOBBIT_TAVERN_SMALL = new HobbitTavernStructure(NoFeatureConfig.CODEC);
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_SMALL = ExtendedStructures.HOBBIT_TAVERN_SMALL.configured(IFeatureConfig.NONE);

	public static void register(RegistryEvent.Register<Structure<?>> event,
			Structure<?> structure, StructureFeature<?, ?> feature, ResourceLocation location, boolean editLand, int spacing, int seperation) {
		if(spacing-seperation<= 0) {
			throw new IllegalArgumentException("Structure SeperationSettings setup detected an invalid configuration where seperation>=spacing: "+seperation+">="+spacing);
		}
		StructureSeparationSettings settings = new StructureSeparationSettings(spacing, seperation, SALT);
		event.getRegistry().register(structure.setRegistryName(location));
		Structure.STRUCTURES_REGISTRY.put(location.getNamespace(), structure);
		if (editLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}
		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, settings).build();
		SETTINGS.put(structure, settings);
		Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(location.getNamespace(), "configured_"+location.getPath()), feature);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(structure, feature);
	}

	public static void init(RegistryEvent.Register<Structure<?>> event) {
		SETTINGS.clear();
		// For StructureSeparationSettings, the first value is the average distance in chunks between spawn attempts (spacing)
		// the second value it minimum distance in chunks between spawn attempts (separation) (this must always be less than the first!
		// Setting spacing/separation to be 1/0, you can then have the structure spawn only in certain chunks in Structure.isFeatureChunk()
		register(event, HOBBIT_TAVERN_SMALL, CONFIGURED_HOBBIT_TAVERN_SMALL, extendedLoc("hobbit_tavern_small"), true, 2, 1);
		ExtendedStructurePieces.init();
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
            provider.generator.getSettings().structureConfig = editMap(new HashMap<>(provider.generator.getSettings().structureConfig()), false);

        }
	}

	public static Map<Structure<?>, StructureSeparationSettings> editMap(Map<Structure<?>, StructureSeparationSettings> map, boolean remove){
		if(remove) {
			ExtendedLog.info("Removing structures from invalid dimension generator");
			for(Structure<?> str : ExtendedStructures.SETTINGS.keySet()) {
				map.remove(str);
			}
		}
		else {
			ExtendedLog.info("Adding structures to valid dimension generator");
			map.putAll(ExtendedStructures.SETTINGS);
		}
		return map;
	}
}
