package com.github.maximuslotro.lotrrextended.client.render.entity.tapestry;

import com.github.maximuslotro.lotrrextended.common.entity.item.ExtendedTapestryEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ExtendedLastAllianceTapestryModel extends EntityModel<ExtendedTapestryEntity> {
	private final ModelRenderer bb_main;

	public ExtendedLastAllianceTapestryModel() {
		texWidth = 256;
		texHeight = 256;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 53).addBox(-15.0F, -48.0F, 7.0F, 30.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 49).addBox(-28.0F, -46.0F, 7.0F, 56.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(162, 0).addBox(39.0F, -41.0F, 7.0F, 1.0F, 41.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 51).addBox(-24.0F, -47.0F, 7.0F, 48.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 47).addBox(-30.0F, -45.0F, 7.0F, 60.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 45).addBox(-31.0F, -44.0F, 7.0F, 62.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 43).addBox(-32.0F, -43.0F, 7.0F, 64.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(158, 0).addBox(-40.0F, -41.0F, 7.0F, 1.0F, 41.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-39.0F, -42.0F, 7.0F, 78.0F, 42.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(ExtendedTapestryEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}