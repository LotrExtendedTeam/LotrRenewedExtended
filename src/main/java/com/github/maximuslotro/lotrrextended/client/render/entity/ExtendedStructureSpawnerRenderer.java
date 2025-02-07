package com.github.maximuslotro.lotrrextended.client.render.entity;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.client.render.entity.model.ExtendedStructureSpawnerModel;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedClientConfig;
import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedStructureSpawnerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedStructureSpawnerRenderer extends EntityRenderer<ExtendedStructureSpawnerEntity> {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/imgnazgulscreamman.png");
	private ExtendedStructureSpawnerModel model =  new ExtendedStructureSpawnerModel();
	public ExtendedStructureSpawnerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	@Override
	public void render(ExtendedStructureSpawnerEntity pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		RenderType rendertype = shouldHide ? null : model.renderType(TEXTURE_LOCATION);
		if (rendertype != null) {
			IVertexBuilder ivertexbuilder = pBuffer.getBuffer(rendertype);
			this.model.renderExtendedToBuffer(pEntity, pMatrixStack, ivertexbuilder, pPartialTicks, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			if (ExtendedClientConfig.enableDevMode.get() && !pEntity.getCMaxBlockPos().equals(BlockPos.ZERO) && !pEntity.getSMaxBlockPos().equals(BlockPos.ZERO)) {
//				IVertexBuilder lineBuilder = pBuffer.getBuffer(RenderType.lines());
//				RenderSystem.lineWidth(10.0f);
//				BlockPos minPosC = pEntity.getCMinBlockPos();
//				BlockPos maxPosC = pEntity.getCMaxBlockPos();
//				BlockPos minPosS = pEntity.getSMinBlockPos();
//				BlockPos maxPosS = pEntity.getSMaxBlockPos();
//				pMatrixStack.pushPose();
//				WorldRenderer.renderLineBox(pMatrixStack, lineBuilder, minPosC.getX(), minPosC.getY(), minPosC.getZ(), maxPosC.getX(), maxPosC.getY(), maxPosC.getZ(), 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
//				pMatrixStack.popPose();
//				pMatrixStack.pushPose();
//				WorldRenderer.renderLineBox(pMatrixStack, lineBuilder, minPosS.getX(), minPosS.getY(), minPosS.getZ(), maxPosS.getX(), maxPosS.getY(), maxPosS.getZ(), 0.9F, 0.9F, 0.9F, 1.0F, 0.2F, 0.2F, 0.2F);
//				pMatrixStack.popPose();
				pMatrixStack.pushPose();
				//RenderSystem.disableDepthTest();
				RenderSystem.disableCull();
				IVertexBuilder ivertexBuilder = pBuffer.getBuffer(RenderType.lines());
				RenderSystem.lineWidth(10.0f);
				IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
				if (buffer == null)
					return;
				IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
				pMatrixStack.pushPose();
				pMatrixStack.translate(-((int) pEntity.getX())-0.5D, -((int) pEntity.getY()), -((int) pEntity.getZ())-0.5D);
				renderLineBox(pMatrixStack, builder, pEntity.getCheckArea(), 51, 204, 255, 255);
				pMatrixStack.popPose();
				pMatrixStack.pushPose();
				pMatrixStack.translate(-((int) pEntity.getX())-0.5D, -((int) pEntity.getY()), -((int) pEntity.getZ())-0.5D);
				renderLineBox(pMatrixStack, builder, pEntity.getSpawnArea(), 255, 255, 0, 255);
				pMatrixStack.popPose();
				RenderSystem.disableDepthTest();
				//RenderSystem.disableCull();
				buffer.endBatch(RenderType.LINES);
				pMatrixStack.popPose();
			}
		}
		pMatrixStack.popPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(ExtendedStructureSpawnerEntity entity) {
		return TEXTURE_LOCATION;
	}

	@Override
	protected boolean shouldShowName(ExtendedStructureSpawnerEntity pEntity) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean shouldHide = pEntity.isInvisibleTo(minecraft.player) || minecraft.options.hideGui;
		return !shouldHide;
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