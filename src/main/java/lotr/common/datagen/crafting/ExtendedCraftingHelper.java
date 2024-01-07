package lotr.common.datagen.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.init.ExtendedBlocks;
import lotr.common.init.ExtendedItems;
import lotr.common.item.LOTRArmorItem;
import lotr.common.item.LOTRAxeItem;
import lotr.common.item.LOTRHoeItem;
import lotr.common.item.LOTRPickaxeItem;
import lotr.common.item.LOTRShovelItem;
import lotr.common.item.LOTRSwordItem;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedCraftingHelper extends RecipeProvider {

	public static List<String> GENERATEDBLOCKRECPIES = new ArrayList<>();
	public static List<String> GENERATEDITEMRECPIES = new ArrayList<>();

	public ExtendedCraftingHelper(DataGenerator gen) {
		super(gen);
	}

	public ResourceLocation lotrLoc(String name) {
        return new ResourceLocation("lotr", name);
    }

	private void makeStairRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Block> resource) {
		GENERATEDBLOCKRECPIES.add(blockToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
		.pattern("  #")
		.pattern(" ##")
		.pattern("###")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(blockToBeCrafted.get().getRegistryName().getPath()+"_1"));
		ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 4)
		.pattern("  #")
		.pattern(" ##")
		.pattern("###")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(blockToBeCrafted.get().getRegistryName().getPath()+"_2"));
	}

	private void makeWallRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Block> resource) {
		GENERATEDBLOCKRECPIES.add(blockToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6)
		.pattern("###")
		.pattern("###")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(blockToBeCrafted.get().getRegistryName().getPath()));
	}

	private void makeSlabRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Block> blockToBeCrafted, Supplier<? extends Block> resource) {
		GENERATEDBLOCKRECPIES.add(blockToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(blockToBeCrafted.get(), 6)
		.pattern("###")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(blockToBeCrafted.get().getRegistryName().getPath()));
	}

	//ARMOR
	private void makeArmorRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> helmetToBeCrafted,
			Supplier<? extends Item> chestplateToBeCrafted,
			Supplier<? extends Item> leggingsToBeCrafted,
			Supplier<? extends Item> bootsToBeCrafted, Supplier<? extends Item> resource) {
		makeHelmetRecpie(consumer, helmetToBeCrafted, resource);
		makeChestplateRecpie(consumer, chestplateToBeCrafted, resource);
		makeLeggingsRecpie(consumer, leggingsToBeCrafted, resource);
		makeBootsRecpie(consumer, bootsToBeCrafted, resource);
	}
	private void makeHelmetRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("###")
		.pattern("# #")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}
	private void makeChestplateRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("# #")
		.pattern("###")
		.pattern("###")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}
	private void makeLeggingsRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("###")
		.pattern("# #")
		.pattern("# #")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}
	private void makeBootsRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("# #")
		.pattern("# #")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}

	//TOOLS+SWORDS
	private void makeSwordRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}
	private void makePickaxeRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("###")
		.pattern(" # ")
		.pattern(" # ")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}
	private void makeShovelRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("#")
		.pattern("#")
		.pattern("#")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
	}
	private void makeAxeRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("##")
		.pattern("##")
		.pattern("# ")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("##")
		.pattern("##")
		.pattern(" #")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()+"_2"));
	}
	private void makeHoeRecpie(Consumer<IFinishedRecipe> consumer, Supplier<? extends Item> itemToBeCrafted, Supplier<? extends Item> resource) {
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("##")
		.pattern("# ")
		.pattern("# ")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()));
		GENERATEDITEMRECPIES.add(itemToBeCrafted.get().getRegistryName().getPath());
		ShapedRecipeBuilder.shaped(itemToBeCrafted.get())
		.pattern("##")
		.pattern(" #")
		.pattern(" #")
		.define('#', Ingredient.of(resource.get()))
		.save(consumer, lotrLoc(itemToBeCrafted.get().getRegistryName().getPath()+"_2"));
	}

	public void checkForMissingRecpies() {
		for(Entry<String, RegistryObject<Block>> block : ExtendedBlocks.REGISTEREDBLOCKS.entrySet()) {
			if(block.getValue().get() instanceof AxialSlabBlock) {
				if(!GENERATEDBLOCKRECPIES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING SLAB RECPIE FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRStairsBlock) {
				if(!GENERATEDBLOCKRECPIES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING STAIR RECPIES FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRWallBlock) {
				if(!GENERATEDBLOCKRECPIES.contains(block.getValue().get().getRegistryName().getPath())) {
					System.out.println("MISSING WALL RECPIE FOR: "+block.getValue().get().getRegistryName().toString());
				}
				continue;
			}
			if(block.getValue().get() instanceof LOTRTorchBlock) {}
		}
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
