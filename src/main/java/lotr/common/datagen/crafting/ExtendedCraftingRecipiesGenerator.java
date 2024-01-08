package lotr.common.datagen.crafting;

import java.util.function.Consumer;

import lotr.common.init.ExtendedItems;
import lotr.common.init.ExtendedRecipes;
import lotr.common.init.LOTRItems;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;

public class ExtendedCraftingRecipiesGenerator extends ExtendedCraftingHelper {
	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		//there are no recipes for leather hats
		GENERATEDITEMRECPIES.add(ExtendedItems.LEATHER_HAT.get().getRegistryName().getPath());
		//MORGUL
		makeArmorRecpie(consumer, ExtendedItems.MORGUL_HELMET, 
				ExtendedItems.MORGUL_CHESTPLATE, 
				ExtendedItems.MORGUL_LEGGINGS, 
				ExtendedItems.MORGUL_BOOTS, 
				ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		makeSwordRecpie(consumer, ExtendedItems.MORGUL_SWORD, ExtendedItems.MORGUL_STEEL_INGOT, LOTRRecipes.ANY_ORC);
		//DOL GULDUR
		makeArmorRecpie(consumer, ExtendedItems.DOL_GULDUR_HELMET, 
				ExtendedItems.DOL_GULDUR_CHESTPLATE, 
				ExtendedItems.DOL_GULDUR_LEGGINGS, 
				ExtendedItems.DOL_GULDUR_BOOTS, 
				LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeToolsRecpie(consumer, ExtendedItems.DOL_GULDUR_PICKAXE, 
				ExtendedItems.DOL_GULDUR_SHOVEL, 
				ExtendedItems.DOL_GULDUR_AXE, 
				ExtendedItems.DOL_GULDUR_HOE, 
				LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		makeSwordRecpie(consumer, ExtendedItems.DOL_GULDUR_SWORD, LOTRItems.ORC_STEEL_INGOT, ExtendedRecipes.DOL_GULDUR_CRAFTING);
		//ANGMAR
		makeArmorRecpie(consumer, ExtendedItems.ANGMAR_HELMET, 
				ExtendedItems.ANGMAR_CHESTPLATE, 
				ExtendedItems.ANGMAR_LEGGINGS, 
				ExtendedItems.ANGMAR_BOOTS, 
				LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		makeToolsRecpie(consumer, ExtendedItems.ANGMAR_PICKAXE, 
				ExtendedItems.ANGMAR_SHOVEL, 
				ExtendedItems.ANGMAR_AXE, 
				ExtendedItems.ANGMAR_HOE, 
				LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		makeSwordRecpie(consumer, ExtendedItems.ANGMAR_SWORD, LOTRItems.ORC_STEEL_INGOT, LOTRRecipes.ANGMAR_CRAFTING);
		//TAURETHRIM
		//there are no recipes for golden armor
		GENERATEDITEMRECPIES.add(ExtendedItems.GOLDEN_TAURETHRIM_HELMET.get().getRegistryName().getPath());
		GENERATEDITEMRECPIES.add(ExtendedItems.GOLDEN_TAURETHRIM_CHESTPLATE.get().getRegistryName().getPath());
		GENERATEDITEMRECPIES.add(ExtendedItems.GOLDEN_TAURETHRIM_LEGGINGS.get().getRegistryName().getPath());
		GENERATEDITEMRECPIES.add(ExtendedItems.GOLDEN_TAURETHRIM_BOOTS.get().getRegistryName().getPath());
		makeArmorRecpie(consumer, ExtendedItems.TAURETHRIM_HELMET, 
				ExtendedItems.TAURETHRIM_CHESTPLATE, 
				ExtendedItems.TAURETHRIM_LEGGINGS, 
				ExtendedItems.TAURETHRIM_BOOTS, 
				LOTRItems.BRONZE_INGOT, ExtendedRecipes.TAURETHRIM_CRAFTING);
		GENERATEDITEMRECPIES.add(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get(), ExtendedRecipes.TAURETHRIM_CRAFTING)
		.pattern("Y")
		.pattern("#")
		.define('Y', Ingredient.of(LOTRItems.FLAME_OF_HARAD.get()))
		.define('#', Ingredient.of(ExtendedItems.TAURETHRIM_HELMET.get()))
		.unlockedBy("has_"+LOTRItems.FLAME_OF_HARAD.get().getRegistryName().getPath(), has(LOTRItems.FLAME_OF_HARAD.get()))
		.unlockedBy("has_"+ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath(), has(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get()))
		.save(consumer, lotrLoc(ExtendedItems.TAURETHRIM_CHIEFTAIN_HELMET.get().getRegistryName().getPath()));
		makeToolsRecpie(consumer, ExtendedItems.TAURETHRIM_PICKAXE, 
				ExtendedItems.TAURETHRIM_SHOVEL, 
				ExtendedItems.TAURETHRIM_AXE, 
				ExtendedItems.TAURETHRIM_HOE, 
				ExtendedItems.OBSIDIAN_SHARD, ExtendedRecipes.TAURETHRIM_CRAFTING);
		GENERATEDITEMRECPIES.add(ExtendedItems.TAURETHRIM_SWORD.get().getRegistryName().getPath());
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
				
		checkForMissingRecpies();
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recpie Provider";
	}
}
