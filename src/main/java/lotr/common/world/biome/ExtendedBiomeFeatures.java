package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;

import lotr.common.init.ExtendedBlocks;
import lotr.common.world.gen.tree.ClusterFoliagePlacer;
import lotr.common.world.gen.tree.PartyTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig.Builder;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.RegistryEvent;

public class ExtendedBiomeFeatures {
	private static BlockState CHESTNUT_LOG;
	private static BlockState CHESTNUT_WOOD;
	private static BlockState CHESTNUT_BRANCH;
	private static BlockState CHESTNUT_LEAVES;
	private static BlockState CHESTNUT_LEAVES_FRUIT;
	private static BlockState CHESTNUT_LEAVES_FLOWERS;
	private static BlockStateProvider CHESTNUT_LEAVES_POOL;
	private static IPlantable CHESTNUT_SAPLING;
	public static BaseTreeFeatureConfig CHESTNUT_TREE;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FANCY;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_BEES;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FANCY_BEES;
	public static BaseTreeFeatureConfig CHESTNUT_PARTY;


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

	public static void setup(RegistryEvent.Register<Block> event) {
		CHESTNUT_LOG = ExtendedBlocks.CHESTNUT_LOG.get().defaultBlockState();
		CHESTNUT_WOOD = ExtendedBlocks.CHESTNUT_WOOD.get().defaultBlockState();
		CHESTNUT_BRANCH = ExtendedBlocks.CHESTNUT_BRANCH.get().defaultBlockState();
		CHESTNUT_LEAVES = ExtendedBlocks.CHESTNUT_LEAVES.get().defaultBlockState();
		CHESTNUT_LEAVES_FRUIT = ExtendedBlocks.CHESTNUT_LEAVES_FRUIT.get().defaultBlockState();
		CHESTNUT_LEAVES_FLOWERS = ExtendedBlocks.CHESTNUT_LEAVES_FLOWERS.get().defaultBlockState();
		CHESTNUT_LEAVES_POOL = new WeightedBlockStateProvider().add(CHESTNUT_LEAVES, 28).add(CHESTNUT_LEAVES_FRUIT, 3).add(CHESTNUT_LEAVES_FLOWERS, 1);
		CHESTNUT_SAPLING = (IPlantable) ExtendedBlocks.CHESTNUT_SAPLING.get();
		CHESTNUT_TREE = buildClassicTree(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL, 4, 3);
		CHESTNUT_TREE_BEES = buildClassicTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL, 4, 3);
		CHESTNUT_TREE_FANCY = buildFancyTree(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL);
		CHESTNUT_TREE_FANCY_BEES = buildFancyTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_POOL);
		CHESTNUT_PARTY = buildNormalPartyTree(CHESTNUT_LOG, CHESTNUT_WOOD, CHESTNUT_BRANCH, CHESTNUT_LEAVES_POOL);
	}

	//Classic Trees
	private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
		return buildClassicTree(log, leavesPool, baseHeight, heightRandA, false, false);
	}

	private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA, boolean bees, boolean vines) {
		return buildClassicTreeWithSpecifiedFoliage(log, leavesPool, baseHeight, heightRandA, new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), bees, vines);
	}

	private static BaseTreeFeatureConfig buildClassicTreeWithBees(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
		return buildClassicTree(log, leavesPool, baseHeight, heightRandA, true, false);
	}

	private static BaseTreeFeatureConfig buildClassicTreeWithSpecifiedFoliage(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA, FoliagePlacer foliage, boolean bees, boolean vines) {
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
