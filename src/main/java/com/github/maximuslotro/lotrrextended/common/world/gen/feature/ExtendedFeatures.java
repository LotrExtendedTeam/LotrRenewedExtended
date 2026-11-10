package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

/**
 * Based off of {@link net.minecraft.world.gen.feature.Features}
 * */
public class ExtendedFeatures {
	public static final ConfiguredFeature<?, ?> ORE_AMETHYST = register("ore_amethyst", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.AMETHYST_ORE, 6)).range(64).squared().count(2));
	public static final ConfiguredFeature<?, ?> ORE_TOPAZ = register("ore_topaz", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.TOPAZ_ORE, 6)).range(64).squared().count(2));
	public static final ConfiguredFeature<?, ?> ORE_AMBER = register("ore_amber", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.AMBER_ORE, 5)).range(48).squared().count(2));
	public static final ConfiguredFeature<?, ?> ORE_OPAL = register("ore_opal", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.OPAL_ORE, 5)).range(48).squared().count(2));
	public static final ConfiguredFeature<?, ?> ORE_SAPPHIRE = register("ore_sapphire", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.SAPPHIRE_ORE, 4)).range(32).squared().count(1));
	public static final ConfiguredFeature<?, ?> ORE_RUBY = register("ore_ruby", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.RUBY_ORE, 4)).range(32).squared().count(1));
	public static final ConfiguredFeature<?, ?> ORE_EMERALD = register("ore_emerald", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.EMERALD_ORE, 4)).range(24).squared().count(1));
	public static final ConfiguredFeature<?, ?> ORE_DIAMOND = register("ore_diamond", Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, States.DIAMOND_ORE, 4)).range(16).squared().count(1));
	public static final ConfiguredFeature<?, ?> FLOWER_FANGORN = register("flower_fangorn", Feature.FLOWER.configured(Configs.FANGORN_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(6));
	public static final ConfiguredFeature<?, ?> FLOWER_FOREST1 = register("flower_forest1", Feature.FLOWER.configured(Configs.FOREST_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(1));
	public static final ConfiguredFeature<?, ?> FLOWER_FOREST2 = register("flower_forest2", Feature.FLOWER.configured(Configs.FOREST_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> FLOWER_FOREST3 = register("flower_forest3", Feature.FLOWER.configured(Configs.FOREST_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(3));
	public static final ConfiguredFeature<?, ?> FLOWER_LOTHLORIEN = register("flower_lothlorien", Feature.FLOWER.configured(Configs.LOTHLORIEN_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(6));
	public static final ConfiguredFeature<?, ?> FLOWER_LOTHLORIEN_EVES = register("flower_lothlorien_eves", Feature.FLOWER.configured(Configs.LOTHLORIEN_EVES_FLOWER_CONFIG).decorated(Placements.ADD_32).decorated(Placements.HEIGHTMAP_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_WATERLILLY_FANGORN = register("patch_waterlilly_fangorn", Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.LILY_PAD), SimpleBlockPlacer.INSTANCE).tries(2).build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(2));
	public static final ConfiguredFeature<?, ?> PATCH_RIVERWEED = register("patch_riverweed", Feature.RANDOM_PATCH.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.RIVERWEED), SimpleBlockPlacer.INSTANCE).tries(10).build()).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(6));
	public static final ConfiguredFeature<?, ?> FLOWER_FANGORN_STONEROOT = register("flower_fangorn_stoneroot", ExtendedFeature.RANDOM_PATCH_DIR_PRE.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.STONEROOT), SimpleBlockPlacer.INSTANCE).xspread(6).yspread(4).zspread(6).build()).range(64).squared().count(1));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String configuredFeatureName, ConfiguredFeature<FC, ?> pConfiguredFeature) {
		return pConfiguredFeature;
		// Can't register, throws JSON not found exception
		//return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,  new ResourceLocation(LotrExtendedMod.MODID, configuredFeatureName), pConfiguredFeature);
	}

	public static void init() {}

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
		public static final BlockClusterFeatureConfig FOREST_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider()
			.add(States.POPPY, 8).add(States.BLUEBELL, 12).add(States.MARIGOLD, 10).add(States.LILY_OF_THE_VALLEY, 10)
			.add(States.AZURE_BLUET, 6).add(States.OXEYE_DAISY, 6).add(States.CORNFLOWER, 5).add(States.ALLIUM, 4).add(States.DANDELION, 8)
			.add(States.RED_TULIP, 1).add(States.ORANGE_TULIP, 1).add(States.WHITE_TULIP, 1).add(States.PINK_TULIP, 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();
		public static final BlockClusterFeatureConfig FANGORN_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider()
			.add(States.POPPY, 8).add(States.BLUEBELL, 12).add(States.MARIGOLD, 10).add(States.LILY_OF_THE_VALLEY, 10)
			.add(States.AZURE_BLUET, 6).add(States.OXEYE_DAISY, 6).add(States.CORNFLOWER, 5).add(States.ALLIUM, 4).add(States.DANDELION, 8)
			.add(States.RED_TULIP, 1).add(States.ORANGE_TULIP, 1).add(States.WHITE_TULIP, 1).add(States.PINK_TULIP, 1)
			.add(States.SUNFRUIT, 2).add(States.TEARS_OF_YAVANNA, 2).add(States.MOONFLOWER, 2).add(States.FANGORNS_BEARD, 2).add(States.ELFSONG, 2).add(States.SPRINGLEAF, 2).add(States.HOURN_LEAF, 2), SimpleBlockPlacer.INSTANCE)).tries(64).build();
		public static final BlockClusterFeatureConfig LOTHLORIEN_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider()
			.add(States.ELANOR, 20).add(States.NIPHREDIL, 20)
			.add(States.POPPY, 8).add(States.BLUEBELL, 8).add(States.MARIGOLD, 10).add(States.LILY_OF_THE_VALLEY, 7)
			.add(States.AZURE_BLUET, 3).add(States.OXEYE_DAISY, 3).add(States.CORNFLOWER, 5).add(States.ALLIUM, 4).add(States.DANDELION, 6)
			.add(States.RED_TULIP, 1).add(States.ORANGE_TULIP, 1).add(States.WHITE_TULIP, 1).add(States.PINK_TULIP, 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();
		public static final BlockClusterFeatureConfig LOTHLORIEN_EVES_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider()
			.add(States.POPPY, 8).add(States.BLUEBELL, 8).add(States.MARIGOLD, 10).add(States.LILY_OF_THE_VALLEY, 7).add(States.ELANOR, 10).add(States.NIPHREDIL, 10)
			.add(States.AZURE_BLUET, 3).add(States.OXEYE_DAISY, 3).add(States.CORNFLOWER, 5).add(States.ALLIUM, 4).add(States.DANDELION, 6)
			.add(States.RED_TULIP, 1).add(States.ORANGE_TULIP, 1).add(States.WHITE_TULIP, 1).add(States.PINK_TULIP, 1), SimpleBlockPlacer.INSTANCE)).tries(64).build();
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
		protected static final BlockState ELANOR = LOTRBlocks.ELANOR.get().defaultBlockState(); 
		protected static final BlockState NIPHREDIL = LOTRBlocks.NIPHREDIL.get().defaultBlockState();
		protected static final BlockState LILY_PAD = Blocks.LILY_PAD.defaultBlockState();
		protected static final BlockState RIVERWEED = ExtendedBlocks.RIVERWEED.get().defaultBlockState();
		protected static final BlockState AMETHYST_ORE = ExtendedBlocks.AMETHYST_ORE.get().defaultBlockState();
		protected static final BlockState TOPAZ_ORE = ExtendedBlocks.TOPAZ_ORE.get().defaultBlockState();
		protected static final BlockState AMBER_ORE = ExtendedBlocks.AMBER_ORE.get().defaultBlockState();
		protected static final BlockState OPAL_ORE = ExtendedBlocks.OPAL_ORE.get().defaultBlockState();
		protected static final BlockState SAPPHIRE_ORE = ExtendedBlocks.SAPPHIRE_ORE.get().defaultBlockState();
		protected static final BlockState RUBY_ORE = ExtendedBlocks.RUBY_ORE.get().defaultBlockState();
		protected static final BlockState EMERALD_ORE = ExtendedBlocks.EMERALD_ORE.get().defaultBlockState();
		protected static final BlockState DIAMOND_ORE = ExtendedBlocks.DIAMOND_ORE.get().defaultBlockState();
		protected static final BlockState STONEROOT = ExtendedBlocks.STONEROOT.get().defaultBlockState();
	}
}
