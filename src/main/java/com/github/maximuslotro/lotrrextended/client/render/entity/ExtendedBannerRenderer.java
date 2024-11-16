package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedBannerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedBannerRenderer extends EntityRenderer<ExtendedBannerEntity> {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/imgnazgulscreamman.png");
	public ExtendedBannerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	@Override
	public void render(ExtendedBannerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.options.renderDebug && pEntity.getProtectionRange() > 0) {
			pMatrixStack.pushPose();
			IVertexBuilder ivertexBuilder = pBuffer.getBuffer(RenderType.lines());
			RenderSystem.lineWidth(10.0f);
			IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
			if (buffer == null)
				return;
			IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
			pMatrixStack.pushPose();
			pMatrixStack.translate(-((int) pEntity.getX())-0.5D, -((int) pEntity.getY()), -((int) pEntity.getZ())-0.5D);
			renderLineBox(pMatrixStack, builder, pEntity.getProtectionArea(), 0, 255, 0, 255);
			pMatrixStack.popPose();
			RenderSystem.disableDepthTest();
			buffer.endBatch(RenderType.LINES);
			pMatrixStack.popPose();
		}
	}

//	if (minecraft.options.renderDebug && pEntity.getProtectionRange()>0) {
//		RenderSystem.lineWidth(10.0f);
//        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
//        if(buffer == null)
//        	return;
//        IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
//        ClientPlayerEntity player = minecraft.player;
//        double x = player.xOld + (player.getX() - player.xOld) * pPartialTicks;
//        double y = player.yOld + (player.getY() - player.yOld) * pPartialTicks;
//        double z = player.zOld + (player.getZ() - player.zOld) * pPartialTicks;
//		minecraft.player.sendMessage(new StringTextComponent("Show "+pEntity.getProtectionRange()+" "+pEntity.getProtectionArea().toString()+"X:"+x+", Y:"+y+", Z:"+z), Util.NIL_UUID);
//        pMatrixStack.pushPose();
//        pMatrixStack.translate(x, -y, z);
//        WorldRenderer.renderLineBox(pMatrixStack, builder, pEntity.getProtectionArea(), 1.0f, 1.0f, 1.0f, 1.0f);
//        pMatrixStack.popPose();
//        RenderSystem.disableDepthTest();
//        buffer.endBatch(RenderType.LINES);
//        
//	}
//	pMatrixStack.popPose();
//	//super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
//}
	@Override
	public ResourceLocation getTextureLocation(ExtendedBannerEntity entity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected boolean shouldShowName(ExtendedBannerEntity pEntity) {
		return false;
	}

	/**
	 * Modified version of WorldRenderer.renderLineBox to support int colors
	 * */
	public static void renderLineBox(MatrixStack pMatrixStack, IVertexBuilder pBuffer, AxisAlignedBB pAabb, int pRed, int pGreen, int pBlue, int pAlpha) {
		renderLineBox(pMatrixStack, pBuffer, pAabb.minX, pAabb.minY, pAabb.minZ, pAabb.maxX, pAabb.maxY, pAabb.maxZ, pRed, pGreen, pBlue, pAlpha);
	}

	/**
	 * Modified version of WorldRenderer.renderLineBox to support int colors
	 * */
	public static void renderLineBox(MatrixStack pMatrixStack, IVertexBuilder pBuffer, double pMinX, double pMinY, double pMinZ, double pMaxX, double pMaxY, double pMaxZ, int pRed, int pGreen, int pBlue, int pAlpha) {
		Matrix4f matrix4f = pMatrixStack.last().pose();
		float f = (float)pMinX;
		float f1 = (float)pMinY;
		float f2 = (float)pMinZ;
		float f3 = (float)pMaxX;
		float f4 = (float)pMaxY;
		float f5 = (float)pMaxZ;
		pBuffer.vertex(matrix4f, f, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f1, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f2).color(pRed, pGreen, pBlue, pAlpha).endVertex();
		pBuffer.vertex(matrix4f, f3, f4, f5).color(pRed, pGreen, pBlue, pAlpha).endVertex();
	}
}