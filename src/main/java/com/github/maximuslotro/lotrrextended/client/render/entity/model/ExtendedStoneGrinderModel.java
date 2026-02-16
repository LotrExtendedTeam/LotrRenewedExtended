package com.github.maximuslotro.lotrrextended.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStoneGrinderModel extends Model {
	public final ModelRenderer stands;
	public final ModelRenderer drum;

	public ExtendedStoneGrinderModel() {
		super(RenderType::entitySolid);
		stands = new ModelRenderer(64, 64, 0, 0);
		stands.setPos(0.0F, 16.0F, 0.0F);
		stands.texOffs(0, 20).addBox(-8.0F, 7.0F, -5.0F, 4.0F, 1.0F, 10.0F, 0.0F, true);
		stands.texOffs(28, 20).addBox(-7.5F, -2.0F, -1.5F, 1.0F, 9.0F, 3.0F, 0.0F, true);
		stands.texOffs(0, 20).addBox(4.0F, 7.0F, -5.0F, 4.0F, 1.0F, 10.0F, 0.0F, false);
		stands.texOffs(28, 20).addBox(6.5F, -2.0F, -1.5F, 1.0F, 9.0F, 3.0F, 0.0F, false);

		drum = new ModelRenderer(64, 64, 0, 0);
		drum.setPos(0.0F, 16.0F, 0.0F);
		drum.texOffs(0, 0).addBox(-6.0F, -5.0F, -5.0F, 12.0F, 10.0F, 10.0F, 0.0F, false);
		drum.texOffs(1, 23).addBox(-8.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);
		drum.texOffs(1, 23).addBox(6.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack pMatrixStack, IVertexBuilder pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
		stands.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
		drum.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
	}
}