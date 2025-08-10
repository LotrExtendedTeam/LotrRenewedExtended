package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.util.ResourceLocation;

public class ExtendedFishingLootGenerator extends FishingLootTables  {
	protected static final List<ResourceLocation> madeFish = new ArrayList<>();

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		make(consumer, ExtendedLootTables.MIDDLE_EARTH_FISHING, makeTable()
			.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
				.add(TableLootEntry.lootTableReference(ExtendedLootTables.MIDDLE_EARTH_FISHING_JUNK).setWeight(10).setQuality(-2))
				.add(TableLootEntry.lootTableReference(ExtendedLootTables.MIDDLE_EARTH_FISHING_TREASURE).setWeight(20).setQuality(2)
					.when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().fishingHook(FishingPredicate.inOpenWater(true)))))
				.add(TableLootEntry.lootTableReference(ExtendedLootTables.MIDDLE_EARTH_FISHING_FISH).setWeight(70).setQuality(-1))));
		make(consumer, ExtendedLootTables.MIDDLE_EARTH_FISHING_FISH, makeTable()
			.withPool(LootPool.lootPool()
				// Using wiki values, with 2 (clownfish (tropical fish)) being one weight, the rest of these were calculated
				.add(EItemLootEntry.lootItem(Items.COD).setWeight(30))
				.add(EItemLootEntry.lootItem(Items.SALMON).setWeight(13))
				.add(EItemLootEntry.lootItem(Items.TROPICAL_FISH).setWeight(1))
				.add(EItemLootEntry.lootItem(Items.PUFFERFISH).setWeight(6))));
		make(consumer, ExtendedLootTables.MIDDLE_EARTH_FISHING_JUNK, makeTable()
			.withPool(LootPool.lootPool()
				// Using wiki values, with 0.515 (hobbit bone) being one weight, the rest of these were calculated
				.add(EItemLootEntry.lootItem(Items.FISHING_ROD).setWeight(5).setMaxLootDurability(0.1f))
				.add(EItemLootEntry.lootItem(Items.WOODEN_SWORD).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(Items.WOODEN_AXE).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(Items.WOODEN_PICKAXE).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(Items.WOODEN_SHOVEL).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(Items.WOODEN_HOE).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(ExtendedItems.LEATHER_HAT.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.LEATHER_HELMET).setWeight(5).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(Items.LEATHER_BOOTS).setWeight(5).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(LOTRItems.BONE_HELMET.get()).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(LOTRItems.BONE_BOOTS.get()).setWeight(2).setMaxLootDurability(0.5f))
				.add(EItemLootEntry.lootItem(Items.SKELETON_SKULL).setWeight(5))
				.add(EItemLootEntry.lootItem(Items.BONE).setWeight(20))
				.add(EItemLootEntry.lootItem(LOTRItems.ORC_BONE.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(LOTRItems.ELF_BONE.get()).setWeight(2))
				.add(EItemLootEntry.lootItem(LOTRItems.DWARF_BONE.get()).setWeight(2))
				.add(EItemLootEntry.lootItem(LOTRItems.HOBBIT_BONE.get()).setWeight(1))
				.add(EItemLootEntry.lootItem(LOTRItems.ROTTEN_LOG.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.LEATHER).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.STRING).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.BOWL).setWeight(10))
				.add(EItemLootEntry.lootItem(LOTRItems.WOODEN_MUG.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.BOOK).setWeight(5))
				.add(EItemLootEntry.lootItem(Items.STICK).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.FEATHER).setWeight(1).setExactly(10))
				.add(EItemLootEntry.lootItem(Items.INK_SAC).setWeight(1).setExactly(5))
				.add(EItemLootEntry.lootItem(Items.ROTTEN_FLESH).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.SUSPICIOUS_MEAT.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.MAN_FLESH.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.MAGGOTY_BREAD.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(Blocks.LILY_PAD).setWeight(15))
				.add(EItemLootEntry.lootItem(Blocks.TRIPWIRE_HOOK).setWeight(1))));
		make(consumer, ExtendedLootTables.MIDDLE_EARTH_FISHING_TREASURE, makeTable()
			.withPool(LootPool.lootPool()
				// Using wiki values, with 0.165 (silver coin 100 and mithril ring) being one weight, the rest of these were calculated
				.add(EItemLootEntry.lootItem(Items.NAME_TAG).setWeight(1))
				.add(EItemLootEntry.lootItem(ExtendedItems.PEARL.get()).setWeight(200))
				.add(EItemLootEntry.lootItem(Items.BOW).setWeight(1).setMaxLootDurability(0.75f))
				.add(EItemLootEntry.lootItem(Items.FISHING_ROD).setWeight(20).setMaxLootDurability(0.75f))
				.add(EItemLootEntry.lootItem(LOTRItems.IRON_DAGGER.get()).setWeight(20).setMaxLootDurability(0.75f))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_DAGGER.get()).setWeight(20).setMaxLootDurability(0.75f))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_ONE.get()).setWeight(100))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_TEN.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(ExtendedItems.SILVER_COIN_HUNDRED.get()).setWeight(1))
				.add(EItemLootEntry.lootItem(LOTRItems.SMALL_POUCH.get()).setWeight(20))
				.add(EItemLootEntry.lootItem(LOTRItems.MEDIUM_POUCH.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(LOTRItems.LARGE_POUCH.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(Items.IRON_INGOT).setWeight(20))
				.add(EItemLootEntry.lootItem(LOTRItems.BRONZE_INGOT.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(LOTRItems.COPPER_INGOT.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(LOTRItems.TIN_INGOT.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(Items.GOLD_NUGGET).setWeight(50))
				.add(EItemLootEntry.lootItem(Items.GOLD_INGOT).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_NUGGET.get()).setWeight(50))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_INGOT.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_NUGGET.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.SILVER_RING.get()).setWeight(10))
				.add(EItemLootEntry.lootItem(LOTRItems.GOLD_RING.get()).setWeight(5))
				.add(EItemLootEntry.lootItem(LOTRItems.MITHRIL_RING.get()).setWeight(1))));
	}

	public LootTable.Builder makeTable(){
		return LootTable.lootTable();
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeFish.add(loc);
	}
}