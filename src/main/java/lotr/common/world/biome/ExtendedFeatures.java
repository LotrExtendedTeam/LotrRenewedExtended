package lotr.common.world.biome;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

/**
 * Based off of {@link net.minecraft.world.gen.feature.Features}
 * */
public class ExtendedFeatures {
	public static final ConfiguredFeature<?, ?> FLOWER_FOREST = register("lotrextended:flower_fangorn", Feature.FLOWER.configured(Configs.FANGORN_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(100));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String pId, ConfiguredFeature<FC, ?> pConfiguredFeature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, pId, pConfiguredFeature);
	}

	public static final class Placements {
		public static final ConfiguredPlacement<NoPlacementConfig> HEIGHTMAP = Placement.HEIGHTMAP.configured(IPlacementConfig.NONE);
		public static final ConfiguredPlacement<NoPlacementConfig> TOP_SOLID_HEIGHTMAP = Placement.TOP_SOLID_HEIGHTMAP.configured(IPlacementConfig.NONE);
		public static final ConfiguredPlacement<NoPlacementConfig> HEIGHTMAP_WORLD_SURFACE = Placement.HEIGHTMAP_WORLD_SURFACE.configured(IPlacementConfig.NONE);
		public static final ConfiguredPlacement<NoPlacementConfig> HEIGHTMAP_DOUBLE = Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE);
		public static final ConfiguredPlacement<?> ADD_32 = Placement.SPREAD_32_ABOVE.configured(NoPlacementConfig.INSTANCE);
		public static final ConfiguredPlacement<?> HEIGHTMAP_SQUARE = HEIGHTMAP.squared();
		public static final ConfiguredPlacement<?> HEIGHTMAP_DOUBLE_SQUARE = HEIGHTMAP_DOUBLE.squared();
		public static final ConfiguredPlacement<?> TOP_SOLID_HEIGHTMAP_SQUARE = TOP_SOLID_HEIGHTMAP.squared();
	}

	public static final class Configs {
		public static final BlockClusterFeatureConfig FOREST_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
				//Common
				.add(States.POPPY, 8).add(States.BLUEBELL, 12).add(States.MARIGOLD, 10).add(States.LILY_OF_THE_VALLEY, 10)
		        //Accents
		        .add(States.AZURE_BLUET, 6).add(States.OXEYE_DAISY, 6).add(States.CORNFLOWER, 5).add(States.ALLIUM, 4).add(States.DANDELION, 8)
		        //Exotic
		        .add(States.RED_TULIP, 1).add(States.ORANGE_TULIP, 1).add(States.WHITE_TULIP, 1).add(States.PINK_TULIP, 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();
		public static final BlockClusterFeatureConfig FANGORN_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
				//Common
				.add(States.POPPY, 8).add(States.BLUEBELL, 12).add(States.MARIGOLD, 10).add(States.LILY_OF_THE_VALLEY, 10)
		        //Accents
		        .add(States.AZURE_BLUET, 6).add(States.OXEYE_DAISY, 6).add(States.CORNFLOWER, 5).add(States.ALLIUM, 4).add(States.DANDELION, 8)
		        //Exotic
		        .add(States.RED_TULIP, 1).add(States.ORANGE_TULIP, 1).add(States.WHITE_TULIP, 1).add(States.PINK_TULIP, 1)
				.add(States.SUNFRUIT, 2).add(States.TEARS_OF_YAVANNA, 2).add(States.MOONFLOWER, 2).add(States.FANGORNS_BEARD, 2).add(States.ELFSONG, 2).add(States.SPRINGLEAF, 2).add(States.HOURN_LEAF, 2), SimpleBlockPlacer.INSTANCE)).tries(64).build();
	}

	public static final class States {
		protected static final BlockState POPPY = Blocks.POPPY.defaultBlockState();
		protected static final BlockState DANDELION = Blocks.DANDELION.defaultBlockState();
		protected static final BlockState ALLIUM = Blocks.ALLIUM.defaultBlockState(); 
		protected static final BlockState AZURE_BLUET = Blocks.AZURE_BLUET.defaultBlockState(); 
		protected static final BlockState RED_TULIP = Blocks.RED_TULIP.defaultBlockState(); 
		protected static final BlockState ORANGE_TULIP = Blocks.ORANGE_TULIP.defaultBlockState(); 
		protected static final BlockState WHITE_TULIP = Blocks.WHITE_TULIP.defaultBlockState(); 
		protected static final BlockState PINK_TULIP = Blocks.PINK_TULIP.defaultBlockState(); 
		protected static final BlockState OXEYE_DAISY = Blocks.OXEYE_DAISY.defaultBlockState(); 
		protected static final BlockState CORNFLOWER = Blocks.CORNFLOWER.defaultBlockState(); 
		protected static final BlockState LILY_OF_THE_VALLEY = Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
		protected static final BlockState SUNFRUIT = ExtendedBlocks.SUNFRUIT.get().defaultBlockState();
		protected static final BlockState TEARS_OF_YAVANNA = ExtendedBlocks.TEARS_OF_YAVANNA.get().defaultBlockState(); 
		protected static final BlockState MOONFLOWER = ExtendedBlocks.MOONFLOWER.get().defaultBlockState(); 
		protected static final BlockState FANGORNS_BEARD = ExtendedBlocks.FANGORNS_BEARD.get().defaultBlockState(); 
		protected static final BlockState ELFSONG = ExtendedBlocks.ELFSONG.get().defaultBlockState(); 
		protected static final BlockState SPRINGLEAF = ExtendedBlocks.SPRINGLEAF.get().defaultBlockState(); 
		protected static final BlockState HOURN_LEAF = ExtendedBlocks.HOURN_LEAF.get().defaultBlockState(); 
		protected static final BlockState BLUEBELL = LOTRBlocks.BLUEBELL.get().defaultBlockState(); 
		protected static final BlockState MARIGOLD = LOTRBlocks.MARIGOLD.get().defaultBlockState(); 

	}
}
