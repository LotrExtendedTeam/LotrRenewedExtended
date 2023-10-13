package lotr.common.world.biome;

import lotr.common.world.gen.feature.ExtendedFeatures;
import lotr.common.world.gen.feature.structure.HobbitHoleFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class ExtendedBiomeFeatures {
	public static void addHobbitHoleStructure(BiomeGenerationSettings.Builder builder, int chance, BlockState block) {
		HobbitHoleFeatureConfig config = new HobbitHoleFeatureConfig(block);
	    ExtendedFeatures.HOBBIT_BURROW.configured(config);
	    ConfiguredPlacement<NoPlacementConfig> configuredPlacement = Placement.HEIGHTMAP.configured(new NoPlacementConfig());
	    configuredPlacement.chance(chance);
	    builder.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ExtendedFeatures.HOBBIT_BURROW.configured(config).decorated(configuredPlacement.chance(chance)));
	  }
}
