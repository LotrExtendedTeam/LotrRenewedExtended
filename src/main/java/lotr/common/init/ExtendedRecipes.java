package lotr.common.init;

import com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders.ExtendedTippedArrowRecipe;

import lotr.common.recipe.FactionTableType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedRecipes {
	public static FactionTableType TAURETHRIM_CRAFTING;
	public static FactionTableType DOL_GULDUR_CRAFTING;
	public static FactionTableType RHUNIC_CRAFTING;
	public static FactionTableType MORWAITH_CRAFTING;
	public static FactionTableType GUNDABAD_CRAFTING;
	public static FactionTableType HALF_TROLL_CRAFTING;
	public static RegistryObject<SpecialRecipeSerializer<ExtendedTippedArrowRecipe>> EXTENDED_TIPPED_ARROW;
}
