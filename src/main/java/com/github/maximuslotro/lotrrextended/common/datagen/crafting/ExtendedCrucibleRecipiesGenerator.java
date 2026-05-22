package com.github.maximuslotro.lotrrextended.common.datagen.crafting;

import java.util.Map.Entry;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import lotr.common.item.LOTRArmorItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.RegistryKey;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedCrucibleRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCrucibleRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}
	// Finished, copper, tin, bronze, gilded iron, iron, silver, mithril, orc steel, dwarven steel, elven, uruk, galvorn
	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		buildVanillaRecipes(consumer);
		buildRenewedRecipes(consumer);
		buildExtendedRecipes(consumer);
		buildOtherModsRecipes(consumer);
		checkForMissingRecipes();
	}
	private void buildVanillaRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCrucibleRecipe(consumer, ()->Items.COMPASS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.CLOCK, ()->Items.GOLD_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.IRON_DOOR, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ()->Items.IRON_TRAPDOOR, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, ()->Items.IRON_BARS, ()->Items.IRON_NUGGET, 3);
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
		makeCrucibleRecipe(consumer, ()->Items.LEATHER_HELMET, ()->Items.LEATHER, 5);
		makeCrucibleRecipe(consumer, ()->Items.LEATHER_CHESTPLATE, ()->Items.LEATHER, 8);
		makeCrucibleRecipe(consumer, ()->Items.LEATHER_LEGGINGS, ()->Items.LEATHER, 7);
		makeCrucibleRecipe(consumer, ()->Items.LEATHER_BOOTS, ()->Items.LEATHER, 4);
		makeCrucibleRecipe(consumer, ()->Items.IRON_HORSE_ARMOR, ()->Items.IRON_INGOT, 6);
		makeCrucibleRecipe(consumer, ()->Items.LEATHER_HORSE_ARMOR, ()->Items.LEATHER, 7);
		makeCrucibleRecipe(consumer, ()->Items.SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ()->Items.BOW, ()->Items.STRING, 3);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_SWORD, ()->Items.DIAMOND, 2);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_SHOVEL, ()->Items.DIAMOND);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_PICKAXE, ()->Items.DIAMOND, 3);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_AXE, ()->Items.DIAMOND, 3);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_HOE, ()->Items.DIAMOND, 2);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_HELMET, ()->Items.DIAMOND, 5);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_CHESTPLATE, ()->Items.DIAMOND, 8);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_LEGGINGS, ()->Items.DIAMOND, 7);
		makeCrucibleRecipe(consumer, ()->Items.DIAMOND_BOOTS, ()->Items.DIAMOND, 4);
		makeCrucibleRecipe(consumer, ()->Items.FISHING_ROD, ()->Items.STRING, 2);
		makeCrucibleRecipe(consumer, ()->Items.CARROT_ON_A_STICK, ()->Items.STRING, 2);
		makeCrucibleRecipe(consumer, ()->Items.WARPED_FUNGUS_ON_A_STICK, ()->Items.STRING, 2);
		makeCrucibleRecipe(consumer, ()->Items.TURTLE_HELMET, ()->Items.SCUTE, 5);
		makeCrucibleRecipe(consumer, ()->Items.STONE_SWORD, ()->Items.STONE, 2);
		makeCrucibleRecipe(consumer, ()->Items.STONE_SHOVEL, ()->Items.STONE);
		makeCrucibleRecipe(consumer, ()->Items.STONE_PICKAXE, ()->Items.STONE, 3);
		makeCrucibleRecipe(consumer, ()->Items.STONE_AXE, ()->Items.STONE, 3);
		makeCrucibleRecipe(consumer, ()->Items.STONE_HOE, ()->Items.STONE, 2);
	}

	private void buildRenewedRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCrucibleRecipe(consumer, LOTRItems.COPPER_GOBLET, LOTRItems.COPPER_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.GOLDEN_GOBLET, ()->Items.GOLD_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_GOBLET, LOTRItems.SILVER_INGOT, 2);

		makeCrucibleRecipe(consumer, LOTRItems.BRONZE_INGOT, LOTRItems.TIN_INGOT, LOTRItems.COPPER_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.RED_BOOK, ()->Items.GOLD_NUGGET);

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
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_GATE, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GATE_GEAR, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.ORC_GATE, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_GATE, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_GATE, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.HIGH_ELVEN_GATE, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_GATE, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_GATE, LOTRItems.URUK_STEEL_INGOT, 2);

		makeCrucibleRecipe(consumer, LOTRItems.ORC_PLATING, ExtendedItems.ORC_STEEL_NUGGET, 4);

		makeCrucibleRecipe(consumer, LOTRItems.IRON_SPEAR, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.IRON_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BLACKSMITH_HAMMER, ()->Items.IRON_INGOT, 3);

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
		makeCrucibleRecipe(consumer, LOTRItems.ORC_BARS, ExtendedItems.ORC_STEEL_NUGGET, 3);
		//makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_BARS, ExtendedItems.DWARVEN_STEEL_NUGGET, 3);
		//makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_BARS, ExtendedItems.DWARVEN_STEEL_NUGGET, 3);

		makeCrucibleRecipe(consumer, LOTRItems.GOLD_RING, ()->Items.GOLD_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_RING, LOTRItems.MITHRIL_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.SILVER_RING, LOTRItems.SILVER_NUGGET, 8);

		makeCrucibleRecipe(consumer, LOTRItems.COAST_SOUTHRON_SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DALE_SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.HARNENNOR_SHIELD, LOTRItems.BRONZE_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_SHIELD, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_SHIELD, ()->Items.IRON_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_SPEAR, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_DAGGER, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_SWORD, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_SHOVEL, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_PICKAXE, LOTRItems.DWARVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.DWARVEN_HOE, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_SPEAR, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_DAGGER, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_SWORD, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_SHOVEL, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_PICKAXE, LOTRItems.DWARVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.BLUE_DWARVEN_HOE, LOTRItems.DWARVEN_STEEL_INGOT, 2);
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
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_TRIMMED_DWARVEN_HELMET, LOTRItems.DWARVEN_STEEL_INGOT, 5, LOTRItems.MITHRIL_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_TRIMMED_DWARVEN_CHESTPLATE, LOTRItems.DWARVEN_STEEL_INGOT, 8, LOTRItems.MITHRIL_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_TRIMMED_DWARVEN_LEGGINGS, LOTRItems.DWARVEN_STEEL_INGOT, 7, LOTRItems.MITHRIL_NUGGET, 8);
		makeCrucibleRecipe(consumer, LOTRItems.MITHRIL_TRIMMED_DWARVEN_BOOTS, LOTRItems.DWARVEN_STEEL_INGOT, 4, LOTRItems.MITHRIL_NUGGET, 8);

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
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_ELVEN_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_ELVEN_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_ELVEN_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_ELVEN_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_ELVEN_DAGGER, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.DORWINION_ELVEN_SWORD, LOTRItems.ELVEN_STEEL_INGOT, 2);

		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_CHESTPLATE, ()->Items.IRON_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_BOOTS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_SWORD, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.ARNOR_SPEAR, ()->Items.IRON_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.WINGED_GONDOR_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_CHESTPLATE, ()->Items.IRON_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_BOOTS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_SWORD, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.GONDOR_SPEAR, ()->Items.IRON_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.RANGER_NORTH_HELMET, ()->Items.LEATHER, 5);
		makeCrucibleRecipe(consumer, LOTRItems.RANGER_NORTH_CHESTPLATE, ()->Items.IRON_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.RANGER_NORTH_LEGGINGS, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.RANGER_NORTH_BOOTS, ()->Items.LEATHER, 4);

		makeCrucibleRecipe(consumer, LOTRItems.RANGER_ITHILIEN_HELMET, ()->Items.LEATHER, 5);
		makeCrucibleRecipe(consumer, LOTRItems.RANGER_ITHILIEN_CHESTPLATE, ()->Items.LEATHER, 8);
		makeCrucibleRecipe(consumer, LOTRItems.RANGER_ITHILIEN_LEGGINGS, ()->Items.LEATHER, 7);
		makeCrucibleRecipe(consumer, LOTRItems.RANGER_ITHILIEN_BOOTS, ()->Items.LEATHER, 4);

		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_HELMET, ()->Items.IRON_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_CHESTPLATE, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_LEGGINGS, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_BOOTS, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_SWORD, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_SPEAR, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_MARSHAL_HELMET, ()->Items.IRON_INGOT, 3, ()->Items.GOLD_NUGGET, 2);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_MARSHAL_CHESTPLATE, ()->Items.IRON_INGOT, 5, ()->Items.GOLD_NUGGET, 2);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_MARSHAL_LEGGINGS, ()->Items.IRON_INGOT, 5, ()->Items.GOLD_NUGGET, 2);
		makeCrucibleRecipe(consumer, LOTRItems.ROHAN_MARSHAL_BOOTS, ()->Items.IRON_INGOT, 2, ()->Items.GOLD_NUGGET, 2);

		makeCrucibleRecipe(consumer, LOTRItems.DUNLENDING_HELMET, ()->Items.IRON_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.DUNLENDING_CHESTPLATE, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DUNLENDING_LEGGINGS, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.DUNLENDING_BOOTS, ()->Items.IRON_INGOT, 2);

		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_HELMET, ()->Items.IRON_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_CHESTPLATE, ()->Items.IRON_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_LEGGINGS, ()->Items.IRON_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_BOOTS, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_DAGGER, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_SCIMITAR, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.UMBAR_SPEAR, ()->Items.IRON_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.HARAD_HELMET, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_WARLORD_HELMET, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_CHESTPLATE, LOTRItems.BRONZE_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_LEGGINGS, LOTRItems.BRONZE_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_BOOTS, LOTRItems.BRONZE_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_DAGGER, LOTRItems.BRONZE_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_SWORD, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.HARAD_SPEAR, LOTRItems.BRONZE_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.HARNENNOR_HELMET, LOTRItems.BRONZE_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.HARNENNOR_CHESTPLATE, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.HARNENNOR_LEGGINGS, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.HARNENNOR_BOOTS, LOTRItems.BRONZE_INGOT, 2);

		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_SCIMITAR, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_SHOVEL, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_PICKAXE, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_AXE, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_HOE, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_HELMET, LOTRItems.ORC_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_CHESTPLATE, LOTRItems.ORC_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_LEGGINGS, LOTRItems.ORC_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_BOOTS, LOTRItems.ORC_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_SPEAR, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.MORDOR_DAGGER, LOTRItems.ORC_STEEL_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_SWORD, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_SHOVEL, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_PICKAXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_AXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_HOE, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_SPEAR, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.GALADHRIM_DAGGER, LOTRItems.ELVEN_STEEL_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.LINDON_SWORD, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_SHOVEL, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_PICKAXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_AXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_HOE, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_SPEAR, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.LINDON_DAGGER, LOTRItems.ELVEN_STEEL_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_SWORD, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_SHOVEL, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_PICKAXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_AXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_HOE, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_SPEAR, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.RIVENDELL_DAGGER, LOTRItems.ELVEN_STEEL_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_SWORD, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_SHOVEL, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_PICKAXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_AXE, LOTRItems.ELVEN_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_HOE, LOTRItems.ELVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 6);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_SPEAR, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.WOOD_ELVEN_DAGGER, LOTRItems.ELVEN_STEEL_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.URUK_CLEAVER, LOTRItems.URUK_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_BERSERKER_CLEAVER, LOTRItems.URUK_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_SHOVEL, LOTRItems.URUK_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_PICKAXE, LOTRItems.URUK_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_AXE, LOTRItems.URUK_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_HOE, LOTRItems.URUK_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_HELMET, LOTRItems.URUK_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_BERSERKER_HELMET, LOTRItems.URUK_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_CHESTPLATE, LOTRItems.URUK_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_LEGGINGS, LOTRItems.URUK_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_BOOTS, LOTRItems.URUK_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_SPEAR, LOTRItems.URUK_STEEL_INGOT);
		makeCrucibleRecipe(consumer, LOTRItems.URUK_DAGGER, LOTRItems.URUK_STEEL_INGOT);

		makeCrucibleRecipe(consumer, LOTRItems.BONE_HELMET, ()->Items.BONE, 5);
		makeCrucibleRecipe(consumer, LOTRItems.BONE_CHESTPLATE, ()->Items.BONE, 8);
		makeCrucibleRecipe(consumer, LOTRItems.BONE_LEGGINGS, ()->Items.BONE, 7);
		makeCrucibleRecipe(consumer, LOTRItems.BONE_BOOTS, ()->Items.BONE, 2);

		makeCrucibleRecipe(consumer, LOTRItems.FUR_HELMET, LOTRItems.FUR, 5);
		makeCrucibleRecipe(consumer, LOTRItems.FUR_CHESTPLATE, LOTRItems.FUR, 8);
		makeCrucibleRecipe(consumer, LOTRItems.FUR_LEGGINGS, LOTRItems.FUR, 7);
		makeCrucibleRecipe(consumer, LOTRItems.FUR_BOOTS, LOTRItems.FUR, 4);

		makeCrucibleRecipe(consumer, LOTRItems.STONE_SPEAR, ()->Items.STONE);
		makeCrucibleRecipe(consumer, LOTRItems.STONE_DAGGER, ()->Items.STONE);
	}

	private void buildExtendedRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCrucibleRecipe(consumer, ExtendedItems.SKULL_GOBLET, LOTRItems.TIN_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.BRANDING_IRON, ()->Items.IRON_INGOT, 2);

		makeCrucibleRecipe(consumer, ExtendedItems.CONQUEST_TABLE, LOTRItems.BRONZE_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.NUMENOREAN_BARS, ()->Items.IRON_NUGGET, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BRICK, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_PILLAR, ()->Items.GOLD_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.HIGH_ELVEN_CHAIN, LOTRItems.SILVER_INGOT, 1, LOTRItems.SILVER_NUGGET, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.ORC_CHAIN, LOTRItems.ORC_STEEL_INGOT, 1, ExtendedItems.ORC_STEEL_NUGGET, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.ORC_CHANDELIER, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.ORC_STEEL_DOOR, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.ORC_STEEL_TRAPDOOR, LOTRItems.ORC_STEEL_INGOT, 2);

		makeCrucibleRecipe(consumer, ExtendedItems.BRONZE_BIRDCAGE, LOTRItems.BRONZE_INGOT, 4, LOTRItems.BRONZE_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.IRON_BIRDCAGE, ()->Items.IRON_INGOT, 4, ()->Items.IRON_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_BIRDCAGE, LOTRItems.SILVER_INGOT, 4, LOTRItems.SILVER_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_BIRDCAGE, ()->Items.GOLD_INGOT, 4, ()->Items.GOLD_NUGGET, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.MITHRIL_BIRDCAGE, LOTRItems.MITHRIL_INGOT, 4, LOTRItems.MITHRIL_NUGGET, 6);

		makeCrucibleRecipe(consumer, ExtendedItems.BRONZE_THROWABLE_AXE, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.IRON_THROWABLE_AXE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.DWARVEN_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.LOSSARNACH_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, 2);

		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_COIN_ONE, LOTRItems.SILVER_NUGGET, 1);
		makeCrucibleRecipe(consumer, ExtendedItems.GILDED_IRON_INGOT, ()->Items.IRON_INGOT, ()->Items.GOLD_NUGGET);

		makeCrucibleRecipe(consumer, ExtendedItems.COMMANDHORN, LOTRItems.BRONZE_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.CHISEL, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.BARROW_BLADE, ()->Items.IRON_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.LEATHER_HAT, ()->Items.LEATHER, 5);

		makeCrucibleRecipe(consumer, ExtendedItems.MITHRIL_MATTOCK, LOTRItems.MITHRIL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.DWARVEN_MATTOCK, LOTRItems.DWARVEN_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BLUE_DWARVEN_MATTOCK, LOTRItems.DWARVEN_STEEL_INGOT, 2);

		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_HELMET, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET, LOTRItems.BRONZE_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_CHESTPLATE, LOTRItems.BRONZE_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_LEGGINGS, LOTRItems.BRONZE_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_BOOTS, LOTRItems.BRONZE_INGOT, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_SWORD, ExtendedItems.OBSIDIAN_SHARD, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_SHOVEL, ExtendedItems.OBSIDIAN_SHARD);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_PICKAXE, ExtendedItems.OBSIDIAN_SHARD, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_AXE, ExtendedItems.OBSIDIAN_SHARD, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_HOE, ExtendedItems.OBSIDIAN_SHARD, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_DAGGER, ExtendedItems.OBSIDIAN_SHARD);
		makeCrucibleRecipe(consumer, ExtendedItems.TAURETHRIM_SPEAR, ExtendedItems.OBSIDIAN_SHARD);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_HELMET, ()->Items.GOLD_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE, ()->Items.GOLD_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS, ()->Items.GOLD_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BOOTS, ()->Items.GOLD_INGOT, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_SWORD, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_SHOVEL, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_PICKAXE, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_AXE, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_HOE, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_HELMET, LOTRItems.ORC_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_CHESTPLATE, LOTRItems.ORC_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_LEGGINGS, LOTRItems.ORC_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_BOOTS, LOTRItems.ORC_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_SPEAR, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_DAGGER, LOTRItems.ORC_STEEL_INGOT);

		makeCrucibleRecipe(consumer, ExtendedItems.ANGMAR_WARG_ARMOR, LOTRItems.ORC_STEEL_INGOT, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_WARG_ARMOR, LOTRItems.ORC_STEEL_INGOT, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.ISENGARD_WARG_ARMOR, LOTRItems.URUK_STEEL_INGOT, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.MAIL_WARG_ARMOR, ()->Items.IRON_INGOT, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.MORDOR_WARG_ARMOR, LOTRItems.ORC_STEEL_INGOT, 6);
		makeCrucibleRecipe(consumer, ExtendedItems.LEATHER_WARG_ARMOR, ()->Items.LEATHER, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.DWARVEN_BOAR_ARMOR, LOTRItems.DWARVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.BLUE_DWARVEN_BOAR_ARMOR, LOTRItems.DWARVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.LEATHER_BOAR_ARMOR, ()->Items.LEATHER, 6);

		makeCrucibleRecipe(consumer, ExtendedItems.ORC_BOW, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_BOW, LOTRItems.URUK_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.DUNEDAIN_BOW, ExtendedItems.BOWSTRING, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.LINDON_BOW, ExtendedItems.BOWSTRING, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.LONGBOW, ExtendedItems.BOWSTRING, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.RANGER_BOW, ExtendedItems.BOWSTRING, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.RIVENDELL_BOW, ExtendedItems.BOWSTRING, 3);

		makeCrucibleRecipe(consumer, ExtendedItems.BLUE_DWARVEN_SHIELD, LOTRItems.DWARVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.BREE_SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.DUNEDAIN_SHIELD, ()->Items.IRON_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_SHIELD, LOTRItems.URUK_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.IMLADRIS_GUARD_SHIELD, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.LINDON_SHIELD, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.EREGION_SHIELD, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.RIVENDELL_SHIELD, LOTRItems.ELVEN_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.URUK_SHIELD, LOTRItems.URUK_STEEL_INGOT);

		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_SWORD, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_SHOVEL, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_PICKAXE, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_AXE, LOTRItems.ORC_STEEL_INGOT, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_HOE, LOTRItems.ORC_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_HELMET, LOTRItems.ORC_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_CHESTPLATE, LOTRItems.ORC_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_LEGGINGS, LOTRItems.ORC_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_BOOTS, LOTRItems.ORC_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_SPEAR, LOTRItems.ORC_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.DOL_GULDUR_DAGGER, LOTRItems.ORC_STEEL_INGOT);

		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_CLEAVER, LOTRItems.URUK_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_HELMET, LOTRItems.URUK_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_CHESTPLATE, LOTRItems.URUK_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_LEGGINGS, LOTRItems.URUK_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_BOOTS, LOTRItems.URUK_STEEL_INGOT, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_SPEAR, LOTRItems.URUK_STEEL_INGOT);
		makeCrucibleRecipe(consumer, ExtendedItems.GUNDABAD_URUK_DAGGER, LOTRItems.URUK_STEEL_INGOT);

		makeCrucibleRecipe(consumer, ExtendedItems.EREGION_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.EREGION_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.EREGION_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.EREGION_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.IMLADRIS_GUARD_HELMET, LOTRItems.ELVEN_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.IMLADRIS_GUARD_CHESTPLATE, LOTRItems.ELVEN_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.IMLADRIS_GUARD_LEGGINGS, LOTRItems.ELVEN_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.IMLADRIS_GUARD_BOOTS, LOTRItems.ELVEN_STEEL_INGOT, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.GALVORN_HELMET, ExtendedItems.GALVORN_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.GALVORN_CHESTPLATE, ExtendedItems.GALVORN_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.GALVORN_LEGGINGS, ExtendedItems.GALVORN_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.GALVORN_BOOTS, ExtendedItems.GALVORN_INGOT, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.MORGUL_BLADE, ExtendedItems.MORGUL_STEEL_INGOT, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.MORGUL_VASSAL_HELMET, ExtendedItems.MORGUL_STEEL_INGOT, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.MORGUL_VASSAL_CHESTPLATE, ExtendedItems.MORGUL_STEEL_INGOT, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.MORGUL_VASSAL_LEGGINGS, ExtendedItems.MORGUL_STEEL_INGOT, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.MORGUL_VASSAL_BOOTS, ExtendedItems.MORGUL_STEEL_INGOT, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.BRONZE_CROSSBOW, LOTRItems.BRONZE_INGOT, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.IRON_CROSSBOW, ()->Items.IRON_INGOT, 4);
		makeCrucibleRecipe(consumer, ExtendedItems.URUK_CROSSBOW, LOTRItems.URUK_STEEL_INGOT, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.BLACK_WARG_HELMET, ExtendedItems.BLACK_FUR, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.BLACK_WARG_CHESTPLATE, ExtendedItems.BLACK_FUR, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.BLACK_WARG_LEGGINGS, ExtendedItems.BLACK_FUR, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.BLACK_WARG_BOOTS, ExtendedItems.BLACK_FUR, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.BROWN_WARG_HELMET, LOTRItems.FUR, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.BROWN_WARG_CHESTPLATE, LOTRItems.FUR, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.BROWN_WARG_LEGGINGS, LOTRItems.FUR, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.BROWN_WARG_BOOTS, LOTRItems.FUR, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.GRAY_WARG_HELMET, ExtendedItems.GRAY_FUR, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.GRAY_WARG_CHESTPLATE, ExtendedItems.GRAY_FUR, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.GRAY_WARG_LEGGINGS, ExtendedItems.GRAY_FUR, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.GRAY_WARG_BOOTS, ExtendedItems.GRAY_FUR, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_WARG_HELMET, ExtendedItems.SILVER_FUR, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_WARG_CHESTPLATE, ExtendedItems.SILVER_FUR, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_WARG_LEGGINGS, ExtendedItems.SILVER_FUR, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.SILVER_WARG_BOOTS, ExtendedItems.SILVER_FUR, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.WHITE_WARG_HELMET, ExtendedItems.WHITE_FUR, 5);
		makeCrucibleRecipe(consumer, ExtendedItems.WHITE_WARG_CHESTPLATE, ExtendedItems.WHITE_FUR, 8);
		makeCrucibleRecipe(consumer, ExtendedItems.WHITE_WARG_LEGGINGS, ExtendedItems.WHITE_FUR, 7);
		makeCrucibleRecipe(consumer, ExtendedItems.WHITE_WARG_BOOTS, ExtendedItems.WHITE_FUR, 4);

		makeCrucibleRecipe(consumer, ExtendedItems.BONE_SWORD, ()->Items.BONE, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BONE_SHOVEL, ()->Items.BONE);
		makeCrucibleRecipe(consumer, ExtendedItems.BONE_PICKAXE, ()->Items.BONE, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.BONE_AXE, ()->Items.BONE, 3);
		makeCrucibleRecipe(consumer, ExtendedItems.BONE_HOE, ()->Items.BONE, 2);
		makeCrucibleRecipe(consumer, ExtendedItems.BONE_SPEAR, ()->Items.BONE);
		makeCrucibleRecipe(consumer, ExtendedItems.BONE_DAGGER, ()->Items.BONE);
	}

	private void buildOtherModsRecipes(Consumer<IFinishedRecipe> consumer) {

	}
	@Override
	public String getName() {
		return "Renewed Extended Crucible Recipe Provider";
	}

	@Override
	public void checkForMissingRecipes() {
		// Can't melt special uncraftables
		GENERATEDRECIPES.add(Items.ELYTRA.getRegistryName().getPath());
		GENERATEDRECIPES.add(Items.TRIDENT.getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.SMOKING_PIPE.get().getRegistryName().getPath());
		// Can't melt wooden/burnable items
		GENERATEDRECIPES.add(Items.WOODEN_AXE.getRegistryName().getPath());
		GENERATEDRECIPES.add(Items.WOODEN_PICKAXE.getRegistryName().getPath());
		GENERATEDRECIPES.add(Items.WOODEN_HOE.getRegistryName().getPath());
		GENERATEDRECIPES.add(Items.WOODEN_SWORD.getRegistryName().getPath());
		GENERATEDRECIPES.add(Items.WOODEN_SHOVEL.getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.WOODEN_DAGGER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.MALLORN_AXE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.MALLORN_PICKAXE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.MALLORN_HOE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.MALLORN_SHOVEL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.MALLORN_SWORD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.DUNLENDING_CLUB.get().getRegistryName().getPath());
		// Can't melt plates (clay based) (or wooden)
		GENERATEDRECIPES.add(LOTRItems.WOODEN_PLATE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.FINE_PLATE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.STONEWARE_PLATE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(LOTRItems.CLAY_PLATE.get().getRegistryName().getPath());
		// Can't melt story items
		GENERATEDRECIPES.add(ExtendedItems.ANDURIL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GLAMDRING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STAFF_GANDALF_GRAY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STAFF_GANDALF_WHITE.get().getRegistryName().getPath());

		for(Entry<RegistryKey<Item>, Item> entry : ForgeRegistries.ITEMS.getEntries()) {
			if(entry.getValue().getRegistryName().getNamespace().equals("lotrfa")) {
				continue;
			}
			if(entry.getValue().getRegistryName().getPath().contains("netherite")) {
				continue;
			}
			if(entry.getValue() instanceof LOTRArmorItem) {
				if(!GENERATEDRECIPES.contains(entry.getValue().getRegistryName().getPath())) {
					System.out.println("MISSING ARMOR RECIPE FOR: "+entry.getValue().getRegistryName().toString());
				}
			}
			if(entry.getValue() instanceof SwordItem) {
				if(!GENERATEDRECIPES.contains(entry.getValue().getRegistryName().getPath())) {
					System.out.println("MISSING SWORD RECIPE FOR: "+entry.getValue().getRegistryName().toString());
				}
			}
			if(entry.getValue() instanceof ToolItem) {
				if(!GENERATEDRECIPES.contains(entry.getValue().getRegistryName().getPath())) {
					System.out.println("MISSING TOOL RECIPE FOR: "+entry.getValue().getRegistryName().toString());
				}
			}
			if(entry.getValue() instanceof ExtendedThrowableAxeItem) {
				if(!GENERATEDRECIPES.contains(entry.getValue().getRegistryName().getPath())) {
					System.out.println("MISSING TOOL RECIPE FOR: "+entry.getValue().getRegistryName().toString());
				}
			}
			if(entry.getValue().getItem().canBeDepleted()) {
				if(!GENERATEDRECIPES.contains(entry.getValue().getRegistryName().getPath())) {
					System.out.println("MISSING DAMAGABLE RECIPE FOR: "+entry.getValue().getRegistryName().toString());
				}
			}
		}
	}
}