package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.LOTRItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class ExtendedWeaponRackLootGenerator extends ChestLootTables  {
	protected static final List<ResourceLocation> madeRacks = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.HOBBIT_WEAPONRACKS, makePool()
				.add(addEntry(LOTRItems.IRON_DAGGER.get(), 1)));

		make(consumer, ExtendedLootTables.RANGER_WEAPONRACKS, makePool()
				.add(addEntry(Items.WOODEN_AXE, 60))
				.add(addEntry(Items.WOODEN_PICKAXE, 60))
				.add(addEntry(Items.WOODEN_SWORD, 80))
				.add(addEntry(LOTRItems.WOODEN_DAGGER.get(), 80))
				//.add(addEntry(LOTRItems.WOODEN_SPEAR.get(), 80))
				.add(addEntry(Items.STONE_AXE, 60))
				.add(addEntry(Items.STONE_PICKAXE, 60))
				.add(addEntry(Items.STONE_SWORD, 80))
				.add(addEntry(LOTRItems.STONE_DAGGER.get(), 80))
				.add(addEntry(LOTRItems.STONE_SPEAR.get(), 80))
				.add(addEntry(LOTRItems.BRONZE_AXE.get(), 70))
				.add(addEntry(LOTRItems.BRONZE_PICKAXE.get(), 70))
				.add(addEntry(LOTRItems.BRONZE_SWORD.get(), 100))
				.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 100))
				.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 100))
				.add(addEntry(Items.IRON_AXE, 60))
				.add(addEntry(Items.IRON_PICKAXE, 60))
				.add(addEntry(Items.IRON_SWORD, 90))
				.add(addEntry(LOTRItems.IRON_DAGGER.get(), 90))
				.add(addEntry(LOTRItems.IRON_SPEAR.get(), 90))
				.add(addEntry(LOTRItems.ARNOR_SWORD.get(), 80))
				.add(addEntry(LOTRItems.ARNOR_DAGGER.get(), 80))
				.add(addEntry(LOTRItems.ARNOR_SPEAR.get(), 80))
				.add(addEntry(Items.BOW, 85))
				.add(addEntry(ExtendedItems.LONGBOW.get(), 65))
				.add(addEntry(ExtendedItems.DUNEDAIN_BOW.get(), 45))
				.add(addEntry(ExtendedItems.RANGER_BOW.get(), 65)));

		make(consumer, ExtendedLootTables.GUNDABAD_WEAPONRACKS, makePool()
				.add(addEntry(Items.WOODEN_AXE, 60))
				.add(addEntry(Items.WOODEN_PICKAXE, 60))
				.add(addEntry(Items.WOODEN_SWORD, 80))
				.add(addEntry(LOTRItems.WOODEN_DAGGER.get(), 80))
				//.add(addEntry(LOTRItems.WOODEN_SPEAR.get(), 80))
				.add(addEntry(Items.STONE_AXE, 60))
				.add(addEntry(Items.STONE_PICKAXE, 60))
				.add(addEntry(Items.STONE_SWORD, 80))
				.add(addEntry(LOTRItems.STONE_DAGGER.get(), 80))
				.add(addEntry(LOTRItems.STONE_SPEAR.get(), 80))
				.add(addEntry(LOTRItems.BRONZE_AXE.get(), 70))
				.add(addEntry(LOTRItems.BRONZE_PICKAXE.get(), 70))
				.add(addEntry(LOTRItems.BRONZE_SWORD.get(), 100))
				.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 100))
				.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 100))
				.add(addEntry(Items.IRON_AXE, 60))
				.add(addEntry(Items.IRON_PICKAXE, 60))
				.add(addEntry(Items.IRON_SWORD, 90))
				.add(addEntry(LOTRItems.IRON_DAGGER.get(), 90))
				.add(addEntry(LOTRItems.IRON_SPEAR.get(), 90))
				.add(addEntry(ExtendedItems.BONE_AXE.get(), 70))
				.add(addEntry(ExtendedItems.BONE_PICKAXE.get(), 70))
				.add(addEntry(ExtendedItems.BONE_SWORD.get(), 100))
				.add(addEntry(ExtendedItems.BONE_DAGGER.get(), 100))
				.add(addEntry(ExtendedItems.BONE_SPEAR.get(), 100))
				.add(addEntry(ExtendedItems.ANGMAR_AXE.get(), 70))
				.add(addEntry(ExtendedItems.ANGMAR_PICKAXE.get(), 70))
				.add(addEntry(ExtendedItems.ANGMAR_SWORD.get(), 100))
				.add(addEntry(ExtendedItems.ANGMAR_DAGGER.get(), 100))
				.add(addEntry(ExtendedItems.ANGMAR_SPEAR.get(), 100))
				.add(addEntry(LOTRItems.MORDOR_AXE.get(), 60))
				.add(addEntry(LOTRItems.MORDOR_PICKAXE.get(), 60))
				.add(addEntry(LOTRItems.MORDOR_SCIMITAR.get(), 80))
				.add(addEntry(LOTRItems.MORDOR_DAGGER.get(), 80))
				.add(addEntry(LOTRItems.MORDOR_SPEAR.get(), 80))
				.add(addEntry(ExtendedItems.GUNDABAD_URUK_CLEAVER.get(), 30))
				.add(addEntry(ExtendedItems.GUNDABAD_URUK_DAGGER.get(), 40))
				.add(addEntry(ExtendedItems.GUNDABAD_URUK_SPEAR.get(), 40))
				.add(addEntry(Items.BOW, 85))
				.add(addEntry(ExtendedItems.ORC_BOW.get(), 65))
				.add(addEntry(ExtendedItems.GUNDABAD_URUK_BOW.get(), 35)));

		make(consumer, ExtendedLootTables.BARROW_DOWN_WEAPONRACKS, makePool()
				.add(addEntry(LOTRItems.BRONZE_AXE.get(), 70, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.BRONZE_PICKAXE.get(), 70, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.BRONZE_SWORD.get(), 100, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 100, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 100, 0.25f, 0.8f))
				.add(addEntry(Items.IRON_AXE, 60, 0.25f, 0.8f))
				.add(addEntry(Items.IRON_PICKAXE, 60, 0.25f, 0.8f))
				.add(addEntry(Items.IRON_SWORD, 90, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.IRON_DAGGER.get(), 90, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.IRON_SPEAR.get(), 90, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.ARNOR_SWORD.get(), 80, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.ARNOR_DAGGER.get(), 80, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.ARNOR_SPEAR.get(), 80, 0.25f, 0.8f))
				.add(addEntry(LOTRItems.MITHRIL_SWORD.get(), 1, 0.95F, 0.98F))
				.add(addEntry(LOTRItems.MITHRIL_DAGGER.get(), 1, 0.95F, 0.98F))
				.add(addEntry(LOTRItems.MITHRIL_SPEAR.get(), 1, 0.95F, 0.98F))
				.add(addEntry(LOTRItems.MITHRIL_AXE.get(), 1, 0.95F, 0.98F))
				.add(addEntry(ExtendedItems.MITHRIL_MATTOCK.get(), 1, 0.95F, 0.98F))
				.add(addEntry(ExtendedItems.BARROW_BLADE.get(), 75, 0.2f, 0.6f)));

		make(consumer, ExtendedLootTables.BREE_MAN_WEAPONRACKS, makePool()
				.add(addEntry(Items.WOODEN_AXE, 60))
				.add(addEntry(Items.WOODEN_PICKAXE, 60))
				.add(addEntry(Items.WOODEN_SWORD, 80))
				.add(addEntry(LOTRItems.WOODEN_DAGGER.get(), 80))
				//.add(addEntry(LOTRItems.WOODEN_SPEAR.get(), 80))
				.add(addEntry(Items.STONE_AXE, 60))
				.add(addEntry(Items.STONE_PICKAXE, 60))
				.add(addEntry(Items.STONE_SWORD, 80))
				.add(addEntry(LOTRItems.STONE_DAGGER.get(), 80))
				.add(addEntry(LOTRItems.STONE_SPEAR.get(), 80))
				.add(addEntry(LOTRItems.BRONZE_AXE.get(), 70))
				.add(addEntry(LOTRItems.BRONZE_PICKAXE.get(), 70))
				.add(addEntry(LOTRItems.BRONZE_SWORD.get(), 100))
				.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 100))
				.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 100))
				.add(addEntry(Items.IRON_AXE, 60))
				.add(addEntry(Items.IRON_PICKAXE, 60))
				.add(addEntry(Items.IRON_SWORD, 90))
				.add(addEntry(LOTRItems.IRON_DAGGER.get(), 90))
				.add(addEntry(LOTRItems.IRON_SPEAR.get(), 90)));

		make(consumer, ExtendedLootTables.BREE_HOBBIT_WEAPONRACKS, makePool()
				.add(addEntry(LOTRItems.IRON_DAGGER.get(), 1)));

		make(consumer, ExtendedLootTables.BREE_BUTCHER_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.WOODEN_DAGGER.get(), 80))
			.add(addEntry(LOTRItems.STONE_DAGGER.get(), 80))
			.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 100))
			.add(addEntry(LOTRItems.IRON_DAGGER.get(), 90))
			.add(addEntry(LOTRItems.IRON_SPEAR.get(), 90)));

		make(consumer, ExtendedLootTables.BREE_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BRONZE_AXE.get(), 70))
			.add(addEntry(LOTRItems.BRONZE_PICKAXE.get(), 70))
			.add(addEntry(LOTRItems.BRONZE_HOE.get(), 70))
			.add(addEntry(LOTRItems.BRONZE_SHOVEL.get(), 70))
			.add(addEntry(LOTRItems.BRONZE_SWORD.get(), 100))
			.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 100))
			.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 100))
			.add(addEntry(Items.IRON_AXE, 60))
			.add(addEntry(Items.IRON_PICKAXE, 60))
			.add(addEntry(Items.IRON_HOE, 60))
			.add(addEntry(Items.IRON_SHOVEL, 60))
			.add(addEntry(Items.IRON_SWORD, 90))
			.add(addEntry(LOTRItems.IRON_DAGGER.get(), 90))
			.add(addEntry(LOTRItems.IRON_SPEAR.get(), 90)));

		make(consumer, ExtendedLootTables.BREE_FARMER_WEAPONRACKS, makePool()
			.add(addEntry(Items.WOODEN_AXE, 60))
			.add(addEntry(Items.WOODEN_SHOVEL, 70))
			.add(addEntry(Items.WOODEN_HOE, 90))
			.add(addEntry(LOTRItems.WOODEN_DAGGER.get(), 40))
			.add(addEntry(Items.STONE_AXE, 60))
			.add(addEntry(Items.STONE_SHOVEL, 70))
			.add(addEntry(Items.STONE_HOE, 90))
			.add(addEntry(LOTRItems.STONE_DAGGER.get(), 40))
			.add(addEntry(LOTRItems.BRONZE_AXE.get(), 50))
			.add(addEntry(LOTRItems.BRONZE_SHOVEL.get(), 60))
			.add(addEntry(LOTRItems.BRONZE_HOE.get(), 80))
			.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 30))
			.add(addEntry(Items.IRON_HOE, 70))
			.add(addEntry(Items.IRON_SHOVEL, 50)));

		// Add blue dwarven hammer, battleaxe, pike
		make(consumer, ExtendedLootTables.BLUE_MOUNTAIN_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BLUE_DWARVEN_DAGGER.get(), 2))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_AXE.get(), 1))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_PICKAXE.get(), 2))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SHOVEL.get(), 1))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_HOE.get(), 1))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SWORD.get(), 2))
			.add(addEntry(LOTRItems.BLUE_DWARVEN_SPEAR.get(), 1))
			.add(addEntry(ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE.get(), 2))
			.add(addEntry(ExtendedItems.BLUE_DWARVEN_MATTOCK.get(), 2)));

		make(consumer, ExtendedLootTables.RIVENDELL_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.RIVENDELL_AXE.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_HOE.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_PICKAXE.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_SHOVEL.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_DAGGER.get(), 1)));

		make(consumer, ExtendedLootTables.RIVENDELL_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.RIVENDELL_AXE.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_HOE.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_PICKAXE.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_SHOVEL.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_DAGGER.get(), 1))
			.add(addEntry(LOTRItems.RIVENDELL_SPEAR.get(), 2))
			.add(addEntry(LOTRItems.RIVENDELL_SWORD.get(), 2)));

		make(consumer, ExtendedLootTables.LINDON_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.LINDON_AXE.get(), 1))
			.add(addEntry(LOTRItems.LINDON_HOE.get(), 1))
			.add(addEntry(LOTRItems.LINDON_PICKAXE.get(), 1))
			.add(addEntry(LOTRItems.LINDON_SHOVEL.get(), 1))
			.add(addEntry(LOTRItems.LINDON_SPEAR.get(), 1))
			.add(addEntry(LOTRItems.LINDON_SWORD.get(), 1))
			.add(addEntry(LOTRItems.LINDON_DAGGER.get(), 1)));

		make(consumer, ExtendedLootTables.LINDON_EQUIPMENT_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.LINDON_SPEAR.get(), 1))
			.add(addEntry(LOTRItems.LINDON_SWORD.get(), 2))
			.add(addEntry(ExtendedItems.LINDON_BOW.get(), 2))
			.add(addEntry(LOTRItems.LINDON_DAGGER.get(), 1)));


		make(consumer, ExtendedLootTables.LINDON_BOW_WEAPONRACKS, makePool()
			.add(addEntry(ExtendedItems.LINDON_BOW.get(), 2)));

		make(consumer, ExtendedLootTables.ISENGARD_WEAPONRACKS, makePool()
				.add(addEntry(LOTRItems.URUK_SPEAR.get(), 50))
				.add(addEntry(LOTRItems.URUK_CLEAVER.get(), 100))
				.add(addEntry(LOTRItems.URUK_BERSERKER_CLEAVER.get(), 25))
				.add(addEntry(LOTRItems.URUK_DAGGER.get(), 75))
				.add(addEntry(LOTRItems.URUK_AXE.get(), 50))
				.add(addEntry(LOTRItems.URUK_PICKAXE.get(), 50))
				.add(addEntry(LOTRItems.URUK_SHOVEL.get(), 50))
				.add(addEntry(ExtendedItems.URUK_CROSSBOW.get(), 70))
				.add(addEntry(ExtendedItems.ORC_BOW.get(), 60)));

		make(consumer, ExtendedLootTables.DUNLAND_WEAPONRACKS, makePool()
			.add(addEntry(Items.STONE_SWORD, 25))
			.add(addEntry(Items.STONE_AXE, 25))
			.add(addEntry(LOTRItems.STONE_DAGGER.get(), 25))
			.add(addEntry(LOTRItems.STONE_SPEAR.get(), 25))
			.add(addEntry(LOTRItems.BRONZE_AXE.get(), 25))
			.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 25))
			.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 25))
			.add(addEntry(Items.IRON_AXE, 25))
			.add(addEntry(LOTRItems.IRON_DAGGER.get(), 25))
			.add(addEntry(LOTRItems.IRON_SPEAR.get(), 25))
			.add(addEntry(LOTRItems.DUNLENDING_CLUB.get(), 100))
			.add(addEntry(Items.BOW, 50)));

		make(consumer, ExtendedLootTables.DUNLAND_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.BRONZE_SWORD.get(), 50))
			.add(addEntry(LOTRItems.BRONZE_AXE.get(), 40))
			.add(addEntry(LOTRItems.BRONZE_DAGGER.get(), 60))
			.add(addEntry(LOTRItems.BRONZE_SPEAR.get(), 60))
			.add(addEntry(Items.IRON_SWORD, 50))
			.add(addEntry(Items.IRON_AXE, 40))
			.add(addEntry(LOTRItems.IRON_DAGGER.get(), 60))
			.add(addEntry(LOTRItems.IRON_SPEAR.get(), 60))
			.add(addEntry(Items.BOW, 50)));

		make(consumer, ExtendedLootTables.DUNLAND_DWARVEN_SMITHY_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_SWORD.get(), 10))
			.add(addEntry(LOTRItems.DWARVEN_AXE.get(), 4))
			.add(addEntry(LOTRItems.DWARVEN_DAGGER.get(), 6))
			.add(addEntry(LOTRItems.DWARVEN_SPEAR.get(), 8))
			.add(addEntry(ExtendedItems.DWARVEN_THROWABLE_AXE.get(), 2)));

		// Add dwarven hammer, battleaxe, pike
		make(consumer, ExtendedLootTables.IRON_HILLS_WEAPONRACKS, makePool()
			.add(addEntry(LOTRItems.DWARVEN_DAGGER.get(), 2))
			.add(addEntry(LOTRItems.DWARVEN_AXE.get(), 1))
			.add(addEntry(LOTRItems.DWARVEN_PICKAXE.get(), 2))
			.add(addEntry(LOTRItems.DWARVEN_SHOVEL.get(), 1))
			.add(addEntry(LOTRItems.DWARVEN_HOE.get(), 1))
			.add(addEntry(LOTRItems.DWARVEN_SWORD.get(), 2))
			.add(addEntry(LOTRItems.DWARVEN_SPEAR.get(), 1))
			.add(addEntry(ExtendedItems.DWARVEN_THROWABLE_AXE.get(), 2))
			.add(addEntry(ExtendedItems.DWARVEN_MATTOCK.get(), 2)));
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item, int weight){
		return addEntry(item, weight, 0.3f, 0.75f);
	}

	public StandaloneLootEntry.Builder<?> addEntry(Item item, int weight, float minDamagePercent, float maxDamagePercent){
		return EItemLootEntry.lootItem(item).setWeight(weight).setDamageRange(minDamagePercent, maxDamagePercent).apply(SetCount.setCount(ConstantRange.exactly(1)));
	}

	public LootPool.Builder makePool(){
		return LootPool.lootPool().setRolls(ConstantRange.exactly(1));
	}

	public void make(BiConsumer<ResourceLocation, Builder> consumer, ResourceLocation loc, LootPool.Builder pool){
		consumer.accept(loc, LootTable.lootTable().withPool(pool));
		madeRacks.add(loc);
	}
}
