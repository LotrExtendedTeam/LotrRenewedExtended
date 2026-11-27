package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;

public class ExtendedItemLootGenerator extends ExtendedAbstractLootGenerator  {
	protected static final List<ResourceLocation> madeitems = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_HELMET, makeTable().withPool(makePool()
			.add(addEntry(LOTRItems.MORDOR_HELMET).setWeight(100))
			.add(addEntry(LOTRItems.GONDOR_HELMET).setWeight(50))
			.add(addEntry(LOTRItems.ARNOR_HELMET).setWeight(50))
			.add(addEntry(LOTRItems.LINDON_HELMET).setWeight(20))
			.add(addEntry(LOTRItems.RIVENDELL_HELMET).setWeight(20))
			.add(addEntry(LOTRItems.GALADHRIM_HELMET).setWeight(20))
			.add(addEntry(LOTRItems.WOOD_ELVEN_HELMET).setWeight(20))
			.add(addEntry(ExtendedItems.IMLADRIS_GUARD_HELMET).setWeight(1))
			.add(addEntry(ExtendedItems.EREGION_HELMET).setWeight(1))
			.add(addEntry(LOTRItems.DWARVEN_HELMET).setWeight(10))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_HELMET).setWeight(5))));
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_CHESTPLATE, makeTable().withPool(makePool()
			.add(addEntry(LOTRItems.MORDOR_CHESTPLATE).setWeight(100))
			.add(addEntry(LOTRItems.GONDOR_CHESTPLATE).setWeight(50))
			.add(addEntry(LOTRItems.ARNOR_CHESTPLATE).setWeight(50))
			.add(addEntry(LOTRItems.LINDON_CHESTPLATE).setWeight(20))
			.add(addEntry(LOTRItems.RIVENDELL_CHESTPLATE).setWeight(20))
			.add(addEntry(LOTRItems.GALADHRIM_CHESTPLATE).setWeight(20))
			.add(addEntry(LOTRItems.WOOD_ELVEN_CHESTPLATE).setWeight(20))
			.add(addEntry(ExtendedItems.IMLADRIS_GUARD_CHESTPLATE).setWeight(1))
			.add(addEntry(ExtendedItems.EREGION_CHESTPLATE).setWeight(1))
			.add(addEntry(LOTRItems.DWARVEN_CHESTPLATE).setWeight(10))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_CHESTPLATE).setWeight(5))));
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_LEGGINGS, makeTable().withPool(makePool()
			.add(addEntry(LOTRItems.MORDOR_LEGGINGS).setWeight(100))
			.add(addEntry(LOTRItems.GONDOR_LEGGINGS).setWeight(50))
			.add(addEntry(LOTRItems.ARNOR_LEGGINGS).setWeight(50))
			.add(addEntry(LOTRItems.LINDON_LEGGINGS).setWeight(20))
			.add(addEntry(LOTRItems.RIVENDELL_LEGGINGS).setWeight(20))
			.add(addEntry(LOTRItems.GALADHRIM_LEGGINGS).setWeight(20))
			.add(addEntry(LOTRItems.WOOD_ELVEN_LEGGINGS).setWeight(20))
			.add(addEntry(ExtendedItems.IMLADRIS_GUARD_LEGGINGS).setWeight(1))
			.add(addEntry(ExtendedItems.EREGION_LEGGINGS).setWeight(1))
			.add(addEntry(LOTRItems.DWARVEN_LEGGINGS).setWeight(10))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_LEGGINGS).setWeight(5))));
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_BOOTS, makeTable().withPool(makePool()
			.add(addEntry(LOTRItems.MORDOR_BOOTS).setWeight(100))
			.add(addEntry(LOTRItems.GONDOR_BOOTS).setWeight(50))
			.add(addEntry(LOTRItems.ARNOR_BOOTS).setWeight(50))
			.add(addEntry(LOTRItems.LINDON_BOOTS).setWeight(20))
			.add(addEntry(LOTRItems.RIVENDELL_BOOTS).setWeight(20))
			.add(addEntry(LOTRItems.GALADHRIM_BOOTS).setWeight(20))
			.add(addEntry(LOTRItems.WOOD_ELVEN_BOOTS).setWeight(20))
			.add(addEntry(ExtendedItems.IMLADRIS_GUARD_BOOTS).setWeight(1))
			.add(addEntry(ExtendedItems.EREGION_BOOTS).setWeight(1))
			.add(addEntry(LOTRItems.DWARVEN_BOOTS).setWeight(10))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_BOOTS).setWeight(5))));
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_SWORD, makeTable().withPool(makePool()
			.add(addEntry(LOTRItems.MORDOR_SCIMITAR).setWeight(100))
			.add(addEntry(LOTRItems.GONDOR_SWORD).setWeight(50))
			.add(addEntry(LOTRItems.ARNOR_SWORD).setWeight(50))
			.add(addEntry(LOTRItems.LINDON_SWORD).setWeight(20))
			.add(addEntry(LOTRItems.RIVENDELL_SWORD).setWeight(20))
			.add(addEntry(LOTRItems.GALADHRIM_SWORD).setWeight(20))
			.add(addEntry(LOTRItems.WOOD_ELVEN_SWORD).setWeight(20))
			.add(addEntry(LOTRItems.DWARVEN_SWORD).setWeight(10))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SWORD).setWeight(5))));
		make(consumer, ExtendedLootTables.ANCIENT_ARMOR_DAGGER, makeTable().withPool(makePool()
			.add(addEntry(LOTRItems.MORDOR_DAGGER).setWeight(100))
			.add(addEntry(LOTRItems.GONDOR_DAGGER).setWeight(50))
			.add(addEntry(LOTRItems.ARNOR_DAGGER).setWeight(50))
			.add(addEntry(LOTRItems.LINDON_DAGGER).setWeight(20))
			.add(addEntry(LOTRItems.RIVENDELL_DAGGER).setWeight(20))
			.add(addEntry(LOTRItems.GALADHRIM_DAGGER).setWeight(20))
			.add(addEntry(LOTRItems.WOOD_ELVEN_DAGGER).setWeight(20))
			.add(addEntry(LOTRItems.DWARVEN_DAGGER).setWeight(10))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_DAGGER).setWeight(5))));
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeitems.add(loc);
	}
}