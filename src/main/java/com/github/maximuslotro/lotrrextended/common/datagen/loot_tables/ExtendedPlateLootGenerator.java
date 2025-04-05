package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class ExtendedPlateLootGenerator extends ChestLootTables  {
	protected static final List<ResourceLocation> madePlates = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		//TODO add corn bread, leeks, leek soup, turnips, cooked turnips, mushroom pie, cooked corn, marzipan, chocolate marzipan
		make(consumer, ExtendedLootTables.HOBBIT_PLATES, makePool()
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_COD))
				.add(addEntry(Items.COOKED_SALMON))
				.add(addEntry(Items.COOKED_CHICKEN))
				.add(addEntry(Items.COOKED_BEEF))
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_MUTTON))
				.add(addEntry(Items.BAKED_POTATO))
				.add(addEntry(Items.APPLE))
				.add(addEntry(Items.BREAD))
				.add(addEntry(Items.CARROT))
				.add(addEntry(Items.BROWN_MUSHROOM))
				.add(addEntry(Items.RED_MUSHROOM))
				.add(addEntry(Items.PUMPKIN_PIE))
				.add(addEntry(Items.COOKIE))
				.add(addEntry(Items.COOKED_RABBIT))
				.add(addEntry(Items.MUSHROOM_STEW))
				.add(addEntry(Items.RABBIT_STEW))
				.add(addEntry(LOTRItems.GAMMON.get()))
				.add(addEntry(LOTRItems.GREEN_APPLE.get()))
				.add(addEntry(LOTRItems.LETTUCE.get()))
				.add(addEntry(LOTRItems.PEAR.get()))
				.add(addEntry(LOTRItems.CHERRY.get()))
				.add(addEntry(ExtendedItems.DUCK.get()))
				.add(addEntry(ExtendedItems.PLUM.get()))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE.get()))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get()))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get()))
				.add(addEntry(ExtendedItems.BLACKBERRY.get()))
				.add(addEntry(ExtendedItems.BLUEBERRY.get()))
				.add(addEntry(ExtendedItems.CRANBERRY.get()))
				.add(addEntry(ExtendedItems.ELDERBERRY.get()))
				.add(addEntry(ExtendedItems.RASPBERRY.get()))
				.add(addEntry(ExtendedItems.ROASTED_CHESTNUT.get())));

		//TODO add cooked deer
		make(consumer, ExtendedLootTables.RANGER_PLATES, makePool()
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_COD))
				.add(addEntry(Items.COOKED_SALMON))
				.add(addEntry(Items.COOKED_CHICKEN))
				.add(addEntry(Items.COOKED_BEEF))
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_MUTTON))
				.add(addEntry(Items.BAKED_POTATO))
				.add(addEntry(Items.APPLE))
				.add(addEntry(Items.BREAD))
				.add(addEntry(Items.COOKED_RABBIT))
				.add(addEntry(Items.BROWN_MUSHROOM))
				.add(addEntry(Items.RED_MUSHROOM))
				.add(addEntry(Items.MUSHROOM_STEW))
				.add(addEntry(Items.RABBIT_STEW))
				.add(addEntry(LOTRItems.GREEN_APPLE.get()))
				.add(addEntry(LOTRItems.PEAR.get()))
				.add(addEntry(ExtendedItems.DUCK.get()))
				.add(addEntry(ExtendedItems.PLUM.get()))
				.add(addEntry(ExtendedItems.BLACKBERRY.get()))
				.add(addEntry(ExtendedItems.BLUEBERRY.get()))
				.add(addEntry(ExtendedItems.CRANBERRY.get()))
				.add(addEntry(ExtendedItems.ELDERBERRY.get()))
				.add(addEntry(ExtendedItems.RASPBERRY.get()))
				.add(addEntry(ExtendedItems.ROASTED_CHESTNUT.get())));

		make(consumer, ExtendedLootTables.GUNDABAD_PLATES, makePool()
				.add(addEntry(Items.ROTTEN_FLESH))
				.add(addEntry(LOTRItems.MAGGOTY_BREAD.get()))
				.add(addEntry(LOTRItems.MAN_FLESH.get())));

		// TODO add cooked deer, turnip, turnip cooked, mushroom pie, leek, leek soup
		make(consumer, ExtendedLootTables.BREE_MAN_PLATES, makePool()
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_COD))
				.add(addEntry(Items.COOKED_SALMON))
				.add(addEntry(Items.COOKED_CHICKEN))
				.add(addEntry(Items.COOKED_BEEF))
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_MUTTON))
				.add(addEntry(Items.BAKED_POTATO))
				.add(addEntry(Items.APPLE))
				.add(addEntry(Items.BREAD))
				.add(addEntry(Items.CARROT))
				.add(addEntry(Items.COOKED_RABBIT))
				.add(addEntry(Items.RABBIT_STEW))
				.add(addEntry(Items.BROWN_MUSHROOM))
				.add(addEntry(Items.RED_MUSHROOM))
				.add(addEntry(Items.MUSHROOM_STEW))
				.add(addEntry(LOTRItems.GREEN_APPLE.get()))
				.add(addEntry(LOTRItems.PEAR.get()))
				.add(addEntry(LOTRItems.LETTUCE.get()))
				.add(addEntry(LOTRItems.PEAR.get()))
				.add(addEntry(LOTRItems.CHERRY.get()))
				.add(addEntry(ExtendedItems.DUCK.get()))
				.add(addEntry(ExtendedItems.PLUM.get()))
				.add(addEntry(ExtendedItems.BLACKBERRY.get()))
				.add(addEntry(ExtendedItems.BLUEBERRY.get()))
				.add(addEntry(ExtendedItems.CRANBERRY.get()))
				.add(addEntry(ExtendedItems.ELDERBERRY.get()))
				.add(addEntry(ExtendedItems.RASPBERRY.get()))
				.add(addEntry(ExtendedItems.ROASTED_CHESTNUT.get()))
				.add(addEntry(ExtendedItems.CHEESE_SLICE.get()))
				.add(addEntry(ExtendedItems.AGED_CHEESE_SLICE.get())));


		// TODO add cooked deer, turnip, turnip cooked, mushroom pie, leek, leek soup
		make(consumer, ExtendedLootTables.BREE_HOBBIT_PLATES, makePool()
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_COD))
				.add(addEntry(Items.COOKED_SALMON))
				.add(addEntry(Items.COOKED_CHICKEN))
				.add(addEntry(Items.COOKED_BEEF))
				.add(addEntry(Items.COOKED_PORKCHOP))
				.add(addEntry(Items.COOKED_MUTTON))
				.add(addEntry(Items.BAKED_POTATO))
				.add(addEntry(Items.APPLE))
				.add(addEntry(Items.BREAD))
				.add(addEntry(Items.CARROT))
				.add(addEntry(Items.COOKED_RABBIT))
				.add(addEntry(Items.RABBIT_STEW))
				.add(addEntry(Items.BROWN_MUSHROOM))
				.add(addEntry(Items.RED_MUSHROOM))
				.add(addEntry(Items.MUSHROOM_STEW))
				.add(addEntry(LOTRItems.GREEN_APPLE.get()))
				.add(addEntry(LOTRItems.PEAR.get()))
				.add(addEntry(LOTRItems.LETTUCE.get()))
				.add(addEntry(LOTRItems.PEAR.get()))
				.add(addEntry(LOTRItems.CHERRY.get()))
				.add(addEntry(ExtendedItems.DUCK.get()))
				.add(addEntry(ExtendedItems.PLUM.get()))
				.add(addEntry(ExtendedItems.BLACKBERRY.get()))
				.add(addEntry(ExtendedItems.BLUEBERRY.get()))
				.add(addEntry(ExtendedItems.CRANBERRY.get()))
				.add(addEntry(ExtendedItems.ELDERBERRY.get()))
				.add(addEntry(ExtendedItems.RASPBERRY.get()))
				.add(addEntry(ExtendedItems.ROASTED_CHESTNUT.get()))
				.add(addEntry(ExtendedItems.BUTTER.get()))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE.get()))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get()))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get())));

		// Add corn bread, mushroom pie
		make(consumer, ExtendedLootTables.BREE_BAKERY_PLATES, makePool()
				.add(addEntry(Items.BREAD, 1, 2))
				.add(addEntry(Items.PUMPKIN_PIE))
				.add(addEntry(ExtendedItems.CHEESE_SLICE.get(), 1, 3))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE.get(), 1, 3))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get(), 1, 2))
				.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get())));

		// Add deer raw and cooked.
		make(consumer, ExtendedLootTables.BREE_BUTCHER_PLATES, makePool()
			.add(addEntry(Items.PORKCHOP, 1, 2))
			.add(addEntry(Items.CHICKEN, 1, 2))
			.add(addEntry(Items.BEEF, 1, 2))
			.add(addEntry(Items.PORKCHOP, 1, 2))
			.add(addEntry(Items.MUTTON, 1, 2))
			.add(addEntry(Items.RABBIT, 1, 2))
			.add(addEntry(ExtendedItems.RAW_DUCK.get(), 1, 2))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 4))
			.add(addEntry(Items.COOKED_CHICKEN, 1, 4))
			.add(addEntry(Items.COOKED_BEEF, 1, 4))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 4))
			.add(addEntry(Items.COOKED_MUTTON, 1, 4))
			.add(addEntry(Items.COOKED_RABBIT, 1, 4))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(ExtendedItems.DUCK.get(), 1, 4)));

		make(consumer, ExtendedLootTables.BREE_CHEESEMONGER_PLATES, makePool()
			.add(addEntry(ExtendedItems.CHEESE_CURDS.get(), 1, 5))
			.add(addEntry(ExtendedItems.CHEESE_SLICE.get(), 1, 3))
			.add(addEntry(ExtendedItems.CHEESE_WHEEL.get()))
			.add(addEntry(ExtendedItems.AGED_CHEESE_SLICE.get(), 1, 3))
			.add(addEntry(ExtendedItems.AGED_CHEESE_WHEEL.get())));
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item){
		return ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(ConstantRange.exactly(1)));
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item, int min, int max){
		return ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(RandomValueRange.between(min, max)));
	}

	public LootPool.Builder makePool(){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(1));
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation t, LootPool.Builder pool){
		consumer.accept(t, LootTable.lootTable().withPool(pool));
		madePlates.add(t);
	}
}
