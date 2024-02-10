package com.github.maximuslotro.lotroExtended.mixins.lotr.common.tileentity;

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselDrinkItem.Potency;
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

	@Final
	@Shadow(remap = false)
	private static final int[] SLOTS_INGREDIENT = { 0, 1, 2, 3, 4, 5 };

	@Final
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

	@Shadow
	public void setItem(int index, ItemStack stack) {}

	@Shadow
	public ItemStack getItem(int index) {return null;}
	/**
	 * @reason Trying to find structure gen null pointer exception
	 * @author maximuslotro
	 */
	@Overwrite(remap = false)
	private void updateBrewingRecipe() {
		if (this.kegMode == KegTileEntity.KegMode.EMPTY) {
			IInventory proxyInv = new Inventory(9);
			int[] var2 = SLOTS_INGREDIENT;
			int var3 = var2.length;

			int var4;
			int i;
			for (var4 = 0; var4 < var3; ++var4) {
				i = var2[var4];
				proxyInv.setItem(i, this.getItem(i));
			}

			var2 = SLOTS_WATER;
			var3 = var2.length;

			for (var4 = 0; var4 < var3; ++var4) {
				i = var2[var4];
				proxyInv.setItem(i, this.getItem(i));
			}
			KegTileEntity entity = ((KegTileEntity)(Object)(this));
			if(!entity.hasLevel()) {
				currentBrewingRecipe = null;
				setItem(9, ItemStack.EMPTY);
				brewTimeTotal = 0;
				entity.setChanged();
				return;
			}
			World level = entity.getLevel();
			Optional<DrinkBrewingRecipe> opt = level.getRecipeManager().getRecipeFor(LOTRRecipes.DRINK_BREWING,
					proxyInv, level);
			if (opt.isPresent()) {
				DrinkBrewingRecipe recipe = (DrinkBrewingRecipe) opt.get();
				this.currentBrewingRecipe = recipe.getId();
				ItemStack result = recipe.assemble(proxyInv);
				result.setCount(16);
				VesselDrinkItem.setPotency(result, Potency.WEAK);
				this.setItem(9, result);
				this.brewTimeTotal = recipe.getBrewTime();
			} else {
				this.currentBrewingRecipe = null;
				this.setItem(9, ItemStack.EMPTY);
				this.brewTimeTotal = 0;
			}

			entity.setChanged();
			if (!level.isClientSide) {
				this.resendKegInventoryToPlayers();
			}
		}
	}
}
