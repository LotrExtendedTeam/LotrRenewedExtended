package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;

public class ExtendedItemLootGenerator extends ExtendedAbstractLootGenerator  {
	protected static final List<ResourceLocation> madeitems = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_HELMET, makeTable()
				.withPool(makePool()
						.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(5)))
				);
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_CHESTPLATE, makeTable()
				.withPool(makePool()
						.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(5)))
				);
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_LEGGINGS, makeTable()
				.withPool(makePool()
						.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(5)))
				);
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_BOOTS, makeTable()
				.withPool(makePool()
						.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(5)))
				);
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_SWORD, makeTable()
				.withPool(makePool()
						.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(5)))
				);
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_DAGGER, makeTable()
				.withPool(makePool()
						.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(5)))
				);
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeitems.add(loc);
	}
}