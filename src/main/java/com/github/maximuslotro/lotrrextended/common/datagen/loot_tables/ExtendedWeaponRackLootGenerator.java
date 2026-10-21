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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedWeaponRackLootGenerator extends ExtendedAbstractLootGenerator  {
	protected static final List<ResourceLocation> madeRacks = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.HOBBIT_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.IRON_DAGGER, 1)));

		make(consumer, ExtendedLootTables.RANGER_WEAPONRACKS, makePool()
			.add(addEntry(Items.WOODEN_AXE, 60))
			.add(addEntry(Items.WOODEN_PICKAXE, 60))
			.add(addEntry(Items.WOODEN_SWORD, 80))
			.add(addEntry(LOTRItems.WOODEN_DAGGER, 80))
			//.add(addEntry(LOTRItems.WOODEN_SPEAR, 80))
			.add(addEntry(Items.STONE_AXE, 60))
			.add(addEntry(Items.STONE_PICKAXE, 60))
			.add(addEntry(Items.STONE_SWORD, 80))
			.add(addEntry(LOTRItems.STONE_DAGGER, 80))
			.add(addEntry(LOTRItems.STONE_SPEAR, 80))
			.add(addEntry(LOTRItems.BRONZE_AXE, 70))
			.add(addEntry(LOTRItems.BRONZE_PICKAXE, 70))
			.add(addEntry(LOTRItems.BRONZE_SWORD, 100))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 100))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 100))
			.add(addEntry(Items.IRON_AXE, 60))
			.add(addEntry(Items.IRON_PICKAXE, 60))
			.add(addEntry(Items.IRON_SWORD, 90))
			.add(addEntry(LOTRItems.IRON_DAGGER, 90))
			.add(addEntry(LOTRItems.IRON_SPEAR, 90))
			.add(addEntry(LOTRItems.ARNOR_SWORD, 80))
			.add(addEntry(LOTRItems.ARNOR_DAGGER, 80))
			.add(addEntry(LOTRItems.ARNOR_SPEAR, 80))
			.add(addEntry(Items.BOW, 85))
			.add(addEntry(ExtendedItems.LONGBOW, 65))
			.add(addEntry(ExtendedItems.DUNEDAIN_BOW, 45))
			.add(addEntry(ExtendedItems.RANGER_BOW, 65)));

		make(consumer, ExtendedLootTables.GUNDABAD_WEAPONRACKS, makePool()
			.add(addEntry(Items.WOODEN_AXE, 60))
			.add(addEntry(Items.WOODEN_PICKAXE, 60))
			.add(addEntry(Items.WOODEN_SWORD, 80))
			.add(addEntry(LOTRItems.WOODEN_DAGGER, 80))
			//.add(addEntry(LOTRItems.WOODEN_SPEAR, 80))
			.add(addEntry(Items.STONE_AXE, 60))
			.add(addEntry(Items.STONE_PICKAXE, 60))
			.add(addEntry(Items.STONE_SWORD, 80))
			.add(addEntry(LOTRItems.STONE_DAGGER, 80))
			.add(addEntry(LOTRItems.STONE_SPEAR, 80))
			.add(addEntry(LOTRItems.BRONZE_AXE, 70))
			.add(addEntry(LOTRItems.BRONZE_PICKAXE, 70))
			.add(addEntry(LOTRItems.BRONZE_SWORD, 100))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 100))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 100))
			.add(addEntry(Items.IRON_AXE, 60))
			.add(addEntry(Items.IRON_PICKAXE, 60))
			.add(addEntry(Items.IRON_SWORD, 90))
			.add(addEntry(LOTRItems.IRON_DAGGER, 90))
			.add(addEntry(LOTRItems.IRON_SPEAR, 90))
			.add(addEntry(ExtendedItems.BONE_AXE, 70))
			.add(addEntry(ExtendedItems.BONE_PICKAXE, 70))
			.add(addEntry(ExtendedItems.BONE_SWORD, 100))
			.add(addEntry(ExtendedItems.BONE_DAGGER, 100))
			.add(addEntry(ExtendedItems.BONE_SPEAR, 100))
			.add(addEntry(ExtendedItems.ANGMAR_AXE, 70))
			.add(addEntry(ExtendedItems.ANGMAR_PICKAXE, 70))
			.add(addEntry(ExtendedItems.ANGMAR_SWORD, 100))
			.add(addEntry(ExtendedItems.ANGMAR_DAGGER, 100))
			.add(addEntry(ExtendedItems.ANGMAR_SPEAR, 100))
			.add(addEntry(LOTRItems.MORDOR_AXE, 60))
			.add(addEntry(LOTRItems.MORDOR_PICKAXE, 60))
			.add(addEntry(LOTRItems.MORDOR_SCIMITAR, 80))
			.add(addEntry(LOTRItems.MORDOR_DAGGER, 80))
			.add(addEntry(LOTRItems.MORDOR_SPEAR, 80))
			.add(addEntry(ExtendedItems.GUNDABAD_URUK_CLEAVER, 30))
			.add(addEntry(ExtendedItems.GUNDABAD_URUK_DAGGER, 40))
			.add(addEntry(ExtendedItems.GUNDABAD_URUK_SPEAR, 40))
			.add(addEntry(Items.BOW, 85))
			.add(addEntry(ExtendedItems.ORC_BOW, 65))
			.add(addEntry(ExtendedItems.GUNDABAD_URUK_BOW, 35)));

		make(consumer, ExtendedLootTables.BARROW_DOWN_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BRONZE_AXE, 70, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.BRONZE_PICKAXE, 70, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.BRONZE_SWORD, 100, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 100, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 100, 0.25f, 0.8f))
			.add(addEntry(Items.IRON_AXE, 60, 0.25f, 0.8f))
			.add(addEntry(Items.IRON_PICKAXE, 60, 0.25f, 0.8f))
			.add(addEntry(Items.IRON_SWORD, 90, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.IRON_DAGGER, 90, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.IRON_SPEAR, 90, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.ARNOR_SWORD, 80, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.ARNOR_DAGGER, 80, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.ARNOR_SPEAR, 80, 0.25f, 0.8f))
			.add(addEntry(LOTRItems.MITHRIL_SWORD, 1, 0.95F, 0.98F))
			.add(addEntry(LOTRItems.MITHRIL_DAGGER, 1, 0.95F, 0.98F))
			.add(addEntry(LOTRItems.MITHRIL_SPEAR, 1, 0.95F, 0.98F))
			.add(addEntry(LOTRItems.MITHRIL_AXE, 1, 0.95F, 0.98F))
			.add(addEntry(ExtendedItems.MITHRIL_MATTOCK, 1, 0.95F, 0.98F))
			.add(addEntry(ExtendedItems.BARROW_BLADE, 75, 0.2f, 0.6f)));

		make(consumer, ExtendedLootTables.BREE_MAN_WEAPONRACKS, makePool()
			.add(addEntry(Items.WOODEN_AXE, 60))
			.add(addEntry(Items.WOODEN_PICKAXE, 60))
			.add(addEntry(Items.WOODEN_SWORD, 80))
			.add(addEntry(LOTRItems.WOODEN_DAGGER, 80))
			//.add(addEntry(LOTRItems.WOODEN_SPEAR, 80))
			.add(addEntry(Items.STONE_AXE, 60))
			.add(addEntry(Items.STONE_PICKAXE, 60))
			.add(addEntry(Items.STONE_SWORD, 80))
			.add(addEntry(LOTRItems.STONE_DAGGER, 80))
			.add(addEntry(LOTRItems.STONE_SPEAR, 80))
			.add(addEntry(LOTRItems.BRONZE_AXE, 70))
			.add(addEntry(LOTRItems.BRONZE_PICKAXE, 70))
			.add(addEntry(LOTRItems.BRONZE_SWORD, 100))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 100))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 100))
			.add(addEntry(Items.IRON_AXE, 60))
			.add(addEntry(Items.IRON_PICKAXE, 60))
			.add(addEntry(Items.IRON_SWORD, 90))
			.add(addEntry(LOTRItems.IRON_DAGGER, 90))
			.add(addEntry(LOTRItems.IRON_SPEAR, 90)));

		make(consumer, ExtendedLootTables.BREE_HOBBIT_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.IRON_DAGGER, 1)));

		make(consumer, ExtendedLootTables.BREE_BUTCHER_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.WOODEN_DAGGER, 80))
			.add(addEntry(LOTRItems.STONE_DAGGER, 80))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 100))
			.add(addEntry(LOTRItems.IRON_DAGGER, 90))
			.add(addEntry(LOTRItems.IRON_SPEAR, 90)));

		make(consumer, ExtendedLootTables.BREE_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BRONZE_AXE, 70))
			.add(addEntry(LOTRItems.BRONZE_PICKAXE, 70))
			.add(addEntry(LOTRItems.BRONZE_HOE, 70))
			.add(addEntry(LOTRItems.BRONZE_SHOVEL, 70))
			.add(addEntry(LOTRItems.BRONZE_SWORD, 100))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 100))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 100))
			.add(addEntry(Items.IRON_AXE, 60))
			.add(addEntry(Items.IRON_PICKAXE, 60))
			.add(addEntry(Items.IRON_HOE, 60))
			.add(addEntry(Items.IRON_SHOVEL, 60))
			.add(addEntry(Items.IRON_SWORD, 90))
			.add(addEntry(LOTRItems.IRON_DAGGER, 90))
			.add(addEntry(LOTRItems.IRON_SPEAR, 90)));

		make(consumer, ExtendedLootTables.BREE_FARMER_WEAPONRACKS, makePool()
			.add(addEntry(Items.WOODEN_AXE, 60))
			.add(addEntry(Items.WOODEN_SHOVEL, 70))
			.add(addEntry(Items.WOODEN_HOE, 90))
			.add(addEntry(LOTRItems.WOODEN_DAGGER, 40))
			.add(addEntry(Items.STONE_AXE, 60))
			.add(addEntry(Items.STONE_SHOVEL, 70))
			.add(addEntry(Items.STONE_HOE, 90))
			.add(addEntry(LOTRItems.STONE_DAGGER, 40))
			.add(addEntry(LOTRItems.BRONZE_AXE, 50))
			.add(addEntry(LOTRItems.BRONZE_SHOVEL, 60))
			.add(addEntry(LOTRItems.BRONZE_HOE, 80))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 30))
			.add(addEntry(Items.IRON_HOE, 70))
			.add(addEntry(Items.IRON_SHOVEL, 50)));

		// Add blue dwarven hammer, battleaxe, pike
		make(consumer, ExtendedLootTables.BLUE_MOUNTAIN_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BLUE_DWARVEN_DAGGER, 2))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_AXE, 1))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_PICKAXE, 2))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SHOVEL, 1))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_HOE, 1))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SWORD, 2))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SPEAR, 1))
			.add(addEntry(ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE, 2))
			.add(addEntry(ExtendedItems.BLUE_DWARVEN_MATTOCK, 2)));

		make(consumer, ExtendedLootTables.RIVENDELL_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.RIVENDELL_AXE, 1))
			.add(addEntry(LOTRItems.RIVENDELL_HOE, 1))
			.add(addEntry(LOTRItems.RIVENDELL_PICKAXE, 1))
			.add(addEntry(LOTRItems.RIVENDELL_SHOVEL, 1))
			.add(addEntry(LOTRItems.RIVENDELL_DAGGER, 1)));

		make(consumer, ExtendedLootTables.RIVENDELL_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.RIVENDELL_AXE, 1))
			.add(addEntry(LOTRItems.RIVENDELL_HOE, 1))
			.add(addEntry(LOTRItems.RIVENDELL_PICKAXE, 1))
			.add(addEntry(LOTRItems.RIVENDELL_SHOVEL, 1))
			.add(addEntry(LOTRItems.RIVENDELL_DAGGER, 1))
			.add(addEntry(LOTRItems.RIVENDELL_SPEAR, 2))
			.add(addEntry(LOTRItems.RIVENDELL_SWORD, 2)));

		make(consumer, ExtendedLootTables.LINDON_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.LINDON_AXE, 1))
			.add(addEntry(LOTRItems.LINDON_HOE, 1))
			.add(addEntry(LOTRItems.LINDON_PICKAXE, 1))
			.add(addEntry(LOTRItems.LINDON_SHOVEL, 1))
			.add(addEntry(LOTRItems.LINDON_SPEAR, 1))
			.add(addEntry(LOTRItems.LINDON_SWORD, 1))
			.add(addEntry(LOTRItems.LINDON_DAGGER, 1)));

		make(consumer, ExtendedLootTables.LINDON_EQUIPMENT_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.LINDON_SPEAR, 1))
			.add(addEntry(LOTRItems.LINDON_SWORD, 2))
			.add(addEntry(ExtendedItems.LINDON_BOW, 2))
			.add(addEntry(LOTRItems.LINDON_DAGGER, 1)));


		make(consumer, ExtendedLootTables.LINDON_BOW_WEAPONRACKS, makePool()
			.add(addEntry(ExtendedItems.LINDON_BOW, 2)));

		make(consumer, ExtendedLootTables.ISENGARD_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.URUK_SPEAR, 50))
			.add(addEntry(LOTRItems.URUK_CLEAVER, 100))
			.add(addEntry(LOTRItems.URUK_BERSERKER_CLEAVER, 25))
			.add(addEntry(LOTRItems.URUK_DAGGER, 75))
			.add(addEntry(LOTRItems.URUK_AXE, 50))
			.add(addEntry(LOTRItems.URUK_PICKAXE, 50))
			.add(addEntry(LOTRItems.URUK_SHOVEL, 50))
			.add(addEntry(ExtendedItems.URUK_CROSSBOW, 70))
			.add(addEntry(ExtendedItems.ORC_BOW, 60)));

		make(consumer, ExtendedLootTables.DUNLAND_WEAPONRACKS, makePool()
			.add(addEntry(Items.STONE_SWORD, 25))
			.add(addEntry(Items.STONE_AXE, 25))
			.add(addEntry(LOTRItems.STONE_DAGGER, 25))
			.add(addEntry(LOTRItems.STONE_SPEAR, 25))
			.add(addEntry(LOTRItems.BRONZE_AXE, 25))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 25))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 25))
			.add(addEntry(Items.IRON_AXE, 25))
			.add(addEntry(LOTRItems.IRON_DAGGER, 25))
			.add(addEntry(LOTRItems.IRON_SPEAR, 25))
			.add(addEntry(LOTRItems.DUNLENDING_CLUB, 100))
			.add(addEntry(Items.BOW, 50)));

		make(consumer, ExtendedLootTables.DUNLAND_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BRONZE_SWORD, 50))
			.add(addEntry(LOTRItems.BRONZE_AXE, 40))
			.add(addEntry(LOTRItems.BRONZE_DAGGER, 60))
			.add(addEntry(LOTRItems.BRONZE_SPEAR, 60))
			.add(addEntry(Items.IRON_SWORD, 50))
			.add(addEntry(Items.IRON_AXE, 40))
			.add(addEntry(LOTRItems.IRON_DAGGER, 60))
			.add(addEntry(LOTRItems.IRON_SPEAR, 60))
			.add(addEntry(Items.BOW, 50)));

		make(consumer, ExtendedLootTables.DUNLAND_DWARVEN_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_SWORD, 10))
			.add(addEntry(LOTRItems.DWARVEN_AXE, 4))
			.add(addEntry(LOTRItems.DWARVEN_DAGGER, 6))
			.add(addEntry(LOTRItems.DWARVEN_SPEAR, 8))
			.add(addEntry(ExtendedItems.DWARVEN_THROWABLE_AXE, 2)));

		// Add dwarven hammer, battleaxe, pike
		make(consumer, ExtendedLootTables.IRON_HILLS_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_DAGGER, 2))
			.add(addEntry(LOTRItems.DWARVEN_AXE, 1))
			.add(addEntry(LOTRItems.DWARVEN_PICKAXE, 2))
			.add(addEntry(LOTRItems.DWARVEN_SHOVEL, 1))
			.add(addEntry(LOTRItems.DWARVEN_HOE, 1))
			.add(addEntry(LOTRItems.DWARVEN_SWORD, 2))
			.add(addEntry(LOTRItems.DWARVEN_SPEAR, 1))
			.add(addEntry(ExtendedItems.DWARVEN_THROWABLE_AXE, 2))
			.add(addEntry(ExtendedItems.DWARVEN_MATTOCK, 2)));
	}

	public EItemLootEntry.ExtendedBuilder addEntry(RegistryObject<Item> item, int weight){
		return addEntry(item.get(), weight);
	}

	public EItemLootEntry.ExtendedBuilder addEntry(Item item, int weight){
		return addEntry(item, weight, 0.3f, 0.75f);
	}

	public EItemLootEntry.ExtendedBuilder addEntry(RegistryObject<Item> item, int weight, float minDamagePercent, float maxDamagePercent){
		return addEntry(item.get(), weight, minDamagePercent, maxDamagePercent);
	}

	public EItemLootEntry.ExtendedBuilder addEntry(Item item, int weight, float minDamagePercent, float maxDamagePercent){
		return EItemLootEntry.lootItem(item).setWeight(weight).setDamageRange(minDamagePercent, maxDamagePercent).setExactly(1);
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation loc, LootPool.Builder pool){
		consumer.accept(loc, makeTable().withPool(pool));
		madeRacks.add(loc);
	}
}
