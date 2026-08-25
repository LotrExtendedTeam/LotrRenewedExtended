package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedDrinkLootGenerator extends ExtendedAbstractLootGenerator  {
	protected static final List<ResourceLocation> madeVessels = new ArrayList<>();
	protected static final List<ResourceLocation> madeKegs = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		// TODO add remaining berry juices
		make(consumer, ExtendedLootTables.HOBBIT_KEGS, ExtendedLootTables.HOBBIT_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE, 2))
			.add(addEntry(LOTRItems.CIDER, 1))
			.add(addEntry(LOTRItems.PERRY, 1))
			.add(addEntry(LOTRItems.CHERRY_LIQUEUR, 1))
			.add(addEntry(LOTRItems.APPLE_JUICE, 1)));

		make(consumer, ExtendedLootTables.RANGER_KEGS, ExtendedLootTables.RANGER_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE, 2))
			.add(addEntry(LOTRItems.CIDER, 1))
			.add(addEntry(LOTRItems.PERRY, 1))
			.add(addEntry(LOTRItems.APPLE_JUICE, 1)));

		make(consumer, ExtendedLootTables.GUNDABAD_KEGS, ExtendedLootTables.GUNDABAD_VESSELS, makePool()
			.add(addEntry(LOTRItems.ORC_DRAUGHT)));

		make(consumer, ExtendedLootTables.BREE_KEGS, ExtendedLootTables.BREE_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE, 3))
			.add(addEntry(LOTRItems.CIDER, 2))
			.add(addEntry(LOTRItems.PERRY, 1))
			.add(addEntry(LOTRItems.APPLE_JUICE, 1))
			.add(addEntry(LOTRItems.MEAD, 1)));

		make(consumer, ExtendedLootTables.BLUE_MOUNTAINS_KEGS, ExtendedLootTables.BLUE_MOUNTAINS_VESSELS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_ALE, 4))
			.add(addEntry(LOTRItems.DWARVEN_TONIC, 1))
			.add(addEntry(LOTRItems.ALE, 1)));

		// TODO add remaining berry juices
		make(consumer, ExtendedLootTables.RIVENDELL_VESSELS, ExtendedLootTables.RIVENDELL_KEGS, makePool()
			.add(addEntry(LOTRItems.MIRUVOR, 2))
			.add(addEntry(LOTRItems.APPLE_JUICE, 1))
			.add(addEntry(LOTRItems.SWEET_BERRY_JUICE, 1)));

		// TODO add remaining berry juices
		make(consumer, ExtendedLootTables.LINDON_VESSELS, ExtendedLootTables.LINDON_KEGS, makePool()
			.add(addEntry(LOTRItems.MIRUVOR, 2))
			.add(addEntry(LOTRItems.APPLE_JUICE, 1))
			.add(addEntry(LOTRItems.SWEET_BERRY_JUICE, 1)));

		make(consumer, ExtendedLootTables.ISENGARD_KEGS, ExtendedLootTables.ISENGARD_VESSELS, makePool()
			.add(addEntry(LOTRItems.ORC_DRAUGHT)));

		make(consumer, ExtendedLootTables.ISENGARD_ORTHANC_KEGS, ExtendedLootTables.ISENGARD_ORTHANC_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE, 3))
			.add(addEntry(LOTRItems.CIDER, 2))
			.add(addEntry(LOTRItems.PERRY, 1))
			.add(addEntry(LOTRItems.CHERRY_LIQUEUR, 3)));

		make(consumer, ExtendedLootTables.DUNLAND_KEGS, ExtendedLootTables.DUNLAND_VESSELS, makePool()
			.add(addEntry(LOTRItems.ALE, 2))
			.add(addEntry(LOTRItems.CIDER, 1))
			.add(addEntry(LOTRItems.MEAD, 1))
			.add(addEntry(LOTRItems.RUM, 1)));

		make(consumer, ExtendedLootTables.IRON_HILLS_KEGS, ExtendedLootTables.IRON_HILLS_VESSELS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_ALE, 4))
			.add(addEntry(LOTRItems.DWARVEN_TONIC, 1))
			.add(addEntry(LOTRItems.ALE, 1)));
	}

	public EItemLootEntry.ExtendedBuilder addEntry(RegistryObject<Item> item, int weight){
		return addEntry(item.get(), weight);
	}

	public EItemLootEntry.ExtendedBuilder addEntry(Item item, int weight){
		return EItemLootEntry.lootItem(item).setWeight(weight).setExactly(1);
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation keg, ResourceLocation vessel, LootPool.Builder pool){
		consumer.accept(keg, makeTable().withPool(pool));
		consumer.accept(vessel, makeTable().withPool(pool));
		madeVessels.add(vessel);
		madeKegs.add(keg);
	}
}
