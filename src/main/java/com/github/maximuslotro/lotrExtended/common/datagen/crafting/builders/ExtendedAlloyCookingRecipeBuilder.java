package com.github.maximuslotro.lotrExtended.common.datagen.crafting.builders;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Code Copied from {@link net.minecraft.data.CookingRecipeBuilder}
 * to add alloy recipe data
 * */
@SuppressWarnings("deprecation")
public class ExtendedAlloyCookingRecipeBuilder {
	   private final Item result;
	   private final List<Ingredient> ingredients = Lists.newArrayList();
	   private final List<Ingredient> aloyingredients = Lists.newArrayList();
	   private final boolean swappable;
	   private final float experience;
	   private final int cookingTime;
	   private final Advancement.Builder advancement = Advancement.Builder.advancement();
	   private String group;
	   private final IRecipeSerializer<?> serializer;

	   private ExtendedAlloyCookingRecipeBuilder(IItemProvider result, float xp, int time, IRecipeSerializer<?> serializer, boolean swappable) {
	      this.result = result.asItem();
	      this.swappable = swappable;
	      this.experience = xp;
	      this.cookingTime = time;
	      this.serializer = serializer;
	   }

	   public static ExtendedAlloyCookingRecipeBuilder cooking(IItemProvider result, IRecipeSerializer<?> serializer) {
	      return new ExtendedAlloyCookingRecipeBuilder(result, 0.7f, 200, serializer, false);
	   }

	   public ExtendedAlloyCookingRecipeBuilder unlockedBy(String p_218628_1_, ICriterionInstance p_218628_2_) {
	      this.advancement.addCriterion(p_218628_1_, p_218628_2_);
	      return this;
	   }

	   public ExtendedAlloyCookingRecipeBuilder requires(ITag<Item> p_203221_1_) {
		   	return this.requires(Ingredient.of(p_203221_1_));
	   }

	   public ExtendedAlloyCookingRecipeBuilder requires(IItemProvider p_200487_1_) {
		   	return this.requires(p_200487_1_, 1);
	   }

	   public ExtendedAlloyCookingRecipeBuilder requires(IItemProvider p_200491_1_, int p_200491_2_) {
		   for(int i = 0; i < p_200491_2_; ++i) {
			   this.requires(Ingredient.of(p_200491_1_));
		   }
		   return this;
	   }
		
	   public ExtendedAlloyCookingRecipeBuilder requires(Ingredient p_200489_1_) {
		   return this.requires(p_200489_1_, 1);
	   }

	   public ExtendedAlloyCookingRecipeBuilder requires(Ingredient p_200492_1_, int p_200492_2_) {
		   for(int i = 0; i < p_200492_2_; ++i) {
			   this.ingredients.add(p_200492_1_);
		   }
		   return this;
	   }
	   
	   public ExtendedAlloyCookingRecipeBuilder requiresAlloy(ITag<Item> p_203221_1_) {
		   	return this.requiresAlloy(Ingredient.of(p_203221_1_));
	   }

	   public ExtendedAlloyCookingRecipeBuilder requiresAlloy(IItemProvider p_200487_1_) {
		   	return this.requiresAlloy(p_200487_1_, 1);
	   }

	   public ExtendedAlloyCookingRecipeBuilder requiresAlloy(IItemProvider p_200491_1_, int p_200491_2_) {
		   for(int i = 0; i < p_200491_2_; ++i) {
			   this.requiresAlloy(Ingredient.of(p_200491_1_));
		   }
		   return this;
	   }
		
	   public ExtendedAlloyCookingRecipeBuilder requiresAlloy(Ingredient p_200489_1_) {
		   return this.requiresAlloy(p_200489_1_, 1);
	   }

	   public ExtendedAlloyCookingRecipeBuilder requiresAlloy(Ingredient p_200492_1_, int p_200492_2_) {
		   for(int i = 0; i < p_200492_2_; ++i) {
			   this.aloyingredients.add(p_200492_1_);
		   }
		   return this;
	   }

	public void save(Consumer<IFinishedRecipe> p_218630_1_) {
	      this.save(p_218630_1_, Registry.ITEM.getKey(this.result));
	   }

	   public void save(Consumer<IFinishedRecipe> p_218632_1_, String p_218632_2_) {
	      ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
	      ResourceLocation resourcelocation1 = new ResourceLocation(p_218632_2_);
	      if (resourcelocation1.equals(resourcelocation)) {
	         throw new IllegalStateException("Recipe " + resourcelocation1 + " should remove its 'save' argument");
	      } else {
	         this.save(p_218632_1_, resourcelocation1);
	      }
	   }

	   public void save(Consumer<IFinishedRecipe> p_218635_1_, ResourceLocation p_218635_2_) {
	      this.ensureValid(p_218635_2_);
	      this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_218635_2_)).rewards(AdvancementRewards.Builder.recipe(p_218635_2_)).requirements(IRequirementsStrategy.OR);
	      p_218635_1_.accept(new ExtendedAlloyCookingRecipeBuilder.Result(p_218635_2_, this.group == null ? "" : this.group, this.ingredients, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(p_218635_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_218635_2_.getPath()), this.serializer, this.aloyingredients, swappable));
	   }

	   private void ensureValid(ResourceLocation p_218634_1_) {
	      if (this.advancement.getCriteria().isEmpty()) {
	         throw new IllegalStateException("No way of obtaining recipe " + p_218634_1_);
	      }
	   }

	   public static class Result implements IFinishedRecipe {
	      private final ResourceLocation id;
	      private final String group;
	      private final List<Ingredient> ingredients;
	      private final List<Ingredient> alloyIngredients;
	      private final boolean swappable;
	      private final Item result;
	      private final float experience;
	      private final int cookingTime;
	      private final Advancement.Builder advancement;
	      private final ResourceLocation advancementId;
	      private final IRecipeSerializer<?> serializer;

	      public Result(ResourceLocation p_i50605_1_, String p_i50605_2_, List<Ingredient> p_i50605_3_, Item p_i50605_4_, float p_i50605_5_, int p_i50605_6_, Advancement.Builder p_i50605_7_, ResourceLocation p_i50605_8_, IRecipeSerializer<?> p_i50605_9_, List<Ingredient> alloyIngredient, boolean swappable) {
	         this.id = p_i50605_1_;
	         this.group = p_i50605_2_;
	         this.ingredients = p_i50605_3_;
	         this.alloyIngredients = alloyIngredient;
	         this.swappable = swappable;
	         this.result = p_i50605_4_;
	         this.experience = p_i50605_5_;
	         this.cookingTime = p_i50605_6_;
	         this.advancement = p_i50605_7_;
	         this.advancementId = p_i50605_8_;
	         this.serializer = p_i50605_9_;
	      }

	      public void serializeRecipeData(JsonObject p_218610_1_) {
	         if (!this.group.isEmpty()) {
	            p_218610_1_.addProperty("group", this.group);
	         }

	         JsonArray jsonarray = new JsonArray();
	         for(Ingredient ingredient : this.ingredients) {
	            jsonarray.add(ingredient.toJson());
	         }
	         p_218610_1_.add("ingredient", jsonarray);
	         
	         JsonArray jsonarray1 = new JsonArray();
	         for(Ingredient ingredient : this.alloyIngredients) {
	            jsonarray1.add(ingredient.toJson());
	         }
	         p_218610_1_.add("alloy", jsonarray1);

	         p_218610_1_.addProperty("swappable", this.swappable);
	         p_218610_1_.addProperty("result", Registry.ITEM.getKey(this.result).toString());
	         p_218610_1_.addProperty("experience", this.experience);
	         p_218610_1_.addProperty("cookingtime", this.cookingTime);
	      }

	      public IRecipeSerializer<?> getType() {
	         return this.serializer;
	      }

	      public ResourceLocation getId() {
	         return this.id;
	      }

	      @Nullable
	      public JsonObject serializeAdvancement() {
	         return this.advancement.serializeToJson();
	      }

	      @Nullable
	      public ResourceLocation getAdvancementId() {
	         return this.advancementId;
	      }
	   }
	}
