package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedLeatherHatItem;
import com.google.gson.JsonObject;
import lotr.common.fac.Faction;
import lotr.common.inv.FactionCraftingInventory;
import lotr.common.inv.PouchInventory;
import lotr.common.item.PouchItem;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.recipe.PouchRecipe;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtendedLeatherHatRecipe extends SpecialRecipe {
    private final IRecipeType<?> recipeType;

    public ExtendedLeatherHatRecipe(ResourceLocation id, IRecipeType<?> recipeType) {
        super(id);
        this.recipeType = recipeType;
    }

    public IRecipeType<?> getType() {
        return this.recipeType;
    }

    public boolean matches(CraftingInventory inv, World world) {
        System.out.println("matches() called");
        return !this.getCraftingResult(inv).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInventory pInv) {
        // TODO: Not sure if this is an issue yet but I can't even get it to detect the recipe
        return null;
    }

    public ItemStack getCraftingResult(CraftingInventory inv) {
        System.out.println("getCraftingResult called");
        Optional<Faction> tableColoringFaction = inv instanceof FactionCraftingInventory ? ((FactionCraftingInventory)inv).getPouchColoringFaction() : Optional.empty();
        List<ItemStack> hats = new ArrayList<>();
        int[] rgb = new int[3];
        int brightestIngredientColor = 0;
        int coloredItems = 0;
        boolean anyDye = false;
        boolean shouldApplyColorToResult = false;

        // TODO: Verify the dying part on crafting tables work
        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack ingredient = inv.getItem(i);
            if (!ingredient.isEmpty()) {
                Item ingredientItem = ingredient.getItem();
                if (ingredientItem instanceof ExtendedLeatherHatItem) {
                    hats.add(ingredient);
                    int hatColor = ExtendedLeatherHatItem.getHatColor(ingredient);
                    float r = (float)(hatColor >> 16 & 255) / 255.0F;
                    float g = (float)(hatColor >> 8 & 255) / 255.0F;
                    float b = (float)(hatColor & 255) / 255.0F;
                    brightestIngredientColor = (int)((float)brightestIngredientColor + Math.max(r, Math.max(g, b)) * 255.0F);
                    rgb[0] = (int)((float)rgb[0] + r * 255.0F);
                    rgb[1] = (int)((float)rgb[1] + g * 255.0F);
                    rgb[2] = (int)((float)rgb[2] + b * 255.0F);
                    ++coloredItems;
                    if (ExtendedLeatherHatItem.isHatDyed(ingredient)) {
                        shouldApplyColorToResult = true;
                    }
                } else {
                    if (!(ingredientItem instanceof DyeItem)) {
                        return ItemStack.EMPTY;
                    }

                    float[] dyeColors = ((DyeItem)ingredientItem).getDyeColor().getTextureDiffuseColors();
                    int r = (int)(dyeColors[0] * 255.0F);
                    int g = (int)(dyeColors[1] * 255.0F);
                    int b = (int)(dyeColors[2] * 255.0F);
                    brightestIngredientColor += Math.max(r, Math.max(g, b));
                    rgb[0] += r;
                    rgb[1] += g;
                    rgb[2] += b;
                    ++coloredItems;
                    shouldApplyColorToResult = true;
                    anyDye = true;
                }
            }
        }

        if (hats.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack hat = null;
            if (hats.size() == 1) {
                if (!anyDye && !tableColoringFaction.isPresent()) {
                    return ItemStack.EMPTY;
                }

                hat = hats.get(0);
                // TODO: I don't think this code is necessary for hats - commenting it out for now
            }
            // TODO: Add functionality for dying multiple hats at the same time?

            if (tableColoringFaction.isPresent() && !anyDye && hat != null) {
                ExtendedLeatherHatItem.setHatDyedByFaction(hat, (Faction)tableColoringFaction.get());
            } else if (shouldApplyColorToResult && coloredItems > 0 && hat != null) {
                int r = rgb[0] / coloredItems;
                int g = rgb[1] / coloredItems;
                int b = rgb[2] / coloredItems;
                float brightestIngredientColorPerItem = (float)brightestIngredientColor / (float)coloredItems;
                float brightestAvgIngredientRgb = (float)Math.max(r, Math.max(g, b));
                r = (int)((float)r * brightestIngredientColorPerItem / brightestAvgIngredientRgb);
                g = (int)((float)g * brightestIngredientColorPerItem / brightestAvgIngredientRgb);
                b = (int)((float)b * brightestIngredientColorPerItem / brightestAvgIngredientRgb);
                int color = (r << 16) + (g << 8) + b;
                ExtendedLeatherHatItem.setHatDyedColor(hat, color);
            }

            return hat;
        }
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    public IRecipeSerializer<?> getSerializer() {
        return LOTRRecipes.CRAFTING_SPECIAL_POUCH.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendedLeatherHatRecipe> {

        @Override
        public ExtendedLeatherHatRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            return null;
        }

        @Nullable
        @Override
        public ExtendedLeatherHatRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            return null;
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, ExtendedLeatherHatRecipe pRecipe) {

        }
    }
}
