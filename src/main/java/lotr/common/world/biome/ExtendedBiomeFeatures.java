package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lotr.common.init.ExtendedBlocks;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.WeightedFeature;
import lotr.common.world.gen.feature.WeightedRandomFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.RegistryEvent;

@SuppressWarnings("unchecked")
public class ExtendedBiomeFeatures {
	private static BlockState CHESTNUT_LOG;
	private static BlockState CHESTNUT_LEAVES;
	private static BlockState CHESTNUT_LEAVES_FRUIT;
	private static BlockState CHESTNUT_LEAVES_FLOWERS;
	private static BlockStateProvider CHESTNUT_LEAVES_FRUIT_POOL;
	private static BlockStateProvider CHESTNUT_LEAVES_FLOWERS_POOL;
	private static BlockStateProvider CHESTNUT_LEAVES_MIX_POOL;
	private static IPlantable CHESTNUT_SAPLING;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FRUIT;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FRUIT_BEES;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FLOWERS;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_FLOWERS_BEES;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_MIX;
	public static BaseTreeFeatureConfig CHESTNUT_TREE_MIX_BEES;

	public static ConfiguredFeature<WeightedRandomFeatureConfig<BaseTreeFeatureConfig>, ?> chestnut() {
		return LOTRFeatures.getWeightedRandom().configured(fromEntries(Feature.TREE.configured(CHESTNUT_TREE_FRUIT), 19, Feature.TREE.configured(CHESTNUT_TREE_FLOWERS), 19, Feature.TREE.configured(CHESTNUT_TREE_MIX), 2));
	}

	public static ConfiguredFeature<WeightedRandomFeatureConfig<BaseTreeFeatureConfig>, ?>  chestnutBees() {
		return LOTRFeatures.getWeightedRandom().configured(fromEntries(Feature.TREE.configured(CHESTNUT_TREE_FRUIT_BEES), 19, Feature.TREE.configured(CHESTNUT_TREE_FLOWERS_BEES), 19, Feature.TREE.configured(CHESTNUT_TREE_MIX_BEES), 2));
	}

	public static void setup(RegistryEvent.Register<Block> event) {
		CHESTNUT_LOG = ExtendedBlocks.CHESTNUT_LOG.get().defaultBlockState();
		CHESTNUT_LEAVES = ExtendedBlocks.CHESTNUT_LEAVES.get().defaultBlockState();
		CHESTNUT_LEAVES_FRUIT = ExtendedBlocks.CHESTNUT_LEAVES_FRUIT.get().defaultBlockState();
		CHESTNUT_LEAVES_FLOWERS = ExtendedBlocks.CHESTNUT_LEAVES_FLOWERS.get().defaultBlockState();
		CHESTNUT_LEAVES_FRUIT_POOL = new WeightedBlockStateProvider().add(CHESTNUT_LEAVES, 15).add(CHESTNUT_LEAVES_FRUIT, 1);
		CHESTNUT_LEAVES_FLOWERS_POOL = new WeightedBlockStateProvider().add(CHESTNUT_LEAVES, 15).add(CHESTNUT_LEAVES_FLOWERS, 1);
		CHESTNUT_LEAVES_MIX_POOL = new WeightedBlockStateProvider().add(CHESTNUT_LEAVES, 30).add(CHESTNUT_LEAVES_FRUIT, 1).add(CHESTNUT_LEAVES_FLOWERS, 1);
		CHESTNUT_SAPLING = (IPlantable) ExtendedBlocks.CHESTNUT_SAPLING.get();
		CHESTNUT_TREE_FRUIT = buildClassicTree(CHESTNUT_LOG, CHESTNUT_LEAVES_FRUIT_POOL, 4, 3);
		CHESTNUT_TREE_FRUIT_BEES = buildClassicTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_FRUIT_POOL, 4, 3);
		CHESTNUT_TREE_FLOWERS = buildClassicTree(CHESTNUT_LOG, CHESTNUT_LEAVES_FLOWERS_POOL, 4, 3);
		CHESTNUT_TREE_FLOWERS_BEES = buildClassicTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_FLOWERS_POOL, 4, 3);
		CHESTNUT_TREE_MIX = buildClassicTree(CHESTNUT_LOG, CHESTNUT_LEAVES_MIX_POOL, 4, 3);
		CHESTNUT_TREE_MIX_BEES = buildClassicTreeWithBees(CHESTNUT_LOG, CHESTNUT_LEAVES_MIX_POOL, 4, 3);
	}

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

	@SuppressWarnings("rawtypes")
	public static <FC extends IFeatureConfig> WeightedRandomFeatureConfig fromEntries(Object... entries) {
		try {
			List<WeightedFeature<FC>> tempList = new ArrayList<>();

			for (int i = 0; i < entries.length; i += 2) {
				ConfiguredFeature<FC, ?> feature = (ConfiguredFeature<FC, ?>) entries[i];
				int weight = (Integer) entries[i + 1];
				WeightedFeature<FC> wf = WeightedFeature.make(feature, weight);
				tempList.add(wf);
			}

			return new WeightedRandomFeatureConfig<FC>(ImmutableList.copyOf(tempList));
		} catch (ArrayIndexOutOfBoundsException | ClassCastException var6) {
			throw new IllegalArgumentException(
					"Error adding biome trees! A list of (tree1, weight1), (tree2, weight2)... is required", var6);
		}
	}
}
