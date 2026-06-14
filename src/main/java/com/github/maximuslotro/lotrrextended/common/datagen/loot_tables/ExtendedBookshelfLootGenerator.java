package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedBookshelfSlot;
import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierScrollAdder;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

public class ExtendedBookshelfLootGenerator extends ChestLootTables  {
	protected static final List<ResourceLocation> madeBookshelves = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.ISENGARD_ORTHANC_BOOKSHELF, makeTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f))
				.add(addEntry(Items.BOOK).setWeight(1).setRange(1, 2)))
			.withPool(LootPool.lootPool()
				.setRolls(RandomValueRange.between(0, 2))
				.when(RandomChance.randomChance(0.125f))
				.add(addEntry(Items.WRITABLE_BOOK).setWeight(60).setRange(1, 3))
				.add(addEntry(Items.PAPER).setWeight(100).setRange(1, 8))
				.add(addEntry(Items.MAP).setWeight(60).setRange(1, 4))
				.add(addEntry(LOTRItems.BOOK_OF_TRUE_SILVER.get()).setWeight(40).setRange(1, 2))
				.add(addEntry(LOTRItems.RED_BOOK.get()).setWeight(40).setRange(1, 2))
				.add(addEntry(ExtendedItems.ARTISAN_TOME.get()).setWeight(5).setRange(1, 2))
				.add(addEntry(ExtendedItems.COMBAT_TOME.get()).setWeight(5).setRange(1, 2))
				.apply(ExtendedModifierScrollAdder.chanceCustom(20)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.0625f))
				.add(addEntry(Items.FLOWER_BANNER_PATTERN).setWeight(5).setRange(1, 2))
				.add(addEntry(ExtendedItems.ANGMAR_BANNER_PATTERN.get()).setWeight(3).setRange(1, 2))
				.add(addEntry(ExtendedItems.DALE_BANNER_PATTERN.get()).setWeight(4).setRange(1, 2))
				.add(addEntry(ExtendedItems.DOL_AMROTH_BANNER_PATTERN.get()).setWeight(7).setRange(1, 2))
				.add(addEntry(ExtendedItems.DOL_GULDUR_BANNER_PATTERN.get()).setWeight(6).setRange(1, 2))
				.add(addEntry(ExtendedItems.DORWINION_BANNER_PATTERN.get()).setWeight(7).setRange(1, 2))
				.add(addEntry(ExtendedItems.DUNLAND_BANNER_PATTERN.get()).setWeight(8).setRange(1, 2))
				.add(addEntry(ExtendedItems.GONDOR_BANNER_PATTERN.get()).setWeight(9).setRange(1, 2))
				.add(addEntry(ExtendedItems.ISENGARD_BANNER_PATTERN.get()).setWeight(10).setRange(1, 2))
				.add(addEntry(ExtendedItems.LINDON_BANNER_PATTERN.get()).setWeight(6).setRange(1, 2))
				.add(addEntry(ExtendedItems.LOTHLORIEN_BANNER_PATTERN.get()).setWeight(5).setRange(1, 2))
				.add(addEntry(ExtendedItems.MORDOR_BANNER_PATTERN.get()).setWeight(8).setRange(1, 2))
				.add(addEntry(ExtendedItems.RIVENDELL_BANNER_PATTERN.get()).setWeight(6).setRange(1, 2))
				.add(addEntry(ExtendedItems.ROHAN_BANNER_PATTERN.get()).setWeight(9).setRange(1, 2))
				.add(addEntry(ExtendedItems.WOODLAND_REALM_BANNER_PATTERN.get()).setWeight(4).setRange(1, 2)))
			);

	}
	public EItemLootEntry.ExtendedBuilder addEntry(Item item){
		ItemStack stack = new ItemStack(item);
		if(!ExtendedBookshelfSlot.isItemValidForBookshelfSlot(stack)) {
			throw new IllegalArgumentException("Invalid item given for bookcase loot-table: "+item.getRegistryName().toString());
		}
		return EItemLootEntry.lootItem(item);
	}
	public LootTable.Builder makeTable(){
		return LootTable.lootTable();
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeBookshelves.add(loc);
	}
}