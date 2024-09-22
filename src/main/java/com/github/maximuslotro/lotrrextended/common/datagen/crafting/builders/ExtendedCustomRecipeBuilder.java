package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;

/**
 * Code Copied from {@link net.minecraft.data.CustomRecipeBuilder}
 * to add namespace setting capability to getId
 * */
public class ExtendedCustomRecipeBuilder {
	private final SpecialRecipeSerializer<?> serializer;

	public ExtendedCustomRecipeBuilder(SpecialRecipeSerializer<?> p_i50786_1_) {
		this.serializer = p_i50786_1_;
	}

	public static ExtendedCustomRecipeBuilder special(SpecialRecipeSerializer<?> p_218656_0_) {
		return new ExtendedCustomRecipeBuilder(p_218656_0_);
	}

	public void save(Consumer<IFinishedRecipe> p_200499_1_, final ResourceLocation location) {
		p_200499_1_.accept(new IFinishedRecipe() {
			public void serializeRecipeData(JsonObject p_218610_1_) {
			}

			public IRecipeSerializer<?> getType() {
				return ExtendedCustomRecipeBuilder.this.serializer;
			}

			public ResourceLocation getId() {
				return location;
			}

			@Nullable
			public JsonObject serializeAdvancement() {
				return null;
			}

			public ResourceLocation getAdvancementId() {
				return new ResourceLocation("");
			}
		});
	}
}