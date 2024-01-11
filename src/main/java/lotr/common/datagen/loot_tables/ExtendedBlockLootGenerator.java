package lotr.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.ExtendedBerryBushBlock;
import lotr.common.block.ExtendedBerryPlantBlock;
import lotr.common.block.ExtendedDirectionalBundleBlock;
import lotr.common.block.ExtendedPackedBundleBlock;
import lotr.common.block.ExtendedSpawnerBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.MineralBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedBlockLootGenerator extends BlockLootTables  {

	@Override
	protected void add(Block p_218507_1_, LootTable.Builder p_218507_2_) {
		if(p_218507_1_.getLootTable().equals(new ResourceLocation("minecraft:empty"))) {
			System.out.println("FOUND EMPTY: "+p_218507_1_.getRegistryName().toString());
		}
		super.add(p_218507_1_, p_218507_2_);
	   }

	@Override
    protected void addTables() {
		dropSelf(ExtendedBlocks.MORGUL_LANTERN.get());
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof AxialSlabBlock) {
				add(block.getValue().get(), BlockLootTables::createSlabItemTable);
				continue;
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof LOTRPillarBlock) {
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
			if(block.getValue().get() instanceof FactionCraftingTableBlock) {
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
			if(block.getValue().get() instanceof ExtendedPackedBundleBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedDirectionalBundleBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			add(ExtendedBlocks.AMBER_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.AMBER.get());});
			add(ExtendedBlocks.TOPAZ_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.TOPAZ.get());});
			add(ExtendedBlocks.AMETHYST_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.AMETHYST.get());});
			add(ExtendedBlocks.SAPPHIRE_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.SAPPHIRE.get());});
			add(ExtendedBlocks.RUBY_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.RUBY.get());});
			add(ExtendedBlocks.DIAMOND_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.DIAMOND.get());});
			add(ExtendedBlocks.OPAL_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.OPAL.get());});
			add(ExtendedBlocks.EMERALD_ORE.get(), (p_241170_0_) -> {
		         return createOreDrop(p_241170_0_, ExtendedItems.EMERALD.get());});
			if(block.getValue().get() instanceof ExtendedBerryBushBlock) {
				dropSelf(block.getValue().get());
				continue;
			}
			if(block.getValue().get() instanceof ExtendedBerryPlantBlock) {
				ExtendedBerryPlantBlock blok = (ExtendedBerryPlantBlock)block.getValue().get();
				add(blok, (p_241171_0_) -> {
			         return applyExplosionDecay(p_241171_0_, 
			        		 LootTable.lootTable().withPool(LootPool.lootPool()
			        				 .when(BlockStateProperty.hasBlockStateProperties(blok)
					        		 .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
					        		 .add(ItemLootEntry.lootTableItem(blok.getBerryItem())).apply(SetCount.setCount(RandomValueRange.between(2.0F, 3.0F)))
					        		 .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool()
							        		 .when(BlockStateProperty.hasBlockStateProperties(blok)
			        						 .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)))
			        						 .add(ItemLootEntry.lootTableItem(blok.getBerryItem())).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))
			        						 .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));});
				continue;
			}
		}
    }
	
	@Override
    protected Iterable<Block> getKnownBlocks() {
		List<RegistryObject<Block>> filtered = new ArrayList<>();
		for(Entry<String, RegistryObject<Block>> bl : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(!(bl.getValue().get() instanceof ExtendedSpawnerBlock)){
				filtered.add(bl.getValue());
			}
		}
        return filtered.stream().map(RegistryObject::get)::iterator;
    }
}
