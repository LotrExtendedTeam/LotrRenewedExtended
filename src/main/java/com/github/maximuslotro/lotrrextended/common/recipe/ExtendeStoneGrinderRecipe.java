package com.github.maximuslotro.lotrrextended.common.recipe;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipeSerializers;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipeTypes;
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
		return ExtendedRecipeTypes.MILLSTONE;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.STONE_GRINDING_SERIALIZER.get();
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(ExtendedBlocks.STONE_GRINDER.get());
	}

	public static class ExtendedStoneGrinderRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendeStoneGrinderRecipe> {

		@Override
		public ExtendeStoneGrinderRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String group = JSONUtils.getAsString(json, "group", "");
			Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			if (ingredient.isEmpty()) {
				throw new JsonParseException("No ingredients for stone grinding recipe");
			}
			if (!json.has("result")) {
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			}
			ItemStack result = getItemStack(json);
			float experience = JSONUtils.getAsFloat(json, "experience", 0.0F);
			int grindingtime = JSONUtils.getAsInt(json, "grindingtime", 200);
			float breakChance = JSONUtils.getAsFloat(json, "breakchance", 0.0F);
			int countMin;
			int countMax;
			if(json.has("countMin")&&json.has("countMax")){
				countMin = JSONUtils.getAsInt(json, "countMin", 1);
				countMax = JSONUtils.getAsInt(json, "countMax", 1);
			}else {
				countMin = countMax = JSONUtils.getAsInt(json, "count", 1);
			}
			return new ExtendeStoneGrinderRecipe(recipeId, group, ingredient, result, countMin, countMax, experience, grindingtime, breakChance);
		}

		public ItemStack getItemStack(JsonObject json) {
			String itemName = JSONUtils.getAsString(json, "result");
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
			if (item == null) {
				throw new JsonSyntaxException("Unknown item '" + itemName + "'");
			}
			return new ItemStack(item);
		}

		@Override
		public ExtendeStoneGrinderRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String group = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			float experience = buffer.readFloat();
			int grindingtime = buffer.readVarInt();
			float breakChance = buffer.readFloat();
			int countMin = buffer.readVarInt();
			int countMax = buffer.readVarInt();
			return new ExtendeStoneGrinderRecipe(recipeId, group, ingredient, result, countMin, countMax, experience, grindingtime, breakChance);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ExtendeStoneGrinderRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.grindingTime);
			buffer.writeFloat(recipe.breakChance);
			buffer.writeVarInt(recipe.countMin);
			buffer.writeVarInt(recipe.countMax);
		}
	}
}