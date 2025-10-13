package com.github.maximuslotro.lotrrextended.common.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import lotr.common.fac.Faction;
import lotr.common.inv.FactionCraftingInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ArmorDyeRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;
import java.util.Optional;

/**
 * Based off of {@link ArmorDyeRecipe}
 * */
public class ExtendedTableDyeableRecipe extends SpecialRecipe {
	private final IRecipeType<?> recipeType;
	private final Ingredient ingredient;

	public ExtendedTableDyeableRecipe(ResourceLocation id, IRecipeType<?> recipeType, Ingredient pIngredient) {
		super(id);
		this.recipeType = recipeType;
		this.ingredient = pIngredient;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_TABLE_ARMOR_DYE.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return recipeType;
	}

	@Override
	public boolean matches(CraftingInventory pInv, World pLevel) {
		ItemStack itemstack = ItemStack.EMPTY;
		List<ItemStack> list = Lists.newArrayList();
		for(int i = 0; i < pInv.getContainerSize(); ++i) {
			ItemStack itemstack1 = pInv.getItem(i);
			if (!itemstack1.isEmpty()) {
				if (ingredient.test(itemstack1)) {
					if (!itemstack.isEmpty()) {
						// No more than one dyeable item
						return false;
					}
					itemstack = itemstack1;
				} else {
					if (!(itemstack1.getItem() instanceof DyeItem)) {
						return false;
					}

					list.add(itemstack1);
				}
			}
		}
		Optional<Faction> tableFaction = pInv instanceof FactionCraftingInventory ? ((FactionCraftingInventory) pInv).getPouchColoringFaction() : Optional.empty();
		return !itemstack.isEmpty() && (!list.isEmpty() || tableFaction.isPresent());
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(CraftingInventory pInv) {
		List<DyeItem> pDyes = Lists.newArrayList();
		ItemStack itemstack = ItemStack.EMPTY;

		for(int i = 0; i < pInv.getContainerSize(); ++i) {
			ItemStack itemstack1 = pInv.getItem(i);
			if (!itemstack1.isEmpty()) {
				if (ingredient.test(itemstack1)) {
					if (!itemstack.isEmpty()) {
						return ItemStack.EMPTY;
					}

					itemstack = itemstack1.copy();
				} else {
					Item item = itemstack1.getItem();
					if (!(item instanceof DyeItem)) {
						return ItemStack.EMPTY;
					}

					pDyes.add((DyeItem)item);
				}
			}
		}
		Optional<Faction> tableFaction = pInv instanceof FactionCraftingInventory ? ((FactionCraftingInventory) pInv).getPouchColoringFaction() : Optional.empty();
		if(!itemstack.isEmpty() && (!pDyes.isEmpty() || tableFaction.isPresent())) {
			ItemStack resultItem = ItemStack.EMPTY;
			int[] aint = new int[3];
			int i = 0;
			int colorsApplied = 0;
			IDyeableArmorItem dyeableItem = null;
			Item item = itemstack.getItem();
			if (item instanceof IDyeableArmorItem) {
				dyeableItem = (IDyeableArmorItem)item;
				resultItem = itemstack.copy();
				resultItem.setCount(1);
				if (dyeableItem.hasCustomColor(itemstack)) {
					int k = dyeableItem.getColor(resultItem);
					float f = (float)(k >> 16 & 255) / 255.0F;
					float f1 = (float)(k >> 8 & 255) / 255.0F;
					float f2 = (float)(k & 255) / 255.0F;
					i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
					aint[0] = (int)((float)aint[0] + f * 255.0F);
					aint[1] = (int)((float)aint[1] + f1 * 255.0F);
					aint[2] = (int)((float)aint[2] + f2 * 255.0F);
					++colorsApplied;
				}

				for(DyeItem dyeitem : pDyes) {
					float[] afloat = dyeitem.getDyeColor().getTextureDiffuseColors();
					int i2 = (int)(afloat[0] * 255.0F);
					int l = (int)(afloat[1] * 255.0F);
					int i1 = (int)(afloat[2] * 255.0F);
					i += Math.max(i2, Math.max(l, i1));
					aint[0] += i2;
					aint[1] += l;
					aint[2] += i1;
					++colorsApplied;
				}
			}

			if (dyeableItem == null) {
				return ItemStack.EMPTY;
			} else if (pDyes.isEmpty() && tableFaction.isPresent()){
				dyeableItem.setColor(resultItem, tableFaction.get().getColor());
				return resultItem;
			}else {
				int r = aint[0] / colorsApplied;
				int g = aint[1] / colorsApplied;
				int b = aint[2] / colorsApplied;
				float f3 = (float)i / (float)colorsApplied;
				float f4 = (float)Math.max(r, Math.max(g, b));
				r = (int)((float)r * f3 / f4);
				g = (int)((float)g * f3 / f4);
				b = (int)((float)b * f3 / f4);
				int resultColor = (r << 8) + g;
				resultColor = (resultColor << 8) + b;
				dyeableItem.setColor(resultItem, resultColor);
				return resultItem;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return pWidth * pHeight >= 2;
	}

	/**
	 * Save and load recipe type to check for
	 * */
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendedTableDyeableRecipe> {
		@Override
		public ExtendedTableDyeableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			IRecipeType<?> recipeType = Registry.RECIPE_TYPE.get(new ResourceLocation(JSONUtils.getAsString(json, "recipe_type", "")));
			Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			return new ExtendedTableDyeableRecipe(recipeId, recipeType, ingredient);
		}

		@Override
		public ExtendedTableDyeableRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			IRecipeType<?> recipeType = Registry.RECIPE_TYPE.get(new ResourceLocation(buffer.readUtf()));
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			return new ExtendedTableDyeableRecipe(recipeId, recipeType, ingredient);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ExtendedTableDyeableRecipe recipe) {
			buffer.writeUtf(Registry.RECIPE_TYPE.getKey(recipe.recipeType).toString());
			recipe.ingredient.toNetwork(buffer);
		}
	}
}
