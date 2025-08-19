package com.github.maximuslotro.lotrrextended.common.datagen.loot_tables;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.loot.ExtendedLootTables;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;

public class ExtendedLootTableGenerator extends LootTableProvider {

	public ExtendedLootTableGenerator(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
		for(ResourceLocation location : ExtendedLootTables.getLocations()) {
			boolean found = false;
			if(ExtendedChestLootGenerator.madeChests.contains(location)) {
				found = true;
			}
			if(ExtendedDrinkLootGenerator.madeKegs.contains(location)) {
				found = true;
			}
			if(ExtendedDrinkLootGenerator.madeVessels.contains(location)) {
				found = true;
			}
			if(ExtendedPlateLootGenerator.madePlates.contains(location)) {
				found = true;
			}
			if(ExtendedWeaponRackLootGenerator.madeRacks.contains(location)) {
				found = true;
			}
			if(ExtendedFishingLootGenerator.madeFish.contains(location)) {
				found = true;
			}
			if(!found) {
				System.out.println("Didn't make a sepcified Loot-Table: "+location.toString());
			}
		}
		ExtendedLog.info("Made "+map.size()+" Loot-Tables!");
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
		return ImmutableList.of(
				Pair.of(ExtendedBlockLootGenerator::new, LootParameterSets.BLOCK),
				Pair.of(ExtendedChestLootGenerator::new, LootParameterSets.CHEST),
				Pair.of(ExtendedEntityLootGenerator::new, LootParameterSets.ENTITY),
				Pair.of(ExtendedDrinkLootGenerator::new, LootParameterSets.CHEST),
				Pair.of(ExtendedPlateLootGenerator::new, LootParameterSets.CHEST),
				Pair.of(ExtendedWeaponRackLootGenerator::new, LootParameterSets.CHEST),
				Pair.of(ExtendedFishingLootGenerator::new, LootParameterSets.FISHING));
	}

	@Override
	public String getName() {
		return "Renewed Extended Loot Tables provider";
	}

}
