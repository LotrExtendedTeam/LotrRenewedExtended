package com.github.maximuslotro.lotrrextended.common.datagen.crafting;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedAlloyCookingRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedCustomRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedDyeableRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapelessRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedVesselBrewingRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeSerializers;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.ExtendedRecipes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRTags;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCraftingRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		buildRecipes(consumer);
		addMissingRenewedRecipes(consumer);
		addMissingVanillaRecipes(consumer);
		buildVanillaRecipeOverrides(consumer);
		buildCookingRecipes(consumer);
		makeDrinkRecipes(consumer);
		buildStoneGrinderRecipes(consumer);
		buildStonecutterRecipes(consumer);
		buildSmeltingRecipes(consumer);
		buildSmithingRecipes(consumer);
		addOverrides();
		checkForMissingRecipes();
	}

	private void buildRecipes(Consumer<IFinishedRecipe> consumer) {
		//Beds
		makeBedRecipe(consumer, ExtendedBlocks.STRAW_BED, Items.WHEAT, null);
		makeBedRecipeTag(consumer, ExtendedBlocks.ORC_BED, ExtendedTags.Items.WOOL_BLOCKS, LOTRRecipes.ANY_ORC);
		makeBedRecipe(consumer, ExtendedBlocks.BLACK_FUR_BED, ExtendedItems.BLACK_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.FUR_BED, LOTRItems.FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.GRAY_FUR_BED, ExtendedItems.GRAY_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.SILVER_FUR_BED, ExtendedItems.SILVER_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.WHITE_FUR_BED, ExtendedItems.WHITE_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.DWARVEN_BED, ExtendedTags.Items.WOOL_BLOCKS, LOTRRecipes.ANY_DWARVEN);
		makeBedRecipe(consumer, ExtendedBlocks.HIGH_ELVEN_BED, ExtendedTags.Items.WOOL_BLOCKS, LOTRRecipes.ANY_HIGH_ELVEN);
		makeCarpetRecipe(consumer, ExtendedBlocks.BLACK_FUR_CARPET, ExtendedItems.BLACK_FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedBlocks.BROWN_FUR_CARPET, LOTRItems.FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedBlocks.GRAY_FUR_CARPET, ExtendedItems.GRAY_FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedBlocks.SILVER_FUR_CARPET, ExtendedItems.SILVER_FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedBlocks.WHITE_FUR_CARPET, ExtendedItems.WHITE_FUR, LOTRRecipes.ANY_ORC);
		GENERATEDRECIPES.add(ExtendedItems.BEDROLL.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BEDROLL.get())
		.pattern("LLF")
		.define('L', Ingredient.of(Tags.Items.LEATHER))
		.define('F', Ingredient.of(ExtendedTags.Items.FURS))
		.unlockedBy("has_"+LOTRItems.FUR.get().getRegistryName().getPath(), has(ExtendedTags.Items.FURS))
		.unlockedBy("has_"+ExtendedItems.BEDROLL.get().getRegistryName().getPath(), has(ExtendedItems.BEDROLL.get()))
		.save(consumer, lotrLoc(ExtendedItems.BEDROLL.get().getRegistryName().getPath()));
		/////////////////WOOD
		//CHESTNUT
		makeBeamSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_BEAM,
			ExtendedBlocks.CHESTNUT_BEAM_SLAB, ExtendedItems.CHESTNUT_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.CHESTNUT_BOAT, ExtendedItems.CHESTNUT_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.CHESTNUT_BRANCH, ExtendedItems.CHESTNUT_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.CHESTNUT_BUTTON, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.CHESTNUT_DOOR, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedBlocks.CHESTNUT_FENCE, ExtendedBlocks.CHESTNUT_FENCE_GATE,
			ExtendedItems.CHESTNUT_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_LOG_SLAB, ExtendedItems.CHESTNUT_LOG, null);
		makeWoodenPressurePlateRecipe(consumer, ExtendedBlocks.CHESTNUT_PRESSURE_PLATE, ExtendedItems.CHESTNUT_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_STAIRS, ExtendedBlocks.CHESTNUT_SLAB, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedBlocks.CHESTNUT_TRAPDOOR, ExtendedItems.CHESTNUT_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_WOOD_STAIRS, ExtendedBlocks.CHESTNUT_WOOD_SLAB, ExtendedItems.CHESTNUT_WOOD, null);
		makeSignRecipe(consumer, ExtendedBlocks.CHESTNUT_SIGN, ExtendedBlocks.CHESTNUT_WALL_SIGN, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedBlocks.CHESTNUT_WOOD, ExtendedItems.CHESTNUT_LOG, null);
		makePlanksRecipe(consumer, ExtendedBlocks.CHESTNUT_PLANKS, ExtendedBlocks.CHESTNUT_LOG,
			ExtendedTags.Items.CHESTNUT_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.CHESTNUT_PLANKS, ExtendedItems.CHESTNUT_LOG_SLAB,
			ExtendedItems.STRIPPED_CHESTNUT_LOG_SLAB, ExtendedItems.CHESTNUT_BRANCH, ExtendedItems.STRIPPED_CHESTNUT_BRANCH,
			ExtendedItems.CHESTNUT_WOOD_SLAB, ExtendedItems.STRIPPED_CHESTNUT_WOOD_SLAB,
			ExtendedItems.CHESTNUT_WOOD_STAIRS, ExtendedItems.STRIPPED_CHESTNUT_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH, ExtendedItems.STRIPPED_CHESTNUT_WOOD, null);
		makeSlabRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB, ExtendedItems.STRIPPED_CHESTNUT_LOG, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB,
			ExtendedItems.STRIPPED_CHESTNUT_WOOD, null);
		makeWoodRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD, ExtendedItems.STRIPPED_CHESTNUT_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedBlocks.FALLEN_CHESTNUT_LEAVES, ExtendedItems.CHESTNUT_LEAVES, null);
		//BANANA
		makeBeamSlabRecipe(consumer, ExtendedBlocks.BANANA_BEAM,
			ExtendedBlocks.BANANA_BEAM_SLAB, ExtendedItems.BANANA_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.BANANA_BOAT, ExtendedItems.BANANA_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.BANANA_BRANCH, ExtendedItems.BANANA_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.BANANA_BUTTON, ExtendedItems.BANANA_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.BANANA_DOOR, ExtendedItems.BANANA_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedBlocks.BANANA_FENCE, ExtendedBlocks.BANANA_FENCE_GATE,
			ExtendedItems.BANANA_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedBlocks.BANANA_LOG_SLAB, ExtendedItems.BANANA_LOG, null);
		makeWoodenPressurePlateRecipe(consumer, ExtendedBlocks.BANANA_PRESSURE_PLATE, ExtendedItems.BANANA_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.BANANA_STAIRS, ExtendedBlocks.BANANA_SLAB, ExtendedItems.BANANA_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedBlocks.BANANA_TRAPDOOR, ExtendedItems.BANANA_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.BANANA_WOOD_STAIRS, ExtendedBlocks.BANANA_WOOD_SLAB, ExtendedItems.BANANA_WOOD, null);
		makeSignRecipe(consumer, ExtendedBlocks.BANANA_SIGN, ExtendedBlocks.BANANA_WALL_SIGN, ExtendedItems.BANANA_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedBlocks.BANANA_WOOD, ExtendedItems.BANANA_LOG, null);
		makePlanksRecipe(consumer, ExtendedBlocks.BANANA_PLANKS, ExtendedBlocks.BANANA_LOG,
			ExtendedTags.Items.BANANA_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.BANANA_PLANKS, ExtendedItems.BANANA_LOG_SLAB,
			ExtendedItems.STRIPPED_BANANA_LOG_SLAB, ExtendedItems.BANANA_BRANCH, ExtendedItems.STRIPPED_BANANA_BRANCH,
			ExtendedItems.BANANA_WOOD_SLAB, ExtendedItems.STRIPPED_BANANA_WOOD_SLAB,
			ExtendedItems.BANANA_WOOD_STAIRS, ExtendedItems.STRIPPED_BANANA_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.STRIPPED_BANANA_BRANCH, ExtendedItems.STRIPPED_BANANA_WOOD, null);
		makeSlabRecipe(consumer, ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB, ExtendedItems.STRIPPED_BANANA_LOG, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.STRIPPED_BANANA_WOOD_STAIRS, ExtendedBlocks.STRIPPED_BANANA_WOOD_SLAB,
			ExtendedItems.STRIPPED_BANANA_WOOD, null);
		makeWoodRecipe(consumer, ExtendedBlocks.STRIPPED_BANANA_WOOD, ExtendedItems.STRIPPED_BANANA_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedBlocks.FALLEN_BANANA_LEAVES, ExtendedItems.BANANA_LEAVES, null);
		//PLUM
		makeBeamSlabRecipe(consumer, ExtendedBlocks.PLUM_BEAM,
			ExtendedBlocks.PLUM_BEAM_SLAB, ExtendedItems.PLUM_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.PLUM_BOAT, ExtendedItems.PLUM_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.PLUM_BRANCH, ExtendedItems.PLUM_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.PLUM_BUTTON, ExtendedItems.PLUM_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.PLUM_DOOR, ExtendedItems.PLUM_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedBlocks.PLUM_FENCE, ExtendedBlocks.PLUM_FENCE_GATE,
			ExtendedItems.PLUM_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedBlocks.PLUM_LOG_SLAB, ExtendedItems.PLUM_LOG, null);
		makeWoodenPressurePlateRecipe(consumer, ExtendedBlocks.PLUM_PRESSURE_PLATE, ExtendedItems.PLUM_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.PLUM_STAIRS, ExtendedBlocks.PLUM_SLAB, ExtendedItems.PLUM_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedBlocks.PLUM_TRAPDOOR, ExtendedItems.PLUM_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.PLUM_WOOD_STAIRS, ExtendedBlocks.PLUM_WOOD_SLAB, ExtendedItems.PLUM_WOOD, null);
		makeSignRecipe(consumer, ExtendedBlocks.PLUM_SIGN, ExtendedBlocks.PLUM_WALL_SIGN, ExtendedItems.PLUM_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedBlocks.PLUM_WOOD, ExtendedItems.PLUM_LOG, null);
		makePlanksRecipe(consumer, ExtendedBlocks.PLUM_PLANKS, ExtendedBlocks.PLUM_LOG,
			ExtendedTags.Items.PLUM_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.PLUM_PLANKS, ExtendedItems.PLUM_LOG_SLAB,
			ExtendedItems.STRIPPED_PLUM_LOG_SLAB, ExtendedItems.PLUM_BRANCH, ExtendedItems.STRIPPED_PLUM_BRANCH,
			ExtendedItems.PLUM_WOOD_SLAB, ExtendedItems.STRIPPED_PLUM_WOOD_SLAB,
			ExtendedItems.PLUM_WOOD_STAIRS, ExtendedItems.STRIPPED_PLUM_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.STRIPPED_PLUM_BRANCH, ExtendedItems.STRIPPED_PLUM_WOOD, null);
		makeSlabRecipe(consumer, ExtendedBlocks.STRIPPED_PLUM_LOG_SLAB, ExtendedItems.STRIPPED_PLUM_LOG, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.STRIPPED_PLUM_WOOD_STAIRS, ExtendedBlocks.STRIPPED_PLUM_WOOD_SLAB,
			ExtendedItems.STRIPPED_PLUM_WOOD, null);
		makeWoodRecipe(consumer, ExtendedBlocks.STRIPPED_PLUM_WOOD, ExtendedItems.STRIPPED_PLUM_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedBlocks.FALLEN_PLUM_LEAVES, ExtendedItems.PLUM_LEAVES, null);
		//WILLOW
		makeBeamSlabRecipe(consumer, ExtendedBlocks.WILLOW_BEAM,
			ExtendedBlocks.WILLOW_BEAM_SLAB, ExtendedItems.WILLOW_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.WILLOW_BOAT, ExtendedItems.WILLOW_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.WILLOW_BRANCH, ExtendedItems.WILLOW_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.WILLOW_BUTTON, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.WILLOW_DOOR, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedBlocks.WILLOW_FENCE, ExtendedBlocks.WILLOW_FENCE_GATE,
			ExtendedItems.WILLOW_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedBlocks.WILLOW_LOG_SLAB, ExtendedItems.WILLOW_LOG, null);
		makeWoodenPressurePlateRecipe(consumer, ExtendedBlocks.WILLOW_PRESSURE_PLATE, ExtendedItems.WILLOW_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.WILLOW_STAIRS, ExtendedBlocks.WILLOW_SLAB, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedBlocks.WILLOW_TRAPDOOR, ExtendedItems.WILLOW_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.WILLOW_WOOD_STAIRS, ExtendedBlocks.WILLOW_WOOD_SLAB, ExtendedItems.WILLOW_WOOD, null);
		makeSignRecipe(consumer, ExtendedBlocks.WILLOW_SIGN, ExtendedBlocks.WILLOW_WALL_SIGN, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedBlocks.WILLOW_WOOD, ExtendedItems.WILLOW_LOG, null);
		makePlanksRecipe(consumer, ExtendedBlocks.WILLOW_PLANKS, ExtendedBlocks.WILLOW_LOG,
			ExtendedTags.Items.WILLOW_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.WILLOW_PLANKS, ExtendedItems.WILLOW_LOG_SLAB,
			ExtendedItems.STRIPPED_WILLOW_LOG_SLAB, ExtendedItems.WILLOW_BRANCH, ExtendedItems.STRIPPED_WILLOW_BRANCH,
			ExtendedItems.WILLOW_WOOD_SLAB, ExtendedItems.STRIPPED_WILLOW_WOOD_SLAB,
			ExtendedItems.WILLOW_WOOD_STAIRS, ExtendedItems.STRIPPED_WILLOW_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedBlocks.STRIPPED_WILLOW_BRANCH, ExtendedItems.STRIPPED_WILLOW_WOOD, null);
		makeSlabRecipe(consumer, ExtendedBlocks.STRIPPED_WILLOW_LOG_SLAB, ExtendedItems.STRIPPED_WILLOW_LOG, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.STRIPPED_WILLOW_WOOD_STAIRS, ExtendedBlocks.STRIPPED_WILLOW_WOOD_SLAB,
			ExtendedItems.STRIPPED_WILLOW_WOOD, null);
		makeWoodRecipe(consumer, ExtendedBlocks.STRIPPED_WILLOW_WOOD, ExtendedItems.STRIPPED_WILLOW_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedBlocks.FALLEN_WILLOW_LEAVES, ExtendedItems.WILLOW_LEAVES, null);
		//ROHAN
		GENERATEDRECIPES.add(ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(LOGS))
		.unlockedBy("has_logs", has(LOGS))
		.unlockedBy("has_"+ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ROHAN_BEAM.get()))
		.save(consumer, lotrLoc(ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORNATE_ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(ExtendedItems.ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ROHAN_BEAM.get()))
		.save(consumer, lotrLoc(ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.GOLDEN_ROHAN_BEAM.get(), LOTRRecipes.ROHAN_CRAFTING)
		.pattern(" G ")
		.pattern("GXG")
		.pattern(" G ")
		.define('X', Ingredient.of(ExtendedItems.ROHAN_BEAM.get()))
		.define('G', Ingredient.of(Items.GOLD_NUGGET))
		.unlockedBy("has_"+ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.GOLDEN_ROHAN_BEAM.get()))
		.save(consumer, lotrLoc(ExtendedItems.GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get()))
		.save(consumer, lotrLoc(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath()));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get(), LOTRRecipes.ROHAN_CRAFTING)
		.pattern(" G ")
		.pattern("GXG")
		.pattern(" G ")
		.define('X', Ingredient.of(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.define('G', Ingredient.of(Items.GOLD_NUGGET))
		.unlockedBy("has_"+ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get()))
		.save(consumer, lotrLoc(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath()+"_from_ornate"));
		//CRAFTING TABLES
		makeCraftingTableRecipe(consumer, ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE,
			()->ExtendedBlocks.TAURETHRIM_BRICK.get().asItem(), "_with_bricks", ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE,
			()->ExtendedBlocks.MUD_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE,
			()->ExtendedBlocks.DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.RHUNIC_CRAFTING_TABLE,
			()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE,
                Blocks.COBBLESTONE::asItem, ExtendedRecipes.HALF_TROLL_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.NORTHERN_ORC_CRAFTING_TABLE,
                Blocks.COBBLESTONE::asItem, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.MORWAITH_CRAFTING_TABLE,
                Blocks.CLAY::asItem, ExtendedRecipes.MORWAITH_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CONQUEST_TABLE.get())
		.pattern("XXX")
		.pattern("YYY")
		.pattern("ZZZ")
		.define('X', Ingredient.of(Items.PAPER))
		.define('Y', Ingredient.of(PLANKS))
		.define('Z', Ingredient.of(LOTRTags.Items.INGOTS_BRONZE))
		.unlockedBy("has_"+LOTRItems.BRONZE_INGOT.get().getRegistryName().getPath(), has(LOTRItems.BRONZE_INGOT.get()))
		.unlockedBy("has_"+ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath(), has(ExtendedItems.CONQUEST_TABLE.get()))
		.save(consumer, lotrLoc(ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.STONE_GRINDER.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.STONE_GRINDER.get())
		.pattern("CIC")
		.pattern("CSC")
		.pattern("CCC")
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('S', Ingredient.of(STICKS))
		.define('I', Ingredient.of(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_"+ExtendedItems.STONE_GRINDER.get().getRegistryName().getPath(), has(ExtendedItems.STONE_GRINDER.get()))
		.save(consumer, lotrLoc(ExtendedItems.STONE_GRINDER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.CRUCIBLE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CRUCIBLE.get())
		.pattern("I I")
		.pattern("SIS")
		.pattern("CCC")
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('S', Ingredient.of(STICKS))
		.define('I', Ingredient.of(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_"+ExtendedItems.CRUCIBLE.get().getRegistryName().getPath(), has(ExtendedItems.CRUCIBLE.get()))
		.save(consumer, lotrLoc(ExtendedItems.CRUCIBLE.get().getRegistryName().getPath()));
		//STORAGE BLOCKS AND ITEMS
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.AMBER, ExtendedBlocks.AMBER_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.AMETHYST, ExtendedBlocks.AMETHYST_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.TOPAZ, ExtendedBlocks.TOPAZ_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.SAPPHIRE, ExtendedBlocks.SAPPHIRE_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.RUBY, ExtendedBlocks.RUBY_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.DIAMOND, ExtendedBlocks.DIAMOND_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.OPAL, ExtendedBlocks.OPAL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.EMERALD, ExtendedBlocks.EMERALD_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.PEARL, ExtendedBlocks.PEARL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.CORAL, ExtendedBlocks.CORAL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.GALVORN_INGOT, ExtendedBlocks.GALVORN_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.BLACK_URUK_STEEL_INGOT, ExtendedBlocks.BLACK_URUK_STEEL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.GILDED_IRON_INGOT, ExtendedBlocks.GILDED_IRON_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.MORGUL_STEEL_INGOT, ExtendedBlocks.MORGUL_STEEL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.OBSIDIAN_SHARD, ()->Blocks.OBSIDIAN, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.BLACK_FUR, ExtendedBlocks.BLACK_PACKED_FUR, null);
		makeNuggetToIngotAndBackRecipe(consumer, LOTRItems.ORC_STEEL_INGOT, ExtendedItems.ORC_STEEL_NUGGET, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.BONE_BLOCK_STAIRS,
			ExtendedBlocks.BONE_BLOCK_SLAB,
			ExtendedBlocks.BONE_BLOCK_WALL,
                Blocks.BONE_BLOCK::asItem, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.BLACK_PACKED_FUR_STAIRS,
			ExtendedBlocks.BLACK_PACKED_FUR_SLAB,
			ExtendedBlocks.BLACK_PACKED_FUR_WALL,
			()->ExtendedBlocks.BLACK_PACKED_FUR.get().asItem(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.GRAY_FUR, ExtendedBlocks.GRAY_PACKED_FUR, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.GRAY_PACKED_FUR_STAIRS,
			ExtendedBlocks.GRAY_PACKED_FUR_SLAB,
			ExtendedBlocks.GRAY_PACKED_FUR_WALL,
			()->ExtendedBlocks.GRAY_PACKED_FUR.get().asItem(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, LOTRItems.FUR, ExtendedBlocks.BROWN_PACKED_FUR, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.BROWN_PACKED_FUR_STAIRS,
			ExtendedBlocks.BROWN_PACKED_FUR_SLAB,
			ExtendedBlocks.BROWN_PACKED_FUR_WALL,
			()->ExtendedBlocks.BROWN_PACKED_FUR.get().asItem(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.SILVER_FUR, ExtendedBlocks.SILVER_PACKED_FUR, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.SILVER_PACKED_FUR_STAIRS,
			ExtendedBlocks.SILVER_PACKED_FUR_SLAB,
			ExtendedBlocks.SILVER_PACKED_FUR_WALL,
			()->ExtendedBlocks.SILVER_PACKED_FUR.get().asItem(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.WHITE_FUR, ExtendedBlocks.WHITE_PACKED_FUR, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.WHITE_PACKED_FUR_STAIRS,
			ExtendedBlocks.WHITE_PACKED_FUR_SLAB,
			ExtendedBlocks.WHITE_PACKED_FUR_WALL,
			()->ExtendedBlocks.WHITE_PACKED_FUR.get().asItem(), null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.BLACK_FUR, ExtendedBlocks.BLACK_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.GRAY_FUR, ExtendedBlocks.GRAY_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.SILVER_FUR, ExtendedBlocks.SILVER_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.WHITE_FUR, ExtendedBlocks.WHITE_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, LOTRItems.FUR, LOTRBlocks.FUR_BUNDLE, null);

		// Hobbit pancakes
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE.get(), 1, LOTRRecipes.HOBBIT_CRAFTING)
		.pattern("EW")
		.pattern("M ")
		.define('E', Items.EGG)
		.define('M', Items.MILK_BUCKET)
		.define('W', Items.WHEAT)
		.unlockedBy("has_eggs", has(Items.EGG))
		.unlockedBy("has_wheat", has(Items.WHEAT))
		.unlockedBy("has_milk", has(Items.MILK_BUCKET))
		.save(consumer, lotrLoc(ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().getPath()));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE.get(), 2, LOTRRecipes.HOBBIT_CRAFTING)
		.pattern("EW")
		.pattern("M ")
		.define('E', ExtendedItems.DUCK_EGG.get())
		.define('M', Items.MILK_BUCKET)
		.define('W', Items.WHEAT)
		.unlockedBy("has_duck_egg", has(ExtendedItems.DUCK_EGG.get()))
		.unlockedBy("has_milk", has(Items.MILK_BUCKET))
		.save(consumer, lotrLoc(ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().getPath()+"_with_duck_egg"));
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get(), 8, LOTRRecipes.HOBBIT_CRAFTING)
		.define('X', ExtendedItems.HOBBIT_PANCAKE.get())
		.define('Y', LOTRItems.MAPLE_SYRUP.get())
		.pattern("XXX")
		.pattern("XYX")
		.pattern("XXX")
		.unlockedBy("has_" + ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_PANCAKE.get()))
		.unlockedBy("has_" + LOTRItems.MAPLE_SYRUP.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_PANCAKE.get()))
		.save(consumer, lotrLoc(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get(), 8, LOTRRecipes.HOBBIT_CRAFTING)
		.define('X', ExtendedItems.HOBBIT_PANCAKE_SYRUP.get())
		.define('Y', ExtendedItems.BUTTER.get())
		.pattern("XXX")
		.pattern("XYX")
		.pattern("XXX")
		.unlockedBy("has_" + ExtendedItems.HOBBIT_PANCAKE_SYRUP.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get()))
		.unlockedBy("has_" + ExtendedItems.BUTTER.get().getRegistryName().getPath(), has(ExtendedItems.BUTTER.get()))
		.save(consumer, lotrLoc(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get().getRegistryName().getPath()));

		//MORGUL
		makeArmorRecipe(consumer, ExtendedItems.MORGUL_HELMET,
			ExtendedItems.MORGUL_CHESTPLATE,
			ExtendedItems.MORGUL_LEGGINGS,
			ExtendedItems.MORGUL_BOOTS,
			ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		makeSwordRecipe(consumer, ExtendedItems.MORGUL_BLADE, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_BRAZIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.MORGUL_BRAZIER.get(), LOTRRecipes.ANY_MORGUL)
		.pattern("YYY")
		.pattern("Y#Y")
		.pattern("YYY")
		.define('#', Ingredient.of(ExtendedBlocks.MORGUL_TORCH.get()))
		.define('Y', Ingredient.of(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.unlockedBy("has_"+ExtendedItems.MORGUL_BRAZIER.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_BRAZIER.get()))
		.unlockedBy("has_"+ExtendedBlocks.MORGUL_TORCH.get().getRegistryName().getPath(), has(ExtendedBlocks.MORGUL_TORCH.get()))
		.unlockedBy("has_orc_steel_nugget", has(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.save(consumer, lotrLoc(ExtendedItems.MORGUL_BRAZIER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.HIGH_ELVEN_LANTERN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HIGH_ELVEN_LANTERN.get(), LOTRRecipes.ANY_HIGH_ELVEN)
		.pattern("YYY")
		.pattern("Y#Y")
		.pattern("YYY")
		.define('#', Ingredient.of(LOTRBlocks.HIGH_ELVEN_TORCH.get()))
		.define('Y', Ingredient.of(ExtendedTags.Items.SILVER_NUGGET))
		.unlockedBy("has_"+ExtendedItems.HIGH_ELVEN_LANTERN.get().getRegistryName().getPath(), has(ExtendedItems.HIGH_ELVEN_LANTERN.get()))
		.unlockedBy("has_"+LOTRBlocks.HIGH_ELVEN_TORCH.get().getRegistryName().getPath(), has(LOTRBlocks.HIGH_ELVEN_TORCH.get()))
		.unlockedBy("has_silver_nugget", has(ExtendedTags.Items.SILVER_NUGGET))
		.save(consumer, lotrLoc(ExtendedItems.HIGH_ELVEN_LANTERN.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.MORGUL_CHANDELIER.get(), LOTRRecipes.ANY_MORGUL)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.MORGUL_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.MORGUL_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_CHANDELIER.get()))
		.unlockedBy("has_"+ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.MORGUL_CHANDELIER.get().getRegistryName().getPath()));
		makeTorchRecipe(consumer, ExtendedBlocks.MORGUL_TORCH, LOTRItems.GULDURIL, LOTRRecipes.ANY_MORGUL);
		makeDoorRecipe(consumer, ExtendedItems.ORC_STEEL_DOOR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeMetalTrapdoorRecipe(consumer, ExtendedBlocks.ORC_STEEL_TRAPDOOR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeChainRecipe(consumer, ExtendedBlocks.ORC_CHAIN, LOTRItems.ORC_STEEL_INGOT, ExtendedItems.ORC_STEEL_NUGGET, LOTRRecipes.ANY_ORC);
		makeChainRecipe(consumer, ExtendedBlocks.HIGH_ELVEN_CHAIN, LOTRItems.SILVER_INGOT, LOTRItems.SILVER_NUGGET, LOTRRecipes.ANY_HIGH_ELVEN);
		//DOL GULDUR
		makeArmorRecipe(consumer, ExtendedItems.DOL_GULDUR_HELMET,
			ExtendedItems.DOL_GULDUR_CHESTPLATE,
			ExtendedItems.DOL_GULDUR_LEGGINGS,
			ExtendedItems.DOL_GULDUR_BOOTS,
			LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeToolsRecipe(consumer, ExtendedItems.DOL_GULDUR_PICKAXE,
			ExtendedItems.DOL_GULDUR_SHOVEL,
			ExtendedItems.DOL_GULDUR_AXE,
			ExtendedItems.DOL_GULDUR_HOE,
			LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeSwordRecipe(consumer, ExtendedItems.DOL_GULDUR_SWORD, LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeSpearRecipe(consumer, ExtendedItems.DOL_GULDUR_SPEAR, LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeDaggerRecipe(consumer, ExtendedItems.DOL_GULDUR_DAGGER, LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK,
			ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS,
			ExtendedBlocks.DOL_GULDUR_BRICK_SLAB,
			ExtendedBlocks.DOL_GULDUR_BRICK_WALL,
                Blocks.STONE::asItem, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.DOL_GULDUR_PILLAR,
			ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB,
                Blocks.STONE::asItem, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedBlocks.CARVED_DOL_GULDUR_BRICK,
			ExtendedBlocks.DOL_GULDUR_BRICK, ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK,
			()->ExtendedBlocks.DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS,
			ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB,
			ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL,
			()->ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK,
			()->ExtendedBlocks.DOL_GULDUR_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS,
			ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB,
			ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL,
			()->ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		//ANGMAR
		makeArmorRecipe(consumer, ExtendedItems.ANGMAR_HELMET,
			ExtendedItems.ANGMAR_CHESTPLATE,
			ExtendedItems.ANGMAR_LEGGINGS,
			ExtendedItems.ANGMAR_BOOTS,
			LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		makeToolsRecipe(consumer, ExtendedItems.ANGMAR_PICKAXE,
			ExtendedItems.ANGMAR_SHOVEL,
			ExtendedItems.ANGMAR_AXE,
			ExtendedItems.ANGMAR_HOE,
			LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		makeSwordRecipe(consumer, ExtendedItems.ANGMAR_SWORD, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		makeSpearRecipe(consumer, ExtendedItems.ANGMAR_SPEAR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		makeDaggerRecipe(consumer, ExtendedItems.ANGMAR_DAGGER, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.ORC_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORC_CHANDELIER.get(), LOTRRecipes.ANY_ORC)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.ORC_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.ORC_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.ORC_CHANDELIER.get()))
		.unlockedBy("has_"+LOTRItems.ORC_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.ORC_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.ORC_CHANDELIER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.URUK_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.URUK_CHANDELIER.get(), LOTRRecipes.URUK_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.URUK_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.URUK_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.URUK_CHANDELIER.get()))
		.unlockedBy("has_"+LOTRItems.URUK_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.URUK_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.URUK_CHANDELIER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DWARVEN_CHANDELIER.get(), LOTRRecipes.DWARVEN_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.DWARVEN_CHANDELIER.get()))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DWARVEN_CHANDELIER.get(), LOTRRecipes.DWARVEN_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.DWARVEN_TORCH.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.DWARVEN_CHANDELIER.get()))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath()+"_with_torch"));
		GENERATEDRECIPES.add(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get()))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.DWARVEN_TORCH.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath(), has(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get()))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, lotrLoc(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath()+"_with_torch"));
		makeToolsRecipe(consumer, ExtendedItems.BONE_PICKAXE,
			ExtendedItems.BONE_SHOVEL,
			ExtendedItems.BONE_AXE,
			ExtendedItems.BONE_HOE,
			Tags.Items.BONES, "bone", null);
		makeSwordRecipe(consumer, ExtendedItems.BONE_SWORD, Tags.Items.BONES, "bone", null);
		makeSpearRecipe(consumer, ExtendedItems.BONE_SPEAR, Tags.Items.BONES, "bone", null);
		makeDaggerRecipe(consumer, ExtendedItems.BONE_DAGGER, Tags.Items.BONES, "bone", null);
		// Gundabad Uruk
		makeArmorRecipe(consumer, ExtendedItems.GUNDABAD_URUK_HELMET,
			ExtendedItems.GUNDABAD_URUK_CHESTPLATE,
			ExtendedItems.GUNDABAD_URUK_LEGGINGS,
			ExtendedItems.GUNDABAD_URUK_BOOTS,
			LOTRItems.URUK_STEEL_INGOT, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeSwordRecipe(consumer, ExtendedItems.GUNDABAD_URUK_CLEAVER, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeSpearRecipe(consumer, ExtendedItems.GUNDABAD_URUK_SPEAR, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeDaggerRecipe(consumer, ExtendedItems.GUNDABAD_URUK_DAGGER, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		//WARG
		makeArmorRecipe(consumer, ExtendedItems.BLACK_WARG_CHESTPLATE,
			ExtendedItems.BLACK_WARG_LEGGINGS, ExtendedItems.BLACK_WARG_BOOTS,
			ExtendedItems.BLACK_FUR, LOTRRecipes.ANY_ORC);
		makeArmorRecipe(consumer, ExtendedItems.BROWN_WARG_CHESTPLATE,
			ExtendedItems.BROWN_WARG_LEGGINGS, ExtendedItems.BROWN_WARG_BOOTS,
			LOTRItems.FUR, LOTRRecipes.ANY_ORC);
		makeArmorRecipe(consumer, ExtendedItems.GRAY_WARG_CHESTPLATE,
			ExtendedItems.GRAY_WARG_LEGGINGS, ExtendedItems.GRAY_WARG_BOOTS,
			ExtendedItems.GRAY_FUR, LOTRRecipes.ANY_ORC);
		makeArmorRecipe(consumer, ExtendedItems.SILVER_WARG_CHESTPLATE,
			ExtendedItems.SILVER_WARG_LEGGINGS, ExtendedItems.SILVER_WARG_BOOTS,
			ExtendedItems.SILVER_FUR, LOTRRecipes.ANY_ORC);
		makeArmorRecipe(consumer, ExtendedItems.WHITE_WARG_CHESTPLATE,
			ExtendedItems.WHITE_WARG_LEGGINGS, ExtendedItems.WHITE_WARG_BOOTS,
			ExtendedItems.WHITE_FUR, LOTRRecipes.ANY_ORC);
		// GOBLIN/URUK
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.GOBLIN_BRICK,
			ExtendedBlocks.GOBLIN_BRICK_STAIRS,
			ExtendedBlocks.GOBLIN_BRICK_SLAB,
			ExtendedBlocks.GOBLIN_BRICK_WALL,
                Blocks.STONE::asItem, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.GOBLIN_PILLAR,
			ExtendedBlocks.GOBLIN_PILLAR_SLAB,
                Blocks.STONE::asItem, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		//TAURETHRIM
		makeArmorRecipe(consumer, ExtendedItems.TAURETHRIM_HELMET,
			ExtendedItems.TAURETHRIM_CHESTPLATE,
			ExtendedItems.TAURETHRIM_LEGGINGS,
			ExtendedItems.TAURETHRIM_BOOTS,
			LOTRItems.BRONZE_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get(), ExtendedRecipes.TAURETHRIM_CRAFTING)
		.pattern("Y")
		.pattern("#")
		.define('Y', Ingredient.of(LOTRItems.FLAME_OF_HARAD.get()))
		.define('#', Ingredient.of(ExtendedItems.TAURETHRIM_HELMET.get()))
		.unlockedBy("has_"+LOTRItems.FLAME_OF_HARAD.get().getRegistryName().getPath(), has(LOTRItems.FLAME_OF_HARAD.get()))
		.unlockedBy("has_"+ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath(), has(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get()))
		.save(consumer, lotrLoc(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath()));
		makeToolsRecipe(consumer, ExtendedItems.TAURETHRIM_PICKAXE,
			ExtendedItems.TAURETHRIM_SHOVEL,
			ExtendedItems.TAURETHRIM_AXE,
			ExtendedItems.TAURETHRIM_HOE,
			ExtendedItems.OBSIDIAN_SHARD, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeSpearRecipe(consumer, ExtendedItems.TAURETHRIM_SPEAR, ExtendedItems.OBSIDIAN_SHARD, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeDaggerRecipe(consumer, ExtendedItems.TAURETHRIM_DAGGER, ExtendedItems.OBSIDIAN_SHARD, ExtendedRecipes.TAURETHRIM_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.TAURETHRIM_SWORD.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.TAURETHRIM_SWORD.get(), ExtendedRecipes.TAURETHRIM_CRAFTING)
		.pattern("#Z#")
		.pattern("#Z#")
		.pattern(" Y ")
		.define('Z', PLANKS)
		.define('Y', STICKS)
		.define('#', Ingredient.of(ExtendedItems.OBSIDIAN_SHARD.get()))
		.unlockedBy("has_"+ExtendedItems.OBSIDIAN_SHARD.get().getRegistryName().getPath(), has(ExtendedItems.OBSIDIAN_SHARD.get()))
		.unlockedBy("has_"+ExtendedItems.TAURETHRIM_SWORD.get().getRegistryName().getPath(), has(ExtendedItems.TAURETHRIM_SWORD.get()))
		.save(consumer, lotrLoc(ExtendedItems.TAURETHRIM_SWORD.get().getRegistryName().getPath()));
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK,
			ExtendedBlocks.TAURETHRIM_BRICK_STAIRS,
			ExtendedBlocks.TAURETHRIM_BRICK_SLAB,
			ExtendedBlocks.TAURETHRIM_BRICK_WALL,
                Blocks.STONE::asItem, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.TAURETHRIM_PILLAR,
			ExtendedBlocks.TAURETHRIM_PILLAR_SLAB,
                Blocks.STONE::asItem, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK,
			()->ExtendedBlocks.TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS,
			ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB,
			ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL,
			()->ExtendedBlocks.MOSSY_TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK,
			()->ExtendedBlocks.TAURETHRIM_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS,
			ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB,
			ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL,
			()->ExtendedBlocks.CRACKED_TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK,
			ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS,
			ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB,
			ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL,
			()->Items.GOLD_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR,
			ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB,
			()->Items.GOLD_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK,
			ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS,
			ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB,
			ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL,
			()->ExtendedItems.OBSIDIAN_SHARD.get(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR,
			ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB,
			()->ExtendedItems.OBSIDIAN_SHARD.get(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		//RHUNIC
		GENERATEDRECIPES.add(ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_BRICK.get(), 4, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(Blocks.STONE.asItem()))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_BRICK.get()))
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE.asItem()))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath()+"_1"));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_BRICK.get(), 4, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(Blocks.SANDSTONE.asItem()))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_BRICK.get()))
		.unlockedBy("has_"+Blocks.SANDSTONE.getRegistryName().getPath(), has(Blocks.SANDSTONE.asItem()))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath()+"_2"));
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.RHUNIC_BRICK_SLAB,
			ExtendedBlocks.RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedBlocks.CARVED_RHUNIC_BRICK,
			ExtendedBlocks.RHUNIC_BRICK, ExtendedBlocks.RHUNIC_BRICK_SLAB, ExtendedRecipes.RHUNIC_CRAFTING);
		GENERATEDRECIPES.add(ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_PILLAR.get(), 3, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(Blocks.STONE.asItem()))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_PILLAR.get()))
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE.asItem()))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath()+"_1"));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_PILLAR.get(), 3, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(Blocks.SANDSTONE.asItem()))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_PILLAR.get()))
		.unlockedBy("has_"+Blocks.SANDSTONE.getRegistryName().getPath(), has(Blocks.SANDSTONE.asItem()))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath()+"_2"));
		makeSlabRecipe(consumer, ExtendedBlocks.RHUNIC_PILLAR_SLAB,
			()->ExtendedBlocks.RHUNIC_PILLAR.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedBlocks.CRACKED_RHUNIC_BRICK,
			()->ExtendedBlocks.RHUNIC_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.CRACKED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		GENERATEDRECIPES.add(ExtendedBlocks.FLOWERY_RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(ExtendedBlocks.FLOWERY_RHUNIC_BRICK.get(), ExtendedRecipes.RHUNIC_CRAFTING)
		.requires(ExtendedBlocks.RHUNIC_BRICK.get())
		.requires(LOTRItems.ORANGE_CHRYSANTHEMUM.get())
		.unlockedBy("has_"+ExtendedBlocks.FLOWERY_RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.FLOWERY_RHUNIC_BRICK.get()))
		.unlockedBy("has_"+LOTRItems.ORANGE_CHRYSANTHEMUM.get().getRegistryName().getPath(), has(LOTRItems.ORANGE_CHRYSANTHEMUM.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.FLOWERY_RHUNIC_BRICK.get().getRegistryName().getPath()));
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.FLOWERY_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_RHUNIC_BRICK,
			()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.MOSSY_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK,
			ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.RED_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.RED_RHUNIC_BRICK_WALL,
			()->LOTRBlocks.RED_ROCK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedBlocks.CARVED_RED_RHUNIC_BRICK,
			ExtendedBlocks.RED_RHUNIC_BRICK, ExtendedBlocks.RED_RHUNIC_BRICK_SLAB, ExtendedRecipes.RHUNIC_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.RED_RHUNIC_PILLAR,
			ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB,
			()->LOTRBlocks.RED_ROCK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);

		makeCrackedBlockRecipe(consumer, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK,
			()->ExtendedBlocks.RED_RHUNIC_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK,
			()->ExtendedBlocks.RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);

		GENERATEDRECIPES.add(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK.get(), ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern(" # ")
		.pattern("#X#")
		.pattern(" # ")
		.define('#', Ingredient.of(Items.GOLD_NUGGET))
		.define('X', Ingredient.of(ExtendedBlocks.RHUNIC_BRICK.get()))
		.unlockedBy("has_"+ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK.get()))
		.unlockedBy("has_"+Items.GOLD_NUGGET.getRegistryName().getPath(), has(Items.GOLD_NUGGET))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_BRICK.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK.get().getRegistryName().getPath()));
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS,
			ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB,
			ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL,
			()->ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		//GONDOR
		makeStairRecipe(consumer, ExtendedBlocks.SMOOTH_GONDOR_ROCK_STAIRS,
			()->LOTRBlocks.SMOOTH_GONDOR_ROCK.get().asItem(), LOTRRecipes.GONDOR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK,
			LOTRBlocks.NUMENOREAN_BRICK, LOTRBlocks.NUMENOREAN_BRICK_SLAB, LOTRRecipes.GONDOR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK,
			LOTRBlocks.NUMENOREAN_BRICK, LOTRBlocks.NUMENOREAN_BRICK_SLAB, LOTRRecipes.UMBAR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedBlocks.CARVED_UMBAR_BRICK,
			LOTRBlocks.UMBAR_BRICK, LOTRBlocks.UMBAR_BRICK_SLAB, LOTRRecipes.UMBAR_CRAFTING);
		makeThrowableAxeRecipe(consumer, ExtendedItems.LOSSARNACH_THROWABLE_AXE, Tags.Items.INGOTS_IRON, "iron", LOTRRecipes.GONDOR_CRAFTING);
		//DWARVEN
		make2x2BlockRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK,
			ExtendedItems.OBSIDIAN_SHARD, LOTRRecipes.DWARVEN_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS,
			ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB,
			ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL,
			()->ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK.get().asItem(), LOTRRecipes.DWARVEN_CRAFTING);
		makeThrowableAxeRecipe(consumer, ExtendedItems.DWARVEN_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, LOTRRecipes.DWARVEN_CRAFTING);
		// BLUE MOUNTAIN DWARVEN
		makeThrowableAxeRecipe(consumer, ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE, LOTRItems.DWARVEN_STEEL_INGOT, LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		// IRON 
		makeThrowableAxeRecipe(consumer, ExtendedItems.IRON_THROWABLE_AXE, Tags.Items.INGOTS_IRON, "iron", null);
		// BRONZE
		makeThrowableAxeRecipe(consumer, ExtendedItems.BRONZE_THROWABLE_AXE, LOTRTags.Items.INGOTS_BRONZE, "bronze", null);
		// Crystal Chandeliers
		makeCrystalChandelierRecipe(consumer, ExtendedBlocks.EDHELVIR_CRYSTAL_CHANDELIER, LOTRItems.EDHELVIR_CRYSTAL, LOTRRecipes.ANY_HIGH_ELVEN);
		makeCrystalChandelierRecipe(consumer, ExtendedBlocks.GLOWSTONE_CRYSTAL_CHANDELIER, LOTRItems.GLOWSTONE_CRYSTAL, null);
		makeCrystalChandelierRecipe(consumer, ExtendedBlocks.GULDURIL_CRYSTAL_CHANDELIER, LOTRItems.GULDURIL_CRYSTAL, LOTRRecipes.ANY_MORGUL);
		//ITEMS
		// TORCH CONVERSIONS
		// DWARVEN
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.DWARVEN_TORCH, LOTRRecipes.ANY_DWARVEN);
		makeTorchConversionRecipe(consumer, LOTRItems.DWARVEN_TORCH, ()->Items.TORCH, null);
		//MORGUL
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, ExtendedItems.MORGUL_TORCH, LOTRItems.GULDURIL, 4, LOTRRecipes.ANY_MORGUL);
		makeTorchConversionRecipe(consumer, ExtendedItems.MORGUL_TORCH, ()->Items.TORCH, null);
		//HIGH ELVEN
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.HIGH_ELVEN_TORCH, LOTRItems.EDHELVIR, 4, LOTRRecipes.ANY_HIGH_ELVEN);
		makeTorchConversionRecipe(consumer, LOTRItems.HIGH_ELVEN_TORCH, ()->Items.TORCH, null);
		//BLUBBER
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.BLUBBER_TORCH, LOTRItems.BLUBBER, 4, LOTRRecipes.LOSSOTH_CRAFTING);
		makeTorchConversionRecipe(consumer, LOTRItems.BLUBBER_TORCH, ()->Items.TORCH, null);
		//MALLORN
		makeTorchConversionRecipe(consumer, LOTRItems.BLUE_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.BLUE_MALLORN_TORCH, LOTRTags.Items.BLUE_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		makeTorchConversionRecipe(consumer, LOTRItems.GOLD_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.GOLD_MALLORN_TORCH, LOTRTags.Items.GOLD_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		makeTorchConversionRecipe(consumer, LOTRItems.GREEN_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.GREEN_MALLORN_TORCH, LOTRTags.Items.GREEN_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		makeTorchConversionRecipe(consumer, LOTRItems.SILVER_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipe(consumer, ()->Items.TORCH, LOTRItems.SILVER_MALLORN_TORCH, LOTRTags.Items.SILVER_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		//ORC
		makeTorchConversionRecipe(consumer, LOTRItems.ORC_TORCH, ()->Items.TORCH, null);
		ExtendedFactionShapedRecipeBuilder.shaped(LOTRItems.ORC_TORCH.get(), 1, LOTRRecipes.ANY_ORC)
		.pattern("T")
		.pattern("S")
		.define('T', Ingredient.of(Items.TORCH))
		.define('S', Ingredient.of(STICKS))
		.unlockedBy("has_torch", has(Items.TORCH))
		.unlockedBy("has_stick", has(STICKS))
		.unlockedBy("has_orc_torch", has(LOTRItems.ORC_TORCH.get()))
		.save(consumer, lotrLoc("orc_torch_from_torch"));
		GENERATEDRECIPES.add(ExtendedItems.SKULL_GOBLET.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.SKULL_GOBLET.get())
		.pattern("S")
		.pattern("T")
		.define('S', Ingredient.of(Items.SKELETON_SKULL))
		.define('T', Ingredient.of(ExtendedTags.Items.TIN_INGOT))
		.unlockedBy("has_"+Items.SKELETON_SKULL.getRegistryName().getPath(), has(Items.SKELETON_SKULL))
		.save(consumer, lotrLoc(ExtendedItems.SKULL_GOBLET.get().getRegistryName().getPath()));

		GENERATEDRECIPES.add(ExtendedItems.ROPE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.ROPE.get())
		.pattern("S")
		.pattern("S")
		.pattern("S")
		.define('S', Ingredient.of(Tags.Items.STRING))
		.unlockedBy("has_string", has(Tags.Items.STRING))
		.unlockedBy("has_"+ExtendedItems.ROPE.get().getRegistryName().getPath(), has(ExtendedItems.ROPE.get()))
		.save(consumer, lotrLoc(ExtendedItems.ROPE.get().getRegistryName().getPath()));
		// BOWS
		makeBowRecipe(consumer, ExtendedItems.GUNDABAD_URUK_BOW, ExtendedItems.BOWSTRING, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeBowRecipe(consumer, ExtendedItems.ORC_BOW, ExtendedItems.BOWSTRING, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel", LOTRRecipes.ANY_ORC);
		makeBowRecipe(consumer, ExtendedItems.RANGER_BOW, ExtendedItems.BOWSTRING, STICKS, "sticks", LOTRRecipes.RANGER_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.LONGBOW.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.LONGBOW.get())
		.pattern("XXX")
		.pattern("X #")
		.pattern("X##")
		.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
		.define('X', Ingredient.of(STICKS))
		.unlockedBy("has_"+ExtendedItems.BOWSTRING.get().getRegistryName().getPath(), has(ExtendedItems.BOWSTRING.get()))
		.unlockedBy("has_stick", has(STICKS))
		.save(consumer, lotrLoc(ExtendedItems.LONGBOW.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BOWSTRING.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.BOWSTRING.get())
		.requires(Tags.Items.STRING)
		.requires(Tags.Items.STRING)
		.requires(Tags.Items.STRING)
		.unlockedBy("has_string", has(Tags.Items.STRING))
		.save(consumer, lotrLoc(ExtendedItems.BOWSTRING.get().getRegistryName().getPath()));
		makeBowRecipe(consumer, ExtendedItems.RIVENDELL_BOW, ExtendedItems.BOWSTRING, STICKS, "sticks", LOTRRecipes.RIVENDELL_CRAFTING);

		GENERATEDRECIPES.add(ExtendedItems.CHISEL.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHISEL.get())
		.pattern("SI")
		.define('S', Ingredient.of(STICKS))
		.define('I', Ingredient.of(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
		.save(consumer, lotrLoc(ExtendedItems.CHISEL.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BRANDING_IRON.get())
		.pattern("  #")
		.pattern(" X ")
		.pattern("#  ")
		.define('#', Ingredient.of(Tags.Items.INGOTS_IRON))
		.define('X', Ingredient.of(Tags.Items.LEATHER))
		.unlockedBy("has_"+ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath(), has(ExtendedItems.BRANDING_IRON.get()))
		.unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
		.save(consumer, lotrLoc(ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath()));
		// Extended cheese
		GENERATEDRECIPES.add(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHEESE_WHEEL.get())
		.pattern("###")
		.pattern("XSX")
		.pattern("###")
		.define('#', Ingredient.of(ExtendedItems.CHEESE_CURDS.get()))
		.define('X', Ingredient.of(LOTRItems.SALT.get()))
		.define('S', Ingredient.of(Tags.Items.MUSHROOMS))
		.unlockedBy("has_"+ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_WHEEL.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_CURDS.get()))
		.save(consumer, lotrLoc(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath()));
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHEESE_WHEEL.get())
		.pattern("XXX")
		.pattern("#X#")
		.pattern("XXX")
		.define('X', Ingredient.of(ExtendedItems.CHEESE_SLICE.get()))
		.define('#', Ingredient.of(ExtendedItems.CHEESE_CURDS.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_WHEEL.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_SLICE.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_SLICE.get()))
		.save(consumer, lotrLoc(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath()+"_from_slices"));
		GENERATEDRECIPES.add(ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BANANA_CAKE.get())
		.pattern("###")
		.pattern("XSX")
		.pattern("ZZZ")
		.define('#', Ingredient.of(Items.MILK_BUCKET))
		.define('X', Ingredient.of(ExtendedItems.BANANA.get()))
		.define('S', Ingredient.of(Tags.Items.EGGS))
		.define('Z', Ingredient.of(Items.WHEAT))
		.unlockedBy("has_"+ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath(), has(ExtendedItems.BANANA_CAKE.get()))
		.unlockedBy("has_"+ExtendedItems.BANANA.get().getRegistryName().getPath(), has(ExtendedItems.BANANA.get()))
		.save(consumer, lotrLoc(ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BANANA_BREAD.get())
		.pattern("XSX")
		.define('S', Ingredient.of(ExtendedItems.BANANA.get()))
		.define('X', Ingredient.of(Items.WHEAT))
		.unlockedBy("has_"+ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath(), has(ExtendedItems.BANANA_BREAD.get()))
		.unlockedBy("has_"+ExtendedItems.BANANA.get().getRegistryName().getPath(), has(ExtendedItems.BANANA.get()))
		.save(consumer, lotrLoc(ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath()));

		GENERATEDRECIPES.add(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.SILVER_COIN_ONE.get(), 4)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(LOTRItems.SILVER_NUGGET.get()))
		.unlockedBy("has_"+ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath(), has(ExtendedItems.SILVER_COIN_ONE.get()))
		.unlockedBy("has_"+LOTRItems.SILVER_NUGGET.get().getRegistryName().getPath(), has(LOTRItems.SILVER_NUGGET.get()))
		.save(consumer, lotrLoc(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.HITHLAIN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HITHLAIN.get(), 3, LOTRRecipes.GALADHRIM_CRAFTING)
		.pattern("###")
		.define('#', Ingredient.of(Tags.Items.STRING))
		.unlockedBy("has_"+ExtendedItems.HITHLAIN.get().getRegistryName().getPath(), has(ExtendedItems.HITHLAIN.get()))
		.unlockedBy("has_string", has(Tags.Items.STRING))
		.save(consumer, lotrLoc(ExtendedItems.HITHLAIN.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.BOTTLE_OF_POISON.get())
		.requires(ExtendedItems.WILDBERRY.get())
		.requires(Items.GLASS_BOTTLE)
		.unlockedBy("has_"+ExtendedItems.WILDBERRY.get().getRegistryName().getPath(), has(ExtendedItems.WILDBERRY.get()))
		.unlockedBy("has_"+ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath(), has(ExtendedItems.BOTTLE_OF_POISON.get()))
		.save(consumer, lotrLoc(ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath()));
		//ExtendedDyeableRecipeBuilder.dyeing(ExtendedRecipeSerializers.EXTENDED_LEATHER_HAT.get(), IRecipeType.CRAFTING)
		//.save(consumer, lotrLoc("leather_hat"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, ExtendedItems.LEATHER_HAT.get()).save(consumer, lotrLoc("leather_hat_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, ExtendedItems.LEATHER_BOAR_ARMOR.get()).save(consumer, lotrLoc("leather_boar_armor_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_BOOTS).save(consumer, lotrLoc("leather_boots_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_LEGGINGS).save(consumer, lotrLoc("leather_leggings_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_CHESTPLATE).save(consumer, lotrLoc("leather_chestplate_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_HELMET).save(consumer, lotrLoc("leather_helmet_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_HORSE_ARMOR).save(consumer, lotrLoc("leather_horse_armor_on_faction_table"));
		GENERATEDRECIPES.add(ExtendedItems.COMMANDHORN.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.COMMANDHORN.get())
		.pattern("#X#")
		.define('#', Ingredient.of(ExtendedTags.Items.BRONZE_INGOT))
		.define('X', Ingredient.of(HORNS))
		.unlockedBy("has_"+ExtendedItems.COMMANDHORN.get().getRegistryName().getPath(), has(ExtendedItems.COMMANDHORN.get()))
		.unlockedBy("has_horn", has(HORNS))
		.save(consumer, lotrLoc(ExtendedItems.COMMANDHORN.get().getRegistryName().getPath()));
		ExtendedCustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_TIPPED_ARROW.get()).save(consumer, lotrLoc("tipped_arrow_poison"));
		GENERATEDRECIPES.add(ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.BLACK_URUK_STEEL_INGOT.get(), LOTRRecipes.ORC_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ORC_STEEL_INGOT)
		.requires(ExtendedTags.Items.MORGUL_IRON_ORE)
		.requiresAlloy(LOTRItems.DURNOR.get())
		.unlockedBy("has_"+ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.BLACK_URUK_STEEL_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.DURNOR.get().getRegistryName().getPath(), has(LOTRItems.DURNOR.get()))
		.save(consumer, lotrLoc(ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.GALVORN_INGOT.get(), LOTRRecipes.DWARVEN_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ELVEN_STEEL_INGOT)
		.requiresAlloy(LOTRItems.EDHELVIR.get())
		.unlockedBy("has_"+ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.GALVORN_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.EDHELVIR.get().getRegistryName().getPath(), has(LOTRItems.EDHELVIR.get()))
		.save(consumer, lotrLoc(ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.GILDED_IRON_INGOT.get(), LOTRRecipes.ALLOY_SERIALIZER.get())
		.requires(Tags.Items.ORES_IRON)
		.requires(Tags.Items.INGOTS_IRON)
		.requiresAlloy(Items.GOLD_NUGGET)
		.unlockedBy("has_"+ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.GILDED_IRON_INGOT.get()))
		.unlockedBy("has_"+Items.GOLD_NUGGET.getRegistryName().getPath(), has(Items.GOLD_NUGGET))
		.save(consumer, lotrLoc(ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.MORGUL_STEEL_INGOT.get(), LOTRRecipes.ORC_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ORC_STEEL_INGOT)
		.requires(ExtendedTags.Items.MORGUL_IRON_ORE)
		.requiresAlloy(LOTRItems.GULDURIL.get())
		.unlockedBy("has_"+ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_STEEL_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.GULDURIL.get().getRegistryName().getPath(), has(LOTRItems.GULDURIL.get()))
		.save(consumer, lotrLoc(ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.ITHILDIN.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.ITHILDIN.get(), LOTRRecipes.ELVEN_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.SILVER_INGOT)
		.requires(ExtendedTags.Items.SILVER_ORE)
		.requiresAlloy(LOTRItems.MITHRIL_NUGGET.get())
		.unlockedBy("has_"+ExtendedItems.ITHILDIN.get().getRegistryName().getPath(), has(ExtendedItems.ITHILDIN.get()))
		.unlockedBy("has_"+LOTRItems.MITHRIL_NUGGET.get().getRegistryName().getPath(), has(LOTRItems.MITHRIL_NUGGET.get()))
		.save(consumer, lotrLoc(ExtendedItems.ITHILDIN.get().getRegistryName().getPath()));
		//Arnor
		makePillarSlabRecipe(consumer, ExtendedBlocks.CRACKED_ARNOR_PILLAR,
			ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB,
			LOTRItems.CRACKED_ARNOR_BRICK, LOTRRecipes.RANGER_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.MOSSY_ARNOR_PILLAR,
			ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB,
			LOTRItems.MOSSY_ARNOR_BRICK, LOTRRecipes.RANGER_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.CRACKED_STONE_PILLAR,
			ExtendedBlocks.CRACKED_STONE_PILLAR_SLAB,
			()->Items.CRACKED_STONE_BRICKS, null);
		GENERATEDRECIPES.add(ExtendedItems.LARGE_POT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.LARGE_POT.get())
		.pattern("# #")
		.pattern("# #")
		.pattern("###")
		.define('#', Ingredient.of(Items.BRICK))
		.unlockedBy("has_brick", has(Items.BRICK))
		.save(consumer, lotrLoc(ExtendedItems.LARGE_POT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.LARGE_FLOWER_POT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.LARGE_FLOWER_POT.get())
		.pattern("# #")
		.pattern("###")
		.define('#', Ingredient.of(Items.BRICK))
		.unlockedBy("has_brick", has(Items.BRICK))
		.save(consumer, lotrLoc(ExtendedItems.LARGE_FLOWER_POT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.CHIMNEYPOT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHIMNEYPOT.get())
		.pattern("# #")
		.pattern("# #")
		.define('#', Ingredient.of(Items.BRICK))
		.unlockedBy("has_brick", has(Items.BRICK))
		.save(consumer, lotrLoc(ExtendedItems.CHIMNEYPOT.get().getRegistryName().getPath()));
		//Shields
		makeShieldRecipe(consumer, ExtendedItems.DUNEDAIN_SHIELD, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.RANGER_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.GUNDABAD_SHIELD, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.BREE_SHIELD, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.BREE_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.BLUE_DWARVEN_SHIELD, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.RIVENDELL_SHIELD, ExtendedTags.Items.ELVEN_STEEL_INGOT, "elven_steel", LOTRRecipes.RIVENDELL_CRAFTING);
		//Banners and patterns
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DUNEDAIN_BANNER, ExtendedItems.DUNEDAIN_BANNER_PATTERN, LOTRRecipes.RANGER_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.HOBBIT_BANNER, ExtendedItems.HOBBIT_BANNER_PATTERN, LOTRRecipes.HOBBIT_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DURINS_FOLK_BANNER, ExtendedItems.DURINS_FOLK_BANNER_PATTERN, LOTRRecipes.DWARVEN_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.BLUE_MOUNTAINS_BANNER, ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN, LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.GUNDABAD_ORC_BANNER, ExtendedItems.GUNDABAD_ORC_BANNER_PATTERN, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.BREE_BANNER, ExtendedItems.BREE_BANNER_PATTERN, LOTRRecipes.BREE_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.ANGMAR_BANNER, ExtendedItems.ANGMAR_BANNER_PATTERN , LOTRRecipes.ANGMAR_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DALE_BANNER, ExtendedItems.DALE_BANNER_PATTERN, LOTRRecipes.DALE_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DOL_AMROTH_BANNER, ExtendedItems.DOL_AMROTH_BANNER_PATTERN, LOTRRecipes.DOL_AMROTH_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DOL_GULDUR_BANNER, ExtendedItems.DOL_GULDUR_BANNER_PATTERN, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DORWINION_BANNER, ExtendedItems.DORWINION_BANNER_PATTERN, LOTRRecipes.DORWINION_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.DUNLAND_BANNER, ExtendedItems.DUNLAND_BANNER_PATTERN, LOTRRecipes.DUNLENDING_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.GONDOR_BANNER, ExtendedItems.GONDOR_BANNER_PATTERN, LOTRRecipes.GONDOR_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.ISENGARD_BANNER, ExtendedItems.ISENGARD_BANNER_PATTERN, LOTRRecipes.URUK_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.LINDON_BANNER, ExtendedItems.LINDON_BANNER_PATTERN, LOTRRecipes.LINDON_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.LOTHLORIEN_BANNER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.LOTHLORIEN_BANNER.get(), LOTRRecipes.GALADHRIM_CRAFTING)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(ExtendedTags.Items.WOOL_BLOCKS))
		.define('S', Ingredient.of(LOTRItems.MALLORN_STICK.get()))
		.define('P', Ingredient.of(LOTRItems.MALLORN_PLANKS.get()))
		.unlockedBy("has_mallorn_stick", has(LOTRItems.MALLORN_STICK.get()))
		.unlockedBy("has_mallorn_plank", has(LOTRItems.MALLORN_PLANKS.get()))
		.unlockedBy("has_"+ExtendedItems.LOTHLORIEN_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.LOTHLORIEN_BANNER.get()))
		.save(consumer, lotrLoc(ExtendedItems.LOTHLORIEN_BANNER.get().getRegistryName().getPath()));
		makeBannerPatternRecipe(consumer, ExtendedItems.LOTHLORIEN_BANNER, ExtendedItems.LOTHLORIEN_BANNER_PATTERN, LOTRRecipes.GALADHRIM_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.MORDOR_BANNER, ExtendedItems.MORDOR_BANNER_PATTERN, LOTRRecipes.MORDOR_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.RIVENDELL_BANNER, ExtendedItems.RIVENDELL_BANNER_PATTERN, LOTRRecipes.RIVENDELL_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.ROHAN_BANNER, ExtendedItems.ROHAN_BANNER_PATTERN, LOTRRecipes.ROHAN_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.WOODLAND_REALM_BANNER, ExtendedItems.WOODLAND_REALM_BANNER_PATTERN, LOTRRecipes.WOOD_ELVEN_CRAFTING);


		makeBoarArmorRecipe(consumer, ExtendedItems.LEATHER_BOAR_ARMOR, Tags.Items.LEATHER, "leather", LOTRRecipes.ANY_DWARVEN);
		makeBoarArmorRecipe(consumer, ExtendedItems.DWARVEN_BOAR_ARMOR, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel", LOTRRecipes.DWARVEN_CRAFTING);
		makeBoarArmorRecipe(consumer, ExtendedItems.BLUE_DWARVEN_BOAR_ARMOR, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);

		//Containers
		GENERATEDRECIPES.add(ExtendedItems.REED_BASKET.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.REED_BASKET.get())
		.pattern("LLL")
		.pattern("L L")
		.pattern("LLL")
		.define('L', Ingredient.of(LOTRItems.DRIED_REEDS.get()))
		.unlockedBy("has_"+LOTRItems.DRIED_REEDS.get().getRegistryName().getPath(), has(LOTRItems.DRIED_REEDS.get()))
		.unlockedBy("has_"+ExtendedItems.REED_BASKET.get().getRegistryName().getPath(), has(ExtendedItems.REED_BASKET.get()))
		.save(consumer, lotrLoc(ExtendedItems.REED_BASKET.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.ORC_BARREL.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORC_BARREL.get(), LOTRRecipes.ANY_ORC)
		.pattern("PLP")
		.pattern("P P")
		.pattern("PLP")
		.define('L', Ingredient.of(Tags.Items.LEATHER))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_leather", has(Tags.Items.LEATHER))
		.unlockedBy("has_planks", has(PLANKS))
		.unlockedBy("has_"+ExtendedItems.ORC_BARREL.get().getRegistryName().getPath(), has(ExtendedItems.ORC_BARREL.get()))
		.save(consumer, lotrLoc(ExtendedItems.ORC_BARREL.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedBlocks.WEAPON_RACK.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedBlocks.WEAPON_RACK.get())
		.pattern("X X")
		.pattern("###")
		.define('#', Ingredient.of(PLANKS))
		.define('X', Ingredient.of(STICKS))
		.unlockedBy("has_planks", has(PLANKS))
		.unlockedBy("has_"+ExtendedBlocks.WEAPON_RACK.get().getRegistryName().getPath(), has(ExtendedBlocks.WEAPON_RACK.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.WEAPON_RACK.get().getRegistryName().getPath()));
		//Birdcages
		GENERATEDRECIPES.add(ExtendedBlocks.WOODEN_BIRDCAGE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedBlocks.WOODEN_BIRDCAGE.get())
		.pattern("BBB")
		.pattern("B B")
		.pattern("III")
		.define('B', Ingredient.of(STICKS))
		.define('I', Ingredient.of(PLANKS))
		.unlockedBy("has_"+ExtendedBlocks.WOODEN_BIRDCAGE.get().getRegistryName().getPath(), has(ExtendedBlocks.WOODEN_BIRDCAGE.get()))
		.unlockedBy("has_sticks", has(STICKS))
		.save(consumer, lotrLoc(ExtendedBlocks.WOODEN_BIRDCAGE.get().getRegistryName().getPath()));
		makeBirdcageRecipe(consumer, ExtendedBlocks.BRONZE_BIRDCAGE, LOTRItems.BRONZE_BARS, ExtendedTags.Items.BRONZE_INGOT, null);
		makeBirdcageRecipe(consumer, ExtendedBlocks.IRON_BIRDCAGE, ()->Items.IRON_BARS, Tags.Items.INGOTS_IRON, null);
		makeBirdcageRecipe(consumer, ExtendedBlocks.SILVER_BIRDCAGE, LOTRItems.SILVER_BARS, LOTRTags.Items.INGOTS_SILVER, null);
		makeBirdcageRecipe(consumer, ExtendedBlocks.GOLDEN_BIRDCAGE, LOTRItems.GOLD_BARS, Tags.Items.INGOTS_GOLD, null);
		makeBirdcageRecipe(consumer, ExtendedBlocks.MITHRIL_BIRDCAGE, LOTRItems.MITHRIL_BARS, ExtendedTags.Items.MITHRIL_INGOT, null);
		//Dirts
		GENERATEDRECIPES.add(ExtendedBlocks.WASTE_BLOCK.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedBlocks.WASTE_BLOCK.get(), 4)
		.pattern("FD")
		.pattern("DB")
		.define('D', Ingredient.of(Items.DIRT))
		.define('F', Ingredient.of(ExtendedTags.Items.FLESH))
		.define('B', Ingredient.of(Tags.Items.BONES))
		.unlockedBy("has_"+Items.DIRT.getRegistryName().getPath(), has(Items.DIRT))
		.unlockedBy("has_"+ExtendedBlocks.WASTE_BLOCK.get().getRegistryName().getPath(), has(ExtendedBlocks.WASTE_BLOCK.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.WASTE_BLOCK.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedBlocks.BARREN_DIRT.get(), 8)
		.pattern("###")
		.pattern("#X#")
		.pattern("###")
		.define('#', Ingredient.of(Blocks.DIRT.asItem()))
		.define('X', Ingredient.of(LOTRItems.SALT.get()))
		.unlockedBy("has_"+LOTRItems.SALT.get().getRegistryName().getPath(), has(LOTRItems.SALT.get()))
		.unlockedBy("has_"+ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath(), has(ExtendedBlocks.BARREN_DIRT.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath()));
		ShapelessRecipeBuilder.shapeless(Blocks.DIRT)
		.requires(Ingredient.of(ExtendedBlocks.BARREN_DIRT.get()))
		.requires(Ingredient.of(Tags.Items.SEEDS))
		.unlockedBy("has_"+Items.DIRT.getRegistryName().getPath(), has(Items.DIRT))
		.unlockedBy("has_"+ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath(), has(ExtendedBlocks.BARREN_DIRT.get()))
		.save(consumer, lotrLoc(Blocks.DIRT.getRegistryName().getPath()+"_from_"+ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath()));
		makeSlabRecipe(consumer, ExtendedBlocks.BARREN_DIRT_SLAB, ExtendedItems.BARREN_DIRT, null);
		makeSlabRecipe(consumer, ExtendedBlocks.COARSE_DIRT_SLAB, () -> Items.COARSE_DIRT, null);
		makeSlabRecipe(consumer, ExtendedBlocks.DIRT_SLAB, () -> Items.DIRT, null);
		makeSlabRecipe(consumer, ExtendedBlocks.GRASS_BLOCK_SLAB, () -> Items.GRASS_BLOCK, null);
		makeSlabRecipe(consumer, ExtendedBlocks.GRASS_PATH_SLAB, () -> Items.GRASS_PATH, null);
		makeSlabRecipe(consumer, ExtendedBlocks.MYCELIUM_SLAB, () -> Items.MYCELIUM, null);
		makeSlabRecipe(consumer, ExtendedBlocks.PODZOL_SLAB, () -> Items.PODZOL, null);
		//Scorched blocks
		GENERATEDRECIPES.add(ExtendedBlocks.SCORCHED_COBBLESTONE.get().getRegistryName().getPath());
		CookingRecipeBuilder.blasting(Ingredient.of(Blocks.COBBLESTONE.asItem()), ExtendedBlocks.SCORCHED_COBBLESTONE.get(), 0.1F, 100)
		.unlockedBy("has_"+Blocks.COBBLESTONE.getRegistryName().getPath(), has(Blocks.COBBLESTONE))
		.unlockedBy("has_"+ExtendedBlocks.SCORCHED_COBBLESTONE.get().getRegistryName().getPath(), has(ExtendedBlocks.SCORCHED_COBBLESTONE.get()))
		.save(consumer, ExtendedBlocks.SCORCHED_COBBLESTONE.get().getRegistryName().getPath()+"_from_blasting");
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.SCORCHED_COBBLESTONE_STAIRS,
			ExtendedBlocks.SCORCHED_COBBLESTONE_SLAB, ExtendedBlocks.SCORCHED_COBBLESTONE_WALL,
			()->ExtendedBlocks.SCORCHED_COBBLESTONE.get().asItem(), null);
		GENERATEDRECIPES.add(ExtendedBlocks.SCORCHED_STONE.get().getRegistryName().getPath());
		CookingRecipeBuilder.blasting(Ingredient.of(Blocks.STONE.asItem()), ExtendedBlocks.SCORCHED_STONE.get(), 0.1F, 100)
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE))
		.unlockedBy("has_"+ExtendedBlocks.SCORCHED_STONE.get().getRegistryName().getPath(), has(ExtendedBlocks.SCORCHED_STONE.get()))
		.save(consumer, ExtendedBlocks.SCORCHED_STONE.get().getRegistryName().getPath()+"_from_blasting");
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.SCORCHED_STONE_STAIRS,
			ExtendedBlocks.SCORCHED_STONE_SLAB, ExtendedBlocks.SCORCHED_STONE_WALL,
			()->ExtendedBlocks.SCORCHED_STONE.get().asItem(), null);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.SCORCHED_BRICK,
			ExtendedBlocks.SCORCHED_BRICK_STAIRS,
			ExtendedBlocks.SCORCHED_BRICK_SLAB,
			ExtendedBlocks.SCORCHED_BRICK_WALL,
			()->ExtendedItems.SCORCHED_STONE.get(), null);
		CookingRecipeBuilder.blasting(Ingredient.of(Blocks.STONE_BRICKS.asItem()), ExtendedBlocks.SCORCHED_BRICK.get(), 0.1F, 100)
		.unlockedBy("has_"+Blocks.STONE_BRICKS.getRegistryName().getPath(), has(Blocks.STONE_BRICKS))
		.unlockedBy("has_"+ExtendedBlocks.SCORCHED_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.SCORCHED_BRICK.get()))
		.save(consumer, ExtendedBlocks.SCORCHED_BRICK.get().getRegistryName().getPath()+"_from_blasting");
		//Missing smooth stair recipes
		makeStairRecipe(consumer, ()->ExtendedBlocks.SMOOTH_MORDOR_ROCK_STAIRS.get(),
			()->LOTRBlocks.SMOOTH_MORDOR_ROCK.get().asItem(), LOTRRecipes.MORDOR_CRAFTING);
		makeStairRecipe(consumer, ()->ExtendedBlocks.SMOOTH_ROHAN_ROCK_STAIRS.get(),
			()->LOTRBlocks.SMOOTH_ROHAN_ROCK.get().asItem(), LOTRRecipes.ROHAN_CRAFTING);
		makeStairRecipe(consumer, ()->ExtendedBlocks.SMOOTH_RED_ROCK_STAIRS.get(),
			()->LOTRBlocks.SMOOTH_RED_ROCK.get().asItem(), null);
		makeStairRecipe(consumer, ()->ExtendedBlocks.SMOOTH_BLUE_ROCK_STAIRS.get(),
			()->LOTRBlocks.SMOOTH_BLUE_ROCK.get().asItem(), null);
		makeStairRecipe(consumer, ()->ExtendedBlocks.SMOOTH_STONE_STAIRS.get(),
                Blocks.SMOOTH_STONE::asItem, null);
		//drystone
		ShapelessRecipeBuilder.shapeless(LOTRBlocks.DRYSTONE.get())
		.requires(ExtendedBlocks.COBBLED_DRYSTONE.get())
		.unlockedBy("has_"+LOTRBlocks.DRYSTONE.get().getRegistryName().getPath(), has(LOTRBlocks.DRYSTONE.get()))
		.unlockedBy("has_"+ExtendedBlocks.COBBLED_DRYSTONE.get().getRegistryName().getPath(), has(ExtendedBlocks.COBBLED_DRYSTONE.get()))
		.save(consumer, lotrLoc(LOTRBlocks.DRYSTONE.get().getRegistryName().getPath()+"_from_"+ExtendedBlocks.COBBLED_DRYSTONE.get().getRegistryName().getPath()));
		ShapelessRecipeBuilder.shapeless(LOTRBlocks.DRYSTONE.get())
		.requires(ExtendedBlocks.CUT_DRYSTONE.get())
		.unlockedBy("has_"+LOTRBlocks.DRYSTONE.get().getRegistryName().getPath(), has(LOTRBlocks.DRYSTONE.get()))
		.unlockedBy("has_"+ExtendedBlocks.CUT_DRYSTONE.get().getRegistryName().getPath(), has(ExtendedBlocks.CUT_DRYSTONE.get()))
		.save(consumer, lotrLoc(LOTRBlocks.DRYSTONE.get().getRegistryName().getPath()+"_from_"+ExtendedBlocks.CUT_DRYSTONE.get().getRegistryName().getPath()));
		make2x2BlockRecipe(consumer, ExtendedBlocks.CUT_DRYSTONE,
			()->LOTRBlocks.DRYSTONE.get().asItem(), null);
		make3x3BlockRecipe(consumer, ExtendedBlocks.COBBLED_DRYSTONE,
			()->LOTRBlocks.DRYSTONE.get().asItem(), null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.DRYSTONE_STAIRS,
			ExtendedBlocks.DRYSTONE_SLAB,
			ExtendedBlocks.DRYSTONE_WALL,
			()->LOTRBlocks.DRYSTONE.get().asItem(), null);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE,
			()->LOTRBlocks.DRYSTONE.get().asItem(), null);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE,
			()->ExtendedBlocks.COBBLED_DRYSTONE.get().asItem(), null, "_from_cobbled_drystone");
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE_STAIRS,
			ExtendedBlocks.MOSSY_DRYSTONE_SLAB,
			ExtendedBlocks.MOSSY_DRYSTONE_WALL,
			()->ExtendedBlocks.MOSSY_DRYSTONE.get().asItem(), null);
		GENERATEDRECIPES.add(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get(), 4)
		.pattern("SDS")
		.pattern("DSD")
		.pattern("SDS")
		.define('S', Ingredient.of(STICKS))
		.define('D', Ingredient.of(LOTRBlocks.WATTLE_AND_DAUB.get()))
		.unlockedBy("has_"+LOTRBlocks.WATTLE_AND_DAUB.get().getRegistryName().getPath(), has(LOTRBlocks.WATTLE_AND_DAUB.get()))
		.save(consumer, lotrLoc(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get().getRegistryName().getPath()));
		//Mud
		makeSlabRecipe(consumer, ()->ExtendedBlocks.MUD_SLAB.get(),
			()->ExtendedBlocks.MUD.get().asItem(), null);
		makeStairRecipe(consumer, ()->ExtendedBlocks.MUD_STAIRS.get(),
			()->ExtendedBlocks.MUD.get().asItem(), null);
		makeSlabRecipe(consumer, ()->ExtendedBlocks.MUD_PATH_SLAB.get(),
			()->ExtendedBlocks.MUD_PATH.get().asItem(), null);
		makeStairRecipe(consumer, ()->ExtendedBlocks.MUD_PATH_STAIRS.get(),
			()->ExtendedBlocks.MUD_PATH.get().asItem(), null);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.MUD_BRICK,
			ExtendedBlocks.MUD_BRICK_STAIRS,
			ExtendedBlocks.MUD_BRICK_SLAB,
			ExtendedBlocks.MUD_BRICK_WALL,
			()->ExtendedBlocks.MUD.get().asItem(), null);
		makeMossyBlockRecipe(consumer, ExtendedBlocks.MOSSY_MUD_BRICK,
			()->ExtendedBlocks.MUD_BRICK.get().asItem(), null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_MUD_BRICK_STAIRS,
			ExtendedBlocks.MOSSY_MUD_BRICK_SLAB,
			ExtendedBlocks.MOSSY_MUD_BRICK_WALL,
			()->ExtendedBlocks.MOSSY_MUD_BRICK.get().asItem(), null);
		makeCrackedBlockRecipe(consumer, ExtendedBlocks.CRACKED_MUD_BRICK,
			()->ExtendedBlocks.MUD_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.CRACKED_MUD_BRICK_STAIRS,
			ExtendedBlocks.CRACKED_MUD_BRICK_SLAB,
			ExtendedBlocks.CRACKED_MUD_BRICK_WALL,
			()->ExtendedBlocks.CRACKED_MUD_BRICK.get().asItem(), null);
		GENERATEDRECIPES.add(ExtendedItems.MOSS_CARPET.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.MOSS_CARPET.get(), 3)
		.pattern("XX")
		.define('X', Ingredient.of(ExtendedItems.MOSS_BLOCK.get()))
		.unlockedBy("has_"+ExtendedItems.MOSS_BLOCK.get().getRegistryName().getPath(), has(ExtendedItems.MOSS_BLOCK.get()))
		.save(consumer, lotrLoc(ExtendedItems.MOSS_CARPET.get().getRegistryName().getPath()));
		makeMossyBlockRecipe(consumer, ()->Blocks.MOSSY_COBBLESTONE,
                Blocks.COBBLESTONE::asItem, null, "", false);
		makeMossyBlockRecipe(consumer, ()->Blocks.MOSSY_STONE_BRICKS,
                Blocks.STONE_BRICKS::asItem, null, "", false);
		//Natural Stones
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.GNEISS_ROCK_STAIRS,
//			ExtendedBlocks.GNEISS_ROCK_SLAB,
//			ExtendedBlocks.GNEISS_ROCK_WALL,
//			ExtendedItems.GNEISS_ROCK, null);
//		make2x2BlockRecipe(consumer, ExtendedBlocks.GNEISS_BRICK,
//			ExtendedItems.GNEISS_ROCK, null);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.GNEISS_BRICK_STAIRS,
//			ExtendedBlocks.GNEISS_BRICK_SLAB,
//			ExtendedBlocks.GNEISS_BRICK_WALL,
//			ExtendedItems.GNEISS_BRICK, null);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK_STAIRS,
//			ExtendedBlocks.LIMESTONE_ROCK_SLAB,
//			ExtendedBlocks.LIMESTONE_ROCK_WALL,
//			ExtendedItems.LIMESTONE_ROCK, null);
//		make2x2BlockRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK,
//			ExtendedItems.LIMESTONE_ROCK, null);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK_STAIRS,
//			ExtendedBlocks.LIMESTONE_BRICK_SLAB,
//			ExtendedBlocks.LIMESTONE_BRICK_WALL,
//			ExtendedItems.LIMESTONE_BRICK, null);
//		make2x2BlockRecipe(consumer, ExtendedBlocks.LIMESTONE_TILE,
//			ExtendedItems.LIMESTONE_BRICK, null);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.LIMESTONE_TILE_STAIRS,
//			ExtendedBlocks.LIMESTONE_TILE_SLAB,
//			ExtendedBlocks.LIMESTONE_TILE_WALL,
//			ExtendedItems.LIMESTONE_TILE, null);
//		makeCrackedBlockRecipe(consumer, ExtendedBlocks.POLISHED_GNEISS,
//			ExtendedItems.GNEISS_ROCK);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.POLISHED_GNEISS_STAIRS,
//			ExtendedBlocks.POLISHED_GNEISS_SLAB,
//			ExtendedBlocks.POLISHED_GNEISS_WALL,
//			ExtendedItems.POLISHED_GNEISS, null);
//		makeCrackedBlockRecipe(consumer, ExtendedBlocks.POLISHED_LIMESTONE,
//			ExtendedItems.LIMESTONE_ROCK);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.POLISHED_LIMESTONE_STAIRS,
//			ExtendedBlocks.POLISHED_LIMESTONE_SLAB,
//			ExtendedBlocks.POLISHED_LIMESTONE_WALL,
//			ExtendedItems.POLISHED_LIMESTONE, null);
//		makeCrackedBlockRecipe(consumer, ExtendedBlocks.POLISHED_SLATE,
//			ExtendedItems.SLATE_ROCK);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.POLISHED_SLATE_STAIRS,
//			ExtendedBlocks.POLISHED_SLATE_SLAB,
//			ExtendedBlocks.POLISHED_SLATE_WALL,
//			ExtendedItems.POLISHED_SLATE, null);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.SLATE_ROCK_STAIRS,
//			ExtendedBlocks.SLATE_ROCK_SLAB,
//			ExtendedBlocks.SLATE_ROCK_WALL,
//			ExtendedItems.SLATE_ROCK, null);
//		make2x2BlockRecipe(consumer, ExtendedBlocks.SLATE_BRICK,
//			ExtendedItems.SLATE_ROCK, null);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.SLATE_BRICK_STAIRS,
//			ExtendedBlocks.SLATE_BRICK_SLAB,
//			ExtendedBlocks.SLATE_BRICK_WALL,
//			ExtendedItems.SLATE_BRICK, null);
//		makeCrackedBlockRecipe(consumer, ExtendedBlocks.SMOOTH_LIMESTONE,
//			ExtendedItems.POLISHED_LIMESTONE);
//		makeStairSlabWallRecipe(consumer, ExtendedBlocks.SMOOTH_LIMESTONE_STAIRS,
//			ExtendedBlocks.SMOOTH_LIMESTONE_SLAB,
//			ExtendedBlocks.SMOOTH_LIMESTONE_WALL,
//			ExtendedItems.SMOOTH_LIMESTONE, null);
		GENERATEDRECIPES.add(ExtendedItems.DEAD_MOSS_VINES.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.DEAD_MOSS_VINES.get())
		.requires(Ingredient.of(Items.VINE))
		.requires(Ingredient.of(LOTRItems.SALT.get()))
		.unlockedBy("has_string", has(LOTRItems.SALT.get()))
		.save(consumer, lotrLoc(ExtendedItems.DEAD_MOSS_VINES.get().getRegistryName().getPath()));
		// Dwarven
		makeDoorRecipe(consumer, ()->ExtendedItems.DWARVEN_DOOR.get(), ()->Items.STONE, LOTRRecipes.DWARVEN_CRAFTING);
		makeDoorRecipe(consumer, ()->ExtendedItems.DWARVEN_DOOR_LUIGON.get(), ()->LOTRItems.BLUE_ROCK.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		makeDoorRecipe(consumer, ()->ExtendedItems.DWARVEN_DOOR_CARGON.get(), ()->LOTRItems.RED_ROCK.get(), null);
		GENERATEDRECIPES.add(ExtendedItems.HOIST.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.HOIST.get())
		.pattern("LCL")
		.pattern("GIG")
		.pattern("LCL")
		.define('L', Ingredient.of(LOGS))
		.define('C', Ingredient.of(ExtendedTags.Items.CHAINS))
		.define('G', Ingredient.of(LOTRItems.GATE_GEAR.get()))
		.define('I', Ingredient.of(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_"+LOTRItems.GATE_GEAR.get().getRegistryName().getPath(), has(LOTRItems.GATE_GEAR.get()))
		.saveNoAdvancement(consumer, lotrLoc(ExtendedItems.HOIST.get().getRegistryName().getPath()));
	}

	private void addMissingRenewedRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_ANGMAR_BRICK,
			LOTRBlocks.ANGMAR_BRICK_SLAB, LOTRRecipes.ANGMAR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_ARNOR_BRICK,
			LOTRBlocks.ARNOR_BRICK_SLAB, LOTRRecipes.RANGER_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_BLUE_BRICK,
			LOTRBlocks.BLUE_BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_BRICK,
			()->Blocks.BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_CHALK_BRICK,
			LOTRBlocks.CHALK_BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_DALE_BRICK,
			LOTRBlocks.DALE_BRICK_SLAB, LOTRRecipes.DALE_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_DWARVEN_BRICK,
			LOTRBlocks.DWARVEN_BRICK_SLAB, LOTRRecipes.ANY_DWARVEN);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_GALADHRIM_BRICK,
			LOTRBlocks.GALADHRIM_BRICK_SLAB, LOTRRecipes.GALADHRIM_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_GONDOR_BRICK,
			LOTRBlocks.GONDOR_BRICK_SLAB, LOTRRecipes.GONDOR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_HIGH_ELVEN_BRICK,
			LOTRBlocks.HIGH_ELVEN_BRICK_SLAB, LOTRRecipes.ANY_HIGH_ELVEN);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_MORDOR_BASALT_BRICK,
			LOTRBlocks.MORDOR_BASALT_BRICK_SLAB, LOTRRecipes.MORDOR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_MORDOR_BRICK,
			LOTRBlocks.MORDOR_BRICK_SLAB, LOTRRecipes.MORDOR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_NUMENOREAN_BRICK,
			LOTRBlocks.NUMENOREAN_BRICK_SLAB, LOTRRecipes.ANY_NUMENOREAN);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_RED_BRICK,
			LOTRBlocks.RED_BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_ROHAN_BRICK,
			LOTRBlocks.ROHAN_BRICK_SLAB, LOTRRecipes.ROHAN_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRBlocks.CARVED_WOOD_ELVEN_BRICK,
			LOTRBlocks.WOOD_ELVEN_BRICK_SLAB, LOTRRecipes.WOOD_ELVEN_CRAFTING);
	}

	private void addMissingVanillaRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCarvedBlockRecipeVanilla(consumer, Blocks.CHISELED_NETHER_BRICKS,
			Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_SLAB);
		makeCarvedBlockRecipeVanilla(consumer, Blocks.CHISELED_POLISHED_BLACKSTONE,
			Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_SLAB);
		makeCarvedBlockRecipeVanilla(consumer, Blocks.CHISELED_QUARTZ_BLOCK,
			Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_SLAB);
		makeCarvedBlockRecipeVanilla(consumer, Blocks.CHISELED_RED_SANDSTONE,
			Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_SLAB);
		makeCarvedBlockRecipeVanilla(consumer, Blocks.CHISELED_SANDSTONE,
			Blocks.SANDSTONE, Blocks.SANDSTONE_SLAB);
		makeCarvedBlockRecipeVanilla(consumer, Blocks.CHISELED_STONE_BRICKS,
			Blocks.STONE_BRICKS, Blocks.STONE_BRICK_SLAB);
		makeCarvedBlockRecipeVanilla(consumer, Blocks.INFESTED_CHISELED_STONE_BRICKS,
			Blocks.INFESTED_STONE_BRICKS, null);
	}

	private void buildVanillaRecipeOverrides(Consumer<IFinishedRecipe> consumer) {
		ExtendedShapedRecipeBuilder.shaped(Items.CAKE)
		.pattern("MMM")
		.pattern("SES")
		.pattern("WWW")
		.define('M', Ingredient.of(Items.MILK_BUCKET))
		.define('S', Ingredient.of(Items.SUGAR))
		.define('E', Ingredient.of(Tags.Items.EGGS))
		.define('W', Ingredient.of(Items.WHEAT))
		.unlockedBy("has_"+Items.CAKE.getRegistryName().getPath(), has(Items.CAKE))
		.save(consumer, mcLoc(Items.CAKE.getRegistryName().getPath()));
		ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_PIE)
		.requires(Items.SUGAR)
		.requires(Items.PUMPKIN)
		.requires(Tags.Items.EGGS)
		.unlockedBy("has_"+Items.PUMPKIN_PIE.getRegistryName().getPath(), has(Items.PUMPKIN_PIE))
		.save(consumer, mcLoc(Items.PUMPKIN_PIE.getRegistryName().getPath()));
		ExtendedShapedRecipeBuilder.shaped(Items.PISTON)
		.pattern("PPP")
		.pattern("CIC")
		.pattern("CGC")
		.define('P', Ingredient.of(PLANKS))
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('G', Ingredient.of(LOTRItems.GATE_GEAR.get()))
		.define('I', Ingredient.of(Tags.Items.INGOTS_IRON))
		.unlockedBy("has_"+LOTRItems.GATE_GEAR.get().getRegistryName().getPath(), has(LOTRItems.GATE_GEAR.get()))
		.save(consumer, mcLoc(Items.PISTON.getRegistryName().getPath()+"_using_gears"));
		ExtendedShapedRecipeBuilder.shaped(Items.DROPPER)
		.pattern("CCC")
		.pattern("C C")
		.pattern("CGC")
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('G', Ingredient.of(LOTRItems.GATE_GEAR.get()))
		.unlockedBy("has_"+LOTRItems.GATE_GEAR.get().getRegistryName().getPath(), has(LOTRItems.GATE_GEAR.get()))
		.save(consumer, mcLoc(Items.DROPPER.getRegistryName().getPath()+"_using_gears"));
		ExtendedShapedRecipeBuilder.shaped(Items.DISPENSER)
		.pattern("CCC")
		.pattern("CBC")
		.pattern("CGC")
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('G', Ingredient.of(LOTRItems.GATE_GEAR.get()))
		.define('B', Ingredient.of(Items.BOW))
		.unlockedBy("has_"+LOTRItems.GATE_GEAR.get().getRegistryName().getPath(), has(LOTRItems.GATE_GEAR.get()))
		.save(consumer, mcLoc(Items.DISPENSER.getRegistryName().getPath()+"_using_gears"));
		ExtendedShapedRecipeBuilder.shaped(Items.NOTE_BLOCK)
		.pattern("PPP")
		.pattern("PGP")
		.pattern("PPP")
		.define('P', Ingredient.of(PLANKS))
		.define('G', Ingredient.of(LOTRItems.GATE_GEAR.get()))
		.unlockedBy("has_"+LOTRItems.GATE_GEAR.get().getRegistryName().getPath(), has(LOTRItems.GATE_GEAR.get()))
		.save(consumer, mcLoc(Items.NOTE_BLOCK.getRegistryName().getPath()+"_using_gears"));
		ExtendedShapedRecipeBuilder.shaped(Items.TARGET)
		.pattern(" S ")
		.pattern("SHS")
		.pattern(" S ")
		.define('H', Ingredient.of(Items.HAY_BLOCK))
		.define('S', Ingredient.of(Tags.Items.STRING))
		.unlockedBy("has_"+Items.HAY_BLOCK.getRegistryName().getPath(), has(Items.HAY_BLOCK))
		.save(consumer, mcLoc(Items.TARGET.getRegistryName().getPath()+"_using_string"));
	}

	private void buildSmeltingRecipes(Consumer<IFinishedRecipe> consumer) {
		makeSmeltingRecipes(consumer, ExtendedItems.AMBER_ORE.get(), ExtendedItems.AMBER.get());
		makeSmeltingRecipes(consumer, ExtendedItems.AMETHYST_ORE.get(), ExtendedItems.AMETHYST.get());
		makeSmeltingRecipes(consumer, ExtendedItems.DIAMOND_ORE.get(), ExtendedItems.DIAMOND.get());
		makeSmeltingRecipes(consumer, ExtendedItems.EMERALD_ORE.get(), ExtendedItems.EMERALD.get());
		makeSmeltingRecipes(consumer, ExtendedItems.OPAL_ORE.get(), ExtendedItems.OPAL.get());
		makeSmeltingRecipes(consumer, ExtendedItems.RUBY_ORE.get(), ExtendedItems.RUBY.get());
		makeSmeltingRecipes(consumer, ExtendedItems.SAPPHIRE_ORE.get(), ExtendedItems.SAPPHIRE.get());
		makeSmeltingRecipes(consumer, ExtendedItems.TOPAZ_ORE.get(), ExtendedItems.TOPAZ.get());
	}

	private void buildSmithingRecipes(Consumer<IFinishedRecipe> consumer) {
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_GEM.get()).save(consumer, LotrExtendedMod.MODID+":gem_coloring");
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_ONE.get()).save(consumer, LotrExtendedMod.MODID+":coin_minting_one");
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_TEN.get()).save(consumer, LotrExtendedMod.MODID+":coin_minting_ten");
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_HUNDRED.get()).save(consumer, LotrExtendedMod.MODID+":coin_minting_hundred");
	}

	private static void makeSmeltingRecipes(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Item ore, Item result) {
		GENERATEDRECIPES.add(result.getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ore), result, 1.0F, 200)
		.unlockedBy("has_"+ore.getRegistryName().getPath(), has(ore))
		.unlockedBy("has_"+result.getRegistryName().getPath(), has(result))
		.save(pFinishedRecipeConsumer, result.getRegistryName().getPath()+"_from_smelting");

	}

	private void buildCookingRecipes(Consumer<IFinishedRecipe> consumer) {
		GENERATEDRECIPES.add(ExtendedItems.BUCKET_OF_CHEESE_CURDS.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(Items.MILK_BUCKET),
			ExtendedItems.BUCKET_OF_CHEESE_CURDS.get(), 0.15F, 200)
		.unlockedBy("has_"+Items.MILK_BUCKET.getRegistryName().getPath(), has(Items.MILK_BUCKET))
		.unlockedBy("has_"+ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_CURDS.get()))
		.unlockedBy("has_"+ExtendedItems.BUCKET_OF_CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.BUCKET_OF_CHEESE_CURDS.get()))
		.save(consumer, lotrLoc(ExtendedItems.BUCKET_OF_CHEESE_CURDS.get().getRegistryName().getPath()));
		// Conker
		GENERATEDRECIPES.add(ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ExtendedItems.CONKER.get()),
			ExtendedItems.ROASTED_CHESTNUT.get(), 0.15F, 200)
		.unlockedBy("has_"+ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath(), has(ExtendedItems.ROASTED_CHESTNUT.get()))
		.unlockedBy("has_"+ExtendedItems.CONKER.get().getRegistryName().getPath(), has(ExtendedItems.CONKER.get()))
		.save(consumer, lotrLoc(ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath()));

		// Duck
		GENERATEDRECIPES.add(ExtendedItems.COOKED_DUCK.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ExtendedItems.RAW_DUCK.get()),
			ExtendedItems.COOKED_DUCK.get(), 0.35F, 200)
		.unlockedBy("has_"+ExtendedItems.COOKED_DUCK.get().getRegistryName().getPath(), has(ExtendedItems.COOKED_DUCK.get()))
		.unlockedBy("has_"+ExtendedItems.RAW_DUCK.get().getRegistryName().getPath(), has(ExtendedItems.RAW_DUCK.get()))
		.save(consumer, lotrLoc(ExtendedItems.COOKED_DUCK.get().getRegistryName().getPath()));

		makeCookingRecipes(consumer, "smoking", IRecipeSerializer.SMOKING_RECIPE, 100);
		makeCookingRecipes(consumer, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600);
	}

	private void makeCookingRecipes(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, String pCookingMethod, CookingRecipeSerializer<?> pCookingSerializer, int pCookingTime) {
		CookingRecipeBuilder.cooking(Ingredient.of(ExtendedItems.CONKER.get()), ExtendedItems.ROASTED_CHESTNUT.get(), 0.15F, pCookingTime, pCookingSerializer).unlockedBy("has_conker", has(ExtendedItems.CONKER.get())).save(pFinishedRecipeConsumer, "cooked_conker_from_" + pCookingMethod);
		CookingRecipeBuilder.cooking(Ingredient.of(ExtendedItems.RAW_DUCK.get()), ExtendedItems.COOKED_DUCK.get(), 0.35F, pCookingTime, pCookingSerializer).unlockedBy("has_raw_duck", has(ExtendedItems.RAW_DUCK.get())).save(pFinishedRecipeConsumer, "cooked_duck_from_" + pCookingMethod);
	}

	private void makeDrinkRecipes(Consumer<IFinishedRecipe> consumer) {
		GENERATEDRECIPES.add(ExtendedItems.BANANA_BEER.get().getRegistryName().getPath());
		ExtendedVesselBrewingRecipeBuilder.brewing(ExtendedItems.BANANA_BEER.get(), LOTRRecipes.DRINK_BREWING_SERIALIZER.get())
		.requires(Ingredient.of(ExtendedItems.BANANA.get()))
		.requires(Ingredient.of(ExtendedItems.BANANA.get()))
		.requires(Ingredient.of(ExtendedItems.BANANA.get()))
		.requires(Ingredient.of(ExtendedItems.BANANA.get()))
		.requires(Ingredient.of(ExtendedItems.BANANA.get()))
		.requires(Ingredient.of(ExtendedItems.BANANA.get()))
		.save(consumer, lotrLoc(ExtendedItems.BANANA_BEER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.PLUM_KVASS.get().getRegistryName().getPath());
		ExtendedVesselBrewingRecipeBuilder.brewing(ExtendedItems.PLUM_KVASS.get(), LOTRRecipes.DRINK_BREWING_SERIALIZER.get())
		.requires(Ingredient.of(ExtendedItems.PLUM.get()))
		.requires(Ingredient.of(ExtendedItems.PLUM.get()))
		.requires(Ingredient.of(ExtendedItems.PLUM.get()))
		.requires(Ingredient.of(Items.WHEAT))
		.requires(Ingredient.of(Items.WHEAT))
		.requires(Ingredient.of(Items.WHEAT))
		.save(consumer, lotrLoc(ExtendedItems.PLUM_KVASS.get().getRegistryName().getPath()));
	}

	private void buildStoneGrinderRecipes(Consumer<IFinishedRecipe> consumer) {
		// Stone/Rock to cracked/gravel
		makeStoneGrinderRecipe(consumer, ()->Blocks.STONE, ()->Items.COBBLESTONE);
		makeStoneGrinderRecipe(consumer, ()->Blocks.COBBLESTONE, ()->Items.GRAVEL, 0.25f);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRItems.MORDOR_GRAVEL, 0.25f);
		// Gravel to items
		makeStoneGrinderRecipe(consumer, LOTRBlocks.MORDOR_GRAVEL, ()->Items.GRAVEL, 0.75f);
		makeStoneGrinderRecipe(consumer, ()->Blocks.GRAVEL, ()->Items.FLINT, 0.75f);
		//makeMillingRecipe(consumer, ExtendedBlocks.OBSIDIAN_GRAVEL, ExtendedItems.OBSIDIAN_SHARD, 0.75f);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.SALT_ORE, LOTRItems.SALT);
		// Sandstones to sand
		makeStoneGrinderRecipe(consumer, ()->Blocks.SANDSTONE, ()->Items.SAND, 2);
		makeStoneGrinderRecipe(consumer, ()->Blocks.RED_SANDSTONE, ()->Items.RED_SAND, 2);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.WHITE_SANDSTONE, LOTRItems.WHITE_SAND, 2);
		// Extended Gemstone grinding recipes
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.AMBER_ORE, ExtendedItems.AMBER, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.AMETHYST_ORE, ExtendedItems.AMETHYST, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.DIAMOND_ORE, ExtendedItems.DIAMOND, 1, 2);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.EMERALD_ORE, ExtendedItems.EMERALD, 1, 2);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.OPAL_ORE, ExtendedItems.OPAL, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RUBY_ORE, ExtendedItems.RUBY, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.SAPPHIRE_ORE, ExtendedItems.SAPPHIRE, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.TOPAZ_ORE, ExtendedItems.TOPAZ, 1, 3);
		// Renewed Gemstone grinding recipes
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DURNOR_ORE, LOTRItems.DURNOR, 2, 5);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.EDHELVIR_ORE, LOTRItems.EDHELVIR, 2, 4);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GLOWSTONE_ORE, ()->Items.GLOWSTONE_DUST, 1, 3);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GULDURIL_ORE_MORDOR, LOTRItems.GULDURIL, 1, 3);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GULDURIL_ORE_STONE, LOTRItems.GULDURIL, 1, 3);
		// Vanilla Gemstone grinding recipes
		makeStoneGrinderRecipe(consumer, ()->Blocks.DIAMOND_ORE, ()->Items.DIAMOND, 1, 2);
		makeStoneGrinderRecipe(consumer, ()->Blocks.EMERALD_ORE, ()->Items.EMERALD, 1, 2);
		makeStoneGrinderRecipe(consumer, ()->Blocks.LAPIS_ORE, ()->Items.LAPIS_LAZULI, 4, 9);
		makeStoneGrinderRecipe(consumer, ()->Blocks.REDSTONE_ORE, ()->Items.REDSTONE, 3, 5);
		// Vanilla blocks to cracked
		makeStoneGrinderRecipe(consumer, ()->Blocks.BRICKS, ()->Items.BRICK, 3);
		makeStoneGrinderRecipe(consumer, ()->Blocks.STONE_BRICKS, ()->Items.CRACKED_STONE_BRICKS);
		makeStoneGrinderRecipe(consumer, ()->Blocks.STONE_BRICK_SLAB, LOTRItems.CRACKED_STONE_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ()->Blocks.STONE_BRICK_STAIRS, LOTRItems.CRACKED_STONE_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ()->Blocks.STONE_BRICK_WALL, LOTRItems.CRACKED_STONE_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ()->Blocks.NETHER_BRICKS, ()->Items.CRACKED_NETHER_BRICKS);
		makeStoneGrinderRecipe(consumer, ()->Blocks.POLISHED_BLACKSTONE_BRICKS, ()->Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		// Renewed cracked recipes
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ANGMAR_BRICK, LOTRItems.CRACKED_ANGMAR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ANGMAR_BRICK_SLAB, LOTRItems.CRACKED_ANGMAR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ANGMAR_BRICK_STAIRS, LOTRItems.CRACKED_ANGMAR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ANGMAR_BRICK_WALL, LOTRItems.CRACKED_ANGMAR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ARNOR_BRICK, LOTRItems.CRACKED_ARNOR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ARNOR_BRICK_SLAB, LOTRItems.CRACKED_ARNOR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ARNOR_BRICK_STAIRS, LOTRItems.CRACKED_ARNOR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ARNOR_BRICK_WALL, LOTRItems.CRACKED_ARNOR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.BLUE_BRICK, LOTRItems.CRACKED_BLUE_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.BLUE_BRICK_SLAB, LOTRItems.CRACKED_BLUE_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.BLUE_BRICK_STAIRS, LOTRItems.CRACKED_BLUE_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.BLUE_BRICK_WALL, LOTRItems.CRACKED_BLUE_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DALE_BRICK, LOTRItems.CRACKED_DALE_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DALE_BRICK_SLAB, LOTRItems.CRACKED_DALE_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DALE_BRICK_STAIRS, LOTRItems.CRACKED_DALE_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DALE_BRICK_WALL, LOTRItems.CRACKED_DALE_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK, LOTRItems.CRACKED_DOL_AMROTH_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK_SLAB, LOTRItems.CRACKED_DOL_AMROTH_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK_STAIRS, LOTRItems.CRACKED_DOL_AMROTH_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK_WALL, LOTRItems.CRACKED_DOL_AMROTH_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DORWINION_BRICK, LOTRItems.CRACKED_DORWINION_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DORWINION_BRICK_SLAB, LOTRItems.CRACKED_DORWINION_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DORWINION_BRICK_STAIRS, LOTRItems.CRACKED_DORWINION_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DORWINION_BRICK_WALL, LOTRItems.CRACKED_DORWINION_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DWARVEN_BRICK, LOTRItems.CRACKED_DWARVEN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DWARVEN_BRICK_SLAB, LOTRItems.CRACKED_DWARVEN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DWARVEN_BRICK_STAIRS, LOTRItems.CRACKED_DWARVEN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.DWARVEN_BRICK_WALL, LOTRItems.CRACKED_DWARVEN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK, LOTRItems.CRACKED_GALADHRIM_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK_SLAB, LOTRItems.CRACKED_GALADHRIM_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK_STAIRS, LOTRItems.CRACKED_GALADHRIM_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK_WALL, LOTRItems.CRACKED_GALADHRIM_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_BRICK, LOTRItems.CRACKED_GONDOR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_BRICK_SLAB, LOTRItems.CRACKED_GONDOR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_BRICK_STAIRS, LOTRItems.CRACKED_GONDOR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_BRICK_WALL, LOTRItems.CRACKED_GONDOR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK, LOTRItems.CRACKED_GONDOR_COBBLEBRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK_SLAB, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK_STAIRS, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK_WALL, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK, LOTRItems.CRACKED_HIGH_ELVEN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK_SLAB, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK_STAIRS, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK_WALL, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.MORDOR_BRICK, LOTRItems.CRACKED_MORDOR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.MORDOR_BRICK_SLAB, LOTRItems.CRACKED_MORDOR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.MORDOR_BRICK_STAIRS, LOTRItems.CRACKED_MORDOR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.MORDOR_BRICK_WALL, LOTRItems.CRACKED_MORDOR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, LOTRItems.CRACKED_NUMENOREAN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK_SLAB, LOTRItems.CRACKED_NUMENOREAN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK_STAIRS, LOTRItems.CRACKED_NUMENOREAN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK_WALL, LOTRItems.CRACKED_NUMENOREAN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_BRICK, LOTRItems.CRACKED_RED_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_BRICK_SLAB, LOTRItems.CRACKED_RED_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_BRICK_STAIRS, LOTRItems.CRACKED_RED_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_BRICK_WALL, LOTRItems.CRACKED_RED_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK, LOTRItems.CRACKED_RED_DORWINION_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK_SLAB, LOTRItems.CRACKED_RED_DORWINION_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK_STAIRS, LOTRItems.CRACKED_RED_DORWINION_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK_WALL, LOTRItems.CRACKED_RED_DORWINION_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ROHAN_BRICK, LOTRItems.CRACKED_ROHAN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ROHAN_BRICK_SLAB, LOTRItems.CRACKED_ROHAN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ROHAN_BRICK_STAIRS, LOTRItems.CRACKED_ROHAN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ROHAN_BRICK_WALL, LOTRItems.CRACKED_ROHAN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.UMBAR_BRICK, LOTRItems.CRACKED_UMBAR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.UMBAR_BRICK_SLAB, LOTRItems.CRACKED_UMBAR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.UMBAR_BRICK_STAIRS, LOTRItems.CRACKED_UMBAR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.UMBAR_BRICK_WALL, LOTRItems.CRACKED_UMBAR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK, LOTRItems.CRACKED_WOOD_ELVEN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK_SLAB, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK_STAIRS, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK_WALL, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_WALL);
		// Extended cracked recipes from renewed blocks
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ARNOR_PILLAR, ExtendedItems.CRACKED_ARNOR_PILLAR);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.ARNOR_PILLAR_SLAB, ExtendedItems.CRACKED_ARNOR_PILLAR_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.STONE_PILLAR, ExtendedItems.CRACKED_STONE_PILLAR);
		makeStoneGrinderRecipe(consumer, LOTRBlocks.STONE_PILLAR_SLAB, ExtendedItems.CRACKED_STONE_PILLAR_SLAB);
		// Extended cracked block recipes
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK, ExtendedItems.CRACKED_DOL_GULDUR_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK_WALL, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.MUD_BRICK, ExtendedItems.CRACKED_MUD_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.MUD_BRICK_SLAB, ExtendedItems.CRACKED_MUD_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.MUD_BRICK_STAIRS, ExtendedItems.CRACKED_MUD_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.MUD_BRICK_WALL, ExtendedItems.CRACKED_MUD_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK, ExtendedItems.CRACKED_RED_RHUNIC_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK_SLAB, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK_WALL, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK, ExtendedItems.CRACKED_RHUNIC_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK_SLAB, ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK_STAIRS, ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK_WALL, ExtendedItems.CRACKED_RHUNIC_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK, ExtendedItems.CRACKED_TAURETHRIM_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK_SLAB, ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK_STAIRS, ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK_WALL, ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL);
	}

	private void buildStonecutterRecipes(Consumer<IFinishedRecipe> consumer) {
		//Add missing recipes for renewed blocks
		makeStoneCutterRecipe(consumer, () ->Blocks.COBBLESTONE, LOTRBlocks.DRYSTONE);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.CARVED_ROHAN_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_PILLAR);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_PILLAR, LOTRBlocks.ROHAN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_ROCK, LOTRBlocks.ROHAN_ROCK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_BRICK, LOTRBlocks.ROHAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_BRICK, LOTRBlocks.ROHAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_BRICK, LOTRBlocks.ROHAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ROHAN_BRICK, LOTRBlocks.CARVED_ROHAN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_ROHAN_ROCK, LOTRBlocks.SMOOTH_ROHAN_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ROHAN_BRICK, LOTRBlocks.MOSSY_ROHAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ROHAN_BRICK, LOTRBlocks.MOSSY_ROHAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ROHAN_BRICK, LOTRBlocks.MOSSY_ROHAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ROHAN_BRICK, LOTRBlocks.CRACKED_ROHAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ROHAN_BRICK, LOTRBlocks.CRACKED_ROHAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ROHAN_BRICK, LOTRBlocks.CRACKED_ROHAN_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.CARVED_GONDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_PILLAR);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_PILLAR, LOTRBlocks.GONDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_ROCK, LOTRBlocks.GONDOR_ROCK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_BRICK, LOTRBlocks.GONDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_BRICK, LOTRBlocks.GONDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_BRICK, LOTRBlocks.GONDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_BRICK, LOTRBlocks.CARVED_GONDOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_GONDOR_ROCK, LOTRBlocks.SMOOTH_GONDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GONDOR_BRICK, LOTRBlocks.MOSSY_GONDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GONDOR_BRICK, LOTRBlocks.MOSSY_GONDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GONDOR_BRICK, LOTRBlocks.MOSSY_GONDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GONDOR_BRICK, LOTRBlocks.CRACKED_GONDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GONDOR_BRICK, LOTRBlocks.CRACKED_GONDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GONDOR_BRICK, LOTRBlocks.CRACKED_GONDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK, LOTRBlocks.GONDOR_COBBLEBRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK, LOTRBlocks.GONDOR_COBBLEBRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GONDOR_COBBLEBRICK, LOTRBlocks.GONDOR_COBBLEBRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GONDOR_COBBLEBRICK, LOTRBlocks.MOSSY_GONDOR_COBBLEBRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GONDOR_COBBLEBRICK, LOTRBlocks.MOSSY_GONDOR_COBBLEBRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GONDOR_COBBLEBRICK, LOTRBlocks.MOSSY_GONDOR_COBBLEBRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GONDOR_COBBLEBRICK, LOTRBlocks.CRACKED_GONDOR_COBBLEBRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GONDOR_COBBLEBRICK, LOTRBlocks.CRACKED_GONDOR_COBBLEBRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GONDOR_COBBLEBRICK, LOTRBlocks.CRACKED_GONDOR_COBBLEBRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_PILLAR, LOTRBlocks.NUMENOREAN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, LOTRBlocks.NUMENOREAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, LOTRBlocks.NUMENOREAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, LOTRBlocks.NUMENOREAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, LOTRBlocks.CARVED_NUMENOREAN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_NUMENOREAN_BRICK, LOTRBlocks.MOSSY_NUMENOREAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_NUMENOREAN_BRICK, LOTRBlocks.MOSSY_NUMENOREAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_NUMENOREAN_BRICK, LOTRBlocks.MOSSY_NUMENOREAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_NUMENOREAN_BRICK, LOTRBlocks.CRACKED_NUMENOREAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_NUMENOREAN_BRICK, LOTRBlocks.CRACKED_NUMENOREAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_NUMENOREAN_BRICK, LOTRBlocks.CRACKED_NUMENOREAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK, LOTRBlocks.DOL_AMROTH_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK, LOTRBlocks.DOL_AMROTH_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DOL_AMROTH_BRICK, LOTRBlocks.DOL_AMROTH_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DOL_AMROTH_BRICK, LOTRBlocks.MOSSY_DOL_AMROTH_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DOL_AMROTH_BRICK, LOTRBlocks.MOSSY_DOL_AMROTH_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DOL_AMROTH_BRICK, LOTRBlocks.MOSSY_DOL_AMROTH_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DOL_AMROTH_BRICK, LOTRBlocks.CRACKED_DOL_AMROTH_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DOL_AMROTH_BRICK, LOTRBlocks.CRACKED_DOL_AMROTH_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DOL_AMROTH_BRICK, LOTRBlocks.CRACKED_DOL_AMROTH_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.CARVED_MORDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_PILLAR);
		//		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_PILLAR, LOTRBlocks.MORDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_ROCK, LOTRBlocks.MORDOR_ROCK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BRICK, LOTRBlocks.MORDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BRICK, LOTRBlocks.MORDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BRICK, LOTRBlocks.MORDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BRICK, LOTRBlocks.CARVED_MORDOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_MORDOR_ROCK, LOTRBlocks.SMOOTH_MORDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_MORDOR_BRICK, LOTRBlocks.MOSSY_MORDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_MORDOR_BRICK, LOTRBlocks.MOSSY_MORDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_MORDOR_BRICK, LOTRBlocks.MOSSY_MORDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_MORDOR_BRICK, LOTRBlocks.CRACKED_MORDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_MORDOR_BRICK, LOTRBlocks.CRACKED_MORDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_MORDOR_BRICK, LOTRBlocks.CRACKED_MORDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BASALT_BRICK, LOTRBlocks.MORDOR_BASALT_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BASALT_BRICK, LOTRBlocks.MORDOR_BASALT_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BASALT_BRICK, LOTRBlocks.MORDOR_BASALT_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MORDOR_BASALT_BRICK, LOTRBlocks.CARVED_MORDOR_BASALT_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HIGH_ELVEN_PILLAR, LOTRBlocks.HIGH_ELVEN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK, LOTRBlocks.HIGH_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK, LOTRBlocks.HIGH_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK, LOTRBlocks.HIGH_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HIGH_ELVEN_BRICK, LOTRBlocks.CARVED_HIGH_ELVEN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK, LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK, LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK, LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK, LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK, LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK, LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GALADHRIM_PILLAR, LOTRBlocks.GALADHRIM_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK, LOTRBlocks.GALADHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK, LOTRBlocks.GALADHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK, LOTRBlocks.GALADHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.GALADHRIM_BRICK, LOTRBlocks.CARVED_GALADHRIM_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GALADHRIM_BRICK, LOTRBlocks.MOSSY_GALADHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GALADHRIM_BRICK, LOTRBlocks.MOSSY_GALADHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_GALADHRIM_BRICK, LOTRBlocks.MOSSY_GALADHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GALADHRIM_BRICK, LOTRBlocks.CRACKED_GALADHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GALADHRIM_BRICK, LOTRBlocks.CRACKED_GALADHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_GALADHRIM_BRICK, LOTRBlocks.CRACKED_GALADHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.WOOD_ELVEN_PILLAR, LOTRBlocks.WOOD_ELVEN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK, LOTRBlocks.WOOD_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK, LOTRBlocks.WOOD_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK, LOTRBlocks.WOOD_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.WOOD_ELVEN_BRICK, LOTRBlocks.CARVED_WOOD_ELVEN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK, LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK, LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK, LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK, LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK, LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK, LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HARAD_PILLAR, LOTRBlocks.HARAD_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HARAD_BRICK, LOTRBlocks.HARAD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HARAD_BRICK, LOTRBlocks.HARAD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.HARAD_BRICK, LOTRBlocks.HARAD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_HARAD_PILLAR, LOTRBlocks.RED_HARAD_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_HARAD_BRICK, LOTRBlocks.RED_HARAD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_HARAD_BRICK, LOTRBlocks.RED_HARAD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_HARAD_BRICK, LOTRBlocks.RED_HARAD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.UMBAR_PILLAR, LOTRBlocks.UMBAR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.UMBAR_BRICK, LOTRBlocks.UMBAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.UMBAR_BRICK, LOTRBlocks.UMBAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.UMBAR_BRICK, LOTRBlocks.UMBAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_UMBAR_BRICK, LOTRBlocks.MOSSY_UMBAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_UMBAR_BRICK, LOTRBlocks.MOSSY_UMBAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_UMBAR_BRICK, LOTRBlocks.MOSSY_UMBAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_UMBAR_BRICK, LOTRBlocks.CRACKED_UMBAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_UMBAR_BRICK, LOTRBlocks.CRACKED_UMBAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_UMBAR_BRICK, LOTRBlocks.CRACKED_UMBAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ARNOR_PILLAR, LOTRBlocks.ARNOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ARNOR_BRICK, LOTRBlocks.ARNOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ARNOR_BRICK, LOTRBlocks.ARNOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ARNOR_BRICK, LOTRBlocks.ARNOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ARNOR_BRICK, LOTRBlocks.CARVED_ARNOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ARNOR_BRICK, LOTRBlocks.MOSSY_ARNOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ARNOR_BRICK, LOTRBlocks.MOSSY_ARNOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ARNOR_BRICK, LOTRBlocks.MOSSY_ARNOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ARNOR_BRICK, LOTRBlocks.CRACKED_ARNOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ARNOR_BRICK, LOTRBlocks.CRACKED_ARNOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ARNOR_BRICK, LOTRBlocks.CRACKED_ARNOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DORWINION_PILLAR, LOTRBlocks.DORWINION_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DORWINION_BRICK, LOTRBlocks.DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DORWINION_BRICK, LOTRBlocks.DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DORWINION_BRICK, LOTRBlocks.DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DORWINION_BRICK, LOTRBlocks.MOSSY_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DORWINION_BRICK, LOTRBlocks.MOSSY_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DORWINION_BRICK, LOTRBlocks.MOSSY_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DORWINION_BRICK, LOTRBlocks.CRACKED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DORWINION_BRICK, LOTRBlocks.CRACKED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DORWINION_BRICK, LOTRBlocks.CRACKED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK, LOTRBlocks.RED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK, LOTRBlocks.RED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.RED_DORWINION_BRICK, LOTRBlocks.RED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_RED_DORWINION_BRICK, LOTRBlocks.MOSSY_RED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_RED_DORWINION_BRICK, LOTRBlocks.MOSSY_RED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_RED_DORWINION_BRICK, LOTRBlocks.MOSSY_RED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_RED_DORWINION_BRICK, LOTRBlocks.CRACKED_RED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_RED_DORWINION_BRICK, LOTRBlocks.CRACKED_RED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_RED_DORWINION_BRICK, LOTRBlocks.CRACKED_RED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.URUK_PILLAR, LOTRBlocks.URUK_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.URUK_BRICK, LOTRBlocks.URUK_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.URUK_BRICK, LOTRBlocks.URUK_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.URUK_BRICK, LOTRBlocks.URUK_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DWARVEN_PILLAR, LOTRBlocks.DWARVEN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DWARVEN_BRICK, LOTRBlocks.DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DWARVEN_BRICK, LOTRBlocks.DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DWARVEN_BRICK, LOTRBlocks.DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DWARVEN_BRICK, LOTRBlocks.CARVED_DWARVEN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DWARVEN_BRICK, LOTRBlocks.MOSSY_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DWARVEN_BRICK, LOTRBlocks.MOSSY_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DWARVEN_BRICK, LOTRBlocks.MOSSY_DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DWARVEN_BRICK, LOTRBlocks.CRACKED_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DWARVEN_BRICK, LOTRBlocks.CRACKED_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DWARVEN_BRICK, LOTRBlocks.CRACKED_DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOWY_DWARVEN_BRICK, LOTRBlocks.SNOWY_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOWY_DWARVEN_BRICK, LOTRBlocks.SNOWY_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOWY_DWARVEN_BRICK, LOTRBlocks.SNOWY_DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_PILLAR, LOTRBlocks.DALE_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_BRICK, LOTRBlocks.DALE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_BRICK, LOTRBlocks.DALE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_BRICK, LOTRBlocks.DALE_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_BRICK, LOTRBlocks.CARVED_DALE_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DALE_BRICK, LOTRBlocks.MOSSY_DALE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DALE_BRICK, LOTRBlocks.MOSSY_DALE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DALE_BRICK, LOTRBlocks.MOSSY_DALE_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DALE_BRICK, LOTRBlocks.CRACKED_DALE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DALE_BRICK, LOTRBlocks.CRACKED_DALE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_DALE_BRICK, LOTRBlocks.CRACKED_DALE_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_PAVING, LOTRBlocks.DALE_PAVING_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_PAVING, LOTRBlocks.DALE_PAVING_STAIRS);
		//makeStoneCutterRecipe(consumer, LOTRBlocks.DALE_PAVING, LOTRBlocks.DALE_PAVING_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DALE_PAVING, LOTRBlocks.MOSSY_DALE_PAVING_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DALE_PAVING, LOTRBlocks.MOSSY_DALE_PAVING_STAIRS);
		//makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_DALE_PAVING, LOTRBlocks.MOSSY_DALE_PAVING_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ANGMAR_PILLAR, LOTRBlocks.ANGMAR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ANGMAR_BRICK, LOTRBlocks.ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ANGMAR_BRICK, LOTRBlocks.ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ANGMAR_BRICK, LOTRBlocks.ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ANGMAR_BRICK, LOTRBlocks.CARVED_ANGMAR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ANGMAR_BRICK, LOTRBlocks.MOSSY_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ANGMAR_BRICK, LOTRBlocks.MOSSY_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.MOSSY_ANGMAR_BRICK, LOTRBlocks.MOSSY_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ANGMAR_BRICK, LOTRBlocks.CRACKED_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ANGMAR_BRICK, LOTRBlocks.CRACKED_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.CRACKED_ANGMAR_BRICK, LOTRBlocks.CRACKED_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOWY_ANGMAR_BRICK, LOTRBlocks.SNOWY_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOWY_ANGMAR_BRICK, LOTRBlocks.SNOWY_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOWY_ANGMAR_BRICK, LOTRBlocks.SNOWY_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOW_BRICK, LOTRBlocks.SNOW_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOW_BRICK, LOTRBlocks.SNOW_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SNOW_BRICK, LOTRBlocks.SNOW_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ICE_BRICK, LOTRBlocks.ICE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ICE_BRICK, LOTRBlocks.ICE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.ICE_BRICK, LOTRBlocks.ICE_BRICK_WALL);

		//Add extended blocks recipes for renewed blocks
		makeStoneCutterRecipe(consumer, ExtendedBlocks.COBBLED_DRYSTONE, LOTRBlocks.DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CUT_DRYSTONE, LOTRBlocks.DRYSTONE);

		//Add vanilla dependent recipes for extended blocks
		makeStoneCutterRecipe(consumer, () ->Blocks.SMOOTH_STONE, ExtendedBlocks.SMOOTH_STONE_STAIRS);
		makeStoneCutterRecipe(consumer, () ->Blocks.BONE_BLOCK, ExtendedBlocks.BONE_BLOCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, () ->Blocks.BONE_BLOCK, ExtendedBlocks.BONE_BLOCK_STAIRS);
		makeStoneCutterRecipe(consumer, () ->Blocks.BONE_BLOCK, ExtendedBlocks.BONE_BLOCK_WALL);

		//Add renewed dependent recipes for extended blocks
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.NUMENOREAN_BRICK, ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.UMBAR_BRICK, ExtendedBlocks.CARVED_UMBAR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_BLUE_ROCK, ExtendedBlocks.SMOOTH_BLUE_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_RED_ROCK, ExtendedBlocks.SMOOTH_RED_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_MORDOR_ROCK, ExtendedBlocks.SMOOTH_MORDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_GONDOR_ROCK, ExtendedBlocks.SMOOTH_GONDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.SMOOTH_ROHAN_ROCK, ExtendedBlocks.SMOOTH_ROHAN_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DRYSTONE, ExtendedBlocks.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DRYSTONE, ExtendedBlocks.COBBLED_DRYSTONE);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DRYSTONE, ExtendedBlocks.DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DRYSTONE, ExtendedBlocks.DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRBlocks.DRYSTONE, ExtendedBlocks.DRYSTONE_WALL);

		//Add extended blocks recipes
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD, ExtendedBlocks.MUD_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD, ExtendedBlocks.MUD_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD, ExtendedBlocks.MUD_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD, ExtendedBlocks.MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD, ExtendedBlocks.MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD, ExtendedBlocks.MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD_BRICK, ExtendedBlocks.MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD_BRICK, ExtendedBlocks.MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD_BRICK, ExtendedBlocks.MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_MUD_BRICK, ExtendedBlocks.CRACKED_MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_MUD_BRICK, ExtendedBlocks.CRACKED_MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_MUD_BRICK, ExtendedBlocks.CRACKED_MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_MUD_BRICK, ExtendedBlocks.MOSSY_MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_MUD_BRICK, ExtendedBlocks.MOSSY_MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_MUD_BRICK, ExtendedBlocks.MOSSY_MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_PATH_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MUD_PATH, ExtendedBlocks.MUD_PATH_STAIRS);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_STONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_STONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_STONE_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_STONE, ExtendedBlocks.SCORCHED_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_BRICK, ExtendedBlocks.SCORCHED_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_BRICK, ExtendedBlocks.SCORCHED_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_BRICK, ExtendedBlocks.SCORCHED_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_COBBLESTONE, ExtendedBlocks.SCORCHED_COBBLESTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_COBBLESTONE, ExtendedBlocks.SCORCHED_COBBLESTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.SCORCHED_COBBLESTONE, ExtendedBlocks.SCORCHED_COBBLESTONE_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE, ExtendedBlocks.MOSSY_DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE, ExtendedBlocks.MOSSY_DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE, ExtendedBlocks.MOSSY_DRYSTONE_WALL);
		makeStoneCutterRecipe(consumer, () ->Blocks.COBBLESTONE, ExtendedBlocks.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, () ->Blocks.COBBLESTONE, ExtendedBlocks.COBBLED_DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.COBBLED_DRYSTONE, ExtendedBlocks.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CUT_DRYSTONE, ExtendedBlocks.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.COBBLED_DRYSTONE, ExtendedBlocks.DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.COBBLED_DRYSTONE, ExtendedBlocks.DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.COBBLED_DRYSTONE, ExtendedBlocks.DRYSTONE_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CUT_DRYSTONE, ExtendedBlocks.DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CUT_DRYSTONE, ExtendedBlocks.DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CUT_DRYSTONE, ExtendedBlocks.DRYSTONE_WALL);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK, ExtendedBlocks.TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK, ExtendedBlocks.TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.TAURETHRIM_BRICK, ExtendedBlocks.TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.TAURETHRIM_PILLAR, ExtendedBlocks.TAURETHRIM_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK, ExtendedBlocks.MOSSY_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK, ExtendedBlocks.CRACKED_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK, ExtendedBlocks.GOLDEN_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR, ExtendedBlocks.GOLDEN_TAURETHRIM_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK, ExtendedBlocks.OBSIDIAN_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR, ExtendedBlocks.OBSIDIAN_TAURETHRIM_PILLAR_SLAB);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOBLIN_BRICK, ExtendedBlocks.GOBLIN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOBLIN_BRICK, ExtendedBlocks.GOBLIN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOBLIN_BRICK, ExtendedBlocks.GOBLIN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOBLIN_PILLAR, ExtendedBlocks.GOBLIN_PILLAR_SLAB);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK, ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK, ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK, ExtendedBlocks.DOL_GULDUR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.DOL_GULDUR_PILLAR, ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK, ExtendedBlocks.CARVED_DOL_GULDUR_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK, ExtendedBlocks.MOSSY_DOL_GULDUR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK, ExtendedBlocks.CRACKED_DOL_GULDUR_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK, ExtendedBlocks.RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK, ExtendedBlocks.RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK, ExtendedBlocks.RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RHUNIC_PILLAR, ExtendedBlocks.RHUNIC_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK, ExtendedBlocks.CARVED_RHUNIC_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_RHUNIC_BRICK, ExtendedBlocks.MOSSY_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_RHUNIC_BRICK, ExtendedBlocks.MOSSY_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_RHUNIC_BRICK, ExtendedBlocks.MOSSY_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_RHUNIC_BRICK, ExtendedBlocks.CRACKED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_RHUNIC_BRICK, ExtendedBlocks.CRACKED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_RHUNIC_BRICK, ExtendedBlocks.CRACKED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK, ExtendedBlocks.GOLD_TRIMMED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.FLOWERY_RHUNIC_BRICK, ExtendedBlocks.FLOWERY_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.FLOWERY_RHUNIC_BRICK, ExtendedBlocks.FLOWERY_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.FLOWERY_RHUNIC_BRICK, ExtendedBlocks.FLOWERY_RHUNIC_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK, ExtendedBlocks.RED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK, ExtendedBlocks.RED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK, ExtendedBlocks.RED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RED_RHUNIC_PILLAR, ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.RED_RHUNIC_BRICK, ExtendedBlocks.CARVED_RED_RHUNIC_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK, ExtendedBlocks.MOSSY_RED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK, ExtendedBlocks.CRACKED_RED_RHUNIC_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_ARNOR_PILLAR, ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.MOSSY_ARNOR_PILLAR, ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedBlocks.CRACKED_STONE_PILLAR, ExtendedBlocks.CRACKED_STONE_PILLAR_SLAB);

//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_ROCK, ExtendedBlocks.GNEISS_ROCK_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_ROCK, ExtendedBlocks.GNEISS_ROCK_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_ROCK, ExtendedBlocks.GNEISS_ROCK_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_ROCK, ExtendedBlocks.GNEISS_BRICK);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_BRICK, ExtendedBlocks.GNEISS_BRICK_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_BRICK, ExtendedBlocks.GNEISS_BRICK_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_BRICK, ExtendedBlocks.GNEISS_BRICK_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_BRICK, ExtendedBlocks.POLISHED_GNEISS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.GNEISS_ROCK, ExtendedBlocks.POLISHED_GNEISS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_GNEISS, ExtendedBlocks.POLISHED_GNEISS_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_GNEISS, ExtendedBlocks.POLISHED_GNEISS_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_GNEISS, ExtendedBlocks.POLISHED_GNEISS_WALL);
//
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_ROCK_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_ROCK_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_ROCK_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_BRICK);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_BRICK_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_BRICK_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_BRICK_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_TILE);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_TILE);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_TILE, ExtendedBlocks.LIMESTONE_TILE_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_TILE, ExtendedBlocks.LIMESTONE_TILE_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_TILE, ExtendedBlocks.LIMESTONE_TILE_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_TILE_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_TILE_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.LIMESTONE_TILE_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_TILE_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_TILE_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_BRICK, ExtendedBlocks.LIMESTONE_TILE_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.POLISHED_LIMESTONE);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_LIMESTONE, ExtendedBlocks.POLISHED_LIMESTONE_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_LIMESTONE, ExtendedBlocks.POLISHED_LIMESTONE_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_LIMESTONE, ExtendedBlocks.POLISHED_LIMESTONE_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.LIMESTONE_ROCK, ExtendedBlocks.SMOOTH_LIMESTONE);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_LIMESTONE, ExtendedBlocks.SMOOTH_LIMESTONE);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SMOOTH_LIMESTONE, ExtendedBlocks.SMOOTH_LIMESTONE_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SMOOTH_LIMESTONE, ExtendedBlocks.SMOOTH_LIMESTONE_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SMOOTH_LIMESTONE, ExtendedBlocks.SMOOTH_LIMESTONE_WALL);
//
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_ROCK, ExtendedBlocks.SLATE_ROCK_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_ROCK, ExtendedBlocks.SLATE_ROCK_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_ROCK, ExtendedBlocks.SLATE_ROCK_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_ROCK, ExtendedBlocks.SLATE_BRICK);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_BRICK, ExtendedBlocks.SLATE_BRICK_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_BRICK, ExtendedBlocks.SLATE_BRICK_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_BRICK, ExtendedBlocks.SLATE_BRICK_WALL);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.SLATE_ROCK, ExtendedBlocks.POLISHED_SLATE);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_SLATE, ExtendedBlocks.POLISHED_SLATE_SLAB, 2);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_SLATE, ExtendedBlocks.POLISHED_SLATE_STAIRS);
//		makeStoneCutterRecipe(consumer, ExtendedBlocks.POLISHED_SLATE, ExtendedBlocks.POLISHED_SLATE_WALL);
	}

	@SuppressWarnings("deprecation")
	private void addOverrides() {
		GENERATEDRECIPES.add(ExtendedItems.LEATHER_HAT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_BOOTS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLACKBERRY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLUEBERRY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CRANBERRY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ELDERBERRY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RASPBERRY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILDBERRY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLACKBERRY_HEDGE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLUEBERRY_HEDGE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CRANBERRY_HEDGE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ELDERBERRY_HEDGE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RASPBERRY_HEDGE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILDBERRY_HEDGE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BLACKBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BLUEBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.CRANBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.ELDERBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.RASPBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.WILDBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SILVER_COIN_TEN.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SILVER_COIN_HUNDRED.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.AMBER_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.AMETHYST_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DIAMOND_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.EMERALD_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.OPAL_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.RUBY_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.SAPPHIRE_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.TOPAZ_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.DWARF_MARRIAGE_RING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_MARRIAGE_RING.get().getRegistryName().getPath());
		for(RegistryObject<Block> block :ExtendedBlocks.ENTITYSPAWNERBLOCKS.values()) {
			GENERATEDRECIPES.add(block.get().getRegistryName().getPath());
		}
		for(RegistryObject<Block> block :ExtendedBlocks.ENTITYGROUPSPAWNERBLOCKS.values()) {
			GENERATEDRECIPES.add(block.get().getRegistryName().getPath());
		}
		GENERATEDRECIPES.add(ExtendedItems.AMBER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.AMETHYST.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.DIAMOND.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EMERALD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.OPAL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RUBY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SAPPHIRE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.TOPAZ.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CORAL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.PEARL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLACK_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GRAY_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SILVER_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WHITE_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.REMAINS_ELF.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.REMAINS_MAN.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.REMAINS_ORC.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.REMAINS_DWARF.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.MUD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.MUD_FARMLAND.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.MUD_PATH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.MUD_GRASS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHEESE_SLICE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CONKER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GREEN_FIRE_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.STONE_CHEST.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.CHESTNUT_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.STRIPPED_CHESTNUT_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_CHESTNUT_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.CHESTNUT_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.CHESTNUT_LEAVES_FRUIT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.CHESTNUT_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BANANA.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BANANA_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.STRIPPED_BANANA_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_BANANA_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BANANA_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BANANA_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.HANGING_BANANA.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.AGED_CHEESE_WHEEL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.AGED_CHEESE_SLICE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.DUNEDAIN_BOW.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DUNEDAIN_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BARROW_BLADE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.HOBBIT_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DURINS_FOLK_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BLUE_MOUNTAINS_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GUNDABAD_ORC_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.DUCK_EGG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ENTITY_SPAWNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.PLUM.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.PLUM_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.STRIPPED_PLUM_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_PLUM_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.PLUM_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.PLUM_LEAVES_FRUIT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.PLUM_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RAW_DUCK.get().getRegistryName().getPath());
//		GENERATEDRECIPES.add(ExtendedBlocks.SLATE_ROCK.get().getRegistryName().getPath());
//		GENERATEDRECIPES.add(ExtendedBlocks.LIMESTONE_ROCK.get().getRegistryName().getPath());
//		GENERATEDRECIPES.add(ExtendedBlocks.GNEISS_ROCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.MOSS_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.ANCIENT_RUNE_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLACK_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BROWN_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GRAY_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SILVER_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WHITE_WARG_HELMET.get().getRegistryName().getPath());
		for(RegistryObject<Block> blo : ExtendedBlocks.REGISTEREDPOTS.values()) {
			GENERATEDRECIPES.add(blo.get().getRegistryName().getPath());
		}
		GENERATEDRECIPES.add(ExtendedBlocks.BREE_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.WILLOW_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.STRIPPED_WILLOW_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_WILLOW_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.WILLOW_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.WILLOW_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILLOW_VINES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GRASS_VOID.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.STONE_SIGN.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GLASS_BOTTLE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.MODIFIER_SCROLL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.IMLADRIS_GUARD_BOOTS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.IMLADRIS_GUARD_LEGGINGS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.IMLADRIS_GUARD_CHESTPLATE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.IMLADRIS_GUARD_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.IMLADRIS_GUARD_SHIELD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.ANGMAR_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DALE_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GONDOR_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.ISENGARD_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.WOODLAND_REALM_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.ROHAN_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DORWINION_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DUNLAND_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.RIVENDELL_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.LINDON_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DOL_GULDUR_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.DOL_AMROTH_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.LOTHLORIEN_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.MORDOR_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ANDURIL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SHARDS_OF_NARSIL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.PLACED_BOOKS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.TAPESTRY.get().getRegistryName().getPath());
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recipe Provider";
	}
}