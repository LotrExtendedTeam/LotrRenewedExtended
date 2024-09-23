package com.github.maximuslotro.lotrrextended.common.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedMillstoneRecipe implements IRecipe<IInventory> {
	private final ResourceLocation id;
	protected final String group;
	protected final Ingredient ingredient;
	protected final ItemStack result;
	protected final float experience;
	protected final int millingTime;
	protected final float breakChance;

	public ExtendedMillstoneRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int millingTime, float breakChance) {
		this.id = id;
		this.group = group;
		this.ingredient = ingredient;
		this.result = result;
		this.experience = experience;
		this.millingTime = millingTime;
		this.breakChance = breakChance;
	}

	@Override
	public boolean matches(IInventory inventory, World world) {
		return this.ingredient.test(inventory.getItem(0));
	}

	@Override
	public ItemStack assemble(IInventory inventory) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.ingredient);
		return nonnulllist;
	}

	public float getExperience() {
		return this.experience;
	}

	public float getBreakChance() {
		return this.breakChance;
	}

	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	public int getMillingTime() {
		return this.millingTime;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeType<?> getType() {
		return ExtendedRecipes.MILLSTONE;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipes.MILLING_SERIALIZER.get();
	}
}