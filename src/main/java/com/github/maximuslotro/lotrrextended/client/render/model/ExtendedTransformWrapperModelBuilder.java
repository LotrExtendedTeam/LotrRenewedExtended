package com.github.maximuslotro.lotrrextended.client.render.model;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtendedTransformWrapperModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T> {
	private ResourceLocation guiModel;
	private ResourceLocation heldModel;

	protected ExtendedTransformWrapperModelBuilder(T parent, ExistingFileHelper existingFileHelper) {
		super(new ResourceLocation("lotrextended", "transform_wrapper"), parent, existingFileHelper);
	}

	public static <T extends ModelBuilder<T>> ExtendedTransformWrapperModelBuilder<T> begin(T parent, ExistingFileHelper existingFileHelper) {
		return new ExtendedTransformWrapperModelBuilder<>(parent, existingFileHelper);
	}

	public ExtendedTransformWrapperModelBuilder<T> guiModel(ItemModelBuilder pGuiModel) {
		this.guiModel = pGuiModel.getUncheckedLocation();
		return this;
	}

	public ExtendedTransformWrapperModelBuilder<T> heldModel(ItemModelBuilder pHeldModel) {
		this.heldModel = pHeldModel.getUncheckedLocation();
		return this;
	}

	@Override
	public JsonObject toJson(JsonObject json) {
		super.toJson(json);
		if(guiModel != null) {
			json.addProperty("gui_model", guiModel.toString());
		}
		if(heldModel != null) {
			json.addProperty("held_model", heldModel.toString());
		}
		return json;
	}
}