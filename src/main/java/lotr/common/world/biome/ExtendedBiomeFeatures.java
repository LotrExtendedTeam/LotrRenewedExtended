package lotr.common.world.biome;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class ExtendedBiomeFeatures {
	
	private static ConfiguredPlacement<?> ExtendedplaceHeightmapChance(int chance) {
		return Placement.HEIGHTMAP.configured(IPlacementConfig.NONE).squared().chance(chance);
	}

	public static void addStructure(BiomeGenerationSettings.Builder builder, Decoration type, int chance, ConfiguredFeature<?, ?> feature) {
		ConfiguredPlacement<?> placement = ExtendedplaceHeightmapChance(chance);
		builder.addFeature(Decoration.SURFACE_STRUCTURES, feature.decorated(placement));
	}
}
