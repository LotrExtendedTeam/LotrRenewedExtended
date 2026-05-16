package com.github.maximuslotro.lotrrextended.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ExtendedCrucibleRecipe implements IRecipe<IInventory> {
	private final ResourceLocation id;
	protected final String group;
	protected final ItemStack resultFull;
	protected final ItemStack resultFraction;
	protected final float experience;
	protected final int meltingTime;
	protected final int itemSplitValue;

	public ExtendedCrucibleRecipe(ResourceLocation id, String group, ItemStack resultFull, ItemStack resultFraction, float experience, int meltingTime, int itemSplitValue) {
		this.id = id;
		this.group = group;
		this.resultFull = resultFull;
		this.resultFraction = resultFraction;
		this.experience = experience;
		this.meltingTime = meltingTime;
		this.itemSplitValue = itemSplitValue;
	}

	// TODO add other crucible recipe types for specific items that are not repairable, or deconstruct into different item
	@Override
	public boolean matches(IInventory inventory, World world) {
		ItemStack input = inventory.getItem(0);
		if(input==null || input.isEmpty()) {
			return false;
		}
		return input.getItem().isValidRepairItem(input, resultFull) || input.getItem().isValidRepairItem(input, resultFraction);
	}

	@Override
	public ItemStack assemble(IInventory inventory) {
		if(resultFull.isEmpty()) {
			return ItemStack.EMPTY;
		}
		inventory.getItem(0); // TODO figure out full result count depending on input craft size
		return this.resultFull.copy();
	}

	public ItemStack assemble2(IInventory inventory) {
		if(resultFraction.isEmpty()) {
			return ItemStack.EMPTY;
		}
		inventory.getItem(0); // TODO figure out fraction result count depending on input craft size
		return this.resultFull.copy();
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	//TODO evaluate the use case of this method
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		//nonnulllist.add(this.ingredient);
		return nonnulllist;
	}

	// Used for recipe book view and search
	@Override
	public ItemStack getResultItem() {
		return this.resultFull;
	}

	// Used for recipe book grouping
	@Override
	public String getGroup() {
		return this.group;
	}

	public int getMeltingTime() {
		return this.meltingTime;
	}

	public float getExperience() {
		return this.experience;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeType<?> getType() {
		return ExtendedRecipeTypes.CRUCIBLE;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.CRUCIBLE_SERIALIZER.get();
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(ExtendedBlocks.CRUCIBLE.get());
	}

	public static class ExtendedCrucibleRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendedCrucibleRecipe> {

		@Override
		public ExtendedCrucibleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String group = JSONUtils.getAsString(json, "group", "");
			Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			if (ingredient.isEmpty()) {
				throw new JsonParseException("No ingredients for stone grinding recipe");
			}
			if (!json.has("result_full")) {
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			}
			ItemStack resultFull = getItemStack(json, "result_full");
			ItemStack resultFraction = getItemStack(json, "result_fraction");
			float experience = JSONUtils.getAsFloat(json, "experience", 0.0F);
			int meltingTime = JSONUtils.getAsInt(json, "melting_time", 200);
			int itemSplitValue = JSONUtils.getAsInt(json, "split_size", 0);
			return new ExtendedCrucibleRecipe(recipeId, group, resultFull, resultFraction, experience, meltingTime, itemSplitValue);
		}

		public ItemStack getItemStack(JsonObject json, String type) {
			String itemName = JSONUtils.getAsString(json, type);
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
			if (item == null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(item);
		}

		@Override
		public ExtendedCrucibleRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String group = buffer.readUtf(32767);
			ItemStack resultFull = buffer.readItem();
			ItemStack resultFraction = buffer.readItem();
			float experience = buffer.readFloat();
			int grindingtime = buffer.readVarInt();
			int itemSplitValue = buffer.readVarInt();
			return new ExtendedCrucibleRecipe(recipeId, group, resultFull, resultFraction, experience, grindingtime, itemSplitValue);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ExtendedCrucibleRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeItem(recipe.resultFull);
			buffer.writeItem(recipe.resultFraction);
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.meltingTime);
			buffer.writeVarInt(recipe.itemSplitValue);
		}
	}
}