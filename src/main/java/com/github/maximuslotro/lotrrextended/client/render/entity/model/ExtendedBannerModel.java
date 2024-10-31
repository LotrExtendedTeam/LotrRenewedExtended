package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;

public class ExtendedBannerModel extends NonLivingModel<ExtendedBannerEntity> {
	private final ModelRenderer spawner;

	public ExtendedBannerModel() {
		texWidth = 20;
		texHeight = 26;

		spawner = new ModelRenderer(this, 0, 0);
		spawner.setPos(0.0F, 0.0F, 0.0F);
		spawner.addBox(-5.0F, 0.0F, 0.0F, 10.0F, 26.0F, 0.0F, 0.0F, true);
	}

	public void renderExtendedToBuffer(ExtendedBannerEntity entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.pushPose();
		spawner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.popPose();
	}
}
