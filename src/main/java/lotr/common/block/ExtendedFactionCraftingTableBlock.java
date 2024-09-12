package lotr.common.block;

import java.util.function.Supplier;

import lotr.common.fac.FactionPointers;
import lotr.common.init.ExtendedRecipes;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public class ExtendedFactionCraftingTableBlock {
	public static class Taurethrim extends ExtendedTwoTypeCraftingTableBlock {
		public Taurethrim(Supplier<? extends Block> block, RegistryObject<Block> blockAlt) {
			super(block, FactionPointers.TAURETHRIM, ExtendedRecipes.TAURETHRIM_CRAFTING, "taurethrim_crafting", blockAlt);
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
	public static class NorthernOrc extends FactionCraftingTableBlock {
		public NorthernOrc(Supplier<? extends Block> block) {
			super(block, FactionPointers.NORTHERN_ORCS, ExtendedRecipes.NORTHERN_ORC_CRAFTING, "northern_orc_crafting");
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
