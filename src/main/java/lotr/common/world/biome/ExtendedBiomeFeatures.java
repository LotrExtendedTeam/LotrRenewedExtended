package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.world.gen.blockstateprovider.ExtendedListTypedBlockStateProvider;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeature;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeatures;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedMultiSphereConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.tree.BananaFoliagePlacer;
import com.github.maximuslotro.lotrrextended.common.world.gen.tree.ExtendedFoilagePlacers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lotr.common.LOTRLog;
import lotr.common.init.ExtendedBlocks;
import lotr.common.world.gen.tree.ClusterFoliagePlacer;
import lotr.common.world.gen.tree.PartyTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;

public class ExtendedBiomeFeatures {
	private static boolean created = false;
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

	public static BlockState BLACKBERRYBUSH;
	public static BlockState BLUEBERRYBUSH;
	public static BlockState CRANBERRYBUSH;
	public static BlockState ELDERBERRYBUSH;
	public static BlockState RASPBERRYBUSH;
	public static BlockState WILDBERRYBUSH;
	public static BlockClusterFeatureConfig BUSH_PATCH_CONFIG;

	private static BlockStateProvider CHESTNUT_LEAVES_POOL;
	public static BaseTreeFeatureConfig CHESTNUT_TREE;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FANCY;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_BEES;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FANCY_BEES;
	public static BaseTreeFeatureConfig CHESTNUT_PARTY;

	private static BlockStateProvider BANANA_LEAVES_POOL;
	public static BaseTreeFeatureConfig BANANA_TREE;

	private static BlockStateProvider PLUM_LEAVES_POOL;
	public static BaseTreeFeatureConfig PLUM_TREE;
	public static BaseTreeFeatureConfig PLUM_TREE_FANCY;
	public static BaseTreeFeatureConfig PLUM_TREE_BEES;
	public static BaseTreeFeatureConfig PLUM_TREE_FANCY_BEES;
	public static BaseTreeFeatureConfig PLUM_PARTY;

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

		BLACKBERRYBUSH = ExtendedBlocks.BLACKBERRY_BUSH_PLANT.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		BLUEBERRYBUSH = ExtendedBlocks.BLUEBERRY_BUSH_PLANT.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		CRANBERRYBUSH = ExtendedBlocks.CRANBERRY_BUSH_PLANT.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		ELDERBERRYBUSH = ExtendedBlocks.ELDERBERRY_BUSH_PLANT.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		RASPBERRYBUSH = ExtendedBlocks.RASPBERRY_BUSH_PLANT.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		WILDBERRYBUSH = ExtendedBlocks.WILDBERRY_BUSH_PLANT.get().defaultBlockState().setValue(ExtendedBerryBushBlock.AGE, 3);
		BUSH_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new ExtendedListTypedBlockStateProvider(BLACKBERRYBUSH)
			.addBlock(BLUEBERRYBUSH).addBlock(CRANBERRYBUSH).addBlock(ELDERBERRYBUSH).addBlock(RASPBERRYBUSH).addBlock(WILDBERRYBUSH), new SimpleBlockPlacer())
			.tries(15).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock())).noProjection().build();
	}

	public static void addBushes(BiomeGenerationSettings.Builder builder, int chance) {
		builder.addFeature(Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configured(BUSH_PATCH_CONFIG).decorated(placeHeightmapDoubleChance(chance)));
	}

	public static void addMoss(BiomeGenerationSettings.Builder builder, int freq) {
		builder.addFeature(Decoration.VEGETAL_DECORATION, ExtendedFeatures.MOSS_PATCH.decorated(placeHeightmapDoubleFreq(freq)));
	}

	public static void addOres(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, AMETHYST_ORE, 6)).range(64).squared().weighted(2F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, TOPAZ_ORE, 6)).range(64).squared().weighted(2F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, AMBER_ORE, 5)).range(48).squared().weighted(1.5F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, OPAL_ORE, 5)).range(48).squared().weighted(1.5F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, SAPPHIRE_ORE, 4)).range(32).squared().weighted(1F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, RUBY_ORE, 4)).range(32).squared().weighted(1F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, EMERALD_ORE, 4)).range(24).squared().weighted(0.75F).feature.get());
		builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, DIAMOND_ORE, 4)).range(16).squared().weighted(0.5F).feature.get());
	}

	public static void addRemains(BiomeGenerationSettings.Builder builder) {

		List<BlockState> remainsTypes = Arrays.asList(REMAINS_ORC, REMAINS_ELF, REMAINS_MAN, REMAINS_DWARF);

		builder.addFeature(Decoration.TOP_LAYER_MODIFICATION, ExtendedFeature.MULTI_SPHERE.get().configured(new ExtendedMultiSphereConfig(remainsTypes,
						FeatureSpread.of(1, 2), 3,
						ImmutableList.of(Blocks.DIRT.defaultBlockState())))
				.decorated(Features.Placements.TOP_SOLID_HEIGHTMAP_SQUARE)
				.weighted(4F)
				.feature.get());
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

	private static ConfiguredPlacement<?> placeHeightmapDoubleChance(int chance) {
		return Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE).squared().chance(chance);
	}

	private static ConfiguredPlacement<?> placeHeightmapDoubleFreq(int freq) {
		return Placement.HEIGHTMAP_SPREAD_DOUBLE.configured(IPlacementConfig.NONE).squared().count(freq);
	}
}
