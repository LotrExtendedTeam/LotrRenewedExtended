package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.maximuslotro.lotrrextended.common.loot.EItemLootEntry;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierScrollAdder;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.item.Item;
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
				.add(addEntry(Items.COD).setWeight(30))
				.add(addEntry(Items.SALMON).setWeight(13))
				.add(addEntry(Items.TROPICAL_FISH).setWeight(1))
				.add(addEntry(Items.PUFFERFISH).setWeight(6))));
		make(consumer, ExtendedLootTables.MIDDLE_EARTH_FISHING_JUNK, makeTable()
			.withPool(LootPool.lootPool()
				// Using wiki values, with 0.515 (hobbit bone) being one weight, the rest of these were calculated
				.add(addEntry(Items.FISHING_ROD).setWeight(5).setMaxLootDurability(0.1f))
				.add(addEntry(Items.WOODEN_SWORD).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(Items.WOODEN_AXE).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(Items.WOODEN_PICKAXE).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(Items.WOODEN_SHOVEL).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(Items.WOODEN_HOE).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(ExtendedItems.LEATHER_HAT.get()).setWeight(10))
				.add(addEntry(Items.LEATHER_HELMET).setWeight(5).setMaxLootDurability(0.5f))
				.add(addEntry(Items.LEATHER_BOOTS).setWeight(5).setMaxLootDurability(0.5f))
				.add(addEntry(LOTRItems.BONE_HELMET.get()).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(LOTRItems.BONE_BOOTS.get()).setWeight(2).setMaxLootDurability(0.5f))
				.add(addEntry(Items.SKELETON_SKULL).setWeight(5))
				.add(addEntry(Items.BONE).setWeight(20))
				.add(addEntry(LOTRItems.ORC_BONE.get()).setWeight(10))
				.add(addEntry(LOTRItems.ELF_BONE.get()).setWeight(2))
				.add(addEntry(LOTRItems.DWARF_BONE.get()).setWeight(2))
				.add(addEntry(LOTRItems.HOBBIT_BONE.get()).setWeight(1))
				.add(addEntry(LOTRItems.ROTTEN_LOG.get()).setWeight(10))
				.add(addEntry(Items.LEATHER).setWeight(10))
				.add(addEntry(Items.STRING).setWeight(10))
				.add(addEntry(Items.BOWL).setWeight(10))
				.add(addEntry(LOTRItems.WOODEN_MUG.get()).setWeight(10))
				.add(addEntry(Items.BOOK).setWeight(5))
				.add(addEntry(Items.STICK).setWeight(10))
				.add(addEntry(Items.FEATHER).setWeight(1).setExactly(10))
				.add(addEntry(Items.INK_SAC).setWeight(1).setExactly(5))
				.add(addEntry(Items.ROTTEN_FLESH).setWeight(5))
				.add(addEntry(LOTRItems.SUSPICIOUS_MEAT.get()).setWeight(5))
				.add(addEntry(LOTRItems.MAN_FLESH.get()).setWeight(5))
				.add(addEntry(LOTRItems.MAGGOTY_BREAD.get()).setWeight(5))
				.add(addEntry(Items.LILY_PAD).setWeight(15))
				.add(addEntry(ExtendedItems.OBSIDIAN_SHARD.get()).setWeight(10))
				.add(addEntry(Items.TRIPWIRE_HOOK).setWeight(1))));
		make(consumer, ExtendedLootTables.MIDDLE_EARTH_FISHING_TREASURE, makeTable()
			.withPool(LootPool.lootPool()
				// Using wiki values, with 0.165 (silver coin 100 and mithril ring) being one weight, the rest of these were calculated
				.add(addEntry(ExtendedItems.PEARL.get()).setWeight(200))
				.add(addEntry(Items.BOW).setWeight(1).setMaxLootDurability(0.75f).addModifiersNoNegative())
				.add(addEntry(Items.FISHING_ROD).setWeight(20).setMaxLootDurability(0.75f).addModifiersNoNegative())
				.add(addEntry(LOTRItems.IRON_DAGGER.get()).setWeight(20).setMaxLootDurability(0.75f).addModifiersNoNegative())
				.add(addEntry(LOTRItems.BRONZE_DAGGER.get()).setWeight(20).setMaxLootDurability(0.75f).addModifiersNoNegative())
				.add(addEntry(ExtendedItems.SILVER_COIN_ONE.get()).setWeight(100))
				.add(addEntry(ExtendedItems.SILVER_COIN_TEN.get()).setWeight(10))
				.add(addEntry(ExtendedItems.SILVER_COIN_HUNDRED.get()).setWeight(1))
				.add(addEntry(LOTRItems.SMALL_POUCH.get()).setWeight(20))
				.add(addEntry(LOTRItems.MEDIUM_POUCH.get()).setWeight(10))
				.add(addEntry(LOTRItems.LARGE_POUCH.get()).setWeight(5))
				.add(addEntry(Items.IRON_INGOT).setWeight(20))
				.add(addEntry(LOTRItems.BRONZE_INGOT.get()).setWeight(10))
				.add(addEntry(LOTRItems.COPPER_INGOT.get()).setWeight(10))
				.add(addEntry(LOTRItems.TIN_INGOT.get()).setWeight(10))
				.add(addEntry(Items.GOLD_NUGGET).setWeight(50))
				.add(addEntry(Items.GOLD_INGOT).setWeight(5))
				.add(addEntry(LOTRItems.SILVER_NUGGET.get()).setWeight(50))
				.add(addEntry(LOTRItems.SILVER_INGOT.get()).setWeight(5))
				.add(addEntry(LOTRItems.MITHRIL_NUGGET.get()).setWeight(5))
				.add(addEntry(LOTRItems.SILVER_RING.get()).setWeight(10))
				.add(addEntry(LOTRItems.GOLD_RING.get()).setWeight(5))
				.add(addEntry(LOTRItems.MITHRIL_RING.get()).setWeight(1))
				.add(addEntry(ExtendedItems.ARTISAN_TOME.get()).setWeight(10))
				.add(addEntry(ExtendedItems.COMBAT_TOME.get()).setWeight(10))
				.add(addEntry(ExtendedItems.COMBAT_TOME.get()).setWeight(5))
				.apply(ExtendedModifierScrollAdder.chanceCustom(100).setSkillful())));
	}

	public EItemLootEntry.ExtendedBuilder addEntry(Item item){
		return EItemLootEntry.lootItem(item);
	}

	public LootTable.Builder makeTable(){
		return LootTable.lootTable();
	}

	public void make(BiConsumer<ResourceLocation, LootTable.Builder> consumer, ResourceLocation loc, LootTable.Builder table){
		consumer.accept(loc, table);
		madeFish.add(loc);
	}
}