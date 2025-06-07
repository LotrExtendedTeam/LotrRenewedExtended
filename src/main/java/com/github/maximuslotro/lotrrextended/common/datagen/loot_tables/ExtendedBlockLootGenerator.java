package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedAncientStoneRuneBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryHedgeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBirdcageBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCakeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedChainBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrucibleBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrystalChandelierBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFireBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneGrinderBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundDirtBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundGrassBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundPathBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargeFlowerPotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargePotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMossBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMossCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedRopeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedThreeTallPillarBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedVineBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWargCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFullModelBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBarrelBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedSingleChestBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedGroupSpawnerBlock;
import com.github.maximuslotro.lotrrextended.common.block.spawners.ExtendedSpawnerBlock;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.ChandelierBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedDwarvenGateBlock;
import lotr.common.block.ExtendedPlantableSpreadableAxialSlabBlock;
import lotr.common.block.ExtendedHearthBlock;
import lotr.common.block.ExtendedLanternBlock;
import lotr.common.block.ExtendedPackedFurBlock;
import lotr.common.block.ExtendedTwoTypeCraftingTableBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.MineralBlock;
import lotr.common.block.StrippableBranchBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.block.WoodBeamBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRBlocks;
import lotr.common.loot.functions.VesselDrink;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockLootGenerator extends BlockLootTables  {
	private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
	private static final ILootCondition.IBuilder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
	private static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
	private static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final ILootCondition.IBuilder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

	@Override
	protected void add(Block p_218507_1_, LootTable.Builder p_218507_2_) {
		if(p_218507_1_.getLootTable().equals(new ResourceLocation("minecraft:empty"))) {
			System.out.println("FOUND EMPTY: "+p_218507_1_.getRegistryName().toString());
		}
		super.add(p_218507_1_, p_218507_2_);
	}

	@Override
	protected void addTables() {
		// Fix Vanilla and Renewed block loot-tables
		createCakePool(Blocks.CAKE);
		createCakePool(LOTRBlocks.CHERRY_PIE.get());
		createCakePool(LOTRBlocks.APPLE_CRUMBLE.get());
		// Generate Extended's block loot-tables
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof ExtendedPlantableSpreadableAxialSlabBlock) {
				add(block.getValue().get(), createGrowableSlabItemTable((SlabBlock)block.getValue().get(),((ExtendedPlantableSpreadableAxialSlabBlock)block.getValue().get()).getBaseForLootTables().get()));
				continue;
			}
			if(block.getValue().get() instanceof AxialSlabBlock) {
				add(block.getValue().get(), BlockLootTables::createSlabItemTable);
				continue;
			}
			if((block.getValue().get() instanceof LOTRWallBlock) || (block.getValue().get() instanceof ExtendedAncientStoneRuneBlock)) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedVineBlock) {
				add(block.getValue().get(), BlockLootTables::createShearsOnlyDrop);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedFireBlock) {
				add(block.getValue().get(), noDrop());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedLanternBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedCrucibleBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedCrystalChandelierBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedDwarvenGateBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBirdcageBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedLargePotBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedLargeFlowerPotBlock) {
				dropExtendedPottedContents(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedChainBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRPillarBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedThreeTallPillarBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ChandelierBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof MineralBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRStoneBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedStoneBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedRopeBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedMossBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedMossCarpetBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedConquestTableBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof FactionCraftingTableBlock) {
				if(block.getValue().get() instanceof ExtendedTwoTypeCraftingTableBlock) {
					dropSelf(block.getValue().get());
					continue;
				}
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRTorchBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRWallTorchBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof StrippableLogBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof StrippableWoodBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRStrippedLogBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRStrippedWoodBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof WoodBeamBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRPlanksBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRFenceBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRFenceGateBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRTrapdoorBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedWargCarpetBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof TrapDoorBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof StrippableBranchBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof BranchBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRWoodPressurePlateBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRWoodButtonBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRStandingSignBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRSaplingBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof VesselDrinkBlock) {
				add(block.getValue().get(), ExtendedBlockLootGenerator::createDrinkVesselTable);
				continue;
			}
			if(block.getValue().get() instanceof LOTRDoorBlock) {
				add(block.getValue().get(), BlockLootTables::createDoorTable);
				continue;
			}
			if(block.getValue().get() instanceof DoorBlock) {
				add(block.getValue().get(), BlockLootTables::createDoorTable);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBedBlock) {
				add(block.getValue().get(), (p_218569_0_) -> createSinglePropConditionTable(p_218569_0_, BedBlock.PART, BedPart.HEAD));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedFullModelBedBlock) {
				add(block.getValue().get(), (p_218569_0_) -> createSinglePropConditionTable(p_218569_0_, BedBlock.PART, BedPart.HEAD));
				continue;
			}
			if(block.getValue().get() instanceof LOTRPottedPlantBlock) {
				dropPottedContents(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedPackedFurBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedCakeBlock) {
				createCakePool(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedDirectionalBundleBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundDirtBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedHearthBlock) {
				dropOther(block.getValue().get(), LOTRBlocks.HEARTH_BLOCK.get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedWeaponRackBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof FallenLeavesBlock) {
				dropWhenSilkTouch(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundFarmlandBlock) {
				dropOther(block.getValue().get(), ((ExtendedGroundFarmlandBlock)block.getValue().get()).getBase().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundGrassBlock) {
				add(block.getValue().get(), (p_241170_0_) -> createSingleItemTableWithSilkTouch(p_241170_0_, ((ExtendedGroundGrassBlock)block.getValue().get()).getBase().get()));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedGroundPathBlock) {
				add(block.getValue().get(), (p_241170_0_) -> createSingleItemTableWithSilkTouch(p_241170_0_, ((ExtendedGroundPathBlock)block.getValue().get()).getBase().get()));
				continue;
			}
			if(block.getValue().get() instanceof ExtendedMEBannerBlock) {
				add(block.getValue().get(), ExtendedBlockLootGenerator::createBannerDrop);
			}
			if(block.getValue().get() instanceof ExtendedMEBannerWallBlock) {
				add(block.getValue().get(), ExtendedBlockLootGenerator::createWallBannerDrop);
			}
			if(block.getValue().get() instanceof ExtendedSingleChestBlock) {
				add(block.getValue().get(), ExtendedBlockLootGenerator::createNameableBlockEntityTable);
			}
			if(block.getValue().get() instanceof ExtendedBarrelBlock) {
				add(block.getValue().get(), ExtendedBlockLootGenerator::createNameableBlockEntityTable);
			}
			if(block.getValue().get() instanceof ExtendedStoneGrinderBlock) {
				add(block.getValue().get(), ExtendedBlockLootGenerator::createNameableBlockEntityTable);
			}
			if(block.getValue().get() instanceof ExtendedBerryHedgeBlock) {
				ExtendedBerryHedgeBlock thingy = (ExtendedBerryHedgeBlock)block.getValue().get();
				add(thingy, (p_241171_0_) -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(thingy).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedBerryHedgeBlock.AGE, 3)))
                                .add(ItemLootEntry.lootTableItem(thingy.getBerryItem())).apply(SetCount.setCount(RandomValueRange.between(1.0F, 7.0F)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(thingy).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedBerryHedgeBlock.AGE, 3)))
                                .add(ItemLootEntry.lootTableItem(thingy.getBerryHedgeItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(thingy).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedBerryHedgeBlock.AGE, 2)))
                                .add(ItemLootEntry.lootTableItem(thingy.getBerryHedgeItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(thingy).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedBerryHedgeBlock.AGE, 1)))
                                .add(ItemLootEntry.lootTableItem(thingy.getBerryHedgeItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(thingy).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedBerryHedgeBlock.AGE, 0)))
                                .add(ItemLootEntry.lootTableItem(thingy.getBerryHedgeItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
						);
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBerryBushBlock) {
				ExtendedBerryBushBlock blok = (ExtendedBerryBushBlock)block.getValue().get();
				add(blok, (p_241171_0_) -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(blok).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
                                .add(ItemLootEntry.lootTableItem(blok.getBerryItem())).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(blok).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)))
                                .add(ItemLootEntry.lootTableItem(blok.getBerryItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(blok).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 1)))
                                .add(ItemLootEntry.lootTableItem(blok.getBerryItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
                        .withPool(LootPool.lootPool()
                                .when(BlockStateProperty.hasBlockStateProperties(blok).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 0)))
                                .add(ItemLootEntry.lootTableItem(blok.getBerryItem())).apply(SetCount.setCount(ConstantRange.exactly(1))).apply(ExplosionDecay.explosionDecay()))
						);
				continue;
			}
		}

		dropSelf(ExtendedBlocks.BARREN_DIRT.get());
		add(ExtendedBlocks.CHESTNUT_LEAVES.get(), (p_241170_0_) -> createLeavesDrops(p_241170_0_, ExtendedBlocks.CHESTNUT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.CHESTNUT_LEAVES_FRUIT.get(), (p_241170_0_) -> createFruitLeavesDrops(p_241170_0_, ExtendedBlocks.CHESTNUT_SAPLING.get(), ExtendedItems.CONKER.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.BANANA_LEAVES.get(), (p_241170_0_) -> createLeavesDrops(p_241170_0_, ExtendedBlocks.BANANA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.HANGING_BANANA.get(), (block) -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ExtendedItems.BANANA.get())
                .apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))))));
		add(ExtendedBlocks.PLUM_LEAVES.get(), (p_241170_0_) -> createLeavesDrops(p_241170_0_, ExtendedBlocks.PLUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.PLUM_LEAVES_FRUIT.get(), (p_241170_0_) -> createFruitLeavesDrops(p_241170_0_, ExtendedBlocks.PLUM_SAPLING.get(), ExtendedItems.PLUM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.WILLOW_LEAVES.get(), (p_241170_0_) -> createLeavesDrops(p_241170_0_, ExtendedBlocks.WILLOW_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.AMBER_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.AMBER.get()));
		add(ExtendedBlocks.TOPAZ_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.TOPAZ.get()));
		add(ExtendedBlocks.AMETHYST_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.AMETHYST.get()));
		add(ExtendedBlocks.SAPPHIRE_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.SAPPHIRE.get()));
		add(ExtendedBlocks.RUBY_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.RUBY.get()));
		add(ExtendedBlocks.DIAMOND_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.DIAMOND.get()));
		add(ExtendedBlocks.OPAL_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.OPAL.get()));
		add(ExtendedBlocks.EMERALD_ORE.get(), (p_241170_0_) -> createOreDrop(p_241170_0_, ExtendedItems.EMERALD.get()));
		dropSelf(ExtendedBlocks.REMAINS_ELF.get());
		dropSelf(ExtendedBlocks.REMAINS_MAN.get());
		dropSelf(ExtendedBlocks.REMAINS_ORC.get());
		dropSelf(ExtendedBlocks.REMAINS_DWARF.get());
		add(ExtendedBlocks.PODZOL_SLAB.get(), createGrowableSlabItemTable((SlabBlock)ExtendedBlocks.PODZOL_SLAB.get(), ExtendedBlocks.DIRT_SLAB.get()));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		List<RegistryObject<Block>> filtered = new ArrayList<>();
		for(Entry<String, RegistryObject<Block>> bl : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if((bl.getValue().get() instanceof ExtendedSpawnerBlock) || (bl.getValue().get() instanceof ExtendedGroupSpawnerBlock)){
				continue;
			}
			filtered.add(bl.getValue());
		}
		filtered.add(LOTRBlocks.CHERRY_PIE);
		filtered.add(LOTRBlocks.APPLE_CRUMBLE);
		Stream<Block> blocks= filtered.stream().map(RegistryObject::get);
		blocks = Stream.concat(blocks, Stream.of(Blocks.CAKE));
		return blocks::iterator;
	}

	protected void createCakePool(Block block){
		add(block, (lamdaBlock) -> LootTable.lootTable().withPool(applyExplosionCondition(lamdaBlock,
                LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(lamdaBlock)
                        .when(BlockStateProperty.hasBlockStateProperties(lamdaBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CakeBlock.BITES, 0)))))));
	}

	protected static LootTable.Builder createNameableBlockEntityTable(Block p_218481_0_) {
		return LootTable.lootTable().withPool(applyExplosionCondition(p_218481_0_, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218481_0_).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))));
	}

	protected static LootTable.Builder createDrinkVesselTable(Block p_218481_0_) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(p_218481_0_).apply(VesselDrink.vesselDrinkBuilder())));
	}

	protected static LootTable.Builder createBannerDrop(Block banner) {
		return LootTable.lootTable().withPool(applyExplosionCondition(banner,
				LootPool.lootPool().setRolls(ConstantRange.exactly(1))
				.when(BlockStateProperty.hasBlockStateProperties(banner).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedMEBannerBlock.HALF, DoubleBlockHalf.LOWER)))
				.add(ItemLootEntry.lootTableItem(banner)
						.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("ProtectionData", "ProtectionData"))
						)));
	}

	protected static LootTable.Builder createWallBannerDrop(Block banner) {
		return LootTable.lootTable().withPool(applyExplosionCondition(banner,
				LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(banner)
						.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("ProtectionData", "ProtectionData"))
						)));
	}

	protected static LootTable.Builder createFruitLeavesDrops(Block p_218526_0_, Block p_218526_1_, IItemProvider item, float... p_218526_2_) {
		return createLeavesDrops(p_218526_0_, p_218526_1_, p_218526_2_).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
				.when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(p_218526_0_, ItemLootEntry.lootTableItem(item))
						.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).apply(ExplosionDecay.explosionDecay())));
	}

	protected static LootTable.Builder createGrowableSlabItemTable(SlabBlock block, Block baseSlabDropNoSilk) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
				.add(ItemLootEntry.lootTableItem(block)
						.apply(SetCount.setCount(ConstantRange.exactly(2)).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))).when(HAS_SILK_TOUCH)
						.otherwise(applyExplosionCondition(block, ItemLootEntry.lootTableItem(baseSlabDropNoSilk)
								.apply(SetCount.setCount(ConstantRange.exactly(2)).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))))));
	}

	public void dropExtendedPottedContents(Block pFlowerPot) {
		this.add(pFlowerPot, (p_241751_0_) -> createExtendedPotFlowerItemTable(((ExtendedLargeFlowerPotBlock)p_241751_0_).getContent()));
	}
	protected static LootTable.Builder createExtendedPotFlowerItemTable(IItemProvider pFlowerPotContent) {
		return LootTable.lootTable().withPool(applyExplosionCondition(ExtendedBlocks.LARGE_FLOWER_POT.get(), LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ExtendedBlocks.LARGE_FLOWER_POT.get())))).withPool(applyExplosionCondition(pFlowerPotContent, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(pFlowerPotContent))));
	}

}
