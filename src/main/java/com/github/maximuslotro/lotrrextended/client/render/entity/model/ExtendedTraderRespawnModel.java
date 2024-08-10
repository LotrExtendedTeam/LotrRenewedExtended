package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedTraderRespawnEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

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
	public void setupAnim(ExtendedTraderRespawnEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.spawner.zRot = ((float) Math.PI / 180F) - MathHelper.rotLerp(ageInTicks, entity.zRotO, entity.zRot);
		//this.spawner.yRot = ((float) Math.PI / 180F) - MathHelper.rotLerp(ageInTicks, entity.yRotO, entity.yRot);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		spawner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}