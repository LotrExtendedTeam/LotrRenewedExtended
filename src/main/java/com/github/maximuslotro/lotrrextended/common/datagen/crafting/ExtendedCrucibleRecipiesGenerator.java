package com.github.maximuslotro.lotrrextended.common.datagen.crafting;

import java.util.function.Consumer;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;

public class ExtendedCrucibleRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCrucibleRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}
	// Finished, copper, tin, bronze, gilded iron, iron, silver, mithril, 
	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		buildVanillaRecipes(consumer);
		buildRenewedRecipes(consumer);
		buildExtendedRecipes(consumer);
		buildOtherModsRecipes(consumer);
	}
	private void buildVanillaRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCrucibleRecipe(consumer, ()->Items.COMPASS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.CLOCK, ()->Items.GOLD_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.IRON_DOOR, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.IRON_TRAPDOOR, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.IRON_BARS, ()->Items.IRON_NUGGET, 3);
		makeCrucibleRecipe(consumer, ()->Items.IRON_HORSE_ARMOR, ()->Items.IRON_INGOT, 6);
		makeCrucibleRecipe(consumer, ()->Items.IRON_SWORD, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.IRON_SHOVEL, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.IRON_PICKAXE, ()->Items.IRON_INGOT, 3);
		makeCrucibleRecipe(consumer, ()->Items.IRON_AXE, ()->Items.IRON_INGOT, 3);
		makeCrucibleRecipe(consumer, ()->Items.IRON_HOE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.IRON_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, ()->Items.IRON_CHESTPLATE, ()->Items.IRON_INGOT, 8);
		makeCrucibleRecipe(consumer, ()->Items.IRON_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, ()->Items.IRON_BOOTS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_SWORD, ()->Items.GOLD_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_SHOVEL, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_PICKAXE, ()->Items.GOLD_INGOT, 3);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_AXE, ()->Items.GOLD_INGOT, 3);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_HOE, ()->Items.GOLD_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_HELMET, ()->Items.GOLD_INGOT, 5);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_CHESTPLATE, ()->Items.GOLD_INGOT, 8);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_LEGGINGS, ()->Items.GOLD_INGOT, 7);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_BOOTS, ()->Items.GOLD_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_APPLE, ()->Items.GOLD_INGOT, 8);
		makeCrucibleRecipe(consumer, ()->Items.GOLDEN_CARROT, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, ()->Items.GLISTERING_MELON_SLICE, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, ()->Items.POWERED_RAIL, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.RAIL, ()->Items.IRON_INGOT, 2, ()->Items.IRON_NUGGET, 4);
		makeCrucibleRecipe(consumer, ()->Items.DETECTOR_RAIL, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.ACTIVATOR_RAIL, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.NETHERITE_INGOT, ()->Items.GOLD_INGOT, 4, ()->Items.NETHERITE_SCRAP, 4);
		makeCrucibleRecipe(consumer, ()->Items.LIGHT_WEIGHTED_PRESSURE_PLATE, ()->Items.GOLD_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.HEAVY_WEIGHTED_PRESSURE_PLATE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.CHAINMAIL_HELMET, ()->Items.IRON_INGOT, 3, ()->Items.IRON_NUGGET, 2);
		makeCrucibleRecipe(consumer, ()->Items.CHAINMAIL_CHESTPLATE, ()->Items.IRON_INGOT, 5, ()->Items.IRON_NUGGET, 3);
		makeCrucibleRecipe(consumer, ()->Items.CHAINMAIL_LEGGINGS, ()->Items.IRON_INGOT, 5, ()->Items.IRON_NUGGET, 2);
		makeCrucibleRecipe(consumer, ()->Items.CHAINMAIL_BOOTS, ()->Items.IRON_INGOT, 2, ()->Items.IRON_NUGGET, 2);
		makeCrucibleRecipe(consumer, ()->Items.CHAIN, ()->Items.IRON_INGOT, 1, ()->Items.IRON_NUGGET, 2);
		makeCrucibleRecipe(consumer, ()->Items.SHEARS, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.BUCKET, ()->Items.IRON_INGOT, 3);
		makeCrucibleRecipe(consumer, ()->Items.FLINT_AND_STEEL, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.CAULDRON, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, ()->Items.MINECART, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, ()->Items.CROSSBOW, ()->Items.IRON_INGOT, 1, ()->Items.STRING, 2);
		makeCrucibleRecipe(consumer, ()->Items.SMITHING_TABLE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.BLAST_FURNACE, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, ()->Items.SADDLE, ()->Items.IRON_INGOT, 2);
	}

	private void buildRenewedRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCrucibleRecipe(consumer, LOTRItems.COPPER_GOBLET, LOTRItems.COPPER_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.GOLDEN_GOBLET, ()->Items.GOLD_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_GOBLET, LOTRItems.SILVER_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_INGOT, LOTRItems.TIN_INGOT, LOTRItems.COPPER_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.ALE_HORN, LOTRItems.TIN_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GOLDEN_ALE_HORN, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.WOODEN_MUG, LOTRItems.TIN_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.CLAY_MUG, LOTRItems.TIN_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.IRON_CHANDELIER, ()->Items.IRON_NUGGET, 5);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_CHANDELIER, LOTRItems.BRONZE_NUGGET, 4);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_CHANDELIER, LOTRItems.SILVER_NUGGET, 4);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_CHANDELIER, ()->Items.GOLD_NUGGET, 4);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_CHANDELIER, LOTRItems.MITHRIL_NUGGET, 4);
		makeCrucibleRecipe(consumer, LOTRItems.IRON_PORTCULLIS, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_PORTCULLIS, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.GOLDEN_GATE, ()->Items.GOLD_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_GATE, LOTRItems.SILVER_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_GATE, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.WOODEN_GATE, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_GATE, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.IRON_SPEAR, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.IRON_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_BARS, LOTRItems.BRONZE_NUGGET, 3);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_LANTERN, LOTRItems.BRONZE_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_SWORD, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_SHOVEL, LOTRItems.BRONZE_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_PICKAXE, LOTRItems.BRONZE_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_AXE, LOTRItems.BRONZE_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_HOE, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_HELMET, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_CHESTPLATE, LOTRItems.BRONZE_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_LEGGINGS, LOTRItems.BRONZE_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_BOOTS, LOTRItems.BRONZE_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_SPEAR, LOTRItems.BRONZE_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_DAGGER, LOTRItems.BRONZE_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_BARS, ()->Items.GOLD_NUGGET, 3);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_BARS, LOTRItems.SILVER_NUGGET, 3);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_RING, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_RING, LOTRItems.MITHRIL_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_RING, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_BLUE_DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_BLUE_DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_BLUE_DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GOLD_TRIMMED_BLUE_DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_BLUE_DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_BLUE_DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_BLUE_DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_TRIMMED_BLUE_DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4, LOTRItems.SILVER_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.RED_BOOK, ()->Items.GOLD_NUGGET);
		makeCrucibleRecipe(consumer, LOTRItems.GATE_GEAR, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_SWORD, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_SHOVEL, LOTRItems.MITHRIL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_PICKAXE, LOTRItems.MITHRIL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_AXE, LOTRItems.MITHRIL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_HOE, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_HELMET, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_CHESTPLATE, LOTRItems.MITHRIL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_LEGGINGS, LOTRItems.MITHRIL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_BOOTS, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_SPEAR, LOTRItems.MITHRIL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_DAGGER, LOTRItems.MITHRIL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_CHESTPLATE, ()->Items.IRON_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_BOOTS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_SWORD, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_SPEAR, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_CHESTPLATE, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_BOOTS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DOL_AMROTH_SWORD, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_CHESTPLATE, ()->Items.IRON_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_BOOTS, ()->Items.IRON_INGOT, 4);
	}

	private void buildExtendedRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCrucibleRecipe(consumer, ExtendedItems.SKULL_GOBLET, LOTRItems.TIN_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.BRANDING_IRON, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BRONZE_BIRDCAGE, LOTRItems.BRONZE_INGOT, 4, LOTRItems.BRONZE_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.IRON_BIRDCAGE, ()->Items.IRON_INGOT, 4, ()->Items.IRON_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_BIRDCAGE, LOTRItems.SILVER_INGOT, 4, LOTRItems.SILVER_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_BIRDCAGE, ()->Items.GOLD_INGOT, 4, ()->Items.GOLD_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.MITHRIL_BIRDCAGE, LOTRItems.MITHRIL_INGOT, 4, LOTRItems.MITHRIL_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.IRON_THROWABLE_AXE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BRONZE_THROWABLE_AXE, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.IRON_THROWABLE_AXE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.DWARVEN_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.GILDED_IRON_INGOT, ()->Items.IRON_INGOT, ()->Items.GOLD_NUGGET);
		makeCrucibleRecipe(consumer, ExtendedItems.CONQUEST_TABLE, LOTRItems.BRONZE_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.COMMANDHORN, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BRICK, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_PILLAR, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.HIGH_ELVEN_CHAIN, LOTRItems.SILVER_INGOT, 1, LOTRItems.SILVER_NUGGET, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.ORC_CHAIN, LOTRItems.ORC_STEEL_INGOT, 1, ExtendedItems.ORC_STEEL_NUGGET, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.CHISEL, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.MITHRIL_MATTOCK, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.DWARVEN_MATTOCK, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BLUE_DWARVEN_MATTOCK, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_COIN_ONE, LOTRItems.SILVER_NUGGET, 1);
		makeCrucibleRecipe(consumer, ExtendedItems.MAIL_WARG_ARMOR, ()->Items.IRON_INGOT, 6);
	}

	private void buildOtherModsRecipes(Consumer<IFinishedRecipe> consumer) {
		
	}
	@Override
	public String getName() {
		return "Renewed Extended Crucible Recipe Provider";
	}
}