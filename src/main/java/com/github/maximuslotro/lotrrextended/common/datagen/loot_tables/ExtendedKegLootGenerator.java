package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class ExtendedKegLootGenerator extends ChestLootTables  {

	@Override
    public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		consumer.accept(ExtendedLootTables.HOBBIT_KEGS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                		.setRolls(ConstantRange.exactly(1))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.ALE.get()).setWeight(100).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.CIDER.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.PERRY.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.CHERRY_LIQUEUR.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.APPLE_JUICE.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))));
		consumer.accept(ExtendedLootTables.RANGER_KEGS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                		.setRolls(ConstantRange.exactly(1))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.ALE.get()).setWeight(100).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.CIDER.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.PERRY.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.APPLE_JUICE.get()).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))));
	}
}
