package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * Move code to Access Transformers for Java 9+ systems
 * @Mixin (targets = "lotr.client.render.model.HandheldItemModels$HandheldWrapperModel")
 */
@Mixin(targets = "lotr.client.render.model.HandheldItemModels$HandheldWrapperModel")
public class MixinHandheldWrapperModelJavaPatch {

	/**
	 * @author maximuslotro
	 * @reason Move code to Access Transformers for Java 9+ systems. Reflection is still slightly used, as I can't directly reference the inner private static class {@code lotr.client.render.model.HandheldItemModels$HandheldWrapperModel}
	 * @Overwrite (remap=false)
	 * @param modelName A ResourceLocation with the name of the model
	 * @param defaultModel The default IBakedModel to render
	 * @param handheldModel The handheld IBakedModel to render
	 */
	@Overwrite(remap=false)
	public static void remapHandheldModelOverrides(ResourceLocation modelName, IBakedModel defaultModel, IBakedModel handheldModel) {
		try {
			ItemOverrideList overrides = handheldModel.getOverrides();
			List<IBakedModel> overrideModels = overrides.overrideModels;

			// Get the HandheldWrapperModel class
			Class<?> handheldWrapperModelClass = Class.forName("lotr.client.render.model.HandheldItemModels$HandheldWrapperModel");

			// Get the constructor
			Constructor<?> constructor = handheldWrapperModelClass.getDeclaredConstructor(IBakedModel.class, IBakedModel.class);
			constructor.setAccessible(true);

			// Map override models to HandheldWrapperModel instances using reflection
			List<Object> remappedOverrideModels = overrideModels.stream().map(handheldOverride -> {
				try {
					return constructor.newInstance(defaultModel, handheldOverride);
				} catch (Exception e) {
					throw new RuntimeException("Failed to instantiate HandheldWrapperModel", e);
				}
			}).collect(Collectors.toList());

			// Set the new override models
			Field overrideModelsField = ObfuscationReflectionHelper.findField(ItemOverrideList.class, "field_209582_c");
			overrideModelsField.setAccessible(true);
			overrideModelsField.set(overrides, remappedOverrideModels);
		} catch (Exception exception) {
			ExtendedLog.error("Java9+ Failed to remap handheld model overrides for model %s", modelName);
			exception.printStackTrace();
		}
	}
}