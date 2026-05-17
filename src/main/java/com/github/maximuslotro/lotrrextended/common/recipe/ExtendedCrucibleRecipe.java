package com.github.maximuslotro.lotrrextended.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedCrucibleRecipe implements ExtendedICurcibleRecipe {
	private final ResourceLocation id;
	protected final String group;
	protected final ItemStack result1;
	protected final ItemStack result2;
	protected final float experience;
	protected final int meltingTime;

	public ExtendedCrucibleRecipe(ResourceLocation id, String group, ItemStack result1, ItemStack result2, float experience, int meltingTime) {
		this.id = id;
		this.group = group;
		this.result1 = result1;
		this.result2 = result2;
		this.experience = experience;
		this.meltingTime = meltingTime;
	}

	// Used for recipe book view and search
	@Override
	public ItemStack getResultItem() {
		return this.result1;
	}

	// Used for recipe book grouping
	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public int getMeltingTime() {
		return this.meltingTime;
	}

	@Override
	public float getExperience() {
		return this.experience;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}
}