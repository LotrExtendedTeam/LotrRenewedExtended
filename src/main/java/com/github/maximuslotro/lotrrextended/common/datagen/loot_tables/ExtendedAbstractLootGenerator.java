package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;

import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public abstract class ExtendedAbstractLootGenerator implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

	public EItemLootEntry.ExtendedBuilder addEntry(RegistryObject<Item> item){
		return addEntry(item.get());
	}

	public EItemLootEntry.ExtendedBuilder addEntry(Item item){
		return EItemLootEntry.lootItem(item);
	}

	public LootTable.Builder makeTable(){
		return LootTable.lootTable();
	}

	public LootPool.Builder makePool(){
		return LootPool.lootPool();
	}
}
