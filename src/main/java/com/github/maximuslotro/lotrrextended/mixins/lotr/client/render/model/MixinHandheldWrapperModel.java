package com.github.maximuslotro.lotrrextended.mixins.lotr.client.render.model;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraftforge.client.ForgeHooksClient;

@Mixin(targets = "lotr.client.render.model.HandheldItemModels$HandheldWrapperModel")
public class MixinHandheldWrapperModel {

	@Shadow(remap = false)
	@Final
	private IBakedModel defaultModel;

	@Shadow(remap = false)
	@Final
	private IBakedModel handheldModel;

	/**
	 * @author maximuslotro
	 * @reason Don't have mess with inject rn, I'm tired rn.
	 * @*/
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
