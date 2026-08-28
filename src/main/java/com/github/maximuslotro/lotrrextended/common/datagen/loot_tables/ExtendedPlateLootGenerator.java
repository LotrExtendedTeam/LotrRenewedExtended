package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedPlateLootGenerator extends ExtendedAbstractLootGenerator  {
	protected static final List<ResourceLocation> madePlates = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		//TODO add corn bread, turnips, cooked turnips, mushroom pie, cooked corn, marzipan, chocolate marzipan
		make(consumer, ExtendedLootTables.HOBBIT_PLATES, makePool()
			.add(addEntry(Items.COOKED_PORKCHOP))
			.add(addEntry(Items.COOKED_COD))
			.add(addEntry(Items.COOKED_SALMON))
			.add(addEntry(Items.COOKED_CHICKEN))
			.add(addEntry(Items.COOKED_BEEF))
			.add(addEntry(Items.COOKED_PORKCHOP))
			.add(addEntry(Items.COOKED_MUTTON))
			.add(addEntry(Items.BAKED_POTATO, 1, 2))
			.add(addEntry(Items.APPLE, 1, 2))
			.add(addEntry(Items.BREAD, 1, 2))
			.add(addEntry(Items.CARROT, 1, 2))
			.add(addEntry(Items.BROWN_MUSHROOM))
			.add(addEntry(Items.RED_MUSHROOM))
			.add(addEntry(Items.PUMPKIN_PIE))
			.add(addEntry(Items.COOKIE))
			.add(addEntry(Items.COOKED_RABBIT))
			.add(addEntry(Items.MUSHROOM_STEW))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(LOTRItems.GAMMON))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.LETTUCE, 1, 3))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.CHERRY, 1, 2))
			.add(addEntry(ExtendedItems.COOKED_DUCK, 1, 2))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 2))
			.add(addEntry(ExtendedItems.LEEK, 1, 2))
			.add(addEntry(ExtendedItems.LEEK_SOUP)));

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
			.add(addEntry(Items.APPLE, 1, 2))
			.add(addEntry(Items.BREAD, 1, 2))
			.add(addEntry(Items.COOKED_RABBIT))
			.add(addEntry(Items.BROWN_MUSHROOM))
			.add(addEntry(Items.RED_MUSHROOM))
			.add(addEntry(Items.MUSHROOM_STEW))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(ExtendedItems.COOKED_DUCK, 1, 2))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 2)));

		make(consumer, ExtendedLootTables.GUNDABAD_PLATES, makePool()
			.add(addEntry(Items.ROTTEN_FLESH))
			.add(addEntry(LOTRItems.MAGGOTY_BREAD))
			.add(addEntry(LOTRItems.MAN_FLESH)));

		// TODO add cooked deer, turnip, turnip cooked, mushroom pie
		make(consumer, ExtendedLootTables.BREE_MAN_PLATES, makePool()
			.add(addEntry(Items.COOKED_PORKCHOP))
			.add(addEntry(Items.COOKED_COD))
			.add(addEntry(Items.COOKED_SALMON))
			.add(addEntry(Items.COOKED_CHICKEN))
			.add(addEntry(Items.COOKED_BEEF))
			.add(addEntry(Items.COOKED_PORKCHOP))
			.add(addEntry(Items.COOKED_MUTTON))
			.add(addEntry(Items.BAKED_POTATO, 1, 2))
			.add(addEntry(Items.APPLE, 1, 2))
			.add(addEntry(Items.BREAD, 1, 2))
			.add(addEntry(Items.CARROT, 1, 2))
			.add(addEntry(Items.COOKED_RABBIT))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(Items.BROWN_MUSHROOM))
			.add(addEntry(Items.RED_MUSHROOM))
			.add(addEntry(Items.MUSHROOM_STEW))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.LETTUCE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.CHERRY, 1, 2))
			.add(addEntry(ExtendedItems.COOKED_DUCK, 1, 2))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 2))
			.add(addEntry(ExtendedItems.CHEESE_SLICE, 1, 2))
			.add(addEntry(ExtendedItems.AGED_CHEESE_SLICE, 1, 2))
			.add(addEntry(ExtendedItems.LEEK, 1, 2))
			.add(addEntry(ExtendedItems.LEEK_SOUP)));


		// TODO add cooked deer, turnip, turnip cooked, mushroom pie
		make(consumer, ExtendedLootTables.BREE_HOBBIT_PLATES, makePool()
			.add(addEntry(Items.COOKED_PORKCHOP))
			.add(addEntry(Items.COOKED_COD))
			.add(addEntry(Items.COOKED_SALMON))
			.add(addEntry(Items.COOKED_CHICKEN))
			.add(addEntry(Items.COOKED_BEEF))
			.add(addEntry(Items.COOKED_PORKCHOP))
			.add(addEntry(Items.COOKED_MUTTON))
			.add(addEntry(Items.BAKED_POTATO, 1, 2))
			.add(addEntry(Items.APPLE, 1, 2))
			.add(addEntry(Items.BREAD, 1, 2))
			.add(addEntry(Items.CARROT, 1, 2))
			.add(addEntry(Items.COOKED_RABBIT))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(Items.BROWN_MUSHROOM))
			.add(addEntry(Items.RED_MUSHROOM))
			.add(addEntry(Items.MUSHROOM_STEW))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.LETTUCE, 1, 3))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.CHERRY, 1, 2))
			.add(addEntry(ExtendedItems.COOKED_DUCK, 1, 2))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 2))
			.add(addEntry(ExtendedItems.BUTTER, 1, 2))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER))
			.add(addEntry(ExtendedItems.LEEK, 1, 2))
			.add(addEntry(ExtendedItems.LEEK_SOUP)));

		// Add corn bread, mushroom pie
		make(consumer, ExtendedLootTables.BREE_BAKERY_PLATES, makePool()
			.add(addEntry(Items.BREAD, 1, 2))
			.add(addEntry(Items.PUMPKIN_PIE))
			.add(addEntry(ExtendedItems.CHEESE_SLICE, 1, 3))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE, 1, 3))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP, 1, 2))
			.add(addEntry(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER)));

		// Add deer raw and cooked.
		make(consumer, ExtendedLootTables.BREE_BUTCHER_PLATES, makePool()
			.add(addEntry(Items.PORKCHOP, 1, 2))
			.add(addEntry(Items.CHICKEN, 1, 2))
			.add(addEntry(Items.BEEF, 1, 2))
			.add(addEntry(Items.PORKCHOP, 1, 2))
			.add(addEntry(Items.MUTTON, 1, 2))
			.add(addEntry(Items.RABBIT, 1, 2))
			.add(addEntry(ExtendedItems.RAW_DUCK, 1, 2))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 4))
			.add(addEntry(Items.COOKED_CHICKEN, 1, 4))
			.add(addEntry(Items.COOKED_BEEF, 1, 4))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 4))
			.add(addEntry(Items.COOKED_MUTTON, 1, 4))
			.add(addEntry(Items.COOKED_RABBIT, 1, 4))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(ExtendedItems.COOKED_DUCK, 1, 4)));

		make(consumer, ExtendedLootTables.BREE_CHEESEMONGER_PLATES, makePool()
			.add(addEntry(ExtendedItems.CHEESE_CURDS, 1, 5))
			.add(addEntry(ExtendedItems.CHEESE_SLICE, 1, 3))
			.add(addEntry(ExtendedItems.CHEESE_WHEEL))
			.add(addEntry(ExtendedItems.AGED_CHEESE_SLICE, 1, 3))
			.add(addEntry(ExtendedItems.AGED_CHEESE_WHEEL)));

		// TODO add turnips
		make(consumer, ExtendedLootTables.BLUE_MOUNTAINS_PLATE, makePool()
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 3))
			.add(addEntry(Items.COOKED_CHICKEN, 1, 2))
			.add(addEntry(Items.COOKED_BEEF, 1, 3))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 3))
			.add(addEntry(Items.COOKED_MUTTON, 1, 3))
			.add(addEntry(Items.COOKED_RABBIT, 1, 3))
			.add(addEntry(Items.COOKED_SALMON, 1, 3))
			.add(addEntry(Items.COOKED_COD, 1, 3))
			.add(addEntry(ExtendedItems.COOKED_DUCK, 1, 3))
			.add(addEntry(LOTRItems.GAMMON, 1, 2))
			.add(addEntry(Items.BREAD, 1, 2))
			.add(addEntry(Items.RABBIT_STEW))
			.add(addEntry(Items.MUSHROOM_STEW)));

		// TODO add turnips
		make(consumer, ExtendedLootTables.RIVENDELL_PLATE, makePool()
			.add(addEntry(Items.BREAD))
			.add(addEntry(Items.APPLE, 2, 3))
			.add(addEntry(Items.CARROT, 1, 3))
			.add(addEntry(Items.CHICKEN, 1, 2))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.LETTUCE, 1, 5))
			.add(addEntry(LOTRItems.LEMBAS, 1, 2))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 4))
			.add(addEntry(ExtendedItems.LEEK, 1, 2)));

		make(consumer, ExtendedLootTables.LINDON_PLATE, makePool()
			.add(addEntry(Items.BREAD))
			.add(addEntry(Items.APPLE, 2, 3))
			.add(addEntry(Items.CARROT, 1, 3))
			.add(addEntry(Items.CHICKEN, 1, 2))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.LETTUCE, 1, 5))
			.add(addEntry(LOTRItems.LEMBAS, 1, 2))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 4))
			.add(addEntry(ExtendedItems.LEEK, 1, 2)));

		make(consumer, ExtendedLootTables.ISENGARD_ORTHANC_PLATE, makePool()
			.add(addEntry(Items.BREAD))
			.add(addEntry(Items.APPLE, 2, 3))
			.add(addEntry(Items.CARROT, 1, 3))
			.add(addEntry(Items.CHICKEN, 1, 2))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(LOTRItems.PEAR, 1, 2))
			.add(addEntry(LOTRItems.LETTUCE, 1, 5))
			.add(addEntry(ExtendedItems.PLUM, 1, 2))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 4))
			.add(addEntry(Items.COOKED_CHICKEN, 1, 4))
			.add(addEntry(Items.COOKED_BEEF, 1, 4))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 4))
			.add(addEntry(Items.COOKED_MUTTON, 1, 4))
			.add(addEntry(Items.COOKED_RABBIT, 1, 4))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 4))
			.add(addEntry(ExtendedItems.LEEK, 1, 2)));

		make(consumer, ExtendedLootTables.ISENGARD_PLATE, makePool()
			.when(RandomChance.randomChance(0.5f))
			.add(addEntry(Items.ROTTEN_FLESH))
			.add(addEntry(LOTRItems.MAGGOTY_BREAD))
			.add(addEntry(LOTRItems.MAN_FLESH)));

		make(consumer, ExtendedLootTables.DUNLAND_PLATE, makePool()
			.add(addEntry(Items.BREAD))
			.add(addEntry(Items.APPLE, 2, 3))
			.add(addEntry(LOTRItems.GREEN_APPLE, 1, 2))
			.add(addEntry(Items.CARROT, 1, 3))
			.add(addEntry(Items.POTATO, 1, 3))
			.add(addEntry(LOTRItems.LETTUCE, 1, 5))
			.add(addEntry(ExtendedItems.BLACKBERRY, 1, 3))
			.add(addEntry(ExtendedItems.BLUEBERRY, 1, 3))
			.add(addEntry(ExtendedItems.CRANBERRY, 1, 3))
			.add(addEntry(ExtendedItems.ELDERBERRY, 1, 3))
			.add(addEntry(ExtendedItems.RASPBERRY, 1, 3))
			.add(addEntry(Items.COOKED_PORKCHOP, 1, 3))
			.add(addEntry(Items.COOKED_CHICKEN, 1, 3))
			.add(addEntry(Items.COOKED_MUTTON, 1, 3))
			.add(addEntry(Items.COOKED_RABBIT, 1, 3))
			.add(addEntry(Items.COOKED_COD, 1, 3))
			.add(addEntry(ExtendedItems.ROASTED_CHESTNUT, 1, 3))
			.add(addEntry(ExtendedItems.LEEK, 1, 2))
			.add(addEntry(ExtendedItems.LEEK_SOUP)));
	}

	public EItemLootEntry.ExtendedBuilder addEntry(RegistryObject<Item> item, int min, int max){
		return addEntry(item.get(), min, max);
	}

	public EItemLootEntry.ExtendedBuilder addEntry(Item item, int min, int max){
		return EItemLootEntry.lootItem(item).setRange(min, max);
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation t, LootPool.Builder pool){
		consumer.accept(t, makeTable().withPool(pool));
		madePlates.add(t);
	}
}
