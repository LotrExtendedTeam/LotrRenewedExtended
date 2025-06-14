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

/**
 * Renders an {@link ExtendedBannerEntity} in the world.
 * <p>
 * This class extends {@link EntityRenderer} to customize the rendering behavior for a modded entity,
 * specifically a banner entity with potential debug rendering for its protection area.
 * </p>
 * 
 * <p>
 * When debug mode is enabled, it visualizes the protection range using a wireframe bounding box.
 * </p>
 */
@OnlyIn(Dist.CLIENT)
public class ExtendedBannerRenderer extends EntityRenderer<ExtendedBannerEntity> {
	/**
     * The texture resource used to render the banner entity.
     */
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LotrExtendedMod.MODID, "textures/entity/imgnazgulscreamman.png");
	/**
     * Constructs a new renderer for the {@link ExtendedBannerEntity}.
     *
     * @param entityRenderDispatcher The dispatcher responsible for rendering entities.
     */
	public ExtendedBannerRenderer(EntityRendererManager entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	/**
     * Renders the {@link ExtendedBannerEntity}, and if debug rendering is enabled and the protection range is greater than 0,
     * draws a bounding box representing the protected area around the entity.
     *
     * @param pEntity       The entity being rendered.
     * @param pEntityYaw    The yaw of the entity.
     * @param pPartialTicks Partial tick time used for interpolation.
     * @param pMatrixStack  The matrix stack for rendering transforms.
     * @param pBuffer       The rendering buffer.
     * @param pPackedLight  Combined lightmap coordinates.
     */
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
	/**
     * Returns the texture location for the {@link ExtendedBannerEntity}.
     *
     * @param entity The entity being rendered.
     * @return The {@link ResourceLocation} for the entity's texture.
     */
	@Override
	public ResourceLocation getTextureLocation(ExtendedBannerEntity entity) {
		return TEXTURE_LOCATION;
	}
	/**
     * Controls whether the entity's name should be rendered.
     *
     * @param pEntity The entity being rendered.
     * @return Always returns false; nameplates are not shown for this entity.
     */
	@Override
	protected boolean shouldShowName(ExtendedBannerEntity pEntity) {
		return false;
	}

	/**
     * Renders a wireframe box using integer color values, wrapping min/max bounds from the given {@link AxisAlignedBB}.
     *
     * @param pMatrixStack The matrix stack to apply transformations.
     * @param pBuffer      The buffer to write vertex data to.
     * @param pAabb        The axis-aligned bounding box to draw.
     * @param pRed         Red color component (0–255).
     * @param pGreen       Green color component (0–255).
     * @param pBlue        Blue color component (0–255).
     * @param pAlpha       Alpha transparency component (0–255).
     */
	public static void renderLineBox(MatrixStack pMatrixStack, IVertexBuilder pBuffer, AxisAlignedBB pAabb, int pRed, int pGreen, int pBlue, int pAlpha) {
		renderLineBox(pMatrixStack, pBuffer, pAabb.minX, pAabb.minY, pAabb.minZ, pAabb.maxX, pAabb.maxY, pAabb.maxZ, pRed, pGreen, pBlue, pAlpha);
	}

	/**
     * Renders a wireframe bounding box using specified min/max coordinates and integer color values.
     * This is a modified version of {@code WorldRenderer.renderLineBox} supporting int color parameters.
     *
     * @param pMatrixStack The matrix stack for transformations.
     * @param pBuffer      The vertex buffer.
     * @param pMinX        Minimum X coordinate.
     * @param pMinY        Minimum Y coordinate.
     * @param pMinZ        Minimum Z coordinate.
     * @param pMaxX        Maximum X coordinate.
     * @param pMaxY        Maximum Y coordinate.
     * @param pMaxZ        Maximum Z coordinate.
     * @param pRed         Red color component (0–255).
     * @param pGreen       Green color component (0–255).
     * @param pBlue        Blue color component (0–255).
     * @param pAlpha       Alpha transparency component (0–255).
     */
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