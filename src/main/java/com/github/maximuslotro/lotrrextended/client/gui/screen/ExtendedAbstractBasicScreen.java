package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

public abstract class ExtendedAbstractBasicScreen extends Screen {

	protected ExtendedAbstractBasicScreen(ITextComponent pTitle) {
		super(pTitle);
	}

	public static void fillFloat(MatrixStack pPoseStack, float pMinX, float pMinY, float pMaxX, float pMaxY, int pColorRGBA) {
		innerFill(pPoseStack.last().pose(), pMinX, pMinY, pMaxX, pMaxY, pColorRGBA);
	}

	private static void innerFill(Matrix4f pMatrix, float pMinX, float pMinY, float pMaxX, float pMaxY, int pColorRGBA) {
		if (pMinX < pMaxX) {
			float i = pMinX;
			pMinX = pMaxX;
			pMaxX = i;
		}

		if (pMinY < pMaxY) {
			float j = pMinY;
			pMinY = pMaxY;
			pMaxY = j;
		}

		float f3 = (float)(pColorRGBA >> 24 & 255) / 255.0F;
		float f = (float)(pColorRGBA >> 16 & 255) / 255.0F;
		float f1 = (float)(pColorRGBA >> 8 & 255) / 255.0F;
		float f2 = (float)(pColorRGBA & 255) / 255.0F;
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.vertex(pMatrix, pMinX, pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
		bufferbuilder.vertex(pMatrix, pMaxX, pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
		bufferbuilder.vertex(pMatrix, pMaxX, pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
		bufferbuilder.vertex(pMatrix, pMinX, pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
		bufferbuilder.end();
		WorldVertexBufferUploader.end(bufferbuilder);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public void blitFloat(MatrixStack pMatrixStack, float pX, float pY, float pUOffset, float pVOffset, float pUWidth, float pVHeight) {
		blitFloat(pMatrixStack, pX, pY, this.getBlitOffset(), (float)pUOffset, (float)pVOffset, pUWidth, pVHeight, 256.0F, 256.0F);
	}

	public void blitFloat(MatrixStack pMatrixStack, float pX, float pY, float pUOffset, float pVOffset, float pUWidth, float pVHeight, float pTextureHeight, float pTextureWidth) {
		blitFloat(pMatrixStack, pX, pY, this.getBlitOffset(), (float)pUOffset, (float)pVOffset, pUWidth, pVHeight, pTextureWidth, pTextureHeight);
	}

	public static void blitFloat(MatrixStack pMatrixStack, float pX, float pY, float pBlitOffset, float pUOffset, float pVOffset, float pUWidth, float pVHeight, float pTextureHeight, float pTextureWidth) {
		innerBlitFloat(pMatrixStack, pX, pX + pUWidth, pY, pY + pVHeight, pBlitOffset, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight);
	}

	private static void innerBlitFloat(MatrixStack pMatrixStack, float pX1, float pX2, float pY1, float pY2, float pBlitOffset, float pUWidth, float pVHeight, float pUOffset, float pVOffset, float pTextureWidth, float pTextureHeight) {
		innerBlitFloat(pMatrixStack.last().pose(), pX1, pX2, pY1, pY2, pBlitOffset, pUOffset / pTextureWidth, (pUOffset + pUWidth) / pTextureWidth, pVOffset / pTextureHeight, (pVOffset + pVHeight) / pTextureHeight);
	}

	private static void innerBlitFloat(Matrix4f pMatrix, float pX1, float pX2, float pY1, float pY2, float pBlitOffset, float pMinU, float pMaxU, float pMinV, float pMaxV) {
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(pMatrix, (float)pX1, (float)pY2, (float)pBlitOffset).uv(pMinU, pMaxV).endVertex();
		bufferbuilder.vertex(pMatrix, (float)pX2, (float)pY2, (float)pBlitOffset).uv(pMaxU, pMaxV).endVertex();
		bufferbuilder.vertex(pMatrix, (float)pX2, (float)pY1, (float)pBlitOffset).uv(pMaxU, pMinV).endVertex();
		bufferbuilder.vertex(pMatrix, (float)pX1, (float)pY1, (float)pBlitOffset).uv(pMinU, pMinV).endVertex();
		bufferbuilder.end();
		RenderSystem.enableAlphaTest();
		WorldVertexBufferUploader.end(bufferbuilder);
	}
}
