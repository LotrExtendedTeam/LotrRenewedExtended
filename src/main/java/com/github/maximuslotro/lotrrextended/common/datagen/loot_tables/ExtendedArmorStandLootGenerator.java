package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
				.when(RandomChance.randomChance(0.25f))
				.add(addEntry(LOTRItems.URUK_HELMET.get()).setWeight(10))
				.add(addEntry(LOTRItems.URUK_BERSERKER_HELMET.get()).setWeight(1)))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f))
				.add(addEntry(LOTRItems.URUK_CHESTPLATE.get())))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f))
				.add(addEntry(LOTRItems.URUK_LEGGINGS.get())))
			.withPool(LootPool.lootPool()
				.setRolls(ConstantRange.exactly(1))
				.when(RandomChance.randomChance(0.25f))
				.add(addEntry(LOTRItems.URUK_BOOTS.get())))
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