package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedAncientStoneRuneBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBarsBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryHedgeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryBushBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBirdcageBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedBundleBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCakeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedChainBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedChimneyPotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedConquestTableBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrucibleBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedCrystalChandelierBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLanternElvenBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLanternNumenoreanBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFireBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFlowerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedFlowerEffectlessBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneGrinderBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneTextSignBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundDirtBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundGrassBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundPathBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedHoistBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLargeFlowerPotBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedLeekBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMEBannerWallBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMaterialBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMossBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMossCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedNarsilShardsBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedOrcBombBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedMattedFurBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedPlacedBooksBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedRopeBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedSaplingBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStandingSignBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedStoneBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedThreeTallPillarBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedVineBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWargCarpetBlock;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedWasteBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedFullModelBedBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBarrelBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedBookshelfBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedLargePotBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedSingleChestBlock;
import com.github.maximuslotro.lotrrextended.common.block.containers.ExtendedWeaponRackBlock;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.ChandelierBlock;
import lotr.common.block.DripstoneBlock;
import lotr.common.block.ExtendedDwarvenGateBlock;
import lotr.common.block.ExtendedAxialSlabPlantableSpreadableBlock;
import lotr.common.block.ExtendedHearthBlock;
import lotr.common.block.ExtendedBrazierBlock;
import lotr.common.block.ExtendedTwoTypeCraftingTableBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.StrippableBranchBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.block.WoodBeamBlock;
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
import net.minecraft.block.WallBlock;
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
	protected void add(Block pBlock, LootTable.Builder pLootBuilder) {
		if(pBlock.getLootTable().equals(new ResourceLocation("minecraft:empty"))) {
			System.out.println("FOUND EMPTY: "+pBlock.getRegistryName().toString());
		}
		super.add(pBlock, pLootBuilder);
	}

	@Override
	protected void addTables() {
		// Fix Vanilla and Renewed block loot-tables
		createCakePool(Blocks.CAKE);
		createCakePool(LOTRBlocks.CHERRY_PIE.get());
		createCakePool(LOTRBlocks.APPLE_CRUMBLE.get());
		// Generate Extended's block loot-tables
		for(RegistryObject<Block> block : ExtendedBlocks.BLOCKS.getEntries()) {
			if(block.get() instanceof ExtendedAxialSlabPlantableSpreadableBlock) {
				add(block.get(), createGrowableSlabItemTable((SlabBlock)block.get(),((ExtendedAxialSlabPlantableSpreadableBlock)block.get()).getBaseForLootTables().get()));
				continue;
			}
			if(block.get() instanceof AxialSlabBlock) {
				add(block.get(), BlockLootTables::createSlabItemTable);
				continue;
			}
			if((block.get() instanceof WallBlock) || (block.get() instanceof ExtendedAncientStoneRuneBlock)) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedVineBlock) {
				add(block.get(), BlockLootTables::createShearsOnlyDrop);
				continue;
			}
			if(block.get() instanceof ExtendedFireBlock) {
				add(block.get(), noDrop());
				continue;
			}
			if(block.get() instanceof ExtendedStoneTextSignBlock) {
				add(block.get(), noDrop());
				continue;
			}
			if(block.get() instanceof ExtendedPlacedBooksBlock) {
				add(block.get(), noDrop());
				continue;
			}
			if(block.get() instanceof ExtendedBrazierBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof DripstoneBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedLanternElvenBlock || block.get() instanceof ExtendedLanternNumenoreanBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedCrucibleBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedBookshelfBlock) {
				add(block.get(), (blockP) -> {
					return createSingleItemTableWithSilkTouch(Blocks.BOOKSHELF, Items.BOOK, ConstantRange.exactly(3));
				});
				continue;
			}
			if(block.get() instanceof ExtendedWasteBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedCrystalChandelierBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedDwarvenGateBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedBirdcageBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedBarsBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRStairsBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedLargePotBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedLargeFlowerPotBlock) {
				dropExtendedPottedContents(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedChainBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedNarsilShardsBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRPillarBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedThreeTallPillarBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedHoistBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ChandelierBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedMaterialBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRStoneBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedStoneBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedRopeBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedMossBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedMossCarpetBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedConquestTableBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof FactionCraftingTableBlock) {
				if(block.get() instanceof ExtendedTwoTypeCraftingTableBlock) {
					dropSelf(block.get());
					continue;
				}
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRTorchBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRWallTorchBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof StrippableLogBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof StrippableWoodBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRStrippedLogBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRStrippedWoodBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof WoodBeamBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRPlanksBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRFenceBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRFenceGateBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRTrapdoorBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedWargCarpetBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof TrapDoorBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof StrippableBranchBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof BranchBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRWoodPressurePlateBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof LOTRWoodButtonBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedStandingSignBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedSaplingBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof VesselDrinkBlock) {
				add(block.get(), ExtendedBlockLootGenerator::createDrinkVesselTable);
				continue;
			}
			if(block.get() instanceof LOTRDoorBlock) {
				add(block.get(), BlockLootTables::createDoorTable);
				continue;
			}
			if(block.get() instanceof DoorBlock) {
				add(block.get(), BlockLootTables::createDoorTable);
				continue;
			}
			if(block.get() instanceof ExtendedBedBlock) {
				add(block.get(), (blockLamda) -> createSinglePropConditionTable(blockLamda, BedBlock.PART, BedPart.HEAD));
				continue;
			}
			if(block.get() instanceof ExtendedFullModelBedBlock) {
				add(block.get(), (blockLamda) -> createSinglePropConditionTable(blockLamda, BedBlock.PART, BedPart.HEAD));
				continue;
			}
			if(block.get() instanceof LOTRPottedPlantBlock) {
				dropPottedContents(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedMattedFurBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedCakeBlock) {
				createCakePool(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedBundleBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedChimneyPotBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedGroundDirtBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedFlowerBlock || block.get() instanceof ExtendedFlowerEffectlessBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof ExtendedHearthBlock) {
				dropOther(block.get(), LOTRBlocks.HEARTH_BLOCK.get());
				continue;
			}
			if(block.get() instanceof ExtendedWeaponRackBlock) {
				dropSelf(block.get());
				continue;
			}
			if(block.get() instanceof FallenLeavesBlock) {
				dropWhenSilkTouch(block.get());//TODO apply shears or explosion conition
				continue;
			}
			if(block.get() instanceof ExtendedGroundFarmlandBlock) {
				dropOther(block.get(), ((ExtendedGroundFarmlandBlock)block.get()).getBase().get());
				continue;
			}
			if(block.get() instanceof ExtendedGroundGrassBlock) {
				add(block.get(), (blockLamda) -> createSingleItemTableWithSilkTouch(blockLamda, ((ExtendedGroundGrassBlock)block.get()).getBase().get()));
				continue;
			}
			if(block.get() instanceof ExtendedGroundPathBlock) {
				add(block.get(), (blockLamda) -> createSingleItemTableWithSilkTouch(blockLamda, ((ExtendedGroundPathBlock)block.get()).getBase().get()));
				continue;
			}
			if(block.get() instanceof ExtendedMEBannerBlock) {
				add(block.get(), ExtendedBlockLootGenerator::createBannerDrop);
			}
			if(block.get() instanceof ExtendedMEBannerWallBlock) {
				add(block.get(), ExtendedBlockLootGenerator::createWallBannerDrop);
			}
			if(block.get() instanceof ExtendedSingleChestBlock) {
				add(block.get(), ExtendedBlockLootGenerator::createNameableBlockEntityTable);
			}
			if(block.get() instanceof ExtendedBarrelBlock) {
				add(block.get(), ExtendedBlockLootGenerator::createNameableBlockEntityTable);
			}
			if(block.get() instanceof ExtendedStoneGrinderBlock) {
				add(block.get(), ExtendedBlockLootGenerator::createNameableBlockEntityTable);
			}
			if(block.get() instanceof ExtendedOrcBombBlock) {
				add(block.get(), LootTable.lootTable().withPool(applyExplosionCondition(block.get(), LootPool.lootPool().setRolls(ConstantRange.exactly(1))
					.add(ItemLootEntry.lootTableItem(block.get()).when(BlockStateProperty.hasBlockStateProperties(block.get())
						.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedOrcBombBlock.UNSTABLE, false)))))));
			}
			if(block.get() instanceof ExtendedBerryHedgeBlock) {
				ExtendedBerryHedgeBlock thingy = (ExtendedBerryHedgeBlock)block.get();
				add(thingy, (blockLamda) -> LootTable.lootTable()
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
			if(block.get() instanceof ExtendedBerryBushBlock) {
				ExtendedBerryBushBlock blok = (ExtendedBerryBushBlock)block.get();
				add(blok, (blockLamda) -> LootTable.lootTable()
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
		add(ExtendedBlocks.CHESTNUT_LEAVES.get(), (blockLamda) -> createLeavesDrops(blockLamda, ExtendedBlocks.CHESTNUT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.CHESTNUT_LEAVES_FRUIT.get(), (blockLamda) -> createFruitLeavesDrops(blockLamda, ExtendedBlocks.CHESTNUT_SAPLING.get(), ExtendedItems.CONKER.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.BANANA_LEAVES.get(), (blockLamda) -> createLeavesDrops(blockLamda, ExtendedBlocks.BANANA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.HANGING_BANANA.get(), (block) -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ExtendedItems.BANANA.get())
			.apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))))));
		add(ExtendedBlocks.PLUM_LEAVES.get(), (blockLamda) -> createLeavesDrops(blockLamda, ExtendedBlocks.PLUM_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.PLUM_LEAVES_FRUIT.get(), (blockLamda) -> createFruitLeavesDrops(blockLamda, ExtendedBlocks.PLUM_SAPLING.get(), ExtendedItems.PLUM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.WILLOW_LEAVES.get(), (blockLamda) -> createLeavesDrops(blockLamda, ExtendedBlocks.WILLOW_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.EBONY_LEAVES.get(), (blockLamda) -> createLeavesDrops(blockLamda, ExtendedBlocks.EBONY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(ExtendedBlocks.AMBER_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.AMBER.get()));
		add(ExtendedBlocks.TOPAZ_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.TOPAZ.get()));
		add(ExtendedBlocks.AMETHYST_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.AMETHYST.get()));
		add(ExtendedBlocks.SAPPHIRE_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.SAPPHIRE.get()));
		add(ExtendedBlocks.RUBY_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.RUBY.get()));
		add(ExtendedBlocks.DIAMOND_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.DIAMOND.get()));
		add(ExtendedBlocks.OPAL_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.OPAL.get()));
		add(ExtendedBlocks.EMERALD_ORE.get(), (blockLamda) -> createOreDrop(blockLamda, ExtendedItems.EMERALD.get()));
		dropSelf(ExtendedBlocks.REMAINS_ELF.get());
		dropSelf(ExtendedBlocks.REMAINS_MAN.get());
		dropSelf(ExtendedBlocks.REMAINS_ORC.get());
		dropSelf(ExtendedBlocks.REMAINS_DWARF.get());
		add(ExtendedBlocks.PODZOL_SLAB.get(), createGrowableSlabItemTable((SlabBlock)ExtendedBlocks.PODZOL_SLAB.get(), ExtendedBlocks.DIRT_SLAB.get()));
		ILootCondition.IBuilder leekConditionBuilder = BlockStateProperty.hasBlockStateProperties(ExtendedBlocks.LEEKS.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ExtendedLeekBlock.AGE, 7));
		add(ExtendedBlocks.LEEKS.get(), (blockLamda) -> applyExplosionDecay(blockLamda, LootTable.lootTable()
			.withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(ExtendedItems.LEEK.get())))
			.withPool(LootPool.lootPool().when(leekConditionBuilder).add(ItemLootEntry.lootTableItem(ExtendedItems.LEEK.get()).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));
		dropSelf(ExtendedBlocks.STONEROOT.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		List<RegistryObject<Block>> filtered = new ArrayList<>();
		for(RegistryObject<Block> bl : ExtendedBlocks.BLOCKS.getEntries()) {
			filtered.add(bl);
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

	protected static LootTable.Builder createNameableBlockEntityTable(Block pBlock) {
		return LootTable.lootTable().withPool(applyExplosionCondition(pBlock, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(pBlock).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))));
	}

	protected static LootTable.Builder createDrinkVesselTable(Block pBlock) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(pBlock).apply(VesselDrink.vesselDrinkBuilder())));
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

	protected static LootTable.Builder createFruitLeavesDrops(Block pLeavesBlock, Block pBlockSapling, IItemProvider item, float... pChances) {
		return createLeavesDrops(pLeavesBlock, pBlockSapling, pChances).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
			.when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(pLeavesBlock, ItemLootEntry.lootTableItem(item))
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
		this.add(pFlowerPot, (blockLamda) -> createExtendedPotFlowerItemTable(((ExtendedLargeFlowerPotBlock)blockLamda).getContent()));
	}
	protected static LootTable.Builder createExtendedPotFlowerItemTable(IItemProvider pFlowerPotContent) {
		return LootTable.lootTable().withPool(applyExplosionCondition(ExtendedBlocks.LARGE_FLOWER_POT.get(), LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ExtendedBlocks.LARGE_FLOWER_POT.get())))).withPool(applyExplosionCondition(pFlowerPotContent, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(pFlowerPotContent))));
	}

}
