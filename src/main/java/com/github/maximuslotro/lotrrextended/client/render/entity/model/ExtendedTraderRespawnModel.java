package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedTraderRespawnModel extends ExtendedNonLivingModel<ExtendedTraderRespawnEntity> {
	private final ModelRenderer coin;

	public ExtendedTraderRespawnModel() {
		texWidth = 32;
		texHeight = 32;

		coin = new ModelRenderer(this);
		coin.setPos(0.0F, 4.5F, 0.0F);
		coin.texOffs(0, 0).addBox(-3.5F, -3.5F, -0.5F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		coin.texOffs(0, 8).addBox(3.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		coin.texOffs(4, 8).addBox(-4.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		coin.texOffs(16, 0).addBox(-2.5F, 3.5F, -0.5F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		coin.texOffs(16, 3).addBox(-2.5F, -4.5F, -0.5F, 5.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void renderExtendedToBuffer(ExtendedTraderRespawnEntity entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.pushPose();
		if (entity != null) {
			//float f = 0.53125F;
			//float f1 = Math.max(entity.getBbWidth(), entity.getBbHeight());
			//if ((double)f1 > 1.0D) {
			//	f /= f1;
			//}

			//matrixStack.translate(0.0D, (double)0.4F, 0.0D);
			matrixStack.mulPose(Vector3f.YP.rotationDegrees((float)MathHelper.lerp(ageInTicks, entity.spinO, entity.spin) * 10.0F));
			//matrixStack.translate(0.0D, (double)-0.2F, 0.0D);
			//matrixStack.mulPose(Vector3f.XP.rotationDegrees(-30.0F));
			float scale = scale(entity.getTickpercentage(), 1.0F, 1200.0F, 0.1F, 0.9F);
			if(scale >0.9F) {
				matrixStack.scale(0.9F, 0.9F, 0.9F);
			}else if(scale <0.1F) {
				matrixStack.scale(0.1F, 0.1F, 0.1F);
			}else {
				matrixStack.scale(scale, scale, scale);
			}
		}
		coin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.popPose();
	}
}