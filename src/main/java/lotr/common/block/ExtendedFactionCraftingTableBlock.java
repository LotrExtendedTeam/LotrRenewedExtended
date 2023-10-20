package lotr.common.block;

import java.util.function.Supplier;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedRecipes;
import net.minecraft.block.Block;

public class ExtendedFactionCraftingTableBlock {
	public static class Taurethrim extends FactionCraftingTableBlock {
		public Taurethrim(Supplier<? extends Block> block) {
			super(block, FactionPointers.TAURETHRIM, ExtendedRecipes.TAURETHRIM_CRAFTING, "taurethrim_crafting");
		}
	}
}
