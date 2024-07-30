package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStructureSpawnerModel extends EntityModel<ExtendedStructureSpawnerEntity> {
	private final ModelRenderer spawner;

	public ExtendedStructureSpawnerModel() {
		texWidth = 32;
		texHeight = 32;

		spawner = new ModelRenderer(this, 6, 3);
		spawner.setPos(0.0F, 0.0F, 0.0F);
		spawner.addBox(-5.0F, 0.0F, 0.0F, 10.0F, 26.0F, 0.0F, 0.0F, true);
	}

	@Override
	public void setupAnim(ExtendedStructureSpawnerEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.spawner.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		spawner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}