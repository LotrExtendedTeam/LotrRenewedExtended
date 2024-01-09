package lotr.common.datagen.crafting;

import java.util.function.Consumer;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.ExtendedRecipes;
import lotr.common.init.LOTRItems;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;

public class ExtendedCraftingRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		buildRecipes(consumer);
		checkForMissingRecipes();
	}

	protected void buildRecipes(Consumer<IFinishedRecipe> consumer) {
		//there are no recipes for leather hats
		GENERATEDITEMRECIPES.add(ExtendedItems.LEATHER_HAT.get().getRegistryName().getPath());
		//MORGUL
		makeArmorRecipe(consumer, ExtendedItems.MORGUL_HELMET, 
				ExtendedItems.MORGUL_CHESTPLATE, 
				ExtendedItems.MORGUL_LEGGINGS, 
				ExtendedItems.MORGUL_BOOTS, 
				ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeSwordRecipe(consumer, ExtendedItems.MORGUL_SWORD, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_ORC);
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
		make4x4BlockandDepandentsStairSlabWallRecipe(consumer, ExtendedBlocks.DOL_GULDUR_BRICK, 
				ExtendedBlocks.DOL_GULDUR_BRICK_STAIRS, 
				ExtendedBlocks.DOL_GULDUR_BRICK_SLAB, 
				ExtendedBlocks.DOL_GULDUR_BRICK_WALL, 
				()->Blocks.STONE.asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.DOL_GULDUR_PILLAR, 
				ExtendedBlocks.DOL_GULDUR_PILLAR_SLAB, 
				()->Blocks.STONE.asItem(), ExtendedRecipes.DOL_GULDUR_CRAFTING);
		make4x4BlockRecipe(consumer, ExtendedBlocks.CARVED_DOL_GULDUR_BRICK, 
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
		//TAURETHRIM
		//there are no recipes for golden armor
		GENERATEDITEMRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_HELMET.get().getRegistryName().getPath());
		GENERATEDITEMRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE.get().getRegistryName().getPath());
		GENERATEDITEMRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS.get().getRegistryName().getPath());
		GENERATEDITEMRECIPES.add(ExtendedItems.GOLDEN_TAURETHRIM_BOOTS.get().getRegistryName().getPath());
		makeArmorRecipe(consumer, ExtendedItems.TAURETHRIM_HELMET, 
				ExtendedItems.TAURETHRIM_CHESTPLATE, 
				ExtendedItems.TAURETHRIM_LEGGINGS, 
				ExtendedItems.TAURETHRIM_BOOTS, 
				LOTRItems.BRONZE_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING);
		GENERATEDITEMRECIPES.add(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath());
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
		GENERATEDITEMRECIPES.add(ExtendedItems.TAURETHRIM_SWORD.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.TAURETHRIM_SWORD.get(), ExtendedRecipes.TAURETHRIM_CRAFTING)
		.pattern("#Z#")
		.pattern("#Z#")
		.pattern(" Y ")
		.define('Z', Ingredient.of(ItemTags.PLANKS))
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
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recipe Provider";
	}
}
