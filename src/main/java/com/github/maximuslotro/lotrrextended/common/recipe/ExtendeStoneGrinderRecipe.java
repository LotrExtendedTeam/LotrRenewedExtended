package com.github.maximuslotro.lotrrextended.common.recipe;

import java.util.Random;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendeStoneGrinderRecipe implements IRecipe<IInventory> {
	private final ResourceLocation id;
	protected final String group;
	protected final Ingredient ingredient;
	protected final ItemStack result;
	protected final float experience;
	protected final int grindingTime;
	protected final float breakChance;
	protected final int countMin;
	protected final int countMax;

	public ExtendeStoneGrinderRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, int countMin, int countMax, float experience, int grindingTime, float breakChance) {
		this.id = id;
		this.group = group;
		this.ingredient = ingredient;
		this.result = result;
		this.experience = experience;
		this.grindingTime = grindingTime;
		this.breakChance = breakChance;
		this.countMin = countMin;
		this.countMax = countMax;
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

	public int getCount(Random rand) {
		if(countMin==countMax) {
			return countMin>0 ? countMin : 1;
		}else {
			int returning = countMin+rand.nextInt(countMax-countMin+1);
			return returning>0 ? returning : 1;
		}
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
		return this.grindingTime;
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
		return ExtendedRecipes.STONE_GRINDING_SERIALIZER.get();
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(ExtendedBlocks.STONE_GRINDER.get());
	}
}