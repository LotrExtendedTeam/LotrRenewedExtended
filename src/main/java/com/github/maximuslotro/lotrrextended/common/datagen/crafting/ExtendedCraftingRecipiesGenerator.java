package com.github.maximuslotro.lotrrextended.common.datagen.crafting;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedAlloyCookingRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedCustomRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapelessRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.tags.ExtendedItemTagGenerator;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCraftingRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		buildRecipes(consumer);
		buildStonecutterRecipes(consumer);
		addOverrides();
		checkForMissingRecipes();
	}

	private void buildRecipes(Consumer<IFinishedRecipe> consumer) {
		//Beds
		makeBedRecipe(consumer, ExtendedBlocks.STRAW_BED, Items.WHEAT, null);
		makeBedRecipeTag(consumer, ExtendedBlocks.ORC_BED, WOOL, LOTRRecipes.ANY_ORC);
		makeBedRecipe(consumer, ExtendedBlocks.BLACK_FUR_BED, ExtendedItems.BLACK_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.FUR_BED, LOTRItems.FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.GRAY_FUR_BED, ExtendedItems.GRAY_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.SILVER_FUR_BED, ExtendedItems.SILVER_FUR.get(), null);
		makeBedRecipe(consumer, ExtendedBlocks.WHITE_FUR_BED, ExtendedItems.WHITE_FUR.get(), null);
		GENERATEDRECIPES.add(ExtendedItems.BEDROLL.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.BEDROLL.get())
		.pattern("LLF")
		.define('L', Ingredient.of(Tags.Items.LEATHER))
		.define('F', Ingredient.of(ExtendedItemTagGenerator.FURS))
		.unlockedBy("has_"+LOTRItems.FUR.get().getRegistryName().getPath(), has(ExtendedItemTagGenerator.FURS))
		.unlockedBy("has_"+ExtendedItems.BEDROLL.get().getRegistryName().getPath(), has(ExtendedItems.BEDROLL.get()))
		.save(consumer, lotrLoc(ExtendedItems.BEDROLL.get().getRegistryName().getPath()));
		/////////////////WOOD
		//CHESTNUT
		makePillarSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_BEAM, 
				ExtendedBlocks.CHESTNUT_BEAM_SLAB, ExtendedItems.CHESTNUT_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.CHESTNUT_BOAT, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWallRecipe(consumer, ExtendedBlocks.CHESTNUT_BRANCH, ExtendedItems.CHESTNUT_WOOD, null);
		makeButtonRecipe(consumer, ExtendedItems.CHESTNUT_BUTTON, ExtendedItems.CHESTNUT_PLANKS, null);
		makeDoorRecipe(consumer, ExtendedItems.CHESTNUT_DOOR, ExtendedItems.CHESTNUT_PLANKS, null);
		makeFenceAndFenceGateRecipe(consumer, ExtendedBlocks.CHESTNUT_FENCE, ExtendedBlocks.CHESTNUT_FENCE_GATE,
				ExtendedItems.CHESTNUT_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_LOG_SLAB, ExtendedItems.CHESTNUT_LOG, null);
		makePressurePlateRecipe(consumer, ExtendedBlocks.CHESTNUT_PRESSURE_PLATE, ExtendedItems.CHESTNUT_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_STAIRS, ExtendedBlocks.CHESTNUT_SLAB, ExtendedItems.CHESTNUT_PLANKS, null);
		makeTrapdoorRecipe(consumer, ExtendedBlocks.CHESTNUT_TRAPDOOR, ExtendedItems.CHESTNUT_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.CHESTNUT_WOOD_STAIRS, ExtendedBlocks.CHESTNUT_WOOD_SLAB, ExtendedItems.CHESTNUT_WOOD, null);
		makeSignRecipe(consumer, ExtendedBlocks.CHESTNUT_SIGN, ExtendedBlocks.CHESTNUT_WALL_SIGN, ExtendedItems.CHESTNUT_PLANKS, null);
		makeWoodRecipe(consumer, ExtendedBlocks.CHESTNUT_WOOD, ExtendedItems.CHESTNUT_LOG, null);
		makePlanksRecipe(consumer, ExtendedBlocks.CHESTNUT_PLANKS, ExtendedBlocks.CHESTNUT_LOG, 
				ExtendedTags.Items.CHESTNUT_LOGS, null);
		makePlanksFromBlocksRecipe(consumer, ExtendedItems.CHESTNUT_PLANKS, ExtendedItems.CHESTNUT_LOG_SLAB, 
				ExtendedItems.STRIPPED_CHESTNUT_LOG_SLAB, ExtendedItems.CHESTNUT_BRANCH, ExtendedItems.STRIPPED_CHESTNUT_BRANCH, 
				ExtendedItems.CHESTNUT_WOOD_SLAB, ExtendedItems.STRIPPED_CHESTNUT_WOOD_SLAB, 
				ExtendedItems.CHESTNUT_WOOD_STAIRS, ExtendedItems.STRIPPED_CHESTNUT_WOOD_STAIRS, null);
		makeWallRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_BRANCH, ExtendedItems.STRIPPED_CHESTNUT_WOOD, null);
		makeSlabRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_LOG_SLAB, ExtendedItems.STRIPPED_CHESTNUT_LOG, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD_SLAB, 
				ExtendedItems.STRIPPED_CHESTNUT_WOOD, null);
		makeWoodRecipe(consumer, ExtendedBlocks.STRIPPED_CHESTNUT_WOOD, ExtendedItems.STRIPPED_CHESTNUT_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedBlocks.FALLEN_CHESTNUT_LEAVES, ExtendedItems.CHESTNUT_LEAVES, null);
		//BANANA
		makePillarSlabRecipe(consumer, ExtendedBlocks.BANANA_BEAM, 
				ExtendedBlocks.BANANA_BEAM_SLAB, ExtendedItems.BANANA_LOG, null);
		makeBoatRecipe(consumer, ExtendedItems.BANANA_BOAT, ExtendedItems.BANANA_PLANKS, null);
		makeButtonRecipe(consumer, ExtendedItems.BANANA_BUTTON, ExtendedItems.BANANA_PLANKS, null);
		makeDoorRecipe(consumer, ExtendedItems.BANANA_DOOR, ExtendedItems.BANANA_PLANKS, null);
		makeFenceAndFenceGateRecipe(consumer, ExtendedBlocks.BANANA_FENCE, ExtendedBlocks.BANANA_FENCE_GATE,
				ExtendedItems.BANANA_PLANKS, null);
		makeSlabRecipe(consumer, ExtendedBlocks.BANANA_LOG_SLAB, ExtendedItems.BANANA_LOG, null);
		makePressurePlateRecipe(consumer, ExtendedBlocks.BANANA_PRESSURE_PLATE, ExtendedItems.BANANA_PLANKS, null);
		makeStairSlabRecipe(consumer, ExtendedBlocks.BANANA_STAIRS, ExtendedBlocks.BANANA_SLAB, ExtendedItems.BANANA_PLANKS, null);
		makeTrapdoorRecipe(consumer, ExtendedBlocks.BANANA_TRAPDOOR, ExtendedItems.BANANA_PLANKS, null);
		makeSignRecipe(consumer, ExtendedBlocks.BANANA_SIGN, ExtendedBlocks.BANANA_WALL_SIGN, ExtendedItems.BANANA_PLANKS, null);
		makePlanksRecipe(consumer, ExtendedBlocks.BANANA_PLANKS, ExtendedBlocks.BANANA_LOG, 
				ExtendedTags.Items.BANANA_LOGS, null);
		makePlanksFromBlocksRecipeNoBranchWood(consumer, ExtendedItems.BANANA_PLANKS, ExtendedItems.BANANA_LOG_SLAB, 
				ExtendedItems.STRIPPED_BANANA_LOG_SLAB, null);
		makeSlabRecipe(consumer, ExtendedBlocks.STRIPPED_BANANA_LOG_SLAB, ExtendedItems.STRIPPED_BANANA_LOG, null);
		makeFallenLeavesRecipe(consumer, ExtendedBlocks.FALLEN_BANANA_LEAVES, ExtendedItems.BANANA_LEAVES, null);
		//ROHAN
		GENERATEDRECIPES.add(ExtendedItems.ROHAN_BEAM.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.ROHAN_BEAM.get(), 3, LOTRRecipes.ROHAN_CRAFTING)
		.pattern("X")
		.pattern("X")
		.pattern("X")
		.define('X', Ingredient.of(ItemTags.LOGS))
		.unlockedBy("has_logs", has(ItemTags.LOGS))
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
		makeCraftingTableRecipe(consumer, ExtendedBlocks.TAURETHRIM_STONE_CRAFTING_TABLE, 
				()->ExtendedBlocks.TAURETHRIM_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.TAURETHRIM_CRAFTING_TABLE, 
				()->ExtendedBlocks.MUD_BRICK.get().asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.DOL_GULDUR_CRAFTING_TABLE, 
				()->ExtendedBlocks.DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.RHUNIC_CRAFTING_TABLE, 
				()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.HALF_TROLL_CRAFTING_TABLE, 
				()->Blocks.COBBLESTONE.asItem(), ExtendedRecipes.HALF_TROLL_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.GUNDABAD_CRAFTING_TABLE, 
				()->Blocks.COBBLESTONE.asItem(), ExtendedRecipes.GUNDABAD_CRAFTING);
		makeCraftingTableRecipe(consumer, ExtendedBlocks.MORWAITH_CRAFTING_TABLE, 
				()->Blocks.CLAY.asItem(), ExtendedRecipes.MORWAITH_CRAFTING);
		GENERATEDRECIPES.add(ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath());
		ExtendedShapedRecipeBuilder.shaped(ExtendedItems.CONQUEST_TABLE.get())
		.pattern("XXX")
		.pattern("YYY")
		.pattern("ZZZ")
		.define('X', Ingredient.of(Items.PAPER))
		.define('Y', Ingredient.of(ItemTags.PLANKS))
		.define('Z', Ingredient.of(LOTRTags.Items.INGOTS_BRONZE))
		.unlockedBy("has_"+LOTRItems.BRONZE_INGOT.get().getRegistryName().getPath(), has(LOTRItems.BRONZE_INGOT.get()))
		.unlockedBy("has_"+ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath(), has(ExtendedItems.CONQUEST_TABLE.get()))
		.saveNoAdvancement(consumer, lotrLoc(ExtendedItems.CONQUEST_TABLE.get().getRegistryName().getPath()));
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
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.BLACK_FUR, ExtendedBlocks.BLACK_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.GRAY_FUR, ExtendedBlocks.GRAY_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.SILVER_FUR, ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.WHITE_FUR, ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);

		//MORGUL
		makeArmorRecipe(consumer, ExtendedItems.MORGUL_HELMET, 
				ExtendedItems.MORGUL_CHESTPLATE, 
				ExtendedItems.MORGUL_LEGGINGS, 
				ExtendedItems.MORGUL_BOOTS, 
				ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		makeSwordRecipe(consumer, ExtendedItems.MORGUL_SWORD, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		makeSpearRecipe(consumer, ExtendedItems.MORGUL_SPEAR, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		makeDaggerRecipe(consumer, ExtendedItems.MORGUL_DAGGER, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_MORGUL);
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_LANTERN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.MORGUL_LANTERN.get(), LOTRRecipes.ANY_MORGUL)
		.pattern("YYY")
		.pattern("Y#Y")
		.pattern("YYY")
		.define('#', Ingredient.of(ExtendedBlocks.MORGUL_TORCH.get()))
		.define('Y', Ingredient.of(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.unlockedBy("has_"+ExtendedItems.MORGUL_LANTERN.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_LANTERN.get()))
		.unlockedBy("has_"+ExtendedBlocks.MORGUL_TORCH.get().getRegistryName().getPath(), has(ExtendedBlocks.MORGUL_TORCH.get()))
		.unlockedBy("has_orc_steel_nugget", has(ExtendedTags.Items.ORC_STEEL_NUGGET))
		.save(consumer, lotrLoc(ExtendedItems.MORGUL_LANTERN.get().getRegistryName().getPath()));
		makeTorchRecipe(consumer, ExtendedBlocks.MORGUL_TORCH, LOTRItems.GULDURIL, LOTRRecipes.ANY_MORGUL);
		makeDoorRecipe(consumer, ExtendedItems.ORC_STEEL_DOOR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeTrapdoorRecipe(consumer, ExtendedBlocks.ORC_STEEL_TRAPDOOR, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANY_ORC);
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
				()->Blocks.STONE.asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.DOL_GULDUR_PILLAR, 
				ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB, 
				()->Blocks.STONE.asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		make2x2BlockRecipe(consumer, ExtendedBlocks.CARVED_DOL_GULDUR_BRICK, 
				()->ExtendedBlocks.DOL_GULDUR_BRICK.get().asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
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
				()->Blocks.STONE.asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.TAURETHRIM_PILLAR, 
				ExtendedBlocks.TAURETHRIM_PILLAR_SLAB, 
				()->Blocks.STONE.asItem(), ExtendedRecipes.TAURETHRIM_CRAFTING);
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
		make2x2BlockRecipe(consumer, ExtendedBlocks.CARVED_RHUNIC_BRICK, 
				()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
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
		make2x2BlockRecipe(consumer, ExtendedBlocks.CARVED_RED_RHUNIC_BRICK, 
				()->ExtendedBlocks.RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
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
		make2x2BlockRecipe(consumer, ExtendedBlocks.CARVED_BLACK_GONDOR_BRICK, 
				()->LOTRBlocks.NUMENOREAN_BRICK.get().asItem(), LOTRRecipes.GONDOR_CRAFTING);
		make2x2BlockRecipe(consumer, ExtendedBlocks.CARVED_BLACK_UMBAR_BRICK, 
				()->LOTRBlocks.NUMENOREAN_BRICK.get().asItem(), LOTRRecipes.UMBAR_CRAFTING);
		make2x2BlockRecipe(consumer, ExtendedBlocks.CARVED_UMBAR_BRICK, 
				()->LOTRBlocks.UMBAR_BRICK.get().asItem(), LOTRRecipes.UMBAR_CRAFTING);
		//DWARVEN
		make2x2BlockRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK, 
				ExtendedItems.OBSIDIAN_SHARD, LOTRRecipes.DWARVEN_CRAFTING);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_STAIRS, 
				ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_SLAB, 
				ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK_WALL, 
				()->ExtendedBlocks.OBSIDIAN_DWARVEN_BRICK.get().asItem(), LOTRRecipes.DWARVEN_CRAFTING);
		//ITEMS
		GENERATEDRECIPES.add(ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.BRANDING_IRON.get())
		.pattern("  #")
		.pattern(" X ")
		.pattern("#  ")
		.define('#', Ingredient.of(IRON_INGOT))
		.define('X', Ingredient.of(Tags.Items.LEATHER))
		.unlockedBy("has_"+ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath(), has(ExtendedItems.BRANDING_IRON.get()))
		.unlockedBy("has_iron_ingot", has(IRON_INGOT))
		.save(consumer, lotrLoc(ExtendedItems.BRANDING_IRON.get().getRegistryName().getPath()));

		GENERATEDRECIPES.add(ExtendedItems.LONGBOW.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.LONGBOW.get())
		.pattern("XXX")
		.pattern("X #")
		.pattern("X##")
		.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
		.define('X', Ingredient.of(Items.STICK))
		.unlockedBy("has_"+ExtendedItems.BOWSTRING.get().getRegistryName().getPath(), has(ExtendedItems.BOWSTRING.get()))
		.unlockedBy("has_stick", has(Items.STICK))
		.save(consumer, lotrLoc(ExtendedItems.LONGBOW.get().getRegistryName().getPath()));

		GENERATEDRECIPES.add(ExtendedItems.BOWSTRING.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.BOWSTRING.get())
		.requires(Items.STRING)
		.requires(Items.STRING)
		.requires(Items.STRING)
		.unlockedBy("has_string", has(Items.STRING))
		.save(consumer, lotrLoc(ExtendedItems.BOWSTRING.get().getRegistryName().getPath()));

		GENERATEDRECIPES.add(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.CHEESE_WHEEL.get())
		.pattern("###")
		.pattern("XSX")
		.pattern("###")
		.define('#', Ingredient.of(ExtendedItems.CHEESE_CURDS.get()))
		.define('X', Ingredient.of(LOTRItems.SALT.get()))
		.define('S', Ingredient.of(Tags.Items.MUSHROOMS))
		.unlockedBy("has_"+ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_WHEEL.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_CURDS.get()))
		.save(consumer, lotrLoc(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath()));
		ShapedRecipeBuilder.shaped(ExtendedItems.CHEESE_WHEEL.get())
		.pattern("XXX")
		.pattern("#X#")
		.pattern("XXX")
		.define('X', Ingredient.of(ExtendedItems.CHEESE_SLICE.get()))
		.define('#', Ingredient.of(ExtendedItems.CHEESE_CURDS.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_WHEEL.get()))
		.unlockedBy("has_"+ExtendedItems.CHEESE_SLICE.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_SLICE.get()))
		.save(consumer, lotrLoc(ExtendedItems.CHEESE_WHEEL.get().getRegistryName().getPath()+"_from_slices"));
		GENERATEDRECIPES.add(ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(Items.MILK_BUCKET), 
				ExtendedItems.CHEESE_CURDS.get(), 0.15F, 200)
		.unlockedBy("has_"+Items.MILK_BUCKET.getRegistryName().getPath(), has(Items.MILK_BUCKET))
		.unlockedBy("has_"+ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath(), has(ExtendedItems.CHEESE_CURDS.get()))
		.save(consumer, lotrLoc(ExtendedItems.CHEESE_CURDS.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ExtendedItems.CONKER.get()), 
				ExtendedItems.ROASTED_CHESTNUT.get(), 0.15F, 200)
		.unlockedBy("has_"+ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath(), has(ExtendedItems.ROASTED_CHESTNUT.get()))
		.unlockedBy("has_"+ExtendedItems.CONKER.get().getRegistryName().getPath(), has(ExtendedItems.CONKER.get()))
		.save(consumer, lotrLoc(ExtendedItems.ROASTED_CHESTNUT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.BANANA_CAKE.get())
		.pattern("###")
		.pattern("XSX")
		.pattern("ZZZ")
		.define('#', Ingredient.of(Items.MILK_BUCKET))
		.define('X', Ingredient.of(ExtendedItems.BANANA.get()))
		.define('S', Ingredient.of(Items.EGG))
		.define('Z', Ingredient.of(Items.WHEAT))
		.unlockedBy("has_"+ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath(), has(ExtendedItems.BANANA_CAKE.get()))
		.unlockedBy("has_"+ExtendedItems.BANANA.get().getRegistryName().getPath(), has(ExtendedItems.BANANA.get()))
		.save(consumer, lotrLoc(ExtendedItems.BANANA_CAKE.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.BANANA_BREAD.get())
		.pattern("XSX")
		.define('S', Ingredient.of(ExtendedItems.BANANA.get()))
		.define('X', Ingredient.of(Items.WHEAT))
		.unlockedBy("has_"+ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath(), has(ExtendedItems.BANANA_BREAD.get()))
		.unlockedBy("has_"+ExtendedItems.BANANA.get().getRegistryName().getPath(), has(ExtendedItems.BANANA.get()))
		.save(consumer, lotrLoc(ExtendedItems.BANANA_BREAD.get().getRegistryName().getPath()));

		GENERATEDRECIPES.add(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.SILVER_COIN_ONE.get(), 4)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(LOTRItems.SILVER_NUGGET.get()))
		.unlockedBy("has_"+ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath(), has(ExtendedItems.SILVER_COIN_ONE.get()))
		.unlockedBy("has_"+LOTRItems.SILVER_NUGGET.get().getRegistryName().getPath(), has(LOTRItems.SILVER_NUGGET.get()))
		.save(consumer, lotrLoc(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.HITHLAIN.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HITHLAIN.get(), 3, LOTRRecipes.GALADHRIM_CRAFTING)
		.pattern("###")
		.define('#', Ingredient.of(Items.STRING))
		.unlockedBy("has_"+ExtendedItems.HITHLAIN.get().getRegistryName().getPath(), has(ExtendedItems.HITHLAIN.get()))
		.unlockedBy("has_"+Items.STRING.getRegistryName().getPath(), has(Items.STRING))
		.save(consumer, lotrLoc(ExtendedItems.HITHLAIN.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(ExtendedItems.BOTTLE_OF_POISON.get())
		.requires(ExtendedItems.WILDBERRY.get())
		.requires(Items.GLASS_BOTTLE)
		.unlockedBy("has_"+ExtendedItems.WILDBERRY.get().getRegistryName().getPath(), has(ExtendedItems.WILDBERRY.get()))
		.unlockedBy("has_"+ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath(), has(ExtendedItems.BOTTLE_OF_POISON.get()))
		.save(consumer, lotrLoc(ExtendedItems.BOTTLE_OF_POISON.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.COMMANDHORN.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.COMMANDHORN.get())
		.pattern("#X#")
		.define('#', Ingredient.of(BRONZE_INGOT))
		.define('X', Ingredient.of(HORNS))
		.unlockedBy("has_"+ExtendedItems.COMMANDHORN.get().getRegistryName().getPath(), has(ExtendedItems.COMMANDHORN.get()))
		.unlockedBy("has_horn", has(HORNS))
		.save(consumer, lotrLoc(ExtendedItems.COMMANDHORN.get().getRegistryName().getPath()));
		ExtendedCustomRecipeBuilder.special(ExtendedRecipes.EXTENDED_TIPPED_ARROW.get()).save(consumer, lotrLoc("tipped_arrow_poison"));
		GENERATEDRECIPES.add(ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.BLACK_URUK_STEEL_INGOT.get(),
				LOTRRecipes.ORC_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ORC_STEEL)
		.requires(ExtendedTags.Items.MORGUL_IRON_ORE)
		.requiresAlloy(LOTRItems.DURNOR.get())
		.unlockedBy("has_"+ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.BLACK_URUK_STEEL_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.DURNOR.get().getRegistryName().getPath(), has(LOTRItems.DURNOR.get()))
		.save(consumer, lotrLoc(ExtendedItems.BLACK_URUK_STEEL_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.GALVORN_INGOT.get(),
				LOTRRecipes.DWARVEN_FORGE_ALLOY_SERIALIZER.get())
		.requires(IRON_ORE)
		.requires(IRON_INGOT)
		.requiresAlloy(LOTRItems.EDHELVIR.get())
		.unlockedBy("has_"+ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.GALVORN_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.EDHELVIR.get().getRegistryName().getPath(), has(LOTRItems.EDHELVIR.get()))
		.save(consumer, lotrLoc(ExtendedItems.GALVORN_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.GILDED_IRON_INGOT.get(),
				LOTRRecipes.ALLOY_SERIALIZER.get())
		.requires(IRON_ORE)
		.requires(IRON_INGOT)
		.requiresAlloy(Items.GOLD_NUGGET)
		.unlockedBy("has_"+ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.GILDED_IRON_INGOT.get()))
		.unlockedBy("has_"+Items.GOLD_NUGGET.getRegistryName().getPath(), has(Items.GOLD_NUGGET))
		.save(consumer, lotrLoc(ExtendedItems.GILDED_IRON_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.MORGUL_STEEL_INGOT.get(),
				LOTRRecipes.ORC_FORGE_ALLOY_SERIALIZER.get())
		.requires(ExtendedTags.Items.ORC_STEEL)
		.requires(ExtendedTags.Items.MORGUL_IRON_ORE)
		.requiresAlloy(LOTRItems.GULDURIL.get())
		.unlockedBy("has_"+ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath(), has(ExtendedItems.MORGUL_STEEL_INGOT.get()))
		.unlockedBy("has_"+LOTRItems.GULDURIL.get().getRegistryName().getPath(), has(LOTRItems.GULDURIL.get()))
		.save(consumer, lotrLoc(ExtendedItems.MORGUL_STEEL_INGOT.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.ITHILDIN.get().getRegistryName().getPath());
		ExtendedAlloyCookingRecipeBuilder.cooking(ExtendedItems.ITHILDIN.get(),
				LOTRRecipes.ELVEN_FORGE_ALLOY_SERIALIZER.get())
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
		//Banners
		GENERATEDRECIPES.add(ExtendedItems.DUNEDAIN_SHIELD.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DUNEDAIN_SHIELD.get(), LOTRRecipes.RANGER_CRAFTING)
		.pattern("PIP")
		.pattern("PPP")
		.pattern(" P ")
		.define('I', Ingredient.of(Items.IRON_INGOT))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_"+Items.IRON_INGOT.getRegistryName().getPath(), has(Items.IRON_INGOT))
		.unlockedBy("has_"+ExtendedItems.DUNEDAIN_SHIELD.get().getRegistryName().getPath(), has(ExtendedItems.DUNEDAIN_SHIELD.get()))
		.save(consumer, lotrLoc(ExtendedItems.DUNEDAIN_SHIELD.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.DUNEDAIN_BANNER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DUNEDAIN_BANNER.get(), LOTRRecipes.RANGER_CRAFTING)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(WOOL))
		.define('S', Ingredient.of(STICKS))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_wool", has(WOOL))
		.unlockedBy("has_"+ExtendedItems.DUNEDAIN_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.DUNEDAIN_BANNER.get()))
		.save(consumer, lotrLoc(ExtendedItems.DUNEDAIN_BANNER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.HOBBIT_BANNER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.HOBBIT_BANNER.get(), LOTRRecipes.HOBBIT_CRAFTING)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(WOOL))
		.define('S', Ingredient.of(STICKS))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_wool", has(WOOL))
		.unlockedBy("has_"+ExtendedItems.HOBBIT_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.HOBBIT_BANNER.get()))
		.save(consumer, lotrLoc(ExtendedItems.HOBBIT_BANNER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.DURINS_FOLK_BANNER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.DURINS_FOLK_BANNER.get(), LOTRRecipes.DWARVEN_CRAFTING)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(WOOL))
		.define('S', Ingredient.of(STICKS))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_wool", has(WOOL))
		.unlockedBy("has_"+ExtendedItems.DURINS_FOLK_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.DURINS_FOLK_BANNER.get()))
		.save(consumer, lotrLoc(ExtendedItems.DURINS_FOLK_BANNER.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BLUE_MOUNTAINS_BANNER.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.BLUE_MOUNTAINS_BANNER.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(WOOL))
		.define('S', Ingredient.of(STICKS))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_wool", has(WOOL))
		.unlockedBy("has_"+ExtendedItems.BLUE_MOUNTAINS_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.BLUE_MOUNTAINS_BANNER.get()))
		.save(consumer, lotrLoc(ExtendedItems.BLUE_MOUNTAINS_BANNER.get().getRegistryName().getPath()));
		//Banner Patterns
		GENERATEDRECIPES.add(ExtendedItems.DUNEDAIN_BANNER_PATTERN.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(ExtendedItems.DUNEDAIN_BANNER_PATTERN.get(), LOTRRecipes.RANGER_CRAFTING)
		.requires(Items.PAPER)
		.requires(ExtendedItems.DUNEDAIN_BANNER.get())
		.unlockedBy("has_"+ExtendedItems.DUNEDAIN_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.DUNEDAIN_BANNER.get()))
		.unlockedBy("has_"+ExtendedItems.DUNEDAIN_BANNER_PATTERN.get().getRegistryName().getPath(), has(ExtendedItems.DUNEDAIN_BANNER_PATTERN.get()))
		.save(consumer, lotrLoc(ExtendedItems.DUNEDAIN_BANNER_PATTERN.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN.get(), LOTRRecipes.BLUE_MOUNTAINS_CRAFTING)
		.requires(Items.PAPER)
		.requires(ExtendedItems.BLUE_MOUNTAINS_BANNER.get())
		.unlockedBy("has_"+ExtendedItems.BLUE_MOUNTAINS_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.BLUE_MOUNTAINS_BANNER.get()))
		.unlockedBy("has_"+ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN.get().getRegistryName().getPath(), has(ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN.get()))
		.save(consumer, lotrLoc(ExtendedItems.BLUE_MOUNTAINS_BANNER_PATTERN.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(ExtendedItems.DURINS_FOLK_BANNER_PATTERN.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(ExtendedItems.DURINS_FOLK_BANNER_PATTERN.get(), LOTRRecipes.DWARVEN_CRAFTING)
		.requires(Items.PAPER)
		.requires(ExtendedItems.DURINS_FOLK_BANNER.get())
		.unlockedBy("has_"+ExtendedItems.DURINS_FOLK_BANNER.get().getRegistryName().getPath(), has(ExtendedItems.DURINS_FOLK_BANNER.get()))
		.unlockedBy("has_"+ExtendedItems.DURINS_FOLK_BANNER_PATTERN.get().getRegistryName().getPath(), has(ExtendedItems.DURINS_FOLK_BANNER_PATTERN.get()))
		.save(consumer, lotrLoc(ExtendedItems.DURINS_FOLK_BANNER_PATTERN.get().getRegistryName().getPath()));
		//Containers
		GENERATEDRECIPES.add(ExtendedItems.REED_BASKET.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.REED_BASKET.get())
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
		ShapedRecipeBuilder.shaped(ExtendedBlocks.WEAPON_RACK.get())
		.pattern("X X")
		.pattern("###")
		.define('#', Ingredient.of(PLANKS))
		.define('X', Ingredient.of(STICKS))
		.unlockedBy("has_planks", has(PLANKS))
		.unlockedBy("has_"+ExtendedBlocks.WEAPON_RACK.get().getRegistryName().getPath(), has(ExtendedBlocks.WEAPON_RACK.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.WEAPON_RACK.get().getRegistryName().getPath()));
		//Dirts
		GENERATEDRECIPES.add(ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedBlocks.BARREN_DIRT.get())
		.pattern("###")
		.pattern("#X#")
		.pattern("###")
		.define('#', Ingredient.of(Blocks.DIRT.asItem()))
		.define('X', Ingredient.of(LOTRItems.SALT.get()))
		.unlockedBy("has_"+LOTRItems.SALT.get().getRegistryName().getPath(), has(LOTRItems.SALT.get()))
		.unlockedBy("has_"+ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath(), has(ExtendedBlocks.BARREN_DIRT.get()))
		.save(consumer, lotrLoc(ExtendedBlocks.BARREN_DIRT.get().getRegistryName().getPath()));
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
				()->Blocks.SMOOTH_STONE.asItem(), null);
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
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.MOSSY_DRYSTONE_STAIRS, 
				ExtendedBlocks.MOSSY_DRYSTONE_SLAB, 
				ExtendedBlocks.MOSSY_DRYSTONE_WALL, 
				()->ExtendedBlocks.MOSSY_DRYSTONE.get().asItem(), null);
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
	}

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
		GENERATEDRECIPES.add(ExtendedItems.BLACKBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.BLUEBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.CRANBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.ELDERBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.RASPBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedItems.WILDBERRY_BUSH.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BLACKBERRY_BUSH_PLANT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.BLUEBERRY_BUSH_PLANT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.CRANBERRY_BUSH_PLANT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.ELDERBERRY_BUSH_PLANT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.RASPBERRY_BUSH_PLANT.get().getRegistryName().getPath());
		GENERATEDRECIPES.add(ExtendedBlocks.WILDBERRY_BUSH_PLANT.get().getRegistryName().getPath());
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
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recipe Provider";
	}
}
