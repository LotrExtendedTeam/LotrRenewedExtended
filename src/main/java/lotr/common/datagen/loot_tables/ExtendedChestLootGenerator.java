package lotr.common.datagen.loot_tables;

import java.util.function.BiConsumer;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.loot.ExtendedLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class ExtendedChestLootGenerator extends ChestLootTables  {

	@Override
    public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		//ADD cooked venison, plum, hobbit pancake, corn, cooked corn, leek, roasted chestnut, leek soup, turnip, cooked turnip, marzipan, chocolate marzipan, 
		//ale, cider, perry, cherry liqueur, apple juice, 
		consumer.accept(ExtendedLootTables.HOBBIT_LARDER, LootTable.lootTable()
				.withPool(LootPool.lootPool()
                		.setRolls(RandomValueRange.between(3, 6))
                		.add(ItemLootEntry.lootTableItem(Items.BREAD).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.POTATO).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.BOWL).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(2, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.APPLE).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.SUGAR).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(2, 5))))
                		.add(ItemLootEntry.lootTableItem(Items.PUMPKIN_PIE).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(Items.MUSHROOM_STEW).setWeight(100).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(Items.BROWN_MUSHROOM).setWeight(25).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.RED_MUSHROOM).setWeight(25).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_BEEF).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_COD).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_SALMON).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_CHICKEN).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_RABBIT).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_MUTTON).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKED_PORKCHOP).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(Items.EGG).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.PIPEWEED.get()).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(3, 8))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.LETTUCE.get()).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.GREEN_APPLE.get()).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.GAMMON.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(Items.MILK_BUCKET).setWeight(100).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(Items.CARROT).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(2, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.COOKIE).setWeight(75).apply(SetCount.setCount(RandomValueRange.between(2, 5))))
                		.add(ItemLootEntry.lootTableItem(Items.COCOA_BEANS).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(2, 6))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.PEAR.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.CHERRY.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(2, 4))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.WOODEN_PLATE.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.FINE_PLATE.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.WOODEN_MUG.get()).setWeight(25).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.CERAMIC_MUG.get()).setWeight(25).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.WOODEN_CUP.get()).setWeight(25).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.BLACKBERRY.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2, 5))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.BLUEBERRY.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2, 5))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.CRANBERRY.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2, 5))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.ELDERBERRY.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2, 5))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.RASPBERRY.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2, 5))))));

		//ADD chestnut, pouches
		consumer.accept(ExtendedLootTables.HOBBIT_TREASURE, LootTable.lootTable()
				.withPool(LootPool.lootPool()
                		.setRolls(RandomValueRange.between(4, 6))
                		.add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.GOLD_NUGGET).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 8))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.SILVER_INGOT.get()).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.SILVER_NUGGET.get()).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 8))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.SILVER_COIN_ONE.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 25))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.SILVER_COIN_TEN.get()).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 10))))
                		.add(ItemLootEntry.lootTableItem(ExtendedItems.SILVER_COIN_HUNDRED.get()).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.GOLD_RING.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.SILVER_RING.get()).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.MITHRIL_NUGGET.get()).setWeight(25).apply(SetCount.setCount(RandomValueRange.between(1, 3))))));

		consumer.accept(ExtendedLootTables.HOBBIT_LIVING, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                		.setRolls(RandomValueRange.between(3, 6))
                		.add(ItemLootEntry.lootTableItem(Items.PAPER).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(2, 8))))
                		.add(ItemLootEntry.lootTableItem(Items.FEATHER).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(Items.INK_SAC).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 3))))
                		.add(ItemLootEntry.lootTableItem(Items.GLASS_BOTTLE).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(2, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.BOOK).setWeight(100).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(Items.STRING).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 5))))
                		.add(ItemLootEntry.lootTableItem(Items.COMPASS).setWeight(50).apply(SetCount.setCount(ConstantRange.exactly(1))))
                		.add(ItemLootEntry.lootTableItem(Items.LEAD).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 2))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.POCKET_MATCH.get()).setWeight(50).apply(SetCount.setCount(RandomValueRange.between(1, 4))))
                		.add(ItemLootEntry.lootTableItem(LOTRItems.SMOKING_PIPE.get()).setWeight(10).apply(SetCount.setCount(ConstantRange.exactly(1))))));
	}
}
