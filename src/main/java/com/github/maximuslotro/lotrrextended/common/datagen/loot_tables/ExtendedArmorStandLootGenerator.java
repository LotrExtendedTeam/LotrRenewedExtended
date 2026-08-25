package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

public class ExtendedArmorStandLootGenerator extends ExtendedAbstractLootGenerator  {
	protected static final List<ResourceLocation> madeArmorStands = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.ISENGARD_ARMOR_STAND, makeTable()
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_HELMET).setWeight(5))
				.add(addEntry(LOTRItems.URUK_BERSERKER_HELMET).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_HELMET).setWeight(5))
				.add(addEntry(LOTRItems.FUR_HELMET))
				.add(addEntry(ExtendedItems.BROWN_WARG_HELMET))
				.add(addEntry(ExtendedItems.GRAY_WARG_HELMET))
				.add(addEntry(ExtendedItems.BLACK_WARG_HELMET))
				.add(addEntry(LOTRItems.BONE_HELMET).setWeight(5))
				.add(addEntry(Items.LEATHER_HELMET).setWeight(5)))
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_CHESTPLATE).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_CHESTPLATE).setWeight(5))
				.add(addEntry(LOTRItems.FUR_CHESTPLATE))
				.add(addEntry(ExtendedItems.BROWN_WARG_CHESTPLATE))
				.add(addEntry(ExtendedItems.GRAY_WARG_CHESTPLATE))
				.add(addEntry(ExtendedItems.BLACK_WARG_CHESTPLATE))
				.add(addEntry(LOTRItems.BONE_CHESTPLATE).setWeight(5))
				.add(addEntry(Items.LEATHER_CHESTPLATE).setWeight(5)))
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_LEGGINGS).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_LEGGINGS).setWeight(5))
				.add(addEntry(LOTRItems.FUR_LEGGINGS))
				.add(addEntry(ExtendedItems.BROWN_WARG_LEGGINGS))
				.add(addEntry(ExtendedItems.GRAY_WARG_LEGGINGS))
				.add(addEntry(ExtendedItems.BLACK_WARG_LEGGINGS))
				.add(addEntry(LOTRItems.BONE_LEGGINGS).setWeight(5))
				.add(addEntry(Items.LEATHER_LEGGINGS).setWeight(5)))
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.URUK_BOOTS).setWeight(5))
				.add(addEntry(LOTRItems.BRONZE_BOOTS).setWeight(5))
				.add(addEntry(LOTRItems.FUR_BOOTS))
				.add(addEntry(ExtendedItems.BROWN_WARG_BOOTS))
				.add(addEntry(ExtendedItems.GRAY_WARG_BOOTS))
				.add(addEntry(ExtendedItems.BLACK_WARG_BOOTS))
				.add(addEntry(LOTRItems.BONE_BOOTS).setWeight(5))
				.add(addEntry(Items.LEATHER_BOOTS).setWeight(5)))
			);
		make(consumer, ExtendedLootTables.DUNLAND_ARMOR_STAND, makeTable()
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_HELMET).setWeight(5))
				.add(addEntry(LOTRItems.FUR_HELMET).setWeight(5)))
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_CHESTPLATE).setWeight(5)))
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_LEGGINGS).setWeight(5)))
			.withPool(makePool()
				.when(RandomChance.randomChance(0.5f))
				.add(addEntry(LOTRItems.DUNLENDING_BOOTS).setWeight(5)))
			);
	}

	@Override
	public EItemLootEntry.ExtendedBuilder addEntry(Item item){
		ItemStack stack = new ItemStack(item);
		if(!(stack.getItem() instanceof ArmorItem)) {
			throw new IllegalArgumentException("Invalid item given for armor stand loot-table: "+item.getRegistryName().toString());
		}
		return super.addEntry(item);
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeArmorStands.add(loc);
	}
}