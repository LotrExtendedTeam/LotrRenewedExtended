package lotr.common.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.ExtendedLog;

import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeStructureAdder {
	public static void addDimensionalSpacing(ServerChunkProvider provider) {
        ExtendedLog.info("Adding structures to valid dimension instanceof MiddleEarthChunkGenerator");
        Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(provider.generator.getSettings().structureConfig());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_BARN.get());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_HOLE_SMALL.get());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_HOUSE.get());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_PICNIC_BLANKET.get());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_PICNIC_TABLE.get());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_TAVERN_SMALL.get());
        addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_WINDMILL.get());

        provider.generator.getSettings().structureConfig = tempMap;
    }
	private static void addStructureToMap(Map<Structure<?>, StructureSeparationSettings> tempMap, Structure<NoFeatureConfig> structure) {
		tempMap.putIfAbsent(structure, DimensionStructuresSettings.DEFAULTS.get(structure));
	}

	public static void addStructuresToBiomes(BiomeLoadingEvent event) {
		if(event.getName()!=null) {
			if(event.getName().toString().equals("lotr:shire")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
			}
		}
	}
}
