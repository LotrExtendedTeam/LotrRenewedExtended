package lotr.common.datagen.crafting;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

public class ExtendedCraftingRecipiesGenerator extends ExtendedCraftingHelper {
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
