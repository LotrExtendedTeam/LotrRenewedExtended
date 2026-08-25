package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedRespawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ExtendedRespawnerModel extends ExtendedNonLivingModel<ExtendedRespawnerEntity> {
	private final ModelRenderer coin;

	public ExtendedRespawnerModel() {
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
	public void renderExtendedToBuffer(ExtendedRespawnerEntity entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		if (entity != null) {
			matrixStack.pushPose();
			//matrixStack.translate(0.0D, 1F, 0.0D);
			matrixStack.mulPose(Vector3f.YP.rotationDegrees((float)MathHelper.lerp(ageInTicks, entity.spinO, entity.spin) * 10.0F));
			float scale = scale(entity.isRespawnerTicking() ? entity.getTickpercentage() : 12000, 1.0F, 1200.0F, 0.1F, 0.9F);
			scale = MathHelper.clamp(scale, 0.1F, 0.9F);
			matrixStack.scale(scale, scale, scale);
			if(entity.getTickpercentage()>0) {
				coin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			}else {
				coin.render(matrixStack, buffer, packedLight, packedOverlay, red, green*0.3F, blue*0.3F, alpha);
			}
			matrixStack.popPose();
		}
	}
}