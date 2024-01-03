package lotr.common.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.RecipeProvider;

public class ExtendedCraftingRecipiesGenerator extends RecipeProvider {

	public ExtendedCraftingRecipiesGenerator(DataGenerator gen) {
		super(gen);
	}

	@Override
	public String getName() {
		return "Renewed Extended Crafting Recpie Provider";
	}
}
