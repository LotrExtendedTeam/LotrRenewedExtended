package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

public class ExtendedArmorStandLootGenerator extends ChestLootTables  {
	protected static final List<ResourceLocation> madeArmorStands = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.ISENGARD_ARMOR_STAND, makeTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_HELMET.get()).setWeight(5))
				.add(addEntry(LOTRItems.URUK_BERSERKER_HELMET.get()).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_HELMET.get()).setWeight(5))
				.add(addEntry(LOTRItems.FUR_HELMET.get()))
				.add(addEntry(ExtendedItems.BROWN_WARG_HELMET.get()))
				.add(addEntry(ExtendedItems.GRAY_WARG_HELMET.get()))
				.add(addEntry(ExtendedItems.BLACK_WARG_HELMET.get()))
				.add(addEntry(LOTRItems.BONE_HELMET.get()).setWeight(5))
				.add(addEntry(Items.LEATHER_HELMET).setWeight(5)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_CHESTPLATE.get()).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_CHESTPLATE.get()).setWeight(5))
				.add(addEntry(LOTRItems.FUR_CHESTPLATE.get()))
				.add(addEntry(ExtendedItems.BROWN_WARG_CHESTPLATE.get()))
				.add(addEntry(ExtendedItems.GRAY_WARG_CHESTPLATE.get()))
				.add(addEntry(ExtendedItems.BLACK_WARG_CHESTPLATE.get()))
				.add(addEntry(LOTRItems.BONE_CHESTPLATE.get()).setWeight(5))
				.add(addEntry(Items.LEATHER_CHESTPLATE).setWeight(5)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_LEGGINGS.get()).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_LEGGINGS.get()).setWeight(5))
				.add(addEntry(LOTRItems.FUR_LEGGINGS.get()))
				.add(addEntry(ExtendedItems.BROWN_WARG_LEGGINGS.get()))
				.add(addEntry(ExtendedItems.GRAY_WARG_LEGGINGS.get()))
				.add(addEntry(ExtendedItems.BLACK_WARG_LEGGINGS.get()))
				.add(addEntry(LOTRItems.BONE_LEGGINGS.get()).setWeight(5))
				.add(addEntry(Items.LEATHER_LEGGINGS).setWeight(5)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_BOOTS.get()).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_BOOTS.get()).setWeight(5))
				.add(addEntry(LOTRItems.FUR_BOOTS.get()))
				.add(addEntry(ExtendedItems.BROWN_WARG_BOOTS.get()))
				.add(addEntry(ExtendedItems.GRAY_WARG_BOOTS.get()))
				.add(addEntry(ExtendedItems.BLACK_WARG_BOOTS.get()))
				.add(addEntry(LOTRItems.BONE_BOOTS.get()).setWeight(5))
				.add(addEntry(Items.LEATHER_BOOTS).setWeight(5)))
			);
		make(consumer, ExtendedLootTables.DUNLAND_ARMOR_STAND, makeTable()
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_HELMET.get()).setWeight(5))
				.add(addEntry(LOTRItems.FUR_HELMET.get()).setWeight(5)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_CHESTPLATE.get()).setWeight(5)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_LEGGINGS.get()).setWeight(5)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_BOOTS.get()).setWeight(5)))
			);
	}
	public EItemLootEntry.ExtendedBuilder addEntry(Item item){
		ItemStack stack = new ItemStack(item);
		if(!(stack.getItem() instanceof ArmorItem)) {
			throw new IllegalArgumentException("Invalid item given for armor stand loot-table: "+item.getRegistryName().toString());
		}
		return EItemLootEntry.lootItem(item);
	}
	public LootTable.Builder makeTable(){
		return LootTable.lootTable();
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeArmorStands.add(loc);
	}
}