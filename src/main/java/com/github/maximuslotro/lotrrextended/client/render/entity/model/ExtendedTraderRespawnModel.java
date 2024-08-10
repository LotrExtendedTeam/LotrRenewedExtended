package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedTraderRespawnModel extends EntityModel<ExtendedTraderRespawnEntity> {
	private final ModelRenderer spawner;

	public ExtendedTraderRespawnModel() {
		texWidth = 16;
		texHeight = 16;

		spawner = new ModelRenderer(this, 0, 0);
		spawner.setPos(0.0F, 4.0F, 0.0F);
		spawner.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(ExtendedTraderRespawnEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
	}

	public void render(ExtendedTraderRespawnEntity entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.pushPose();
		if (entity != null) {
			//float f = 0.53125F;
			//float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
			//if ((double)f1 > 1.0D) {
			//	f /= f1;
			//}

			//matrixStack.translate(0.0D, (double)0.4F, 0.0D);
			matrixStack.mulPose(Vector3f.YP.rotationDegrees((float)MathHelper.lerp((double)ageInTicks, entity.spinO, entity.spin) * 10.0F));
			//matrixStack.translate(0.0D, (double)-0.2F, 0.0D);
			//matrixStack.mulPose(Vector3f.XP.rotationDegrees(-30.0F));
			///matrixStack.scale(f, f, f);
		}

		spawner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.popPose();
	}
}