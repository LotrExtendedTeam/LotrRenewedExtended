package lotr.common.datagen.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import lotr.common.init.ExtendedItems;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import lotr.common.recipe.FactionBasedRecipeType;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCraftingHelper extends RecipeProvider {

	public static List<String> GENERATEDBLOCKRECPIES = new ArrayList<>();
	public static List<String> GENERATEDITEMRECPIES = new ArrayList<>();

    public static final ITag.INamedTag<Item> STICKS = ItemTags.bind("forge:rods/wooden");
    
	public ExtendedCraftingHelper(DataGenerator gen) {
		super(gen);
	}

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	protected void makeStairRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Block> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDBLOCKRECPIES.add(name);
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

	protected void makeWallRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Block> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDBLOCKRECPIES.add(name);
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

	protected void makeSlabRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Block> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = blockToBeCrafted.get().getRegistryName().getPath();
		GENERATEDBLOCKRECPIES.add(name);
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
	protected void makeArmorRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> helmetToBeCrafted,
			Supplier<? extends Item> chestplateToBeCrafted,
			Supplier<? extends Item> leggingsToBeCrafted,
			Supplier<? extends Item> bootsToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makeHelmetRecpie(consumer, helmetToBeCrafted, resource, table);
		makeChestplateRecpie(consumer, chestplateToBeCrafted, resource, table);
		makeLeggingsRecpie(consumer, leggingsToBeCrafted, resource, table);
		makeBootsRecpie(consumer, bootsToBeCrafted, resource, table);
	}
	protected void makeHelmetRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeChestplateRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeLeggingsRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeBootsRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeSwordRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeToolsRecpie(Consumer<IFinishedRecipe> consumer, 
			Supplier<? extends Item> pickaxeToBeCrafted,
			Supplier<? extends Item> shovelToBeCrafted,
			Supplier<? extends Item> axeToBeCrafted,
			Supplier<? extends Item> hoeToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		makePickaxeRecpie(consumer, pickaxeToBeCrafted, resource, table);
		makeShovelRecpie(consumer, shovelToBeCrafted, resource, table);
		makeAxeRecpie(consumer, axeToBeCrafted, resource, table);
		makeHoeRecpie(consumer, hoeToBeCrafted, resource, table);
	}
	protected void makePickaxeRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeShovelRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeAxeRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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
	protected void makeHoeRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource, @Nullable FactionBasedRecipeType<ICraftingRecipe> table) {
		String name = itemToBeCrafted.get().getRegistryName().getPath();
		GENERATEDITEMRECPIES.add(name);
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

	public void checkForMissingRecpies() {
//		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
//			if(block.getValue().get() instanceof AxialSlabBlock) {
//				if(!GENERATEDBLOCKRECPIES.contains(block.getValue().get().getRegistryName().getPath())) {
//					System.out.println("MISSING SLAB RECPIE FOR: "+block.getValue().get().getRegistryName().toString());
//				}
//				continue;
//			}
//			if(block.getValue().get() instanceof LOTRStairsBlock) {
//				if(!GENERATEDBLOCKRECPIES.contains(block.getValue().get().getRegistryName().getPath())) {
//					System.out.println("MISSING STAIR RECPIES FOR: "+block.getValue().get().getRegistryName().toString());
//				}
//				continue;
//			}
//			if(block.getValue().get() instanceof LOTRWallBlock) {
//				if(!GENERATEDBLOCKRECPIES.contains(block.getValue().get().getRegistryName().getPath())) {
//					System.out.println("MISSING WALL RECPIE FOR: "+block.getValue().get().getRegistryName().toString());
//				}
//				continue;
//			}
//			if(block.getValue().get() instanceof LOTRTorchBlock) {}
//		}
		for(Entry<String, RegistryObject<Item>> item : ExtendedItems.REGISTEREDITEMS.entrySet()) {
			if(item.getValue().get() instanceof LOTRArmorItem) {
				if(!GENERATEDITEMRECPIES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING ARMOR RECPIE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRSwordItem) {
				if(!GENERATEDITEMRECPIES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SWORD RECPIE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRPickaxeItem) {
				if(!GENERATEDITEMRECPIES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING PICKAXE RECPIE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRShovelItem) {
				if(!GENERATEDITEMRECPIES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SHOVEL RECPIE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRAxeItem) {
				if(!GENERATEDITEMRECPIES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING AXE RECPIE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
			if(item.getValue().get() instanceof LOTRHoeItem ) {
				if(!GENERATEDITEMRECPIES.contains(item.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING HOE RECPIE FOR: "+item.getValue().get().getRegistryName().toString());
				}
			}
		}
	}
}
