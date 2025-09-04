package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipes;
import com.google.gson.JsonObject;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedSmithingRecipe implements ExtendedISmeltingRecipe {
	private final Ingredient base;
	private final Ingredient addition;
	private final ItemStack result;
	private final ResourceLocation id;

	public ExtendedSmithingRecipe(ResourceLocation pId, Ingredient pBase, Ingredient pAddition, ItemStack pResult) {
		this.id = pId;
		this.base = pBase;
		this.addition = pAddition;
		this.result = pResult;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(IInventory pInv, World pLevel) {
		return this.base.test(pInv.getItem(0)) && this.addition.test(pInv.getItem(1));
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(IInventory pInv) {
		ItemStack itemstack = this.result.copy();
		CompoundNBT compoundnbt = pInv.getItem(0).getTag();
		if (compoundnbt != null) {
			itemstack.setTag(compoundnbt.copy());
		}

		return itemstack;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return pWidth * pHeight >= 2;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public boolean isAdditionIngredient(ItemStack pAddition) {
		return this.addition.test(pAddition);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(Blocks.SMITHING_TABLE);
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipes.EXTENDED_SMITHING.get();
	}

	public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendedSmithingRecipe> {
		@Override
		public ExtendedSmithingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
			Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "base"));
			Ingredient ingredient1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(pJson, "addition"));
			ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(pJson, "result"));
			return new ExtendedSmithingRecipe(pRecipeId, ingredient, ingredient1, itemstack);
		}

		@Override
		public ExtendedSmithingRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
			Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
			Ingredient ingredient1 = Ingredient.fromNetwork(pBuffer);
			ItemStack itemstack = pBuffer.readItem();
			return new ExtendedSmithingRecipe(pRecipeId, ingredient, ingredient1, itemstack);
		}

		@Override
		public void toNetwork(PacketBuffer pBuffer, ExtendedSmithingRecipe pRecipe) {
			pRecipe.base.toNetwork(pBuffer);
			pRecipe.addition.toNetwork(pBuffer);
			pBuffer.writeItem(pRecipe.result);
		}
	}
}
