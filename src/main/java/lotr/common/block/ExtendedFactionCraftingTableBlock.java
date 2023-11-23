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
	public static class Dol_Guldur extends FactionCraftingTableBlock {
		public Dol_Guldur(Supplier<? extends Block> block) {
			super(block, FactionPointers.DOL_GULDUR, ExtendedRecipes.DOL_GULDUR_CRAFTING, "dol_guldur_crafting");
		}

	}
	public static class Rhunic extends FactionCraftingTableBlock {
		public Rhunic(Supplier<? extends Block> block) {
			super(block, FactionPointers.RHUDEL, ExtendedRecipes.RHUNIC_CRAFTING, "rhunic_crafting");
		}
	}
	public static class Gundabad extends FactionCraftingTableBlock {
		public Gundabad(Supplier<? extends Block> block) {
			super(block, FactionPointers.NORTHERN_ORCS, ExtendedRecipes.GUNDABAD_CRAFTING, "northern_orc_crafting");
		}
	}
	public static class Morwaith extends FactionCraftingTableBlock {
		public Morwaith(Supplier<? extends Block> block) {
			super(block, FactionPointers.MORWAITH, ExtendedRecipes.MORWAITH_CRAFTING, "morwaith_crafting");
		}
	}
	public static class HalfTroll extends FactionCraftingTableBlock {
		public HalfTroll(Supplier<? extends Block> block) {
			super(block, FactionPointers.HALF_TROLLS, ExtendedRecipes.HALF_TROLL_CRAFTING, "half_troll_crafting");
		}
	}
}
