package lotr.common.structure.StructureV4;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

public class ExtendedStructureLists {
	public static List<StructureFeature<?, ?>> SHIRE_STRUCTURES = new ArrayList<>();

	public static void startup() {
		SHIRE_STRUCTURES.add(ExtendedStructures.CONFIGURED_HOBBIT_TAVERN_SMALL);
	}
	public static void addStructureType(BiomeGenerationSettingsBuilder builder,List<StructureFeature<?, ?>> structures) {
		for(StructureFeature<?, ?> structure : structures) {
			builder.addStructureStart(structure);
		}
	}
}
