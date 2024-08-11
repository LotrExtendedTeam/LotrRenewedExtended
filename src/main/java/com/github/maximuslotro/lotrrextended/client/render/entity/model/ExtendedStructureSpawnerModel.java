package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStructureSpawnerModel extends NonLivingModel<ExtendedStructureSpawnerEntity> {
	private final ModelRenderer spawner;

	public ExtendedStructureSpawnerModel() {
		texWidth = 20;
		texHeight = 26;

		spawner = new ModelRenderer(this, 0, 0);
		spawner.setPos(0.0F, 0.0F, 0.0F);
		spawner.addBox(-5.0F, 0.0F, 0.0F, 10.0F, 26.0F, 0.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
	}

	public void renderExtendedToBuffer(ExtendedStructureSpawnerEntity entity, MatrixStack matrixStack, IVertexBuilder buffer, float ageInTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.pushPose();
		if (entity != null) {
			matrixStack.mulPose(Vector3f.YP.rotationDegrees((float)MathHelper.lerp((double)ageInTicks, entity.spinO, entity.spin) * 10.0F));
		}
		spawner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.popPose();
	}
}