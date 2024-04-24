package com.github.maximuslotro.lotrrextended.common.datagen.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapelessRecipeBuilder;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import lotr.common.recipe.FactionBasedRecipeType;
import net.minecraft.block.Block;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCraftingHelper extends RecipeProvider {

	public static List<String> GENERATEDRECIPES = new ArrayList<>();

    public static final ITag.INamedTag<Item> STICKS = ItemTags.bind("forge:rods/wooden");
    public static final ITag.INamedTag<Item> PLANKS = ItemTags.bind("minecraft:planks");
    public static final ITag.INamedTag<Item> HORNS = ItemTags.bind("forge:horns");
    public static final ITag.INamedTag<Item> WOOL = ItemTags.bind("minecraft:wool");
    public static final ITag.INamedTag<Item> BRONZE_INGOT = ItemTags.bind("forge:ingots/bronze");
    public static final ITag.INamedTag<Item> IRON_ORE = ItemTags.bind("forge:ores/iron");
    public static final ITag.INamedTag<Item> IRON_INGOT = ItemTags.bind("forge:ingots/iron");
    
	public ExtendedCraftingHelper(DataGenerator gen) {
		super(gen);
	}

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	protected void makePillarSlabRecipe(Consumer<IFinishedRecipe> consumer,
			Supplier<? extends Block> pillarToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted,
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makePillarRecipe(consumer, pillarToBeCrafted, resource, table);
		makeSlabRecipe(consumer, slabToBeCrafted, ()->pillarToBeCrafted.get().asItem(), table);
	}
	protected void make4x4BlockandDepandentsStairSlabWallRecipe(Consumer<IFinishedRecipe> consumer,
			Supplier<? extends Block> blockToBeCrafted,
			Supplier<? extends Block> stairsToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted,
			Supplier<? extends Block> wallToBeCrafted, 
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		make2x2BlockRecipe(consumer, blockToBeCrafted, resource, table);
		makeStairRecipe(consumer, stairsToBeCrafted, ()->blockToBeCrafted.get().asItem(), table);
		makeSlabRecipe(consumer, slabToBeCrafted, ()->blockToBeCrafted.get().asItem(), table);
		makeWallRecipe(consumer, wallToBeCrafted, ()->blockToBeCrafted.get().asItem(), table);
	}
	protected void makeStairSlabWallRecipe(Consumer<IFinishedRecipe> consumer, 
			Supplier<? extends Block> stairsToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted,
			Supplier<? extends Block> wallToBeCrafted, 
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeStairRecipe(consumer, stairsToBeCrafted, resource, table);
		makeSlabRecipe(consumer, slabToBeCrafted, resource, table);
		makeWallRecipe(consumer, wallToBeCrafted, resource, table);
	}
	protected void makeStairSlabRecipe(Consumer<IFinishedRecipe> consumer, 
			Supplier<? extends Block> stairsToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted, 
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeStairRecipe(consumer, stairsToBeCrafted, resource, table);
		makeSlabRecipe(consumer, slabToBeCrafted, resource, table);
	}

	protected void makeBoatRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> boatOnject, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = boatOnject.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(boatOnject.get(), 4)
			.pattern("# #")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(boatOnject.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(boatOnject.get(), 4, table)
			.pattern("# #")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(boatOnject.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makePressurePlateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> pressurePlate, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = pressurePlate.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(pressurePlate.get())
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(pressurePlate.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(pressurePlate.get(), table)
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(pressurePlate.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeTrapdoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> trapdoor, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = trapdoor.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(trapdoor.get(), 2)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(trapdoor.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(trapdoor.get(), 2, table)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(trapdoor.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeBedRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> bed, Item reasource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = bed.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(bed.get())
			.pattern("###")
			.pattern("XXX")
			.define('#', Ingredient.of(reasource))
			.define('X', PLANKS)
			.unlockedBy("has_"+name, has(bed.get()))
			.unlockedBy("has_"+reasource.getRegistryName().getPath(), has(reasource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(bed.get(), table)
			.pattern("###")
			.pattern("XXX")
			.define('#', Ingredient.of(reasource))
			.define('X', PLANKS)
			.unlockedBy("has_"+name, has(bed.get()))
			.unlockedBy("has_"+reasource.getRegistryName().getPath(), has(reasource))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeBedRecipeTag(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> bed, ITag<Item> reasource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = bed.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(bed.get())
			.pattern("###")
			.pattern("XXX")
			.define('#', reasource)
			.define('X', PLANKS)
			.unlockedBy("has_"+name, has(bed.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(bed.get(), table)
			.pattern("###")
			.pattern("XXX")
			.define('#', reasource)
			.define('X', PLANKS)
			.unlockedBy("has_"+name, has(bed.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeDoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> door, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = door.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(door.get(), 3)
			.pattern("##")
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(door.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(door.get(), 3, table)
			.pattern("##")
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(door.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeButtonRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> button, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = button.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(button.get())
			.requires(resource.get())
			.unlockedBy("has_"+name, has(button.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(button.get(), table)
			.requires(resource.get())
			.unlockedBy("has_"+name, has(button.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makePlanksRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> planks, Supplier<? extends Block> log, ITag<Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = planks.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(planks.get(), 4)
			.requires(resource)
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+log.get().getRegistryName().getPath(), has(resource))
			.group("planks")
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 4, table)
			.requires(resource)
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+log.get().getRegistryName().getPath(), has(resource))
			.group("planks")
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makePlanksFromBlocksRecipe(Consumer<IFinishedRecipe> consumer, 
			Supplier<? extends Item> planks, 
			Supplier<? extends Item> log_slab,
			Supplier<? extends Item> stripped_log_slab,
			Supplier<? extends Item> branch,
			Supplier<? extends Item> stripped_branch,
			Supplier<? extends Item> wood_slab,
			Supplier<? extends Item> stripped_wood_slab,
			Supplier<? extends Item> wood_stairs,
			Supplier<? extends Item> stripped_wood_stairs, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = planks.get().getRegistryName().getPath();
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(planks.get(), 2)
			.requires(branch.get())
			.requires(stripped_branch.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+branch.get().getRegistryName().getPath(), has(branch.get()))
			.unlockedBy("has_"+stripped_branch.get().getRegistryName().getPath(), has(stripped_branch.get()))
			.save(consumer, lotrLoc(name+"_from_"+branch.get().getRegistryName().getPath()));
			ShapelessRecipeBuilder.shapeless(planks.get(), 2)
			.requires(log_slab.get())
			.requires(stripped_log_slab.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
			ShapelessRecipeBuilder.shapeless(planks.get(), 2)
			.requires(wood_slab.get())
			.requires(stripped_wood_slab.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+wood_slab.get().getRegistryName().getPath(), has(wood_slab.get()))
			.unlockedBy("has_"+stripped_wood_slab.get().getRegistryName().getPath(), has(stripped_wood_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_slab.get().getRegistryName().getPath()));
			ShapelessRecipeBuilder.shapeless(planks.get(), 3)
			.requires(wood_stairs.get())
			.requires(stripped_wood_stairs.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+wood_stairs.get().getRegistryName().getPath(), has(wood_stairs.get()))
			.unlockedBy("has_"+stripped_wood_stairs.get().getRegistryName().getPath(), has(stripped_wood_stairs.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_stairs.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.requires(branch.get())
			.requires(stripped_branch.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+branch.get().getRegistryName().getPath(), has(branch.get()))
			.unlockedBy("has_"+stripped_branch.get().getRegistryName().getPath(), has(stripped_branch.get()))
			.save(consumer, lotrLoc(name+"_from_"+branch.get().getRegistryName().getPath()));
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.requires(log_slab.get())
			.requires(stripped_log_slab.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.requires(wood_slab.get())
			.requires(stripped_wood_slab.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+wood_slab.get().getRegistryName().getPath(), has(wood_slab.get()))
			.unlockedBy("has_"+stripped_wood_slab.get().getRegistryName().getPath(), has(stripped_wood_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_slab.get().getRegistryName().getPath()));
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 3, table)
			.requires(wood_stairs.get())
			.requires(stripped_wood_stairs.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+wood_stairs.get().getRegistryName().getPath(), has(wood_stairs.get()))
			.unlockedBy("has_"+stripped_wood_stairs.get().getRegistryName().getPath(), has(stripped_wood_stairs.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_stairs.get().getRegistryName().getPath()));
		}
	}

	protected void makePlanksFromBlocksRecipeNoBranchWood(Consumer<IFinishedRecipe> consumer, 
			Supplier<? extends Item> planks, 
			Supplier<? extends Item> log_slab,
			Supplier<? extends Item> stripped_log_slab, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = planks.get().getRegistryName().getPath();
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(planks.get(), 2)
			.requires(log_slab.get())
			.requires(stripped_log_slab.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.requires(log_slab.get())
			.requires(stripped_log_slab.get())
			.unlockedBy("has_"+name, has(planks.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
		}
	}


	protected void makeFenceAndFenceGateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> fence, Supplier<? extends Block> gate, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String fenceName = fence.get().getRegistryName().getPath();
		String gateName = gate.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(fenceName);
		GENERATEDRECIPES.add(gateName);
		if(table==null) {
			ShapedRecipeBuilder.shaped(fence.get(), 3)
			.pattern("#I#")
			.pattern("#I#")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+fenceName, has(fence.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(fenceName));
			ShapedRecipeBuilder.shaped(gate.get())
			.pattern("I#I")
			.pattern("I#I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+gateName, has(gate.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(gateName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(fence.get(), 3, table)
			.pattern("#I#")
			.pattern("#I#")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+fenceName, has(fence.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(fenceName));
			ExtendedFactionShapedRecipeBuilder.shaped(gate.get(), table)
			.pattern("I#I")
			.pattern("I#I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+gateName, has(gate.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(gateName));
		}
	}

	protected void makeSignRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> sign, Supplier<? extends Block> wallSign, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String signName = sign.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(signName);
		GENERATEDRECIPES.add(wallSign.get().getRegistryName().getPath());
		if(table==null) {
			ShapedRecipeBuilder.shaped(sign.get(), 3)
			.pattern("###")
			.pattern("###")
			.pattern(" I ")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+signName, has(sign.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(signName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(sign.get(), 3, table)
			.pattern("###")
			.pattern("###")
			.pattern(" I ")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+signName, has(sign.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(signName));
		}
	}

	protected void makeWoodRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 3)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(objectToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 3, table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(objectToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void make2x2BlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 4)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(objectToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 4, table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(objectToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void make3x3BlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		Supplier<? extends Item> item = ()->objectToBeCrafted.get().asItem();
		make3x3ObjectRecipe(consumer, item, resource, table);
	}
	protected void make3x3ObjectRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> objectToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 9)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(objectToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 9, table)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(objectToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeCrackedBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		CookingRecipeBuilder.smelting(
				Ingredient.of(resource.get()), 
				blockToBeCrafted.get(), 0.1F, 200)
		.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
		.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
		.save(consumer, lotrLoc(name));
	}

	protected void makeMossyBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(blockToBeCrafted.get())
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(()->Items.VINE)))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(blockToBeCrafted.get(), table)
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(()->Items.VINE)))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makePillarRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 3)
			.pattern("#")
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 3, table)
			.pattern("#")
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeCraftingTableRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("##")
			.pattern("XX")
			.define('#', Ingredient.of(ItemTags.PLANKS))
			.define('X', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("##")
			.pattern("XX")
			.define('#', Ingredient.of(ItemTags.PLANKS))
			.define('X', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeTorchRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		GENERATEDRECIPES.add(name+"_wall");
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
			.pattern("#")
			.pattern("I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4, table)
			.pattern("#")
			.pattern("I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeStairRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
			.pattern("  #")
			.pattern(" ##")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4, table)
			.pattern("  #")
			.pattern(" ##")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeFallenLeavesRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 16)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 16, table)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeWallRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6, table)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeSlabRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6, table)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	//ARMOR
	protected void makeArmorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> helmetToBeCrafted,
			Supplier<? extends Item> chestplateToBeCrafted,
			Supplier<? extends Item> leggingsToBeCrafted,
			Supplier<? extends Item> bootsToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeHelmetRecipe(consumer, helmetToBeCrafted, resource, table);
		makeChestplateRecipe(consumer, chestplateToBeCrafted, resource, table);
		makeLeggingsRecipe(consumer, leggingsToBeCrafted, resource, table);
		makeBootsRecipe(consumer, bootsToBeCrafted, resource, table);
	}
	protected void makeHelmetRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}

	}
	protected void makeChestplateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("# #")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("# #")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeLeggingsRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeBootsRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	//TOOLS+SWORDS
	protected void makeSwordRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeSpearRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("  #")
			.pattern(" X ")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + name, has(itemToBeCrafted.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(),has(resource.get()))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("  #")
			.pattern(" X ")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + name, has(itemToBeCrafted.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeDaggerRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + name, has(itemToBeCrafted.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(),has(resource.get()))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + name, has(itemToBeCrafted.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeToolsRecipe(Consumer<IFinishedRecipe> consumer, 
			Supplier<? extends Item> pickaxeToBeCrafted,
			Supplier<? extends Item> shovelToBeCrafted,
			Supplier<? extends Item> axeToBeCrafted,
			Supplier<? extends Item> hoeToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makePickaxeRecipe(consumer, pickaxeToBeCrafted, resource, table);
		makeShovelRecipe(consumer, shovelToBeCrafted, resource, table);
		makeAxeRecipe(consumer, axeToBeCrafted, resource, table);
		makeHoeRecipe(consumer, hoeToBeCrafted, resource, table);
	}
	protected void makePickaxeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern(" X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern(" X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeShovelRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("X")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("X")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeAxeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("##")
			.pattern("#X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("##")
			.pattern("#X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeHoeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("##")
			.pattern(" X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("##")
			.pattern(" X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(itemToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeStorageBlock3x3ToItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> item, Supplier<? extends Block> block, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = item.get().getRegistryName().getPath();
		String name2 = block.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(item.get(), 9)
			.requires(block.get())
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ShapedRecipeBuilder.shaped(block.get())
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(item.get(), 9, table)
			.requires(block.get())
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedFactionShapedRecipeBuilder.shaped(block.get(), table)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}

	protected void makeStorageBlock2x2ToItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> item, Supplier<? extends Block> block, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = item.get().getRegistryName().getPath();
		String name2 = block.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(item.get(), 4)
			.requires(block.get())
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ShapedRecipeBuilder.shaped(block.get())
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(item.get(), 4, table)
			.requires(block.get())
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedFactionShapedRecipeBuilder.shaped(block.get(), table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}

	protected void makeNuggetToIngotAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> ingot, Supplier<? extends Item> nugget, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = ingot.get().getRegistryName().getPath();
		String name2 = nugget.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(nugget.get(), 9)
			.requires(ingot.get())
			.unlockedBy("has_"+name1, has(ingot.get()))
			.unlockedBy("has_"+name2, has(nugget.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ShapedRecipeBuilder.shaped(ingot.get())
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(nugget.get()))
			.unlockedBy("has_"+name1, has(nugget.get()))
			.unlockedBy("has_"+name2, has(ingot.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(nugget.get(), 9, table)
			.requires(ingot.get())
			.unlockedBy("has_"+name1, has(nugget.get()))
			.unlockedBy("has_"+name2, has(ingot.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedFactionShapedRecipeBuilder.shaped(ingot.get(), table)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(nugget.get()))
			.unlockedBy("has_"+name1, has(nugget.get()))
			.unlockedBy("has_"+name2, has(ingot.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}


	protected void makeStoneCutterRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> ing, Supplier<? extends Block> output) {
		makeStoneCutterRecipe(consumer, ing, output, 1);
	}
	protected void makeStoneCutterRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> ing, Supplier<? extends Block> output, int num) {
		String name1 = ing.get().getRegistryName().getPath();
		String name2 = output.get().getRegistryName().getPath();
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(ing.get()), output.get(), num).unlocks("has_"+name1, has(ing.get())).save(consumer, name2+"_from_"+name1);
	}

	public void checkForMissingRecipes() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof AxialSlabBlock) {
				if(!GENERATEDRECIPES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SLAB RECIPE FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				if(!GENERATEDRECIPES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING STAIR RECIPE FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				if(!GENERATEDRECIPES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING WALL RECIPE FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRPillarBlock) {
				if(!GENERATEDRECIPES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING PILLAR RECIPE FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(!GENERATEDRECIPES.contains(block.getValue().get().getRegistryName().getPath())) {
				System.out.println("MISSING BLOCK RECIPE FOR: "+block.getValue().get().getRegistryName().toString());
			}
		}
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if(item.getValue().get() instanceof LOTRArmorItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING ARMOR RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRSwordItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SWORD RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRPickaxeItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING PICKAXE RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRShovelItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SHOVEL RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRAxeItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING AXE RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRHoeItem ) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING HOE RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
				System.out.println("MISSING ITEM RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
			}
		}
	}
}
