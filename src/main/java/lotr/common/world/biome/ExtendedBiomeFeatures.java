package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.world.gen.blockstateprovider.ExtendedListTypedBlockStateProvider;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeature;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedBlobFeatureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedBlobFeatureConfig.ExtendedFillerBlockType;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedConfiguredFeatures;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedHoleySphereConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedLandBlotFeatureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.tree.BananaFoliagePlacer;
import com.github.maximuslotro.lotrrextended.common.world.gen.tree.ExtendedFoilagePlacers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.gen.feature.BoulderFeatureConfig;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.tree.ClusterFoliagePlacer;
import lotr.common.world.gen.tree.PartyTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig.Builder;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;

public class ExtendedBiomeFeatures {
	private static boolean created = false;
	public static final RuleTest MIDDLE_EARTH_SURFACE = new TagMatchRuleTest(ExtendedTags.Blocks.MIDDLE_EARTH_PLACEABLE_SURFACE);

	// Trees
	private static BlockState CHESTNUT_LOG;
	private static BlockState CHESTNUT_WOOD;
	private static BlockState CHESTNUT_BRANCH;
	private static BlockState CHESTNUT_LEAVES;
	private static BlockState CHESTNUT_LEAVES_FRUIT;

	private static BlockState BANANA_LOG;
	private static BlockState BANANA_LEAVES;
	public static BlockState BANANA_FRUIT;

	private static BlockState PLUM_LOG;
	private static BlockState PLUM_WOOD;
	private static BlockState PLUM_BRANCH;
	private static BlockState PLUM_LEAVES;
	private static BlockState PLUM_LEAVES_FRUIT;

	private static BlockState EBONY_LOG;
	private static BlockState EBONY_BRANCH;
	private static BlockState EBONY_LEAVES;

	// Stones
	private static BlockState BLUE_ROCK;
	private static BlockState BLUE_ROCK_SLAB;
	private static BlockState BLUE_ROCK_STAIRS;
	private static BlockState STONE;
	private static BlockState STONE_SLAB;
	private static BlockState STONE_STAIRS;
	private static BlockState ANDESITE;
	private static BlockState ANDESITE_SLAB;
	private static BlockState ANDESITE_STAIRS;
	private static BlockState DIORITE;
	private static BlockState DIORITE_SLAB;
	private static BlockState DIORITE_STAIRS;
	private static BlockState GRANITE;
	private static BlockState GRANITE_SLAB;
	private static BlockState GRANITE_STAIRS;
	// Ores
	private static BlockState AMETHYST_ORE;
	private static BlockState TOPAZ_ORE;
	private static BlockState AMBER_ORE;
	private static BlockState OPAL_ORE;
	private static BlockState SAPPHIRE_ORE;
	private static BlockState RUBY_ORE;
	private static BlockState EMERALD_ORE;
	private static BlockState DIAMOND_ORE;

	// Remains Ores
	private static BlockState REMAINS_ORC;
	private static BlockState REMAINS_ELF;
	private static BlockState REMAINS_MAN;
	private static BlockState REMAINS_DWARF;

	private static BlockState BLACKBERRYBUSH;
	private static BlockState BLUEBERRYBUSH;
	private static BlockState CRANBERRYBUSH;
	private static BlockState ELDERBERRYBUSH;
	private static BlockState RASPBERRYBUSH;
	private static BlockState WILDBERRYBUSH;
	private static BlockClusterFeatureConfig BUSH_PATCH_CONFIG;

	private static BlockStateProvider CHESTNUT_LEAVES_POOL;
	private static BaseTreeFeatureConfig CHESTNUT_TREE;
	private static BaseTreeFeatureConfig CHESTNUT_TREE_FANCY;
	private static BaseTreeFeatureConfig CHESTNUT_TREE_BEES;
	private static BaseTreeFeatureConfig CHESTNUT_TREE_FANCY_BEES;
	private static BaseTreeFeatureConfig CHESTNUT_PARTY;

	private static BlockStateProvider BANANA_LEAVES_POOL;
	private static BaseTreeFeatureConfig BANANA_TREE;

	private static BlockStateProvider PLUM_LEAVES_POOL;
	private static BaseTreeFeatureConfig PLUM_TREE;
	private static BaseTreeFeatureConfig PLUM_TREE_FANCY;
	private static BaseTreeFeatureConfig PLUM_TREE_BEES;
	private static BaseTreeFeatureConfig PLUM_TREE_FANCY_BEES;
	private static BaseTreeFeatureConfig PLUM_PARTY;

	private static BlockStateProvider EBONY_LEAVES_POOL;
	private static BaseTreeFeatureConfig EBONY_TREE;
	private static BaseTreeFeatureConfig EBONY_TREE_FANCY;
	private static BaseTreeFeatureConfig EBONY_TREE_BEES;
	private static BaseTreeFeatureConfig EBONY_TREE_FANCY_BEES;

	//Chestnut
	//TODO add to Celebrant, Pukel, Rhun, Adornland when biomes are added
	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> chestnut() {
		return Feature.TREE.configured(CHESTNUT_TREE);
	}                         

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> chestnutFancy() {
		return Feature.TREE.configured(CHESTNUT_TREE_FANCY);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> chestnutBees() {
		return Feature.TREE.configured(CHESTNUT_TREE_BEES);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> chestnutFancyBees() {
		return Feature.TREE.configured(CHESTNUT_TREE_FANCY_BEES);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> chestnutParty() {
		return Feature.TREE.configured(CHESTNUT_PARTY);
	}

	// Default willowTree implementation requires water for terrain gen
	public static ConfiguredFeature<NoFeatureConfig, ?> willowTree() {
		return ExtendedConfiguredFeatures.WILLOW_TREE_WATER_FEATURE;
	}

	// Default willowTree implementation requires water for terrain gen
	public static ConfiguredFeature<NoFeatureConfig, ?> willowTreeNoWaterRequirement() {
		return ExtendedConfiguredFeatures.WILLOW_TREE_FEATURE;
	}

	//TODO add to biomes
	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> banana() {
		return Feature.TREE.configured(BANANA_TREE);
	}

	//Plum
	//TODO add to GulfOfHarad, nearHaradFertile, Pukel, Rhun when biomes are added
	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> plum() {
		return Feature.TREE.configured(PLUM_TREE);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> plumFancy() {
		return Feature.TREE.configured(PLUM_TREE_FANCY);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> plumBees() {
		return Feature.TREE.configured(PLUM_TREE_BEES);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> plumFancyBees() {
		return Feature.TREE.configured(PLUM_TREE_FANCY_BEES);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> plumParty() {
		return Feature.TREE.configured(PLUM_PARTY);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> ebony() {
		return Feature.TREE.configured(EBONY_TREE);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> ebonyFancy() {
		return Feature.TREE.configured(EBONY_TREE_FANCY);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> ebonyBees() {
		return Feature.TREE.configured(EBONY_TREE_BEES);
	}

	public static ConfiguredFeature<BaseTreeFeatureConfig, ?> ebonyFancyBees() {
		return Feature.TREE.configured(EBONY_TREE_FANCY_BEES);
	}

	public static void setup(RegistryEvent.Register<Block> event) {
		// tree block states
		CHESTNUT_LOG = ExtendedBlocks.CHESTNUT_LOG.get().defaultBlockState();
		CHESTNUT_WOOD = ExtendedBlocks.CHESTNUT_WOOD.get().defaultBlockState();
		CHESTNUT_BRANCH = ExtendedBlocks.CHESTNUT_BRANCH.get().defaultBlockState();
		CHESTNUT_LEAVES = ExtendedBlocks.CHESTNUT_LEAVES.get().defaultBlockState();
		CHESTNUT_LEAVES_FRUIT = ExtendedBlocks.CHESTNUT_LEAVES_FRUIT.get().defaultBlockState();

		BANANA_LOG = ExtendedBlocks.BANANA_LOG.get().defaultBlockState();
		BANANA_LEAVES = ExtendedBlocks.BANANA_LEAVES.get().defaultBlockState();
		BANANA_FRUIT = ExtendedBlocks.HANGING_BANANA.get().defaultBlockState();

		PLUM_LOG = ExtendedBlocks.PLUM_LOG.get().defaultBlockState();
		PLUM_WOOD = ExtendedBlocks.PLUM_WOOD.get().defaultBlockState();
		PLUM_BRANCH = ExtendedBlocks.PLUM_BRANCH.get().defaultBlockState();
		PLUM_LEAVES = ExtendedBlocks.PLUM_LEAVES.get().defaultBlockState();
		PLUM_LEAVES_FRUIT = ExtendedBlocks.PLUM_LEAVES_FRUIT.get().defaultBlockState();

		EBONY_LOG = ExtendedBlocks.EBONY_LOG.get().defaultBlockState();
		EBONY_BRANCH = ExtendedBlocks.EBONY_BRANCH.get().defaultBlockState();
		EBONY_LEAVES = ExtendedBlocks.EBONY_LEAVES.get().defaultBlockState();

		// stone block states
		BLUE_ROCK = LOTRBlocks.BLUE_ROCK.get().defaultBlockState();
		BLUE_ROCK_SLAB = LOTRBlocks.BLUE_ROCK_SLAB.get().defaultBlockState();
		BLUE_ROCK_STAIRS = LOTRBlocks.BLUE_ROCK_STAIRS.get().defaultBlockState();
		STONE = Blocks.STONE.defaultBlockState();
		STONE_SLAB = Blocks.STONE_SLAB.defaultBlockState();
		STONE_STAIRS = Blocks.STONE_STAIRS.defaultBlockState();
		ANDESITE = Blocks.ANDESITE.defaultBlockState();
		ANDESITE_SLAB = Blocks.ANDESITE_SLAB.defaultBlockState();
		ANDESITE_STAIRS = Blocks.ANDESITE_STAIRS.defaultBlockState();
		DIORITE = Blocks.DIORITE.defaultBlockState();
		DIORITE_SLAB = Blocks.DIORITE_SLAB.defaultBlockState();
		DIORITE_STAIRS = Blocks.DIORITE_STAIRS.defaultBlockState();
		GRANITE = Blocks.GRANITE.defaultBlockState();
		GRANITE_SLAB = Blocks.GRANITE_SLAB.defaultBlockState();
		GRANITE_STAIRS = Blocks.GRANITE_STAIRS.defaultBlockState();

		// gem block states
		AMETHYST_ORE = ExtendedBlocks.AMETHYST_ORE.get().defaultBlockState();
		TOPAZ_ORE = ExtendedBlocks.TOPAZ_ORE.get().defaultBlockState();
		AMBER_ORE = ExtendedBlocks.AMBER_ORE.get().defaultBlockState();
		OPAL_ORE = ExtendedBlocks.OPAL_ORE.get().defaultBlockState();
		SAPPHIRE_ORE = ExtendedBlocks.SAPPHIRE_ORE.get().defaultBlockState();
		RUBY_ORE = ExtendedBlocks.RUBY_ORE.get().defaultBlockState();
		EMERALD_ORE = ExtendedBlocks.EMERALD_ORE.get().defaultBlockState();
		DIAMOND_ORE = ExtendedBlocks.DIAMOND_ORE.get().defaultBlockState();

		// remains block states
		REMAINS_ORC = ExtendedBlocks.REMAINS_ORC.get().defaultBlockState();
		REMAINS_ELF = ExtendedBlocks.REMAINS_ELF.get().defaultBlockState();
		REMAINS_MAN = ExtendedBlocks.REMAINS_MAN.get().defaultBlockState();
		REMAINS_DWARF = ExtendedBlocks.REMAINS_DWARF.get().defaultBlockState();


		//Use flag to catch re-initialization of block that causes doubling of registry items causing crashing
		if(!created) {
			created = true;
			ExtendedFoilagePlacers.registerLeafBuilders();
		}

		// tree setup
		CHESTNUT_LEAVES_POOL = new WeightedBlockStateProvider().add(CHESTNUT_LEAVES, 28).add(CHESTNUT_LEAVES_FRUIT, 3);
		CHESTNUT_TREE = buildClassicTree(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL, 4, 3);
		CHESTNUT_TREE_BEES = buildClassicTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL, 4, 3);
		CHESTNUT_TREE_FANCY = buildFancyTree(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL);
		CHESTNUT_TREE_FANCY_BEES = buildFancyTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL);
		CHESTNUT_PARTY = buildNormalPartyTree(CHESTNUT_LOG, CHESTNUT_WOOD, CHESTNUT_BRANCH, CHESTNUT_LEAVES_POOL);

		BANANA_LEAVES_POOL = new SimpleBlockStateProvider(BANANA_LEAVES);
		BANANA_TREE = buildTreeWithSpecifiedFoliageGivenTrunk(BANANA_LOG, BANANA_LEAVES_POOL, 4, 2, new BananaFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(0), FeatureSpread.of(2, 2)), false, false);

		PLUM_LEAVES_POOL = new WeightedBlockStateProvider().add(PLUM_LEAVES, 28).add(PLUM_LEAVES_FRUIT, 4);
		PLUM_TREE = buildClassicTree(PLUM_LOG, PLUM_LEAVES_POOL, 4, 3);
		PLUM_TREE_BEES = buildClassicTreeWithBees(PLUM_LOG, PLUM_LEAVES_POOL, 4, 3);
		PLUM_TREE_FANCY = buildFancyTree(PLUM_LOG, PLUM_LEAVES_POOL);
		PLUM_TREE_FANCY_BEES = buildFancyTreeWithBees(PLUM_LOG, PLUM_LEAVES_POOL);
		PLUM_PARTY = buildNormalPartyTree(PLUM_LOG, PLUM_WOOD, PLUM_BRANCH, PLUM_LEAVES_POOL);

		EBONY_LEAVES_POOL = new SimpleBlockStateProvider(EBONY_LEAVES);
		EBONY_TREE = buildClassicTree(EBONY_LOG, EBONY_LEAVES_POOL, 4, 3);
		EBONY_TREE_BEES = buildClassicTreeWithBees(EBONY_LOG, EBONY_LEAVES_POOL, 4, 3);
		EBONY_TREE_FANCY = buildFancyTree(EBONY_LOG, EBONY_LEAVES_POOL);
		EBONY_TREE_FANCY_BEES = buildFancyTreeWithBees(EBONY_LOG, EBONY_LEAVES_POOL);		

		BLACKBERRYBUSH = ExtendedBlocks.BLACKBERRY_BUSH.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		BLUEBERRYBUSH = ExtendedBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		CRANBERRYBUSH = ExtendedBlocks.CRANBERRY_BUSH.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		ELDERBERRYBUSH = ExtendedBlocks.ELDERBERRY_BUSH.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		RASPBERRYBUSH = ExtendedBlocks.RASPBERRY_BUSH.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		WILDBERRYBUSH = ExtendedBlocks.WILDBERRY_BUSH.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		BUSH_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new ExtendedListTypedBlockStateProvider(BLACKBERRYBUSH)
			.addBlock(BLUEBERRYBUSH).addBlock(CRANBERRYBUSH).addBlock(ELDERBERRYBUSH).addBlock(RASPBERRYBUSH).addBlock(WILDBERRYBUSH), new SimpleBlockPlacer())
			.tries(15).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock())).noProjection().build();
	}

	public static void addBushes(BiomeGenerationSettings.Builder builder, int chance) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configured(BUSH_PATCH_CONFIG).decorated(Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE).squared().chance(chance)));
	}

	public static void addMoss(BiomeGenerationSettings.Builder builder, int freq) {
		builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ExtendedConfiguredFeatures.MOSS_PATCH.decorated(Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE).squared().count(freq)));
	}

	public static void addMeteorites(BiomeGenerationSettings.Builder builder, int chance) {
		builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ExtendedConfiguredFeatures.METEORITE_IMPACT.decorated(
			Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE).squared().chance(chance)));
	}
	public static void addOrthancWalls(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ExtendedConfiguredFeatures.RING_OF_ISENGARD.decorated(
			Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE).chance(1)));
	}

	public static void addScorchedLand(BiomeGenerationSettings.Builder builder, ConfiguredFeature<ExtendedLandBlotFeatureConfig, ?> feature, int chance) {
		builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, feature.decorated(
			Placement.HEIGHTMAP_WORLD_SURFACE.configured(IPlacementConfig.NONE).squared().chance(chance)));
	}

	public static void addOres(BiomeGenerationSettings.Builder builder) {
		addUndergroundOreWeighted(builder, AMETHYST_ORE, 6, 64, 2F);
		addUndergroundOreWeighted(builder, TOPAZ_ORE, 6, 64, 2F);
		addUndergroundOreWeighted(builder, AMBER_ORE, 5, 48, 1.5F);
		addUndergroundOreWeighted(builder, OPAL_ORE, 5, 48, 1.5F);
		addUndergroundOreWeighted(builder, SAPPHIRE_ORE, 4, 32, 1F);
		addUndergroundOreWeighted(builder, RUBY_ORE, 4, 32, 1F);
		addUndergroundOreWeighted(builder, EMERALD_ORE, 4, 24, 0.75F);
		addUndergroundOreWeighted(builder, DIAMOND_ORE, 4, 16, 0.5F);
	}

	public static void addBlueOres(BiomeGenerationSettings.Builder builder) {
		addUndergroundOre(builder, AMETHYST_ORE, 6, 64, 2);
		addUndergroundOre(builder, TOPAZ_ORE, 6, 64, 2);
		addUndergroundOre(builder, AMBER_ORE, 5, 48, 2);
		addUndergroundOre(builder, OPAL_ORE, 5, 48, 2);
		addUndergroundOre(builder, SAPPHIRE_ORE, 4, 32, 1);
		addUndergroundOre(builder, RUBY_ORE, 4, 32, 1);
		addUndergroundOre(builder, EMERALD_ORE, 4, 24, 1);
		addUndergroundOre(builder, DIAMOND_ORE, 4, 16, 1);
		addUndergroundOre(builder, Blocks.LAPIS_ORE.defaultBlockState(), 7, 32, 2);
	}

	private static void addUndergroundOreWeighted(BiomeGenerationSettings.Builder builder, BlockState stone, int size, int maxHightFromBedrock, float weight) {
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, stone, size)).range(maxHightFromBedrock).squared().weighted(weight).feature.get());
	}

	private static void addUndergroundOre(BiomeGenerationSettings.Builder builder, BlockState stone, int size, int maxHightFromBedrock, int frequency) {
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, stone, size)).range(maxHightFromBedrock).squared().count(frequency));
	}

	// Common Blobs
	public static void addCommonAndesiteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, ANDESITE, ANDESITE_SLAB, ANDESITE_STAIRS, 61, 96, 20);
	}

	public static void addCommonDioriteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, DIORITE, DIORITE_SLAB, DIORITE_STAIRS, 61, 96, 12);
	}

	public static void addCommonGraniteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, GRANITE_SLAB, GRANITE_SLAB, GRANITE_STAIRS, 61, 96, 12);
	}

	// Blobs
	public static void addAndesiteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, ANDESITE, ANDESITE_SLAB, ANDESITE_STAIRS, 61, 80, 10);
	}

	public static void addDioriteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, DIORITE, DIORITE_SLAB, DIORITE_STAIRS, 61, 80, 5);
	}

	public static void addGraniteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, GRANITE_SLAB, GRANITE_SLAB, GRANITE_STAIRS, 61, 80, 5);
	}

	// Deep Blobs
	public static void addDeepAndesiteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, ANDESITE, ANDESITE_SLAB, ANDESITE_STAIRS, 61, 32, 10);
	}

	public static void addDeepDioriteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, DIORITE, DIORITE_SLAB, DIORITE_STAIRS, 61, 32, 5);
	}

	public static void addDeepGraniteBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, GRANITE_SLAB, GRANITE_SLAB, GRANITE_STAIRS, 61, 32, 5);
	}

	public static void addStoneBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, STONE, STONE_SLAB, STONE_STAIRS, 61, 90, 5);
	}

	public static void addLuigonBlobs(BiomeGenerationSettings.Builder builder) {
		addUndergroundStones(builder, BLUE_ROCK, BLUE_ROCK_SLAB, BLUE_ROCK_SLAB, 61, 96, 6);
	}

	private static void addUndergroundStones(BiomeGenerationSettings.Builder builder, BlockState stone, BlockState slab, BlockState stair, int size, int maxHightFromBedrock, int frequency) {
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeature.BLOB_PRE.configured(new ExtendedBlobFeatureConfig(
			ExtendedFillerBlockType.NATURAL_STONE_MIDDLE_EARTH,
			ExtendedFillerBlockType.NATURAL_STONE_SLAB_MIDDLE_EARTH,
			ExtendedFillerBlockType.NATURAL_STONE_STAIRS_MIDDLE_EARTH,
			stone, slab, stair, size)).range(maxHightFromBedrock).squared().count(frequency));
	}

	public static void addRemains(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, ExtendedFeature.HOLEY_UNDERWATER_SPHERE.get()
			.configured(new ExtendedHoleySphereConfig(Arrays.asList(REMAINS_ORC, REMAINS_ELF, REMAINS_MAN, REMAINS_DWARF), FeatureSpread.of(2, 3), 4, ImmutableList.of(Blocks.DIRT.defaultBlockState()), 20))
			.decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE).weighted(5F).feature.get());
	}

	public static void addBoulders(BiomeGenerationSettings.Builder builder, BlockState block, int minWidth, int maxWidth, int chanceInChunk, int genAmount, int heightCheck) {
		BoulderFeatureConfig config = new BoulderFeatureConfig(new SimpleBlockStateProvider(block), minWidth, maxWidth, heightCheck);
		float increaseChance = 1.0F / chanceInChunk;
		ConfiguredPlacement<?> placement = (Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE).squared()).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, increaseChance, genAmount)));
		builder.addFeature(Decoration.LOCAL_MODIFICATIONS, LOTRFeatures.BOULDER.configured(config).decorated(placement));
	}

	//Classic Trees
	private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
		return buildClassicTree(log, leavesPool, baseHeight, heightRandA, false, false);
	}

	private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA, boolean bees, boolean vines) {
		return buildTreeWithSpecifiedFoliageGivenTrunk(log, leavesPool, baseHeight, heightRandA, new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), bees, vines);
	}

	private static BaseTreeFeatureConfig buildClassicTreeWithBees(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
		return buildClassicTree(log, leavesPool, baseHeight, heightRandA, true, false);
	}

	private static BaseTreeFeatureConfig buildTreeWithSpecifiedFoliageGivenTrunk(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA, FoliagePlacer foliage, boolean bees, boolean vines) {
		List<TreeDecorator> decorators = new ArrayList<>();
		if (bees) {
			decorators.add(Placements.BEEHIVE_005);
		}
		if (vines) {
			decorators.add(TrunkVineTreeDecorator.INSTANCE);
			decorators.add(LeaveVineTreeDecorator.INSTANCE);
		}
		return new Builder(new SimpleBlockStateProvider(log), leavesPool, foliage, new StraightTrunkPlacer(baseHeight, heightRandA, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().decorators(ImmutableList.copyOf(decorators)).build();
	}

	//Fancy Trees
	private static BaseTreeFeatureConfig buildFancyTree(BlockState log, BlockStateProvider leavesPool) {
		return buildFancyTree(log, leavesPool, false);
	}

	private static BaseTreeFeatureConfig buildFancyTreeWithBees(BlockState log, BlockStateProvider leavesPool) {
		return buildFancyTree(log, leavesPool, true);
	}

	private static BaseTreeFeatureConfig buildFancyTree(BlockState log, BlockStateProvider leavesPool, boolean bees) {
		List<TreeDecorator> decorators = new ArrayList<>();
		if (bees) {
			decorators.add(Placements.BEEHIVE_005);
		}

		return new Builder(new SimpleBlockStateProvider(log), leavesPool, new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))).ignoreVines().heightmap(Type.MOTION_BLOCKING).decorators(ImmutableList.copyOf(decorators)).build();
	}
	//Party aka Huge trees
	private static BaseTreeFeatureConfig buildNormalFoliagePartyTree(BlockState log, BlockState wood, BlockState branch, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
		return buildPartyTree(log, wood, branch, leavesPool, baseHeight, heightRandA, new ClusterFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0)), ImmutableList.of());
	}

	private static BaseTreeFeatureConfig buildNormalHeightPartyTree(BlockState log, BlockState wood, BlockState branch, BlockStateProvider leavesPool, FoliagePlacer foliage) {
		return buildPartyTree(log, wood, branch, leavesPool, 10, 14, foliage, ImmutableList.of());
	}

	private static BaseTreeFeatureConfig buildNormalPartyTree(BlockState log, BlockState wood, BlockState branch, BlockStateProvider leavesPool) {
		return buildNormalFoliagePartyTree(log, wood, branch, leavesPool, 10, 14);
	}

	private static BaseTreeFeatureConfig buildPartyTree(BlockState log, BlockState wood, BlockState branch, BlockStateProvider leavesPool, int baseHeight, int heightRandA, FoliagePlacer foliage) {
		return buildPartyTree(log, wood, branch, leavesPool, baseHeight, heightRandA, foliage, ImmutableList.of());
	}

	private static BaseTreeFeatureConfig buildPartyTree(BlockState log, BlockState wood, BlockState branch, BlockStateProvider leavesPool, int baseHeight, int heightRandA, FoliagePlacer foliage, List<TreeDecorator> decorators) {
		return new Builder(new SimpleBlockStateProvider(log), leavesPool, foliage, new PartyTrunkPlacer(baseHeight, heightRandA, 0, wood, branch), new TwoLayerFeature(1, 1, 2)).ignoreVines().decorators(ImmutableList.copyOf(decorators)).build();
	}
}
