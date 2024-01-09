package lotr.common.datagen.crafting;

import java.util.function.Consumer;

import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.init.ExtendedRecipes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
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
		addOverrides();
		checkForMissingRecipes();
	}

	private void buildRecipes(Consumer<IFinishedRecipe> consumer) {
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
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.BLACK_PACKED_FUR_STAIRS, 
				ExtendedBlocks.BLACK_PACKED_FUR_SLAB, 
				ExtendedBlocks.BLACK_PACKED_FUR_WALL, 
				()->ExtendedBlocks.BLACK_PACKED_FUR.get().asItem(), null);
		makeStorageBlock3x3ToItemAndBackRecipe(consumer, ExtendedItems.GREY_FUR, ExtendedBlocks.GREY_PACKED_FUR, null);
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.GREY_PACKED_FUR_STAIRS, 
				ExtendedBlocks.GREY_PACKED_FUR_SLAB, 
				ExtendedBlocks.GREY_PACKED_FUR_WALL, 
				()->ExtendedBlocks.GREY_PACKED_FUR.get().asItem(), null);
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
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.GREY_FUR, ExtendedBlocks.GREY_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.SILVER_FUR, ExtendedBlocks.SILVER_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);
		makeStorageBlock2x2ToItemAndBackRecipe(consumer, ExtendedItems.WHITE_FUR, ExtendedBlocks.WHITE_FUR_BUNDLE, LOTRRecipes.LOSSOTH_CRAFTING);

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
		GENERATEDRECIPES.add(ExtendedItems.TAURETHRIM_SWORD.get().getRegistryName().getPath());
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
		//RHUNIC
		GENERATEDRECIPES.add(ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_BRICK.get(), 4, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(Blocks.STONE))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_BRICK.get()))
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath()+"_1"));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_BRICK.get(), 4, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(Blocks.SANDSTONE))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_BRICK.get()))
		.unlockedBy("has_"+Blocks.SANDSTONE.getRegistryName().getPath(), has(Blocks.SANDSTONE))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_BRICK.get().getRegistryName().getPath()+"_2"));
		makeStairSlabWallRecipe(consumer, ExtendedBlocks.RHUNIC_BRICK_STAIRS, 
				ExtendedBlocks.RHUNIC_BRICK_SLAB, 
				ExtendedBlocks.RHUNIC_BRICK_WALL, 
				()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		make4x4BlockRecipe(consumer, ExtendedBlocks.CARVED_RHUNIC_BRICK, 
				()->ExtendedBlocks.RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		GENERATEDRECIPES.add(ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_PILLAR.get(), 3, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(Blocks.STONE))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_PILLAR.get()))
		.unlockedBy("has_"+Blocks.STONE.getRegistryName().getPath(), has(Blocks.STONE))
		.save(consumer, lotrLoc(ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath()+"_1"));
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedBlocks.RHUNIC_PILLAR.get(), 3, ExtendedRecipes.RHUNIC_CRAFTING)
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(Blocks.SANDSTONE))
		.unlockedBy("has_"+ExtendedBlocks.RHUNIC_PILLAR.get().getRegistryName().getPath(), has(ExtendedBlocks.RHUNIC_PILLAR.get()))
		.unlockedBy("has_"+Blocks.SANDSTONE.getRegistryName().getPath(), has(Blocks.SANDSTONE))
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
		make4x4BlockRecipe(consumer, ExtendedBlocks.CARVED_RED_RHUNIC_BRICK, 
				()->ExtendedBlocks.RED_RHUNIC_BRICK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
		makePillarSlabRecipe(consumer, ExtendedBlocks.RED_RHUNIC_PILLAR, 
				ExtendedBlocks.RED_RHUNIC_PILLAR_SLAB, 
				()->LOTRBlocks.RED_ROCK.get().asItem(), ExtendedRecipes.RHUNIC_CRAFTING);
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
		//ITEMS
		GENERATEDRECIPES.add(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(ExtendedItems.SILVER_COIN_ONE.get(), 4)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(LOTRItems.SILVER_NUGGET.get()))
		.unlockedBy("has_"+ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath(), has(ExtendedItems.SILVER_COIN_ONE.get()))
		.unlockedBy("has_"+LOTRItems.SILVER_NUGGET.get().getRegistryName().getPath(), has(LOTRItems.SILVER_NUGGET.get()))
		.save(consumer, lotrLoc(ExtendedItems.SILVER_COIN_ONE.get().getRegistryName().getPath()));
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
	}
	@Override
	public String getName() {
		return "Renewed Extended Crafting Recipe Provider";
	}
}
