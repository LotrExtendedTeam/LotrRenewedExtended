package com.github.maximuslotro.lotrextended.mixins.lotr.client.render.model;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraftforge.client.ForgeHooksClient;

/**
 * Used to edit Renewed's model TransformTypes
 * @Mixin (targets = "lotr.client.render.model.HandheldItemModels$HandheldWrapperModel")
 * */
@Mixin(targets = "lotr.client.render.model.HandheldItemModels$HandheldWrapperModel")
public class MixinHandheldWrapperModel {

	/**
	 * Get the default model instance for this model
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Shadow(remap = false)
	@Final
	private IBakedModel defaultModel;

	/**
	 * Get the overriding model instance for this model
	 * @Shadow (remap = false)
	 * @Final Mark the field to be essentially final
	 * */
	@Shadow(remap = false)
	@Final
	private IBakedModel handheldModel;

	/**
	 * @author maximuslotro
	 * @reason Need to overwrite large view weapons to render on weaponracks
	 * @Overwrite (remap = false)
	 * @param transformType The TransformType this model is trying to be rendered as
	 * @param mat The MatrixStack instance for this model
	 * @return The selected IBakedModel instance to render
	 * */
	@Overwrite(remap = false)
	public IBakedModel handlePerspective(TransformType transformType, MatrixStack mat) {
		IBakedModel modelToUse = defaultModel;
		if (transformType == TransformType.FIRST_PERSON_LEFT_HAND || transformType == TransformType.FIRST_PERSON_RIGHT_HAND
				|| transformType == TransformType.THIRD_PERSON_LEFT_HAND || transformType == TransformType.THIRD_PERSON_RIGHT_HAND
				|| transformType == TransformType.FIXED) {
			modelToUse = handheldModel;
		}
		return ForgeHooksClient.handlePerspective(modelToUse, transformType, mat);
	}
}
