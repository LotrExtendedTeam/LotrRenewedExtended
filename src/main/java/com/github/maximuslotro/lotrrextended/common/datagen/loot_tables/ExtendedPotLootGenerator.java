package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierScrollAdder;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

public class ExtendedPotLootGenerator extends ChestLootTables  {
	protected static final List<ResourceLocation> madePots = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.BLUE_MOUNTAINS_TRADER_HALL_POT, makeTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f)) // 25% chance
				.add(EItemLootEntry.lootItem(LOTRItems.FUR.get()).setWeight(70).setRange(1, 4))
				.add(EItemLootEntry.lootItem(Items.STRING).setWeight(50).setRange(1, 4))
				.add(EItemLootEntry.lootItem(Items.FEATHER).setWeight(50).setRange(1, 4))
				.add(EItemLootEntry.lootItem(Items.COOKED_BEEF).setWeight(60).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COOKED_MUTTON).setWeight(60).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COOKED_PORKCHOP).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.BEEF).setWeight(60).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.CHICKEN).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.MUTTON).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.PORKCHOP).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.RABBIT).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(ExtendedItems.RAW_DUCK.get()).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_DAGGER.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.IRON_DAGGER.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_DAGGER.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(Items.BOW).setWeight(10).setExactly(1).setDamageRange(0.1f, 0.65f))
				.add(EItemLootEntry.lootItem(Items.ARROW).setWeight(10).setRange(2, 8))
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_ROCK.get()).setWeight(80).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COBBLESTONE).setWeight(60).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.STONE).setWeight(70).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.STONE_BRICKS).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.IRON_ORE).setWeight(20).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COAL).setWeight(90).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.CHARCOAL).setWeight(80).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.GOLD_ORE).setWeight(1).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.FLINT).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.FLINT_AND_STEEL).setWeight(25).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.STICK).setWeight(70).setRange(1, 8))
				.add(EItemLootEntry.lootItem(Items.BREAD).setWeight(60).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.GAMMON.get()).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.TIN_ORE.get()).setWeight(30).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.COPPER_ORE.get()).setWeight(30).setRange(1, 6))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_ORE.get()).setWeight(1).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_PICKAXE.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_PICKAXE.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(ExtendedItems.LEATHER_HAT.get()).setWeight(50).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.WATERSKIN.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.WRITABLE_BOOK).setWeight(40).setRange(1, 2))
				.add(EItemLootEntry.lootItem(Items.BOOK).setWeight(30).setRange(1, 4))
				.add(EItemLootEntry.lootItem(Items.INK_SAC).setWeight(30).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.BOOKSHELF).setWeight(10).setRange(1, 2))
				.add(EItemLootEntry.lootItem(Items.LEATHER).setWeight(20).setRange(1, 2))
				.add(EItemLootEntry.lootItem(Items.PAPER).setWeight(25).setRange(1, 6))
				.add(EItemLootEntry.lootItem(Items.IRON_NUGGET).setWeight(25).setRange(1, 5))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_NUGGET.get()).setWeight(25).setRange(1, 5))
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_SWORD.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_SPEAR.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_BOOTS.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_HELMET.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_CHESTPLATE.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_LEGGINGS.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.BLUE_DWARVEN_BOOTS.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(ExtendedItems.BLUE_DWARVEN_SHIELD.get()).setWeight(25).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(ExtendedItems.BLUE_MOUNTAINS_BANNER.get()).setWeight(20).setExactly(1))
				.add(EItemLootEntry.lootItem(Items.COMPASS).setWeight(20).setExactly(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.DWARF_MARRIAGE_RING.get()).setWeight(15).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.WOODEN_MUG.get()).setWeight(40).setRange(1, 2))
				.add(EItemLootEntry.lootItem(LOTRItems.CERAMIC_MUG.get()).setWeight(40).setRange(1, 2))
				.add(EItemLootEntry.lootItem(LOTRItems.ALE_HORN.get()).setWeight(40).setRange(1, 2))
				.add(EItemLootEntry.lootItem(LOTRItems.WOODEN_CUP.get()).setWeight(40).setRange(1, 2))
				.add(EItemLootEntry.lootItem(LOTRItems.COPPER_GOBLET.get()).setWeight(40).setRange(1, 2))
				.add(EItemLootEntry.lootItem(Items.COOKED_CHICKEN).setWeight(70).setRange(1, 3))
				.add(EItemLootEntry.lootItem(ExtendedItems.COOKED_DUCK.get()).setWeight(70).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COOKED_RABBIT).setWeight(75).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COOKED_COD).setWeight(60).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COOKED_SALMON).setWeight(50).setRange(1, 3))
				.apply(ExtendedModifierScrollAdder.chanceVeryRare()))
			);

		make(consumer, ExtendedLootTables.RIVENDELL_POT, makeTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f)) // 25% chance
				.add(EItemLootEntry.lootItem(Items.BOOK).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.ARROW).setWeight(50).setRange(2, 6))
				.add(EItemLootEntry.lootItem(ExtendedItems.RIVENDELL_BOW.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.RIVENDELL_SWORD.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.RIVENDELL_SPEAR.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.RIVENDELL_DAGGER.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(Items.FEATHER).setWeight(20).setRange(2, 6))
				.add(EItemLootEntry.lootItem(Items.STICK).setWeight(25).setRange(3, 8))
				.add(EItemLootEntry.lootItem(Items.APPLE).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.BREAD).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.CARROT).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.GREEN_APPLE.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.PEAR.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.LETTUCE.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.LEMBAS.get()).setWeight(10).setExactly(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.PLUM.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(ExtendedItems.ROASTED_CHESTNUT.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COAL).setWeight(80).setRange(2, 8))
				.add(EItemLootEntry.lootItem(Items.CHARCOAL).setWeight(40).setRange(2, 8))
				.add(EItemLootEntry.lootItem(Items.GOLD_NUGGET).setWeight(1).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_RING.get()).setWeight(1).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.GOLD_RING.get()).setWeight(1).setExactly(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_ONE.get()).setWeight(10).setRange(1, 10))
				.apply(ExtendedModifierScrollAdder.chanceVeryRare()))
			);

		make(consumer, ExtendedLootTables.LINDON_POT, makeTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f)) // 25% chance
				.add(EItemLootEntry.lootItem(Items.BOOK).setWeight(50).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.ARROW).setWeight(50).setRange(2, 6))
				.add(EItemLootEntry.lootItem(ExtendedItems.RIVENDELL_BOW.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.LINDON_SWORD.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.LINDON_SPEAR.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(LOTRItems.LINDON_DAGGER.get()).setWeight(10).setExactly(1).standardDamageRange())
				.add(EItemLootEntry.lootItem(Items.FEATHER).setWeight(20).setRange(2, 6))
				.add(EItemLootEntry.lootItem(Items.STICK).setWeight(25).setRange(3, 8))
				.add(EItemLootEntry.lootItem(Items.APPLE).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.BREAD).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.CARROT).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.GREEN_APPLE.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.PEAR.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.LETTUCE.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.LEMBAS.get()).setWeight(10).setExactly(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.PLUM.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(ExtendedItems.ROASTED_CHESTNUT.get()).setWeight(25).setRange(1, 3))
				.add(EItemLootEntry.lootItem(Items.COAL).setWeight(80).setRange(2, 8))
				.add(EItemLootEntry.lootItem(Items.CHARCOAL).setWeight(40).setRange(2, 8))
				.add(EItemLootEntry.lootItem(Items.GOLD_NUGGET).setWeight(1).setRange(1, 3))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_RING.get()).setWeight(1).setExactly(1))
				.add(EItemLootEntry.lootItem(LOTRItems.GOLD_RING.get()).setWeight(1).setExactly(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_ONE.get()).setWeight(10).setRange(1, 10))
				.apply(ExtendedModifierScrollAdder.chanceVeryRare()))
			);
	}

	public LootTable.Builder makeTable(){
		return LootTable.lootTable();
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madePots.add(loc);
	}
}