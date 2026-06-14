package com.github.maximuslotro.lotrrextended.common.datagen.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedCrucibleRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedFactionShapelessRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedMillingRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedShapedRecipeBuilder;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedSwordItem;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeSerializers;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.recipe.FactionBasedRecipeType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCraftingHelper extends RecipeProvider {

	public List<String> GENERATEDRECIPES = new ArrayList<>();

    public static final ITag.INamedTag<Item> LOGS = ItemTags.LOGS;
    public static final ITag.INamedTag<Item> STICKS = Tags.Items.RODS_WOODEN;
    public static final ITag.INamedTag<Item> PLANKS = ItemTags.PLANKS;
    public static final ITag.INamedTag<Item> HORNS = ItemTags.bind("forge:horns");

	public ExtendedCraftingHelper(DataGenerator gen) {
		super(gen);
	}

	public ResourceLocation mcLoc(String name) {
        return new ResourceLocation(name);
    }

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	public ResourceLocation extendedLoc(String name) {
        return new ResourceLocation("lotrextended", name);
    }

	protected void makeBeamSlabRecipe(Consumer<IFinishedRecipe> consumer,
		Supplier<? extends Block> pillarToBeCrafted,
		Supplier<? extends Block> slabToBeCrafted,
		Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeBeamRecipe(consumer, pillarToBeCrafted, resource, table);
		makeSlabRecipe(consumer, slabToBeCrafted, ()->pillarToBeCrafted.get().asItem(), table, "");
	}
	protected void makePillarSlabRecipe(Consumer<IFinishedRecipe> consumer,
		Supplier<? extends Block> pillarToBeCrafted,
		Supplier<? extends Block> slabToBeCrafted,
		Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makePillarRecipe(consumer, pillarToBeCrafted, resource, table);
		makeSlabRecipe(consumer, slabToBeCrafted, ()->pillarToBeCrafted.get().asItem(), table, "");
	}
	protected void make4x4BlockandDepandentsStairSlabWallRecipe(Consumer<IFinishedRecipe> consumer,
			Supplier<? extends Block> blockToBeCrafted,
			Supplier<? extends Block> stairsToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted,
			Supplier<? extends Block> wallToBeCrafted,
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String path) {
		make2x2BlockRecipe(consumer, blockToBeCrafted, resource, table, path);
		makeStairRecipe(consumer, stairsToBeCrafted, ()->blockToBeCrafted.get().asItem(), table, path);
		makeSlabRecipe(consumer, slabToBeCrafted, ()->blockToBeCrafted.get().asItem(), table, path);
		makeWallRecipe(consumer, wallToBeCrafted, ()->blockToBeCrafted.get().asItem(), table, "", path);
	}
	protected void makeStairSlabWallRecipe(Consumer<IFinishedRecipe> consumer,
			Supplier<? extends Block> stairsToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted,
			Supplier<? extends Block> wallToBeCrafted,
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeStairRecipe(consumer, stairsToBeCrafted, resource, table, "");
		makeSlabRecipe(consumer, slabToBeCrafted, resource, table, "");
		makeWallRecipe(consumer, wallToBeCrafted, resource, table);
	}
	protected void makeStairSlabRecipe(Consumer<IFinishedRecipe> consumer,
			Supplier<? extends Block> stairsToBeCrafted,
			Supplier<? extends Block> slabToBeCrafted,
			Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeStairRecipe(consumer, stairsToBeCrafted, resource, table, "");
		makeSlabRecipe(consumer, slabToBeCrafted, resource, table, "");
	}

	protected void makeBoatRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> boatOnject, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = boatOnject.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(boatOnject.get())
			.group("boat")
			.pattern("# #")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(boatOnject.get(), table)
			.group("boat")
			.pattern("# #")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeWoodenPressurePlateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> pressurePlate, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makePressurePlateRecipe(consumer, pressurePlate, resource, table, "wooden_pressure_plate");
	}
	protected void makePressurePlateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> pressurePlate, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makePressurePlateRecipe(consumer, pressurePlate, resource, table, "");
	}
	private void makePressurePlateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> pressurePlate, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String group) {
		String name = pressurePlate.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(pressurePlate.get())
			.group(group)
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(pressurePlate.get(), table)
			.group(group)
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeWoodenTrapdoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> trapdoor, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeTrapdoorRecipe(consumer, trapdoor, resource, table, "wooden_trapdoor");
	}
	protected void makeTrapdoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> trapdoor, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeTrapdoorRecipe(consumer, trapdoor, resource, table, "");
	}
	private void makeTrapdoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> trapdoor, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String group) {
		String name = trapdoor.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(trapdoor.get(), 2)
			.group(group)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(trapdoor.get(), 2, table)
			.group(group)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeMetalTrapdoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> trapdoor, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = trapdoor.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(trapdoor.get(), 2)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(trapdoor.get(), 2, table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeMetalBarRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> trapdoor, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = trapdoor.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(trapdoor.get(), 2)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(trapdoor.get(), 2, table)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeChainRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> chain, Supplier<? extends Item> ingot, Supplier<? extends Item> nugget, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = chain.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(chain.get())
			.pattern("N")
			.pattern("#")
			.pattern("N")
			.define('#', Ingredient.of(ingot.get()))
			.define('N', Ingredient.of(nugget.get()))
			.unlockedBy("has_"+nugget.get().getRegistryName().getPath(), has(nugget.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(chain.get(), table)
			.pattern("N")
			.pattern("#")
			.pattern("N")
			.define('#', Ingredient.of(ingot.get()))
			.define('N', Ingredient.of(nugget.get()))
			.unlockedBy("has_"+nugget.get().getRegistryName().getPath(), has(nugget.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeCrystalChandelierRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> chandelier, Supplier<? extends Item> crystal, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(chandelier.get().getRegistryName().getPath());
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(chandelier.get())
			.pattern(" I ")
			.pattern("E#E")
			.pattern(" E ")
			.define('I', Ingredient.of(ExtendedTags.Items.CHAINS))
			.define('#', Ingredient.of(Blocks.COBBLESTONE))
			.define('E', Ingredient.of(crystal.get()))
			.unlockedBy("has_"+crystal.get().getRegistryName().getPath(), has(crystal.get()))
			.save(consumer, lotrLoc(chandelier.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(chandelier.get(), table)
			.pattern(" I ")
			.pattern("E#E")
			.pattern(" E ")
			.define('I', Ingredient.of(ExtendedTags.Items.CHAINS))
			.define('#', Ingredient.of(Blocks.COBBLESTONE))
			.define('E', Ingredient.of(crystal.get()))
			.unlockedBy("has_"+crystal.get().getRegistryName().getPath(), has(crystal.get()))
			.save(consumer, lotrLoc(chandelier.get().getRegistryName().getPath()));
		}
	}

	protected void makeBedRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> bed, ITag<Item> tag, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = bed.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(bed.get())
			.pattern("###")
			.pattern("XXX")
			.define('#', Ingredient.of(tag))
			.define('X', PLANKS)
			.unlockedBy("has_planks", has(PLANKS))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(bed.get(), table)
			.pattern("###")
			.pattern("XXX")
			.define('#', Ingredient.of(tag))
			.define('X', PLANKS)
			.unlockedBy("has_planks", has(PLANKS))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeBedRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> bed, Item reasource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = bed.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(bed.get())
			.pattern("###")
			.pattern("XXX")
			.define('#', Ingredient.of(reasource))
			.define('X', PLANKS)
			.unlockedBy("has_"+reasource.getRegistryName().getPath(), has(reasource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(bed.get(), table)
			.pattern("###")
			.pattern("XXX")
			.define('#', Ingredient.of(reasource))
			.define('X', PLANKS)
			.unlockedBy("has_"+reasource.getRegistryName().getPath(), has(reasource))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeBedRecipeTag(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> bed, ITag<Item> reasource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = bed.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(bed.get())
			.pattern("###")
			.pattern("XXX")
			.define('#', reasource)
			.define('X', PLANKS)
			.unlockedBy("has_planks", has(PLANKS))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(bed.get(), table)
			.pattern("###")
			.pattern("XXX")
			.define('#', reasource)
			.define('X', PLANKS)
			.unlockedBy("has_planks", has(PLANKS))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeCarpetRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> carpet, Supplier<? extends Item> reasource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = carpet.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(carpet.get())
			.pattern("##")
			.define('#', Ingredient.of(reasource.get()))
			.unlockedBy("has_"+reasource.get().getRegistryName().getPath(), has(reasource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(carpet.get(), table)
			.pattern("##")
			.define('#', Ingredient.of(reasource.get()))
			.unlockedBy("has_"+reasource.get().getRegistryName().getPath(), has(reasource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeShieldRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> shield, ITag<Item> reasource, String type, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(shield.get().getRegistryName().getPath());
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(shield.get())
			.pattern("PIP")
			.pattern("PPP")
			.pattern(" P ")
			.define('I', Ingredient.of(reasource))
			.define('P', Ingredient.of(PLANKS))
			.unlockedBy("has_"+type, has(reasource))
			.save(consumer, lotrLoc(shield.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(shield.get(), table)
			.pattern("PIP")
			.pattern("PPP")
			.pattern(" P ")
			.define('I', Ingredient.of(reasource))
			.define('P', Ingredient.of(PLANKS))
			.unlockedBy("has_"+type, has(reasource))
			.save(consumer, lotrLoc(shield.get().getRegistryName().getPath()));
		}
	}

	protected void makeBoarArmorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> armor, ITag<Item> reasource, String type, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(armor.get().getRegistryName().getPath());
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(armor.get())
			.pattern("  M")
			.pattern("MLM")
			.pattern("M M")
			.define('M', Ingredient.of(reasource))
			.define('L', Ingredient.of(Tags.Items.LEATHER))
			.unlockedBy("has_"+type, has(reasource))
			.save(consumer, lotrLoc(armor.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(armor.get(), table)
			.pattern("  M")
			.pattern("MLM")
			.pattern("M M")
			.define('M', Ingredient.of(reasource))
			.define('L', Ingredient.of(Tags.Items.LEATHER))
			.unlockedBy("has_"+type, has(reasource))
			.save(consumer, lotrLoc(armor.get().getRegistryName().getPath()));
		}
	}

	protected void makeWargArmorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> armor, ITag<Item> reasource, String type, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(armor.get().getRegistryName().getPath());
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(armor.get())
			.pattern("  M")
			.pattern("MLM")
			.pattern("MMM")
			.define('M', Ingredient.of(reasource))
			.define('L', Ingredient.of(Tags.Items.LEATHER))
			.unlockedBy("has_"+type, has(reasource))
			.save(consumer, lotrLoc(armor.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(armor.get(), table)
			.pattern("  M")
			.pattern("MLM")
			.pattern("MMM")
			.define('M', Ingredient.of(reasource))
			.define('L', Ingredient.of(Tags.Items.LEATHER))
			.unlockedBy("has_"+type, has(reasource))
			.save(consumer, lotrLoc(armor.get().getRegistryName().getPath()));
		}
	}

	protected void makeBannerAndPatternRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> banner, Supplier<? extends Item> pattern, FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(banner.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(banner.get(), table)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(ExtendedTags.Items.WOOL_BLOCKS))
		.define('S', Ingredient.of(STICKS))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_wool", has(ExtendedTags.Items.WOOL_BLOCKS))
		.save(consumer, lotrLoc(banner.get().getRegistryName().getPath()));
		GENERATEDRECIPES.add(pattern.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(pattern.get(), table)
		.requires(Items.PAPER)
		.requires(banner.get())
		.unlockedBy("has_"+banner.get().getRegistryName().getPath(), has(banner.get()))
		.save(consumer, lotrLoc(pattern.get().getRegistryName().getPath()));
	}
	protected void makeBannerRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> banner, FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(banner.get().getRegistryName().getPath());
		ExtendedFactionShapedRecipeBuilder.shaped(banner.get(), table)
		.pattern("W")
		.pattern("S")
		.pattern("P")
		.define('W', Ingredient.of(ExtendedTags.Items.WOOL_BLOCKS))
		.define('S', Ingredient.of(STICKS))
		.define('P', Ingredient.of(PLANKS))
		.unlockedBy("has_wool", has(ExtendedTags.Items.WOOL_BLOCKS))
		.save(consumer, lotrLoc(banner.get().getRegistryName().getPath()));
	}
	protected void makeBannerPatternRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> banner, Supplier<? extends Item> pattern, FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(pattern.get().getRegistryName().getPath());
		ExtendedFactionShapelessRecipeBuilder.shapeless(pattern.get(), table)
		.requires(Items.PAPER)
		.requires(banner.get())
		.unlockedBy("has_"+banner.get().getRegistryName().getPath(), has(banner.get()))
		.save(consumer, lotrLoc(pattern.get().getRegistryName().getPath()));
	}

	protected void makeWoodenDoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> door, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeDoorRecipe(consumer, door, resource, table, "wooden_door");
	}
	
	protected void makeDoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> door, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeDoorRecipe(consumer, door, resource, table, "");
	}
	private void makeDoorRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> door, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String group) {
		String name = door.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(door.get(), 3)
			.group(group)
			.pattern("##")
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(door.get(), 3, table)
			.group(group)
			.pattern("##")
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeWoodenButtonRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> button, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeButtonRecipe(consumer, button, resource, table, "wooden_button");
	}
	protected void makeButtonRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> button, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeButtonRecipe(consumer, button, resource, table, "");
	}
	private void makeButtonRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> button, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String group) {
		String name = button.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(button.get())
			.group(group)
			.requires(resource.get())
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(button.get(), table)
			.group(group)
			.requires(resource.get())
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makePlanksRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> planks, Supplier<? extends Block> log, ITag<Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = planks.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(planks.get(), 4)
			.group("planks")
			.requires(resource)
			.unlockedBy("has_"+log.get().getRegistryName().getPath(), has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 4, table)
			.group("planks")
			.requires(resource)
			.unlockedBy("has_"+log.get().getRegistryName().getPath(), has(resource))
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
			.group("planks")
			.requires(Ingredient.of(branch.get(), stripped_branch.get()))
			.unlockedBy("has_"+branch.get().getRegistryName().getPath(), has(branch.get()))
			.unlockedBy("has_"+stripped_branch.get().getRegistryName().getPath(), has(stripped_branch.get()))
			.save(consumer, lotrLoc(name+"_from_"+branch.get().getRegistryName().getPath()));
			ShapelessRecipeBuilder.shapeless(planks.get(), 2)
			.group("planks")
			.requires(Ingredient.of(log_slab.get(), stripped_log_slab.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
			ShapelessRecipeBuilder.shapeless(planks.get(), 2)
			.group("planks")
			.requires(Ingredient.of(wood_slab.get(), stripped_wood_slab.get()))
			.unlockedBy("has_"+wood_slab.get().getRegistryName().getPath(), has(wood_slab.get()))
			.unlockedBy("has_"+stripped_wood_slab.get().getRegistryName().getPath(), has(stripped_wood_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_slab.get().getRegistryName().getPath()));
			ShapelessRecipeBuilder.shapeless(planks.get(), 3)
			.group("planks")
			.requires(Ingredient.of(wood_stairs.get(), stripped_wood_stairs.get()))
			.unlockedBy("has_"+wood_stairs.get().getRegistryName().getPath(), has(wood_stairs.get()))
			.unlockedBy("has_"+stripped_wood_stairs.get().getRegistryName().getPath(), has(stripped_wood_stairs.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_stairs.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.group("planks")
			.requires(Ingredient.of(branch.get(), stripped_branch.get()))
			.unlockedBy("has_"+branch.get().getRegistryName().getPath(), has(branch.get()))
			.unlockedBy("has_"+stripped_branch.get().getRegistryName().getPath(), has(stripped_branch.get()))
			.save(consumer, lotrLoc(name+"_from_"+branch.get().getRegistryName().getPath()));
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.group("planks")
			.requires(Ingredient.of(log_slab.get(), stripped_log_slab.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.group("planks")
			.requires(Ingredient.of(wood_slab.get(), stripped_wood_slab.get()))
			.unlockedBy("has_"+wood_slab.get().getRegistryName().getPath(), has(wood_slab.get()))
			.unlockedBy("has_"+stripped_wood_slab.get().getRegistryName().getPath(), has(stripped_wood_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+wood_slab.get().getRegistryName().getPath()));
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 3, table)
			.group("planks")
			.requires(Ingredient.of(wood_stairs.get(), stripped_wood_stairs.get()))
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
			.group("planks")
			.requires(Ingredient.of(log_slab.get(), stripped_log_slab.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(planks.get(), 2, table)
			.group("planks")
			.requires(Ingredient.of(log_slab.get(), stripped_log_slab.get()))
			.unlockedBy("has_"+log_slab.get().getRegistryName().getPath(), has(log_slab.get()))
			.unlockedBy("has_"+stripped_log_slab.get().getRegistryName().getPath(), has(stripped_log_slab.get()))
			.save(consumer, lotrLoc(name+"_from_"+log_slab.get().getRegistryName().getPath()));
		}
	}


	protected void makeWoodenFenceAndFenceGateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> fence, Supplier<? extends Block> gate, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String fenceName = fence.get().getRegistryName().getPath();
		String gateName = gate.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(fenceName);
		GENERATEDRECIPES.add(gateName);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(fence.get(), 3)
			.group("wooden_fence")
			.pattern("#I#")
			.pattern("#I#")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(fenceName));
			ExtendedShapedRecipeBuilder.shaped(gate.get())
			.group("wooden_fence_gate")
			.pattern("I#I")
			.pattern("I#I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(gateName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(fence.get(), 3, table)
			.group("wooden_fence")
			.pattern("#I#")
			.pattern("#I#")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(fenceName));
			ExtendedFactionShapedRecipeBuilder.shaped(gate.get(), table)
			.group("wooden_fence_gate")
			.pattern("I#I")
			.pattern("I#I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(gateName));
		}
	}

	protected void makeSignRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> sign, Supplier<? extends Block> wallSign, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String signName = sign.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(signName);
		GENERATEDRECIPES.add(wallSign.get().getRegistryName().getPath());
		if(table==null) {
ExtendedShapedRecipeBuilder.shaped(sign.get(), 3)
			.pattern("###")
			.pattern("###")
			.pattern(" I ")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
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
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(signName));
		}
	}

	protected void makeWoodRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 3)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 3, table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void make2x2BlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String path) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 4)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 4, table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+path));
		}
	}

	protected void makeCarvedBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Block> resource1, Supplier<? extends Block> resource2, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted.get())
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource1.get()))
			.unlockedBy("has_"+resource1.get().getRegistryName().getPath(), has(resource1.get()))
			.save(consumer, lotrLoc(name));
			makeCarvedBlockRecipeOnlySlabs(consumer, objectToBeCrafted, resource2, table);
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource1.get()))
			.unlockedBy("has_"+resource1.get().getRegistryName().getPath(), has(resource1.get()))
			.save(consumer, lotrLoc(name));
			makeCarvedBlockRecipeOnlySlabs(consumer, objectToBeCrafted, resource2, table);
		}
	}

	protected void makeCarvedBlockRecipeOnlySlabs(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> objectToBeCrafted, Supplier<? extends Block> resource2, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = objectToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted.get())
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource2.get()))
			.unlockedBy("has_"+resource2.get().getRegistryName().getPath(), has(resource2.get()))
			.save(consumer, lotrLoc(name+"_from_slabs"));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), table)
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource2.get()))
			.unlockedBy("has_"+resource2.get().getRegistryName().getPath(), has(resource2.get()))
			.save(consumer, lotrLoc(name+"_from_slabs"));
		}
	}

	protected void makeCarvedBlockRecipeVanilla(Consumer<IFinishedRecipe> consumer, Block objectToBeCrafted, Block resource1, Block resource2) {
		String name = objectToBeCrafted.getRegistryName().getPath();
		ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted)
		.pattern("##")
		.pattern("##")
		.define('#', Ingredient.of(resource1))
		.unlockedBy("has_"+resource1.getRegistryName().getPath(), has(resource1))
		.save(consumer, mcLoc(name));
		makeCarvedBlockRecipeOnlySlabsVanilla(consumer, objectToBeCrafted, resource2);
	}

	protected void makeCarvedBlockRecipeOnlySlabsVanilla(Consumer<IFinishedRecipe> consumer, Block objectToBeCrafted, Block resource2) {
		if(resource2!=null) {
			String name = objectToBeCrafted.getRegistryName().getPath();
			ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted)
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource2))
			.unlockedBy("has_"+resource2.getRegistryName().getPath(), has(resource2))
			.save(consumer, mcLoc(name+"_from_slabs"));
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
			ExtendedShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 9)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(objectToBeCrafted.get(), 9, table)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
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
		.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
		.save(consumer, lotrLoc(name));
	}

	protected void makeMossyBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeMossyBlockRecipe(consumer, blockToBeCrafted, resource, table, "", true);
	}
	protected void makeMossyBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String altName) {
		makeMossyBlockRecipe(consumer, blockToBeCrafted, resource, table, altName, true);
	}
	protected void makeMossyBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String altName, boolean useVinesVanillaOverride) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			if(useVinesVanillaOverride) {
				ShapelessRecipeBuilder.shapeless(blockToBeCrafted.get())
				.requires(Ingredient.of(resource.get()))
				.requires((Ingredient.of(()->Items.VINE)))
				.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
				.save(consumer, lotrLoc(name+altName+"_using_vines"));
			}
			ShapelessRecipeBuilder.shapeless(blockToBeCrafted.get())
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(ExtendedItems.WILLOW_VINES.get())))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName+"_using_vines_willow"));
			ShapelessRecipeBuilder.shapeless(blockToBeCrafted.get())
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(ExtendedItems.MOSS_BLOCK.get())))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName+"_using_moss_block"));
			ShapelessRecipeBuilder.shapeless(blockToBeCrafted.get())
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(ExtendedItems.MOSS_CARPET.get())))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName+"_using_moss_carpet"));
		}
		else {
			if(useVinesVanillaOverride) {
				ExtendedFactionShapelessRecipeBuilder.shapeless(blockToBeCrafted.get(), table)
				.requires(Ingredient.of(resource.get()))
				.requires((Ingredient.of(()->Items.VINE)))
				.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
				.save(consumer, lotrLoc(name+altName+"_using_vines"));
			}
			ExtendedFactionShapelessRecipeBuilder.shapeless(blockToBeCrafted.get(), table)
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(ExtendedItems.WILLOW_VINES.get())))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName+"_using_vines_willow"));
			ExtendedFactionShapelessRecipeBuilder.shapeless(blockToBeCrafted.get(), table)
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(ExtendedItems.MOSS_BLOCK.get())))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName+"_using_moss_block"));
			ExtendedFactionShapelessRecipeBuilder.shapeless(blockToBeCrafted.get(), table)
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(ExtendedItems.MOSS_CARPET.get())))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName+"_using_moss_carpet"));
		}
	}

	protected void makeSnowyBlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(blockToBeCrafted.get())
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(Items.SNOWBALL)))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(blockToBeCrafted.get(), table)
			.requires(Ingredient.of(resource.get()))
			.requires((Ingredient.of(Items.SNOWBALL)))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeBeamRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makePillarRecipe(consumer, blockToBeCrafted, resource, table, "lotr:beam");
	}
	protected void makePillarRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makePillarRecipe(consumer, blockToBeCrafted, resource, table, "");
	}
	private void makePillarRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String group) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 3)
			.group(group)
			.pattern("#")
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 3, table)
			.group(group)
			.pattern("#")
			.pattern("#")
			.pattern("#")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeCraftingTableRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeCraftingTableRecipe(consumer, blockToBeCrafted, resource, "", table);
	}

	protected void makeCraftingTableRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, String altName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("##")
			.pattern("XX")
			.define('#', Ingredient.of(PLANKS))
			.define('X', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("##")
			.pattern("XX")
			.define('#', Ingredient.of(PLANKS))
			.define('X', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName));
		}
	}

	protected void makeBarsRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, ITag<Item> base, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 16)
			.pattern("XXX")
			.pattern("XXX")
			.define('X', Ingredient.of(base))
			.unlockedBy("has_"+resourceName, has(base))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 16, table)
			.pattern("XXX")
			.pattern("XXX")
			.define('X', Ingredient.of(base))
			.unlockedBy("has_"+resourceName, has(base))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeForgeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resourceBlocks, Supplier<? extends Item> resource, String altName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("BBB")
			.pattern("BRB")
			.pattern("BBB")
			.define('B', Ingredient.of(resourceBlocks.get()))
			.define('R', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("BBB")
			.pattern("BRB")
			.pattern("BBB")
			.define('B', Ingredient.of(resourceBlocks.get()))
			.define('R', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+altName));
		}
	}

	protected void makeTorchRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		GENERATEDRECIPES.add(name+"_wall");
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
			.pattern("#")
			.pattern("I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4, table)
			.pattern("#")
			.pattern("I")
			.define('#', Ingredient.of(resource.get()))
			.define('I', STICKS)
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeBirdcageRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> bars, ITag<Item> base, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		GENERATEDRECIPES.add(blockToBeCrafted.get().getRegistryName().getPath());
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("BBB")
			.pattern("B B")
			.pattern("III")
			.define('B', Ingredient.of(bars.get()))
			.define('I', Ingredient.of(base))
			.unlockedBy("has_"+bars.get().getRegistryName().getPath(), has(bars.get()))
			.save(consumer, lotrLoc(blockToBeCrafted.get().getRegistryName().getPath()));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("BBB")
			.pattern("B B")
			.pattern("III")
			.define('B', Ingredient.of(bars.get()))
			.define('I', Ingredient.of(base))
			.unlockedBy("has_"+bars.get().getRegistryName().getPath(), has(bars.get()))
			.save(consumer, lotrLoc(blockToBeCrafted.get().getRegistryName().getPath()));
		}
	}

	protected void makeStairRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String path) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
			.pattern("  #")
			.pattern(" ##")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+path));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4, table)
			.pattern("  #")
			.pattern(" ##")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+path));
		}
	}

	protected void makeFallenLeavesRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 16)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 16, table)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeBranchRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeWallRecipe(consumer, blockToBeCrafted, resource, table, "lotr:branch", "");
	}
	protected void makeWallRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeWallRecipe(consumer, blockToBeCrafted, resource, table, "", "");
	}
	private void makeWallRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String group, String path) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6)
			.group(group)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6, table)
			.group(group)
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+path));
		}
	}

	protected void makeSlabRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table, String path) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6, table)
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name+path));
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
	protected void makeArmorRecipe(Consumer<IFinishedRecipe> consumer,
		Supplier<? extends Item> chestplateToBeCrafted,
		Supplier<? extends Item> leggingsToBeCrafted,
		Supplier<? extends Item> bootsToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
	makeChestplateRecipe(consumer, chestplateToBeCrafted, resource, table);
	makeLeggingsRecipe(consumer, leggingsToBeCrafted, resource, table);
	makeBootsRecipe(consumer, bootsToBeCrafted, resource, table);
}
	protected void makeHelmetRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}

	}
	protected void makeChestplateRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("# #")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("# #")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeLeggingsRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeBootsRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("# #")
			.pattern("# #")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	//TOOLS+SWORDS
	protected void makeSwordRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
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
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeSwordRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeSpearRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("  #")
			.pattern(" X ")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(),has(resource.get()))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("  #")
			.pattern(" X ")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeSpearRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("  #")
			.pattern(" X ")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_" + resourceName,has(resource))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("  #")
			.pattern(" X ")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_" + resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeDaggerRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(),has(resource.get()))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_" + resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeDaggerRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_" + resourceName,has(resource))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_" + resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeBowRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern(" X#")
			.pattern("X #")
			.pattern(" X#")
			.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
			.define('X', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern(" X#")
			.pattern("X #")
			.pattern(" X#")
			.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
			.define('X', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeCrossbowRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if (table == null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("XXS")
			.pattern("#TX")
			.pattern("S#X")
			.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
			.define('X', Ingredient.of(resource))
			.define('S', Ingredient.of(STICKS))
			.define('T', Ingredient.of(Items.TRIPWIRE_HOOK))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		} else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("XXS")
			.pattern("#TX")
			.pattern("S#X")
			.define('#', Ingredient.of(ExtendedItems.BOWSTRING.get()))
			.define('X', Ingredient.of(resource))
			.define('S', Ingredient.of(STICKS))
			.define('T', Ingredient.of(Items.TRIPWIRE_HOOK))
			.unlockedBy("has_"+resourceName, has(resource))
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
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern(" X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
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
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeShovelRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("X")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
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
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeAxeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("##")
			.pattern("#X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
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
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeHoeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("##")
			.pattern(" X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource.get()))
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
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeToolsRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> pickaxeToBeCrafted, Supplier<? extends Item> shovelToBeCrafted,
		Supplier<? extends Item> axeToBeCrafted, Supplier<? extends Item> hoeToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makePickaxeRecipe(consumer, pickaxeToBeCrafted, resource, resourceName, table);
		makeShovelRecipe(consumer, shovelToBeCrafted, resource, resourceName, table);
		makeAxeRecipe(consumer, axeToBeCrafted, resource, resourceName, table);
		makeHoeRecipe(consumer, hoeToBeCrafted, resource, resourceName, table);
	}

	protected void makePickaxeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern(" X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern(" X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeShovelRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("#")
			.pattern("X")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("#")
			.pattern("X")
			.pattern("X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeAxeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("##")
			.pattern("#X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("##")
			.pattern("#X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeHoeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("##")
			.pattern(" X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("##")
			.pattern(" X")
			.pattern(" X")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeMattockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("###")
			.pattern("#X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("###")
			.pattern("#X ")
			.pattern(" X ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeThrowableAxeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern(" # ")
			.pattern(" X#")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern(" # ")
			.pattern(" X#")
			.pattern("X  ")
			.define('X', STICKS)
			.define('#', Ingredient.of(resource))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name));
		}
	}

	protected void makeOrcBombRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get(), 4)
			.pattern("PIP")
			.pattern("IPI")
			.pattern("PIP")
			.define('I', Ingredient.of(resource))
			.define('P', Ingredient.of(Items.GUNPOWDER))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name+"_from_"+resourceName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), 4, table)
			.pattern("PIP")
			.pattern("IPI")
			.pattern("PIP")
			.define('I', Ingredient.of(resource))
			.define('P', Ingredient.of(Items.GUNPOWDER))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name+"_from_"+resourceName));
		}
	}
	protected void makeOrcBombFireRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> itemToBeUpgraded, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("LB")
			.define('B', Ingredient.of(itemToBeUpgraded.get()))
			.define('L', Ingredient.of(Items.LAVA_BUCKET))
			.unlockedBy("has_"+Items.LAVA_BUCKET.getRegistryName().getPath(), has(Items.LAVA_BUCKET))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("LB")
			.define('B', Ingredient.of(itemToBeUpgraded.get()))
			.define('L', Ingredient.of(Items.LAVA_BUCKET))
			.unlockedBy("has_"+Items.LAVA_BUCKET.getRegistryName().getPath(), has(Items.LAVA_BUCKET))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void makeOrcBombUpgradeRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> itemToBeUpgraded, ITag<Item> resource, String resourceName, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(itemToBeCrafted.get())
			.pattern("PBI")
			.define('B', Ingredient.of(itemToBeUpgraded.get()))
			.define('I', Ingredient.of(resource))
			.define('P', Ingredient.of(Items.GUNPOWDER))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name+"_from_"+resourceName));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(itemToBeCrafted.get(), table)
			.pattern("PBI")
			.define('B', Ingredient.of(itemToBeUpgraded.get()))
			.define('I', Ingredient.of(resource))
			.define('P', Ingredient.of(Items.GUNPOWDER))
			.unlockedBy("has_"+resourceName, has(resource))
			.save(consumer, lotrLoc(name+"_from_"+resourceName));
		}
	}

	protected void makeTorchConversionRecipeDirect(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> fromItem, Supplier<? extends Item> toItem, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = fromItem.get().getRegistryName().getPath();
		String name2 = toItem.get().getRegistryName().getPath();
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(toItem.get())
			.requires(fromItem.get())
			.unlockedBy("has_"+name1, has(fromItem.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(toItem.get(), table)
			.requires(fromItem.get())
			.unlockedBy("has_"+name1, has(fromItem.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}

	protected void makeTorchConversionRecipeItem(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> fromItem, Supplier<? extends Item> toItem, Supplier<? extends Item> material, int conversionFactor, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = fromItem.get().getRegistryName().getPath();
		String name2 = toItem.get().getRegistryName().getPath();
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(toItem.get(), conversionFactor)
			.pattern(" T ")
			.pattern("TMT")
			.pattern(" T ")
			.define('T', Ingredient.of(fromItem.get()))
			.define('M', Ingredient.of(material.get()))
			.unlockedBy("has_"+name1, has(fromItem.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(toItem.get(), conversionFactor, table)
			.pattern(" T ")
			.pattern("TMT")
			.pattern(" T ")
			.define('T', Ingredient.of(fromItem.get()))
			.define('M', Ingredient.of(material.get()))
			.unlockedBy("has_"+name1, has(fromItem.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}

	protected void makeTorchConversionRecipeTag(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> fromItem, Supplier<? extends Item> toItem, ITag<Item> resource, int conversionFactor, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = fromItem.get().getRegistryName().getPath();
		String name2 = toItem.get().getRegistryName().getPath();
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(toItem.get(), conversionFactor)
			.pattern(" T ")
			.pattern("TMT")
			.pattern(" T ")
			.define('T', Ingredient.of(fromItem.get()))
			.define('M', Ingredient.of(resource))
			.unlockedBy("has_"+name1, has(fromItem.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(toItem.get(), conversionFactor, table)
			.pattern(" T ")
			.pattern("TMT")
			.pattern(" T ")
			.define('T', Ingredient.of(fromItem.get()))
			.define('M', Ingredient.of(resource))
			.unlockedBy("has_"+name1, has(fromItem.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}
	protected void makeStorageBlock3x3ToItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> item, Supplier<? extends Block> block, 
			@Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		this.makeStorageBlock3x3ToItemAndBackRecipe(consumer, item, block, table, null, null);
	}
	protected void makeStorageBlock3x3ToItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> item, Supplier<? extends Block> block, 
			@Nullable FactionBasedRecipeType<ICraftingRecipe> table, String itemRecipeGroup, String blockRecipeGroup) {
		String name1 = item.get().getRegistryName().getPath();
		String name2 = block.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(item.get(), 9)
			.group(itemRecipeGroup)
			.requires(block.get())
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedShapedRecipeBuilder.shaped(block.get())
			.group(blockRecipeGroup)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(item.get(), 9, table)
			.group(itemRecipeGroup)
			.requires(block.get())
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedFactionShapedRecipeBuilder.shaped(block.get(), table)
			.group(blockRecipeGroup)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
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
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedShapedRecipeBuilder.shaped(block.get())
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(item.get(), 4, table)
			.requires(block.get())
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedFactionShapedRecipeBuilder.shaped(block.get(), table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}

	protected void makeCoralRecipes(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> output, Supplier<? extends Item> inputCoral, Supplier<? extends Item> inputFan, Supplier<? extends Item> inputBlock) {
		GENERATEDRECIPES.add(output.get().getRegistryName().getPath());
		ShapelessRecipeBuilder.shapeless(output.get())
		.group(output.get().getRegistryName().getPath())
		.requires(inputFan.get())
		.unlockedBy("has_"+inputFan.get().getRegistryName().getPath(), has(inputFan.get()))
		.save(consumer, lotrLoc(output.get().getRegistryName().getPath()+"_from_"+inputFan.get().getRegistryName().getPath()));
		ShapelessRecipeBuilder.shapeless(output.get())
		.group(output.get().getRegistryName().getPath())
		.requires(inputCoral.get())
		.unlockedBy("has_"+inputCoral.get().getRegistryName().getPath(), has(inputCoral.get()))
		.save(consumer, lotrLoc(output.get().getRegistryName().getPath()+"_from_"+inputCoral.get().getRegistryName().getPath()));
		ShapelessRecipeBuilder.shapeless(output.get(), 4)
		.group(output.get().getRegistryName().getPath())
		.requires(inputBlock.get())
		.unlockedBy("has_"+inputBlock.get().getRegistryName().getPath(), has(inputBlock.get()))
		.save(consumer, lotrLoc(output.get().getRegistryName().getPath()+"_from_"+inputBlock.get().getRegistryName().getPath()));
	}

	protected void makeNuggetToIngotAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> ingot, Supplier<? extends Item> nugget, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name1 = ingot.get().getRegistryName().getPath();
		String name2 = nugget.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(nugget.get(), 9)
			.requires(ingot.get())
			.unlockedBy("has_"+name1, has(ingot.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedShapedRecipeBuilder.shaped(ingot.get())
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(nugget.get()))
			.unlockedBy("has_"+name1, has(nugget.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
		else {
			ExtendedFactionShapelessRecipeBuilder.shapeless(nugget.get(), 9, table)
			.requires(ingot.get())
			.unlockedBy("has_"+name2, has(ingot.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			ExtendedFactionShapedRecipeBuilder.shaped(ingot.get(), table)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(nugget.get()))
			.unlockedBy("has_"+name1, has(nugget.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
	}

	protected void makeTarpItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> resource, 
			Supplier<? extends Block> blockToBeCrafted, 
			Supplier<? extends Block> slabToBeCrafted, 
			Supplier<? extends Block> stairToBeCrafted, 
			Supplier<? extends Block> wallToBeCrafted, 
			@Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeTarpBlockItemAndBackRecipe(consumer, resource, blockToBeCrafted, table);
		makeTarpSlabItemAndBackRecipe(consumer, resource, slabToBeCrafted, table);
		makeTarpStairItemAndBackRecipe(consumer, resource, stairToBeCrafted, table);
		makeTarpWallItemAndBackRecipe(consumer, resource, wallToBeCrafted, table);

	}
	protected void makeTarpBlockItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> resource, Supplier<? extends Block> blockToBeCrafted, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String resourceName = resource.get().getRegistryName().getPath();
		String output = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(output);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("SF")
			.pattern("FS")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resourceName, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ShapelessRecipeBuilder.shapeless(resource.get(), 2)
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("SF")
			.pattern("FS")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+resourceName, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ExtendedFactionShapelessRecipeBuilder.shapeless(resource.get(), 2, table)
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
	}
	protected void makeTarpSlabItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> resource, Supplier<? extends Block> blockToBeCrafted, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String resourceName = resource.get().getRegistryName().getPath();
		String output = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(output);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("SFS")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+output, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ShapelessRecipeBuilder.shapeless(resource.get())
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("SFS")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+output, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ExtendedFactionShapelessRecipeBuilder.shapeless(resource.get(), table)
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
	}
	protected void makeTarpWallItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> resource, Supplier<? extends Block> blockToBeCrafted, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String resourceName = resource.get().getRegistryName().getPath();
		String output = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(output);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern("S")
			.pattern("F")
			.pattern("S")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+output, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ShapelessRecipeBuilder.shapeless(resource.get())
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern("S")
			.pattern("F")
			.pattern("S")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+output, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ExtendedFactionShapelessRecipeBuilder.shapeless(resource.get(), table)
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
	}
	protected void makeTarpStairItemAndBackRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> resource, Supplier<? extends Block> blockToBeCrafted, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String resourceName = resource.get().getRegistryName().getPath();
		String output = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(output);
		if(table==null) {
			ExtendedShapedRecipeBuilder.shaped(blockToBeCrafted.get())
			.pattern(" F")
			.pattern("FS")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+output, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ShapelessRecipeBuilder.shapeless(resource.get(), 2)
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), table)
			.pattern(" F")
			.pattern("FS")
			.define('S', Ingredient.of(STICKS))
			.define('F', Ingredient.of(resource.get()))
			.unlockedBy("has_"+output, has(resource.get()))
			.save(consumer, lotrLoc(output));
			ExtendedFactionShapelessRecipeBuilder.shapeless(resource.get(), 2, table)
			.requires(blockToBeCrafted.get())
			.unlockedBy("has_"+output, has(blockToBeCrafted.get()))
			.save(consumer, lotrLoc(resourceName+"_from_"+output));
		}
	}
	protected void makeStoneCutterRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> ing, Supplier<? extends Block> output) {
		makeStoneCutterRecipe(consumer, ing, output, 1);
	}
	protected void makeStoneCutterRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> ing, Supplier<? extends Block> output, int num) {
		String name1 = ing.get().getRegistryName().getPath();
		String name2 = output.get().getRegistryName().getPath();
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(ing.get()), output.get(), num).unlocks("has_"+name1, has(ing.get()))
		.save(consumer, name2+"_from_"+name1);
	}

	protected void makeStoneGrinderRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> input, Supplier<? extends Item> output) {
		makeMillingRecipe(consumer, input, output, 0.0F, 1, 1);
	}

	protected void makeStoneGrinderRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> input, Supplier<? extends Item> output, float breakChance) {
		makeMillingRecipe(consumer, input, output, breakChance, 1, 1);
	}

	protected void makeStoneGrinderRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> input, Supplier<? extends Item> output, int count) {
		makeMillingRecipe(consumer, input, output, 0.0F, count, count);
	}

	protected void makeStoneGrinderRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> input, Supplier<? extends Item> output, int countMin, int countMax) {
		makeMillingRecipe(consumer, input, output, 0.0F, countMin, countMax);
	}

	protected void makeMillingRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> input, Supplier<? extends Item> output, float breakChance, int countMin, int countMax) {
		String name1 = output.get().getRegistryName().getNamespace()+"_"+output.get().getRegistryName().getPath();
		String name2 = input.get().getRegistryName().getNamespace()+"_"+input.get().getRegistryName().getPath();
		ExtendedMillingRecipeBuilder.grinding(output.get(), countMin, countMax, Ingredient.of(input.get()), breakChance, ExtendedRecipeSerializers.STONE_GRINDING_SERIALIZER.get())
		.save(consumer, extendedLoc(name1+"_grinded_from_"+name2));
	}

	protected void makeOreFurnaceRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Item ore, Item result, float xp) {
		makeSmeltingRecipe(pFinishedRecipeConsumer, ore, result, xp, 200);
		makeBlastingRecipe(pFinishedRecipeConsumer, ore, result, xp, 100);
	}

	protected void makeSmeltingRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Item ore, Item result, float xp, int ticks) {
		GENERATEDRECIPES.add(result.getRegistryName().getPath());
		CookingRecipeBuilder.smelting(Ingredient.of(ore), result, xp, ticks)
		.unlockedBy("has_"+ore.getRegistryName().getPath(), has(ore))
		.save(pFinishedRecipeConsumer, result.getRegistryName().getPath()+"_from_smelting");
	}

	protected void makeBlastingRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Item ore, Item result, float xp, int ticks) {
		GENERATEDRECIPES.add(result.getRegistryName().getPath());
		CookingRecipeBuilder.blasting(Ingredient.of(ore), result, xp, ticks)
		.unlockedBy("has_"+ore.getRegistryName().getPath(), has(ore))
		.save(pFinishedRecipeConsumer, result.getRegistryName().getPath()+"_from_blasting");
	}

	protected void makeCrucibleRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Supplier<? extends Item> input, Supplier<? extends Item> result) {
		makeCrucibleRecipe(pFinishedRecipeConsumer, input, result, 1);
	}
	protected void makeCrucibleRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Supplier<? extends Item> input, Supplier<? extends Item> result, int count) {
		GENERATEDRECIPES.add(input.get().getRegistryName().getPath());
		ExtendedCrucibleRecipeBuilder.specific(Ingredient.of(input.get()), new ItemStack(result.get(), count), ExtendedRecipeSerializers.CRUCIBLE_SPECIFIC_SERIALIZER.get())
		.save(pFinishedRecipeConsumer);
	}
	protected void makeCrucibleRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Supplier<? extends Item> input, Supplier<? extends Item> result1, Supplier<? extends Item> result2) {
		makeCrucibleRecipe(pFinishedRecipeConsumer, input, result1, 1, result2, 1);
	}
	protected void makeCrucibleRecipe(Consumer<IFinishedRecipe> pFinishedRecipeConsumer, Supplier<? extends Item> input, Supplier<? extends Item> result1, int count1, Supplier<? extends Item> result2, int count2) {
		GENERATEDRECIPES.add(input.get().getRegistryName().getPath());
		ExtendedCrucibleRecipeBuilder.specific(Ingredient.of(input.get()), new ItemStack(result1.get(), count1), new ItemStack(result2.get(), count2), ExtendedRecipeSerializers.CRUCIBLE_SPECIFIC_SERIALIZER.get())
		.save(pFinishedRecipeConsumer);
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
			if(item.getValue().get() instanceof ExtendedSwordItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SWORD RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof PickaxeItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING PICKAXE RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof ShovelItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SHOVEL RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof AxeItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING AXE RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRHoeItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING HOE RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof ExtendedThrowableAxeItem) {
				if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING TOOL RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(!GENERATEDRECIPES.contains(item.getValue().get().getRegistryName().getPath())) {
				System.out.println("MISSING ITEM RECIPE FOR: "+item.getValue().get().getRegistryName().toString());
			}
		}
	}
}
