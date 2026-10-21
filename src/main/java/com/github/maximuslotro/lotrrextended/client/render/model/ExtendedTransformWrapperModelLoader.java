package com.github.maximuslotro.lotrrextended.client.render.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelLoader;

public class ExtendedTransformWrapperModelLoader implements IModelLoader<ExtendedTransformWrapperUnbakedModel> {
	public static final ExtendedTransformWrapperModelLoader INSTANCE = new ExtendedTransformWrapperModelLoader();

	private ExtendedTransformWrapperModelLoader() {}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}

	@Override
	public ExtendedTransformWrapperUnbakedModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		ResourceLocation inventoryLocation = new ResourceLocation(JSONUtils.getAsString(modelContents, "gui_model"));
		ResourceLocation handheldLocation = new ResourceLocation(JSONUtils.getAsString(modelContents, "held_model"));
		return new ExtendedTransformWrapperUnbakedModel(inventoryLocation, handheldLocation);
	}
}
