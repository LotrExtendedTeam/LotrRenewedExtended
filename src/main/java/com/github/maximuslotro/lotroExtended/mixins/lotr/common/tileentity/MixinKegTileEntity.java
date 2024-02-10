package com.github.maximuslotro.lotroExtended.mixins.lotr.common.tileentity;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.item.VesselDrinkItem;
import lotr.common.recipe.DrinkBrewingRecipe;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@Mixin(KegTileEntity.class)
public class MixinKegTileEntity {

	@Shadow(remap = false)
	private static final int[] SLOTS_INGREDIENT = { 0, 1, 2, 3, 4, 5 };

	@Shadow(remap = false)
	private static final int[] SLOTS_WATER = { 6, 7, 8 };
	
	@Shadow(remap = false)
	private KegTileEntity.KegMode kegMode;

	@Shadow(remap = false)
	private ResourceLocation currentBrewingRecipe;

	@Shadow(remap = false)
	private void resendKegInventoryToPlayers() {}

	@Shadow(remap = false)
	private int brewTimeTotal;

	@Shadow(remap = false)
	public void setItem(int index, ItemStack stack) {}

	@Shadow
	public ItemStack getItem(int index) {return null;}
	/**
	 * @reason Trying to find structure gen null pointer exception
	 * @author maximuslotro
	 */
	@Overwrite(remap = false)
	private void updateBrewingRecipe() {
		System.out.println("A1");
		if (kegMode == KegTileEntity.KegMode.EMPTY) {
			IInventory proxyInv = new Inventory(9);
			System.out.println("A2");
			int[] var2 = SLOTS_INGREDIENT;
			System.out.println("A3");
			int var3 = var2.length;

			System.out.println("B1");
			int var4;
			int i;
			for (var4 = 0; var4 < var3; ++var4) {
				i = var2[var4];
				proxyInv.setItem(i, getItem(i));
			}
			System.out.println("B2");
			var2 = SLOTS_WATER;
			var3 = var2.length;
			System.out.println("B3");
			for (var4 = 0; var4 < var3; ++var4) {
				i = var2[var4];
				proxyInv.setItem(i, getItem(i));
			}
			System.out.println("B4");
			World level = ((KegTileEntity)(Object)(this)).getLevel();
			System.out.println("B5");
			Optional<DrinkBrewingRecipe>  opt = level.getRecipeManager().getRecipeFor(LOTRRecipes.DRINK_BREWING, proxyInv, level);
			if (opt.isPresent()) {
				System.out.println("C6");
				DrinkBrewingRecipe recipe = (DrinkBrewingRecipe) opt.get();
				System.out.println("C7");
				currentBrewingRecipe = recipe.getId();
				System.out.println("C8");
				ItemStack result = recipe.assemble(proxyInv);
				System.out.println("C9");
				result.setCount(16);
				System.out.println("C10");
				VesselDrinkItem.setPotency(result, VesselDrinkItem.Potency.WEAK);
				System.out.println("C11");
				setItem(9, result);
				System.out.println("C12");
				brewTimeTotal = recipe.getBrewTime();
			} else {
				System.out.println("D13");
				currentBrewingRecipe = null;
				System.out.println("D14");
				setItem(9, ItemStack.EMPTY);
				System.out.println("D15");
				brewTimeTotal = 0;
			}
			System.out.println("E16");
			((KegTileEntity)(Object)(this)).setChanged();
			if (!level.isClientSide) {
				System.out.println("F17");
				resendKegInventoryToPlayers();
			}
			System.out.println("E18");
		}

	}
}
