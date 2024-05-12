package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class ExtendedKegLootGenerator extends ChestLootTables  {

	@Override
    public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.HOBBIT_KEGS, makePool()
                		.add(addEntry(LOTRItems.ALE.get(), 100))
                		.add(addEntry(LOTRItems.CIDER.get(), 50))
                		.add(addEntry(LOTRItems.PERRY.get(), 50))
                		.add(addEntry(LOTRItems.CHERRY_LIQUEUR.get(), 50))
                		.add(addEntry(LOTRItems.APPLE_JUICE.get(), 50)));

		make(consumer, ExtendedLootTables.RANGER_KEGS, makePool()
                		.add(addEntry(LOTRItems.ALE.get(), 100))
                		.add(addEntry(LOTRItems.CIDER.get(), 50))
                		.add(addEntry(LOTRItems.PERRY.get(), 50))
                		.add(addEntry(LOTRItems.APPLE_JUICE.get(), 50)));
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item, int weight){
		return ItemLootEntry.lootTableItem(item).setWeight(weight).apply(SetCount.setCount(ConstantRange.exactly(1)));
	}

	public LootPool.Builder makePool(){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(1));
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation t, LootPool.Builder pool){
		consumer.accept(t, LootTable.lootTable().withPool(pool));
	}
}
