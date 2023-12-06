package lotr.common.world.biome;

import lotr.common.world.gen.feature.ExtendedFeatures;
import lotr.common.world.gen.feature.structure.HobbitHoleFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class ExtendedBiomeFeatures {
	
	private static ConfiguredPlacement<?> ExtendedplaceHeightmapChance(int chance) {
		return Placement.HEIGHTMAP.configured(IPlacementConfig.NONE).squared().chance(chance);
	}

	public static void addHobbitHoleStructure(BiomeGenerationSettings.Builder builder, int chance, BlockState block) {
		HobbitHoleFeatureConfig config = new HobbitHoleFeatureConfig(block);
		ConfiguredPlacement<?> placement = ExtendedplaceHeightmapChance(chance);
		builder.addFeature(Decoration.SURFACE_STRUCTURES, ExtendedFeatures.HOBBIT_BURROW.configured(config).decorated(placement));
	}
}
