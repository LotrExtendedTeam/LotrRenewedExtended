package lotr.common.datagen.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

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
    public static final ITag.INamedTag<Item> HORNS = ItemTags.bind("forge:horns");
    public static final ITag.INamedTag<Item> BRONZE_INGOT = ItemTags.bind("forge:ingots/bronze");
    
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

	protected void make2x2BlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4, table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
	}
	protected void make3x3BlockRecipe(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDRECIPES.add(name);
		if(table==null) {
			ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 9)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
			.unlockedBy("has_"+resource.get().getRegistryName().getPath(), has(resource.get()))
			.save(consumer, lotrLoc(name));
		}
		else {
			ExtendedFactionShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 9, table)
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.define('#', Ingredient.of(resource.get()))
			.unlockedBy("has_"+name, has(blockToBeCrafted.get()))
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
		GENERATEDRECIPES.add(name1);
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(item.get(), 9)
			.requires(block.get())
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			GENERATEDRECIPES.add(name2);
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
			GENERATEDRECIPES.add(name2);
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
		GENERATEDRECIPES.add(name1);
		GENERATEDRECIPES.add(name2);
		if(table==null) {
			ShapelessRecipeBuilder.shapeless(item.get(), 4)
			.requires(block.get())
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name1+"_from_"+name2));
			GENERATEDRECIPES.add(name2);
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
			GENERATEDRECIPES.add(name2);
			ExtendedFactionShapedRecipeBuilder.shaped(block.get(), table)
			.pattern("##")
			.pattern("##")
			.define('#', Ingredient.of(item.get()))
			.unlockedBy("has_"+name1, has(item.get()))
			.unlockedBy("has_"+name2, has(block.get()))
			.save(consumer, lotrLoc(name2+"_from_"+name1));
		}
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
