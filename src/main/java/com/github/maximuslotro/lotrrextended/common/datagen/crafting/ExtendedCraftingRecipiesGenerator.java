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
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeSerializers;

import lotr.common.init.ExtendedRecipes;
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
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

// Important! Only add unlockedBy criteria for ingredients, output should not unlock recipe
public class ExtendedCraftingRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		buildRecipes(consumer);
		addMissingRenewedRecipes(consumer);
		addMissingVanillaRecipes(consumer);
		buildRenewedRecipeOverrides(consumer);
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
		makeBedRecipe(consumer, ExtendedItems.STRAW_BED, Tags.Items.CROPS_WHEAT, null);
		makeBedRecipeTag(consumer, ExtendedItems.ORC_BED, ExtendedTags.Items.WOOL_BLOCKS, LOTRRecipes.ANY_ORC);
		makeBedRecipe(consumer, ExtendedItems.BLACK_FUR_BED, ExtendedItems.BLACK_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedItems.FUR_BED, LOTRItems.FUR.get(), null);
		makeBedRecipe(consumer, ExtendedItems.GRAY_FUR_BED, ExtendedItems.GRAY_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedItems.SILVER_FUR_BED, ExtendedItems.SILVER_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedItems.WHITE_FUR_BED, ExtendedItems.WHITE_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedItems.DWARVEN_BED, ExtendedTags.Items.WOOL_BLOCKS, LOTRRecipes.ANY_DWARVEN);
		makeBedRecipe(consumer, ExtendedItems.HIGH_ELVEN_BED, ExtendedTags.Items.WOOL_BLOCKS, LOTRRecipes.ANY_HIGH_ELVEN);
		makeCarpetRecipe(consumer, ExtendedItems.BLACK_FUR_CARPET, ExtendedItems.BLACK_FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedItems.BROWN_FUR_CARPET, LOTRItems.FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedItems.GRAY_FUR_CARPET, ExtendedItems.GRAY_FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedItems.SILVER_FUR_CARPET, ExtendedItems.SILVER_FUR, LOTRRecipes.ANY_ORC);
		makeCarpetRecipe(consumer, ExtendedItems.WHITE_FUR_CARPET, ExtendedItems.WHITE_FUR, LOTRRecipes.ANY_ORC);
		GENERATEDRECIPES.add(ExtendedItems.BEDROLL.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BEDROLL.get())
		.pattern("LLF")
		.define('L', Ingredient.of(Tags.Items.LEATHER))
		.define('F', Ingredient.of(ExtendedTags.Items.FURS))
		.unlockedBy("has_"+LOTRItems.FUR.get().getRegistryName().getPath(), has(ExtendedTags.Items.FURS))
		.save(consumer, ExtendedItems.BEDROLL);
		/////////////////WOOD
		//CHESTNUT
		makeBeamSlabRecipe(consumer, ExtendedItems.CHESTNUT_BEAM,
			ExtendedItems.CHESTNUT_BEAM_SLAB, ExtendedItems.CHESTNUT_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.CHESTNUT_BOAT, ExtendedItems.CHESTNUT_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedItems.CHESTNUT_BRANCH, ExtendedItems.CHESTNUT_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.CHESTNUT_BUTTON, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.CHESTNUT_DOOR, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedItems.CHESTNUT_FENCE, ExtendedItems.CHESTNUT_FENCE_GATE,
			ExtendedItems.CHESTNUT_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedItems.CHESTNUT_LOG_SLAB, ExtendedItems.CHESTNUT_LOG, null, "");
		makeWoodenPressurePlateRecipe(consumer, ExtendedItems.CHESTNUT_PRESSURE_PLATE, ExtendedItems.CHESTNUT_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.CHESTNUT_STAIRS, ExtendedItems.CHESTNUT_SLAB, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedItems.CHESTNUT_TRAPDOOR, ExtendedItems.CHESTNUT_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.CHESTNUT_WOOD_STAIRS, ExtendedItems.CHESTNUT_WOOD_SLAB, ExtendedItems.CHESTNUT_WOOD, null);
		makeSignRecipe(consumer, ExtendedItems.CHESTNUT_SIGN, ExtendedBlocks.CHESTNUT_WALL_SIGN, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedItems.CHESTNUT_WOOD, ExtendedItems.CHESTNUT_LOG, null);
		makePlanksRecipe(consumer, ExtendedItems.CHESTNUT_PLANKS, ExtendedItems.CHESTNUT_LOG,
			ExtendedTags.Items.CHESTNUT_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.CHESTNUT_PLANKS, ExtendedItems.CHESTNUT_LOG_SLAB,
			ExtendedItems.STRIPPED_CHESTNUT_LOG_SLAB, ExtendedItems.CHESTNUT_BRANCH, ExtendedItems.STRIPPED_CHESTNUT_BRANCH,
			ExtendedItems.CHESTNUT_WOOD_SLAB, ExtendedItems.STRIPPED_CHESTNUT_WOOD_SLAB,
			ExtendedItems.CHESTNUT_WOOD_STAIRS, ExtendedItems.STRIPPED_CHESTNUT_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedItems.STRIPPED_CHESTNUT_BRANCH, ExtendedItems.STRIPPED_CHESTNUT_WOOD, null);
		makeSlabRecipe(consumer, ExtendedItems.STRIPPED_CHESTNUT_LOG_SLAB, ExtendedItems.STRIPPED_CHESTNUT_LOG, null, "");
		makeStairSlabRecipe(consumer, ExtendedItems.STRIPPED_CHESTNUT_WOOD_STAIRS, ExtendedItems.STRIPPED_CHESTNUT_WOOD_SLAB,
			ExtendedItems.STRIPPED_CHESTNUT_WOOD, null);
		makeWoodRecipe(consumer, ExtendedItems.STRIPPED_CHESTNUT_WOOD, ExtendedItems.STRIPPED_CHESTNUT_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedItems.FALLEN_CHESTNUT_LEAVES, ExtendedItems.CHESTNUT_LEAVES, null);
		//BANANA
		makeBeamSlabRecipe(consumer, ExtendedItems.BANANA_BEAM,
			ExtendedItems.BANANA_BEAM_SLAB, ExtendedItems.BANANA_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.BANANA_BOAT, ExtendedItems.BANANA_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedItems.BANANA_BRANCH, ExtendedItems.BANANA_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.BANANA_BUTTON, ExtendedItems.BANANA_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.BANANA_DOOR, ExtendedItems.BANANA_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedItems.BANANA_FENCE, ExtendedItems.BANANA_FENCE_GATE,
			ExtendedItems.BANANA_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedItems.BANANA_LOG_SLAB, ExtendedItems.BANANA_LOG, null, "");
		makeWoodenPressurePlateRecipe(consumer, ExtendedItems.BANANA_PRESSURE_PLATE, ExtendedItems.BANANA_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.BANANA_STAIRS, ExtendedItems.BANANA_SLAB, ExtendedItems.BANANA_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedItems.BANANA_TRAPDOOR, ExtendedItems.BANANA_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.BANANA_WOOD_STAIRS, ExtendedItems.BANANA_WOOD_SLAB, ExtendedItems.BANANA_WOOD, null);
		makeSignRecipe(consumer, ExtendedItems.BANANA_SIGN, ExtendedBlocks.BANANA_WALL_SIGN, ExtendedItems.BANANA_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedItems.BANANA_WOOD, ExtendedItems.BANANA_LOG, null);
		makePlanksRecipe(consumer, ExtendedItems.BANANA_PLANKS, ExtendedItems.BANANA_LOG,
			ExtendedTags.Items.BANANA_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.BANANA_PLANKS, ExtendedItems.BANANA_LOG_SLAB,
			ExtendedItems.STRIPPED_BANANA_LOG_SLAB, ExtendedItems.BANANA_BRANCH, ExtendedItems.STRIPPED_BANANA_BRANCH,
			ExtendedItems.BANANA_WOOD_SLAB, ExtendedItems.STRIPPED_BANANA_WOOD_SLAB,
			ExtendedItems.BANANA_WOOD_STAIRS, ExtendedItems.STRIPPED_BANANA_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedItems.STRIPPED_BANANA_BRANCH, ExtendedItems.STRIPPED_BANANA_WOOD, null);
		makeSlabRecipe(consumer, ExtendedItems.STRIPPED_BANANA_LOG_SLAB, ExtendedItems.STRIPPED_BANANA_LOG, null, "");
		makeStairSlabRecipe(consumer, ExtendedItems.STRIPPED_BANANA_WOOD_STAIRS, ExtendedItems.STRIPPED_BANANA_WOOD_SLAB,
			ExtendedItems.STRIPPED_BANANA_WOOD, null);
		makeWoodRecipe(consumer, ExtendedItems.STRIPPED_BANANA_WOOD, ExtendedItems.STRIPPED_BANANA_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedItems.FALLEN_BANANA_LEAVES, ExtendedItems.BANANA_LEAVES, null);
		//PLUM
		makeBeamSlabRecipe(consumer, ExtendedItems.PLUM_BEAM,
			ExtendedItems.PLUM_BEAM_SLAB, ExtendedItems.PLUM_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.PLUM_BOAT, ExtendedItems.PLUM_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedItems.PLUM_BRANCH, ExtendedItems.PLUM_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.PLUM_BUTTON, ExtendedItems.PLUM_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.PLUM_DOOR, ExtendedItems.PLUM_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedItems.PLUM_FENCE, ExtendedItems.PLUM_FENCE_GATE,
			ExtendedItems.PLUM_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedItems.PLUM_LOG_SLAB, ExtendedItems.PLUM_LOG, null, "");
		makeWoodenPressurePlateRecipe(consumer, ExtendedItems.PLUM_PRESSURE_PLATE, ExtendedItems.PLUM_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.PLUM_STAIRS, ExtendedItems.PLUM_SLAB, ExtendedItems.PLUM_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedItems.PLUM_TRAPDOOR, ExtendedItems.PLUM_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.PLUM_WOOD_STAIRS, ExtendedItems.PLUM_WOOD_SLAB, ExtendedItems.PLUM_WOOD, null);
		makeSignRecipe(consumer, ExtendedItems.PLUM_SIGN, ExtendedBlocks.PLUM_WALL_SIGN, ExtendedItems.PLUM_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedItems.PLUM_WOOD, ExtendedItems.PLUM_LOG, null);
		makePlanksRecipe(consumer, ExtendedItems.PLUM_PLANKS, ExtendedItems.PLUM_LOG,
			ExtendedTags.Items.PLUM_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.PLUM_PLANKS, ExtendedItems.PLUM_LOG_SLAB,
			ExtendedItems.STRIPPED_PLUM_LOG_SLAB, ExtendedItems.PLUM_BRANCH, ExtendedItems.STRIPPED_PLUM_BRANCH,
			ExtendedItems.PLUM_WOOD_SLAB, ExtendedItems.STRIPPED_PLUM_WOOD_SLAB,
			ExtendedItems.PLUM_WOOD_STAIRS, ExtendedItems.STRIPPED_PLUM_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedItems.STRIPPED_PLUM_BRANCH, ExtendedItems.STRIPPED_PLUM_WOOD, null);
		makeSlabRecipe(consumer, ExtendedItems.STRIPPED_PLUM_LOG_SLAB, ExtendedItems.STRIPPED_PLUM_LOG, null, "");
		makeStairSlabRecipe(consumer, ExtendedItems.STRIPPED_PLUM_WOOD_STAIRS, ExtendedItems.STRIPPED_PLUM_WOOD_SLAB,
			ExtendedItems.STRIPPED_PLUM_WOOD, null);
		makeWoodRecipe(consumer, ExtendedItems.STRIPPED_PLUM_WOOD, ExtendedItems.STRIPPED_PLUM_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedItems.FALLEN_PLUM_LEAVES, ExtendedItems.PLUM_LEAVES, null);
		//WILLOW
		makeBeamSlabRecipe(consumer, ExtendedItems.WILLOW_BEAM,
			ExtendedItems.WILLOW_BEAM_SLAB, ExtendedItems.WILLOW_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.WILLOW_BOAT, ExtendedItems.WILLOW_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedItems.WILLOW_BRANCH, ExtendedItems.WILLOW_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.WILLOW_BUTTON, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.WILLOW_DOOR, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedItems.WILLOW_FENCE, ExtendedItems.WILLOW_FENCE_GATE,
			ExtendedItems.WILLOW_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedItems.WILLOW_LOG_SLAB, ExtendedItems.WILLOW_LOG, null, "");
		makeWoodenPressurePlateRecipe(consumer, ExtendedItems.WILLOW_PRESSURE_PLATE, ExtendedItems.WILLOW_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.WILLOW_STAIRS, ExtendedItems.WILLOW_SLAB, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedItems.WILLOW_TRAPDOOR, ExtendedItems.WILLOW_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.WILLOW_WOOD_STAIRS, ExtendedItems.WILLOW_WOOD_SLAB, ExtendedItems.WILLOW_WOOD, null);
		makeSignRecipe(consumer, ExtendedItems.WILLOW_SIGN, ExtendedBlocks.WILLOW_WALL_SIGN, ExtendedItems.WILLOW_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedItems.WILLOW_WOOD, ExtendedItems.WILLOW_LOG, null);
		makePlanksRecipe(consumer, ExtendedItems.WILLOW_PLANKS, ExtendedItems.WILLOW_LOG,
			ExtendedTags.Items.WILLOW_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.WILLOW_PLANKS, ExtendedItems.WILLOW_LOG_SLAB,
			ExtendedItems.STRIPPED_WILLOW_LOG_SLAB, ExtendedItems.WILLOW_BRANCH, ExtendedItems.STRIPPED_WILLOW_BRANCH,
			ExtendedItems.WILLOW_WOOD_SLAB, ExtendedItems.STRIPPED_WILLOW_WOOD_SLAB,
			ExtendedItems.WILLOW_WOOD_STAIRS, ExtendedItems.STRIPPED_WILLOW_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedItems.STRIPPED_WILLOW_BRANCH, ExtendedItems.STRIPPED_WILLOW_WOOD, null);
		makeSlabRecipe(consumer, ExtendedItems.STRIPPED_WILLOW_LOG_SLAB, ExtendedItems.STRIPPED_WILLOW_LOG, null, "");
		makeStairSlabRecipe(consumer, ExtendedItems.STRIPPED_WILLOW_WOOD_STAIRS, ExtendedItems.STRIPPED_WILLOW_WOOD_SLAB,
			ExtendedItems.STRIPPED_WILLOW_WOOD, null);
		makeWoodRecipe(consumer, ExtendedItems.STRIPPED_WILLOW_WOOD, ExtendedItems.STRIPPED_WILLOW_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedItems.FALLEN_WILLOW_LEAVES, ExtendedItems.WILLOW_LEAVES, null);
		//EBONY
		makeBeamSlabRecipe(consumer, ExtendedItems.EBONY_BEAM,
			ExtendedItems.EBONY_BEAM_SLAB, ExtendedItems.EBONY_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.EBONY_BOAT, ExtendedItems.EBONY_PLANKS, null);
		makeBranchRecipe(consumer, ExtendedItems.EBONY_BRANCH, ExtendedItems.EBONY_WOOD, null);
		makeWoodenButtonRecipe(consumer, ExtendedItems.EBONY_BUTTON, ExtendedItems.EBONY_PLANKS, null);
		makeWoodenDoorRecipe(consumer, ExtendedItems.EBONY_DOOR, ExtendedItems.EBONY_PLANKS, null);
		makeWoodenFenceAndFenceGateRecipe(consumer, ExtendedItems.EBONY_FENCE, ExtendedItems.EBONY_FENCE_GATE,
			ExtendedItems.EBONY_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedItems.EBONY_LOG_SLAB, ExtendedItems.EBONY_LOG, null, "");
		makeWoodenPressurePlateRecipe(consumer, ExtendedItems.EBONY_PRESSURE_PLATE, ExtendedItems.EBONY_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.EBONY_STAIRS, ExtendedItems.EBONY_SLAB, ExtendedItems.EBONY_PLANKS, null);
		makeWoodenTrapdoorRecipe(consumer, ExtendedItems.EBONY_TRAPDOOR, ExtendedItems.EBONY_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedItems.EBONY_WOOD_STAIRS, ExtendedItems.EBONY_WOOD_SLAB, ExtendedItems.EBONY_WOOD, null);
		makeSignRecipe(consumer, ExtendedItems.EBONY_SIGN, ExtendedBlocks.EBONY_WALL_SIGN, ExtendedItems.EBONY_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedItems.EBONY_WOOD, ExtendedItems.EBONY_LOG, null);
		makePlanksRecipe(consumer, ExtendedItems.EBONY_PLANKS, ExtendedItems.EBONY_LOG,
			ExtendedTags.Items.EBONY_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.EBONY_PLANKS, ExtendedItems.EBONY_LOG_SLAB,
			ExtendedItems.STRIPPED_EBONY_LOG_SLAB, ExtendedItems.EBONY_BRANCH, ExtendedItems.STRIPPED_EBONY_BRANCH,
			ExtendedItems.EBONY_WOOD_SLAB, ExtendedItems.STRIPPED_EBONY_WOOD_SLAB,
			ExtendedItems.EBONY_WOOD_STAIRS, ExtendedItems.STRIPPED_EBONY_WOOD_STAIRS, null);
		makeBranchRecipe(consumer, ExtendedItems.STRIPPED_EBONY_BRANCH, ExtendedItems.STRIPPED_EBONY_WOOD, null);
		makeSlabRecipe(consumer, ExtendedItems.STRIPPED_EBONY_LOG_SLAB, ExtendedItems.STRIPPED_EBONY_LOG, null, "");
		makeStairSlabRecipe(consumer, ExtendedItems.STRIPPED_EBONY_WOOD_STAIRS, ExtendedItems.STRIPPED_EBONY_WOOD_SLAB,
			ExtendedItems.STRIPPED_EBONY_WOOD, null);
		makeWoodRecipe(consumer, ExtendedItems.STRIPPED_EBONY_WOOD, ExtendedItems.STRIPPED_EBONY_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedItems.FALLEN_EBONY_LEAVES, ExtendedItems.EBONY_LEAVES, null);
		//ROHAN
		GENERATEDRECIPES.add(ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(LOGS))
		.unlockedBy("has_logs", has(LOGS))
		.save(consumer, ExtendedItems.ROHAN_BEAM);
		GENERATEDRECIPES.add(ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORNATE_ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(ExtendedItems.ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ROHAN_BEAM.get()))
		.save(consumer, ExtendedItems.ORNATE_ROHAN_BEAM);
		GENERATEDRECIPES.add(ExtendedItems.GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.GOLDEN_ROHAN_BEAM.get(), LOTRRecipes.ROHAN_CRAFTING)
		.pattern(" G ")
		.pattern("GXG")
		.pattern(" G ")
		.define('X', Ingredient.of(ExtendedItems.ROHAN_BEAM.get()))
		.define('G', Ingredient.of(Items.GOLD_NUGGET))
		.unlockedBy("has_"+ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ROHAN_BEAM.get()))
		.save(consumer, ExtendedItems.GOLDEN_ROHAN_BEAM);
		GENERATEDRECIPES.add(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(ExtendedItems.GOLDEN_ROHAN_BEAM.get()))
		.unlockedBy("has_"+ExtendedItems.GOLDEN_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.GOLDEN_ROHAN_BEAM.get()))
		.save(consumer, ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM);
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get(), LOTRRecipes.ROHAN_CRAFTING)
		.pattern(" G ")
		.pattern("GXG")
		.pattern(" G ")
		.define('X', Ingredient.of(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.define('G', Ingredient.of(Items.GOLD_NUGGET))
		.unlockedBy("has_"+ExtendedItems.ORNATE_ROHAN_BEAM.get().getRegistryName().getPath(), has(ExtendedItems.ORNATE_ROHAN_BEAM.get()))
		.save(consumer, new ResourceLocation(ExtendedItems.ORNATE_GOLDEN_ROHAN_BEAM.get().getRegistryName().toString()+"_from_ornate"));
		//CRAFTING TABLES
		makeCraftingTableRecipe(consumer, ExtendedItems.TAURETHRIM_CRAFTING_TABLE, ExtendedItems.TAURETHRIM_BRICK, "_with_bricks", ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedItems.TAURETHRIM_CRAFTING_TABLE, ExtendedItems.MUD_BRICK, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedItems.DOL_GULDUR_CRAFTING_TABLE, ExtendedItems.DOL_GULDUR_BRICK, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedItems.RHUNIC_CRAFTING_TABLE, ExtendedItems.RHUNIC_BRICK, ExtendedRecipes.RHUNIC_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedItems.HALF_TROLL_CRAFTING_TABLE, Items.COBBLESTONE::asItem, ExtendedRecipes.HALF_TROLL_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedItems.NORTHERN_ORC_CRAFTING_TABLE, Items.COBBLESTONE::asItem, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedItems.MORWAITH_CRAFTING_TABLE, Items.CLAY::asItem, ExtendedRecipes.MORWAITH_CRAFTING);
		// Furnaces +Overrides
		makeForgeRecipe(consumer, LOTRItems.ALLOY_FORGE, Items.STONE_BRICKS::asItem, Items.COAL_BLOCK::asItem, "", null);
		makeForgeRecipe(consumer, LOTRItems.DWARVEN_FORGE, LOTRItems.DWARVEN_BRICK, Items.COAL_BLOCK::asItem, "", LOTRRecipes.ANY_DWARVEN);
		makeForgeRecipe(consumer, LOTRItems.DWARVEN_FORGE, LOTRItems.BLUE_BRICK, Items.COAL_BLOCK::asItem, "_blue", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ELVEN_FORGE, LOTRItems.GALADHRIM_BRICK, Items.COAL_BLOCK::asItem, "_galadhrim", LOTRRecipes.GALADHRIM_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ELVEN_FORGE, LOTRItems.HIGH_ELVEN_BRICK, Items.COAL_BLOCK::asItem, "_high_elven", LOTRRecipes.ANY_HIGH_ELVEN);
		makeForgeRecipe(consumer, LOTRItems.ELVEN_FORGE, LOTRItems.WOOD_ELVEN_BRICK, Items.COAL_BLOCK::asItem, "_wood_elven", LOTRRecipes.WOOD_ELVEN_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.HOBBIT_OVEN, Items.BRICKS::asItem, Items.COAL_BLOCK::asItem, "", LOTRRecipes.ANY_HOBBIT);
		makeForgeRecipe(consumer, LOTRItems.ORC_FORGE, ExtendedItems.GOBLIN_BRICK, Items.COAL_BLOCK::asItem, "_northern_orc", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ORC_FORGE, LOTRItems.ANGMAR_BRICK, LOTRItems.GULDURIL_BLOCK, "_angmar", LOTRRecipes.ANGMAR_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ORC_FORGE, ExtendedItems.RED_ANGMAR_BRICK, LOTRItems.GULDURIL_BLOCK, "_angmar_red", LOTRRecipes.ANGMAR_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ORC_FORGE, ExtendedItems.DOL_GULDUR_BRICK, LOTRItems.MIRK_SHROOM, "_dol_guldur", ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ORC_FORGE, LOTRItems.MORDOR_BRICK, LOTRItems.DURNOR_BLOCK, "_mordor", LOTRRecipes.MORDOR_CRAFTING);
		makeForgeRecipe(consumer, LOTRItems.ORC_FORGE, LOTRItems.URUK_BRICK, Items.COAL_BLOCK::asItem, "_uruk", LOTRRecipes.URUK_CRAFTING);
		// Other Containers
		GENERATEDRECIPES.add(ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CONQUEST_TABLE.get())
		.pattern("XXX")
		.pattern("YYY")
		.pattern("ZZZ")
		.define('X', Ingredient.of(Items.PAPER))
		.define('Y', Ingredient.of(PLANKS))
		.define('Z', Ingredient.of(LOTRTags.Items.INGOTS_BRONZE))
		.unlockedBy("has_"+LOTRItems.BRONZE_INGOT.get().getRegistryName().getPath(), has(LOTRItems.BRONZE_INGOT.get()))
		.save(consumer, ExtendedItems.CONQUEST_TABLE);
		GENERATEDRECIPES.add(ExtendedItems.STONE_GRINDER.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.STONE_GRINDER.get())
		.pattern("CIC")
		.pattern("CSC")
		.pattern("CCC")
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('S', Ingredient.of(STICKS))
		.define('I', Ingredient.of(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.unlockedBy("has_iron_ingots", has(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.save(consumer, ExtendedItems.STONE_GRINDER);
		GENERATEDRECIPES.add(ExtendedItems.CRUCIBLE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CRUCIBLE.get())
		.pattern("I I")
		.pattern("SIS")
		.pattern("CCC")
		.define('C', Ingredient.of(Tags.Items.COBBLESTONE))
		.define('S', Ingredient.of(STICKS))
		.define('I', Ingredient.of(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.unlockedBy("has_iron_ingots", has(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.save(consumer, ExtendedItems.CRUCIBLE);
		//STORAGE BLOCKS AND ITEMS
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.AMBER, ExtendedItems.AMBER_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.AMETHYST, ExtendedItems.AMETHYST_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.TOPAZ, ExtendedItems.TOPAZ_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.SAPPHIRE, ExtendedItems.SAPPHIRE_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.RUBY, ExtendedItems.RUBY_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.DIAMOND, ExtendedItems.DIAMOND_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.OPAL, ExtendedItems.OPAL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.EMERALD, ExtendedItems.EMERALD_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.PEARL, ExtendedItems.PEARL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.CORAL, ExtendedItems.CORAL_BLOCK, null, ExtendedItems.CORAL.get().getRegistryName().getPath(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.DEAD_CORAL, ExtendedItems.DEAD_CORAL_BLOCK, null, ExtendedItems.DEAD_CORAL.get().getRegistryName().getPath(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.GALVORN_INGOT, ExtendedItems.GALVORN_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.BLACK_URUK_STEEL_INGOT, ExtendedItems.BLACK_URUK_STEEL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.GILDED_IRON_INGOT, ExtendedItems.GILDED_IRON_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.MORGUL_STEEL_INGOT, ExtendedItems.MORGUL_STEEL_BLOCK, null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.OBSIDIAN_SHARD, Items.OBSIDIAN::asItem, null);
		makeNuggetToIngotAndBackRecipe(consumer, LOTRItems.ORC_STEEL_INGOT, ExtendedItems.ORC_STEEL_NUGGET, null);
		makeStairSlabWallRecipe(consumer, ExtendedItems.BONE_BLOCK_STAIRS,
			ExtendedItems.BONE_BLOCK_SLAB,
			ExtendedItems.BONE_BLOCK_WALL,
			Items.BONE_BLOCK::asItem, null);

		makeTarpItemAndBackRecipe(consumer, ExtendedItems.BLACK_FUR, ExtendedItems.BLACK_MATTED_FUR, 
			ExtendedItems.BLACK_MATTED_FUR_SLAB, ExtendedItems.BLACK_MATTED_FUR_WALL, ExtendedItems.BLACK_MATTED_FUR_STAIRS, null);
		makeTarpItemAndBackRecipe(consumer, ExtendedItems.GRAY_FUR, ExtendedItems.GRAY_MATTED_FUR, 
			ExtendedItems.GRAY_MATTED_FUR_SLAB, ExtendedItems.GRAY_MATTED_FUR_WALL, ExtendedItems.GRAY_MATTED_FUR_STAIRS, null);
		makeTarpItemAndBackRecipe(consumer, LOTRItems.FUR, ExtendedItems.BROWN_MATTED_FUR, 
			ExtendedItems.BROWN_MATTED_FUR_SLAB, ExtendedItems.BROWN_MATTED_FUR_WALL, ExtendedItems.BROWN_MATTED_FUR_STAIRS, null);
		makeTarpItemAndBackRecipe(consumer, ExtendedItems.SILVER_FUR, ExtendedItems.SILVER_MATTED_FUR, 
			ExtendedItems.SILVER_MATTED_FUR_SLAB, ExtendedItems.SILVER_MATTED_FUR_WALL, ExtendedItems.SILVER_MATTED_FUR_STAIRS, null);
		makeTarpItemAndBackRecipe(consumer, ExtendedItems.WHITE_FUR, ExtendedItems.WHITE_MATTED_FUR, 
			ExtendedItems.WHITE_MATTED_FUR_SLAB, ExtendedItems.WHITE_MATTED_FUR_WALL, ExtendedItems.WHITE_MATTED_FUR_STAIRS, null);

		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.BLACK_FUR, ExtendedItems.BLACK_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.GRAY_FUR, ExtendedItems.GRAY_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.SILVER_FUR, ExtendedItems.SILVER_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.WHITE_FUR, ExtendedItems.WHITE_FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, LOTRItems.FUR, LOTRItems.FUR_BUNDLE, null);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ()->Items.LEATHER, LOTRItems.LEATHER_BUNDLE, null);
		makeCoralRecipes(consumer, ExtendedItems.CORAL, ()->Items.BRAIN_CORAL, ()->Items.BRAIN_CORAL_FAN, ()->Items.BRAIN_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.CORAL, ()->Items.BUBBLE_CORAL, ()->Items.BUBBLE_CORAL_FAN, ()->Items.BUBBLE_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.CORAL, ()->Items.FIRE_CORAL, ()->Items.FIRE_CORAL_FAN, ()->Items.FIRE_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.CORAL, ()->Items.HORN_CORAL, ()->Items.HORN_CORAL_FAN, ()->Items.HORN_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.CORAL, ()->Items.TUBE_CORAL, ()->Items.TUBE_CORAL_FAN, ()->Items.TUBE_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.DEAD_CORAL, ()->Items.DEAD_BRAIN_CORAL, ()->Items.DEAD_BRAIN_CORAL_FAN, ()->Items.DEAD_BRAIN_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.DEAD_CORAL, ()->Items.DEAD_BUBBLE_CORAL, ()->Items.DEAD_BUBBLE_CORAL_FAN, ()->Items.DEAD_BUBBLE_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.DEAD_CORAL, ()->Items.DEAD_FIRE_CORAL, ()->Items.DEAD_FIRE_CORAL_FAN, ()->Items.DEAD_FIRE_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.DEAD_CORAL, ()->Items.DEAD_HORN_CORAL, ()->Items.DEAD_HORN_CORAL_FAN, ()->Items.DEAD_HORN_CORAL_BLOCK);
		makeCoralRecipes(consumer, ExtendedItems.DEAD_CORAL, ()->Items.DEAD_TUBE_CORAL, ()->Items.DEAD_TUBE_CORAL_FAN, ()->Items.DEAD_TUBE_CORAL_BLOCK);
		// Hobbit pancakes
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE.get(), 1, LOTRRecipes.HOBBIT_CRAFTING)
		.pattern("EW")
		.pattern("M ")
		.define('E', Items.EGG)
		.define('M', Items.MILK_BUCKET)
		.define('W', Tags.Items.CROPS_WHEAT)
		.unlockedBy("has_eggs", has(Items.EGG))
		.unlockedBy("has_wheat", has(Tags.Items.CROPS_WHEAT))
		.unlockedBy("has_milk", has(Items.MILK_BUCKET))
		.save(consumer, ExtendedItems.HOBBIT_PANCAKE);
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE.get(), 2, LOTRRecipes.HOBBIT_CRAFTING)
		.pattern("EW")
		.pattern("M ")
		.define('E', ExtendedItems.DUCK_EGG.get())
		.define('M', Items.MILK_BUCKET)
		.define('W', Tags.Items.CROPS_WHEAT)
		.unlockedBy("has_duck_egg", has(ExtendedItems.DUCK_EGG.get()))
		.unlockedBy("has_milk", has(Items.MILK_BUCKET))
		.save(consumer, new ResourceLocation(ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().toString()+"_with_duck_egg"));
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get(), 8, LOTRRecipes.HOBBIT_CRAFTING)
		.define('X', ExtendedItems.HOBBIT_PANCAKE.get())
		.define('Y', LOTRItems.MAPLE_SYRUP.get())
		.pattern("XXX")
		.pattern("XYX")
		.pattern("XXX")
		.unlockedBy("has_" + ExtendedItems.HOBBIT_PANCAKE.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_PANCAKE.get()))
		.unlockedBy("has_" + LOTRItems.MAPLE_SYRUP.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_PANCAKE.get()))
		.save(consumer, ExtendedItems.HOBBIT_PANCAKE_SYRUP);
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER.get(), 8, LOTRRecipes.HOBBIT_CRAFTING)
		.define('X', ExtendedItems.HOBBIT_PANCAKE_SYRUP.get())
		.define('Y', ExtendedItems.BUTTER.get())
		.pattern("XXX")
		.pattern("XYX")
		.pattern("XXX")
		.unlockedBy("has_" + ExtendedItems.HOBBIT_PANCAKE_SYRUP.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_PANCAKE_SYRUP.get()))
		.unlockedBy("has_" + ExtendedItems.BUTTER.get().getRegistryName().getPath(), has(ExtendedItems.BUTTER.get()))
		.save(consumer, ExtendedItems.HOBBIT_PANCAKE_SYRUP_BUTTER);

		//MORGUL
		//makeArmorRecipe(consumer, ExtendedItems.MORGUL_HELMET,
		//	ExtendedItems.MORGUL_CHESTPLATE,
		//	ExtendedItems.MORGUL_LEGGINGS,
		//	ExtendedItems.MORGUL_BOOTS,
		//	ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.MORDOR_CRAFTING);
		makeSwordRecipe(consumer, ExtendedItems.MORGUL_BLADE, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_BRAZIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.MORGUL_BRAZIER.get(), LOTRRecipes.ANY_MORGUL)
		.pattern("YYY")
		.pattern("Y#Y")
		.pattern("YYY")
		.define('#', Ingredient.of(ExtendedItems.MORGUL_TORCH.get()))
		.define('Y', Ingredient.of(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.unlockedBy("has_"+ExtendedItems.MORGUL_TORCH.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_TORCH.get()))
		.unlockedBy("has_orc_steel_nugget", has(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.save(consumer, ExtendedItems.MORGUL_BRAZIER);
		GENERATEDRECIPES.add(ExtendedItems.HIGH_ELVEN_LANTERN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HIGH_ELVEN_LANTERN.get(), LOTRRecipes.ANY_HIGH_ELVEN)
		.pattern("YYY")
		.pattern("Y#Y")
		.pattern("YYY")
		.define('#', Ingredient.of(LOTRItems.HIGH_ELVEN_TORCH.get()))
		.define('Y', Ingredient.of(ExtendedTags.Items.SILVER_NUGGET))
		.unlockedBy("has_"+LOTRItems.HIGH_ELVEN_TORCH.get().getRegistryName().getPath(), has(LOTRItems.HIGH_ELVEN_TORCH.get()))
		.unlockedBy("has_silver_nugget", has(ExtendedTags.Items.SILVER_NUGGET))
		.save(consumer, ExtendedItems.HIGH_ELVEN_LANTERN);
		GENERATEDRECIPES.add(ExtendedItems.NUMENOREAN_LANTERN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.NUMENOREAN_LANTERN.get(), LOTRRecipes.GONDOR_CRAFTING)
		.pattern("YYY")
		.pattern("Y#Y")
		.pattern("YMY")
		.define('#', Ingredient.of(Items.TORCH))
		.define('Y', Ingredient.of(Tags.Items.NUGGETS_IRON))
		.define('M', Ingredient.of(ExtendedItems.MORGOND.get()))
		.unlockedBy("has_"+ExtendedItems.MORGOND.get().getRegistryName().getPath(), has(ExtendedItems.MORGOND.get()))
		.save(consumer, ExtendedItems.NUMENOREAN_LANTERN);
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.MORGUL_CHANDELIER.get(), LOTRRecipes.ANY_MORGUL)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.MORGUL_STEEL_INGOT))
		.unlockedBy("has_"+ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_STEEL_INGOT.get()))
		.save(consumer, ExtendedItems.MORGUL_CHANDELIER);
		makeTorchRecipe(consumer, ExtendedItems.MORGUL_TORCH, LOTRItems.GULDURIL, LOTRRecipes.ANY_MORGUL);
		makeDoorRecipe(consumer, ExtendedItems.ORC_STEEL_DOOR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeMetalTrapdoorRecipe(consumer, ExtendedItems.ORC_STEEL_TRAPDOOR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeChainRecipe(consumer, ExtendedItems.ORC_CHAIN, LOTRItems.ORC_STEEL_INGOT, ExtendedItems.ORC_STEEL_NUGGET, LOTRRecipes.ANY_ORC);
		makeChainRecipe(consumer, ExtendedItems.HIGH_ELVEN_CHAIN, LOTRItems.SILVER_INGOT, LOTRItems.SILVER_NUGGET, LOTRRecipes.ANY_HIGH_ELVEN);
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
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK,
			ExtendedItems.DOL_GULDUR_BRICK_STAIRS,
			ExtendedItems.DOL_GULDUR_BRICK_SLAB,
			ExtendedItems.DOL_GULDUR_BRICK_WALL,
			Blocks.STONE::asItem, ExtendedRecipes.DOL_GULDUR_CRAFTING,"");
		makePillarSlabRecipe(consumer, ExtendedItems.DOL_GULDUR_PILLAR,
			ExtendedItems.DOL_GULDUR_PILLAR_SLAB,
			Blocks.STONE::asItem, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_DOL_GULDUR_BRICK,
			ExtendedItems.DOL_GULDUR_BRICK, ExtendedItems.DOL_GULDUR_BRICK_SLAB, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_DOL_GULDUR_BRICK,
			()->ExtendedItems.DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_DOL_GULDUR_BRICK_STAIRS,
			ExtendedItems.MOSSY_DOL_GULDUR_BRICK_SLAB,
			ExtendedItems.MOSSY_DOL_GULDUR_BRICK_WALL,
			()->ExtendedItems.MOSSY_DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedItems.CRACKED_DOL_GULDUR_BRICK,
			()->ExtendedItems.DOL_GULDUR_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS,
			ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB,
			ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL,
			()->ExtendedItems.CRACKED_DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		//ANGMAR
		makeArmorRecipe(consumer, ExtendedItems.MORGUL_VASSAL_HELMET,
			ExtendedItems.MORGUL_VASSAL_CHESTPLATE,
			ExtendedItems.MORGUL_VASSAL_LEGGINGS,
			ExtendedItems.MORGUL_VASSAL_BOOTS,
			ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
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
		.unlockedBy("has_"+LOTRItems.ORC_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.ORC_STEEL_INGOT.get()))
		.save(consumer, ExtendedItems.ORC_CHANDELIER);
		GENERATEDRECIPES.add(ExtendedItems.URUK_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.URUK_CHANDELIER.get(), LOTRRecipes.URUK_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.URUK_STEEL_INGOT))
		.unlockedBy("has_"+LOTRItems.URUK_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.URUK_STEEL_INGOT.get()))
		.save(consumer, ExtendedItems.URUK_CHANDELIER);
		GENERATEDRECIPES.add(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DWARVEN_CHANDELIER.get(), LOTRRecipes.DWARVEN_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, ExtendedItems.DWARVEN_CHANDELIER);
		GENERATEDRECIPES.add(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DWARVEN_CHANDELIER.get(), LOTRRecipes.DWARVEN_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.DWARVEN_TORCH.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, new ResourceLocation(ExtendedItems.DWARVEN_CHANDELIER.get().getRegistryName().toString()+"_with_torch"));
		GENERATEDRECIPES.add(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.CANDLE.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, ExtendedItems.BLUE_DWARVEN_CHANDELIER);
		GENERATEDRECIPES.add(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING)
		.pattern(" N ")
		.pattern("CIC")
		.define('N', Ingredient.of(Items.IRON_NUGGET))
		.define('C', Ingredient.of(LOTRItems.DWARVEN_TORCH.get()))
		.define('I', Ingredient.of(ExtendedTags.Items.DWARVEN_STEEL_INGOT))
		.unlockedBy("has_"+LOTRItems.DWARVEN_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.DWARVEN_STEEL_INGOT.get()))
		.save(consumer, new ResourceLocation(ExtendedItems.BLUE_DWARVEN_CHANDELIER.get().getRegistryName().toString()+"_with_torch"));
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
		makeWargArmorRecipe(consumer, ExtendedItems.LEATHER_WARG_ARMOR, Tags.Items.LEATHER, "leather", LOTRRecipes.ANY_ORC);
		makeWargArmorRecipe(consumer, ExtendedItems.MAIL_WARG_ARMOR, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.ANY_ORC);
		makeWargArmorRecipe(consumer, ExtendedItems.ANGMAR_WARG_ARMOR, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.ANGMAR_CRAFTING);
		makeWargArmorRecipe(consumer, ExtendedItems.ISENGARD_WARG_ARMOR, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel_ingot", LOTRRecipes.URUK_CRAFTING);
		makeWargArmorRecipe(consumer, ExtendedItems.MORDOR_WARG_ARMOR, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.MORDOR_CRAFTING);
		makeWargArmorRecipe(consumer, ExtendedItems.GUNDABAD_WARG_ARMOR, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", ExtendedRecipes.NORTHERN_ORC_CRAFTING);

		// GOBLIN/URUK
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.GOBLIN_BRICK,
			ExtendedItems.GOBLIN_BRICK_STAIRS,
			ExtendedItems.GOBLIN_BRICK_SLAB,
			ExtendedItems.GOBLIN_BRICK_WALL,
			Blocks.STONE::asItem, ExtendedRecipes.NORTHERN_ORC_CRAFTING,"");
		makePillarSlabRecipe(consumer, ExtendedItems.GOBLIN_PILLAR,
			ExtendedItems.GOBLIN_PILLAR_SLAB,
			Blocks.STONE::asItem, ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		// ORC
		makeOrcBombRecipe(consumer, ExtendedItems.ORC_BOMB, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.ANY_MORGUL);
		makeOrcBombRecipe(consumer, ExtendedItems.ORC_BOMB, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel_ingot", LOTRRecipes.URUK_CRAFTING);
		makeOrcBombFireRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE, ExtendedItems.ORC_BOMB, LOTRRecipes.ANY_ORC);
		makeOrcBombFireRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE_DOUBLE, ExtendedItems.ORC_BOMB_DOUBLE, LOTRRecipes.ANY_ORC);
		makeOrcBombFireRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE_TRIPLE, ExtendedItems.ORC_BOMB_TRIPLE, LOTRRecipes.ANY_ORC);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_DOUBLE, ExtendedItems.ORC_BOMB, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.ANY_MORGUL);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE_DOUBLE, ExtendedItems.ORC_BOMB_FIRE, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.ANY_MORGUL);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_TRIPLE, ExtendedItems.ORC_BOMB_DOUBLE, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.ANY_MORGUL);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE_TRIPLE, ExtendedItems.ORC_BOMB_FIRE_DOUBLE, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel_ingot", LOTRRecipes.ANY_MORGUL);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_DOUBLE, ExtendedItems.ORC_BOMB, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel_ingot", LOTRRecipes.URUK_CRAFTING);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE_DOUBLE, ExtendedItems.ORC_BOMB_FIRE, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel_ingot", LOTRRecipes.URUK_CRAFTING);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_TRIPLE, ExtendedItems.ORC_BOMB_DOUBLE, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel_ingot", LOTRRecipes.URUK_CRAFTING);
		makeOrcBombUpgradeRecipe(consumer, ExtendedItems.ORC_BOMB_FIRE_TRIPLE, ExtendedItems.ORC_BOMB_FIRE_DOUBLE, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel_ingot", LOTRRecipes.URUK_CRAFTING);
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
		.save(consumer, ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET);
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
		.save(consumer, ExtendedItems.TAURETHRIM_SWORD);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK,
			ExtendedItems.TAURETHRIM_BRICK_STAIRS,
			ExtendedItems.TAURETHRIM_BRICK_SLAB,
			ExtendedItems.TAURETHRIM_BRICK_WALL,
			Blocks.STONE::asItem, ExtendedRecipes.TAURETHRIM_CRAFTING,"");
		makePillarSlabRecipe(consumer, ExtendedItems.TAURETHRIM_PILLAR,
			ExtendedItems.TAURETHRIM_PILLAR_SLAB,
			Blocks.STONE::asItem, ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_TAURETHRIM_BRICK,
			()->ExtendedItems.TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_TAURETHRIM_BRICK_STAIRS,
			ExtendedItems.MOSSY_TAURETHRIM_BRICK_SLAB,
			ExtendedItems.MOSSY_TAURETHRIM_BRICK_WALL,
			()->ExtendedItems.MOSSY_TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedItems.CRACKED_TAURETHRIM_BRICK,
			()->ExtendedItems.TAURETHRIM_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS,
			ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB,
			ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL,
			()->ExtendedItems.CRACKED_TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BRICK,
			ExtendedItems.GOLDEN_TAURETHRIM_BRICK_STAIRS,
			ExtendedItems.GOLDEN_TAURETHRIM_BRICK_SLAB,
			ExtendedItems.GOLDEN_TAURETHRIM_BRICK_WALL,
			()->Items.GOLD_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING,"");
		makePillarSlabRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_PILLAR,
			ExtendedItems.GOLDEN_TAURETHRIM_PILLAR_SLAB,
			()->Items.GOLD_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK,
			ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_STAIRS,
			ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_SLAB,
			ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_WALL,
			()->ExtendedItems.OBSIDIAN_SHARD.get(), ExtendedRecipes.TAURETHRIM_CRAFTING,"");
		makePillarSlabRecipe(consumer, ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR,
			ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR_SLAB,
			()->ExtendedItems.OBSIDIAN_SHARD.get(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		//RHUNIC
		GENERATEDRECIPES.add(ExtendedItems.RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.RHUNIC_BRICK.get(), 4, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(Blocks.STONE.asItem()))
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE.asItem()))
		.save(consumer, new ResourceLocation(ExtendedItems.RHUNIC_BRICK.get().getRegistryName().toString()+"_1"));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.RHUNIC_BRICK.get(), 4, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(Blocks.SANDSTONE.asItem()))
		.unlockedBy("has_"+Blocks.SANDSTONE.getRegistryName().getPath(), has(Blocks.SANDSTONE.asItem()))
		.save(consumer,new ResourceLocation(ExtendedItems.RHUNIC_BRICK.get().getRegistryName().toString()+"_2"));
		makeStairSlabWallRecipe(consumer, ExtendedItems.RHUNIC_BRICK_STAIRS,
			ExtendedItems.RHUNIC_BRICK_SLAB,
			ExtendedItems.RHUNIC_BRICK_WALL,
			()->ExtendedItems.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_RHUNIC_BRICK,
			ExtendedItems.RHUNIC_BRICK, ExtendedItems.RHUNIC_BRICK_SLAB, ExtendedRecipes.RHUNIC_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.RHUNIC_PILLAR.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.RHUNIC_PILLAR.get(), 3, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(Blocks.STONE.asItem()))
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE.asItem()))
		.save(consumer, new ResourceLocation(ExtendedItems.RHUNIC_PILLAR.get().getRegistryName().toString()+"_1"));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.RHUNIC_PILLAR.get(), 3, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(Blocks.SANDSTONE.asItem()))
		.unlockedBy("has_"+Blocks.SANDSTONE.getRegistryName().getPath(), has(Blocks.SANDSTONE.asItem()))
		.save(consumer, new ResourceLocation(ExtendedItems.RHUNIC_PILLAR.get().getRegistryName().toString()+"_2"));
		makeSlabRecipe(consumer, ExtendedItems.RHUNIC_PILLAR_SLAB,
			()->ExtendedItems.RHUNIC_PILLAR.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING, "");
		makeCrackedBlockRecipe(consumer, ExtendedItems.CRACKED_RHUNIC_BRICK,
			()->ExtendedItems.RHUNIC_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS,
			ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB,
			ExtendedItems.CRACKED_RHUNIC_BRICK_WALL,
			()->ExtendedItems.CRACKED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.FLOWERY_RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(ExtendedItems.FLOWERY_RHUNIC_BRICK.get(), ExtendedRecipes.RHUNIC_CRAFTING)
		.requires(ExtendedItems.RHUNIC_BRICK.get())
		.requires(LOTRItems.ORANGE_CHRYSANTHEMUM.get())
		.unlockedBy("has_"+LOTRItems.ORANGE_CHRYSANTHEMUM.get().getRegistryName().getPath(), has(LOTRItems.ORANGE_CHRYSANTHEMUM.get()))
		.save(consumer, ExtendedItems.FLOWERY_RHUNIC_BRICK);
		makeStairSlabWallRecipe(consumer, ExtendedItems.FLOWERY_RHUNIC_BRICK_STAIRS,
			ExtendedItems.FLOWERY_RHUNIC_BRICK_SLAB,
			ExtendedItems.FLOWERY_RHUNIC_BRICK_WALL,
			()->ExtendedItems.FLOWERY_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_RHUNIC_BRICK,
			()->ExtendedItems.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_RHUNIC_BRICK_STAIRS,
			ExtendedItems.MOSSY_RHUNIC_BRICK_SLAB,
			ExtendedItems.MOSSY_RHUNIC_BRICK_WALL,
			()->ExtendedItems.MOSSY_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK,
			ExtendedItems.RED_RHUNIC_BRICK_STAIRS,
			ExtendedItems.RED_RHUNIC_BRICK_SLAB,
			ExtendedItems.RED_RHUNIC_BRICK_WALL,
			()->LOTRItems.RED_ROCK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING,"");
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_RED_RHUNIC_BRICK,
			ExtendedItems.RED_RHUNIC_BRICK, ExtendedItems.RED_RHUNIC_BRICK_SLAB, ExtendedRecipes.RHUNIC_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedItems.RED_RHUNIC_PILLAR,
			ExtendedItems.RED_RHUNIC_PILLAR_SLAB,
			()->LOTRItems.RED_ROCK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedItems.CRACKED_RED_RHUNIC_BRICK,
			()->ExtendedItems.RED_RHUNIC_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_STAIRS,
			ExtendedItems.CRACKED_RED_RHUNIC_BRICK_SLAB,
			ExtendedItems.CRACKED_RED_RHUNIC_BRICK_WALL,
			()->ExtendedItems.CRACKED_RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_RED_RHUNIC_BRICK,
			()->ExtendedItems.RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_RED_RHUNIC_BRICK_STAIRS,
			ExtendedItems.MOSSY_RED_RHUNIC_BRICK_SLAB,
			ExtendedItems.MOSSY_RED_RHUNIC_BRICK_WALL,
			()->ExtendedItems.MOSSY_RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);

		GENERATEDRECIPES.add(ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK.get(), ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern(" # ")
		.pattern("#X#")
		.pattern(" # ")
		.define('#', Ingredient.of(Items.GOLD_NUGGET))
		.define('X', Ingredient.of(ExtendedItems.RHUNIC_BRICK.get()))
		.unlockedBy("has_"+Items.GOLD_NUGGET.getRegistryName().getPath(), has(Items.GOLD_NUGGET))
		.unlockedBy("has_"+ExtendedItems.RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedItems.RHUNIC_BRICK.get()))
		.save(consumer,ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK);
		makeStairSlabWallRecipe(consumer, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS,
			ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_SLAB,
			ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_WALL,
			()->ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		// ANGMAR
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.RED_ANGMAR_BRICK,
			ExtendedItems.RED_ANGMAR_BRICK_STAIRS,
			ExtendedItems.RED_ANGMAR_BRICK_SLAB,
			ExtendedItems.RED_ANGMAR_BRICK_WALL,
			()->Blocks.GRANITE.asItem(), LOTRRecipes.ANGMAR_CRAFTING,"");
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_RED_ANGMAR_BRICK,
			ExtendedItems.RED_ANGMAR_BRICK, ExtendedItems.RED_ANGMAR_BRICK_SLAB, LOTRRecipes.ANGMAR_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedItems.RED_ANGMAR_PILLAR,
			ExtendedItems.RED_ANGMAR_PILLAR_SLAB,
			()->Blocks.GRANITE.asItem(), LOTRRecipes.ANGMAR_CRAFTING);
		makeCrackedBlockRecipe(consumer, ExtendedItems.CRACKED_RED_ANGMAR_BRICK,
			()->ExtendedItems.RED_ANGMAR_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedItems.CRACKED_RED_ANGMAR_BRICK_STAIRS,
			ExtendedItems.CRACKED_RED_ANGMAR_BRICK_SLAB,
			ExtendedItems.CRACKED_RED_ANGMAR_BRICK_WALL,
			()->ExtendedItems.CRACKED_RED_ANGMAR_BRICK.get().asItem(), LOTRRecipes.ANGMAR_CRAFTING);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_RED_ANGMAR_BRICK,
			()->ExtendedItems.RED_ANGMAR_BRICK.get().asItem(), LOTRRecipes.ANGMAR_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_RED_ANGMAR_BRICK_STAIRS,
			ExtendedItems.MOSSY_RED_ANGMAR_BRICK_SLAB,
			ExtendedItems.MOSSY_RED_ANGMAR_BRICK_WALL,
			()->ExtendedItems.MOSSY_RED_ANGMAR_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeSnowyBlockRecipe(consumer, ExtendedItems.SNOWY_RED_ANGMAR_BRICK,
			()->ExtendedItems.RED_ANGMAR_BRICK.get().asItem(), LOTRRecipes.ANGMAR_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.SNOWY_RED_ANGMAR_BRICK_STAIRS,
			ExtendedItems.SNOWY_RED_ANGMAR_BRICK_SLAB,
			ExtendedItems.SNOWY_RED_ANGMAR_BRICK_WALL,
			()->ExtendedItems.SNOWY_RED_ANGMAR_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		//GONDOR
		makeStairRecipe(consumer, ExtendedItems.SMOOTH_GONDOR_ROCK_STAIRS,
			()->LOTRItems.SMOOTH_GONDOR_ROCK.get().asItem(), LOTRRecipes.GONDOR_CRAFTING, "");
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_BLACK_GONDOR_BRICK,
			LOTRItems.NUMENOREAN_BRICK, LOTRItems.NUMENOREAN_BRICK_SLAB, LOTRRecipes.GONDOR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_BLACK_UMBAR_BRICK,
			LOTRItems.NUMENOREAN_BRICK, LOTRItems.NUMENOREAN_BRICK_SLAB, LOTRRecipes.UMBAR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_UMBAR_BRICK,
			LOTRItems.UMBAR_BRICK, LOTRItems.UMBAR_BRICK_SLAB, LOTRRecipes.UMBAR_CRAFTING);
		makeThrowableAxeRecipe(consumer, ExtendedItems.LOSSARNACH_THROWABLE_AXE, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.GONDOR_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.MORGOND.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.MORGOND.get())
		.pattern("GWS")
		.pattern("MOC")
		.pattern("BLR")
		.define('G', Ingredient.of(Items.GRAVEL))
		.define('W', Ingredient.of(Items.WATER_BUCKET))
		.define('S', Ingredient.of(Tags.Items.SAND))
		.define('M', Ingredient.of(Items.CLAY))
		.define('O', Ingredient.of(ExtendedItems.OBSIDIAN_SHARD.get()))
		.define('C', Ingredient.of(Items.COAL_BLOCK))
		.define('B', Ingredient.of(Items.BASALT))
		.define('L', Ingredient.of(Items.LAVA_BUCKET))
		.define('R', Ingredient.of(ExtendedTags.Items.MORGOND_ROCKS))
		.unlockedBy("has_"+ExtendedItems.OBSIDIAN_SHARD.get().getRegistryName().getPath(), has(ExtendedItems.OBSIDIAN_SHARD.get()))
		.save(consumer, ExtendedItems.MORGOND);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MORGOND_STAIRS,
			ExtendedItems.MORGOND_SLAB,
			ExtendedItems.MORGOND_WALL,
			ExtendedItems.MORGOND, LOTRRecipes.GONDOR_CRAFTING);
		makeCarvedBlockRecipe(consumer, ExtendedItems.CARVED_MORGOND,
			ExtendedItems.MORGOND, ExtendedItems.MORGOND_SLAB, LOTRRecipes.GONDOR_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.CARVED_MORGOND_STAIRS,
			ExtendedItems.CARVED_MORGOND_SLAB,
			ExtendedItems.CARVED_MORGOND_WALL,
			ExtendedItems.CARVED_MORGOND, LOTRRecipes.GONDOR_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedItems.MORGOND_PILLAR,
			ExtendedItems.MORGOND_PILLAR_SLAB,
			ExtendedItems.MORGOND, LOTRRecipes.GONDOR_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedItems.SMOOTH_MORGOND_STAIRS,
			ExtendedItems.SMOOTH_MORGOND_SLAB,
			ExtendedItems.SMOOTH_MORGOND_WALL,
			ExtendedItems.SMOOTH_MORGOND, LOTRRecipes.GONDOR_CRAFTING);
		//DWARVEN
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.OBSIDIAN_DWARVEN_BRICK,
			ExtendedItems.OBSIDIAN_DWARVEN_BRICK_STAIRS,
			ExtendedItems.OBSIDIAN_DWARVEN_BRICK_SLAB,
			ExtendedItems.OBSIDIAN_DWARVEN_BRICK_WALL,
			ExtendedItems.OBSIDIAN_SHARD, LOTRRecipes.DWARVEN_CRAFTING, "_on_dwarven");
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.OBSIDIAN_DWARVEN_BRICK,
			ExtendedItems.OBSIDIAN_DWARVEN_BRICK_STAIRS,
			ExtendedItems.OBSIDIAN_DWARVEN_BRICK_SLAB,
			ExtendedItems.OBSIDIAN_DWARVEN_BRICK_WALL,
			ExtendedItems.OBSIDIAN_SHARD, LOTRRecipes.BLUE_MOUNTAINS_CRAFTING, "_on_blue_dwarven");
		makeThrowableAxeRecipe(consumer, ExtendedItems.DWARVEN_THROWABLE_AXE, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.DWARVEN_CRAFTING);
		makeMattockRecipe(consumer, ExtendedItems.DWARVEN_MATTOCK, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.DWARVEN_CRAFTING);
		// BLUE MOUNTAIN DWARVEN
		makeThrowableAxeRecipe(consumer, ExtendedItems.BLUE_DWARVEN_THROWABLE_AXE, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		makeMattockRecipe(consumer, ExtendedItems.BLUE_DWARVEN_MATTOCK, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		// IRON 
		makeThrowableAxeRecipe(consumer, ExtendedItems.IRON_THROWABLE_AXE, Tags.Items.INGOTS_IRON, "iron_ingot", null);
		// BRONZE
		makeThrowableAxeRecipe(consumer, ExtendedItems.BRONZE_THROWABLE_AXE, LOTRTags.Items.INGOTS_BRONZE, "bronze", null);
		// MITHRIL
		makeMattockRecipe(consumer, ExtendedItems.MITHRIL_MATTOCK, ExtendedTags.Items.MITHRIL_INGOT, "mithril_ingot", null);
		// Crystal Chandeliers
		makeCrystalChandelierRecipe(consumer, ExtendedItems.EDHELVIR_CRYSTAL_CHANDELIER, LOTRItems.EDHELVIR_CRYSTAL, LOTRRecipes.ANY_HIGH_ELVEN);
		makeCrystalChandelierRecipe(consumer, ExtendedItems.GLOWSTONE_CRYSTAL_CHANDELIER, LOTRItems.GLOWSTONE_CRYSTAL, null);
		makeCrystalChandelierRecipe(consumer, ExtendedItems.GULDURIL_CRYSTAL_CHANDELIER, LOTRItems.GULDURIL_CRYSTAL, LOTRRecipes.ANY_MORGUL);
		//ITEMS
		// TORCH CONVERSIONS
		// DWARVEN
		makeTorchConversionRecipeDirect(consumer, ()->Items.TORCH, LOTRItems.DWARVEN_TORCH, LOTRRecipes.ANY_DWARVEN);
		makeTorchConversionRecipeDirect(consumer, LOTRItems.DWARVEN_TORCH, ()->Items.TORCH, null);
		//MORGUL
		makeTorchConversionRecipeItem(consumer, ()->Items.TORCH, ExtendedItems.MORGUL_TORCH, LOTRItems.GULDURIL, 4, LOTRRecipes.ANY_MORGUL);
		makeTorchConversionRecipeDirect(consumer, ExtendedItems.MORGUL_TORCH, ()->Items.TORCH, null);
		//HIGH ELVEN
		makeTorchConversionRecipeItem(consumer, ()->Items.TORCH, LOTRItems.HIGH_ELVEN_TORCH, LOTRItems.EDHELVIR, 4, LOTRRecipes.ANY_HIGH_ELVEN);
		makeTorchConversionRecipeDirect(consumer, LOTRItems.HIGH_ELVEN_TORCH, ()->Items.TORCH, null);
		makeArmorRecipe(consumer, ExtendedItems.GALVORN_HELMET,
			ExtendedItems.GALVORN_CHESTPLATE,
			ExtendedItems.GALVORN_LEGGINGS,
			ExtendedItems.GALVORN_BOOTS,
			ExtendedItems.GALVORN_INGOT, LOTRRecipes.ANY_ELVEN);
		//BLUBBER
		makeTorchConversionRecipeItem(consumer, ()->Items.TORCH, LOTRItems.BLUBBER_TORCH, LOTRItems.BLUBBER, 4, LOTRRecipes.LOSSOTH_CRAFTING);
		makeTorchConversionRecipeDirect(consumer, LOTRItems.BLUBBER_TORCH, ()->Items.TORCH, null);
		//MALLORN
		makeTorchConversionRecipeDirect(consumer, LOTRItems.BLUE_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipeTag(consumer, ()->Items.TORCH, LOTRItems.BLUE_MALLORN_TORCH, LOTRTags.Items.BLUE_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		makeTorchConversionRecipeDirect(consumer, LOTRItems.GOLD_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipeTag(consumer, ()->Items.TORCH, LOTRItems.GOLD_MALLORN_TORCH, LOTRTags.Items.GOLD_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		makeTorchConversionRecipeDirect(consumer, LOTRItems.GREEN_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipeTag(consumer, ()->Items.TORCH, LOTRItems.GREEN_MALLORN_TORCH, LOTRTags.Items.GREEN_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		makeTorchConversionRecipeDirect(consumer, LOTRItems.SILVER_MALLORN_TORCH, ()->Items.TORCH, null);
		makeTorchConversionRecipeTag(consumer, ()->Items.TORCH, LOTRItems.SILVER_MALLORN_TORCH, LOTRTags.Items.SILVER_MALLORN_COLORANTS, 4, LOTRRecipes.GALADHRIM_CRAFTING);
		//ORC
		makeTorchConversionRecipeDirect(consumer, LOTRItems.ORC_TORCH, ()->Items.TORCH, null);
		ExtendedFactionShapedRecipeBuilder.shaped(LOTRItems.ORC_TORCH.get(), 1, LOTRRecipes.ANY_ORC)
		.pattern("T")
		.pattern("S")
		.define('T', Ingredient.of(Items.TORCH))
		.define('S', Ingredient.of(STICKS))
		.unlockedBy("has_torch", has(Items.TORCH))
		.unlockedBy("has_stick", has(STICKS))
		.save(consumer, new ResourceLocation(LOTRItems.ORC_TORCH.get().getRegistryName()+"_from_torch"));
		GENERATEDRECIPES.add(ExtendedItems.SKULL_GOBLET.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.SKULL_GOBLET.get())
		.pattern("S")
		.pattern("T")
		.define('S', Ingredient.of(Items.SKELETON_SKULL))
		.define('T', Ingredient.of(ExtendedTags.Items.TIN_INGOT))
		.unlockedBy("has_"+Items.SKELETON_SKULL.getRegistryName().getPath(), has(Items.SKELETON_SKULL))
		.save(consumer, ExtendedItems.SKULL_GOBLET);

		GENERATEDRECIPES.add(ExtendedItems.ROPE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.ROPE.get())
		.pattern("S")
		.pattern("S")
		.pattern("S")
		.define('S', Ingredient.of(Tags.Items.STRING))
		.unlockedBy("has_string", has(Tags.Items.STRING))
		.save(consumer, ExtendedItems.ROPE);
		// BOWS
		makeBowRecipe(consumer, ExtendedItems.GUNDABAD_URUK_BOW, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeBowRecipe(consumer, ExtendedItems.ORC_BOW, ExtendedTags.Items.ORC_STEEL_INGOT, "orc_steel", LOTRRecipes.ANY_ORC);
		makeBowRecipe(consumer, ExtendedItems.RANGER_BOW, STICKS, "sticks", LOTRRecipes.RANGER_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.LONGBOW.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.LONGBOW.get())
		.pattern("XXX")
		.pattern("X #")
		.pattern("X##")
		.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
		.define('X', Ingredient.of(STICKS))
		.unlockedBy("has_"+ExtendedItems.BOWSTRING.get().getRegistryName().getPath(), has(ExtendedItems.BOWSTRING.get()))
		.unlockedBy("has_stick", has(STICKS))
		.save(consumer, ExtendedItems.LONGBOW);
		GENERATEDRECIPES.add(ExtendedItems.BOWSTRING.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.BOWSTRING.get())
		.requires(Tags.Items.STRING)
		.requires(Tags.Items.STRING)
		.requires(Tags.Items.STRING)
		.unlockedBy("has_string", has(Tags.Items.STRING))
		.save(consumer, ExtendedItems.BOWSTRING.get().getRegistryName());
		makeBowRecipe(consumer, ExtendedItems.RIVENDELL_BOW, STICKS, "sticks", LOTRRecipes.RIVENDELL_CRAFTING);
		makeBowRecipe(consumer, ExtendedItems.LINDON_BOW, STICKS, "sticks", LOTRRecipes.LINDON_CRAFTING);
		makeCrossbowRecipe(consumer, ExtendedItems.BRONZE_CROSSBOW, ExtendedTags.Items.BRONZE_INGOT, "bronze_ingot", null);
		makeCrossbowRecipe(consumer, ExtendedItems.IRON_CROSSBOW, Tags.Items.INGOTS_IRON, "iron_ingot", null);
		makeCrossbowRecipe(consumer, ExtendedItems.URUK_CROSSBOW, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", LOTRRecipes.URUK_CRAFTING);
		makeCrossbowRecipe(consumer, ExtendedItems.MITHRIL_CROSSBOW, ExtendedTags.Items.MITHRIL_INGOT, "mithril_ingot", null);
		makeBowRecipe(consumer, ExtendedItems.ROHAN_BOW, STICKS, "sticks", LOTRRecipes.ROHAN_CRAFTING);

		GENERATEDRECIPES.add(ExtendedItems.CHISEL.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHISEL.get())
		.pattern("SI")
		.define('S', Ingredient.of(STICKS))
		.define('I', Ingredient.of(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.unlockedBy("has_iron_ingots", has(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.save(consumer, ExtendedItems.CHISEL);
		GENERATEDRECIPES.add(ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BRANDING_IRON.get())
		.pattern("  #")
		.pattern(" X ")
		.pattern("#  ")
		.define('#', Ingredient.of(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.define('X', Ingredient.of(Tags.Items.LEATHER))
		.unlockedBy("has_iron_ingots", has(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.save(consumer, ExtendedItems.BRANDING_IRON);
		// Extended foods
		GENERATEDRECIPES.add(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHEESE_WHEEL.get())
		.pattern("###")
		.pattern("XSX")
		.pattern("###")
		.define('#', Ingredient.of(ExtendedItems.CHEESE_CURDS.get()))
		.define('X', Ingredient.of(LOTRItems.SALT.get()))
		.define('S', Ingredient.of(Tags.Items.MUSHROOMS))
		.unlockedBy("has_"+ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_CURDS.get()))
		.save(consumer, ExtendedItems.CHEESE_WHEEL);
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHEESE_WHEEL.get())
		.pattern("XXX")
		.pattern("#X#")
		.pattern("XXX")
		.define('X', Ingredient.of(ExtendedItems.CHEESE_SLICE.get()))
		.define('#', Ingredient.of(ExtendedItems.CHEESE_CURDS.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_SLICE.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_SLICE.get()))
		.save(consumer, new ResourceLocation(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().toString()+"_from_slices"));
		GENERATEDRECIPES.add(ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BANANA_CAKE.get())
		.pattern("###")
		.pattern("XSX")
		.pattern("ZZZ")
		.define('#', Ingredient.of(Items.MILK_BUCKET))
		.define('X', Ingredient.of(ExtendedItems.BANANA.get()))
		.define('S', Ingredient.of(Tags.Items.EGGS))
		.define('Z', Ingredient.of(Tags.Items.CROPS_WHEAT))
		.unlockedBy("has_"+ExtendedItems.BANANA.get().getRegistryName().getPath(), has(ExtendedItems.BANANA.get()))
		.save(consumer, ExtendedItems.BANANA_CAKE);
		GENERATEDRECIPES.add(ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BANANA_BREAD.get())
		.pattern("XSX")
		.define('S', Ingredient.of(ExtendedItems.BANANA.get()))
		.define('X', Ingredient.of(Tags.Items.CROPS_WHEAT))
		.unlockedBy("has_"+ExtendedItems.BANANA.get().getRegistryName().getPath(), has(ExtendedItems.BANANA.get()))
		.save(consumer, ExtendedItems.BANANA_BREAD);
		GENERATEDRECIPES.add(ExtendedItems.LEEK_SOUP.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BANANA_BREAD.get())
		.pattern("LPL")
		.pattern(" B ")
		.define('L', Ingredient.of(ExtendedTags.Items.CROPS_LEEK))
		.define('P', Ingredient.of(Tags.Items.CROPS_POTATO))
		.define('B', Ingredient.of(Items.BOWL))
		.unlockedBy("has_"+ExtendedItems.LEEK.get().getRegistryName().getPath(), has(ExtendedItems.LEEK.get()))
		.save(consumer, ExtendedItems.LEEK_SOUP);
		makePieRecipe(consumer, ExtendedItems.BLACKBERRY_PIE, ExtendedItems.BLACKBERRY, null);
		makePieRecipe(consumer, ExtendedItems.BLUEBERRY_PIE, ExtendedItems.BLUEBERRY, null);
		makePieRecipe(consumer, ExtendedItems.CRANBERRY_PIE, ExtendedItems.CRANBERRY, null);
		makePieRecipe(consumer, ExtendedItems.ELDERBERRY_PIE, ExtendedItems.ELDERBERRY, null);
		makePieRecipe(consumer, ExtendedItems.RASPBERRY_PIE, ExtendedItems.RASPBERRY, null);
		makePieRecipe(consumer, ExtendedItems.SWEET_BERRY_PIE, ()->Items.SWEET_BERRIES, null);
		makePieRecipe(consumer, ExtendedItems.WILDBERRY_PIE, ExtendedItems.WILDBERRY, null);
		GENERATEDRECIPES.add(ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.BOTTLE_OF_POISON.get())
		.requires(ExtendedItems.WILDBERRY.get())
		.requires(Items.GLASS_BOTTLE)
		.unlockedBy("has_"+ExtendedItems.WILDBERRY.get().getRegistryName().getPath(), has(ExtendedItems.WILDBERRY.get()))
		.save(consumer, ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName());
		// Misc Materials
		GENERATEDRECIPES.add(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.SILVER_COIN_ONE.get(), 4)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(LOTRItems.SILVER_NUGGET.get()))
		.unlockedBy("has_"+LOTRItems.SILVER_NUGGET.get().getRegistryName().getPath(), has(LOTRItems.SILVER_NUGGET.get()))
		.save(consumer, ExtendedItems.SILVER_COIN_ONE);
		GENERATEDRECIPES.add(ExtendedItems.HITHLAIN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HITHLAIN.get(), 3, LOTRRecipes.GALADHRIM_CRAFTING)
		.pattern("###")
		.define('#', Ingredient.of(Tags.Items.STRING))
		.unlockedBy("has_string", has(Tags.Items.STRING))
		.save(consumer, ExtendedItems.HITHLAIN);
		//ExtendedDyeableRecipeBuilder.dyeing(ExtendedRecipeSerializers.EXTENDED_LEATHER_HAT.get(), IRecipeType.CRAFTING)
		//.save(consumer, lotrLoc("leather_hat"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, ExtendedItems.LEATHER_HAT.get()).save(consumer, extendedLoc("leather_hat_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, ExtendedItems.LEATHER_BOAR_ARMOR.get()).save(consumer, extendedLoc("leather_boar_armor_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_BOOTS).save(consumer, extendedLoc("leather_boots_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_LEGGINGS).save(consumer, extendedLoc("leather_leggings_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_CHESTPLATE).save(consumer, extendedLoc("leather_chestplate_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_HELMET).save(consumer, extendedLoc("leather_helmet_on_faction_table"));
		ExtendedDyeableRecipeBuilder.dyeing(LOTRRecipes.ANY_FACTION, Items.LEATHER_HORSE_ARMOR).save(consumer, extendedLoc("leather_horse_armor_on_faction_table"));
		GENERATEDRECIPES.add(ExtendedItems.COMMANDHORN.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.COMMANDHORN.get())
		.pattern("#X#")
		.define('#', Ingredient.of(ExtendedTags.Items.BRONZE_INGOT))
		.define('X', Ingredient.of(HORNS))
		.unlockedBy("has_horn", has(HORNS))
		.save(consumer, ExtendedItems.COMMANDHORN);
		ExtendedCustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_BANNER_PROT_REMOVAL.get()).save(consumer, extendedLoc("banner_protection_removal"));
		ExtendedCustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_TIPPED_ARROW.get()).save(consumer, extendedLoc("tipped_arrow_poison"));
		ExtendedCustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_DAGGER_POISONING.get()).save(consumer, extendedLoc("dagger_poisoning"));
		GENERATEDRECIPES.add(ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.BLACK_URUK_STEEL_INGOT.get(), LOTRRecipes.ORC_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ORC_STEEL_INGOT)
		.requires(ExtendedTags.Items.MORGUL_IRON_ORE)
		.requiresAlloy(LOTRItems.DURNOR.get())
		.unlockedBy("has_"+LOTRItems.DURNOR.get().getRegistryName().getPath(), has(LOTRItems.DURNOR.get()))
		.save(consumer, ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName());
		GENERATEDRECIPES.add(ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.GALVORN_INGOT.get(), LOTRRecipes.DWARVEN_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedItems.SKY_IRON.get())
		.requiresAlloy(LOTRItems.EDHELVIR.get())
		.unlockedBy("has_"+ExtendedItems.SKY_IRON.get().getRegistryName().getPath(), has(ExtendedItems.SKY_IRON.get()))
		.save(consumer, ExtendedItems.GALVORN_INGOT.get().getRegistryName());
		GENERATEDRECIPES.add(ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.GILDED_IRON_INGOT.get(), LOTRRecipes.ALLOY_SERIALIZER.get())
		.requires(Tags.Items.ORES_IRON)
		.requires(Tags.Items.INGOTS_IRON)
		.requiresAlloy(Items.GOLD_NUGGET)
		.unlockedBy("has_"+Items.GOLD_NUGGET.getRegistryName().getPath(), has(Items.GOLD_NUGGET))
		.save(consumer, ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName());
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.MORGUL_STEEL_INGOT.get(), LOTRRecipes.ORC_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ORC_STEEL_INGOT)
		.requires(ExtendedTags.Items.MORGUL_IRON_ORE)
		.requiresAlloy(LOTRItems.GULDURIL.get())
		.unlockedBy("has_"+LOTRItems.GULDURIL.get().getRegistryName().getPath(), has(LOTRItems.GULDURIL.get()))
		.save(consumer, ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName());
		GENERATEDRECIPES.add(ExtendedItems.ITHILDIN.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.ITHILDIN.get(), LOTRRecipes.ELVEN_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.SILVER_INGOT)
		.requires(ExtendedTags.Items.SILVER_ORE)
		.requiresAlloy(LOTRItems.MITHRIL_NUGGET.get())
		.unlockedBy("has_"+LOTRItems.MITHRIL_NUGGET.get().getRegistryName().getPath(), has(LOTRItems.MITHRIL_NUGGET.get()))
		.save(consumer, ExtendedItems.ITHILDIN.get().getRegistryName());
		//Arnor
		makePillarSlabRecipe(consumer, ExtendedItems.CRACKED_ARNOR_PILLAR,
			ExtendedItems.CRACKED_ARNOR_PILLAR_SLAB,
			LOTRItems.CRACKED_ARNOR_BRICK, LOTRRecipes.RANGER_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedItems.MOSSY_ARNOR_PILLAR,
			ExtendedItems.MOSSY_ARNOR_PILLAR_SLAB,
			LOTRItems.MOSSY_ARNOR_BRICK, LOTRRecipes.RANGER_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedItems.CRACKED_STONE_PILLAR,
			ExtendedItems.CRACKED_STONE_PILLAR_SLAB,
			()->Items.CRACKED_STONE_BRICKS, null);
		GENERATEDRECIPES.add(ExtendedItems.LARGE_POT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.LARGE_POT.get())
		.pattern("# #")
		.pattern("# #")
		.pattern("###")
		.define('#', Ingredient.of(Items.BRICK))
		.unlockedBy("has_brick", has(Items.BRICK))
		.save(consumer, ExtendedItems.LARGE_POT);
		GENERATEDRECIPES.add(ExtendedItems.LARGE_FLOWER_POT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.LARGE_FLOWER_POT.get())
		.pattern("# #")
		.pattern("###")
		.define('#', Ingredient.of(Items.BRICK))
		.unlockedBy("has_brick", has(Items.BRICK))
		.save(consumer, ExtendedItems.LARGE_FLOWER_POT);
		GENERATEDRECIPES.add(ExtendedItems.CHIMNEYPOT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CHIMNEYPOT.get())
		.pattern("# #")
		.pattern("# #")
		.define('#', Ingredient.of(Items.BRICK))
		.unlockedBy("has_brick", has(Items.BRICK))
		.save(consumer, ExtendedItems.CHIMNEYPOT);
		//Shields
		makeShieldRecipe(consumer, ExtendedItems.DUNEDAIN_SHIELD, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.RANGER_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.GUNDABAD_SHIELD, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", ExtendedRecipes.NORTHERN_ORC_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.BREE_SHIELD, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.BREE_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.BLUE_DWARVEN_SHIELD, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.RIVENDELL_SHIELD, ExtendedTags.Items.ELVEN_STEEL_INGOT, "elven_steel", LOTRRecipes.RIVENDELL_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.LINDON_SHIELD, ExtendedTags.Items.ELVEN_STEEL_INGOT, "elven_steel", LOTRRecipes.LINDON_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.URUK_SHIELD, ExtendedTags.Items.URUK_STEEL_INGOT, "uruk_steel", LOTRRecipes.URUK_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.DUNLENDING_SHIELD, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.DUNLENDING_CRAFTING);
		makeShieldRecipe(consumer, ExtendedItems.DWARVEN_SHIELD, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.DWARVEN_CRAFTING);
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
		.save(consumer, ExtendedItems.LOTHLORIEN_BANNER);
		makeBannerPatternRecipe(consumer, ExtendedItems.LOTHLORIEN_BANNER, ExtendedItems.LOTHLORIEN_BANNER_PATTERN, LOTRRecipes.GALADHRIM_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.MORDOR_BANNER, ExtendedItems.MORDOR_BANNER_PATTERN, LOTRRecipes.MORDOR_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.RIVENDELL_BANNER, ExtendedItems.RIVENDELL_BANNER_PATTERN, LOTRRecipes.RIVENDELL_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.ROHAN_BANNER, ExtendedItems.ROHAN_BANNER_PATTERN, LOTRRecipes.ROHAN_CRAFTING);
		makeBannerAndPatternRecipe(consumer, ExtendedItems.WOODLAND_REALM_BANNER, ExtendedItems.WOODLAND_REALM_BANNER_PATTERN, LOTRRecipes.WOOD_ELVEN_CRAFTING);


		makeBoarArmorRecipe(consumer, ExtendedItems.LEATHER_BOAR_ARMOR, Tags.Items.LEATHER, "leather", LOTRRecipes.ANY_DWARVEN);
		makeBoarArmorRecipe(consumer, ExtendedItems.DWARVEN_BOAR_ARMOR, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.DWARVEN_CRAFTING);
		makeBoarArmorRecipe(consumer, ExtendedItems.BLUE_DWARVEN_BOAR_ARMOR, ExtendedTags.Items.DWARVEN_STEEL_INGOT, "dwarven_steel_ingot", LOTRRecipes.BLUE_MOUNTAINS_CRAFTING);

		//Containers
		GENERATEDRECIPES.add(ExtendedItems.REED_BASKET.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.REED_BASKET.get())
		.pattern("LLL")
		.pattern("L L")
		.pattern("LLL")
		.define('L', Ingredient.of(LOTRItems.DRIED_REEDS.get()))
		.unlockedBy("has_"+LOTRItems.DRIED_REEDS.get().getRegistryName().getPath(), has(LOTRItems.DRIED_REEDS.get()))
		.save(consumer, ExtendedItems.REED_BASKET);
		GENERATEDRECIPES.add(ExtendedItems.ORC_BARREL.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ORC_BARREL.get(), LOTRRecipes.ANY_ORC)
		.pattern("PLP")
		.pattern("P P")
		.pattern("PLP")
		.define('L', Ingredient.of(Tags.Items.LEATHER))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_leather", has(Tags.Items.LEATHER))
		.unlockedBy("has_planks", has(PLANKS))
		.save(consumer, ExtendedItems.ORC_BARREL);
		GENERATEDRECIPES.add(ExtendedItems.WEAPON_RACK.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.WEAPON_RACK.get())
		.pattern("X X")
		.pattern("###")
		.define('#', Ingredient.of(PLANKS))
		.define('X', Ingredient.of(STICKS))
		.unlockedBy("has_planks", has(PLANKS))
		.save(consumer, ExtendedItems.WEAPON_RACK);
		//Birdcages
		GENERATEDRECIPES.add(ExtendedItems.WOODEN_BIRDCAGE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.WOODEN_BIRDCAGE.get())
		.pattern("BBB")
		.pattern("B B")
		.pattern("III")
		.define('B', Ingredient.of(STICKS))
		.define('I', Ingredient.of(PLANKS))
		.unlockedBy("has_sticks", has(STICKS))
		.save(consumer, ExtendedItems.WOODEN_BIRDCAGE);
		makeBirdcageRecipe(consumer, ExtendedItems.BRONZE_BIRDCAGE, LOTRItems.BRONZE_BARS, ExtendedTags.Items.BRONZE_INGOT, null);
		makeBirdcageRecipe(consumer, ExtendedItems.IRON_BIRDCAGE, ()->Items.IRON_BARS, Tags.Items.INGOTS_IRON, null);
		makeBirdcageRecipe(consumer, ExtendedItems.SILVER_BIRDCAGE, LOTRItems.SILVER_BARS, LOTRTags.Items.INGOTS_SILVER, null);
		makeBirdcageRecipe(consumer, ExtendedItems.GOLDEN_BIRDCAGE, LOTRItems.GOLD_BARS, Tags.Items.INGOTS_GOLD, null);
		makeBirdcageRecipe(consumer, ExtendedItems.MITHRIL_BIRDCAGE, LOTRItems.MITHRIL_BARS, ExtendedTags.Items.MITHRIL_INGOT, null);
		makeBarsRecipe(consumer, ExtendedItems.NUMENOREAN_BARS, Tags.Items.INGOTS_IRON, "iron_ingot", LOTRRecipes.GONDOR_CRAFTING);
		//Dirts
		GENERATEDRECIPES.add(ExtendedItems.WASTE_BLOCK.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.WASTE_BLOCK.get(), 4)
		.pattern("FD")
		.pattern("DB")
		.define('D', Ingredient.of(Items.DIRT))
		.define('F', Ingredient.of(ExtendedTags.Items.FLESH))
		.define('B', Ingredient.of(Tags.Items.BONES))
		.unlockedBy("has_"+Items.DIRT.getRegistryName().getPath(), has(Items.DIRT))
		.save(consumer, ExtendedItems.WASTE_BLOCK);
		GENERATEDRECIPES.add(ExtendedItems.BARREN_DIRT.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.BARREN_DIRT.get(), 8)
		.pattern("###")
		.pattern("#X#")
		.pattern("###")
		.define('#', Ingredient.of(Blocks.DIRT.asItem()))
		.define('X', Ingredient.of(LOTRItems.SALT.get()))
		.unlockedBy("has_"+LOTRItems.SALT.get().getRegistryName().getPath(), has(LOTRItems.SALT.get()))
		.save(consumer, ExtendedItems.BARREN_DIRT);
		ShapelessRecipeBuilder.shapeless(Blocks.DIRT)
		.requires(Ingredient.of(ExtendedItems.BARREN_DIRT.get()))
		.requires(Ingredient.of(Tags.Items.SEEDS))
		.unlockedBy("has_"+ExtendedItems.BARREN_DIRT.get().getRegistryName().getPath(), has(ExtendedItems.BARREN_DIRT.get()))
		.save(consumer, new ResourceLocation(Blocks.DIRT.getRegistryName().toString()+"_from_"+ExtendedItems.BARREN_DIRT.get().getRegistryName().getPath()));
		makeSlabRecipe(consumer, ExtendedItems.BARREN_DIRT_SLAB, ExtendedItems.BARREN_DIRT, null, "");
		makeSlabRecipe(consumer, ExtendedItems.COARSE_DIRT_SLAB, () -> Items.COARSE_DIRT, null, "");
		makeSlabRecipe(consumer, ExtendedItems.DIRT_SLAB, () -> Items.DIRT, null, "");
		makeSlabRecipe(consumer, ExtendedItems.GRASS_BLOCK_SLAB, () -> Items.GRASS_BLOCK, null, "");
		makeSlabRecipe(consumer, ExtendedItems.GRASS_PATH_SLAB, () -> Items.GRASS_PATH, null, "");
		makeSlabRecipe(consumer, ExtendedItems.MYCELIUM_SLAB, () -> Items.MYCELIUM, null, "");
		makeSlabRecipe(consumer, ExtendedItems.PODZOL_SLAB, () -> Items.PODZOL, null, "");
		makeSlabRecipe(consumer, ExtendedItems.SNOW_SLAB, () -> Items.SNOW, null, "");
		makeSlabRecipe(consumer, ExtendedItems.SNOW_PATH_SLAB, LOTRItems.SNOW_PATH, null, "");
		makeSlabRecipe(consumer, ExtendedItems.CHALK_PATH_SLAB, LOTRItems.CHALK_PATH, null, "");
		makeSlabRecipe(consumer, ExtendedItems.MORDOR_DIRT_SLAB, LOTRItems.MORDOR_DIRT, LOTRRecipes.MORDOR_CRAFTING, "");
		makeSlabRecipe(consumer, ExtendedItems.MORDOR_DIRT_PATH_SLAB, LOTRItems.MORDOR_DIRT_PATH, LOTRRecipes.MORDOR_CRAFTING, "");

		//Scorched blocks
		GENERATEDRECIPES.add(ExtendedItems.SCORCHED_COBBLESTONE.get().getRegistryName().getPath());
		CookingRecipeBuilder.blasting(Ingredient.of(Blocks.COBBLESTONE.asItem()), ExtendedItems.SCORCHED_COBBLESTONE.get(), 0.1F, 100)
		.unlockedBy("has_"+Blocks.COBBLESTONE.getRegistryName().getPath(), has(Blocks.COBBLESTONE))
		.save(consumer, ExtendedItems.SCORCHED_COBBLESTONE.get().getRegistryName().getPath()+"_from_blasting");
		makeStairSlabWallRecipe(consumer, ExtendedItems.SCORCHED_COBBLESTONE_STAIRS,
			ExtendedItems.SCORCHED_COBBLESTONE_SLAB, ExtendedItems.SCORCHED_COBBLESTONE_WALL,
			()->ExtendedItems.SCORCHED_COBBLESTONE.get().asItem(), null);
		GENERATEDRECIPES.add(ExtendedItems.SCORCHED_STONE.get().getRegistryName().getPath());
		CookingRecipeBuilder.blasting(Ingredient.of(Blocks.STONE.asItem()), ExtendedItems.SCORCHED_STONE.get(), 0.1F, 100)
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE))
		.save(consumer, ExtendedItems.SCORCHED_STONE.get().getRegistryName().getPath()+"_from_blasting");
		makeStairSlabWallRecipe(consumer, ExtendedItems.SCORCHED_STONE_STAIRS,
			ExtendedItems.SCORCHED_STONE_SLAB, ExtendedItems.SCORCHED_STONE_WALL,
			()->ExtendedItems.SCORCHED_STONE.get().asItem(), null);
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.SCORCHED_BRICK,
			ExtendedItems.SCORCHED_BRICK_STAIRS,
			ExtendedItems.SCORCHED_BRICK_SLAB,
			ExtendedItems.SCORCHED_BRICK_WALL,
			()->ExtendedItems.SCORCHED_STONE.get(), null,"");
		CookingRecipeBuilder.blasting(Ingredient.of(Blocks.STONE_BRICKS.asItem()), ExtendedItems.SCORCHED_BRICK.get(), 0.1F, 100)
		.unlockedBy("has_"+Blocks.STONE_BRICKS.getRegistryName().getPath(), has(Blocks.STONE_BRICKS))
		.save(consumer, ExtendedItems.SCORCHED_BRICK.get().getRegistryName().getPath()+"_from_blasting");
		//Missing smooth stair recipes
		makeStairRecipe(consumer, ()->ExtendedItems.SMOOTH_MORDOR_ROCK_STAIRS.get(),
			()->LOTRItems.SMOOTH_MORDOR_ROCK.get().asItem(), LOTRRecipes.MORDOR_CRAFTING, "");
		makeStairRecipe(consumer, ()->ExtendedItems.SMOOTH_ROHAN_ROCK_STAIRS.get(),
			()->LOTRItems.SMOOTH_ROHAN_ROCK.get().asItem(), LOTRRecipes.ROHAN_CRAFTING, "");
		makeStairRecipe(consumer, ()->ExtendedItems.SMOOTH_RED_ROCK_STAIRS.get(),
			()->LOTRItems.SMOOTH_RED_ROCK.get().asItem(), null, "");
		makeStairRecipe(consumer, ()->ExtendedItems.SMOOTH_BLUE_ROCK_STAIRS.get(),
			()->LOTRItems.SMOOTH_BLUE_ROCK.get().asItem(), null, "");
		makeStairRecipe(consumer, ()->ExtendedItems.SMOOTH_STONE_STAIRS.get(),
			Blocks.SMOOTH_STONE::asItem, null, "");
		//drystone
		ShapelessRecipeBuilder.shapeless(LOTRItems.DRYSTONE.get())
		.requires(ExtendedItems.COBBLED_DRYSTONE.get())
		.unlockedBy("has_"+ExtendedItems.COBBLED_DRYSTONE.get().getRegistryName().getPath(), has(ExtendedItems.COBBLED_DRYSTONE.get()))
		.save(consumer, new ResourceLocation(LOTRItems.DRYSTONE.get().getRegistryName().toString()+"_crafted_from_"+ExtendedItems.COBBLED_DRYSTONE.get().getRegistryName().getPath()));
		ShapelessRecipeBuilder.shapeless(LOTRItems.DRYSTONE.get())
		.requires(ExtendedItems.CUT_DRYSTONE.get())
		.unlockedBy("has_"+ExtendedItems.CUT_DRYSTONE.get().getRegistryName().getPath(), has(ExtendedItems.CUT_DRYSTONE.get()))
		.save(consumer, new ResourceLocation(LOTRItems.DRYSTONE.get().getRegistryName().toString()+"_crafted_from_"+ExtendedItems.CUT_DRYSTONE.get().getRegistryName().getPath()));
		make2x2BlockRecipe(consumer, ExtendedItems.CUT_DRYSTONE, ()->LOTRItems.DRYSTONE.get().asItem(), null,"");
		make3x3BlockRecipe(consumer, ExtendedItems.COBBLED_DRYSTONE, ()->LOTRItems.DRYSTONE.get().asItem(), null);
		makeStairSlabWallRecipe(consumer, ExtendedItems.DRYSTONE_STAIRS,
			ExtendedItems.DRYSTONE_SLAB,
			ExtendedItems.DRYSTONE_WALL,
			()->LOTRItems.DRYSTONE.get().asItem(), null);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_DRYSTONE,
			()->LOTRItems.DRYSTONE.get().asItem(), null);
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_DRYSTONE,
			()->ExtendedItems.COBBLED_DRYSTONE.get().asItem(), null, "_from_cobbled_drystone");
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_DRYSTONE_STAIRS,
			ExtendedItems.MOSSY_DRYSTONE_SLAB,
			ExtendedItems.MOSSY_DRYSTONE_WALL,
			()->ExtendedItems.MOSSY_DRYSTONE.get().asItem(), null);
		GENERATEDRECIPES.add(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CROSSED_WATTLE_AND_DAUB.get(), 4)
		.pattern("SDS")
		.pattern("DSD")
		.pattern("SDS")
		.define('S', Ingredient.of(STICKS))
		.define('D', Ingredient.of(LOTRItems.WATTLE_AND_DAUB.get()))
		.unlockedBy("has_"+LOTRItems.WATTLE_AND_DAUB.get().getRegistryName().getPath(), has(LOTRItems.WATTLE_AND_DAUB.get()))
		.save(consumer, ExtendedItems.CROSSED_WATTLE_AND_DAUB);
		//Mud
		makeSlabRecipe(consumer, ()->ExtendedItems.MUD_SLAB.get(),
			()->ExtendedItems.MUD.get().asItem(), null, "");
		makeStairRecipe(consumer, ()->ExtendedItems.MUD_STAIRS.get(),
			()->ExtendedItems.MUD.get().asItem(), null, "");
		makeSlabRecipe(consumer, ()->ExtendedItems.MUD_PATH_SLAB.get(),
			()->ExtendedItems.MUD_PATH.get().asItem(), null, "");
		makeStairRecipe(consumer, ()->ExtendedItems.MUD_PATH_STAIRS.get(),
			()->ExtendedItems.MUD_PATH.get().asItem(), null, "");
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedItems.MUD_BRICK,
			ExtendedItems.MUD_BRICK_STAIRS,
			ExtendedItems.MUD_BRICK_SLAB,
			ExtendedItems.MUD_BRICK_WALL,
			()->ExtendedItems.MUD.get().asItem(), null,"");
		makeMossyBlockRecipe(consumer, ExtendedItems.MOSSY_MUD_BRICK,
			()->ExtendedItems.MUD_BRICK.get().asItem(), null);
		makeStairSlabWallRecipe(consumer, ExtendedItems.MOSSY_MUD_BRICK_STAIRS,
			ExtendedItems.MOSSY_MUD_BRICK_SLAB,
			ExtendedItems.MOSSY_MUD_BRICK_WALL,
			()->ExtendedItems.MOSSY_MUD_BRICK.get().asItem(), null);
		makeCrackedBlockRecipe(consumer, ExtendedItems.CRACKED_MUD_BRICK,
			()->ExtendedItems.MUD_BRICK.get().asItem());
		makeStairSlabWallRecipe(consumer, ExtendedItems.CRACKED_MUD_BRICK_STAIRS,
			ExtendedItems.CRACKED_MUD_BRICK_SLAB,
			ExtendedItems.CRACKED_MUD_BRICK_WALL,
			()->ExtendedItems.CRACKED_MUD_BRICK.get().asItem(), null);
		GENERATEDRECIPES.add(ExtendedItems.MOSS_CARPET.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.MOSS_CARPET.get(), 3)
		.pattern("XX")
		.define('X', Ingredient.of(ExtendedItems.MOSS_BLOCK.get()))
		.unlockedBy("has_"+ExtendedItems.MOSS_BLOCK.get().getRegistryName().getPath(), has(ExtendedItems.MOSS_BLOCK.get()))
		.save(consumer, ExtendedItems.MOSS_CARPET);
		//Natural Stones
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.GNEISS_ROCK_STAIRS,
		//			ExtendedItems.GNEISS_ROCK_SLAB,
		//			ExtendedItems.GNEISS_ROCK_WALL,
		//			ExtendedItems.GNEISS_ROCK, null);
		//		make2x2BlockRecipe(consumer, ExtendedItems.GNEISS_BRICK,
		//			ExtendedItems.GNEISS_ROCK, null);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.GNEISS_BRICK_STAIRS,
		//			ExtendedItems.GNEISS_BRICK_SLAB,
		//			ExtendedItems.GNEISS_BRICK_WALL,
		//			ExtendedItems.GNEISS_BRICK, null);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.LIMESTONE_ROCK_STAIRS,
		//			ExtendedItems.LIMESTONE_ROCK_SLAB,
		//			ExtendedItems.LIMESTONE_ROCK_WALL,
		//			ExtendedItems.LIMESTONE_ROCK, null);
		//		make2x2BlockRecipe(consumer, ExtendedItems.LIMESTONE_BRICK,
		//			ExtendedItems.LIMESTONE_ROCK, null);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.LIMESTONE_BRICK_STAIRS,
		//			ExtendedItems.LIMESTONE_BRICK_SLAB,
		//			ExtendedItems.LIMESTONE_BRICK_WALL,
		//			ExtendedItems.LIMESTONE_BRICK, null);
		//		make2x2BlockRecipe(consumer, ExtendedItems.LIMESTONE_TILE,
		//			ExtendedItems.LIMESTONE_BRICK, null);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.LIMESTONE_TILE_STAIRS,
		//			ExtendedItems.LIMESTONE_TILE_SLAB,
		//			ExtendedItems.LIMESTONE_TILE_WALL,
		//			ExtendedItems.LIMESTONE_TILE, null);
		//		makeCrackedBlockRecipe(consumer, ExtendedItems.POLISHED_GNEISS,
		//			ExtendedItems.GNEISS_ROCK);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.POLISHED_GNEISS_STAIRS,
		//			ExtendedItems.POLISHED_GNEISS_SLAB,
		//			ExtendedItems.POLISHED_GNEISS_WALL,
		//			ExtendedItems.POLISHED_GNEISS, null);
		//		makeCrackedBlockRecipe(consumer, ExtendedItems.POLISHED_LIMESTONE,
		//			ExtendedItems.LIMESTONE_ROCK);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.POLISHED_LIMESTONE_STAIRS,
		//			ExtendedItems.POLISHED_LIMESTONE_SLAB,
		//			ExtendedItems.POLISHED_LIMESTONE_WALL,
		//			ExtendedItems.POLISHED_LIMESTONE, null);
		//		makeCrackedBlockRecipe(consumer, ExtendedItems.POLISHED_SLATE,
		//			ExtendedItems.SLATE_ROCK);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.POLISHED_SLATE_STAIRS,
		//			ExtendedItems.POLISHED_SLATE_SLAB,
		//			ExtendedItems.POLISHED_SLATE_WALL,
		//			ExtendedItems.POLISHED_SLATE, null);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.SLATE_ROCK_STAIRS,
		//			ExtendedItems.SLATE_ROCK_SLAB,
		//			ExtendedItems.SLATE_ROCK_WALL,
		//			ExtendedItems.SLATE_ROCK, null);
		//		make2x2BlockRecipe(consumer, ExtendedItems.SLATE_BRICK,
		//			ExtendedItems.SLATE_ROCK, null);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.SLATE_BRICK_STAIRS,
		//			ExtendedItems.SLATE_BRICK_SLAB,
		//			ExtendedItems.SLATE_BRICK_WALL,
		//			ExtendedItems.SLATE_BRICK, null);
		//		makeCrackedBlockRecipe(consumer, ExtendedItems.SMOOTH_LIMESTONE,
		//			ExtendedItems.POLISHED_LIMESTONE);
		//		makeStairSlabWallRecipe(consumer, ExtendedItems.SMOOTH_LIMESTONE_STAIRS,
		//			ExtendedItems.SMOOTH_LIMESTONE_SLAB,
		//			ExtendedItems.SMOOTH_LIMESTONE_WALL,
		//			ExtendedItems.SMOOTH_LIMESTONE, null);
		GENERATEDRECIPES.add(ExtendedItems.DEAD_MOSS_VINES.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.DEAD_MOSS_VINES.get())
		.requires(Ingredient.of(Items.VINE))
		.requires(Ingredient.of(LOTRItems.SALT.get()))
		.unlockedBy("has_string", has(LOTRItems.SALT.get()))
		.save(consumer, ExtendedItems.DEAD_MOSS_VINES.get().getRegistryName());
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
		.saveNoAdvancement(consumer, ExtendedItems.HOIST.get().getRegistryName());
	}

	private void addMissingRenewedRecipes(Consumer<IFinishedRecipe> consumer) {
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_ANGMAR_BRICK,
			LOTRItems.ANGMAR_BRICK_SLAB, LOTRRecipes.ANGMAR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_ARNOR_BRICK,
			LOTRItems.ARNOR_BRICK_SLAB, LOTRRecipes.RANGER_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_BLUE_BRICK,
			LOTRItems.BLUE_BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_BRICK,
			()->Items.BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_CHALK_BRICK,
			LOTRItems.CHALK_BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_DALE_BRICK,
			LOTRItems.DALE_BRICK_SLAB, LOTRRecipes.DALE_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_DWARVEN_BRICK,
			LOTRItems.DWARVEN_BRICK_SLAB, LOTRRecipes.ANY_DWARVEN);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_GALADHRIM_BRICK,
			LOTRItems.GALADHRIM_BRICK_SLAB, LOTRRecipes.GALADHRIM_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_GONDOR_BRICK,
			LOTRItems.GONDOR_BRICK_SLAB, LOTRRecipes.GONDOR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_HIGH_ELVEN_BRICK,
			LOTRItems.HIGH_ELVEN_BRICK_SLAB, LOTRRecipes.ANY_HIGH_ELVEN);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_MORDOR_BASALT_BRICK,
			LOTRItems.MORDOR_BASALT_BRICK_SLAB, LOTRRecipes.MORDOR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_MORDOR_BRICK,
			LOTRItems.MORDOR_BRICK_SLAB, LOTRRecipes.MORDOR_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_NUMENOREAN_BRICK,
			LOTRItems.NUMENOREAN_BRICK_SLAB, LOTRRecipes.ANY_NUMENOREAN);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_RED_BRICK,
			LOTRItems.RED_BRICK_SLAB, null);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_ROHAN_BRICK,
			LOTRItems.ROHAN_BRICK_SLAB, LOTRRecipes.ROHAN_CRAFTING);
		makeCarvedBlockRecipeOnlySlabs(consumer, LOTRItems.CARVED_WOOD_ELVEN_BRICK,
			LOTRItems.WOOD_ELVEN_BRICK_SLAB, LOTRRecipes.WOOD_ELVEN_CRAFTING);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_ANGMAR_BRICK,
			LOTRItems.ANGMAR_BRICK.get()::asItem, LOTRRecipes.ANGMAR_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_ARNOR_BRICK,
			LOTRItems.ARNOR_BRICK.get()::asItem, LOTRRecipes.RANGER_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_BLUE_BRICK,
			LOTRItems.BLUE_BRICK.get()::asItem, LOTRRecipes.BLUE_MOUNTAINS_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_DALE_BRICK,
			LOTRItems.DALE_BRICK.get()::asItem, LOTRRecipes.DALE_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_DALE_PAVING,
				LOTRItems.DALE_PAVING.get()::asItem, LOTRRecipes.DALE_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_DOL_AMROTH_BRICK,
				LOTRItems.DOL_AMROTH_BRICK.get()::asItem, LOTRRecipes.DOL_AMROTH_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_DORWINION_BRICK,
				LOTRItems.DORWINION_BRICK.get()::asItem, LOTRRecipes.DORWINION_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_DWARVEN_BRICK,
				LOTRItems.DWARVEN_BRICK.get()::asItem, LOTRRecipes.DWARVEN_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_GALADHRIM_BRICK,
				LOTRItems.GALADHRIM_BRICK.get()::asItem, LOTRRecipes.GALADHRIM_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_GONDOR_BRICK,
				LOTRItems.GONDOR_BRICK.get()::asItem, LOTRRecipes.GONDOR_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_GONDOR_COBBLEBRICK,
				LOTRItems.GONDOR_COBBLEBRICK.get()::asItem, LOTRRecipes.GONDOR_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_HIGH_ELVEN_BRICK,
				LOTRItems.HIGH_ELVEN_BRICK.get()::asItem, LOTRRecipes.ANY_HIGH_ELVEN, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_MORDOR_BRICK,
				LOTRItems.MORDOR_BRICK.get()::asItem, LOTRRecipes.MORDOR_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_NUMENOREAN_BRICK,
				LOTRItems.NUMENOREAN_BRICK.get()::asItem, LOTRRecipes.ANY_NUMENOREAN, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_RED_BRICK,
				LOTRItems.RED_BRICK.get()::asItem, null, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_RED_DORWINION_BRICK,
				LOTRItems.RED_DORWINION_BRICK.get()::asItem, LOTRRecipes.DORWINION_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_ROHAN_BRICK,
				LOTRItems.ROHAN_BRICK.get()::asItem, LOTRRecipes.ROHAN_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_UMBAR_BRICK,
				LOTRItems.UMBAR_BRICK.get()::asItem, LOTRRecipes.UMBAR_CRAFTING, "", false);
		makeMossyBlockRecipe(consumer, LOTRItems.MOSSY_WOOD_ELVEN_BRICK,
				LOTRItems.WOOD_ELVEN_BRICK.get()::asItem, LOTRRecipes.WOOD_ELVEN_CRAFTING, "", false);
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
		makeMossyBlockRecipe(consumer, ()->Items.MOSSY_COBBLESTONE,
				Blocks.COBBLESTONE::asItem, null, "", false);
		makeMossyBlockRecipe(consumer, ()->Items.MOSSY_STONE_BRICKS,
				Blocks.STONE_BRICKS::asItem, null, "", false);
	}

	private void buildVanillaRecipeOverrides(Consumer<IFinishedRecipe> consumer) {
		ExtendedShapedRecipeBuilder.shaped(Items.CAKE)
		.pattern("MMM")
		.pattern("SES")
		.pattern("WWW")
		.define('M', Ingredient.of(Items.MILK_BUCKET))
		.define('S', Ingredient.of(Items.SUGAR))
		.define('E', Ingredient.of(Tags.Items.EGGS))
		.define('W', Ingredient.of(Tags.Items.CROPS_WHEAT))
		.unlockedBy("has_"+Items.CAKE.getRegistryName().getPath(), has(Items.CAKE))
		.save(consumer, mcLoc(Items.CAKE.getRegistryName().getPath()+"_more_eggs"));
		ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_PIE)
		.requires(Items.SUGAR)
		.requires(Items.PUMPKIN)
		.requires(Tags.Items.EGGS)
		.unlockedBy("has_"+Items.PUMPKIN_PIE.getRegistryName().getPath(), has(Items.PUMPKIN_PIE))
		.save(consumer, mcLoc(Items.PUMPKIN_PIE.getRegistryName().getPath()+"_more_eggs"));
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
		ExtendedShapedRecipeBuilder.shaped(Items.ANVIL)
		.pattern("III")
		.pattern(" B ")
		.pattern("III")
		.define('I', Ingredient.of(ExtendedTags.Items.ANVIL_IRON_INGOT))
		.define('B', Ingredient.of(ExtendedTags.Items.ANVIL_IRON_BLOCK))
		.unlockedBy("has_iron_blocks", has(ExtendedTags.Items.ANVIL_IRON_BLOCK))
		.save(consumer, mcLoc(Items.ANVIL.getRegistryName().getPath()+"_cheepened"));
		ExtendedShapedRecipeBuilder.shaped(Items.LODESTONE)
		.pattern("SSS")
		.pattern("S#S")
		.pattern("SSS")
		.define('S', Ingredient.of(Items.CHISELED_STONE_BRICKS))
		.define('#', Ingredient.of(Tags.Items.INGOTS_IRON))
		.unlockedBy("iron_ingot", has(Tags.Items.INGOTS_IRON))
		.save(consumer, mcLoc(Items.LODESTONE.getRegistryName().getPath()+"_backported"));
		ExtendedShapedRecipeBuilder.shaped(Items.JUKEBOX)
		.pattern("PPP")
		.pattern("PDP")
		.pattern("PPP")
		.define('D', Ingredient.of(ExtendedItems.DIAMOND.get()))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_"+ExtendedItems.DIAMOND.get().getRegistryName().getPath(), has(ExtendedItems.DIAMOND.get()))
		.save(consumer, mcLoc(Items.JUKEBOX.getRegistryName().getPath()+"using_e_diamonds"));
	}
	private void buildRenewedRecipeOverrides(Consumer<IFinishedRecipe> consumer) {
		ExtendedFactionShapedRecipeBuilder.shaped(LOTRItems.ORC_PLATING.get(), 8, LOTRRecipes.ANY_ORC)
		.pattern(" # ")
		.pattern("# #")
		.pattern(" # ")
		.define('#', Ingredient.of(LOTRItems.ORC_STEEL_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.ORC_STEEL_INGOT.get().getRegistryName().getPath(), has(LOTRItems.ORC_STEEL_INGOT.get()))
		.save(consumer, LOTRItems.ORC_PLATING);
		makeWallRecipe(consumer, ExtendedItems.ORC_PLATING_WALL, LOTRItems.ORC_PLATING, LOTRRecipes.ANY_ORC);
		makeWallRecipe(consumer, ExtendedItems.RUSTED_ORC_PLATING_WALL, LOTRItems.RUSTED_ORC_PLATING, LOTRRecipes.ANY_ORC);
	}

	private void buildSmithingRecipes(Consumer<IFinishedRecipe> consumer) {
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_GEM.get()).save(consumer, LotrExtendedMod.MODID+":gem_coloring");
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_ONE.get()).save(consumer, LotrExtendedMod.MODID+":coin_minting_one");
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_TEN.get()).save(consumer, LotrExtendedMod.MODID+":coin_minting_ten");
		CustomRecipeBuilder.special(ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_HUNDRED.get()).save(consumer, LotrExtendedMod.MODID+":coin_minting_hundred");
	}

	private void buildSmeltingRecipes(Consumer<IFinishedRecipe> consumer) {
		makeOreFurnaceRecipe(consumer, ExtendedItems.AMBER_ORE.get(), ExtendedItems.AMBER.get(), 0.9f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.AMETHYST_ORE.get(), ExtendedItems.AMETHYST.get(), 0.5f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.DIAMOND_ORE.get(), ExtendedItems.DIAMOND.get(), 1.0f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.EMERALD_ORE.get(), ExtendedItems.EMERALD.get(), 0.9f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.OPAL_ORE.get(), ExtendedItems.OPAL.get(), 0.5f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.RUBY_ORE.get(), ExtendedItems.RUBY.get(), 0.5f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.SAPPHIRE_ORE.get(), ExtendedItems.SAPPHIRE.get(), 0.5f);
		makeOreFurnaceRecipe(consumer, ExtendedItems.TOPAZ_ORE.get(), ExtendedItems.TOPAZ.get(), 0.5f);
		makeBlastingRecipe(consumer, ExtendedItems.METEORITE_ORE.get(), ExtendedItems.SKY_IRON.get(), 1.0f, 2400);
		makeSmeltingRecipe(consumer, ExtendedItems.MORGOND.get(), ExtendedItems.SMOOTH_MORGOND.get(), 1.0f, 2400);
	}

	private void buildCookingRecipes(Consumer<IFinishedRecipe> consumer) {
		GENERATEDRECIPES.add(ExtendedItems.BUCKET_OF_CHEESE_CURDS.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(Items.MILK_BUCKET),
			ExtendedItems.BUCKET_OF_CHEESE_CURDS.get(), 0.15F, 200)
		.unlockedBy("has_"+Items.MILK_BUCKET.getRegistryName().getPath(), has(Items.MILK_BUCKET))
		.unlockedBy("has_"+ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_CURDS.get()))
		.save(consumer, ExtendedItems.BUCKET_OF_CHEESE_CURDS.get().getRegistryName());
		// Conker
		GENERATEDRECIPES.add(ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ExtendedItems.CONKER.get()),
			ExtendedItems.ROASTED_CHESTNUT.get(), 0.15F, 200)
		.unlockedBy("has_"+ExtendedItems.CONKER.get().getRegistryName().getPath(), has(ExtendedItems.CONKER.get()))
		.save(consumer, ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName());

		// Duck
		GENERATEDRECIPES.add(ExtendedItems.COOKED_DUCK.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ExtendedItems.RAW_DUCK.get()),
			ExtendedItems.COOKED_DUCK.get(), 0.35F, 200)
		.unlockedBy("has_"+ExtendedItems.RAW_DUCK.get().getRegistryName().getPath(), has(ExtendedItems.RAW_DUCK.get()))
		.save(consumer, ExtendedItems.COOKED_DUCK.get().getRegistryName());

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
		.save(consumer, ExtendedItems.BANANA_BEER.get().getRegistryName());
		GENERATEDRECIPES.add(ExtendedItems.PLUM_KVASS.get().getRegistryName().getPath());
		ExtendedVesselBrewingRecipeBuilder.brewing(ExtendedItems.PLUM_KVASS.get(), LOTRRecipes.DRINK_BREWING_SERIALIZER.get())
		.requires(Ingredient.of(ExtendedItems.PLUM.get()))
		.requires(Ingredient.of(ExtendedItems.PLUM.get()))
		.requires(Ingredient.of(ExtendedItems.PLUM.get()))
		.requires(Ingredient.of(Tags.Items.CROPS_WHEAT))
		.requires(Ingredient.of(Tags.Items.CROPS_WHEAT))
		.requires(Ingredient.of(Tags.Items.CROPS_WHEAT))
		.save(consumer, ExtendedItems.PLUM_KVASS.get().getRegistryName());
	}

	private void buildStoneGrinderRecipes(Consumer<IFinishedRecipe> consumer) {
		// Stone/Rock to cracked/gravel
		makeStoneGrinderRecipe(consumer, ()->Items.STONE, ()->Items.COBBLESTONE);
		makeStoneGrinderRecipe(consumer, ()->Items.COBBLESTONE, ()->Items.GRAVEL, 0.25f);
		makeStoneGrinderRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_GRAVEL, 0.25f);
		// Gravel to items
		makeStoneGrinderRecipe(consumer, LOTRItems.MORDOR_GRAVEL, ()->Items.GRAVEL, 0.75f);
		makeStoneGrinderRecipe(consumer, ()->Items.GRAVEL, ()->Items.FLINT, 0.75f);
		//makeMillingRecipe(consumer, ExtendedItems.OBSIDIAN_GRAVEL, ExtendedItems.OBSIDIAN_SHARD, 0.75f);
		makeStoneGrinderRecipe(consumer, LOTRItems.SALT_ORE, LOTRItems.SALT);
		// Sandstones to sand
		makeStoneGrinderRecipe(consumer, ()->Items.SANDSTONE, ()->Items.SAND, 2);
		makeStoneGrinderRecipe(consumer, ()->Items.RED_SANDSTONE, ()->Items.RED_SAND, 2);
		makeStoneGrinderRecipe(consumer, LOTRItems.WHITE_SANDSTONE, LOTRItems.WHITE_SAND, 2);
		// Extended Gemstone grinding recipes
		makeStoneGrinderRecipe(consumer, ExtendedItems.AMBER_ORE, ExtendedItems.AMBER, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedItems.AMETHYST_ORE, ExtendedItems.AMETHYST, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedItems.DIAMOND_ORE, ExtendedItems.DIAMOND, 1, 2);
		makeStoneGrinderRecipe(consumer, ExtendedItems.EMERALD_ORE, ExtendedItems.EMERALD, 1, 2);
		makeStoneGrinderRecipe(consumer, ExtendedItems.OPAL_ORE, ExtendedItems.OPAL, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RUBY_ORE, ExtendedItems.RUBY, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedItems.SAPPHIRE_ORE, ExtendedItems.SAPPHIRE, 1, 3);
		makeStoneGrinderRecipe(consumer, ExtendedItems.TOPAZ_ORE, ExtendedItems.TOPAZ, 1, 3);
		// Renewed Gemstone grinding recipes
		makeStoneGrinderRecipe(consumer, LOTRItems.DURNOR_ORE, LOTRItems.DURNOR, 2, 5);
		makeStoneGrinderRecipe(consumer, LOTRItems.EDHELVIR_ORE, LOTRItems.EDHELVIR, 2, 4);
		makeStoneGrinderRecipe(consumer, LOTRItems.GLOWSTONE_ORE, ()->Items.GLOWSTONE_DUST, 1, 3);
		makeStoneGrinderRecipe(consumer, LOTRItems.GULDURIL_ORE_MORDOR, LOTRItems.GULDURIL, 1, 3);
		makeStoneGrinderRecipe(consumer, LOTRItems.GULDURIL_ORE_STONE, LOTRItems.GULDURIL, 1, 3);
		// Vanilla Gemstone grinding recipes
		makeStoneGrinderRecipe(consumer, ()->Items.DIAMOND_ORE, ()->Items.DIAMOND, 1, 2);
		makeStoneGrinderRecipe(consumer, ()->Items.EMERALD_ORE, ()->Items.EMERALD, 1, 2);
		makeStoneGrinderRecipe(consumer, ()->Items.LAPIS_ORE, ()->Items.LAPIS_LAZULI, 4, 9);
		makeStoneGrinderRecipe(consumer, ()->Items.REDSTONE_ORE, ()->Items.REDSTONE, 3, 5);
		// Vanilla blocks to cracked
		makeStoneGrinderRecipe(consumer, ()->Items.BRICKS, ()->Items.BRICK, 3);
		makeStoneGrinderRecipe(consumer, ()->Items.STONE_BRICKS, ()->Items.CRACKED_STONE_BRICKS);
		makeStoneGrinderRecipe(consumer, ()->Items.STONE_BRICK_SLAB, LOTRItems.CRACKED_STONE_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ()->Items.STONE_BRICK_STAIRS, LOTRItems.CRACKED_STONE_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ()->Items.STONE_BRICK_WALL, LOTRItems.CRACKED_STONE_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ()->Items.NETHER_BRICKS, ()->Items.CRACKED_NETHER_BRICKS);
		makeStoneGrinderRecipe(consumer, ()->Items.POLISHED_BLACKSTONE_BRICKS, ()->Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		// Renewed cracked recipes
		makeStoneGrinderRecipe(consumer, LOTRItems.ANGMAR_BRICK, LOTRItems.CRACKED_ANGMAR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.ANGMAR_BRICK_SLAB, LOTRItems.CRACKED_ANGMAR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.ANGMAR_BRICK_STAIRS, LOTRItems.CRACKED_ANGMAR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.ANGMAR_BRICK_WALL, LOTRItems.CRACKED_ANGMAR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.ARNOR_BRICK, LOTRItems.CRACKED_ARNOR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.ARNOR_BRICK_SLAB, LOTRItems.CRACKED_ARNOR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.ARNOR_BRICK_STAIRS, LOTRItems.CRACKED_ARNOR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.ARNOR_BRICK_WALL, LOTRItems.CRACKED_ARNOR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.BLUE_BRICK, LOTRItems.CRACKED_BLUE_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.BLUE_BRICK_SLAB, LOTRItems.CRACKED_BLUE_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.BLUE_BRICK_STAIRS, LOTRItems.CRACKED_BLUE_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.BLUE_BRICK_WALL, LOTRItems.CRACKED_BLUE_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.DALE_BRICK, LOTRItems.CRACKED_DALE_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.DALE_BRICK_SLAB, LOTRItems.CRACKED_DALE_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.DALE_BRICK_STAIRS, LOTRItems.CRACKED_DALE_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.DALE_BRICK_WALL, LOTRItems.CRACKED_DALE_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK, LOTRItems.CRACKED_DOL_AMROTH_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK_SLAB, LOTRItems.CRACKED_DOL_AMROTH_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK_STAIRS, LOTRItems.CRACKED_DOL_AMROTH_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK_WALL, LOTRItems.CRACKED_DOL_AMROTH_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.DORWINION_BRICK, LOTRItems.CRACKED_DORWINION_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.DORWINION_BRICK_SLAB, LOTRItems.CRACKED_DORWINION_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.DORWINION_BRICK_STAIRS, LOTRItems.CRACKED_DORWINION_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.DORWINION_BRICK_WALL, LOTRItems.CRACKED_DORWINION_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.DWARVEN_BRICK, LOTRItems.CRACKED_DWARVEN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.DWARVEN_BRICK_SLAB, LOTRItems.CRACKED_DWARVEN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.DWARVEN_BRICK_STAIRS, LOTRItems.CRACKED_DWARVEN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.DWARVEN_BRICK_WALL, LOTRItems.CRACKED_DWARVEN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.GALADHRIM_BRICK, LOTRItems.CRACKED_GALADHRIM_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.GALADHRIM_BRICK_SLAB, LOTRItems.CRACKED_GALADHRIM_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.GALADHRIM_BRICK_STAIRS, LOTRItems.CRACKED_GALADHRIM_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.GALADHRIM_BRICK_WALL, LOTRItems.CRACKED_GALADHRIM_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_BRICK, LOTRItems.CRACKED_GONDOR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_BRICK_SLAB, LOTRItems.CRACKED_GONDOR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_BRICK_STAIRS, LOTRItems.CRACKED_GONDOR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_BRICK_WALL, LOTRItems.CRACKED_GONDOR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK, LOTRItems.CRACKED_GONDOR_COBBLEBRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK_SLAB, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK_STAIRS, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK_WALL, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK, LOTRItems.CRACKED_HIGH_ELVEN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK_SLAB, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK_STAIRS, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK_WALL, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.MORDOR_BRICK, LOTRItems.CRACKED_MORDOR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.MORDOR_BRICK_SLAB, LOTRItems.CRACKED_MORDOR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.MORDOR_BRICK_STAIRS, LOTRItems.CRACKED_MORDOR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.MORDOR_BRICK_WALL, LOTRItems.CRACKED_MORDOR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, LOTRItems.CRACKED_NUMENOREAN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.NUMENOREAN_BRICK_SLAB, LOTRItems.CRACKED_NUMENOREAN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.NUMENOREAN_BRICK_STAIRS, LOTRItems.CRACKED_NUMENOREAN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.NUMENOREAN_BRICK_WALL, LOTRItems.CRACKED_NUMENOREAN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_BRICK, LOTRItems.CRACKED_RED_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_BRICK_SLAB, LOTRItems.CRACKED_RED_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_BRICK_STAIRS, LOTRItems.CRACKED_RED_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_BRICK_WALL, LOTRItems.CRACKED_RED_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_DORWINION_BRICK, LOTRItems.CRACKED_RED_DORWINION_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_DORWINION_BRICK_SLAB, LOTRItems.CRACKED_RED_DORWINION_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_DORWINION_BRICK_STAIRS, LOTRItems.CRACKED_RED_DORWINION_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.RED_DORWINION_BRICK_WALL, LOTRItems.CRACKED_RED_DORWINION_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.ROHAN_BRICK, LOTRItems.CRACKED_ROHAN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.ROHAN_BRICK_SLAB, LOTRItems.CRACKED_ROHAN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.ROHAN_BRICK_STAIRS, LOTRItems.CRACKED_ROHAN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.ROHAN_BRICK_WALL, LOTRItems.CRACKED_ROHAN_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.UMBAR_BRICK, LOTRItems.CRACKED_UMBAR_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.UMBAR_BRICK_SLAB, LOTRItems.CRACKED_UMBAR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.UMBAR_BRICK_STAIRS, LOTRItems.CRACKED_UMBAR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.UMBAR_BRICK_WALL, LOTRItems.CRACKED_UMBAR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK, LOTRItems.CRACKED_WOOD_ELVEN_BRICK);
		makeStoneGrinderRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK_SLAB, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK_STAIRS, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK_WALL, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_WALL);
		// Extended cracked recipes from renewed blocks
		makeStoneGrinderRecipe(consumer, LOTRItems.ARNOR_PILLAR, ExtendedItems.CRACKED_ARNOR_PILLAR);
		makeStoneGrinderRecipe(consumer, LOTRItems.ARNOR_PILLAR_SLAB, ExtendedItems.CRACKED_ARNOR_PILLAR_SLAB);
		makeStoneGrinderRecipe(consumer, LOTRItems.STONE_PILLAR, ExtendedItems.CRACKED_STONE_PILLAR);
		makeStoneGrinderRecipe(consumer, LOTRItems.STONE_PILLAR_SLAB, ExtendedItems.CRACKED_STONE_PILLAR_SLAB);
		// Extended cracked block recipes
		makeStoneGrinderRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK, ExtendedItems.CRACKED_DOL_GULDUR_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK_SLAB, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK_STAIRS, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK_WALL, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedItems.MUD_BRICK, ExtendedItems.CRACKED_MUD_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedItems.MUD_BRICK_SLAB, ExtendedItems.CRACKED_MUD_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedItems.MUD_BRICK_STAIRS, ExtendedItems.CRACKED_MUD_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedItems.MUD_BRICK_WALL, ExtendedItems.CRACKED_MUD_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK, ExtendedItems.CRACKED_RED_RHUNIC_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK_SLAB, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK_STAIRS, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK_WALL, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RHUNIC_BRICK, ExtendedItems.CRACKED_RHUNIC_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RHUNIC_BRICK_SLAB, ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RHUNIC_BRICK_STAIRS, ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedItems.RHUNIC_BRICK_WALL, ExtendedItems.CRACKED_RHUNIC_BRICK_WALL);
		makeStoneGrinderRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK, ExtendedItems.CRACKED_TAURETHRIM_BRICK);
		makeStoneGrinderRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK_SLAB, ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB);
		makeStoneGrinderRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK_STAIRS, ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS);
		makeStoneGrinderRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK_WALL, ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL);
	}

	private void buildStonecutterRecipes(Consumer<IFinishedRecipe> consumer) {
		//Add missing recipes for renewed blocks
		makeStoneCutterRecipe(consumer, () ->Items.COBBLESTONE, LOTRItems.DRYSTONE);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.CARVED_ROHAN_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_PILLAR);
		//		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_PILLAR, LOTRItems.ROHAN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_ROCK, LOTRItems.ROHAN_ROCK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_BRICK, LOTRItems.ROHAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_BRICK, LOTRItems.ROHAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_BRICK, LOTRItems.ROHAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ROHAN_BRICK, LOTRItems.CARVED_ROHAN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_ROHAN_ROCK, LOTRItems.SMOOTH_ROHAN_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ROHAN_BRICK, LOTRItems.MOSSY_ROHAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ROHAN_BRICK, LOTRItems.MOSSY_ROHAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ROHAN_BRICK, LOTRItems.MOSSY_ROHAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ROHAN_BRICK, LOTRItems.CRACKED_ROHAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ROHAN_BRICK, LOTRItems.CRACKED_ROHAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ROHAN_BRICK, LOTRItems.CRACKED_ROHAN_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.CARVED_GONDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_PILLAR);
		//		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_PILLAR, LOTRItems.GONDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_ROCK, LOTRItems.GONDOR_ROCK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_BRICK, LOTRItems.GONDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_BRICK, LOTRItems.GONDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_BRICK, LOTRItems.GONDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_BRICK, LOTRItems.CARVED_GONDOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_GONDOR_ROCK, LOTRItems.SMOOTH_GONDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GONDOR_BRICK, LOTRItems.MOSSY_GONDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GONDOR_BRICK, LOTRItems.MOSSY_GONDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GONDOR_BRICK, LOTRItems.MOSSY_GONDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GONDOR_BRICK, LOTRItems.CRACKED_GONDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GONDOR_BRICK, LOTRItems.CRACKED_GONDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GONDOR_BRICK, LOTRItems.CRACKED_GONDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK, LOTRItems.GONDOR_COBBLEBRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK, LOTRItems.GONDOR_COBBLEBRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.GONDOR_COBBLEBRICK, LOTRItems.GONDOR_COBBLEBRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GONDOR_COBBLEBRICK, LOTRItems.MOSSY_GONDOR_COBBLEBRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GONDOR_COBBLEBRICK, LOTRItems.MOSSY_GONDOR_COBBLEBRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GONDOR_COBBLEBRICK, LOTRItems.MOSSY_GONDOR_COBBLEBRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GONDOR_COBBLEBRICK, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GONDOR_COBBLEBRICK, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GONDOR_COBBLEBRICK, LOTRItems.CRACKED_GONDOR_COBBLEBRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_PILLAR, LOTRItems.NUMENOREAN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, LOTRItems.NUMENOREAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, LOTRItems.NUMENOREAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, LOTRItems.NUMENOREAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, LOTRItems.CARVED_NUMENOREAN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_NUMENOREAN_BRICK, LOTRItems.MOSSY_NUMENOREAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_NUMENOREAN_BRICK, LOTRItems.MOSSY_NUMENOREAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_NUMENOREAN_BRICK, LOTRItems.MOSSY_NUMENOREAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_NUMENOREAN_BRICK, LOTRItems.CRACKED_NUMENOREAN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_NUMENOREAN_BRICK, LOTRItems.CRACKED_NUMENOREAN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_NUMENOREAN_BRICK, LOTRItems.CRACKED_NUMENOREAN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK, LOTRItems.DOL_AMROTH_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK, LOTRItems.DOL_AMROTH_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.DOL_AMROTH_BRICK, LOTRItems.DOL_AMROTH_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DOL_AMROTH_BRICK, LOTRItems.MOSSY_DOL_AMROTH_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DOL_AMROTH_BRICK, LOTRItems.MOSSY_DOL_AMROTH_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DOL_AMROTH_BRICK, LOTRItems.MOSSY_DOL_AMROTH_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DOL_AMROTH_BRICK, LOTRItems.CRACKED_DOL_AMROTH_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DOL_AMROTH_BRICK, LOTRItems.CRACKED_DOL_AMROTH_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DOL_AMROTH_BRICK, LOTRItems.CRACKED_DOL_AMROTH_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.CARVED_MORDOR_BRICK);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_PILLAR);
		//		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_PILLAR, LOTRItems.MORDOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_ROCK, LOTRItems.MORDOR_ROCK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BRICK, LOTRItems.MORDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BRICK, LOTRItems.MORDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BRICK, LOTRItems.MORDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BRICK, LOTRItems.CARVED_MORDOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_MORDOR_ROCK, LOTRItems.SMOOTH_MORDOR_ROCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_MORDOR_BRICK, LOTRItems.MOSSY_MORDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_MORDOR_BRICK, LOTRItems.MOSSY_MORDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_MORDOR_BRICK, LOTRItems.MOSSY_MORDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_MORDOR_BRICK, LOTRItems.CRACKED_MORDOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_MORDOR_BRICK, LOTRItems.CRACKED_MORDOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_MORDOR_BRICK, LOTRItems.CRACKED_MORDOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BASALT_BRICK, LOTRItems.MORDOR_BASALT_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BASALT_BRICK, LOTRItems.MORDOR_BASALT_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BASALT_BRICK, LOTRItems.MORDOR_BASALT_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MORDOR_BASALT_BRICK, LOTRItems.CARVED_MORDOR_BASALT_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.HIGH_ELVEN_PILLAR, LOTRItems.HIGH_ELVEN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK, LOTRItems.HIGH_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK, LOTRItems.HIGH_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK, LOTRItems.HIGH_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.HIGH_ELVEN_BRICK, LOTRItems.CARVED_HIGH_ELVEN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_HIGH_ELVEN_BRICK, LOTRItems.MOSSY_HIGH_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_HIGH_ELVEN_BRICK, LOTRItems.MOSSY_HIGH_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_HIGH_ELVEN_BRICK, LOTRItems.MOSSY_HIGH_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_HIGH_ELVEN_BRICK, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_HIGH_ELVEN_BRICK, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_HIGH_ELVEN_BRICK, LOTRItems.CRACKED_HIGH_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.GALADHRIM_PILLAR, LOTRItems.GALADHRIM_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GALADHRIM_BRICK, LOTRItems.GALADHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.GALADHRIM_BRICK, LOTRItems.GALADHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.GALADHRIM_BRICK, LOTRItems.GALADHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.GALADHRIM_BRICK, LOTRItems.CARVED_GALADHRIM_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GALADHRIM_BRICK, LOTRItems.MOSSY_GALADHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GALADHRIM_BRICK, LOTRItems.MOSSY_GALADHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_GALADHRIM_BRICK, LOTRItems.MOSSY_GALADHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GALADHRIM_BRICK, LOTRItems.CRACKED_GALADHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GALADHRIM_BRICK, LOTRItems.CRACKED_GALADHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_GALADHRIM_BRICK, LOTRItems.CRACKED_GALADHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.WOOD_ELVEN_PILLAR, LOTRItems.WOOD_ELVEN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK, LOTRItems.WOOD_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK, LOTRItems.WOOD_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK, LOTRItems.WOOD_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.WOOD_ELVEN_BRICK, LOTRItems.CARVED_WOOD_ELVEN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_WOOD_ELVEN_BRICK, LOTRItems.MOSSY_WOOD_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_WOOD_ELVEN_BRICK, LOTRItems.MOSSY_WOOD_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_WOOD_ELVEN_BRICK, LOTRItems.MOSSY_WOOD_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_WOOD_ELVEN_BRICK, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_WOOD_ELVEN_BRICK, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_WOOD_ELVEN_BRICK, LOTRItems.CRACKED_WOOD_ELVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.HARAD_PILLAR, LOTRItems.HARAD_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.HARAD_BRICK, LOTRItems.HARAD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.HARAD_BRICK, LOTRItems.HARAD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.HARAD_BRICK, LOTRItems.HARAD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_HARAD_PILLAR, LOTRItems.RED_HARAD_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_HARAD_BRICK, LOTRItems.RED_HARAD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_HARAD_BRICK, LOTRItems.RED_HARAD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_HARAD_BRICK, LOTRItems.RED_HARAD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.UMBAR_PILLAR, LOTRItems.UMBAR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.UMBAR_BRICK, LOTRItems.UMBAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.UMBAR_BRICK, LOTRItems.UMBAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.UMBAR_BRICK, LOTRItems.UMBAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_UMBAR_BRICK, LOTRItems.MOSSY_UMBAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_UMBAR_BRICK, LOTRItems.MOSSY_UMBAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_UMBAR_BRICK, LOTRItems.MOSSY_UMBAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_UMBAR_BRICK, LOTRItems.CRACKED_UMBAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_UMBAR_BRICK, LOTRItems.CRACKED_UMBAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_UMBAR_BRICK, LOTRItems.CRACKED_UMBAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ARNOR_PILLAR, LOTRItems.ARNOR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ARNOR_BRICK, LOTRItems.ARNOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ARNOR_BRICK, LOTRItems.ARNOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.ARNOR_BRICK, LOTRItems.ARNOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ARNOR_BRICK, LOTRItems.CARVED_ARNOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ARNOR_BRICK, LOTRItems.MOSSY_ARNOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ARNOR_BRICK, LOTRItems.MOSSY_ARNOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ARNOR_BRICK, LOTRItems.MOSSY_ARNOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ARNOR_BRICK, LOTRItems.CRACKED_ARNOR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ARNOR_BRICK, LOTRItems.CRACKED_ARNOR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ARNOR_BRICK, LOTRItems.CRACKED_ARNOR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DORWINION_PILLAR, LOTRItems.DORWINION_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DORWINION_BRICK, LOTRItems.DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DORWINION_BRICK, LOTRItems.DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.DORWINION_BRICK, LOTRItems.DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DORWINION_BRICK, LOTRItems.MOSSY_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DORWINION_BRICK, LOTRItems.MOSSY_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DORWINION_BRICK, LOTRItems.MOSSY_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DORWINION_BRICK, LOTRItems.CRACKED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DORWINION_BRICK, LOTRItems.CRACKED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DORWINION_BRICK, LOTRItems.CRACKED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_DORWINION_BRICK, LOTRItems.RED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_DORWINION_BRICK, LOTRItems.RED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.RED_DORWINION_BRICK, LOTRItems.RED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_RED_DORWINION_BRICK, LOTRItems.MOSSY_RED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_RED_DORWINION_BRICK, LOTRItems.MOSSY_RED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_RED_DORWINION_BRICK, LOTRItems.MOSSY_RED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_RED_DORWINION_BRICK, LOTRItems.CRACKED_RED_DORWINION_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_RED_DORWINION_BRICK, LOTRItems.CRACKED_RED_DORWINION_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_RED_DORWINION_BRICK, LOTRItems.CRACKED_RED_DORWINION_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.URUK_PILLAR, LOTRItems.URUK_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.URUK_BRICK, LOTRItems.URUK_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.URUK_BRICK, LOTRItems.URUK_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.URUK_BRICK, LOTRItems.URUK_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DWARVEN_PILLAR, LOTRItems.DWARVEN_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DWARVEN_BRICK, LOTRItems.DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DWARVEN_BRICK, LOTRItems.DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.DWARVEN_BRICK, LOTRItems.DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DWARVEN_BRICK, LOTRItems.CARVED_DWARVEN_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DWARVEN_BRICK, LOTRItems.MOSSY_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DWARVEN_BRICK, LOTRItems.MOSSY_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DWARVEN_BRICK, LOTRItems.MOSSY_DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DWARVEN_BRICK, LOTRItems.CRACKED_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DWARVEN_BRICK, LOTRItems.CRACKED_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DWARVEN_BRICK, LOTRItems.CRACKED_DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOWY_DWARVEN_BRICK, LOTRItems.SNOWY_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOWY_DWARVEN_BRICK, LOTRItems.SNOWY_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOWY_DWARVEN_BRICK, LOTRItems.SNOWY_DWARVEN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_PILLAR, LOTRItems.DALE_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_BRICK, LOTRItems.DALE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_BRICK, LOTRItems.DALE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_BRICK, LOTRItems.DALE_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_BRICK, LOTRItems.CARVED_DALE_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DALE_BRICK, LOTRItems.MOSSY_DALE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DALE_BRICK, LOTRItems.MOSSY_DALE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DALE_BRICK, LOTRItems.MOSSY_DALE_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DALE_BRICK, LOTRItems.CRACKED_DALE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DALE_BRICK, LOTRItems.CRACKED_DALE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_DALE_BRICK, LOTRItems.CRACKED_DALE_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_PAVING, LOTRItems.DALE_PAVING_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DALE_PAVING, LOTRItems.DALE_PAVING_STAIRS);
		//makeStoneCutterRecipe(consumer, LOTRItems.DALE_PAVING, LOTRItems.DALE_PAVING_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DALE_PAVING, LOTRItems.MOSSY_DALE_PAVING_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DALE_PAVING, LOTRItems.MOSSY_DALE_PAVING_STAIRS);
		//makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_DALE_PAVING, LOTRItems.MOSSY_DALE_PAVING_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ANGMAR_PILLAR, LOTRItems.ANGMAR_PILLAR_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ANGMAR_BRICK, LOTRItems.ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ANGMAR_BRICK, LOTRItems.ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.ANGMAR_BRICK, LOTRItems.ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ANGMAR_BRICK, LOTRItems.CARVED_ANGMAR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ANGMAR_BRICK, LOTRItems.MOSSY_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ANGMAR_BRICK, LOTRItems.MOSSY_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.MOSSY_ANGMAR_BRICK, LOTRItems.MOSSY_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ANGMAR_BRICK, LOTRItems.CRACKED_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ANGMAR_BRICK, LOTRItems.CRACKED_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.CRACKED_ANGMAR_BRICK, LOTRItems.CRACKED_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOWY_ANGMAR_BRICK, LOTRItems.SNOWY_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOWY_ANGMAR_BRICK, LOTRItems.SNOWY_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOWY_ANGMAR_BRICK, LOTRItems.SNOWY_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOW_BRICK, LOTRItems.SNOW_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOW_BRICK, LOTRItems.SNOW_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SNOW_BRICK, LOTRItems.SNOW_BRICK_WALL);
		makeStoneCutterRecipe(consumer, LOTRItems.ICE_BRICK, LOTRItems.ICE_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.ICE_BRICK, LOTRItems.ICE_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.ICE_BRICK, LOTRItems.ICE_BRICK_WALL);

		//Add extended blocks recipes for renewed blocks
		makeStoneCutterRecipe(consumer, ExtendedItems.COBBLED_DRYSTONE, LOTRItems.DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedItems.CUT_DRYSTONE, LOTRItems.DRYSTONE);

		//Add vanilla dependent recipes for extended blocks
		makeStoneCutterRecipe(consumer, () ->Items.SMOOTH_STONE, ExtendedItems.SMOOTH_STONE_STAIRS);
		makeStoneCutterRecipe(consumer, () ->Items.BONE_BLOCK, ExtendedItems.BONE_BLOCK_SLAB, 2);
		makeStoneCutterRecipe(consumer, () ->Items.BONE_BLOCK, ExtendedItems.BONE_BLOCK_STAIRS);
		makeStoneCutterRecipe(consumer, () ->Items.BONE_BLOCK, ExtendedItems.BONE_BLOCK_WALL);

		//Add renewed dependent recipes for extended blocks
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, ExtendedItems.CARVED_BLACK_GONDOR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.NUMENOREAN_BRICK, ExtendedItems.CARVED_BLACK_UMBAR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.UMBAR_BRICK, ExtendedItems.CARVED_UMBAR_BRICK);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_BLUE_ROCK, ExtendedItems.SMOOTH_BLUE_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_RED_ROCK, ExtendedItems.SMOOTH_RED_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_MORDOR_ROCK, ExtendedItems.SMOOTH_MORDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_GONDOR_ROCK, ExtendedItems.SMOOTH_GONDOR_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.SMOOTH_ROHAN_ROCK, ExtendedItems.SMOOTH_ROHAN_ROCK_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.DRYSTONE, ExtendedItems.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, LOTRItems.DRYSTONE, ExtendedItems.COBBLED_DRYSTONE);
		makeStoneCutterRecipe(consumer, LOTRItems.DRYSTONE, ExtendedItems.DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, LOTRItems.DRYSTONE, ExtendedItems.DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, LOTRItems.DRYSTONE, ExtendedItems.DRYSTONE_WALL);

		//Add extended blocks recipes
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD, ExtendedItems.MUD_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD, ExtendedItems.MUD_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD, ExtendedItems.MUD_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD, ExtendedItems.MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD, ExtendedItems.MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD, ExtendedItems.MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD_BRICK, ExtendedItems.MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD_BRICK, ExtendedItems.MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD_BRICK, ExtendedItems.MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_MUD_BRICK, ExtendedItems.CRACKED_MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_MUD_BRICK, ExtendedItems.CRACKED_MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_MUD_BRICK, ExtendedItems.CRACKED_MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_MUD_BRICK, ExtendedItems.MOSSY_MUD_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_MUD_BRICK, ExtendedItems.MOSSY_MUD_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_MUD_BRICK, ExtendedItems.MOSSY_MUD_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD_PATH, ExtendedItems.MUD_PATH_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MUD_PATH, ExtendedItems.MUD_PATH_STAIRS);

		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_STONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_STONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_STONE_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_STONE, ExtendedItems.SCORCHED_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_BRICK, ExtendedItems.SCORCHED_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_BRICK, ExtendedItems.SCORCHED_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_BRICK, ExtendedItems.SCORCHED_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_COBBLESTONE, ExtendedItems.SCORCHED_COBBLESTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_COBBLESTONE, ExtendedItems.SCORCHED_COBBLESTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.SCORCHED_COBBLESTONE, ExtendedItems.SCORCHED_COBBLESTONE_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_DRYSTONE, ExtendedItems.MOSSY_DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_DRYSTONE, ExtendedItems.MOSSY_DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_DRYSTONE, ExtendedItems.MOSSY_DRYSTONE_WALL);
		makeStoneCutterRecipe(consumer, () ->Items.COBBLESTONE, ExtendedItems.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, () ->Items.COBBLESTONE, ExtendedItems.COBBLED_DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedItems.COBBLED_DRYSTONE, ExtendedItems.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedItems.CUT_DRYSTONE, ExtendedItems.CUT_DRYSTONE);
		makeStoneCutterRecipe(consumer, ExtendedItems.COBBLED_DRYSTONE, ExtendedItems.DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.COBBLED_DRYSTONE, ExtendedItems.DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.COBBLED_DRYSTONE, ExtendedItems.DRYSTONE_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CUT_DRYSTONE, ExtendedItems.DRYSTONE_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CUT_DRYSTONE, ExtendedItems.DRYSTONE_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CUT_DRYSTONE, ExtendedItems.DRYSTONE_WALL);

		makeStoneCutterRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK, ExtendedItems.TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK, ExtendedItems.TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.TAURETHRIM_BRICK, ExtendedItems.TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.TAURETHRIM_PILLAR, ExtendedItems.TAURETHRIM_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_TAURETHRIM_BRICK, ExtendedItems.MOSSY_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_TAURETHRIM_BRICK, ExtendedItems.MOSSY_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_TAURETHRIM_BRICK, ExtendedItems.MOSSY_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_TAURETHRIM_BRICK, ExtendedItems.CRACKED_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_TAURETHRIM_BRICK, ExtendedItems.CRACKED_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_TAURETHRIM_BRICK, ExtendedItems.CRACKED_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BRICK, ExtendedItems.GOLDEN_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BRICK, ExtendedItems.GOLDEN_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_BRICK, ExtendedItems.GOLDEN_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLDEN_TAURETHRIM_PILLAR, ExtendedItems.GOLDEN_TAURETHRIM_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK, ExtendedItems.OBSIDIAN_TAURETHRIM_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR, ExtendedItems.OBSIDIAN_TAURETHRIM_PILLAR_SLAB);

		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_DWARVEN_BRICK, ExtendedItems.OBSIDIAN_DWARVEN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_DWARVEN_BRICK, ExtendedItems.OBSIDIAN_DWARVEN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.OBSIDIAN_DWARVEN_BRICK, ExtendedItems.OBSIDIAN_DWARVEN_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedItems.GOBLIN_BRICK, ExtendedItems.GOBLIN_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOBLIN_BRICK, ExtendedItems.GOBLIN_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOBLIN_BRICK, ExtendedItems.GOBLIN_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOBLIN_PILLAR, ExtendedItems.GOBLIN_PILLAR_SLAB);

		makeStoneCutterRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK, ExtendedItems.DOL_GULDUR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK, ExtendedItems.DOL_GULDUR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK, ExtendedItems.DOL_GULDUR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.DOL_GULDUR_PILLAR, ExtendedItems.DOL_GULDUR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.DOL_GULDUR_BRICK, ExtendedItems.CARVED_DOL_GULDUR_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_DOL_GULDUR_BRICK, ExtendedItems.MOSSY_DOL_GULDUR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_DOL_GULDUR_BRICK, ExtendedItems.MOSSY_DOL_GULDUR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_DOL_GULDUR_BRICK, ExtendedItems.MOSSY_DOL_GULDUR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_DOL_GULDUR_BRICK, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_DOL_GULDUR_BRICK, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_DOL_GULDUR_BRICK, ExtendedItems.CRACKED_DOL_GULDUR_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedItems.RHUNIC_BRICK, ExtendedItems.RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.RHUNIC_BRICK, ExtendedItems.RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.RHUNIC_BRICK, ExtendedItems.RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.RHUNIC_PILLAR, ExtendedItems.RHUNIC_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.RHUNIC_BRICK, ExtendedItems.CARVED_RHUNIC_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RHUNIC_BRICK, ExtendedItems.MOSSY_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RHUNIC_BRICK, ExtendedItems.MOSSY_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RHUNIC_BRICK, ExtendedItems.MOSSY_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RHUNIC_BRICK, ExtendedItems.CRACKED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RHUNIC_BRICK, ExtendedItems.CRACKED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RHUNIC_BRICK, ExtendedItems.CRACKED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK, ExtendedItems.GOLD_TRIMMED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.FLOWERY_RHUNIC_BRICK, ExtendedItems.FLOWERY_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.FLOWERY_RHUNIC_BRICK, ExtendedItems.FLOWERY_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.FLOWERY_RHUNIC_BRICK, ExtendedItems.FLOWERY_RHUNIC_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK, ExtendedItems.RED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK, ExtendedItems.RED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK, ExtendedItems.RED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_RHUNIC_PILLAR, ExtendedItems.RED_RHUNIC_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_RHUNIC_BRICK, ExtendedItems.CARVED_RED_RHUNIC_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RED_RHUNIC_BRICK, ExtendedItems.MOSSY_RED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RED_RHUNIC_BRICK, ExtendedItems.MOSSY_RED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RED_RHUNIC_BRICK, ExtendedItems.MOSSY_RED_RHUNIC_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RED_RHUNIC_BRICK, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RED_RHUNIC_BRICK, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RED_RHUNIC_BRICK, ExtendedItems.CRACKED_RED_RHUNIC_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedItems.RED_ANGMAR_BRICK, ExtendedItems.RED_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_ANGMAR_BRICK, ExtendedItems.RED_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_ANGMAR_BRICK, ExtendedItems.RED_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_ANGMAR_PILLAR, ExtendedItems.RED_ANGMAR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.RED_ANGMAR_BRICK, ExtendedItems.CARVED_RED_ANGMAR_BRICK);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RED_ANGMAR_BRICK, ExtendedItems.MOSSY_RED_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RED_ANGMAR_BRICK, ExtendedItems.MOSSY_RED_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_RED_ANGMAR_BRICK, ExtendedItems.MOSSY_RED_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RED_ANGMAR_BRICK, ExtendedItems.CRACKED_RED_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RED_ANGMAR_BRICK, ExtendedItems.CRACKED_RED_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_RED_ANGMAR_BRICK, ExtendedItems.CRACKED_RED_ANGMAR_BRICK_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.SNOWY_RED_ANGMAR_BRICK, ExtendedItems.SNOWY_RED_ANGMAR_BRICK_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.SNOWY_RED_ANGMAR_BRICK, ExtendedItems.SNOWY_RED_ANGMAR_BRICK_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.SNOWY_RED_ANGMAR_BRICK, ExtendedItems.SNOWY_RED_ANGMAR_BRICK_WALL);

		makeStoneCutterRecipe(consumer, ExtendedItems.MORGOND, ExtendedItems.MORGOND_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.MORGOND, ExtendedItems.MORGOND_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.MORGOND, ExtendedItems.MORGOND_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.MORGOND_PILLAR, ExtendedItems.MORGOND_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.MORGOND, ExtendedItems.CARVED_MORGOND);
		makeStoneCutterRecipe(consumer, ExtendedItems.MORGOND, ExtendedItems.MORGOND_DRIPSTONE);
		makeStoneCutterRecipe(consumer, ExtendedItems.CARVED_MORGOND, ExtendedItems.CARVED_MORGOND_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.CARVED_MORGOND, ExtendedItems.CARVED_MORGOND_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.CARVED_MORGOND, ExtendedItems.CARVED_MORGOND_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_MORGOND, ExtendedItems.SMOOTH_MORGOND_SLAB, 2);
		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_MORGOND, ExtendedItems.SMOOTH_MORGOND_STAIRS);
		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_MORGOND, ExtendedItems.SMOOTH_MORGOND_WALL);
		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_MORGOND, ExtendedItems.SMOOTH_MORGOND_DRIPSTONE);

		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_ARNOR_PILLAR, ExtendedItems.CRACKED_ARNOR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.MOSSY_ARNOR_PILLAR, ExtendedItems.MOSSY_ARNOR_PILLAR_SLAB);
		makeStoneCutterRecipe(consumer, ExtendedItems.CRACKED_STONE_PILLAR, ExtendedItems.CRACKED_STONE_PILLAR_SLAB);

		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_ROCK, ExtendedItems.GNEISS_ROCK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_ROCK, ExtendedItems.GNEISS_ROCK_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_ROCK, ExtendedItems.GNEISS_ROCK_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_ROCK, ExtendedItems.GNEISS_BRICK);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_BRICK, ExtendedItems.GNEISS_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_BRICK, ExtendedItems.GNEISS_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_BRICK, ExtendedItems.GNEISS_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_BRICK, ExtendedItems.POLISHED_GNEISS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.GNEISS_ROCK, ExtendedItems.POLISHED_GNEISS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_GNEISS, ExtendedItems.POLISHED_GNEISS_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_GNEISS, ExtendedItems.POLISHED_GNEISS_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_GNEISS, ExtendedItems.POLISHED_GNEISS_WALL);
		//
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_ROCK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_ROCK_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_ROCK_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_BRICK);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_TILE);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_TILE);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_TILE, ExtendedItems.LIMESTONE_TILE_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_TILE, ExtendedItems.LIMESTONE_TILE_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_TILE, ExtendedItems.LIMESTONE_TILE_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_TILE_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_TILE_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.LIMESTONE_TILE_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_TILE_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_TILE_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_BRICK, ExtendedItems.LIMESTONE_TILE_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.POLISHED_LIMESTONE);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_LIMESTONE, ExtendedItems.POLISHED_LIMESTONE_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_LIMESTONE, ExtendedItems.POLISHED_LIMESTONE_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_LIMESTONE, ExtendedItems.POLISHED_LIMESTONE_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.LIMESTONE_ROCK, ExtendedItems.SMOOTH_LIMESTONE);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_LIMESTONE, ExtendedItems.SMOOTH_LIMESTONE);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_LIMESTONE, ExtendedItems.SMOOTH_LIMESTONE_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_LIMESTONE, ExtendedItems.SMOOTH_LIMESTONE_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SMOOTH_LIMESTONE, ExtendedItems.SMOOTH_LIMESTONE_WALL);
		//
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_ROCK, ExtendedItems.SLATE_ROCK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_ROCK, ExtendedItems.SLATE_ROCK_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_ROCK, ExtendedItems.SLATE_ROCK_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_ROCK, ExtendedItems.SLATE_BRICK);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_BRICK, ExtendedItems.SLATE_BRICK_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_BRICK, ExtendedItems.SLATE_BRICK_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_BRICK, ExtendedItems.SLATE_BRICK_WALL);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.SLATE_ROCK, ExtendedItems.POLISHED_SLATE);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_SLATE, ExtendedItems.POLISHED_SLATE_SLAB, 2);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_SLATE, ExtendedItems.POLISHED_SLATE_STAIRS);
		//		makeStoneCutterRecipe(consumer, ExtendedItems.POLISHED_SLATE, ExtendedItems.POLISHED_SLATE_WALL);
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
		GENERATEDRECIPES.add(ExtendedItems.AMBER_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.AMETHYST_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.DIAMOND_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EMERALD_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.OPAL_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RUBY_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SAPPHIRE_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.TOPAZ_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.DWARF_MARRIAGE_RING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_MARRIAGE_RING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.PEARL.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLACK_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GRAY_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SILVER_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WHITE_FUR.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.REMAINS_ELF.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.REMAINS_MAN.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.REMAINS_ORC.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.REMAINS_DWARF.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.MUD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.MUD_FARMLAND.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.MUD_PATH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.MUD_GRASS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHEESE_SLICE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CONKER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GREEN_FLAME_HEARTH_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.GREEN_FIRE_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STONE_CHEST.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHESTNUT_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STRIPPED_CHESTNUT_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_CHESTNUT_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHESTNUT_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHESTNUT_LEAVES_FRUIT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CHESTNUT_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BANANA.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BANANA_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STRIPPED_BANANA_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_BANANA_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BANANA_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BANANA_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.HANGING_BANANA.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.AGED_CHEESE_WHEEL.get().getRegistryName().getPath());
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
		GENERATEDRECIPES.add(ExtendedItems.PLUM_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STRIPPED_PLUM_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_PLUM_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.PLUM_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.PLUM_LEAVES_FRUIT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.PLUM_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RAW_DUCK.get().getRegistryName().getPath());
		//		GENERATEDRECIPES.add(ExtendedItems.SLATE_ROCK.get().getRegistryName().getPath());
		//		GENERATEDRECIPES.add(ExtendedItems.LIMESTONE_ROCK.get().getRegistryName().getPath());
		//		GENERATEDRECIPES.add(ExtendedItems.GNEISS_ROCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.MOSS_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ANCIENT_RUNE_BLOCK.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLACK_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BROWN_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GRAY_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SILVER_WARG_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WHITE_WARG_HELMET.get().getRegistryName().getPath());
		for(RegistryObject<Block> blo : ExtendedBlocks.REGISTEREDPOTS.values()) {
			GENERATEDRECIPES.add(blo.get().getRegistryName().getPath());
		}
		GENERATEDRECIPES.add(ExtendedBlocks.BREE_WALL_BANNER.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILLOW_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STRIPPED_WILLOW_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_WILLOW_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILLOW_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILLOW_SAPLING.get().getRegistryName().getPath());
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
		GENERATEDRECIPES.add(ExtendedItems.EREGION_BOOTS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EREGION_LEGGINGS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EREGION_CHESTPLATE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EREGION_HELMET.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EREGION_SHIELD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.EXTENDED_BOOKSHELF.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.METEORITE_ORE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EBONY_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STRIPPED_EBONY_LOG.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.POTTED_EBONY_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EBONY_LEAVES.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.EBONY_SAPLING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STAFF_GANDALF_GRAY.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.STAFF_GANDALF_WHITE.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.GLAMDRING.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ARNOR_SHIELD.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.COMBAT_TOME.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ARTISAN_TOME.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.SMITH_TOME.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.LEEKS.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.LEEK.get().getRegistryName().getPath());
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recipe Provider";
	}
}