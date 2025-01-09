package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Code Copied from {@link net.minecraft.data.ShapedRecipeBuilder}
 * to add ability to make null requirements
 * */
public class ExtendedShapedRecipeBuilder {
	private static final Logger LOGGER = LogManager.getLogger();
	private final Item result;
	private final int count;
	private final List<String> rows = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private String group;

	public ExtendedShapedRecipeBuilder(IItemProvider p_i48261_1_, int p_i48261_2_) {
		this.result = p_i48261_1_.asItem();
		this.count = p_i48261_2_;
	}

	public static ExtendedShapedRecipeBuilder shaped(IItemProvider p_200470_0_) {
		return shaped(p_200470_0_, 1);
	}

	public static ExtendedShapedRecipeBuilder shaped(IItemProvider p_200468_0_, int p_200468_1_) {
		return new ExtendedShapedRecipeBuilder(p_200468_0_, p_200468_1_);
	}

	public ExtendedShapedRecipeBuilder define(Character p_200469_1_, ITag<Item> p_200469_2_) {
		return this.define(p_200469_1_, Ingredient.of(p_200469_2_));
	}

	public ExtendedShapedRecipeBuilder define(Character p_200462_1_, IItemProvider p_200462_2_) {
		return this.define(p_200462_1_, Ingredient.of(p_200462_2_));
	}

	public ExtendedShapedRecipeBuilder define(Character p_200471_1_, Ingredient p_200471_2_) {
		if (this.key.containsKey(p_200471_1_)) {
			throw new IllegalArgumentException("Symbol '" + p_200471_1_ + "' is already defined!");
		} else if (p_200471_1_ == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			this.key.put(p_200471_1_, p_200471_2_);
			return this;
		}
	}

	public ExtendedShapedRecipeBuilder pattern(String p_200472_1_) {
		if (!this.rows.isEmpty() && p_200472_1_.length() != this.rows.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.rows.add(p_200472_1_);
			return this;
		}
	}

	public ExtendedShapedRecipeBuilder unlockedBy(String p_200465_1_, ICriterionInstance p_200465_2_) {
		this.advancement.addCriterion(p_200465_1_, p_200465_2_);
		return this;
	}

	public ExtendedShapedRecipeBuilder group(String p_200473_1_) {
		this.group = p_200473_1_;
		return this;
	}

	public void save(Consumer<IFinishedRecipe> p_200464_1_) {
		this.save(p_200464_1_, ForgeRegistries.ITEMS.getKey(this.result));
	}

	public void save(Consumer<IFinishedRecipe> p_200466_1_, String p_200466_2_) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
		if ((new ResourceLocation(p_200466_2_)).equals(resourcelocation)) {
			throw new IllegalStateException("Shaped Recipe " + p_200466_2_ + " should remove its 'save' argument");
		} else {
			this.save(p_200466_1_, new ResourceLocation(p_200466_2_));
		}
	}

	public void save(Consumer<IFinishedRecipe> p_200467_1_, ResourceLocation p_200467_2_) {
		this.ensureValid(p_200467_2_);
		this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200467_2_)).rewards(AdvancementRewards.Builder.recipe(p_200467_2_)).requirements(IRequirementsStrategy.OR);
		p_200467_1_.accept(new ExtendedShapedRecipeBuilder.Result(p_200467_2_, this.result, this.count, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, new ResourceLocation(p_200467_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_200467_2_.getPath())));
	}

	public void saveNoAdvancement(Consumer<IFinishedRecipe> p_200467_1_, ResourceLocation p_200467_2_) {
		this.ensureValid(p_200467_2_);
		p_200467_1_.accept(new ExtendedShapedRecipeBuilder.Result(p_200467_2_, this.result, this.count, this.group == null ? "" : this.group, this.rows, this.key, null, null));
	}

	private void ensureValid(ResourceLocation p_200463_1_) {
		if (this.rows.isEmpty()) {
			throw new IllegalStateException("No pattern is defined for shaped recipe " + p_200463_1_ + "!");
		} else {
			Set<Character> set = Sets.newHashSet(this.key.keySet());
			set.remove(' ');

			for(String s : this.rows) {
				for(int i = 0; i < s.length(); ++i) {
					char c0 = s.charAt(i);
					if (!this.key.containsKey(c0) && c0 != ' ') {
						throw new IllegalStateException("Pattern in recipe " + p_200463_1_ + " uses undefined symbol '" + c0 + "'");
					}

					set.remove(c0);
				}
			}

			if (!set.isEmpty()) {
				throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + p_200463_1_);
			} else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
				throw new IllegalStateException("Shaped recipe " + p_200463_1_ + " only takes in a single item - should it be a shapeless recipe instead?");
			} else if (this.advancement.getCriteria().isEmpty()) {
				System.out.println("No way of obtaining recipe " + p_200463_1_);
			}
		}
	}

	public class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final Item result;
		private final int count;
		private final String group;
		private final List<String> pattern;
		private final Map<Character, Ingredient> key;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;

		public Result(ResourceLocation p_i48271_2_, Item p_i48271_3_, int p_i48271_4_, String p_i48271_5_, List<String> p_i48271_6_, Map<Character, Ingredient> p_i48271_7_, Advancement.Builder p_i48271_8_, ResourceLocation p_i48271_9_) {
			this.id = p_i48271_2_;
			this.result = p_i48271_3_;
			this.count = p_i48271_4_;
			this.group = p_i48271_5_;
			this.pattern = p_i48271_6_;
			this.key = p_i48271_7_;
			this.advancement = p_i48271_8_;
			this.advancementId = p_i48271_9_;
		}

		@Override
		public void serializeRecipeData(JsonObject p_218610_1_) {
			if (!this.group.isEmpty()) {
				p_218610_1_.addProperty("group", this.group);
			}

			JsonArray jsonarray = new JsonArray();

			for(String s : this.pattern) {
				jsonarray.add(s);
			}

			p_218610_1_.add("pattern", jsonarray);
			JsonObject jsonobject = new JsonObject();

			for(Entry<Character, Ingredient> entry : this.key.entrySet()) {
				jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
			}

			p_218610_1_.add("key", jsonobject);
			JsonObject jsonobject1 = new JsonObject();
			jsonobject1.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
			if (this.count > 1) {
				jsonobject1.addProperty("count", this.count);
			}

			p_218610_1_.add("result", jsonobject1);
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return IRecipeSerializer.SHAPED_RECIPE;
		}

		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		@Nullable
		public JsonObject serializeAdvancement() {
			if(advancement == null) {
				return null;
			}
			return this.advancement.serializeToJson();
		}

		@Override
		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}