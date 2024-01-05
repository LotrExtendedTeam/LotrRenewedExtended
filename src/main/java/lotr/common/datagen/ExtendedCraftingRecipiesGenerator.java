package lotr.common.datagen;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

public class ExtendedCraftingRecipiesGenerator extends RecipeProvider {

	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recpie Provider";
	}
}
