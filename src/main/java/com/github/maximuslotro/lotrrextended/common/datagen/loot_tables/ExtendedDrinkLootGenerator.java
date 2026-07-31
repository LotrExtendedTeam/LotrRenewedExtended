package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.util.ResourceLocation;

public class ExtendedDrinkLootGenerator extends ChestLootTables  {
	protected static final List<ResourceLocation> madeVessels = new ArrayList<>();
	protected static final List<ResourceLocation> madeKegs = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		// TODO add remaining berry juices
		make(consumer, ExtendedLootTables.HOBBIT_KEGS, ExtendedLootTables.HOBBIT_VESSELS, makePool()
				.add(addEntry(LOTRItems.ALE.get(), 2))
				.add(addEntry(LOTRItems.CIDER.get(), 1))
				.add(addEntry(LOTRItems.PERRY.get(), 1))
				.add(addEntry(LOTRItems.CHERRY_LIQUEUR.get(), 1))
				.add(addEntry(LOTRItems.APPLE_JUICE.get(), 1)));

		make(consumer, ExtendedLootTables.RANGER_KEGS, ExtendedLootTables.RANGER_VESSELS, makePool()
				.add(addEntry(LOTRItems.ALE.get(), 2))
				.add(addEntry(LOTRItems.CIDER.get(), 1))
				.add(addEntry(LOTRItems.PERRY.get(), 1))
				.add(addEntry(LOTRItems.APPLE_JUICE.get(), 1)));

		make(consumer, ExtendedLootTables.GUNDABAD_KEGS, ExtendedLootTables.GUNDABAD_VESSELS, makePool()
				.add(addEntry(LOTRItems.ORC_DRAUGHT.get())));

		make(consumer, ExtendedLootTables.BREE_KEGS, ExtendedLootTables.BREE_VESSELS, makePool()
				.add(addEntry(LOTRItems.ALE.get(), 3))
				.add(addEntry(LOTRItems.CIDER.get(), 2))
				.add(addEntry(LOTRItems.PERRY.get(), 1))
				.add(addEntry(LOTRItems.APPLE_JUICE.get(), 1))
				.add(addEntry(LOTRItems.MEAD.get(), 1)));

		make(consumer, ExtendedLootTables.BLUE_MOUNTAINS_KEGS, ExtendedLootTables.BLUE_MOUNTAINS_VESSELS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_ALE.get(), 4))
			.add(addEntry(LOTRItems.DWARVEN_TONIC.get(), 1))
			.add(addEntry(LOTRItems.ALE.get(), 1)));

		// TODO add remaining berry juices
		make(consumer, ExtendedLootTables.RIVENDELL_VESSELS, ExtendedLootTables.RIVENDELL_KEGS, makePool()
			.add(addEntry(LOTRItems.MIRUVOR.get(), 2))
			.add(addEntry(LOTRItems.APPLE_JUICE.get(), 1))
			.add(addEntry(LOTRItems.SWEET_BERRY_JUICE.get(), 1)));

		// TODO add remaining berry juices
		make(consumer, ExtendedLootTables.LINDON_VESSELS, ExtendedLootTables.LINDON_KEGS, makePool()
			.add(addEntry(LOTRItems.MIRUVOR.get(), 2))
			.add(addEntry(LOTRItems.APPLE_JUICE.get(), 1))
			.add(addEntry(LOTRItems.SWEET_BERRY_JUICE.get(), 1)));

		make(consumer, ExtendedLootTables.ISENGARD_KEGS, ExtendedLootTables.ISENGARD_VESSELS, makePool()
			.add(addEntry(LOTRItems.ORC_DRAUGHT.get())));

		make(consumer, ExtendedLootTables.ISENGARD_ORTHANC_KEGS, ExtendedLootTables.ISENGARD_ORTHANC_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE.get(), 3))
			.add(addEntry(LOTRItems.CIDER.get(), 2))
			.add(addEntry(LOTRItems.PERRY.get(), 1))
			.add(addEntry(LOTRItems.CHERRY_LIQUEUR.get(), 3)));

		make(consumer, ExtendedLootTables.DUNLAND_KEGS, ExtendedLootTables.DUNLAND_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE.get(), 2))
			.add(addEntry(LOTRItems.CIDER.get(), 1))
			.add(addEntry(LOTRItems.MEAD.get(), 1))
			.add(addEntry(LOTRItems.RUM.get(), 1)));

		make(consumer, ExtendedLootTables.IRON_HILLS_KEGS, ExtendedLootTables.IRON_HILLS_VESSELS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_ALE.get(), 4))
			.add(addEntry(LOTRItems.DWARVEN_TONIC.get(), 1))
			.add(addEntry(LOTRItems.ALE.get(), 1)));
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item, int weight){
		return EItemLootEntry.lootItem(item).setWeight(weight).setExactly(1);
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item){
		return EItemLootEntry.lootItem(item).setExactly(1);
	}

	public LootPool.Builder makePool(){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(1));
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation keg, ResourceLocation vessel, LootPool.Builder pool){
		consumer.accept(keg, LootTable.lootTable().withPool(pool));
		consumer.accept(vessel, LootTable.lootTable().withPool(pool));
		madeVessels.add(vessel);
		madeKegs.add(keg);
	}
}
